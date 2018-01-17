package etestyonline.service;

import etestyonline.model.OrgUnit;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrgUnitService {

    @PreAuthorize("isAnonymous()")
    OrgUnit getDefaultOrgUnitForUser(User user);

    @PreAuthorize("hasRole('TEACHER')")
    List<OrgUnit> getAllOrgUnits();

    @PreAuthorize("hasRole('TEACHER')")
    Page<OrgUnit> getAllOrgUnits(Pageable pageable, boolean respectAdminRole);

    @PreAuthorize("hasRole('TEACHER')")
    void deleteOrgUnitById(String orgUnitId);

    @PreAuthorize("hasRole('TEACHER')")
    OrgUnit getOrgUnitById(String orgUnitId);
}
