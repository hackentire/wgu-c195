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
import net.mcentire.repository.ContactRepository;
import net.mcentire.repository.CustomerRepository;
import net.mcentire.util.Time;

import java.net.URL;
import java.time.Duration;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;
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
//        loggedInAsLabel.setText("Logged in as [" + AppContext.getActiveUser().getName() + "]");
    }

    /**
     * Handle user sign off
     * @param actionEvent
     */
    public void onLogOut(ActionEvent actionEvent) {
        AppContext.setActiveUser(null);
        new SceneLoader(actionEvent).ChangeToLoginScene();
    }

    /**
     * Handle a user exitting the application
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Navigate to the Appointments scene
     * @param actionEvent
     */
    public void navigateAppointments(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToAppointmentScene();
    }

    /**
     * Navigate to the Customers scene
     * @param actionEvent
     */
    public void navigateCustomers(ActionEvent actionEvent) {
        new SceneLoader(actionEvent).ChangeToCustomerScene();
    }

    /**
     * Loads report: Total # of Customers by Type and Month
     * Makes use of lambda predicates to group an Appointment stream by their starting months, and iterating on the resultant
     * Map, and grouping the Map value (a list of appointments) by Appointment type while building a report in a StringBuilder.
     * @param actionEvent
     */
    public void loadReport1(ActionEvent actionEvent) {
        /*
        January:
            TypeA: 1
            TypeB: 4
        February:
            TypeA: 2
        ...
         */
        var appointments = new AppointmentRepository().getAll();
        var stringBuilder = new StringBuilder();

        stringBuilder.append(
                  "[REPORT]:\n The number of appointments of each type occurring in each month, sorted by month.\n\n".toUpperCase()
        );

        // FUNCTIONAL_INTERFACE: set up a custom Comparator to sort the months in this Map
        var monthComparator = new Comparator<Map.Entry<Month, List<Appointment>>>() {
            @Override
            public int compare(Map.Entry<Month, List<Appointment>> o1, Map.Entry<Month, List<Appointment>> o2) {
                if (o1.getKey().ordinal() < o2.getKey().ordinal()) {
                    return -1;
                }
                if (o1.getKey().ordinal() > o2.getKey().ordinal()) {
                    return 1;
                }
                return 0;
            }
        };

        // LAMBDA_USAGE:
        //  Anonymous function used to iterate over a map and build the report
        appointments.stream()
                .collect(Collectors.groupingBy(p -> p.getStart().getMonth()))
                .entrySet()
                .stream()
                .sorted(monthComparator)
                .forEach(p -> {
                    stringBuilder.append(p.getKey().toString() + ":\n"); // Month
                    var typeMap = p.getValue()
                            .stream()
                            .collect(Collectors.groupingBy(g -> g.getType()));
                    for (var thing : typeMap.entrySet()) {
                        stringBuilder.append(" └─ " + thing.getKey() + ": " + thing.getValue().size() + "\n");
                    }
                    stringBuilder.append("\n");
                });

        stringBuilder.append("\nGENERATED: " + Time.getCurrentUtcTime().format(DateTimeFormatter.ISO_DATE_TIME) + " UTC");
        dataField.setText(stringBuilder.toString());
    }

    /**
     * Loads report: Schedule for each Contact with: AppointmentID, Title, Type, Description, Start, End, and CustomerID
     * Makes use of lambda Comparator (FunctionalInterface) as a method to order appointments by starting DateTimes.
     * The chronologically sorted Appointment list is then iterated on the build the report.
     * @param actionEvent
     */
    public void loadReport2(ActionEvent actionEvent) {

        // FUNCTIONAL_INTERFACE: as Lambda: set up a custom Comparator to sort the appointments on the timeline.
        var contacts = new ContactRepository().getAll();
        var appointments = new AppointmentRepository().getAll().stream().sorted((o1, o2) -> {
            if (o1.getStart().isBefore(o2.getStart())) {
                return -1;
            }
            if (o1.getStart().isAfter(o2.getStart())) {
                return 1;
            }
            return 0;
        }).toList();

        var stringBuilder = new StringBuilder();
        stringBuilder.append(
                ("[REPORT]:\n Details of appointments associated with each contact in chronological order. " +
                        "Dates and times displayed in Local Time: " + ZoneId.systemDefault() + "\n\n").toUpperCase()
        );

        var formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

        for (Contact contact : contacts) {
            stringBuilder.append("ID: " + contact.getId() + " - " + contact.getName() + ":\n");
            for (Appointment appointment : appointments) {
                if (appointment.getContactId() == contact.getId()) {
                    stringBuilder.append(
                            "   APPOINTMENT ID: " + appointment.getId() +
                                    " | Title: " + appointment.getTitle() +
                                    " | Type: " + appointment.getType() +
                                    " | CustomerID: " + appointment.getCustomerId() + "\n" +
                            "   FROM: [" + appointment.getStart().format(formatter) + "] TO: [" + appointment.getEnd().format(formatter) + "]\n" +
                            "   DESCRIPTION: " + appointment.getDescription() + "\n\n"
                    );
                }
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append("GENERATED: " + Time.getCurrentUtcTime().format(DateTimeFormatter.ISO_DATE_TIME) + " UTC");
        dataField.setText(stringBuilder.toString());
    }

    /**
     * Loads report: Custom report of the total # of hours allotted to each customer
     * @param actionEvent
     */
    public void loadReport3(ActionEvent actionEvent) {
        var appointments = new AppointmentRepository().getAll();
        var customers = new CustomerRepository().getAll();

        var stringBuilder = new StringBuilder();
        stringBuilder.append(
                "[REPORT]:\n The total number of hours scheduled for each Customer.\n\n".toUpperCase()
        );

        for (Customer customer : customers) {
            stringBuilder.append("ID: " + customer.getId() + " - " + customer.getName() + ":\n");
            Duration total = Duration.ZERO;
            int count = 0;
            for (Appointment appointment : appointments) {
                if (appointment.getCustomerId() == customer.getId()) {
                    total = total.plus(Duration.between(appointment.getStart(), appointment.getEnd()));
                    ++count;
                }
            }
            Duration average = Duration.ZERO;
            if (count > 0) {
                average = total.dividedBy(count);
            }
            stringBuilder.append("  TOTAL SCHEDULED TIME: " + formatDuration(total) + "\n" +
                    "  TOTAL COUNT: " + count + "\n" +
                    "  AVERAGE APPOINTMENT DURATION: " + formatDuration(average) + "\n\n");
        }


        stringBuilder.append("\nGENERATED: " + Time.getCurrentUtcTime().format(DateTimeFormatter.ISO_DATE_TIME) + " UTC");
        dataField.setText(stringBuilder.toString());
    }

    /**
     * Formats the duration in a H:MM:SS format (uncapped decimal hours i.e. 73:10:12)
     * @param duration duration to be formatted.
     * @return
     */
    // https://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss
    private static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}