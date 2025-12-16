package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.MealPlan;
import com.example.metafit_v1.services.MealPlanGenerator;
import com.example.metafit_v1.services.MealPlanGeneratorWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AutomealController implements Initializable {

    @FXML
    private ToggleButton budgetButton;

    @FXML
    private AnchorPane budgetPane;

    @FXML
    private ToggleGroup categoryToggle;

    @FXML
    private ToggleButton proteinButton;

    @FXML
    private AnchorPane proteinPane;

    @FXML
    private StackPane toggleButtonStackPane;

    @FXML
    private ToggleButton weightGainButton;

    @FXML
    private ToggleButton weightLossButton;

    @FXML
    private AnchorPane weightPane;

    @FXML
    private TextField budgetField;

    @FXML
    private ChoiceBox<String> durationBox;

    @FXML
    private ListView<String> satFoodList;

    @FXML
    private ListView<String> satWeightList;
    @FXML
    private ListView<String> satMacroList;

    @FXML
    private ListView<String> satMacroWeightList;

    @FXML
    private ListView<String> friFoodList;

    @FXML
    private ListView<String> friMacroList;

    @FXML
    private ListView<String> friMacroWeightList;

    @FXML
    private ListView<String> friWeightList;

    @FXML
    private ListView<String> monFoodList;

    @FXML
    private ListView<String> monMacroList;

    @FXML
    private ListView<String> monMacroWeightList;

    @FXML
    private ListView<String> monWeightList;

    @FXML
    private TextField proteinField;

    @FXML
    private ListView<String> sunFoodList;

    @FXML
    private ListView<String> sunMacroList;

    @FXML
    private ListView<String> sunMacroWeightList;

    @FXML
    private ListView<String> sunWeightList;

    @FXML
    private ListView<String> thuFoodList;

    @FXML
    private ListView<String> thuMacroList;

    @FXML
    private ListView<String> thuMacroWeightList;

    @FXML
    private ListView<String> thuWeightList;

    @FXML
    private ListView<String> tueFoodList;

    @FXML
    private ListView<String> tueMacroList;

    @FXML
    private ListView<String> tueMacroWeightList;

    @FXML
    private ListView<String> tueWeightList;

    @FXML
    private ListView<String> wedFoodList;

    @FXML
    private ListView<String> wedMacroList;

    @FXML
    private ListView<String> wedMacroWeightList;

    @FXML
    private ListView<String> wedWeightList;

    @FXML
    private TextField weightField;

    @FXML
    void budgetEvent(ActionEvent event) {

    }
    @FXML
    void weightGainEvent(ActionEvent event) {

    }
    @FXML
    void weightLossEvent(ActionEvent event) {

    }
    @FXML
    void proteinEvent(ActionEvent event) {

    }

    @FXML
    void budgetCreateMeal(ActionEvent event) {

    }

    @FXML
    void proteinCreateMeal(ActionEvent event) {
        Double targetProtein = Double.valueOf(proteinField.getText());

        MealPlan mealPlan = MealPlanGenerator.generateMealPlan(targetProtein);

        ObservableList<String> satMealList = FXCollections.observableArrayList();
        satMealList.addAll(mealPlan.getSatFoodList());
        satFoodList.setItems(satMealList);
        tueFoodList.setItems(satMealList);
        friFoodList.setItems(satMealList);

        ObservableList<String> sunMealList = FXCollections.observableArrayList();
        sunMealList.addAll(mealPlan.getSunFoodList());
        sunFoodList.setItems(sunMealList);
        wedFoodList.setItems(sunMealList);

        ObservableList<String> monMealList = FXCollections.observableArrayList();
        monMealList.addAll(mealPlan.getMonFoodList());
        monFoodList.setItems(monMealList);
        thuFoodList.setItems(monMealList);

        ObservableList<String> satWList = FXCollections.observableArrayList();
        satWList.addAll(mealPlan.getSatWeightList());
        satWeightList.setItems(satWList);
        tueWeightList.setItems(satWList);
        friWeightList.setItems(satWList);

        ObservableList<String> sunWList = FXCollections.observableArrayList();
        sunWList.addAll(mealPlan.getSunWeightList());
        sunWeightList.setItems(sunWList);
        wedWeightList.setItems(sunWList);

        ObservableList<String> monWList = FXCollections.observableArrayList();
        monWList.addAll(mealPlan.getMonWeightList());
        monWeightList.setItems(monWList);
        thuWeightList.setItems(monWList);

        ObservableList<String> satMarco = FXCollections.observableArrayList();
        satMarco.addAll(mealPlan.getSatMarcoWeightList());
        satMacroWeightList.setItems(satMarco);
        tueMacroWeightList.setItems(satMarco);
        friMacroWeightList.setItems(satMarco);

        ObservableList<String> sunMarco = FXCollections.observableArrayList();
        sunMarco.addAll(mealPlan.getSunMarcoWeightList());
        sunMacroWeightList.setItems(sunMarco);
        wedMacroWeightList.setItems(sunMarco);

        ObservableList<String> monMarco = FXCollections.observableArrayList();
        monMarco.addAll(mealPlan.getMonMarcoWeightList());
        monMacroWeightList.setItems(monMarco);
        thuMacroWeightList.setItems(monMarco);
    }

    @FXML
    void weightCreateMeal(ActionEvent event) {
        double current = 65;
        double target = Double.parseDouble(weightField.getText());
        int duration = 0;
        switch (durationBox.getValue()){
            case "1 Month" : duration = 1;break;
            case "3 Months": duration = 3;break;
            case "6 Months": duration = 6;break;
            default: duration = 1;break;
        }

        MealPlan mealPlan = MealPlanGeneratorWeight.generateWeightGoalMealPlan(current, target, duration);

        ObservableList<String> satMealList = FXCollections.observableArrayList();
        satMealList.addAll(mealPlan.getSatFoodList());
        satFoodList.setItems(satMealList);
        tueFoodList.setItems(satMealList);
        friFoodList.setItems(satMealList);

        ObservableList<String> sunMealList = FXCollections.observableArrayList();
        sunMealList.addAll(mealPlan.getSunFoodList());
        sunFoodList.setItems(sunMealList);
        wedFoodList.setItems(sunMealList);

        ObservableList<String> monMealList = FXCollections.observableArrayList();
        monMealList.addAll(mealPlan.getMonFoodList());
        monFoodList.setItems(monMealList);
        thuFoodList.setItems(monMealList);

        ObservableList<String> satWList = FXCollections.observableArrayList();
        satWList.addAll(mealPlan.getSatWeightList());
        satWeightList.setItems(satWList);
        tueWeightList.setItems(satWList);
        friWeightList.setItems(satWList);

        ObservableList<String> sunWList = FXCollections.observableArrayList();
        sunWList.addAll(mealPlan.getSunWeightList());
        sunWeightList.setItems(sunWList);
        wedWeightList.setItems(sunWList);

        ObservableList<String> monWList = FXCollections.observableArrayList();
        monWList.addAll(mealPlan.getMonWeightList());
        monWeightList.setItems(monWList);
        thuWeightList.setItems(monWList);

        ObservableList<String> satMarco = FXCollections.observableArrayList();
        satMarco.addAll(mealPlan.getSatMarcoWeightList());
        satMacroWeightList.setItems(satMarco);
        tueMacroWeightList.setItems(satMarco);
        friMacroWeightList.setItems(satMarco);

        ObservableList<String> sunMarco = FXCollections.observableArrayList();
        sunMarco.addAll(mealPlan.getSunMarcoWeightList());
        sunMacroWeightList.setItems(sunMarco);
        wedMacroWeightList.setItems(sunMarco);

        ObservableList<String> monMarco = FXCollections.observableArrayList();
        monMarco.addAll(mealPlan.getMonMarcoWeightList());
        monMacroWeightList.setItems(monMarco);
        thuMacroWeightList.setItems(monMarco);
    }

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboard");
    }
    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("userProfile");
    }
    @FXML
    void gotoWorkout(MouseEvent event) {
        HelloApplication.changeScene("workout");
    }
    @FXML
    void gotoMealEntry(MouseEvent event) {
        HelloApplication.changeScene("mealEntry");
    }


    public ObservableList<String> macroList = FXCollections.observableArrayList();
    public ObservableList<String> durationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        macroList.addAll("Protein", "Carbs", "Fat", "Fiber", "Sugar", "", "Total Cal");
        satMacroList.setItems(macroList);
        sunMacroList.setItems(macroList);
        monMacroList.setItems(macroList);
        tueMacroList.setItems(macroList);
        wedMacroList.setItems(macroList);
        thuMacroList.setItems(macroList);
        friMacroList.setItems(macroList);

        durationList.addAll("1 Month", "3 Months", "6 Months");
        durationBox.setItems(durationList);


        showPane(weightPane, weightLossButton);
        categoryToggle.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == weightLossButton) {
                showPane(weightPane, weightLossButton);
            } else if (newToggle == weightGainButton) {
                showPane(weightPane, weightGainButton);
            } else if (newToggle == budgetButton) {
                showPane(budgetPane, budgetButton);
            } else if (newToggle == proteinButton) {
                showPane(proteinPane, proteinButton);
            }
        });
    }
    private void showPane(AnchorPane paneToShow, ToggleButton button) {
        // Hide all panes
        weightPane.setVisible(false);
        weightPane.setManaged(false);
        weightGainButton.setStyle("-fx-background-color: #465149; -fx-text-fill: white;");
        weightLossButton.setStyle("-fx-background-color: #465149; -fx-text-fill: white;");

        budgetPane.setVisible(false);
        budgetPane.setManaged(false);
        budgetButton.setStyle("-fx-background-color: #465149; -fx-text-fill: white;");

        proteinPane.setVisible(false);
        proteinPane.setManaged(false);
        proteinButton.setStyle("-fx-background-color: #465149; -fx-text-fill: white;");

        // Show the selected one
        paneToShow.setVisible(true);
        paneToShow.setManaged(true);
        button.setStyle("-fx-background-color: #85cc17; -fx-text-fill: white;");
    }





}
