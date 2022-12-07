package net.mcentire.app;

import javafx.collections.ObservableList;
import net.mcentire.database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.mcentire.model.Division;
import net.mcentire.repository.DivisionRepository;

import java.util.Locale;

import java.io.IOException;
import java.sql.SQLException;

public class SchedulerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulerApplication.class.getResource("/net/mcentire/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        Locale.setDefault(new Locale("fr"));

        Locale locale = Locale.getDefault();
        String lang = locale.getDisplayLanguage();
        String country = locale.getDisplayCountry();

        JDBC.openConnection();
        launch();

        JDBC.closeConnection();
    }
}