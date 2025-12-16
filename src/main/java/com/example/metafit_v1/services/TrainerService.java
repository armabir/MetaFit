package com.example.metafit_v1.services;

import com.example.metafit_v1.models.Trainer;
import com.example.metafit_v1.util.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {
    public boolean createTrainer(Trainer trainer) {
        String query = "INSERT INTO trainers (Username, Name, Email, Password) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, trainer.getUsername());
            pstmt.setString(2, trainer.getName());
            pstmt.setString(3, trainer.getEmail());
            pstmt.setString(4, trainer.getPassword()); // Should be hashed before passing

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get trainer by TrainerID
    public Trainer getTrainerByID(int trainerID) {
        String query = "SELECT * FROM trainers WHERE TrainerID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, trainerID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrainer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get trainer by Username
    public Trainer getTrainerByUsername(String username) {
        String query = "SELECT * FROM trainers WHERE Username = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrainer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get trainer by Email
    public Trainer getTrainerByEmail(String email) {
        String query = "SELECT * FROM trainers WHERE Email = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrainer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all trainers
    public List<Trainer> getAllTrainers() {
        String query = "SELECT * FROM trainers ORDER BY Name ASC";
        List<Trainer> trainers = new ArrayList<>();

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                trainers.add(mapResultSetToTrainer(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    // UPDATE - Update trainer profile
    public boolean updateTrainer(Trainer trainer) {
        String query = "UPDATE trainers SET Name = ?, Email = ? WHERE TrainerID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, trainer.getName());
            pstmt.setString(2, trainer.getEmail());
            pstmt.setInt(3, trainer.getTrainerID());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE - Update trainer password
    public boolean updatePassword(int trainerID, String newHashedPassword) {
        String query = "UPDATE trainers SET Password = ? WHERE TrainerID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, trainerID);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete trainer
    public boolean deleteTrainer(int trainerID) {
        String query = "DELETE FROM trainers WHERE TrainerID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, trainerID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // CHECK - Username exists
    public boolean usernameExists(String username) {
        return getTrainerByUsername(username) != null;
    }

    // CHECK - Email exists
    public boolean emailExists(String email) {
        return getTrainerByEmail(email) != null;
    }

    // HELPER - Map ResultSet to Trainer object
    private Trainer mapResultSetToTrainer(ResultSet rs) throws SQLException {
        return new Trainer(
                rs.getInt("TrainerID"),
                rs.getString("Username"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getString("Password"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime().toLocalDate() : null
        );
    }
}
