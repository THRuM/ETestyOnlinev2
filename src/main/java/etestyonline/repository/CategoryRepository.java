package etestyonline.repository;

import etestyonline.model.Category;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Page<Category> findAllByUser(User user, Pageable pageable);
    List<Category> findAllByUser(User user);
    Category findByIdAndUser(String categoryId, User user);
    List<Category> findAllByIdIn(List<String> categoryIds);
}
