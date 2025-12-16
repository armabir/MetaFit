package com.example.metafit_v1.user_controllers;

import com.example.metafit_v1.HelloApplication;
import com.example.metafit_v1.models.ExerciseView;
import com.example.metafit_v1.other_controllers.ExercisePanelController;
import com.example.metafit_v1.services.ExerciseLogService;
import com.example.metafit_v1.services.ExerciseService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutController implements Initializable {


    @FXML
    private Rectangle absStatus;

    @FXML
    private Rectangle bicepsStatus;

    @FXML
    private Rectangle calvesStatus;

    @FXML
    private Rectangle chestStatus;

    @FXML
    private DatePicker exerciseDate;

    @FXML
    private ScrollPane exerciseScrollPane;

    @FXML
    private VBox exerciseVbox;

    @FXML
    private Rectangle hamsStatus;

    @FXML
    private Rectangle latsStatus;

    @FXML
    private Button logbutton;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel2;

    @FXML
    private Label nameLabel3;

    @FXML
    private Label nameLabel4;

    @FXML
    private Label nameLabel5;

    @FXML
    private Label nameLabel6;

    @FXML
    private Rectangle quadricepsStatus;

    @FXML
    private Rectangle shoulderStatus;

    @FXML
    private Rectangle trapsStatus;

    @FXML
    private Rectangle tricepsStatus;

    @FXML
    private Button viewButton1;

    @FXML
    private Button viewButton2;

    @FXML
    private Button viewButton3;

    @FXML
    private Button viewButton4;

    @FXML
    private Button viewButton5;

    @FXML
    private Button viewButton6;

    @FXML
    private Label welcomeLabel;

    @FXML
    void gotoDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboard");
    }

    @FXML
    void gotoProfile(MouseEvent event) {
        HelloApplication.changeScene("userProfile");
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
    void logWorkout(ActionEvent event) {
        resetStatus();
        setStatus();

        if (exerciseDate.getValue() == null){
            showAlert("Error", "Date is Empty");
            return;
        }
        ExerciseLogService service = new ExerciseLogService();
        LocalDate day = exerciseDate.getValue();
        int userId = 1;

        service.addUniqueExerciseLogsFromList(userId, displayedList, day);

        exerciseVbox.getChildren().clear();
    }


    ExerciseService service = new ExerciseService();
    public List<ExerciseView> displayedList = new ArrayList<>();

    @FXML
    void viewRoutine1(ActionEvent event){
        displayedList.clear();
        displayedList.addAll(service.getExercisesView(1));

        exerciseVbox.getChildren().clear();
        service.paneLoader(displayedList, exerciseVbox);
    }

    @FXML
    void viewRoutine2(ActionEvent event) {
        displayedList.clear();
        displayedList.addAll(service.getExercisesView(2));

        exerciseVbox.getChildren().clear();
        service.paneLoader(displayedList, exerciseVbox);
    }

    @FXML
    void viewRoutine3(ActionEvent event) {
        displayedList.clear();
        displayedList.addAll(service.getExercisesView(3));

        exerciseVbox.getChildren().clear();
        service.paneLoader(displayedList, exerciseVbox);
    }

    @FXML
    void viewRoutine4(ActionEvent event) {
        displayedList.clear();
        displayedList.addAll(service.getExercisesView(4));

        exerciseVbox.getChildren().clear();
        service.paneLoader(displayedList, exerciseVbox);
    }

    @FXML
    void viewRoutine5(ActionEvent event) {

    }

    @FXML
    void viewRoutine6(ActionEvent event) {

    }

    public void setStatus(){
        long Chest = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Chest")).count();
        if (Chest > 0){
            chestStatus.setStyle("-fx-fill: red");
        }
        long Triceps = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Triceps")).count();
        if (Triceps > 0){
            tricepsStatus.setStyle("-fx-fill: red");
        }
        long Shoulder = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Shoulder")).count();
        if (Shoulder > 0){
            shoulderStatus.setStyle("-fx-fill: red");
        }
        long Traps = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Traps")).count();
        if (Traps > 0){
            trapsStatus.setStyle("-fx-fill: red");
        }
        long Lats = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Lats")).count();
        if (Lats > 0){
            latsStatus.setStyle("-fx-fill: red");
        }
        long Biceps = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Biceps")).count();
        if (Biceps > 0){
            bicepsStatus.setStyle("-fx-fill: red");
        }
        long Abs = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Abs")).count();
        if (Abs > 0){
            absStatus.setStyle("-fx-fill: red");
        }
        long Quadriceps = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Quadriceps")).count();
        if (Quadriceps > 0){
            quadricepsStatus.setStyle("-fx-fill: red");
        }
        long Hamstrings = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Hamstrings")).count();
        if (Hamstrings > 0){
            hamsStatus.setStyle("-fx-fill: red");
        }
        long Calves = displayedList.stream()
                .filter(c-> c.getMuscle().equals("Calves")).count();
        if (Calves > 0){
            calvesStatus.setStyle("-fx-fill: red");
        }
    }
    public void resetStatus(){
        absStatus.setStyle("-fx-fill: white");
        chestStatus.setStyle("-fx-fill: white");
        shoulderStatus.setStyle("-fx-fill: white");
        trapsStatus.setStyle("-fx-fill: white");
        tricepsStatus.setStyle("-fx-fill: white");
        bicepsStatus.setStyle("-fx-fill: white");
        latsStatus.setStyle("-fx-fill: white");
        hamsStatus.setStyle("-fx-fill: white");
        quadricepsStatus.setStyle("-fx-fill: white");
        calvesStatus.setStyle("-fx-fill: white");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetStatus();
    }




}
