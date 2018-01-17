package etestyonline.repository;

import etestyonline.model.Test;
import etestyonline.model.User;
import etestyonline.model.util.TestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {
    Page<Test> findAllByUser(User user, Pageable pageable);
    List<Test> findAllByUser(User user);
    Test findById(String testId);
    Page<Test> findAllByOrgUnit(String orgUnit, Pageable pageable);
    List<TestProjection> findTop5ByUserOrderByEndTimeDesc(User user);
}
