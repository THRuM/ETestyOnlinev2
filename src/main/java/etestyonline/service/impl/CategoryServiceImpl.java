package etestyonline.service.impl;

import etestyonline.model.Category;
import etestyonline.model.User;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.CategoryRepository;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.CategoryService;
import etestyonline.service.TestUtilService;
import etestyonline.service.exceptions.CategoryNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static etestyonline.model.util.SETTINGS.LOG_NOT_FOUND;
import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private TestUtilService testUtilService;

    private static Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        User user = authenticationWrapper.getAuthenticatetUser();

        return categoryRepository.findAllByUser(user, pageable);
    }

    @Override
    public List<Category> getAllCategories() {
        User user = authenticationWrapper.getAuthenticatetUser();

        return categoryRepository.findAllByUser(user);
    }

    @Override
    public void addCategory(String categoryValue) {
        User user = authenticationWrapper.getAuthenticatetUser();

        Category category = new Category(user, categoryValue);

        categoryRepository.save(category);
        testUtilService.addCategory(category);
    }

    @Override
    public Category getCategoryById(String categoryId) {
        User user = authenticationWrapper.getAuthenticatetUser();

        Category category;

        if(user.getRoles().contains(SETTINGS.TEACHER) || user.getRoles().contains(SETTINGS.ADMIN)) {
            category = categoryRepository.findByIdAndUser(categoryId, user);
        } else {
            category = categoryRepository.findByIdAndUser(categoryId, user.getPrincipal());
        }

        if(category == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Category with id: " + categoryId + LOG_NOT_FOUND);
            throw new CategoryNotFoundException("Category with id " + categoryId + LOG_NOT_FOUND);
        }

        return category;
    }

    @Override
    public List<Category> getCategoryForIds(List<String> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds);
    }

    @Override
    public void deleteCategoryById(String categoryId) {
        Category category = getCategoryById(categoryId);

        if(category == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Category with id: " + categoryId + LOG_NOT_FOUND);
            throw new CategoryNotFoundException("Category with id: " + categoryId + LOG_NOT_FOUND);
        }

        categoryRepository.delete(category);
    }
}
