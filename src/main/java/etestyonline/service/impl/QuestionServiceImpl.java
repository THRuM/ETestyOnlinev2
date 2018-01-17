package etestyonline.service.impl;

import com.mongodb.BasicDBObject;
import etestyonline.model.Category;
import etestyonline.model.Question;
import etestyonline.model.User;
import etestyonline.model.util.QuestionDTO;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.CustomAggregationOperation;
import etestyonline.repository.QuestionRepository;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.CategoryService;
import etestyonline.service.QuestionService;
import etestyonline.service.exceptions.BadInputException;
import etestyonline.service.exceptions.CategoryNotFoundException;
import etestyonline.service.exceptions.QuestionNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static etestyonline.model.util.SETTINGS.LOG_NOT_FOUND;
import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MongoOperations mongo;

    private static Logger logger = LogManager.getLogger(QuestionServiceImpl.class);

    private static String defaultImage = "";

    @Override
    public String addQuestion(QuestionDTO questionDTO) {

        Question question;

        if(questionDTO.getQuestionId() != null){
            question = getQuestion(questionDTO.getQuestionId());
        } else {
            question = new Question();
        }

        if(questionDTO.getResetImage() != null && questionDTO.getResetImage()){
            question.setImgStr(null);
        } else {
            byte[] targetArray;

            try {
                targetArray = IOUtils.toByteArray(questionDTO.getImgInputStream());
            } catch (IOException e) {
                logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Bad data for image");
                throw new BadInputException("Bad data for image");
            }

            if(targetArray.length > 0) {
                question.setImgStr(Base64.getEncoder().encodeToString(targetArray));
            }

        }
        // ID != null - edycja; ID == null - dowawanie
        question.setId(questionDTO.getQuestionId());

        question.setText(questionDTO.getText());

        Category category = categoryService.getCategoryById(questionDTO.getCategory());

        if(category == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't edit/save question category with id: " + questionDTO.getCategory() + LOG_NOT_FOUND);
            throw new CategoryNotFoundException("Category with id: " + questionDTO.getCategory() + LOG_NOT_FOUND);
        }

        question.setCategory(category);

        String[] answers = new String[4];
        answers[0] =  questionDTO.getAnswer1();
        answers[1] =  questionDTO.getAnswer2();
        answers[2] =  questionDTO.getAnswer3();
        answers[3] =  questionDTO.getAnswer4();

        Integer numberOfCorrectAnswer = questionDTO.getCorrectAnswer();

        question.setAnswers(answers);
        question.setCorrectAnswer(answers[numberOfCorrectAnswer]);

        question.setOwner(authenticationWrapper.getAuthenticatetUser());

        Question saved = questionRepository.save(question);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Question with id: " + saved.getId() + " created/updated");

        return saved.getId();
    }

    @Override
    public String editQuestion(QuestionDTO questionDTO) {
        Question question = getQuestion(questionDTO.getQuestionId());

        if(question == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't edit question with id: " + questionDTO.getQuestionId() + " question not found");
            throw new QuestionNotFoundException("Question with id: " + questionDTO.getQuestionId() + LOG_NOT_FOUND);
        }

        return addQuestion(questionDTO);
    }

    @Override
    public Question getQuestion(String questionId) {

        User user = authenticationWrapper.getAuthenticatetUser();
        Question question;

        if(user.getRoles().contains(SETTINGS.ADMIN)){
            question = questionRepository.getQuestionById(questionId);
        } else {
            question = questionRepository.getQuestionByIdAndOwner(questionId, authenticationWrapper.getAuthenticatetUser());
        }

        return question;
    }

    @Override
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.getAllByOwner(authenticationWrapper.getAuthenticatetUser(), pageable);
    }

    @Override
    public QuestionDTO getQuestionDTO(String questionId) {
        Question question = getQuestion(questionId);

        if(question == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Question with id: " + questionId + LOG_NOT_FOUND);
            throw new QuestionNotFoundException("Question with id: " + questionId + LOG_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setQuestionId(question.getId());
        questionDTO.setText(question.getText());
        questionDTO.setCategory(question.getCategory().getId());

        String[] answers = question.getAnswers();

        questionDTO.setAnswer1(answers[0]);
        questionDTO.setAnswer2(answers[1]);
        questionDTO.setAnswer3(answers[2]);
        questionDTO.setAnswer4(answers[3]);

        Integer correctAnswer = 0;

        questionDTO.setImgStr(question.getImgStr());

        for(int i=0; i<answers.length; i++) {
            if (question.getCorrectAnswer().equals(answers[i])) {
                correctAnswer = i;
                break;
            }
        }

        questionDTO.setCorrectAnswer(correctAnswer);

        return questionDTO;
    }

    @Override
    public void deleteQuestionById(String questionId) {

        Question question = getQuestion(questionId);

        if(question == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Question with id: " + questionId + LOG_NOT_FOUND);
            throw new QuestionNotFoundException("Question with id " + questionId + LOG_NOT_FOUND);
        }

        questionRepository.delete(question);
        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Question with id: " + questionId + " deleted");
    }

    public List<Question> getRandomQuestionsForCategoryAndAmount(List<Category> categories, Integer amount) {
        //Sprawdzam id kategorii jeśli są poprawne dla danego kontekstu to i pytania przypisane muszą być poprawne
        List<ObjectId> objectIds = new ArrayList<>();

        for (Category c : categories){
            objectIds.add(new ObjectId(c.getId()));
        }

        Aggregation agg = Aggregation.newAggregation(Aggregation.match(
                Criteria.where("category.$id").in(objectIds)),
                new CustomAggregationOperation(
                        new BasicDBObject("$sample", new BasicDBObject("size", amount))
                ));
        AggregationResults<Question> aggRes = mongo.aggregate(agg, "question", Question.class);

        return aggRes.getMappedResults();
    }
}
