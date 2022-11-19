package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemParser {

    private static Item createFromResultSet(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getInt("id"),
                resultSet.getDouble("purchasedPrice"),
                resultSet.getInt("inventoryQuantity"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("purchasedDate").toLocalDate(),
                resultSet.getInt("categoryId"),
                resultSet.getInt("supplierId")
        );
    }

    public static ArrayList<Item> parseItemsFromResultSet(ResultSet resultSet) {
        try {
            ArrayList<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(createFromResultSet(resultSet));
            }
            return items;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Item parseItemFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return createFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }
}
