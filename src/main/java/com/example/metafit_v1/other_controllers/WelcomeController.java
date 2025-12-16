package com.example.metafit_v1.other_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private Button userBtn;
    @FXML
    private Button trainerBtn;

    private VBox userCard;
    private VBox trainerCard;

    @FXML
    public void initialize() {
        // Get the parent VBox containers for the cards (they contain the buttons)
        // We'll reference them through the buttons
    }

    @FXML
    private void handleUserLogin() {
        HelloApplication.changeScene("login");
    }

    @FXML
    private void handleTrainerLogin() {
        HelloApplication.changeScene("loginTrainer");
    }

    @FXML
    private void handleUserButtonHover() {
        userBtn.getParent().setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 30; -fx-background-radius: 10; -fx-cursor: hand;");
    }

    @FXML
    private void handleUserButtonExit() {
        userBtn.getParent().setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 30; -fx-background-radius: 10; -fx-cursor: hand;");
    }

    @FXML
    private void handleTrainerButtonHover() {
        trainerBtn.getParent().setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 30; -fx-background-radius: 10; -fx-cursor: hand;");
    }

    @FXML
    private void handleTrainerButtonExit() {
        trainerBtn.getParent().setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 30; -fx-background-radius: 10; -fx-cursor: hand;");
    }
}