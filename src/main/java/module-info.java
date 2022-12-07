module net.mcentire.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens net.mcentire.controller to javafx.fxml;
    exports net.mcentire.controller;
    exports net.mcentire.app;
    opens net.mcentire.app to javafx.fxml;
}