package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.Food;
import com.example.metafit_v1.services.MealEntryService;
import com.example.metafit_v1.services.MealLoggingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MealEntryController implements Initializable {

    @FXML
    private VBox foodListVbox;

    @FXML
    private VBox foodVbox;

    @FXML
    private Label calorieLabel;

    @FXML
    private Label carbsLabel;

    @FXML
    private Label carbsWeight;

    @FXML
    private Label fatLabel;

    @FXML
    private Label fatWeight;

    @FXML
    private Label fiberLabel;

    @FXML
    private Label fiberWeight;

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
    private ListView<String> macroList1;

    @FXML
    private ListView<String> macroList2;

    @FXML
    private ListView<String> macroList3;

    @FXML
    private ListView<String> macroList4;

    @FXML
    private ListView<String> macroWeightList1;

    @FXML
    private ListView<String> macroWeightList2;

    @FXML
    private ListView<String> macroWeightList3;

    @FXML
    private ListView<String> macroWeightList4;

    @FXML
    private DatePicker mealDate;

    @FXML
    private Label mealName1;

    @FXML
    private Label mealName2;

    @FXML
    private Label mealName3;

    @FXML
    private Label mealName4;

    @FXML
    private TextField nameField;

    @FXML
    private Label proteinLabel;

    @FXML
    private Label proteinWeight;

    @FXML
    private TextField searchField;

    @FXML
    private Label sugarLabel;

    @FXML
    private Label sugarWeight;

    @FXML
    private TextField mealTime;

    @FXML
    private ChoiceBox<String> mealChoice;

    @FXML
    void gotoDashboard(MouseEvent event) {HelloApplication.changeScene("dashboard");
    }

    @FXML
    void gotoProfile(MouseEvent event) {HelloApplication.changeScene("userProfile");
    }

    @FXML
    void gotoWorkout(MouseEvent event) {HelloApplication.changeScene("workout");
    }

    @FXML
    void gotoAutomeal(MouseEvent event) {HelloApplication.changeScene("automeal");
    }

    @FXML
    void deleteMeal1(ActionEvent event) {
        // TODO: Implement meal deletion
    }

    @FXML
    void deleteMeal2(ActionEvent event) {
        // TODO: Implement meal deletion
    }

    @FXML
    void deleteMeal3(ActionEvent event) {
        // TODO: Implement meal deletion
    }

    @FXML
    void deleteMeal4(ActionEvent event) {
        // TODO: Implement meal deletion
    }

    @FXML
    void logMeal1(ActionEvent event) {
        // TODO: Display logged meal details
    }

    @FXML
    void logMeal2(ActionEvent event) {
        // TODO: Display logged meal details
    }

    @FXML
    void logMeal3(ActionEvent event) {
        // TODO: Display logged meal details
    }

    @FXML
    void logMeal4(ActionEvent event) {
        // TODO: Display logged meal details
    }

    @FXML
    void searchEvent(KeyEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        // If search field is empty, show all foods
        if (searchText.isEmpty()) {
            displayedFoodlist = new ArrayList<>(service.getAllFoods());
        } else {
            // Filter foods from memory (no database query)
            displayedFoodlist = service.getAllFoods().stream()
                    .filter(food -> food.getName().toLowerCase().contains(searchText))
                    .toList();
        }

        // Refresh the UI with filtered results
        refreshDisplayedFoodDisplay();
    }

    MealEntryService service = new MealEntryService();
    MealLoggingService loggingService = new MealLoggingService();

    public List<Food> displayedFoodlist = service.getAllFoods();
    public List<Food> selectedFoodList = new ArrayList<>();

    private static MealEntryController instance;

    public MealEntryController() {
        instance = this;
    }

    public static MealEntryController getInstance() {
        return instance;
    }

    public void addFoodToSelectedList(Food food) {
        if (!selectedFoodList.contains(food)) {
            selectedFoodList.add(food);
            refreshSelectedFoodDisplay();
        }
    }

    public void refreshSelectedFoodDisplay() {
        if (foodListVbox != null) {
            foodListVbox.getChildren().clear();
            service.loadFoodListPane(selectedFoodList, foodListVbox);
        } else {
            System.err.println("ERROR: foodListVbox is null!");
        }
    }
    public void refreshDisplayedFoodDisplay() {
        if (foodVbox != null) {
            foodVbox.getChildren().clear();
            service.loadFoodPane(displayedFoodlist, foodVbox);
        } else {
            System.err.println("ERROR: foodVbox is null!");
        }
    }

    private void clearForm() {
        nameField.clear();
        mealTime.clear();
        mealDate.setValue(null);
        selectedFoodList.clear();
        refreshSelectedFoodDisplay();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    /**
     * Log Meal - Save to logged_meals table with name, date, and time
     */
    @FXML
    void logMeal(ActionEvent event) {
        String mealName = mealChoice.getValue();
        LocalDate mealDateValue = mealDate.getValue();
        String mealTimeStr = mealTime.getText();

        // Validate inputs
        if (mealName == null || mealName.trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a meal name");
            return;
        }

        if (mealDateValue == null) {
            showAlert("Validation Error", "Please select a meal date");
            return;
        }

        if (mealTimeStr == null || mealTimeStr.trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a meal time (HH:mm format)");
            return;
        }

        if (selectedFoodList.isEmpty()) {
            showAlert("Validation Error", "Please select at least one food item");
            return;
        }

        try {
            // Parse time from HH:mm format
            LocalTime mealTime = LocalTime.parse(mealTimeStr);
            LocalDateTime mealDateTime = LocalDateTime.of(mealDateValue, mealTime);

            // Call service to log meal
            boolean success = loggingService.logMealToDatabase(
                    mealName,
                    mealDateTime,
                    new ArrayList<>(selectedFoodList)
            );

            if (success) {
                showAlert("Success", "Meal logged successfully!");
                clearForm();
            } else {
                showAlert("Error", "Failed to log meal. Please try again.");
            }

        } catch (Exception e) {
//            showAlert("Error", "Invalid time format. Please use HH:mm (e.g., 14:30)");
            e.printStackTrace();
        }
        foodListVbox.getChildren().clear();
    }

    /**
     * Save as Preset - Save to meal_presets table with only meal name
     */
    @FXML
    void savePreset(ActionEvent event) {
        fList.addAll("Apple", "Beef", "Roti", "Banana", "Egg");
        foodList3.setItems(fList);

        wList.addAll("150g", "250g", "200g", "30g", "60g");
        foodWeightList3.setItems(wList);

        mWeightList.addAll("90g", "320g", "25g", "10g", "15g", "", "2105");
        macroWeightList3.setItems(mWeightList);
    }


    @FXML
    void calculateEvent(ActionEvent event) {
        proteinWeight.setText(generateRandomWithG(90, 160)+ "g");
        carbsWeight.setText(generateRandomWithG(300, 450)+ "g");
        fatWeight.setText(generateRandomWithG(15, 30)+ "g");
        fiberWeight.setText(generateRandomWithG(10, 25)+ "g");
        sugarWeight.setText(generateRandomWithG(10, 35)+ "g");

        calorieLabel.setText(generateRandomWithG(1800, 2600));
    }

    public static String generateRandomWithG(int min, int max) {
        Random rand = new Random();
        int randomValue = rand.nextInt(max - min + 1) + min;
        return String.valueOf(randomValue);
    }


    public ObservableList<String> foodList = FXCollections.observableArrayList();
    public ObservableList<String> fList = FXCollections.observableArrayList();
    public ObservableList<String> wList = FXCollections.observableArrayList();
    public ObservableList<String> weightList = FXCollections.observableArrayList();
    public ObservableList<String> macroList = FXCollections.observableArrayList();
    public ObservableList<String> macroWeightList = FXCollections.observableArrayList();
    public ObservableList<String> mWeightList = FXCollections.observableArrayList();
    public ObservableList<String> mealTypes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mealTypes.add("Breakfast");
        mealTypes.add("Lunch");
        mealTypes.add("Dinner");
        mealTypes.add("Extra Meal");
        mealChoice.setItems(mealTypes);

        //-------------------------------

        foodList.addAll("Chicken", "Rice", "Bread", "Banana");
        foodList1.setItems(foodList);
        foodList2.setItems(foodList);

        weightList.addAll("150g", "450g", "50g", "30g");
        foodWeightList1.setItems(weightList);
        foodWeightList2.setItems(weightList);

        macroList.addAll("Protein", "Carbs", "Fat", "Fiber", "Sugar", "", "Total Cal");
        macroList1.setItems(macroList);
        macroList2.setItems(macroList);
        macroList3.setItems(macroList);
        macroList4.setItems(macroList);

        macroWeightList.addAll("150g", "450g", "50g", "30g", "20g", "", "2300");
        macroWeightList1.setItems(macroWeightList);
        macroWeightList2.setItems(macroWeightList);


        proteinWeight.setText("00g");
        carbsWeight.setText("00g");
        fatWeight.setText("00g");
        fiberWeight.setText("00g");
        sugarWeight.setText("00g");
        calorieLabel.setText("00");


        // Load food list
        service.loadFoodPane(displayedFoodlist, foodVbox);
    }
}