package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.SupplierParser;

import java.sql.SQLException;
import java.util.ArrayList;

public class Supplier {
    private int id;
    private String name;
    private String address;
    private int itemId;
    private ArrayList<Item> items;


    public Supplier(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static Supplier findById(int id) {
        return DB.getInstance()
                .executeSelect(
                        "select * from suppliers where id = ?",
                        DB.makeParams(id),
                        SupplierParser::parseSupplierFromResultSet
                );
    }

    public static Supplier createSupplier(String name, String address) {
        try {
            int createdSupplierId = DB.getInstance()
                    .executeInsert(
                            "insert into suppliers (name, address) values (?, ?)",
                            DB.makeParams(name, address)
                    );
            return findById(createdSupplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Supplier> getAllSuppliers() {
        return DB.getInstance()
                .executeSelect(
                        "select * from suppliers",
                        SupplierParser::parseSuppliersFromResultSet
                );
    }

    public static Supplier findByName(String name) {
        return DB.getInstance()
                .executeSelect(
                        "select * from suppliers where name = ?",
                        DB.makeParams(name),
                        SupplierParser::parseSupplierFromResultSet
                );
    }

    public static void delete(Supplier supplier) {
        try {
            DB.getInstance()
                    .executeDelete(
                            "delete from suppliers where id = ?",
                            DB.makeParams(supplier.getId())
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getId() {
        return id;
    }

    public void loadItems() {
        this.items = Item.findBySupplierId(this.id);
    }

    public ArrayList<Item> getItems() {
        this.loadItems();
        return items;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
