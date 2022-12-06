module net.mcentire.scheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.mcentire.scheduler to javafx.fxml;
    exports net.mcentire.scheduler;
}