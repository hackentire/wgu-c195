package net.mcentire.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.mcentire.app.AppContext;
import net.mcentire.model.Customer;
import net.mcentire.repository.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController {

    public Label loggedInAsLabel;
public Label appTitleLabel;
    // Customer elements
    @FXML
    private TableColumn customerNameColumn;
    @FXML
    private TableColumn customerAddressColumn;
    @FXML
	private TableColumn customerPostalCodeColumn;
    @FXML
	private TableColumn customerPhoneColumn;
    @FXML
	private TableColumn customerDivisionColumn;
    @FXML
	private Label addOrModifyCustomerLabel;
    @FXML
	private TextField customerNameField;
    @FXML
	private TextField customerAddressField;
    @FXML
	private TextField customerPostalCodeField;
    @FXML
	private TextField customerPhoneField;
    @FXML
	private ComboBox customerCountryCombo;
    @FXML
	private ComboBox customerDivisionCombo;
    @FXML
	private TextField customerIdField;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button modifyCustomerButton;
    @FXML
    private TableView customerTable;
    @FXML
    private TableColumn customerIdColumn;

    public void onAddCustomer(ActionEvent actionEvent) {
    }

    public void onModifyCustomer(ActionEvent actionEvent) {
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onCustomerCountryChange(ActionEvent actionEvent) {
    }

    public void onSaveChangesCustomer(ActionEvent actionEvent) {
    }

    public void onCancelChangesCustomer(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInAsLabel.setText("Logged in as: " + AppContext.getActiveUser().getName());

        BindCustomerElements();
    }

    private void BindCustomerElements() {
        CustomerRepository repo = new CustomerRepository();

        ObservableList<Customer> customers = repo.getAll();

        customerTable.setItems(customers);
        // Bind table
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }

    public void onLogOut(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToLoginScene();
        AppContext.setActiveUser(null);
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAppointmentRadioChange(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) {
    }

    public void onEditAppointment(ActionEvent actionEvent) {
    }

    public void onCancelAppointment(ActionEvent actionEvent) {
    }

    public void onSaveChangesAppointment(ActionEvent actionEvent) {
    }

    public void onCancelChangesAppointment(ActionEvent actionEvent) {
    }
}