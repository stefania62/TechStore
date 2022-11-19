package app.finalprojectfx.register;

import app.finalprojectfx.App;
import app.finalprojectfx.alert.Alerts;
import app.finalprojectfx.db.model.User;
import app.finalprojectfx.db.model.UserRole;
import app.finalprojectfx.exception.FormValidationException;
import app.finalprojectfx.exception.UsernameUsedException;
import app.finalprojectfx.validator.Validators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController extends Dialog<User> {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public PasswordField confirmPasswordField;

    public ChoiceBox<UserRole> roleSelect;
    public ImageView imageView;
    public Button signUpButton;
    public Button cancelButton;

    private final RegisterService registerService = new RegisterService();

    public RegisterController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("register-view.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            setTitle("Add User");
            setDialogPane(dialogPane);
            Stage stage = (Stage)dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/assets/React.png"));
            setResultConverter(buttonType -> {
                if(Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return this.saveUser();
                }
                return null;
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void initialize() {
        roleSelect.getItems()
                .addAll(
                        UserRole.ADMINISTRATOR,
                        UserRole.CASHIER,
                        UserRole.MANAGER
                );
    }



    private String validateFirstName() {
        String firstNameFieldName = "First Name";
        String validatedFirstName = Validators.validateRequiredField(firstNameTextField.getText(), firstNameFieldName);
        validatedFirstName = Validators.validateFieldLength(validatedFirstName, 1, 50, firstNameFieldName);
        return validatedFirstName;
    }

    private String validateLastName() {
        String lastNameFieldName = "Last Name";
        String validatedLastName = Validators.validateRequiredField(lastNameTextField.getText(), lastNameFieldName);
        validatedLastName = Validators.validateFieldLength(validatedLastName, 1, 50, lastNameFieldName);
        return validatedLastName;
    }

    private String validateUsername() {
        String usernameFieldName = "Username";
        String validatedUsername = Validators.validateRequiredField(usernameTextField.getText(), usernameFieldName);
        validatedUsername = Validators.validateFieldLength(validatedUsername, 1, 15, usernameFieldName);
        return validatedUsername;
    }

    private String validatePassword() {
        String passwordFieldName = "Password";
        String validatedPassword = Validators.validateRequiredField(passwordTextField.getText(), passwordFieldName);
        validatedPassword = Validators.validateFieldLength(validatedPassword, 8, 16, passwordFieldName);

        if (!validatedPassword.equals(confirmPasswordField.getText())) {
            throw new FormValidationException("Passwords do not match");
        }

        return validatedPassword;
    }

    private UserRole validateUserRole() {
        UserRole userRole = roleSelect.getValue();
        if (userRole == null) {
            throw new FormValidationException("User Role is required");
        }
        return userRole;
    }

    @FXML
    protected User saveUser() {
        try { //try to execute the following block of code
            String firstName = validateFirstName();
            String lastName = validateLastName();

            String username = validateUsername();

            String password = validatePassword();

            UserRole role = validateUserRole();

            System.out.println("First name is: " + firstName);
            System.out.println("Last name is: " + lastName);
            System.out.println("Username is: " + username);
            System.out.println("Password is: " + password);
            System.out.println("Role is: " + role);
            return this.registerService.registerNewUser(firstName, lastName, username, password, role);
        } catch (UsernameUsedException e) { //if we encounter an UserUsedException error:
            Alerts.showErrorAlert("Error", e.getMessage());
        } catch (FormValidationException formValidationException) {
            Alerts.showErrorAlert("Validation Error", formValidationException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showUnknownErrorAlert();
        }
        return null;
    }


}
