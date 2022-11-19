package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.ItemParser;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

//this class represents a table on the db
public class Item {
    private final int id;
    private final double purchasedPrice;
    private final int inventoryQuantity;
    private final String name;
    private final String description;
    private final LocalDate purchasedDate;
    private final int categoryId;
    private final int supplierId;
    private Category category;//category.id = categoryId
    private Supplier supplier;
    private ArrayList<SaleInvoiceItem> saleInvoiceItems;

    public Item(int id, double purchasedPrice, int inventoryQuantity, String name, String description, LocalDate purchasedDate, int categoryId, int supplierId) {
        this.id = id;
        this.purchasedPrice = purchasedPrice;
        this.inventoryQuantity = inventoryQuantity;
        this.name = name;
        this.description = description;
        this.purchasedDate = purchasedDate;
        this.categoryId = categoryId;
        this.supplierId = supplierId;

    }

    public static Item findById(int itemId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from items where id = ?",
                        DB.makeParams(itemId),
                        ItemParser::parseItemFromResultSet
                );
    }

    public static ArrayList<Item> findBySupplierId(int supplierId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from items where supplierId = ?",
                        DB.makeParams(supplierId),
                        ItemParser::parseItemsFromResultSet
                );
    }


    public static ArrayList<Item> findByCategoryId(int categoryId) {
        return DB.getInstance()
                .executeSelect(
                        "select * from items where categoryId = ?",
                        DB.makeParams(categoryId),
                        ItemParser::parseItemsFromResultSet
                );
    }


    public static ArrayList<Item> getAllItems() {
        return DB.getInstance()
                .executeSelect(
                        "select * from items",
                        ItemParser::parseItemsFromResultSet
                );
    }


    public static Item createItem(double purchasedPrice, int inventoryQuantity, String name, String description, LocalDate purchasedDate, int categoryId, int supplierId) {
        try {
            int insertedId = DB.getInstance()
                    .executeInsert(
                            "insert into items (purchasedPrice, inventoryQuantity, name, description, purchasedDate, categoryId, supplierId) values (?, ?, ?, ?, ?, ?, ?)",
                            DB.makeParams(purchasedPrice, inventoryQuantity, name, description, purchasedDate, categoryId, supplierId)
                    );
            return findById(insertedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item findByName(String name) {
        return DB.getInstance().executeSelect(
                "select * from items where name = ?",
                DB.makeParams(name),
                ItemParser::parseItemFromResultSet
        );
    }

    public static void delete(Item item) {
        try {
            DB.getInstance()
                    .executeDelete(
                            "delete from items where id = ?",
                            DB.makeParams(item.getId())
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void loadCategory() {
        this.category = Category.findCategoryById(this.categoryId); //get category from db
    }

    public Category getCategory() {
        this.loadCategory(); //load category from db
        return category; //return it
    }

    public Category dbGetCategory() {
        this.loadCategory(); //load category from db
        return category; //return it
    }

    public void loadSupplier() {
        this.supplier = Supplier.findById(this.supplierId);
    }

    public Supplier getSupplier() {
        this.loadSupplier();
        return supplier;
    }

    public int getId() {
        return id;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    @Override
    public String toString() {
        return this.name + " | " + this.description;
    }


    public void loadSaleInvoiceItems() {
        this.saleInvoiceItems = SaleInvoiceItem.findByItemId(this.id);
    }

    public ArrayList<SaleInvoiceItem> getSaleInvoiceItems() {
        this.loadSaleInvoiceItems();
        return saleInvoiceItems;
    }
}
