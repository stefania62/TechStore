package app.finalprojectfx.invoice;

import app.finalprojectfx.domain.InvoiceProductContainer;
import app.finalprojectfx.db.model.SaleInvoice;
import app.finalprojectfx.db.model.SaleInvoiceItem;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceService {

    public SaleInvoice createSaleInvoice(LocalDate saleDate, ArrayList<InvoiceProductContainer> products) {
        SaleInvoice invoice = SaleInvoice.createSaleInvoice(saleDate);
        SaleInvoiceItem.createItemsOfInvoice(invoice, products);
        return invoice;
    }

    public SaleInvoice updateSaleInvoice(SaleInvoice invoice, LocalDate saleDate, ArrayList<InvoiceProductContainer> products) {
        invoice.setSaleDate(saleDate);
        invoice.save();
        SaleInvoiceItem.deleteItemsOfInvoice(invoice);
        SaleInvoiceItem.createItemsOfInvoice(invoice, products);
        return invoice;
    }
}
