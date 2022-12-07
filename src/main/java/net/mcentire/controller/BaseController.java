package net.mcentire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.mcentire.app.App;
import net.mcentire.app.AppContext;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    protected final static AppContext context = AppContext.getInstance();
    @FXML
    protected static ResourceBundle resourceBundle = context.getResourceBundle();

    /**
     * Helper class for changing JavaFX scenes
     */
    static class SceneLoader {
        private ActionEvent actionEvent;
        private static final String viewPath = "/net/mcentire/view/";

        /**
         * @param actionEvent the ActionEvent used to determine the stage
         */
        SceneLoader(ActionEvent actionEvent) {
            this.actionEvent = actionEvent;
        }

        ActionEvent getActionEvent() { return actionEvent; }

        void setActionEvent(ActionEvent actionEvent) { this.actionEvent = actionEvent; }

        static void ChangeStageTitle(ActionEvent actionEvent, String title) {
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle(title);
        }

        /**
         * Changes the JavaFX scene to the Main scene
         * @throws IOException
         */
        void ChangeToMainScene() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "main-view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 900, 512);
            stage.setTitle(resourceBundle.getString("scheduler") + " 1.0");
            stage.setScene(scene);
            stage.show();
        }

        /**
         * Changes the JavaFX scene to the Main scene
         * @throws IOException
         */
        void ChangeToLoginScene() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "login-view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 900, 512);
            stage.setTitle(resourceBundle.getString("welcome"));
            stage.setScene(scene);
            stage.show();
        }

        /**
         * Changes the JavaFX scene to the Add Part scene
         * @throws IOException
         */
        void ChangeToCustomerScene() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "customer-view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 900, 512);
            stage.setScene(scene);
            stage.show();
        }

        /**
         * Changes the JavaFX scene to the Add Product scene
         * @throws IOException
         */
        void ChangeToAppointmentScene() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "appointment-view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 900, 512);
            stage.setScene(scene);
            stage.show();
        }
    }
}
