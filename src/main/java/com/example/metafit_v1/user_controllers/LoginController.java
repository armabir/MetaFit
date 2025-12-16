package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.User;
import com.example.metafit_v1.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    public static User loggedUser = null;
    @FXML
    private TextField usernameEmailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button loginBtn;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
        errorLabel.setText("");
        infoLabel.setText("");
    }

    @FXML
    private void handleLogin() {
        // Clear previous messages
        errorLabel.setText("");
        infoLabel.setText("");

        // Validate inputs
        String usernameOrEmail = usernameEmailField.getText().trim();
        String password = passwordField.getText();

        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username/Email and password cannot be empty!");
            return;
        }

        // Fetch user from database
        if (usernameOrEmail.contains("@")) {
            loggedUser = userService.getUserByEmail(usernameOrEmail);
        } else {
            loggedUser = userService.getUserByUsername(usernameOrEmail);
        }

        // Check if user exists
        if (loggedUser == null) {
            errorLabel.setText("User not found!");
            return;
        }

        // Verify password (for now, plain text comparison - should use BCrypt)
        if (!loggedUser.getPassword().equals(password)) {
            errorLabel.setText("Incorrect password!");
            return;
        }

        // Login successful
        infoLabel.setText("Login successful! Redirecting...");

        // Redirect to dashboard based on role
        HelloApplication.changeScene("dashboard");
    }

    @FXML
    private void handleSignUp() {
        HelloApplication.changeScene("signUp");
    }

}