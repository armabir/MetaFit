package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class DashboardTrainerController {

    @FXML
    private Label caloriLabel;

    @FXML
    private Label caloriParcentLabel;

    @FXML
    private Label carbsAmountLabel;

    @FXML
    private Label carbsWeightLabel;

    @FXML
    private Label fatAmountLabel;

    @FXML
    private Label fatWeightLabel;

    @FXML
    private Label fiberAmountLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label heightLabel1;

    @FXML
    private Label mealCaloriLabel;

    @FXML
    private Label mealNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel11;

    @FXML
    private Label nameLabel111;

    @FXML
    private Label nameLabel1111;

    @FXML
    private Label nameLabel11111;

    @FXML
    private Label nameLabel111111;

    @FXML
    private Label nameLabel112;

    @FXML
    private Label nameLabel1121;

    @FXML
    private Label nameLabel12;

    @FXML
    private Label nameLabel121;

    @FXML
    private Label proteinAmountLabel;

    @FXML
    private Label proteinWeightLabel;

    @FXML
    private Label sugarAmountLabel;

    @FXML
    private Label targetLabel;

    @FXML
    private Label targetLabel1;

    @FXML
    private Label targetLabel2;

    @FXML
    private Label targetLabel21;

    @FXML
    private Label targetLabel211;

    @FXML
    private Label targetLabel2111;

    @FXML
    private Label targetLabel22;

    @FXML
    private Label targetLabel221;

    @FXML
    private Label tdeeLabel;

    @FXML
    private Label totalCaloriLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userNameLabel1;

    @FXML
    private Label waterAmountLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    void gotoExerciseTrainer(ActionEvent event) {
        HelloApplication.changeScene("workoutTrainer");
    }

    @FXML
    void gotoMealTrainer(ActionEvent event) {
        HelloApplication.changeScene("mealTrainer");
    }

    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("trainerProfile");
    }

    @FXML
    void gotoUser(MouseEvent event) {
        HelloApplication.changeScene("dashboard");
    }

}
