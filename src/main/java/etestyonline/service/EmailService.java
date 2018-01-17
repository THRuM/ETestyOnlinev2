package etestyonline.service;

import etestyonline.model.PasswordResetToken;
import etestyonline.model.User;
import etestyonline.model.VerificationToken;
import org.springframework.security.access.prepost.PreAuthorize;

public interface EmailService {
    @PreAuthorize("isAnonymous()")
    boolean sendVerificationEmail(VerificationToken token);

    @PreAuthorize("isAnonymous()")
    boolean sendPasswordResetEmail(PasswordResetToken token);

    boolean sendPasswordChangeEmail(User user);
}
