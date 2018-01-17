package etestyonline.model.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        /*
        Nie ma potrzeby inicjalizacji
         */
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDTO user;
        PasswordChangeDTO passwordChangeDTO;
        if(obj instanceof UserDTO) {
            user = (UserDTO) obj;
            return user.getPassword().equals(user.getMatchingPassword());
        } else if(obj instanceof PasswordChangeDTO) {
            passwordChangeDTO = (PasswordChangeDTO) obj;
            return passwordChangeDTO.getPassword().equals(passwordChangeDTO.getMatchingPassword());
        } else {
            return false;
        }
    }
}
