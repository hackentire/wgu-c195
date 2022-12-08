package net.mcentire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import net.mcentire.app.AppContext;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {
    @FXML
    protected static ResourceBundle resourceBundle = AppContext.getResourceBundle();

    /**
     * Helper class for changing JavaFX scenes
     */
    static class SceneLoader {
        private ActionEvent actionEvent;
        private static final String viewPath = "/net/mcentire/view/";
        private static final int defaultWidth = 960;
        private static final int defaultHeight = 720;

        /**
         * @param actionEvent the ActionEvent used to determine the stage
         */
        SceneLoader(ActionEvent actionEvent) {
            this.actionEvent = actionEvent;
        }

//        ActionEvent getActionEvent() { return actionEvent; }

        void setActionEvent(ActionEvent actionEvent) { this.actionEvent = actionEvent; }

        static void ChangeStageTitle(ActionEvent actionEvent, String title) {
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle(title);
        }

        private void ChangeScene(String fxml, String title, int width, int height) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(viewPath + fxml));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, width, height, false, SceneAntialiasing.BALANCED);
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        private void ChangeScene(String fxml, String title) {
            ChangeScene(fxml, title, defaultWidth, defaultHeight);
        }

        /**
         * Changes the JavaFX scene to the Main scene
         */
        void ChangeToMainScene() {
            ChangeScene("main-view.fxml", resourceBundle.getString("scheduler") + " 1.0");
        }

        /**
         * Changes the JavaFX scene to the Log In scene
         */
        void ChangeToLoginScene() {
            ChangeScene("login-view.fxml", resourceBundle.getString("welcome"), defaultWidth, 512);
        }
    }
}
