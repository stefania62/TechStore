package app.finalprojectfx.addInvoiceProduct;

import app.finalprojectfx.App;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.domain.InvoiceProductContainer;
import app.finalprojectfx.exception.FormValidationException;
import app.finalprojectfx.validator.Validators;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Objects;

public class AddInvoiceProductController extends Dialog<InvoiceProductContainer> {//dialog kthen gjithmone rezultat & llojin e rez e caktojme kur e bejmeextend klasen dialog

    public AddInvoiceProductController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("addInvoiceProduct.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initModality(Modality.APPLICATION_MODAL);//mos humbase fokusi
            setResizable(false);//te mos ndryshohet size
            setTitle("Add Product");
            setDialogPane(dialogPane);
            setResultConverter(buttonType -> {
                //if Add Item btn is clicked
                if(Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return this.getInvoiceProductResult();
                }
                return null;
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TextField quantityTextField;

    public TextField sellingPriceTextField;
    public ChoiceBox<Item> itemSelect;

    private final AddInvoiceProductService addInvoiceProductService = new AddInvoiceProductService();

    public void initialize() {
        itemSelect.setItems(FXCollections.observableList(addInvoiceProductService.getAllItems()));
    }

    private int validateQuantity() {
        return Validators.validatePositiveInteger(quantityTextField.getText(), "Quantity");
    }

    private Item validateItem() {
        Item item = itemSelect.getValue();
        if (item == null) {
            throw new FormValidationException("Item is required");
        }
        return item;
    }

    private double validateSellingPrice() {
        return Validators.validatePositiveDouble(sellingPriceTextField.getText(), "Selling Price");
    }

    public InvoiceProductContainer getInvoiceProductResult() {
        try {
            Item item = this.validateItem();
            int quantity = this.validateQuantity();
            double sellingPrice = this.validateSellingPrice();
            System.out.println("Item: " + item.getName());
            System.out.println("Quantity: " + quantity);
            System.out.println("Selling Price: " + sellingPrice);
            return new InvoiceProductContainer(item, quantity, sellingPrice);
        } catch (FormValidationException ex) {
            Alerts.showErrorAlert("Validation Error", ex.getMessage());
            return null;
        }
    }
}
