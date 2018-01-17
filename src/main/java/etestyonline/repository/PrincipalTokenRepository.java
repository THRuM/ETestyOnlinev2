package etestyonline.repository;

import etestyonline.model.PrincipalToken;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrincipalTokenRepository extends MongoRepository<PrincipalToken, String> {
    Page<PrincipalToken> findAllByTeacher(User teacher, Pageable pageable);
    PrincipalToken findByUser(User user);
}
