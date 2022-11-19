package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.UserParser;

import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;

    private String name;
    private String surname;
    private String username;
    private String password;

    private UserRole role;


    public User(int id, String name, String surname, String username, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return this.name + " " + this.getSurname();
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public static User findByUsername(String username) {
        return DB.getInstance()
                .executeSelect(
                        "select * from users where username = ?",
                        DB.makeParams(username),
                        UserParser::parseUserFromResultSet
                );
    }

    public static User createUser(String firstName, String lastName, String username, String password, UserRole role) {
        try {
            int createdUserId = DB.getInstance()
                    .executeInsert(
                            "insert into users (name, surname, username, password, role) values (?, ?, ?, ?, ?)",
                            DB.makeParams(firstName, lastName, username, password, role.toString())
                    );
            return findById(createdUserId);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static User findById(int userId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from users where id = ?",
                        DB.makeParams(userId),
                        UserParser::parseUserFromResultSet
                );
    }


    public static ArrayList<User> getAllUsers() {
        return DB.getInstance()
                .executeSelect(
                        "select * from users",
                        UserParser::parseUsersFromResultSet
                );
    }
}
