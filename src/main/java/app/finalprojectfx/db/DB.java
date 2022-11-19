package app.finalprojectfx.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class DB {

    private final String dbms;

    private final String serverName;

    private final int portNumber;

    private final String username;

    private final String password;

    private final String schema;

    private static final DB instance = new DB(
            "mysql",
            "localhost",
            3306,
            "root",
            "root",
            "tech_store"
    );

    public DB(String dbms, String serverName, int portNumber, String username, String password, String schema) {
        this.dbms = dbms;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.username = username;
        this.password = password;
        this.schema = schema;
    }

    public static DB getInstance() {
        return instance;
    }

    private Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.username);
        connectionProps.put("password", this.password);
        return DriverManager.getConnection(
                "jdbc:%s://%s:%d/%s".formatted(this.dbms, this.serverName, this.portNumber, this.schema),
                connectionProps);
    }

    public static ArrayList<Object> makeParams(Object... params) {
        return new ArrayList<>(List.of(params));
    }

    public <T> T executeSelect(String query, ArrayList<Object> params, Function<ResultSet, T> parser) {
        try (
                Connection connection = this.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }
            return parser.apply(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T executeSelect(String query, Function<ResultSet, T> parser) {
        return executeSelect(query, null, parser);
    }

    public int executeInsert(String query, ArrayList<Object> params) throws SQLException {
        try (
                Connection connection = this.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            return 0;
        }
    }

    public int executeDelete(String query, ArrayList<Object> params) throws SQLException {
        try (
                Connection connection = this.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            return statement.executeUpdate();
        }
    }

}
