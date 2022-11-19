package app.finalprojectfx.db.parser;

import app.finalprojectfx.db.model.SaleInvoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleInvoiceParser {
    private static SaleInvoice createOneFromResultSet(ResultSet resultSet) throws SQLException {
        return new SaleInvoice(
                resultSet.getInt("id"),
                resultSet.getDate("saleDate").toLocalDate()
        );
    }

    public static ArrayList<SaleInvoice> parseSaleInvoicesFromResultSet(ResultSet resultSet) {
        ArrayList<SaleInvoice> saleInvoices = new ArrayList<>();
        try {
            while (resultSet.next()) {
                saleInvoices.add(createOneFromResultSet(resultSet));
            }
            return saleInvoices;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static SaleInvoice parseSaleInvoiceFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return createOneFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

    public static double parseTotal(ResultSet rs) {
        try {
            rs.next();
            return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
