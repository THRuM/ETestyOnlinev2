package etestyonline.model.util;

import etestyonline.model.Category;

import java.util.List;

public interface TestProjection {
    String getId();
    List<Category> getCategories();
    Double getResult();
    Integer getNumberOfQuestions();
}
