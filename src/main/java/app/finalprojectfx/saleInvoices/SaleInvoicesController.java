package app.finalprojectfx.saleInvoices;

import app.finalprojectfx.db.model.SaleInvoice;
import app.finalprojectfx.invoice.InvoiceController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class SaleInvoicesController {

    public TableView<SaleInvoice> saleInvoicesTable;
    public TableColumn<SaleInvoice, Integer> saleInvoicesTableIdColumn;
    public TableColumn<SaleInvoice, LocalDate> saleInvoicesTableDateColumn;
    public TableColumn<SaleInvoice, Double> saleInvoicesTableValueColumn;

    private SaleInvoicesService saleInvoicesService = new SaleInvoicesService();

    public void initialize() {
        reloadTable();
        saleInvoicesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        saleInvoicesTableDateColumn.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        saleInvoicesTableValueColumn.setCellValueFactory((row) -> new SimpleObjectProperty<>(row.getValue().getTotal()));// TODO: 03/11/2022
        saleInvoicesTable.setRowFactory((table) -> {
            TableRow<SaleInvoice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    SaleInvoice saleInvoice = row.getItem();
                    InvoiceController invoiceController = new InvoiceController(saleInvoice);
                    invoiceController.showAndWait();
                    this.reloadTable();
                }
            });
            return row;
        });
    }

    private void reloadTable() {
        ArrayList<SaleInvoice> saleInvoices = this.saleInvoicesService.getAllSaleInvoices();
        saleInvoicesTable.setItems(FXCollections.observableList(saleInvoices));

    }

    @FXML
    protected void onNewInvoiceButtonClick() {
        InvoiceController invoiceController = new InvoiceController();
        Optional<SaleInvoice> optionalInvoice = invoiceController.showAndWait();//Dialog<SaleInvoice> qe ne fund kur te behet shownwait te ktheht ai tip objekti
        //optional eshte nje klase e java-s qe sherben si "container" i nje objekti te llojit <SaleInvoice>

        if (optionalInvoice.isPresent()) {
            SaleInvoice freshSaleInvoice = optionalInvoice.get();
            System.out.println("Created Sale Invoice with id: " + freshSaleInvoice.getId());
            this.reloadTable();
            return;
        }
    }
}
