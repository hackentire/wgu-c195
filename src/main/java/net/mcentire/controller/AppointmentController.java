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
import net.mcentire.model.*;
import net.mcentire.repository.AppointmentRepository;
import net.mcentire.util.Time;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.IntStream;

public class AppointmentController extends BaseController {

    //region FXML declarations
    @FXML
	private SplitPane appointmentSplitPane;
    @FXML
	private Button addAppointmentButton;
    @FXML
	private Button editAppointmentButton;
    @FXML
	private Button removeAppointmentButton;
    @FXML
	private TableView<Appointment> appointmentTable;
    @FXML
	private TableColumn appointmentIdColumn;
    @FXML
	private TableColumn appointmentTitleColumn;
    @FXML
	private TableColumn appointmentDescriptionColumn;
    @FXML
	private TableColumn appointmentLocationColumn;
    @FXML
	private TableColumn appointmentTypeColumn;
    @FXML
	private TableColumn<Appointment, String> appointmentStartColumn;
    @FXML
	private TableColumn<Appointment, String> appointmentEndColumn;
    @FXML
	private TableColumn<Appointment, String> appointmentCustomerColumn;
    @FXML
	private TableColumn<Appointment, String> appointmentUserIdColumn;
    @FXML
	private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML
	private Label addOrEditAppointmentLabel;
    @FXML
	private TextField appointmentTitleField;
    @FXML
	private TextField appointmentLocationField;
    @FXML
	private TextField appointmentTypeField;
    @FXML
	private TextField appointmentIdField;
    @FXML
	private TextArea appointmentDescriptionField;
    @FXML
	private DatePicker appointmentStartDate;
    @FXML
	private ComboBox appointmentStartTime;
    @FXML
	private DatePicker appointmentEndDate;
    @FXML
	private ComboBox appointmentEndTime;
    @FXML
	private ComboBox appointmentContactCombo;
    @FXML
    private ComboBox appointmentCustomerCombo;
    @FXML
    private ComboBox appointmentUserCombo;
    @FXML
	private RadioButton appointmentWeekRadio;
    @FXML
	private RadioButton appointmentMonthRadio;
    @FXML
	private RadioButton appointmentAllRadio;
    @FXML
	private ToggleGroup appointmentViewRadioGroup;
    @FXML
    private TabPane tabBar;
    @FXML
    private Label loggedInAsLabel;
    //endregion

