package etestyonline.service.impl;

import etestyonline.model.*;
import etestyonline.model.util.SETTINGS;
import etestyonline.model.util.TestProjection;
import etestyonline.repository.TestRepository;
import etestyonline.service.*;
import etestyonline.service.exceptions.BadInputException;
import etestyonline.service.exceptions.BadRoleException;
import etestyonline.service.exceptions.QuestionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUtilService testUtilService;

    private static Logger logger = LogManager.getLogger(TestServiceImpl.class);

    private static String defaultImage = "";

    @Override
    public Test makeTest(TestParams testParams) {

        if(!testUtilService.isTestParamValid(testParams)){
            throw new BadInputException("Wrong parameters for test");
        }

        User authUser = authenticationWrapper.getAuthenticatetUser();

        Test test = new Test();
        test.setUser(authUser);
        test.setStartTime(LocalDateTime.now());

        test.setTime(testParams.getTime());
        test.setNumberOfQuestions(testParams.getAmount());

        List<Category> categories = categoryService.getCategoryForIds(testParams.getCategoryIds());

        test.setCategories(categories);

        List<Question> questions = questionService.getRandomQuestionsForCategoryAndAmount(categories, testParams.getAmount());

        //Shuffling
        List<String> answers;

        for(Question q : questions){
            //Domyślna flaga dla braku odpowiedzi
            q.setSelectedAnswer(99);
            answers = Arrays.asList(q.getAnswers());
            Collections.shuffle(answers);
            q.setAnswers(answers.toArray(q.getAnswers()));
        }

        if(questions.isEmpty()){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : No questions found for given categories");
            throw new QuestionNotFoundException("No questions found for given categories");
        }

        test.setQuestions(questions);

        if(authUser.getRoles().contains(SETTINGS.ADMIN) || authUser.getRoles().contains(SETTINGS.TEACHER)){
            test.setOrgUnit(authUser.getEmail());
        } else {
            test.setOrgUnit(authUser.getPrincipal().getEmail());
        }

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Test started");

        return test;
    }

    @Override
    public Double endTest(Test test) {
        int numberOfCorrectAnswers = 0;
        test.setEndTime(LocalDateTime.now());

        Integer selectedAnswer;

        for(Question question : test.getQuestions()) {
            selectedAnswer = question.getSelectedAnswer();

            if(selectedAnswer == null)
                selectedAnswer = 99;

            if(selectedAnswer != 99) {
                if(question.getCorrectAnswer().equals(question.getAnswers()[selectedAnswer])){
                    numberOfCorrectAnswers++;
                }
            }
        }

        BigDecimal result = BigDecimal.valueOf(((double) numberOfCorrectAnswers / test.getQuestions().size()) * 100.0);
        result = result.setScale(2, RoundingMode.HALF_UP);

        test.setResult(result.doubleValue());

        testRepository.save(test);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Test ended");

        return test.getResult();
    }

    @Override
    public Page<Test> allTests(Pageable pageable) {
        User user = authenticationWrapper.getAuthenticatetUser();

        return testRepository.findAllByUser(user, pageable);
    }

    @Override
    public Page<Test> allTestsForUserId(String userId, Pageable pageable){
        User authUser = authenticationWrapper.getAuthenticatetUser();

        if(authUser.getId().equals(userId)){
            return allTests(pageable);
        }

        User user = userService.getUser(userId);

        if (authUser.getRoles().contains(SETTINGS.ADMIN)) {
            return testRepository.findAllByUser(user, pageable);
        } else {
            if(user.getPrincipal().getId().equals(authUser.getId())) {
                return testRepository.findAllByUser(user, pageable);
            } else {
                logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't access tests for user " + user.getEmail());
                throw new BadRoleException("Can't access tests for user " + user.getEmail());
            }
        }
    }

    @Override
    public Test getTestForId(String testId) {

        if("".equals(defaultImage)){
            defaultImage = testUtilService.getDefaultImage();
        }

        User authUser = authenticationWrapper.getAuthenticatetUser();

        Test test = testRepository.findById(testId);

        for(Question q : test.getQuestions()){
            if(q.getImgStr() == null){
                q.setImgStr(defaultImage);
            }
        }

        if(authUser.getRoles().contains(SETTINGS.ADMIN))
            return test;

        if(test.getUser().getId().equals(authUser.getId()))
            return test;

        User testOwner = test.getUser();

        if(testOwner.getPrincipal().getId().equals(authUser.getId())){
            return test;
        } else {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't access test for user " + test.getUser().getEmail());
            throw new BadRoleException("Can't access test for user " + test.getUser().getEmail());
        }
    }

    @Override
    public Page<Test> getAllTestForTeacher(Pageable pageable) {
        User authUser = authenticationWrapper.getAuthenticatetUser();
        return testRepository.findAllByOrgUnit(authUser.getEmail(), pageable);
    }

    @Override
    public void updateOrgUnitForUser(User user, String newOrgUnit) {
        List<Test> allUserTests = testRepository.findAllByUser(user);

        for(Test t : allUserTests){
            t.setOrgUnit(newOrgUnit);
        }

        testRepository.save(allUserTests);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit for user " + user.getEmail() + " updated to " + newOrgUnit);
    }

    @Override
    public List<TestProjection> getLastFiveTestsForUser() {
        User user = authenticationWrapper.getAuthenticatetUser();
        return testRepository.findTop5ByUserOrderByEndTimeDesc(user);
    }

    @Override
    public long checkTimeForTest(Test test) {
        LocalDateTime maxEndTime = test.getStartTime().plusMinutes(test.getTime());
        Duration duration = Duration.between(LocalDateTime.now(), maxEndTime);
        return duration.getSeconds();
    }
}
