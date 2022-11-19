package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.SaleInvoiceItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleInvoiceItemParser {
    public static SaleInvoiceItem createOneFromResultSet(ResultSet resultSet) throws SQLException {
        return new SaleInvoiceItem(
                resultSet.getInt("id"),
                resultSet.getDouble("salePrice"),
                resultSet.getInt("soldQuantity"),
                resultSet.getInt("itemId"),
                resultSet.getInt("saleInvoiceId")
        );
    }

    public static ArrayList<SaleInvoiceItem> parseSaleInvoiceItemsFromResultSet(ResultSet resultSet) {
        ArrayList<SaleInvoiceItem> saleInvoiceItems = new ArrayList<>();
        try {
            while (resultSet.next()) {
                saleInvoiceItems.add(createOneFromResultSet(resultSet));
            }
            return saleInvoiceItems;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static SaleInvoiceItem parseSaleInvoiceItemFromResultSet(ResultSet resultSet) {
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