    private boolean isModifying = false;
    private Appointment selectedAppointment = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInAsLabel.setText("Logged in as [" + AppContext.getActiveUser().getName() + "]");
        appointmentSplitPane.setDividerPositions(1);
        BindAppointmentElements();
        PopulateTimeOptions();
    }

    private void PopulateTimeOptions() {
        ObservableList<String> time = FXCollections.observableArrayList();

        LocalTime start = LocalTime.MIN;
        LocalTime end = LocalTime.MAX;

        time.add(start.toString());
        while (start.isBefore(end)) {
            // break on overflow (assumes even increments)
            if (start.plusMinutes(30) == LocalTime.MIN)
                break;
            start = start.plusMinutes(30);
            time.add(start.toString());
        }

        appointmentStartTime.setItems(time);
        appointmentEndTime.setItems(time);
    }

    private void BindAppointmentElements() {
        AppointmentRepository repo = new AppointmentRepository();

        ObservableList<Appointment> appointments = AppContext.getData().getAppointments();
        ObservableList<Customer> customers = AppContext.getData().getCustomers();
        ObservableList<User> users = AppContext.getData().getUsers();
        ObservableList<Contact> contacts = AppContext.getData().getContacts();

        //region ComboBox setup
        appointmentContactCombo.setItems(contacts);
        appointmentCustomerCombo.setItems(customers);
        appointmentUserCombo.setItems(users);

        StringConverter<Named> converter = new StringConverter<Named>() {
            @Override
            public String toString(Named c) {
                if (c != null)
                    return c.getName();
                return "";
            }
            @Override
            public Named fromString(String s) {
                return null;
            }
        };
        appointmentContactCombo.setConverter(converter);
        appointmentCustomerCombo.setConverter(converter);
        appointmentUserCombo.setConverter(converter);
        //endregion

        //region Table setup
        // Bind table
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(p -> {
            String formattedDate = p.getValue().getStart().format(
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
            ObservableValue<String> cellValue = new SimpleStringProperty(formattedDate);
            return cellValue;
        });
        appointmentEndColumn.setCellValueFactory(p -> {
            String formattedDate = p.getValue().getEnd().format(
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
            ObservableValue<String> cellValue = new SimpleStringProperty(formattedDate);
            return cellValue;
        });
        appointmentContactColumn.setCellValueFactory(p -> {
            int contactId = p.getValue().getContactId();
            /*
             * LAMBDA_USAGE: inline predicate to filter the list of Divisions
             */
            List<Contact> result = new ArrayList<>();
            if (contacts.size() > 0)
                result = contacts.stream().filter(f -> contactId == f.getId()).toList();

            // Default to an empty string if not found
            String resultName = (result.size() > 0) ? result.iterator().next().getName() : "";

            ObservableValue<String> cellValue = new SimpleStringProperty(resultName);
            return cellValue;
        });

        appointmentCustomerColumn.setCellValueFactory(p -> {
            int customerId = p.getValue().getCustomerId();
            /*
             * LAMBDA_USAGE: inline predicate to filter the list of Divisions
             */
            List<Customer> result = new ArrayList<>();
            if (customers.size() > 0)
                result = customers.stream().filter(f -> customerId == f.getId()).toList();

            // Default to an empty string if not found
            String resultName = (result.size() > 0) ? result.iterator().next().getName() : "";

            ObservableValue<String> cellValue = new SimpleStringProperty(resultName);
            return cellValue;
        });

        appointmentUserIdColumn.setCellValueFactory(p -> {
            int userId = p.getValue().getUserId();
            /*
             * LAMBDA_USAGE: inline predicate to filter the list of Divisions
             */
            List<User> result = new ArrayList<>();
            if (users.size() > 0)
                result = users.stream().filter(f -> userId == f.getId()).toList();

            // Default to an empty string if not found
            String resultName = (result.size() > 0) ? result.iterator().next().getName() : "";

            ObservableValue<String> cellValue = new SimpleStringProperty(resultName);
            return cellValue;
        });

        appointmentTable.setItems(appointments);

        // Add a listener to the table to handle selection changes (to enable/disable modify&delete buttons)
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((value, oldSelection, newSelection) -> {
            boolean hasSelection = (newSelection != null);
            editAppointmentButton.setDisable(!hasSelection);
            removeAppointmentButton.setDisable(!hasSelection);
            selectedAppointment = newSelection;
        });
        //endregion
    }

    public void onLogOut(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToLoginScene();
        AppContext.setActiveUser(null);
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAppointmentRadioChange(ActionEvent actionEvent) {
        Toggle selectedToggle = appointmentViewRadioGroup.getSelectedToggle();
        if (selectedToggle.equals(appointmentAllRadio)) {
            appointmentTable.setItems(AppContext.getData().getAppointments());
        }
        // Get appointments in this month (not in the next rolling 30 days)
        if (selectedToggle.equals(appointmentMonthRadio)) {
            appointmentTable.setItems(
                    FXCollections.observableList(
                            AppContext.getData().getAppointments().stream()
                                    .filter(p -> p.getStart().getMonth() == LocalDateTime.now().getMonth()).toList()));
        }
        if (selectedToggle.equals(appointmentWeekRadio)) {
            appointmentTable.setItems(
                    FXCollections.observableList(
                            AppContext.getData().getAppointments().stream()
                                    .filter(p -> p.getStart().isBefore(LocalDateTime.now().plusWeeks(1)) &&
                                            p.getStart().isAfter(LocalDateTime.now())).toList()));
        }
    }

    public void onAddAppointment(ActionEvent actionEvent) {
        isModifying = false;
        addAppointmentButton.setDisable(true);
        editAppointmentButton.setDisable(false);
        addOrEditAppointmentLabel.setText("Create an Appointment");
        appointmentIdField.setText("Auto-generated");
        clearAppointmentForm();
        appointmentSplitPane.setDividerPositions(.5);
        appointmentTitleField.requestFocus();
    }

    private void clearAppointmentForm() {
        UiUtil.clear(appointmentTitleField, appointmentDescriptionField, appointmentTypeField, appointmentLocationField);
        UiUtil.clear(appointmentContactCombo, appointmentCustomerCombo, appointmentUserCombo, appointmentStartTime, appointmentEndTime);

        appointmentStartDate.setValue(LocalDateTime.now().toLocalDate());
        appointmentEndDate.setValue(LocalDateTime.now().toLocalDate());
    }

    public void onEditAppointment(ActionEvent actionEvent) {
        if (selectedAppointment == null)
            return;

        isModifying = true;
        addAppointmentButton.setDisable(false);
        editAppointmentButton.setDisable(true);
        addOrEditAppointmentLabel.setText("Modify Customer");
        appointmentIdField.setText("");
        appointmentSplitPane.setDividerPositions(.5);

        appointmentIdField.setText(String.valueOf(selectedAppointment.getId()));
        appointmentTitleField.setText(selectedAppointment.getTitle());
        appointmentDescriptionField.setText(selectedAppointment.getDescription());
        appointmentTypeField.setText(selectedAppointment.getType());
        appointmentLocationField.setText(selectedAppointment.getLocation());
        appointmentStartDate.setValue(selectedAppointment.getStart().toLocalDate());
        appointmentEndDate.setValue(selectedAppointment.getEnd().toLocalDate());
        appointmentStartTime.getEditor().setText(selectedAppointment.getStart().toLocalTime().toString());
        appointmentEndTime.getEditor().setText(selectedAppointment.getEnd().toLocalTime().toString());

        OptionalInt contactIndex = IntStream.range(0, appointmentContactCombo.getItems().size())
                .filter(i -> ((Contact) appointmentContactCombo.getItems().get(i)).getId() == selectedAppointment.getContactId()).findFirst();
        OptionalInt userIndex = IntStream.range(0, appointmentUserCombo.getItems().size())
                .filter(i -> ((User) appointmentUserCombo.getItems().get(i)).getId() == selectedAppointment.getUserId()).findFirst();
        OptionalInt customerIndex = IntStream.range(0, appointmentCustomerCombo.getItems().size())
                .filter(i -> ((Customer) appointmentCustomerCombo.getItems().get(i)).getId() == selectedAppointment.getCustomerId()).findFirst();

        if (contactIndex.isPresent()) {
            appointmentContactCombo.getSelectionModel().select(contactIndex.getAsInt());
        }
        if (userIndex.isPresent()) {
            appointmentUserCombo.getSelectionModel().select(userIndex.getAsInt());
        }
        if (customerIndex.isPresent()) {
            appointmentCustomerCombo.getSelectionModel().select(customerIndex.getAsInt());
        }

        appointmentTitleField.requestFocus();
    }

    public void onRemoveAppointment(ActionEvent actionEvent) {
        if (selectedAppointment == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm this deletion.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            String message = "Deleted: \n\tAppointment ID: [" + selectedAppointment.getId() + "]" + System.lineSeparator() + "\tType: [" +
                    selectedAppointment.getType() + "] " + System.lineSeparator();

            new AppointmentRepository().delete(selectedAppointment.getId());
            AppContext.getData().getAppointments().removeIf(p -> p.getId() == selectedAppointment.getId());

            new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
        }
    }

    public void onSaveChangesAppointment(ActionEvent actionEvent) {
        if (!validateAppointmentForm())
            return;

        AppointmentRepository repo = new AppointmentRepository();

        boolean successful = false;
        int index = -1;

        // Build a new customer object
        int id;
        try {
            id = Integer.parseInt(appointmentIdField.getText());
        } catch (NumberFormatException e ) {
            id = 0;
        }

        LocalDateTime startTime = LocalDateTime.of(appointmentStartDate.getValue(),
                LocalTime.parse((String) appointmentStartTime.getEditor().getText()));
        LocalDateTime endTime = LocalDateTime.of(appointmentEndDate.getValue(),
                LocalTime.parse((String) appointmentEndTime.getEditor().getText()));

        Customer tentativeCustomer = (Customer) appointmentCustomerCombo.getSelectionModel().getSelectedItem();

        if (!hasNoInvalidDurations(startTime, endTime))
            return;
        if (!hasNoEndAfterStart(startTime, endTime))
            return;
        if (!isWithinEstBusinessHours(startTime, endTime))
            return;
        if (!hasNoConflictingAppointments(tentativeCustomer, startTime, endTime))
            return;

        Appointment appointment = new Appointment(
                id,
                appointmentTitleField.getText(),
                appointmentDescriptionField.getText(),
                appointmentLocationField.getText(),
                appointmentTypeField.getText(),
                startTime,
                endTime,
                tentativeCustomer.getId(),
                ((User) appointmentUserCombo.getSelectionModel().getSelectedItem()).getId(),
                ((Contact) appointmentContactCombo.getSelectionModel().getSelectedItem()).getId()
        );

        // Create or Update depending on context
        if (isModifying) {
            var updated = repo.update(appointment);
            successful = updated;
            if (updated) {
                index = AppContext.getData().getAppointments().indexOf(selectedAppointment);
                AppContext.getData().getAppointments().removeIf(p -> p.getId() == appointment.getId());
            }
        } else {
            var resultId = repo.create(appointment);
            successful = resultId > 0;
            appointment.setId(resultId);
        }

        // Update local state to avoid DB calls
        if (successful) {
            var contextAppointments = AppContext.getData().getAppointments();

            if (index > -1) {
                // Replace existing entry retaining order for display
                contextAppointments.add(index, appointment);
            } else {
                contextAppointments.add(appointment);
            }
            appointmentTable.setItems(contextAppointments);

            // Optionally refresh from DB instead:
            //appointmentTable.setItems(repo.getAll());

            // Reset form
            onCancelChangesAppointment(actionEvent);
        }

    }

    /**
     * Test if an appointment of 0 minutes duration exists
     * @param startTime
     * @param endTime
     * @return
     */
    private boolean hasNoInvalidDurations(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime == startTime) {
            new Alert(Alert.AlertType.ERROR, "Cannot add an appointment with a duration of 0 minutes.").showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Test if an appointment has an end time preceding its start time
     * @param startTime
     * @param endTime
     * @return
     */
    private boolean hasNoEndAfterStart(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            new Alert(Alert.AlertType.ERROR, "End datetime cannot be before the Start datetime").showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Test if an appointment conflicts with an existing appointment attached to the customer
     * @param customer
     * @param tentativeStart
     * @param tentativeEnd
     * @return
     */
    private boolean hasNoConflictingAppointments(Customer customer, LocalDateTime tentativeStart, LocalDateTime tentativeEnd) {

        LocalDateTime newStartUtc = Time.toUtcLocalDateTime(tentativeStart);
        LocalDateTime newEndUtc = Time.toUtcLocalDateTime(tentativeEnd);

        // Fetch customer appointments
        var customerAppointments = new AppointmentRepository().getCustomerAppointments(customer.getId());

        // Check for collisions
        for (Appointment appointment : customerAppointments)
        {
            var startUtc = Time.toUtcLocalDateTime(appointment.getStart());
            var endUtc = Time.toUtcLocalDateTime(appointment.getEnd());

            /**
             * Cases:
             *  Start and end inside existing appointment window
             *  Start in window, end after window,
             *  Start after window, end inside
             *  Start before window and end after window
             */
            if ((newStartUtc.isAfter(startUtc) && newEndUtc.isBefore(endUtc)) ||
                    (newStartUtc.isBefore(startUtc) && newEndUtc.isAfter(startUtc)) ||
                    (newStartUtc.isAfter(startUtc) && newStartUtc.isBefore(endUtc) && newEndUtc.isAfter(endUtc)) ||
                    (newStartUtc.isBefore(startUtc) && newEndUtc.isAfter(endUtc)))
            {
                new Alert(Alert.AlertType.ERROR, "This customer has appointments that conflict with this schedule", null).showAndWait();
                return false;
            }
        }
        return true;
    }

    /**
     * Test if an appointment is set up during Eastern Time business hours (8:00AM-10:00PM ET)
     * @param startTime
     * @param endTime
     * @return
     */
    private boolean isWithinEstBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime zonedStartTime = Time.toEstLocalDateTime(startTime);
        LocalDateTime zonedEndTime = Time.toEstLocalDateTime(endTime);

        List<String> errors = new ArrayList<>();

        // Start time out of bounds
        if (zonedStartTime.getHour() < 8 || zonedStartTime.getHour() > 22 )
            errors.add("Start time is outside EST business hours.");
        // End time out of bounds
        if (zonedEndTime.getHour() < 8 || zonedEndTime.getHour() > 22)
            errors.add("End time is outside EST business hours.");

        if (errors.size() > 0) {
            new Alert(Alert.AlertType.ERROR, String.join("\n" ,errors), null).showAndWait();
            return false;
        }

        return true;
    }

    private boolean validateAppointmentForm() {
        if (appointmentContactCombo.getSelectionModel().isEmpty() || appointmentTypeField.getText() == "") {
            new Alert(Alert.AlertType.ERROR, "Missing data in form.").showAndWait();
            return false;
        }
        return true;
    }

    public void onCancelChangesAppointment(ActionEvent actionEvent) {
        clearAppointmentForm();
        selectedAppointment = null;
        appointmentTable.getSelectionModel().clearSelection();
        appointmentSplitPane.setDividerPositions(1);
        addAppointmentButton.setDisable(false);
        editAppointmentButton.setDisable(true);
    }

    public void navigateCustomers(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToCustomerScene();
    }

    public void navigateReports(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToReportScene();
    }
}