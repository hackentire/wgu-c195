package net.mcentire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import net.mcentire.app.AppContext;
import net.mcentire.model.Appointment;
import net.mcentire.model.User;
import net.mcentire.repository.*;
import net.mcentire.util.Logger;
import net.mcentire.util.Time;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginViewController extends BaseController {

    @FXML
    private Label zoneLabel;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private ImageView loginSplash;
    @FXML
    private Label localeLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button clearButton;

    /**
     * Handle a login attempt
     * @param actionEvent
     */
    public void onSubmitAction(ActionEvent actionEvent) {
        User authenticatedUser = UserRepository.login(userField.getText(), passwordField.getText());

        // Clear password field and log attempt on failure
        if (authenticatedUser == null) {
            UiUtil.clear(passwordField);
            Logger.log(userField.getText(), false);

            Alert alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("failedLogin"));
            alert.showAndWait();
            return;
        }

        // Log successful attempt
        Logger.log(authenticatedUser.getName(), true);

        // Update user context
        AppContext.setActiveUser(authenticatedUser);

        // Fetch initial data for forms
        var contextData = AppContext.getData();
        contextData.setCustomers(new CustomerRepository().getAll());
        contextData.setAppointments(new AppointmentRepository().getAll());
        contextData.setCountries(new CountryRepository().getAll());
        contextData.setDivisions(new DivisionRepository().getAll());
        contextData.setContacts(new ContactRepository().getAll());
        contextData.setUsers(new UserRepository().getAll());

        new SceneLoader(actionEvent).ChangeToAppointmentScene();

        // Check if any relevant meetings are occurring soon
        checkUserAppointments();
    }

    private void checkUserAppointments() {
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeIn15Minutes = timeNow.plusMinutes(15);

        var appointments = AppContext.getData().getAppointments().stream()
                .filter(p -> {
                    if (p.getUserId() == AppContext.getActiveUser().getId() &&
                            p.getStart().isBefore(timeIn15Minutes) && p.getStart().isAfter(timeNow)
                    ) {
                        return true;
                    }
                    return false;
                })
                .toList();

        // Display alert for each upcoming appointment in the next 15 minutes.
        for (Appointment appointment : appointments) {
            new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment:\n\tAppointment ID: " + appointment.getId() + "\n\t" +
                    "Date: " + appointment.getStart().toLocalDate() + "\n\t" +
                    "Time: " + appointment.getStart().toLocalTime()
            ).showAndWait();
        }
    }

    /**
     * Handle clearing the login form
     * @param actionEvent
     */
    public void onClearAction(ActionEvent actionEvent) {
        UiUtil.clear(userField, passwordField);
        // Refocus username field
        userField.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle res) {
        UpdateLocalization(null);
    }

    /**
     * Update the UI elements for the current Locale
     * @param actionEvent
     */
    @FXML
    private void UpdateLocalization(ActionEvent actionEvent) {
        URL imagePath = getClass().getResource("/net/mcentire/view/" + resourceBundle.getString("splashSrc"));
        assert imagePath != null;
        loginSplash.setImage(new Image(imagePath.toString()));
        localeLabel.setText("Locale: " + Locale.getDefault());
        zoneLabel.setText("Zone: " + ZoneId.systemDefault());
        loginLabel.setText(resourceBundle.getString("logIn"));
        userField.setPromptText(resourceBundle.getString("username"));
        passwordField.setPromptText(resourceBundle.getString("password"));
        loginButton.setText(resourceBundle.getString("submit"));
        clearButton.setText(resourceBundle.getString("clear"));
        toggleButton.setText(resourceBundle.getString("toggle"));
        if (actionEvent != null)
            SceneLoader.ChangeStageTitle(actionEvent, resourceBundle.getString("welcome"));
    }

    /**
     * Handle toggle button click
     * @param actionEvent
     */
    public void toggleLanguage(ActionEvent actionEvent) {
        Locale newLocale = (Objects.equals(Locale.getDefault().getLanguage(), "fr")) ?
                new Locale("en", "US") : new Locale("fr", "FR");

        Locale.setDefault(newLocale);
        AppContext.setResourceBundle(
                ResourceBundle.getBundle("localization/localization", Locale.getDefault())
        );
        resourceBundle = AppContext.getResourceBundle();
        UpdateLocalization(actionEvent);
    }

    /**
     * Allow form submission with the Enter key
     * @param keyEvent
     */
    public void onPasswordKeySubmit(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equals("\r") )
        {
            onSubmitAction(new ActionEvent(passwordField, null));
        }
    }
}
