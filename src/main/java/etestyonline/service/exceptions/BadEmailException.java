package etestyonline.service.exceptions;

public class BadEmailException extends RuntimeException {
    public BadEmailException(String s) {
        super(s);
    }
}
