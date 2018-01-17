package etestyonline.service;

import etestyonline.model.CustomUserDetails;
import etestyonline.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthenticationWrapper {
    @PreAuthorize("hasRole('USER')")
    CustomUserDetails getAuthenticatetUserDetails();

    @PreAuthorize("hasRole('USER')")
    User getAuthenticatetUser();
}
