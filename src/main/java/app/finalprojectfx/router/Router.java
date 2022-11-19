package app.finalprojectfx.router;

import app.finalprojectfx.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {
    private static void redirect(ActionEvent event, String view, String title, boolean maximized, int width, int height) {
        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(view));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            newStage.setMaximized(maximized);
            newStage.setTitle("Tech Store | " + title);
            newStage.setScene(scene);
            newStage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void redirectWindowed(ActionEvent event, String view, String title, int width, int height) {
        redirect(event, view, title, false, width, height);
    }

    public static void redirectFullScreen(ActionEvent event, String view, String title) {
        redirect(event, view, title, true, 0, 0);
    }

}
