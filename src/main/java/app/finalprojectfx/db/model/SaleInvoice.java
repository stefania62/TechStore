package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.SaleInvoiceParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class SaleInvoice {
    private int id;
    private LocalDate saleDate;

    private ArrayList<SaleInvoiceItem> invoiceItems;

    public static SaleInvoice findById(int invoiceId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from sale_invoices where id = ?",
                        DB.makeParams(invoiceId),
                        SaleInvoiceParser::parseSaleInvoiceFromResultSet
                );
    }

    public SaleInvoice(int id, LocalDate saleDate) {
        this.id = id;
        this.saleDate = saleDate;
    }

    public static ArrayList<SaleInvoice> getAllSaleInvoices() {
        return DB.getInstance()
                .executeSelect(
                        "select * from sale_invoices",
                        SaleInvoiceParser::parseSaleInvoicesFromResultSet
                );
    }

    public static double getAllSaleInvoicesTotal() {
        return DB.getInstance()
                .executeSelect(
                        """
                              select
                                    sum(sii.soldQuantity * sii.salePrice) total
                                from sale_invoices si
                                    join sale_invoice_items sii on sii.saleInvoiceId = si.id
                              """,
                        SaleInvoiceParser::parseTotal
                );
    }


    public static double getTodaySaleInvoicesTotal() {
        return DB.getInstance()
                .executeSelect(
                        """
                                select
                                    sum(sii.soldQuantity * sii.salePrice) total
                                from sale_invoices si
                                    join sale_invoice_items sii on sii.saleInvoiceId = si.id
                                where date(si.saleDate) = current_date()
                            """,
                        SaleInvoiceParser::parseTotal
                );
    }

    public static double getThisMonthSaleInvoicesTotal() {
        return DB.getInstance()
                .executeSelect(
                        """
                                select
                                    sum(sii.soldQuantity * sii.salePrice) total
                                from sale_invoices si
                                    join sale_invoice_items sii on sii.saleInvoiceId = si.id
                                where date(si.saleDate) >= date_format(now(), '%Y-%m-01')
                             """,
                        SaleInvoiceParser::parseTotal
                );
    }

    public void loadInvoiceItems() {
        this.invoiceItems = SaleInvoiceItem.findBySaleInvoiceId(this.id);
    }

    public ArrayList<SaleInvoiceItem> getInvoiceItems() {
        this.loadInvoiceItems();
        return invoiceItems;
    }

    public static SaleInvoice createSaleInvoice(LocalDate saleDate) {
        try {
            Date date = Date.from(saleDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            int insertedId = DB.getInstance()
                    .executeInsert(
                            "insert into sale_invoices (saleDate) values (?)",
                            DB.makeParams(date)
                    );
            return findById(insertedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return this.id;
    }

    public LocalDate getSaleDate() {
        return this.saleDate;
    }

    public double getTotal() {
        double total = 0;
        ArrayList<SaleInvoiceItem> items = this.getInvoiceItems();
        for (SaleInvoiceItem item : items) {
            total += item.getValue();
        }
        return total;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public void save() {
        try {
            Date date = Date.from(this.saleDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            DB.getInstance()
                    .executeInsert(
                            "update sale_invoices set saleDate = ? where id = ?",
                            DB.makeParams(date, this.id)
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
