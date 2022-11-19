package app.finalprojectfx.session;

import app.finalprojectfx.db.model.User;

public class Session {
    private User loggedInUser;

    private static final Session instance = new Session();

    public static Session getInstance() {
        return instance;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
