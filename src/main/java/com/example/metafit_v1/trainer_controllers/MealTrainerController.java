package com.example.metafit_v1.trainer_controllers;

import com.example.metafit_v1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class MealTrainerController {

    @FXML
    private ListView<?> satFoodList;

    @FXML
    private ListView<?> satMacroList;

    @FXML
    private ListView<?> satMacroWeightList;

    @FXML
    private ListView<?> satWeightList;

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboardTrainer");
    }

    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("trainerProfile");
    }

}
