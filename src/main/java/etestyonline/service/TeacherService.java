package etestyonline.service;

import etestyonline.model.PrincipalToken;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeacherService {

    @PreAuthorize("hasRole('TEACHER')")
    Page<PrincipalToken> getAllClaims(Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    void acceptClaimById(String principalTokenId);

    @PreAuthorize("hasRole('TEACHER')")
    void rejectClaimById(String principalTokenId);

    @PreAuthorize("hasRole('TEACHER')")
    Page<User> getAllAssignedUsers(Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    void addOrgUnit(String description);

    @PreAuthorize("hasRole('TEACHER')")
    void updateOrgUnitForUser(String userId, String orgUnitId);

    @PreAuthorize("hasRole('USER')")
    boolean haveRightsToEdit(String userId);
}
