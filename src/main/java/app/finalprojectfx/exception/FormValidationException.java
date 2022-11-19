package app.finalprojectfx.exception;

// user-friendly exception which can be used on alerts
public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}
