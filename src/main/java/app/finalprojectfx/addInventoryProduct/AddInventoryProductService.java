package app.finalprojectfx.addInventoryProduct;

import app.finalprojectfx.db.model.Category;
import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.DuplicateEntryException;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddInventoryProductService {
    public ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return Supplier.getAllSuppliers();
    }

    public Item createItem(double purchasedPrice, int inventoryQuantity, String name, String description, LocalDate purchasedDate, Category category, Supplier supplier) {
        Item existingItem = Item.findByName(name);
        if (existingItem != null) {
            throw new DuplicateEntryException("This product is already registered on the system");
        }

        return Item.createItem(purchasedPrice, inventoryQuantity, name, description, purchasedDate, category.getId(), supplier.getId());
    }
}
