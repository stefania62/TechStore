package app.finalprojectfx.inventory;

import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.db.model.SaleInvoiceItem;
import app.finalprojectfx.exception.FKConstraintException;

import java.util.ArrayList;

public class InventoryService {
    public ArrayList<Item> getAllItems() {
        return Item.getAllItems();
    }

    public void deleteItem(Item item) {
        ArrayList<SaleInvoiceItem> saleInvoiceItems = item.getSaleInvoiceItems();
        if (saleInvoiceItems != null && saleInvoiceItems.size() > 0) {
            throw new FKConstraintException("This item cannot be deleted because it has sale invoices which are related to it");
        }
        Item.delete(item);
    }
}
