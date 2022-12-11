package net.mcentire.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import net.mcentire.database.JDBC;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Initial entry point for the application
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/mcentire/view/login-view.fxml"));
            // Enable antialiasing for clearer scene rendering
            Scene scene = new Scene(fxmlLoader.load(), 960, 480, false, SceneAntialiasing.BALANCED);
            stage.setTitle(AppContext.getResourceBundle().getString("welcome"));
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Test French/France Locale
//        Locale.setDefault(new Locale("fr", "FR"));

        // Determine the system locale and load the associated resource bundle
        AppContext.setResourceBundle(
                ResourceBundle.getBundle("localization/localization", Locale.getDefault())
        );

        // Establish DB connection over lifetime of program
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}