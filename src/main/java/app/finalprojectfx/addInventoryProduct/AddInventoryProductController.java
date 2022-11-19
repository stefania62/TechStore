package app.finalprojectfx.addInventoryProduct;

import app.finalprojectfx.App;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.Category;
import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.DuplicateEntryException;
import app.finalprojectfx.exception.FormValidationException;
import app.finalprojectfx.validator.Validators;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AddInventoryProductController extends Dialog<Item> {

    public TextField nameTextField;
    public TextField descriptionTextField;
    public TextField purchasedPriceTextField;
    public TextField quantityTextField;
    public ChoiceBox<Category> categoryChoiceBox;
    public ChoiceBox<Supplier> supplierChoiceBox;
    public DatePicker purchasedDatePicker;

    private final AddInventoryProductService addInventoryProductService = new AddInventoryProductService();

    public AddInventoryProductController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("addInventoryProduct.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            setTitle("Add Inventory Product");
            setDialogPane(dialogPane);
            Stage stage = (Stage)dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/assets/React.png"));
            setResultConverter(buttonType -> {
                if (Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return this.addInventoryProduct();
                }
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initialize() {
        categoryChoiceBox.setItems(FXCollections.observableList(addInventoryProductService.getAllCategories()));
        supplierChoiceBox.setItems(FXCollections.observableList(addInventoryProductService.getAllSuppliers()));
    }

    private String validateName() {
        String fieldName = "Name";
        String validatedName = Validators.validateRequiredField(nameTextField.getText(), fieldName);
        validatedName = Validators.validateFieldLength(validatedName, 1, 50, fieldName);
        return validatedName;
    }

    private String validateDescription() {
        String fieldName = "Description";
        String validateDescription = Validators.validateRequiredField(descriptionTextField.getText(), fieldName);
        validateDescription = Validators.validateFieldLength(validateDescription, 1, 50, fieldName);
        return validateDescription;
    }

    private Category validateCategory() {
        Category category = categoryChoiceBox.getValue();
        if (category == null) {
            throw new FormValidationException("Category is required");
        }
        return category;
    }

    private Supplier validateSupplier() {
        Supplier supplier = supplierChoiceBox.getValue();
        if (supplier == null) {
            throw new FormValidationException("Supplier is required");
        }
        return supplier;
    }

    private LocalDate validatePurchaseDate() {
        LocalDate purchaseDate = this.purchasedDatePicker.getValue();
        if (purchaseDate == null) {
            throw new FormValidationException("Purchase Date is required");
        }
        return purchaseDate;
    }

    private int validateQuantity() {
        return Validators.validatePositiveInteger(quantityTextField.getText(), "Quantity");
    }

    private double validatePurchasePrice() {
        return Validators.validatePositiveDouble(purchasedPriceTextField.getText(), "Purchase Price");
    }

    private Item addInventoryProduct() {
        try {
            String name = this.validateName();
            String description = this.validateDescription();
            Category category = this.validateCategory();
            Supplier supplier = this.validateSupplier();
            LocalDate purchaseDate = this.validatePurchaseDate();
            int quantity = this.validateQuantity();
            double purchasePrice = this.validatePurchasePrice();
            return addInventoryProductService.createItem(purchasePrice, quantity, name, description, purchaseDate, category, supplier);
        } catch (FormValidationException ex) {
            Alerts.showErrorAlert("Validation Error", ex.getMessage());
        } catch (DuplicateEntryException e) {
            Alerts.showErrorAlert("Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showUnknownErrorAlert();
        }
        return null;
    }
}
