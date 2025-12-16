package com.example.metafit_v1.services;

import com.example.metafit_v1.models.Food;
import com.example.metafit_v1.other_controllers.FoodListPanelController;
import com.example.metafit_v1.other_controllers.FoodPanelController;
import com.example.metafit_v1.util.ConnectionSingleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealEntryService {
    public List<Food> getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        String query = "SELECT * FROM food ORDER BY name";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionSingleton.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double protein = rs.getDouble("protein");
                double carb = rs.getDouble("carb");
                double fat = rs.getDouble("fat");
                double fiber = rs.getDouble("fiber");
                double sugar = rs.getDouble("sugar");
                double calorie = rs.getDouble("calorie");

                Food food = new Food(id, name, protein, carb, fat, fiber, sugar, calorie);
                foodList.add(food);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving foods: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close ResultSet and Statement, but NOT the Connection
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // DO NOT close conn here - ConnectionSingleton manages it
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return foodList;
    }

    public List<Food> searchFoods(String searchQuery) {
        List<Food> foodList = new ArrayList<>();
        String query = "SELECT * FROM food WHERE name LIKE ? ORDER BY name";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionSingleton.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + searchQuery + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double protein = rs.getDouble("protein");
                double carb = rs.getDouble("carb");
                double fat = rs.getDouble("fat");
                double fiber = rs.getDouble("fiber");
                double sugar = rs.getDouble("sugar");
                double calorie = rs.getDouble("calorie");

                Food food = new Food(id, name, protein, carb, fat, fiber, sugar, calorie);
                foodList.add(food);
            }

        } catch (SQLException e) {
            System.out.println("Error searching foods: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // DO NOT close conn here - ConnectionSingleton manages it
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return foodList;
    }

    /**
     * Retrieves a single food by ID
     * @param foodId The ID of the food to retrieve
     * @return Food object if found, null otherwise
     */
    public Food getFoodById(int foodId) {
        String query = "SELECT * FROM food WHERE id = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, foodId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double protein = rs.getDouble("protein");
                double carb = rs.getDouble("carb");
                double fat = rs.getDouble("fat");
                double fiber = rs.getDouble("fiber");
                double sugar = rs.getDouble("sugar");
                double calorie = rs.getDouble("calorie");

                return new Food(id, name, protein, carb, fat, fiber, sugar, calorie);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving food by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void loadFoodPane(List<Food> foodList, VBox vBox){
        for (Food food : foodList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/metafit_v1/foodPanel.fxml"));
                Pane item = loader.load();

                FoodPanelController controller = loader.getController();
                controller.setData(food);

                vBox.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadFoodListPane(List<Food> foodList, VBox vBox){
        for (Food e : foodList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/metafit_v1/foodListPanel.fxml"));
                Pane item = loader.load();

                FoodListPanelController controller = loader.getController();
                controller.setData(e.getName());

                vBox.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Log a meal to the database
     * Inserts into logged_meals table and meal_foods table
     */
    public boolean logMealToDatabase(String mealName, LocalDateTime mealDateTime, List<Food> foods) {
        Connection conn = null;
        try {
            conn = ConnectionSingleton.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert into logged_meals table
            String insertMealSQL = "INSERT INTO logged_meals (meal_name, meal_date, meal_time) VALUES (?, ?, ?)";
            PreparedStatement mealStmt = conn.prepareStatement(insertMealSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            mealStmt.setString(1, mealName);
            mealStmt.setDate(2, java.sql.Date.valueOf(mealDateTime.toLocalDate()));
            mealStmt.setTime(3, java.sql.Time.valueOf(mealDateTime.toLocalTime()));

            mealStmt.executeUpdate();

            // Get the generated meal ID
            ResultSet generatedKeys = mealStmt.getGeneratedKeys();
            int mealId = 0;
            if (generatedKeys.next()) {
                mealId = generatedKeys.getInt(1);
            }

            // Insert food items for this meal
            String insertFoodSQL = "INSERT INTO meal_foods (meal_id, food_id, food_name, protein, carbs, fat, fiber, sugar, calorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement foodStmt = conn.prepareStatement(insertFoodSQL);

            for (Food food : foods) {
                foodStmt.setInt(1, mealId);
                foodStmt.setInt(2, food.getId());
                foodStmt.setString(3, food.getName());
                foodStmt.setDouble(4, food.getProtein());
                foodStmt.setDouble(5, food.getCarb());
                foodStmt.setDouble(6, food.getFat());
                foodStmt.setDouble(7, food.getFiber());
                foodStmt.setDouble(8, food.getSugar());
                foodStmt.setDouble(9, food.getCalorie());
                foodStmt.addBatch();
            }

            foodStmt.executeBatch();
            conn.commit(); // Commit transaction

            System.out.println("✓ Meal logged successfully: " + mealName);
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("ERROR logging meal: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Save a meal preset to the database
     * Inserts into meal_presets table and preset_foods table
     */
    public boolean saveMealPresetToDatabase(String presetName, List<Food> foods) {
        Connection conn = null;
        try {
            conn = ConnectionSingleton.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert into meal_presets table
            String insertPresetSQL = "INSERT INTO meal_presets (preset_name, created_at) VALUES (?, NOW())";
            PreparedStatement presetStmt = conn.prepareStatement(insertPresetSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            presetStmt.setString(1, presetName);
            presetStmt.executeUpdate();

            // Get the generated preset ID
            ResultSet generatedKeys = presetStmt.getGeneratedKeys();
            int presetId = 0;
            if (generatedKeys.next()) {
                presetId = generatedKeys.getInt(1);
            }

            // Insert food items for this preset
            String insertFoodSQL = "INSERT INTO preset_foods (preset_id, food_id, food_name, protein, carbs, fat, fiber, sugar, calorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement foodStmt = conn.prepareStatement(insertFoodSQL);

            for (Food food : foods) {
                foodStmt.setInt(1, presetId);
                foodStmt.setInt(2, food.getId());
                foodStmt.setString(3, food.getName());
                foodStmt.setDouble(4, food.getProtein());
                foodStmt.setDouble(5, food.getCarb());
                foodStmt.setDouble(6, food.getFat());
                foodStmt.setDouble(7, food.getFiber());
                foodStmt.setDouble(8, food.getSugar());
                foodStmt.setDouble(9, food.getCalorie());
                foodStmt.addBatch();
            }

            foodStmt.executeBatch();
            conn.commit(); // Commit transaction

            System.out.println("✓ Preset saved successfully: " + presetName);
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("ERROR saving preset: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}