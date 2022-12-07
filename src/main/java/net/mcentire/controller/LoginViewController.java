package net.mcentire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.mcentire.model.User;
import net.mcentire.repository.UserRepository;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginViewController extends BaseController {
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

        // Do nothing if credentials provided were bad
        if (authenticatedUser == null)
            return;

        // Update user context
        context.setActiveUser(authenticatedUser);

        // Check if any relevant meetings are occurring soon
    }

    /**
     * Handle clearing the login form
     * @param actionEvent
     */
    public void onClearAction(ActionEvent actionEvent) {
        userField.setText("");
        passwordField.setText("");
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
        loginSplash.setImage(new Image(imagePath.toString()));
        localeLabel.setText("Locale: " + resourceBundle.getString("locale"));
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
        Locale newLocale = (Locale.getDefault().getLanguage() == "fr") ?
                new Locale("en", "US") : new Locale("fr", "FR");

        Locale.setDefault(newLocale);
        context.setResourceBundle(
                ResourceBundle.getBundle("localization/localization", Locale.getDefault())
        );
        resourceBundle = context.getResourceBundle();
        UpdateLocalization(actionEvent);
    }
}
