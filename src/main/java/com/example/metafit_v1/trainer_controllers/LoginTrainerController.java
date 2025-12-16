package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.Trainer;
import com.example.metafit_v1.services.TrainerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginTrainerController {
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

    private TrainerService trainerService;

    @FXML
    public void initialize() {
        trainerService = new TrainerService();
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

        // Fetch trainer from database
        Trainer trainer = null;
        if (usernameOrEmail.contains("@")) {
            trainer = trainerService.getTrainerByEmail(usernameOrEmail);
        } else {
            trainer = trainerService.getTrainerByUsername(usernameOrEmail);
        }

        // Check if trainer exists
        if (trainer == null) {
            errorLabel.setText("Trainer not found!");
            return;
        }

        // Verify password (for now, plain text comparison - should use BCrypt)
        if (!trainer.getPassword().equals(password)) {
            errorLabel.setText("Incorrect password!");
            return;
        }

        // Login successful
        infoLabel.setText("Login successful! Redirecting to trainer dashboard...");

        // Store trainer in session
        //TrainerSession.setCurrentTrainer(trainer);

        HelloApplication.changeScene("dashboardTrainer");
    }

    @FXML
    private void handleSignUp() {
        HelloApplication.changeScene("signUpTrainer");
    }

    @FXML
    private void handleBackToUserLogin() {
        HelloApplication.changeScene("login");
    }
}
