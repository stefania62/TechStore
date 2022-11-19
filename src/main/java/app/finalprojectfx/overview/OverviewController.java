package app.finalprojectfx.overview;

import javafx.scene.control.TextField;

public class OverviewController {

    public TextField salesTodayInput;
    public TextField salesThisMonthInput;
    public TextField salesTotalInput;

    private final OverviewService overviewService = new OverviewService();


    public void initialize() {
        this.salesTodayInput.setText(String.format("$%.2f", overviewService.getSalesTodayTotal()));
        this.salesThisMonthInput.setText(String.format("$%.2f", overviewService.getSalesThisMonthTotal()));
        this.salesTotalInput.setText(String.format("$%.2f", overviewService.getSalesTotal()));
    }
}
