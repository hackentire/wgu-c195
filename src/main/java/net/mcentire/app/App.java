package net.mcentire.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import net.mcentire.database.JDBC;
import net.mcentire.repository.AppointmentRepository;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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

//        var app = new AppointmentRepository().get(1);
//        var start = app.getStart();
//        var end = app.getEnd();
//        System.out.println("APP: \t\t" + start.toString() + " | " + end.toString());
//        System.out.println("APPCONV: \t" + Time.toUtcTimestamp(start) + " | " + Time.toUtcTimestamp(end));
//        System.out.println("APPLOCAL: \t" + Time.toLocalZoneDateTime(start) + " | " + Time.toLocalZoneDateTime(end));
//
//        System.out.println("INSTANT: \t" + Time.getCurrentUtcTime().toString());
//        System.out.println("LOCAL: \t\t" + LocalDateTime.now());

        JDBC.closeConnection();
    }
}