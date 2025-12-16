package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.User;
import com.example.metafit_v1.services.UserService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class SignUpController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private ComboBox<String> genderCombo;
    @FXML
    private TextField weightField;
    @FXML
    private TextField heightField;
    @FXML
    private ComboBox<String> targetCombo;
    @FXML
    private ComboBox<String> activityLevelCombo;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button backBtn;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();

        // Populate ComboBoxes
        genderCombo.getItems().addAll("Male", "Female", "Other");
        targetCombo.getItems().addAll("Weight Loss", "Muscle Gain", "Maintenance");
        activityLevelCombo.getItems().addAll("Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extremely Active");
    }

    @FXML
    private void handleSignUp() {
        // Clear previous messages
        errorLabel.setText("");
        successLabel.setText("");

        // Validate inputs
        String validationError = validateInputs();
        if (validationError != null) {
            errorLabel.setText(validationError);
            return;
        }

        // Check if username exists
        if (userService.usernameExists(usernameField.getText())) {
            errorLabel.setText("Username already taken!");
            return;
        }

        // Check if email exists
        if (userService.emailExists(emailField.getText())) {
            errorLabel.setText("Email already registered!");
            return;
        }

        // Create user object
        User newUser = new User(
                usernameField.getText(),
                nameField.getText(),
                emailField.getText(),
                passwordField.getText(), // Hash before passing in production
                null, // imageUrl (optional for now)
                birthdayPicker.getValue(),
                genderCombo.getValue(),
                Double.parseDouble(weightField.getText()),
                Double.parseDouble(heightField.getText()),
                targetCombo.getValue(),
                activityLevelCombo.getValue()
        );

        // Insert into database
        if (userService.createUser(newUser)) {
            successLabel.setText("Account created successfully!");
        } else {
            errorLabel.setText("Failed to create account. Please try again!");
        }
    }

    @FXML
    private void handleBackToLogin() {
        HelloApplication.changeScene("login");
    }

    private String validateInputs() {
        if (usernameField.getText().trim().isEmpty()) {
            return "Username cannot be empty!";
        }
        if (nameField.getText().trim().isEmpty()) {
            return "Full name cannot be empty!";
        }
        if (emailField.getText().trim().isEmpty() || !emailField.getText().contains("@")) {
            return "Please enter a valid email!";
        }
        if (passwordField.getText().isEmpty()) {
            return "Password cannot be empty!";
        }
        if (passwordField.getText().length() < 6) {
            return "Password must be at least 6 characters!";
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            return "Passwords do not match!";
        }
        if (birthdayPicker.getValue() == null) {
            return "Please select your birthday!";
        }
        if (genderCombo.getValue() == null) {
            return "Please select your gender!";
        }
        if (weightField.getText().trim().isEmpty()) {
            return "Weight cannot be empty!";
        }
        if (heightField.getText().trim().isEmpty()) {
            return "Height cannot be empty!";
        }
        try {
            Double.parseDouble(weightField.getText());
            Double.parseDouble(heightField.getText());
        } catch (NumberFormatException e) {
            return "Weight and height must be valid numbers!";
        }
        if (targetCombo.getValue() == null) {
            return "Please select your goal!";
        }
        if (activityLevelCombo.getValue() == null) {
            return "Please select your activity level!";
        }
        return null; // All validations passed
    }
}