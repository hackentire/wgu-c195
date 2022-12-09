package net.mcentire.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import net.mcentire.app.AppContext;
import net.mcentire.model.Country;
import net.mcentire.model.Customer;
import net.mcentire.model.Division;
import net.mcentire.repository.CustomerRepository;

import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class ReportController extends BaseController {


    //region FXML declarations
    @FXML
    private Label loggedInAsLabel;
    @FXML
    private TextArea dataField;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInAsLabel.setText("Logged in as [" + AppContext.getActiveUser().getName() + "]");
    }

    public void onLogOut(ActionEvent actionEvent) {
        AppContext.setActiveUser(null);
        new SceneLoader(actionEvent).ChangeToLoginScene();
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void navigateAppointments(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToAppointmentScene();
    }

    public void navigateCustomers(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToCustomerScene();
    }

    /**
     * Report: Total # of Customers by Type and Month
     * @param actionEvent
     */
    public void loadReport1(ActionEvent actionEvent) {
    }

    /**
     * Report: Schedule for each Contact with: AppointmentID, Title, Type, Description, Start, End, and CustomerID
     * @param actionEvent
     */
    public void loadReport2(ActionEvent actionEvent) {
    }

    /**
     * Report: Custom report of the total # of hours allotted to each customer
     * @param actionEvent
     */
    public void loadReport3(ActionEvent actionEvent) {
    }
}