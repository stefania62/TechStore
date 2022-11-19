package app.finalprojectfx.saleInvoices;

import app.finalprojectfx.db.model.SaleInvoice;

import java.util.ArrayList;

public class SaleInvoicesService {
    public ArrayList<SaleInvoice> getAllSaleInvoices() {
        return SaleInvoice.getAllSaleInvoices();
    }
}
