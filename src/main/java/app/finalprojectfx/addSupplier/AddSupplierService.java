package app.finalprojectfx.addSupplier;

import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.DuplicateEntryException;

public class AddSupplierService {
    public Supplier createSupplier(String name, String address) {
        Supplier existingSupplier = Supplier.findByName(name);
        if (existingSupplier != null) {
            throw new DuplicateEntryException("This supplier is already registered on the system");
        }
        return Supplier.createSupplier(name, address);
    }
}
