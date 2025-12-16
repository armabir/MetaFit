package com.example.metafit_v1;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;


public class TestController {
    @FXML private VBox weightLossPane;
    @FXML private VBox weightGainPane;
    @FXML private VBox targetBudgetPane;
    @FXML private VBox targetProteinPane;

    @FXML private ToggleButton weightLossBtn;
    @FXML private ToggleButton weightGainBtn;
    @FXML private ToggleButton targetBudgetBtn;
    @FXML
    private ToggleButton targetProteinBtn;
    @FXML
    private StackPane circleContainer;
    @FXML
    public void initialize() {

    }

    private void showPane(VBox paneToShow) {
        weightLossPane.setVisible(false);
        weightLossPane.setManaged(false);
        weightGainPane.setVisible(false);
        weightGainPane.setManaged(false);
        targetBudgetPane.setVisible(false);
        targetBudgetPane.setManaged(false);
        targetProteinPane.setVisible(false);
        targetProteinPane.setManaged(false);

        paneToShow.setVisible(true);
        paneToShow.setManaged(true);
    }

    public void gotoProfile(MouseEvent mouseEvent) {
    }

    public void gotoAutomeal(MouseEvent mouseEvent) {
    }

    public void gotoMealEntry(MouseEvent mouseEvent) {
    }

    public void gotoWorkout(MouseEvent mouseEvent) {
    }
}

