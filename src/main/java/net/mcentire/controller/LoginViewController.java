package net.mcentire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginViewController {
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

    public void onSubmitAction(ActionEvent actionEvent) {
    }

    public void onClearAction(ActionEvent actionEvent) {
    }
}
