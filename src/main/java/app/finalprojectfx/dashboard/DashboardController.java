package app.finalprojectfx.dashboard;

import app.finalprojectfx.App;
import app.finalprojectfx.db.model.User;
import app.finalprojectfx.db.model.UserRole;
import app.finalprojectfx.router.Router;
import app.finalprojectfx.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DashboardController {
    public Pane mainPane;

    public Label userNameLabel;
    public Button saleInvoicesMenuButton;
    public Button inventoryMenuButton;
    public Button suppliersMenuButton;
    public Button usersMenuButton;


    public void initialize() {
        User loggedInUser = Session.getInstance()
                .getLoggedInUser();
        this.userNameLabel.setText(loggedInUser.getFullName());

        saleInvoicesMenuButton.setDisable(false);

        boolean isInventoryMenuButtonEnabled = loggedInUser.getRole() == UserRole.ADMINISTRATOR || loggedInUser.getRole() == UserRole.MANAGER;
        inventoryMenuButton.setDisable(!isInventoryMenuButtonEnabled);

        boolean isSuppliersMenuButtonEnabled = loggedInUser.getRole() == UserRole.ADMINISTRATOR || loggedInUser.getRole() == UserRole.MANAGER;
        suppliersMenuButton.setDisable(!isSuppliersMenuButtonEnabled);

        boolean isUsersMenuButtonEnabled = loggedInUser.getRole() == UserRole.ADMINISTRATOR;
        usersMenuButton.setDisable(!isUsersMenuButtonEnabled);

        showViewOnMainPane("overview-view.fxml");
    }

    @FXML
    protected void onOverviewMenuButtonClick() {
        showViewOnMainPane("overview-view.fxml");
    }

    @FXML
    protected void onSaleInvoicesMenuButtonClick() {
        showViewOnMainPane("saleInvoices-view.fxml");
    }

    @FXML
    protected void onInventoryMenuButtonClick() {
        showViewOnMainPane("inventory-view.fxml");
    }

    @FXML
    protected void onMenuLogoutButtonClick(ActionEvent event) {
        Session.getInstance().setLoggedInUser(null);
        Router.redirectWindowed(event, "login-view.fxml", "Login", 320, 240);
    }

    @FXML
    protected void onUsersMenuButtonClick() {
        showViewOnMainPane("users-view.fxml");
    }

    @FXML
    protected void onSuppliersMenuButtonClick() { showViewOnMainPane("suppliers-view.fxml");
    }

    @FXML
    protected void  onGiftCardsMenuButtonClick(){ showViewOnMainPane("giftcards-view.fxml");}

    private void showViewOnMainPane(String view) {
        try {
            if (mainPane.getChildren().size() > 0) {
                mainPane.getChildren().remove(0);
            }
            mainPane.getChildren().add(FXMLLoader.load(App.class.getResource(view)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
