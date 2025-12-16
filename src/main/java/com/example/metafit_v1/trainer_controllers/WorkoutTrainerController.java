package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WorkoutTrainerController {

    @FXML
    private TextField testField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label welcomeLabel1;

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboardTrainer");
    }

    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("trainerProfile");
    }

}
