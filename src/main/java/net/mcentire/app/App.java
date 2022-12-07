package net.mcentire.app;

import net.mcentire.database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/mcentire/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(AppContext.getInstance().getResourceBundle().getString("welcome"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        // Determine the system locale and load the associated resource bundle
        Locale.setDefault(new Locale("fr", "FR"));
        AppContext.getInstance().setResourceBundle(
                ResourceBundle.getBundle("localization/localization", Locale.getDefault())
        );

        JDBC.openConnection();
        launch();

        JDBC.closeConnection();
    }
}