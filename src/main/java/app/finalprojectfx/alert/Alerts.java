package app.finalprojectfx.alert;

import app.finalprojectfx.App;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.cell.ImageGridCell;

import java.util.Optional;

public class Alerts {
    public static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    public static void showUnknownErrorAlert() {
        Alerts.showErrorAlert("Unknown Error", "An unknown error occurred. Contact the administrator");
    }

    public static void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    public static void showConfirmationAlertAndWait(String title, String content, Runnable onConfirmation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setContentText(content);
        Optional<ButtonType> buttonTypeOptional = alert.showAndWait();
        buttonTypeOptional.ifPresent((buttonType -> {
            if (buttonType.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                onConfirmation.run();
            }
        }));




    }
}
