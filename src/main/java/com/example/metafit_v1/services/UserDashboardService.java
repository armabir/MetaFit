package com.example.metafit_v1.services;

import com.example.metafit_v1.models.Nutrition;
import com.example.metafit_v1.user_controllers.DashboardController;
import com.example.metafit_v1.util.ConnectionSingleton;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboardService {

    public static Nutrition getNutrition(int week, String dayOfWeek){
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM nutrition where week = ? and dayOfWeek = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, week);
            preparedStatement.setString(2, dayOfWeek);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                double protein = resultSet.getDouble("protein");
                double carbs = resultSet.getDouble("carbs");
                double fat = resultSet.getDouble("fat");
                double fiber = resultSet.getDouble("fiber");
                double sugar = resultSet.getDouble("sugar");
                double water = resultSet.getDouble("wate");
                double calorie = resultSet.getDouble("calorie");

                return new Nutrition(week, dayOfWeek, protein, carbs, fat, fiber, sugar,water,calorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void buttonStyle(Button button){
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#ffffff"));
        glow.setRadius(15);
        glow.setSpread(.2);

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: #84ca17; -fx-text-fill: white;");
            button.setEffect(glow);
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: #2F3C33");
            button.setEffect(null);// Restore original look
        });

    }

    public static void setRectangle(Rectangle rectangle, double actualValue, double targetValue){
        double percent = (actualValue/targetValue)*100;
        double newHeight = (210*percent)/100;
        double oldHeight = rectangle.getHeight();
        double oldY = rectangle.getY();
        rectangle.setY(oldY - (newHeight - oldHeight));
        rectangle.setHeight(newHeight);
    }

    public static void setRectanglePane(Pane pane, double actualValue, double targetValue){
        double percent = (actualValue/targetValue)*100;
        double newHeight = (210*percent)/100;
        double oldHeight = pane.getHeight();
        double oldY = pane.getLayoutY();
        pane.setLayoutY(oldY - (newHeight - oldHeight));
        pane.setPrefHeight(newHeight);
    }

    public static void lineChart(LineChart<String, Number> weightLineChart){
        NumberAxis yAxis = (NumberAxis) weightLineChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(45);
        yAxis.setUpperBound(85);
        yAxis.setTickUnit(5);

        // Sample data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Actual Weight");
        series1.getData().add(new XYChart.Data<>("Jan", 67));
        series1.getData().add(new XYChart.Data<>("Feb", 68.5));
        series1.getData().add(new XYChart.Data<>("Mar", 63.2));
        series1.getData().add(new XYChart.Data<>("Apr", 66.6));
        series1.getData().add(new XYChart.Data<>("May", 69));
        series1.getData().add(new XYChart.Data<>("Jun", 71.2));
        series1.getData().add(new XYChart.Data<>("Jul", 73.7));
        series1.getData().add(new XYChart.Data<>("Aug", 72));
        series1.getData().add(new XYChart.Data<>("Sep", 71.2));

//        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
//        series2.setName("Target Weight");
//        series2.getData().add(new XYChart.Data<>("Jan", 60));
//        series2.getData().add(new XYChart.Data<>("Sep", 75));

        // Add data to chart
        weightLineChart.getData().add(series1);
        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #84ca17; -fx-stroke-width: 2px;");
//        series2.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #ffa767; -fx-stroke-width: 2px;");
    }
}
