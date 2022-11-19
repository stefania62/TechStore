package app.finalprojectfx.supplier;

import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.FKConstraintException;

import java.util.ArrayList;

public class SuppliersService {


    public ArrayList<Supplier> getAllSuppliers() {
        return Supplier.getAllSuppliers();
    }

    public void deleteSupplier(Supplier supplier) {
        ArrayList<Item> supplierItems = supplier.getItems();
        if (supplierItems != null && supplierItems.size() > 0) {
            throw new FKConstraintException("This supplier cannot be deleted because it has items which are related to it");
        }
        Supplier.delete(supplier);
    }
}
