package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.Trainer;
import com.example.metafit_v1.services.TrainerService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpTrainerController {

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
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button backBtn;

    private TrainerService trainerService;

    @FXML
    public void initialize() {
        trainerService = new TrainerService();
        errorLabel.setText("");
        successLabel.setText("");
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
        if (trainerService.usernameExists(usernameField.getText())) {
            errorLabel.setText("Username already taken!");
            return;
        }

        // Check if email exists
        if (trainerService.emailExists(emailField.getText())) {
            errorLabel.setText("Email already registered!");
            return;
        }

        // Create trainer object
        Trainer newTrainer = new Trainer(
                usernameField.getText(),
                nameField.getText(),
                emailField.getText(),
                passwordField.getText() // Hash before passing in production
        );

        // Insert into database
        if (trainerService.createTrainer(newTrainer)) {
            successLabel.setText("Trainer account created successfully! Redirecting to login...");
            // Redirect after 2 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> handleBackToLogin());
            pause.play();
        } else {
            errorLabel.setText("Failed to create account. Please try again!");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            HelloApplication.changeScene("loginTrainer");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error navigating to login!");
        }
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
        return null; // All validations passed
    }
}