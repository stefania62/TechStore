package app.finalprojectfx.register;

import app.finalprojectfx.db.model.User;
import app.finalprojectfx.db.model.UserRole;
import app.finalprojectfx.exception.UsernameUsedException;

public class RegisterService {

    public User registerNewUser(String firstName, String lastName, String username, String password, UserRole role) {
        User existingUser = User.findByUsername(username);
        if (existingUser != null) { //if there is an actual user with the inputted username
            UsernameUsedException usernameUsedException = new UsernameUsedException("This username is already taken. Choose another one");
            throw usernameUsedException;
        }
        return User.createUser(firstName, lastName, username, password, role);
    }
}
