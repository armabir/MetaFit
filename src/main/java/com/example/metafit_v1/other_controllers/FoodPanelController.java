package com.example.metafit_v1.other_controllers;

import com.example.metafit_v1.models.Food;
import com.example.metafit_v1.user_controllers.MealEntryController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import com.example.metafit_v1.user_controllers.MealEntryController;

public class FoodPanelController {

    @FXML
    private Label nameLabel;

    private Food foodData;
    @FXML
    void addToList(MouseEvent event) {
        if (foodData != null) {
            // Get the global MealEntryController instance
            MealEntryController controller = MealEntryController.getInstance();
            if (controller != null) {
                controller.addFoodToSelectedList(foodData);
                System.out.println("Added: " + foodData.getName());
            } else {
                System.err.println("ERROR: MealEntryController instance not found!");
            }
        }
    }
    public void setData(Food food){
        this.foodData = food;
        nameLabel.setText(food.getName());
    }

}
