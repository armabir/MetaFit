package com.example.metafit_v1.other_controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ExercisePanelController {

    @FXML
    private Label deleteButton;
    @FXML
    private ImageView exerciseImage;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField repField;

    @FXML
    private TextField setField;

    @FXML
    private TextField weightField;

    @FXML
    void deletePanel(MouseEvent event) {
        Node currentPanel = deleteButton.getParent();
        Pane pane = (Pane) currentPanel.getParent();
        VBox vBox = (VBox) pane;
        vBox.getChildren().remove(pane);
    }

    public String getName(){
        return nameLabel.getText();
    }
    public int getSet(){
        return Integer.parseInt(setField.getText());
    }
    public int getRep(){
        return Integer.parseInt(repField.getText());
    }
    public double getWeight(){
        return Double.parseDouble(weightField.getText());
    }
    public String getImageURL(){
        return exerciseImage.getImage().getUrl();
    }

    public void setData(String name, int sets, int reps, double weight, String url){
        nameLabel.setText(name);
        setField.setText(String.valueOf(sets));
        repField.setText(String.valueOf(reps));
        weightField.setText(String.valueOf(weight));
        Image image = new Image(getClass().getResource(url).toExternalForm());
        exerciseImage.setImage(image);

        setField.setStyle("-fx-text-fill: white");
        repField.setStyle("-fx-text-fill: white");
        weightField.setStyle("-fx-text-fill: white");
    }

}
