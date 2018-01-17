package etestyonline.repository;

import etestyonline.model.TestSpecs;
import etestyonline.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestSpecsRepository extends MongoRepository<TestSpecs, String> {
    TestSpecs findByOwner(User owner);
}
