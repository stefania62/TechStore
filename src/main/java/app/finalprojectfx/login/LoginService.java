package app.finalprojectfx.login;

import app.finalprojectfx.db.model.User;//jane ne package te ndryshme duhen bere import
import app.finalprojectfx.exception.AuthenticationException;
import app.finalprojectfx.exception.FormValidationException;

import java.util.FormatFlagsConversionMismatchException;

//business logic
public class LoginService {
    User authenticateUser(String username, String password) {
        User user = User.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("User not found. Please enter the right credentials.");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Password is incorrect!");
        }

        return user;
    }
}
