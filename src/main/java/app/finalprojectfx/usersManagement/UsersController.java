package app.finalprojectfx.usersManagement;
import app.finalprojectfx.db.model.User;
import app.finalprojectfx.db.model.UserRole;
import app.finalprojectfx.register.RegisterController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

public class UsersController {

    public TableView<User> usersTable;
    public TableColumn<String, Integer> usersTableIdColum;
    public TableColumn<String, String> usersTableNameColumn;
    public TableColumn<String, String> usersTableSurnameColumn;
    public TableColumn<String, String> usersTableUsernameColumn;
    public TableColumn<String, UserRole> usersTableRoleColumn;

    private final UsersService usersService = new UsersService();

    @FXML
    public void initialize() {
        this.reloadTable();

        //we are telling the TableView a way to load which properties at which columns
        usersTableIdColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        usersTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersTableSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usersTableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usersTableRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }


    @FXML
    protected void onRegisterUserButtonClick() {
        RegisterController registerUserDialog = new RegisterController();
        Optional<User> optionalUser = registerUserDialog.showAndWait();

        if (optionalUser.isPresent()) {
            User createdUser = optionalUser.get();
            System.out.println("Created User with id: " + createdUser.getId());
            this.reloadTable();
        }
    }

    private void reloadTable() {
        ArrayList<User> allUsersOnDb = usersService.getAllUsers();

        //replace old values if any
        //setItems is the method which is used to set items of a table
        //FXCollections.observableList creates a proper list supported by TableView
        usersTable.setItems(FXCollections.observableList(allUsersOnDb));
    }
}
