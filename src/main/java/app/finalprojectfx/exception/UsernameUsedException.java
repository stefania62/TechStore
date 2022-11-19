package app.finalprojectfx.exception;

public class UsernameUsedException extends RuntimeException {
    public UsernameUsedException(String message) {
        super(message); //we are calling the constructor of RuntimeException
    }
}
