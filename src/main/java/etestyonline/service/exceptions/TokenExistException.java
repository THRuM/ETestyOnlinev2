package etestyonline.service.exceptions;

public class TokenExistException extends RuntimeException {
    public TokenExistException(String s) {
        super(s);
    }
}
