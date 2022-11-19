package app.finalprojectfx.invoice;

import app.finalprojectfx.App;
import app.finalprojectfx.addInvoiceProduct.AddInvoiceProductController;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.SaleInvoice;
import app.finalprojectfx.db.model.SaleInvoiceItem;
import app.finalprojectfx.domain.InvoiceProductContainer;
import app.finalprojectfx.exception.FormValidationException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InvoiceController extends Dialog<SaleInvoice> {

    private final InvoiceService invoiceService = new InvoiceService();

    private SaleInvoice invoice;

    public InvoiceController(SaleInvoice invoice) {
        try {
            this.invoice = invoice;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("invoice-view.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            setTitle("Add Invoice");
            setDialogPane(dialogPane);
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/assets/React.png"));
            setResultConverter(buttonType -> {
                if (Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return this.saveSaleInvoice();
                }
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public InvoiceController() {
        this(null);
    }

    public TableView<InvoiceProductContainer> productsTable;
    public DatePicker invoiceDatePicker;
    public TableColumn<InvoiceProductContainer, String> productsTableProductNameColumn;
    public TableColumn<InvoiceProductContainer, Integer> productsTableQuantityColumn;
    public TableColumn<InvoiceProductContainer, Double> productsTableSellingPriceColumn;
    public TableColumn<InvoiceProductContainer, Double> productsTableValueColumn;

    public void initialize() {
        //we are telling the table columns what to show
        if (invoice != null) {
            this.invoiceDatePicker.setValue(invoice.getSaleDate());
            ArrayList<SaleInvoiceItem> items = invoice.getInvoiceItems();
            ArrayList<InvoiceProductContainer> products = new ArrayList<>();

            for (SaleInvoiceItem invoiceItem : items) {
                products.add(new InvoiceProductContainer(invoiceItem.getItem(), invoiceItem.getSoldQuantity(), invoiceItem.getSalePrice()));
            }
            productsTable.setItems(FXCollections.observableList(products));
        }

        productsTableProductNameColumn.setCellValueFactory((row) -> new SimpleStringProperty(row.getValue().getItem().getName()));
        productsTableQuantityColumn.setCellValueFactory((row) -> new SimpleObjectProperty<>(row.getValue().getQuantity()));
        productsTableSellingPriceColumn.setCellValueFactory((row) -> new SimpleObjectProperty<>(row.getValue().getPrice()));
        productsTableValueColumn.setCellValueFactory((row) -> new SimpleObjectProperty<>(row.getValue().getPrice() * row.getValue().getQuantity()));
    }

    @FXML
    protected void onAddProductClick() {
        AddInvoiceProductController invoiceDialog = new AddInvoiceProductController();
        Optional<InvoiceProductContainer> addInvoiceProductResult = invoiceDialog.showAndWait();
        if (addInvoiceProductResult.isEmpty()) {
            return;
        }
        productsTable.getItems().add(addInvoiceProductResult.get());
    }

    private ArrayList<InvoiceProductContainer> validateTableProducts() {
        List<InvoiceProductContainer> tableProducts = productsTable.getItems().stream().toList();
        if (tableProducts.isEmpty()) {
            throw new FormValidationException("Invoice Products are required");
        }
        return new ArrayList<>(tableProducts);
    }

    private LocalDate validateInvoiceDate() {
        LocalDate date = invoiceDatePicker.getValue();
        if (date == null) {
            throw new FormValidationException("Invoice Date is required");
        }
        return date;
    }


    private SaleInvoice saveSaleInvoice() {
        try {
            ArrayList<InvoiceProductContainer> products = this.validateTableProducts();
            LocalDate date = this.validateInvoiceDate();
            if (this.invoice != null) {
                return this.invoiceService.updateSaleInvoice(invoice, date, products);
            }
            return this.invoiceService.createSaleInvoice(date, products);
        } catch (FormValidationException ex) {
            Alerts.showErrorAlert("Error", ex.getMessage());
            return null;
        }
    }

}
