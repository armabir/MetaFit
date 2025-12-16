package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TrainerProfileController {

    @FXML
    private Label userNameLabel;

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboardTrainer");
    }

}
