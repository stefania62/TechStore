package app.finalprojectfx.supplier;

import app.finalprojectfx.addSupplier.AddSupplierController;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.Supplier;
import app.finalprojectfx.exception.FKConstraintException;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class SuppliersController {
    public TableView<Supplier> suppliersTable;
    public TableColumn<Supplier, Integer> suppliersTableIdColumn;
    public TableColumn<Supplier, String> suppliersTableNameColumn;
    public TableColumn<Supplier, String> suppliersTableAddressColumn;
    private final SuppliersService suppliersService = new SuppliersService();


    public void initialize() {
        this.reloadTable();
        this.suppliersTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.suppliersTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.suppliersTableAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void reloadTable() {
        this.suppliersTable.setItems(FXCollections.observableList(this.suppliersService.getAllSuppliers()));
    }

    public void onAddSupplierButtonClick() {
        AddSupplierController addSupplierDialog = new AddSupplierController();
        Optional<Supplier> createdSupplierOptional = addSupplierDialog.showAndWait();
        if (createdSupplierOptional.isPresent()) {
            this.reloadTable();
        }
    }

    public void onDeleteSupplierButtonClick() {
        Alerts.showConfirmationAlertAndWait(
                "Delete Supplier",
                "Are you sure to delete this supplier?",
                this::doDeleteSupplier
        );
    }

    private void doDeleteSupplier() {
        try {
            Supplier selectedSupplier = this.suppliersTable.getSelectionModel().getSelectedItem();
            if (selectedSupplier == null) {
                return;
            }
            this.suppliersService.deleteSupplier(selectedSupplier);
            this.reloadTable();
        } catch (FKConstraintException e) {
            Alerts.showErrorAlert("Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showUnknownErrorAlert();
        }
    }
}
