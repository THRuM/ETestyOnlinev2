package etestyonline.repository;

import etestyonline.model.OrgUnit;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    List<User> findByRolesContains(String role);
    Page<User> findAllByPrincipal(User principal, Pageable pageable);
    Page<User> findAllByOrgUnit(OrgUnit orgUnit, Pageable pageable);
    User findByIdAndPrincipal(String userId, User principal);
    Page<User> findAll(Pageable pageable);
    User findByIdAndRolesContains(String id, String role);
    Page<User> findByRolesContainsAndIdNotIn(String role, String[] ids, Pageable pageable);
}
