package net.mcentire.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class CustomerController extends BaseController {

    //region FXML declarations
    @FXML
    private Label loggedInAsLabel;
    @FXML
    private SplitPane customerSplitPane;
    @FXML
    private TableColumn customerIdColumn;
    @FXML
    private TableColumn customerNameColumn;
    @FXML
    private TableColumn customerAddressColumn;
    @FXML
    private TableColumn customerPostalCodeColumn;
    @FXML
    private TableColumn customerPhoneColumn;
    @FXML
    private TableColumn<Customer, String> customerDivisionColumn;
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
    private Button deleteCustomerButton;
    @FXML
    private TableView<Customer> customerTable;
    //endregion

    private boolean isModifying = false;
    private Customer selectedCustomer = null;

    public void onAddCustomer(ActionEvent actionEvent) {
        isModifying = false;
        addCustomerButton.setDisable(true);
        modifyCustomerButton.setDisable(false);
        addOrModifyCustomerLabel.setText("Add a Customer");
        customerIdField.setText("Auto-generated");
        clearCustomerForm();
        customerSplitPane.setDividerPositions(.5);
        customerNameField.requestFocus();
    }

    public void onModifyCustomer(ActionEvent actionEvent) {
        if (selectedCustomer == null)
            return;

        isModifying = true;
        addCustomerButton.setDisable(false);
        modifyCustomerButton.setDisable(true);
        addOrModifyCustomerLabel.setText("Modify Customer");
        customerIdField.setText("");
        customerSplitPane.setDividerPositions(.5);

        customerIdField.setText(String.valueOf(selectedCustomer.getId()));
        customerNameField.setText(selectedCustomer.getName());
        customerAddressField.setText(selectedCustomer.getAddress());
        customerPostalCodeField.setText(selectedCustomer.getPostalCode());
        customerPhoneField.setText(selectedCustomer.getPhoneNumber());

        int countryId = AppContext.getData().getDivisions().stream().filter(p -> p.getId() == selectedCustomer.getDivisionId())
                .iterator().next().getCountryId();

        // Update ComboBox to display relevant options
        customerDivisionCombo.setDisable(false);
        customerDivisionCombo.setItems(
                FXCollections.observableList(
                        AppContext.getData().getDivisions().stream()
                                .filter(p -> p.getCountryId() == countryId).toList()
                )
        );

        // Iterate over the length of the lists and determine the ComboBox index of both Country and Division for selection
        OptionalInt countryIndex = IntStream.range(0, customerCountryCombo.getItems().size())
                .filter(i -> ((Country) customerCountryCombo.getItems().get(i)).getId() == countryId).findFirst();
        OptionalInt divisionIndex = IntStream.range(0, customerDivisionCombo.getItems().size())
                .filter(i -> ((Division) customerDivisionCombo.getItems().get(i)).getId() == selectedCustomer.getDivisionId()).findFirst();

        if (countryIndex.isPresent())
            customerCountryCombo.getSelectionModel().select(countryIndex.getAsInt());
        if (divisionIndex.isPresent())
            customerDivisionCombo.getSelectionModel().select(divisionIndex.getAsInt());

        customerNameField.requestFocus();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
        if (selectedCustomer == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm this action.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            new CustomerRepository().delete(selectedCustomer.getId());
            AppContext.getData().getCustomers().removeIf(p -> p.getId() == selectedCustomer.getId());
        }
    }

    /**
     * Updates the division combo box with relevant divisions on country selection change
     * @param actionEvent
     */
    public void onCustomerCountryChange(ActionEvent actionEvent) {
        Country selection = (Country) customerCountryCombo.getValue();
        if (selection == null) {
            customerDivisionCombo.setDisable(true);
            customerDivisionCombo.setItems(null);
        } else {
            customerDivisionCombo.setDisable(false);
            customerDivisionCombo.setItems(
                    FXCollections.observableList(
                            AppContext.getData().getDivisions().stream()
                                    .filter(p -> p.getCountryId() == selection.getId()).toList()
                    )
            );
        }
    }

    public void onSaveChangesCustomer(ActionEvent actionEvent) {
        if (!validateCustomerForm())
            return;

        CustomerRepository repo = new CustomerRepository();
        boolean successful = false;
        int index = -1;

        // Build new customer object
        int id;
        try {
            id = Integer.parseInt(customerIdField.getText());
        } catch (NumberFormatException e) {
            id = 0;
        }
        Customer customer = new Customer(
                id,
                customerNameField.getText(),
                customerAddressField.getText(),
                customerPostalCodeField.getText(),
                customerPhoneField.getText(),
                ((Division) customerDivisionCombo.getValue()).getId()
        );

        // Create or Update depending on context
        if (isModifying) {
            var updated = repo.update(customer);
            successful = updated;
            if (updated) {
                index = AppContext.getData().getCustomers().indexOf(selectedCustomer);
                AppContext.getData().getCustomers().removeIf(p -> p.getId() == customer.getId());
            }
        } else {
            var resultId = repo.create(customer);
            successful = resultId > 0;
            customer.setId(resultId);
        }

        // Update local state to avoid DB calls
        if (successful) {
            if (index > -1) {
                // Replace existing entry retaining order for display
                AppContext.getData().getCustomers().add(index, customer);
            } else {
                AppContext.getData().getCustomers().add(customer);
            }
            customerTable.setItems(AppContext.getData().getCustomers());

            // Optionally refresh from DB instead:
            //customerTable.setItems(repo.getAll());

            // Reset form
            onCancelChangesCustomer(actionEvent);
        }
    }

    private boolean validateCustomerForm() {
        if (customerDivisionCombo.getSelectionModel().isEmpty() || customerCountryCombo.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Missing data in form.").showAndWait();
            return false;
        }
        return true;
    }

    public void onCancelChangesCustomer(ActionEvent actionEvent) {
        clearCustomerForm();
        customerSplitPane.setDividerPositions(1);
        addCustomerButton.setDisable(false);
        modifyCustomerButton.setDisable(false);
    }

    private void clearCustomerForm() {
        UiUtil.clear(customerNameField, customerAddressField, customerPhoneField, customerPostalCodeField);
        UiUtil.clear(customerCountryCombo, customerDivisionCombo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInAsLabel.setText("Logged in as [" + AppContext.getActiveUser().getName() + "]");
        customerSplitPane.setDividerPositions(1);
        BindCustomerElements();
    }

    private void BindCustomerElements() {
        CustomerRepository repo = new CustomerRepository();

        ObservableList<Customer> customers = AppContext.getData().getCustomers();
        ObservableList<Division> divisions = AppContext.getData().getDivisions();
        ObservableList<Country> countries = AppContext.getData().getCountries();

        //region ComboBox setup
        customerCountryCombo.setItems(countries);
        customerDivisionCombo.setItems(FXCollections.observableArrayList());
        customerCountryCombo.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country c) {
                if (c != null)
                    return c.getName();
                return "";
            }
            @Override
            public Country fromString(String s) {
                return null;
            }
        });
        customerDivisionCombo.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division d) {
                if (d != null)
                    return d.getName();
                return "";
            }
            @Override
            public Division fromString(String s) {
                return null;
            }
        });
        //endregion

        //region Table setup
        // Bind table
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        /**
         * RUNTIME_ERROR:
         * Fix: Implemented a custom CellValueFactory which looks up the customer's division ID and retrieves its name from
         *     prefetched data in the "divisions" variable for display.
         *
         * LAMBDA_USAGE: Callback<P,R> (a JavaFX functional interface).
         *     Callback type: Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>()
         */
        customerDivisionColumn.setCellValueFactory(p -> {
            int divisionId = p.getValue().getDivisionId();
            /*
             * LAMBDA_USAGE: inline predicate to filter the list of Divisions
             */
            var result = divisions.stream().filter(f -> divisionId == f.getId()).toList();

            // Default to an empty string if not found
            String divisionName = (result.size() > 0) ? result.iterator().next().getName() : "";

            ObservableValue<String> cellValue = new SimpleStringProperty(divisionName);
            return cellValue;
        });

        customerTable.setItems(customers);

        // Add a listener to the table to handle selection changes (to enable/disable modify&delete buttons)
        customerTable.getSelectionModel().selectedItemProperty().addListener((value, oldSelection, newSelection) -> {
            boolean hasSelection = (newSelection != null);
            modifyCustomerButton.setDisable(!hasSelection);
            deleteCustomerButton.setDisable(!hasSelection);
            selectedCustomer = newSelection;
        });

        // Earlier failed attempt at a custom CellValueFactory
//        customerDivisionColumn.setCellFactory(c -> new TableCell<Customer, String>() {
//            @Override
//            protected void updateItem(String divisionId, boolean empty) {
//                super.updateItem(String.valueOf(divisionId), empty);
//                if (divisionId == null || empty) {
//                    setText(null);
//                } else {
//                    String divisionName = divisions.stream().filter(p -> Integer.parseInt(divisionId) == p.getId()).toList().iterator().next().getName();
//                    this.setText(divisionName);
//                }
//            }
//        });

        //endregion
    }

    public void onLogOut(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToLoginScene();
        AppContext.setActiveUser(null);
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void navigateAppointments(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToAppointmentScene();
    }

    public void navigateReports(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToReportScene();
    }
}