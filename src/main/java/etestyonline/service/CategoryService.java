package etestyonline.service;

import etestyonline.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CategoryService {

    @PreAuthorize("hasRole('TEACHER')")
    Page<Category> getAllCategories(Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    List<Category> getAllCategories();

    @PreAuthorize("hasRole('TEACHER')")
    void addCategory(String categoryValue);

    @PreAuthorize("hasRole('TEACHER')")
    void deleteCategoryById(String categoryId);

    @PreAuthorize("hasRole('TEACHER')")
    Category getCategoryById(String categoryId);

    @PreAuthorize("hasRole('USER')")
    List<Category> getCategoryForIds(List<String> categoryIds);
}
