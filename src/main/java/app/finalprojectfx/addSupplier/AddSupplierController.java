package app.finalprojectfx.addSupplier;

import app.finalprojectfx.App;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.DuplicateEntryException;
import app.finalprojectfx.exception.FormValidationException;
import app.finalprojectfx.validator.Validators;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddSupplierController extends Dialog<Supplier> {

    public TextField supplierNameInput;

    public TextArea supplierAddressTextArea;

    private final AddSupplierService addSupplierService = new AddSupplierService();

    public AddSupplierController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("addSupplier-view.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            setTitle("Add Supplier");
            setDialogPane(dialogPane);

            Stage stage = (Stage)dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/assets/React.png"));

            setResultConverter(buttonType -> {
                if(Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return this.createSupplier();
                }
                return null;
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String validateSupplierName() {
        String fieldName = "Name";
        String supplierName = Validators.validateRequiredField(this.supplierNameInput.getText(), fieldName);
        return Validators.validateFieldLength(supplierName, 3, 45, fieldName);
    }

    private String validateSupplierAddress() {
        String fieldName = "Address";
        String supplierAddress = Validators.validateRequiredField(this.supplierAddressTextArea.getText(), fieldName);
        return Validators.validateFieldLength(supplierAddress, 5, 255, fieldName);
    }


    private Supplier createSupplier() {
        try {
            return this.addSupplierService.createSupplier(this.validateSupplierName(), this.validateSupplierAddress());
        } catch (FormValidationException ex) {
            Alerts.showErrorAlert("Validation Error", ex.getMessage());
        } catch (DuplicateEntryException e) {
            Alerts.showErrorAlert("Error", e.getMessage());
        }catch (Exception e) {
            Alerts.showUnknownErrorAlert();
        }
        return null;
    }
}
