package etestyonline.service;

import etestyonline.model.TeacherPromoteToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AdminService {

    @PreAuthorize("hasRole('ADMIN')")
    Page<TeacherPromoteToken> getAllClaims(Pageable pageable);

    @PreAuthorize("hasRole('ADMIN')")
    TeacherPromoteToken getClaimById(String claimId);

    @PreAuthorize("hasRole('ADMIN')")
    void acceptPromoteClaimById(String claimId);

    @PreAuthorize("hasRole('ADMIN')")
    void rejectPromoteClaimById(String claimId);
}
