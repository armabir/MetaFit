package com.example.metafit_v1.services;

import com.example.metafit_v1.models.Exercise;
import com.example.metafit_v1.models.ExerciseRoutine;
import com.example.metafit_v1.models.ExerciseView;
import com.example.metafit_v1.other_controllers.ExercisePanelController;
import com.example.metafit_v1.user_controllers.WorkoutController;
import com.example.metafit_v1.util.ConnectionSingleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ExerciseService {
    private Connection connection = ConnectionSingleton.getConnection();

    /**
     * Get all exercises for a specific routine
     * @param routineId The routine ID
     * @return List of Exercise objects for the routine
     */
    public List<Exercise> getExercisesByRoutine(int routineId) {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT e.exercise_id, e.name, e.muscle, e.image_url " +
                "FROM exercises e " +
                "INNER JOIN routine_exercises re ON e.exercise_id = re.exercise_id " +
                "WHERE re.routine_id = ? " +
                "ORDER BY re.routine_exercise_id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int exerciseId = rs.getInt("exercise_id");
                String name = rs.getString("name");
                String muscle = rs.getString("muscle");
                String imageUrl = rs.getString("image_url");

                exercises.add(new Exercise(exerciseId, name, muscle, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    public List<ExerciseView> getExercisesView(int routineId) {
        List<ExerciseView> exercises = new ArrayList<>();
        String query = "SELECT e.exercise_id, e.name, e.muscle, e.image_url, re.sets, re.reps, re.weight " +
                "FROM exercises e " +
                "INNER JOIN routine_exercises re ON e.exercise_id = re.exercise_id " +
                "WHERE re.routine_id = ? " +
                "ORDER BY re.routine_exercise_id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int exerciseId = rs.getInt("exercise_id");
                String name = rs.getString("name");
                String muscle = rs.getString("muscle");
                String imageUrl = rs.getString("image_url");
                int sets = rs.getInt("sets");
                int reps = rs.getInt("reps");
                double weight = rs.getDouble("weight");

                exercises.add(new ExerciseView(exerciseId, name, muscle, imageUrl, sets, reps, weight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    /**
     * Get all exercises by muscle group
     * @param muscle The muscle group name
     * @return List of Exercise objects for that muscle
     */
    public List<Exercise> getExercisesByMuscle(String muscle) {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT exercise_id, name, muscle, image_url FROM exercises WHERE muscle = ? ORDER BY name";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, muscle);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int exerciseId = rs.getInt("exercise_id");
                String name = rs.getString("name");
                String muscleGroup = rs.getString("muscle");
                String imageUrl = rs.getString("image_url");

                exercises.add(new Exercise(exerciseId, name, muscleGroup, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    /**
     * Get all exercises
     * @return List of all Exercise objects
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT exercise_id, name, muscle, image_url FROM exercises ORDER BY name";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int exerciseId = rs.getInt("exercise_id");
                String name = rs.getString("name");
                String muscle = rs.getString("muscle");
                String imageUrl = rs.getString("image_url");

                exercises.add(new Exercise(exerciseId, name, muscle, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    /**
     * Get a single exercise by ID
     * @param exerciseId The exercise ID
     * @return Exercise object or null if not found
     */
    public Exercise getExerciseById(int exerciseId) {
        String query = "SELECT exercise_id, name, muscle, image_url FROM exercises WHERE exercise_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String muscle = rs.getString("muscle");
                String imageUrl = rs.getString("image_url");

                return new Exercise(exerciseId, name, muscle, imageUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void paneLoader(List<ExerciseView> exerciseList, VBox vBox){
        for (ExerciseView e : exerciseList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/metafit_v1/exercisePanel.fxml"));
                Pane item = loader.load();

                ExercisePanelController controller = loader.getController();
                controller.setData(e.getName(), e.getSets(), e.getReps(), e.getWeight(), e.getImageUrl());

                vBox.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
