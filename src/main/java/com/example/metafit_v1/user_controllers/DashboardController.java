package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.Nutrition;
import com.example.metafit_v1.services.UserDashboardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label bmrLabel;

    @FXML
    private Label caloriLabel;

    @FXML
    private Label caloriParcentLabel;

    @FXML
    private Label carbsAmountLabel;

    @FXML
    private Pane carbsRectangle;

    @FXML
    private Label carbsWeightLabel;

    @FXML
    private Label eatLabel;

    @FXML
    private Label fatAmountLabel;

    @FXML
    private Pane fatRectangle;

    @FXML
    private Label fatWeightLabel;

    @FXML
    private Label fiberAmountLabel;

    @FXML
    private Pane fiberRectangle;

    @FXML
    private Label heightLabel;

    @FXML
    private Label mealCaloriLabel;

    @FXML
    private Label mealNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label neatLabel;

    @FXML
    private Label proteinAmountLabel;

    @FXML
    private Pane proteinRectangle;

    @FXML
    private Label proteinWeightLabel;

    @FXML
    private Label sugarAmountLabel;

    @FXML
    private Pane sugarRectangle;

    @FXML
    private Label targetLabel;

    @FXML
    private Label tdeeLabel;

    @FXML
    private Label tefLabel;

    @FXML
    private Label totalCaloriLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label waterAmountLabel;

    @FXML
    private Pane waterRectangle;
    @FXML
    private Pane calorieRectangle;

    @FXML
    private Label weightLabel;
    @FXML
    private ListView<String> lunchFoodList;
    @FXML
    private ListView<String> lunchWeightList;

    @FXML
    private Button saturdayButton;
    @FXML
    private Button sundayButton;
    @FXML
    private Button mondayButton;
    @FXML
    private Button tuesdayButton;
    @FXML
    private Button wednessdayButton;
    @FXML
    private Button thursdayButton;
    @FXML
    private Button fridayButton;

    @FXML
    private ListView<String> foodList1;

    @FXML
    private ListView<String> foodList2;

    @FXML
    private ListView<String> foodList3;

    @FXML
    private ListView<String> foodList4;

    @FXML
    private ListView<String> foodWeightList1;

    @FXML
    private ListView<String> foodWeightList2;

    @FXML
    private ListView<String> foodWeightList3;

    @FXML
    private ListView<String> foodWeightList4;

    @FXML
    private LineChart<String, Number> weightLineChart;

    public static UserDashboardService service = new UserDashboardService();

    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("userProfile");
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
    @FXML
    void gotoTrainer(MouseEvent event) {
        HelloApplication.changeScene("dashboardTrainer");
    }


    @FXML
    void satEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "SATURDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 40);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 40.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));

    }

    @FXML
    void sunEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "SUNDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }
    @FXML
    void monEvent(ActionEvent event) {
        currentDate();
    }
    @FXML
    void tuesEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "TUESDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }
    @FXML
    void wedEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "WEDNESDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }
    @FXML
    void thuEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "THURSDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }
    @FXML
    void friEvent(ActionEvent event) {
        Nutrition nutrition = service.getNutrition(1, "FRIDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }

    public void currentDate(){
        Nutrition nutrition = service.getNutrition(1, "MONDAY");

        service.setRectanglePane(proteinRectangle, nutrition.getProtein(), 150);
        service.setRectanglePane(fatRectangle, nutrition.getFat(), 30);
        service.setRectanglePane(carbsRectangle, nutrition.getCarbs(), 450);
        service.setRectanglePane(waterRectangle, nutrition.getWater(), 4);
        service.setRectanglePane(sugarRectangle, nutrition.getSugar(), 30);
        service.setRectanglePane(fiberRectangle, nutrition.getFiber(), 30);
        service.setRectanglePane(calorieRectangle, nutrition.getCalorie(), 2300);

        proteinAmountLabel.setText(nutrition.getProtein() + " of " + 150.0);
        carbsAmountLabel.setText(nutrition.getCarbs() + " of " + 450.0);
        fatAmountLabel.setText(nutrition.getFat() + " of " + 30.0);
        waterAmountLabel.setText(nutrition.getWater() + " of " + 4.0);
        sugarAmountLabel.setText(nutrition.getSugar() + " of " + 30.0);
        fiberAmountLabel.setText(nutrition.getFiber() + " of " + 30.);
        totalCaloriLabel.setText("of 2300");
        caloriLabel.setText(String.valueOf(nutrition.getCalorie()));

        proteinWeightLabel.setText(String.valueOf(nutrition.getProtein()));
        carbsWeightLabel.setText(String.valueOf(nutrition.getCarbs()));
        fatWeightLabel.setText(String.valueOf(nutrition.getFat()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.lineChart(weightLineChart);

        ObservableList<String> foodList = FXCollections.observableArrayList();
        ObservableList<String> foodWeightList = FXCollections.observableArrayList();
        foodList.add("Chicken");
        foodList.add("Rice");
        foodList.add("Dal");
        foodWeightList.add("120g");
        foodWeightList.add("350g");
        foodWeightList.add("50g");
        foodList1.setItems(foodList);
        foodWeightList1.setItems(foodWeightList);

        ObservableList<String> fList2 = FXCollections.observableArrayList();
        ObservableList<String> fWeightList2 = FXCollections.observableArrayList();
        fList2.add("Bread");
        fList2.add("Fish");
        fList2.add("Egg");
        fList2.add("Apple");
        fList2.add("Banana");
        fWeightList2.add("100g");
        fWeightList2.add("150g");
        fWeightList2.add("50g");
        fWeightList2.add("110g");
        fWeightList2.add("40g");
        foodList2.setItems(fList2);
        foodWeightList2.setItems(fWeightList2);


        weightLabel.setText(String.valueOf(LoginController.loggedUser.getWeight()));
        heightLabel.setText(String.valueOf(LoginController.loggedUser.getHeight()));
        targetLabel.setText(String.valueOf(LoginController.loggedUser.getTarget()));

        currentDate();
    }
}
