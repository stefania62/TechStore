package app.finalprojectfx.addInvoiceProduct;
import app.finalprojectfx.db.model.Item;

import java.util.ArrayList;

public class AddInvoiceProductService {
    public ArrayList<Item> getAllItems() {
        return Item.getAllItems();
    }
}
