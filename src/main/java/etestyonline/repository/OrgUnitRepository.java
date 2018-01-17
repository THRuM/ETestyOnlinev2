package etestyonline.repository;

import etestyonline.model.OrgUnit;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrgUnitRepository extends MongoRepository<OrgUnit, String> {
    List<OrgUnit> findAllByOwner(User owner);
    Page<OrgUnit> findAllByOwner(User owner, Pageable pageable);
    OrgUnit findByOwnerAndDescription(User owner, String description);
    OrgUnit findByIdAndOwner(String orgUnitId, User owner);
}
