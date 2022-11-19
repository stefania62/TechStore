package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.SaleInvoiceItemParser;
import app.finalprojectfx.domain.InvoiceProductContainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class SaleInvoiceItem {
    private int id;//id e saleinvoiceItem
    private double salePrice;
    private int soldQuantity;
    private int itemId;
    private int saleInvoiceId;

    private Item item;
    private SaleInvoice saleInvoice;

    public SaleInvoiceItem(int id, double salePrice, int soldQuantity, int itemId, int saleInvoiceId) {
        this.id = id;
        this.salePrice = salePrice;
        this.soldQuantity = soldQuantity;
        this.itemId = itemId;
        this.saleInvoiceId = saleInvoiceId;
    }

    public static void deleteItemsOfInvoice(SaleInvoice invoice) {
        try {
            DB.getInstance()
                    .executeDelete(
                            "delete from sale_invoice_items where saleInvoiceId = ?",
                            DB.makeParams(invoice.getId())
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getId() {
        return id;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSaleInvoiceId() {
        return saleInvoiceId;
    }

    public void loadItem() {
        this.item = Item.findById(this.itemId);
    }

    public Item getItem() {
        this.loadItem();
        return item;
    }

    public void loadSaleInvoice() {
        this.saleInvoice = SaleInvoice.findById(this.saleInvoiceId);
    }

    public SaleInvoice getSaleInvoice() {
        this.loadSaleInvoice();
        return saleInvoice;
    }


    public static SaleInvoiceItem findById(int id) {
        return DB.getInstance()
                .executeSelect(
                        "select * from sale_invoice_items where id = ?",
                        DB.makeParams(id),
                        SaleInvoiceItemParser::parseSaleInvoiceItemFromResultSet
                );
    }

    public static ArrayList<SaleInvoiceItem> findBySaleInvoiceId(int invoiceId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from sale_invoice_items where saleInvoiceId = ?",
                        DB.makeParams(invoiceId),
                        SaleInvoiceItemParser::parseSaleInvoiceItemsFromResultSet
                );
    }

    public static ArrayList<SaleInvoiceItem> findByItemId(int itemId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from sale_invoice_items where itemId = ?",
                        DB.makeParams(itemId),
                        SaleInvoiceItemParser::parseSaleInvoiceItemsFromResultSet
                );
    }

    public static ArrayList<SaleInvoiceItem> createItemsOfInvoice(SaleInvoice invoice, ArrayList<InvoiceProductContainer> products) {
        try {
        for (InvoiceProductContainer product : products) {
                DB.getInstance()
                        .executeInsert(
                                "insert into sale_invoice_items (salePrice, soldQuantity, itemId, saleInvoiceId) values (?, ?, ?, ?)",
                                DB.makeParams(product.getPrice(), product.getQuantity(), product.getItem().getId(), invoice.getId())
                        );
        }
        return findBySaleInvoiceId(invoice.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getValue() {
        return this.soldQuantity * this.salePrice;
    }
}
