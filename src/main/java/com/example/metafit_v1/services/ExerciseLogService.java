package com.example.metafit_v1.services;

import com.example.metafit_v1.models.ExerciseLog;
import com.example.metafit_v1.models.ExerciseView;
import com.example.metafit_v1.util.ConnectionSingleton;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseLogService {
    private Connection connection = ConnectionSingleton.getConnection();

    /**
     * Add a new exercise log entry
     * @param exerciseLog ExerciseLog object to be added
     * @return true if successful, false otherwise
     */
    public boolean addExerciseLog(ExerciseLog exerciseLog) {
        String query = "INSERT INTO workout_log (UserID, exercise_date, muscle) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseLog.getUserID());
            stmt.setDate(2, Date.valueOf(exerciseLog.getExerciseDate()));
            stmt.setString(3, exerciseLog.getMuscle());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Add exercise logs from a list of ExerciseView objects with the same date
     * @param UserID The user ID
     * @param exerciseViews List of ExerciseView objects
     * @param exerciseDate The date for all exercises
     * @return true if all insertions successful, false otherwise
     */
    public boolean addExerciseLogsFromList(int UserID, List<ExerciseView> exerciseViews, LocalDate exerciseDate) {
        String query = "INSERT INTO workout_log (UserID, exercise_date, muscle) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (ExerciseView exercise : exerciseViews) {
                stmt.setInt(1, UserID);
                stmt.setDate(2, Date.valueOf(exerciseDate));
                stmt.setString(3, exercise.getMuscle());
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            return results.length == exerciseViews.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Add exercise logs from a list of ExerciseView objects with the same date
     * Inserts only UNIQUE muscles (no duplicates)
     * @param UserID The user ID
     * @param exerciseViews List of ExerciseView objects
     * @param exerciseDate The date for all exercises
     * @return true if insertions successful, false otherwise
     */
    public boolean addUniqueExerciseLogsFromList(int UserID, List<ExerciseView> exerciseViews, LocalDate exerciseDate) {
        String query = "INSERT INTO workout_log (UserID, exercise_date, muscle) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            Set<String> uniqueMuscles = new HashSet<>();

            for (ExerciseView exercise : exerciseViews) {
                uniqueMuscles.add(exercise.getMuscle());
            }

            for (String muscle : uniqueMuscles) {
                stmt.setInt(1, UserID);
                stmt.setDate(2, Date.valueOf(exerciseDate));
                stmt.setString(3, muscle);
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            return results.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get all exercise logs for a specific user
     * @param UserID The user ID
     * @return List of ExerciseLog objects for the user
     */
    public List<ExerciseLog> getExerciseLogsByUser(int UserID) {
        List<ExerciseLog> logs = new ArrayList<>();
        String query = "SELECT workout_log_id, UserID, exercise_date, muscle FROM workout_log WHERE UserID = ? ORDER BY exercise_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UserID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int workoutLogId = rs.getInt("workout_log_id");
                int userId = rs.getInt("UserID");
                LocalDate exerciseDate = rs.getDate("exercise_date").toLocalDate();
                String muscle = rs.getString("muscle");

                logs.add(new ExerciseLog(workoutLogId, userId, exerciseDate, muscle));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Get exercise logs for a specific user on a specific date
     * @param UserID The user ID
     * @param date The exercise date
     * @return List of ExerciseLog objects for the user on that date
     */
    public List<ExerciseLog> getExerciseLogsByUserAndDate(int UserID, LocalDate date) {
        List<ExerciseLog> logs = new ArrayList<>();
        String query = "SELECT workout_log_id, UserID, exercise_date, muscle FROM workout_log WHERE UserID = ? AND exercise_date = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UserID);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int workoutLogId = rs.getInt("workout_log_id");
                int userId = rs.getInt("UserID");
                LocalDate exerciseDate = rs.getDate("exercise_date").toLocalDate();
                String muscle = rs.getString("muscle");

                logs.add(new ExerciseLog(workoutLogId, userId, exerciseDate, muscle));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Get exercise logs for a specific muscle group for a user
     * @param UserID The user ID
     * @param muscle The muscle group
     * @return List of ExerciseLog objects for the muscle group
     */
    public List<ExerciseLog> getExerciseLogsByMuscle(int UserID, String muscle) {
        List<ExerciseLog> logs = new ArrayList<>();
        String query = "SELECT workout_log_id, UserID, exercise_date, muscle FROM workout_log WHERE UserID = ? AND muscle = ? ORDER BY exercise_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UserID);
            stmt.setString(2, muscle);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int workoutLogId = rs.getInt("workout_log_id");
                int userId = rs.getInt("UserID");
                LocalDate exerciseDate = rs.getDate("exercise_date").toLocalDate();
                String muscleGroup = rs.getString("muscle");

                logs.add(new ExerciseLog(workoutLogId, userId, exerciseDate, muscleGroup));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Delete an exercise log entry
     * @param workoutLogId The workout log ID
     * @return true if successful, false otherwise
     */
    public boolean deleteExerciseLog(int workoutLogId) {
        String query = "DELETE FROM workout_log WHERE workout_log_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, workoutLogId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get total exercise log count for a user
     * @param UserID The user ID
     * @return Total number of logged exercises
     */
    public int getTotalExerciseLogCount(int UserID) {
        String query = "SELECT COUNT(*) as total FROM workout_log WHERE UserID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UserID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}