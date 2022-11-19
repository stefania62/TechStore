package app.finalprojectfx.usersManagement;

import app.finalprojectfx.db.model.User;

import java.util.ArrayList;

public class UsersService {

    public ArrayList<User> getAllUsers() {
        return User.getAllUsers();
    }
}
