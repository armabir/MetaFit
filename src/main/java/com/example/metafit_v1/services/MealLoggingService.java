package com.example.metafit_v1.services;

import com.example.metafit_v1.models.Food;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class MealLoggingService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/metafit";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "armabir1"; // Change if you have a password

    /**
     * Get database connection
     */
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Log a meal to the database
     * Inserts into logged_meals table and meal_foods table
     */
    public boolean logMealToDatabase(String mealName, LocalDateTime mealDateTime, List<Food> foods) {
        Connection conn = null;
        try {
            conn = getConnection();
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
                foodStmt.setInt(2, food.getId()); // Assuming Food model has getId()
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
                    conn.close();
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
            conn = getConnection();
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
                foodStmt.setInt(2, food.getId()); // Assuming Food model has getId()
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
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}