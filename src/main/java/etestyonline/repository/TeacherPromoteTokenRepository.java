package etestyonline.repository;

import etestyonline.model.TeacherPromoteToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherPromoteTokenRepository extends MongoRepository<TeacherPromoteToken, String> {
    TeacherPromoteToken findById(String id);
}
