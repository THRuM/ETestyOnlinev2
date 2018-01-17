package etestyonline.service.impl;

import etestyonline.model.Category;
import etestyonline.model.TestParams;
import etestyonline.model.TestSpecs;
import etestyonline.model.User;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.TestSpecsRepository;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.CategoryService;
import etestyonline.service.TestUtilService;
import etestyonline.service.UserService;
import etestyonline.service.exceptions.BadInputException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class TestUtilServiceImpl implements TestUtilService {

    @Autowired
    private TestSpecsRepository testSpecsRepository;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private static String defaultImage = "";

    private static Logger LOGGER = LogManager.getLogger(TestUtilServiceImpl.class);

    @Override
    public void addTestSpecsForUser(User user) {
        //Używane przy pierwszym promowaniu usera na TEACHERa
        TestSpecs testSpecs = new TestSpecs();
        testSpecs.setOwner(user);

        testSpecs.setAmounts(SETTINGS.amounts);
        testSpecs.setTimes(SETTINGS.times);

        testSpecs.setCategories(new ArrayList<>());

        testSpecsRepository.save(testSpecs);

        LOGGER.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : User " + user.getEmail() + " promoted to TEACHER");
    }

    @Override
    public void addCategory(Category category) {
        TestSpecs testSpecs = getTestSpecs();

        testSpecs.getCategories().add(category);

        testSpecsRepository.save(testSpecs);
    }

    @Override
    public TestSpecs getTestSpecs() {

        User user = authenticationWrapper.getAuthenticatetUser();

        TestSpecs testSpecs;

        if(user.getRoles().contains(SETTINGS.TEACHER) || user.getRoles().contains(SETTINGS.ADMIN)) {
            testSpecs = testSpecsRepository.findByOwner(user);
        } else {
            testSpecs = testSpecsRepository.findByOwner(user.getPrincipal());
        }

        return testSpecs;
    }

    @Override
    public boolean isTestParamValid(TestParams testParams) {
        TestSpecs testSpecs = getTestSpecs();

        boolean isValid = true;

        if(!testSpecs.getAmounts().contains(testParams.getAmount()))
            isValid = false;

        if(!testSpecs.getTimes().contains(testParams.getTime()))
            isValid = false;

        if(testParams.getCategoryIds().isEmpty())
            return false;

        List<Category> categories = categoryService.getCategoryForIds(testParams.getCategoryIds());

        List<Category> possibleCategories = testSpecs.getCategories();

        for(Category category : categories) {
            if(!possibleCategories.contains(category)){
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    @Override
    public void addTimeAndAmount(Integer time, Integer amount) {
        TestSpecs testSpecs = getTestSpecs();

        if(time != null){
            testSpecs.getTimes().add(time);
        }

        if(amount != null){
            testSpecs.getAmounts().add(amount);
        }

        if(time != null || amount != null)
            testSpecsRepository.save(testSpecs);
    }

    @Override
    public void removeTimeAndAmount(String time, String amount) {
        TestSpecs testSpecs = getTestSpecs();

        Integer value = null;

        if(!"none".equals(time)) {
            try {
                value = Integer.valueOf(time);
            } catch (NumberFormatException e) {
                LOGGER.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't add new time - provided value is not a number");
                throw new BadInputException("Can't add new time - provided value is not a number");
            }

            testSpecs.getTimes().remove(value);
        }

        if(!"none".equals(amount)){
            try {
                value = Integer.valueOf(amount);
            } catch (NumberFormatException e) {
                LOGGER.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Can't add new amount - provided value is not a number");
                throw new BadInputException("Can't add new amount - provided value is not a number");
            }

            testSpecs.getAmounts().remove(value);
        }

        if(value != null){
            testSpecsRepository.save(testSpecs);
        }
    }

    @Override
    public void addDefaultPicture(MultipartFile file) {
        TestSpecs testSpecs = getTestSpecs();

        byte[] targetArray;

        try {
            targetArray = IOUtils.toByteArray(file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Bad data for image");
            throw new BadInputException("Bad data for image");
        }

        String encodedImg = Base64.getEncoder().encodeToString(targetArray);

        testSpecs.setDefaultImage(encodedImg);

        testSpecsRepository.save(testSpecs);

        defaultImage = "";
    }

    @Override
    public String getDefaultImage() {

        if("".equals(defaultImage)){
            User admin = userService.getUserForEmail(SETTINGS.HEAD_ADMIN_EMAIL);

            TestSpecs testSpecs = testSpecsRepository.findByOwner(admin);

            defaultImage = testSpecs.getDefaultImage();
        }

        return defaultImage;
    }
}
