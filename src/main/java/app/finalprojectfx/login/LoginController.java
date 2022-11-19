package app.finalprojectfx.login;

import app.finalprojectfx.db.model.User;
import app.finalprojectfx.exception.AuthenticationException;
import app.finalprojectfx.exception.FormValidationException;
import app.finalprojectfx.router.Router;
import app.finalprojectfx.session.Session;
import app.finalprojectfx.validator.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController extends VBox {

    public TextField usernameTextField;
    public PasswordField passwordTextField;

    private final LoginService loginService = new LoginService();

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        try {
            System.out.println("user has inputted username: " + usernameTextField.getText());//getText per te marre infon e krijuar nga java vete
            System.out.println("user has inputted password: " + passwordTextField.getText());

            String username = Validators.validateRequiredField(usernameTextField.getText(), "Username");
            String password = Validators.validateRequiredField(passwordTextField.getText(), "Password");

            User user = loginService.authenticateUser(username, password);
            System.out.println("Hello " + user.getName());
            Session.getInstance().setLoggedInUser(user);
            Router.redirectFullScreen(event, "dashboard-view.fxml", "Dashboard");
        } catch (FormValidationException e) {
            showErrorAlert("VALIDATION ERROR", e.getMessage());
        } catch (AuthenticationException e) {
            showErrorAlert("VALIDATION ERROR", e.getMessage());
        }
    }
}