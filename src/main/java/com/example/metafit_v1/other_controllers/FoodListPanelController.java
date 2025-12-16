package com.example.metafit_v1.other_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FoodListPanelController {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField weightField;

    public void setData(String name){
        nameLabel.setText(name);
        weightField.setText("00");
    }

}
