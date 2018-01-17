package etestyonline.service.impl;

import etestyonline.model.CustomUserDetails;
import etestyonline.model.User;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationWrapperImpl implements AuthenticationWrapper {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails getAuthenticatetUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (CustomUserDetails) authentication.getPrincipal();
    }

    @Override
    public User getAuthenticatetUser() {
        CustomUserDetails customUserDetails = getAuthenticatetUserDetails();
        return userService.getUserForEmail(customUserDetails.getUsername());
    }
}
