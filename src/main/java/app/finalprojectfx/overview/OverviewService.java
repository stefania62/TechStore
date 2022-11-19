package app.finalprojectfx.overview;

import app.finalprojectfx.db.model.SaleInvoice;

public class OverviewService {

    public double getSalesTotal() {
        return SaleInvoice.getAllSaleInvoicesTotal();
    }

    public double getSalesTodayTotal() {
        return SaleInvoice.getTodaySaleInvoicesTotal();
    }

    public double getSalesThisMonthTotal() {
        return SaleInvoice.getThisMonthSaleInvoicesTotal();
    }
}
