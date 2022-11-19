package app.finalprojectfx.inventory;

import app.finalprojectfx.addInventoryProduct.AddInventoryProductController;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.Item;
import app.finalprojectfx.exception.FKConstraintException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Optional;

public class InventoryController {
    public TableColumn<Item, String> productsTableNameColumn;
    public TableColumn<Item, String> productsTableDescriptionColumn;
    public TableColumn<Item, String> productsTableCategoryColumn;
    public TableColumn<Item, LocalDate> productsTablePurchasedDateColumn;
    public TableColumn<Item, Float> productsTablePurchasedPriceColumn;
    public TableColumn<Item, String> productsTableSupplierColumn;
    public TableColumn<Item, Integer> productsTableQuantityColumn;
    public TableView<Item> productsTable;

    private final InventoryService inventoryService = new InventoryService();

    public void initialize() {
        this.reloadTable();

        this.productsTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productsTableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.productsTableCategoryColumn.setCellValueFactory((row) -> new SimpleStringProperty(row.getValue().getCategory().getName()));
        this.productsTablePurchasedDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        this.productsTablePurchasedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));
        this.productsTableSupplierColumn.setCellValueFactory((row) -> new SimpleStringProperty(row.getValue().getSupplier().getName()));
        this.productsTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryQuantity"));
    }

    private void reloadTable() {
        this.productsTable.setItems(FXCollections.observableList(this.inventoryService.getAllItems()));
    }

    public void onAddNewProductButtonClick() {
        AddInventoryProductController inventoryProductDialog = new AddInventoryProductController();
        Optional<Item> optionalItem = inventoryProductDialog.showAndWait();
        if (optionalItem.isPresent()) {
            this.reloadTable();
        }
    }

    public void onDeleteButtonClick() {
        Alerts.showConfirmationAlertAndWait(
                "Delete Product",
                "Are you sure to delete this product?",
                this::doDeleteItem
        );
    }

    private void doDeleteItem() {
        try {
            Item selectedItem = this.productsTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            this.inventoryService.deleteItem(selectedItem);
            this.reloadTable();
        } catch (FKConstraintException e) {
            Alerts.showErrorAlert("Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showUnknownErrorAlert();
        }
    }
}
