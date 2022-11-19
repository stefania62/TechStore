package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierParser {
    private static Supplier createOneFromResultSet(ResultSet resultSet) throws SQLException {
        return new Supplier(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address")
        );
    }

    public static ArrayList<Supplier> parseSuppliersFromResultSet(ResultSet resultSet) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                suppliers.add(createOneFromResultSet(resultSet));
            }
            return suppliers;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Supplier parseSupplierFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return createOneFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }
}
