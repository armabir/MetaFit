package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class UserProfileController {

    @FXML
    private Label bmrLabel;

    @FXML
    private Label eatLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel11;

    @FXML
    private Label neatLabel;

    @FXML
    private Label tefLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userNameLabel1;

    @FXML
    private Label userNameLabel11;

    @FXML
    private Label userNameLabel111;

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboard");
    }

    @FXML
    void gotoWorkout(MouseEvent event) {
        HelloApplication.changeScene("workout");
    }
    @FXML
    void gotoAutomeal(MouseEvent event) {
        HelloApplication.changeScene("automeal");
    }
    @FXML
    void gotoMealEntry(MouseEvent event) {
        HelloApplication.changeScene("mealEntry");
    }

}
