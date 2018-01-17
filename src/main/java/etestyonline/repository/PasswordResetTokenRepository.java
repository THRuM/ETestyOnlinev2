package etestyonline.repository;

import etestyonline.model.PasswordResetToken;
import etestyonline.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
}
