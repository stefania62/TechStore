package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryParser {
    public static Category createFromResultSet(ResultSet resultSet) throws SQLException {
        return new Category(resultSet.getInt("id"), resultSet.getString("name"));
    }

    public static Category parseCategoryFromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                return createFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static ArrayList<Category> parseCategoriesFromResultSet(ResultSet rs) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            while (rs.next()) {
                categories.add(createFromResultSet(rs));
            }
            return categories;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
