module net.mcentire.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens net.mcentire.controller to javafx.fxml;
    opens net.mcentire.model to javafx.fxml;
    exports net.mcentire.model;
    exports net.mcentire.controller;
    exports net.mcentire.app;
    opens net.mcentire.app to javafx.fxml;
}