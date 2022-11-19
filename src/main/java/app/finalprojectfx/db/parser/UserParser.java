package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.User;
import app.finalprojectfx.db.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserParser {
    public static ArrayList<User> parseUsersFromResultSet(ResultSet resultSet) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(createOneFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static User parseUserFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return createOneFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

    private static User createOneFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                UserRole.valueOf(resultSet.getString("role"))
        );
    }
}
