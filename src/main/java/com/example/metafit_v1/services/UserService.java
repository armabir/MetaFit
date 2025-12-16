package com.example.metafit_v1.services;

import com.example.metafit_v1.models.User;
import com.example.metafit_v1.util.ConnectionSingleton;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // CREATE - Register new user
    public boolean createUser(User user) {
        String query = "INSERT INTO users (Username, Name, Email, Password, ImageUrl, Birthday, Gender, Weight, Height, Target, ActivityLevel, TrainerId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionSingleton.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getImageUrl());
            pstmt.setDate(6, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            pstmt.setString(7, user.getGender());
            pstmt.setDouble(8, user.getWeight());
            pstmt.setDouble(9, user.getHeight());
            pstmt.setString(10, user.getTarget());
            pstmt.setString(11, user.getActivityLevel());
            pstmt.setObject(12, user.getTrainerID());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ - Get user by UserID
    public User getUserByID(int userID) {
        String query = "SELECT * FROM users WHERE UserID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get user by Username
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE Username = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get user by Email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE Email = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all clients of a trainer
    public List<User> getClientsByTrainerID(int trainerID) {
        String query = "SELECT * FROM users WHERE TrainerId = ? ORDER BY Name ASC";
        List<User> clients = new ArrayList<>();

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, trainerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                clients.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    // UPDATE - Update user profile
    public boolean updateUser(User user) {
        String query = "UPDATE users SET Name = ?, Email = ?, ImageUrl = ?, Birthday = ?, Gender = ?, Weight = ?, Height = ?, Target = ?, ActivityLevel = ?, TrainerId = ? " +
                "WHERE UserID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getImageUrl());
            pstmt.setDate(4, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            pstmt.setString(5, user.getGender());
            pstmt.setDouble(6, user.getWeight());
            pstmt.setDouble(7, user.getHeight());
            pstmt.setString(8, user.getTarget());
            pstmt.setString(9, user.getActivityLevel());
            pstmt.setObject(10, user.getTrainerID());
            pstmt.setInt(11, user.getUserID());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE - Update user password
    public boolean updatePassword(int userID, String newHashedPassword) {
        String query = "UPDATE users SET Password = ? WHERE UserID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete user
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM users WHERE UserID = ?";

        try (Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // CHECK - Username exists
    public boolean usernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    // CHECK - Email exists
    public boolean emailExists(String email) {
        return getUserByEmail(email) != null;
    }

    // HELPER - Map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("UserID"),
                rs.getString("Username"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getString("Password"),
                rs.getString("ImageUrl"),
                rs.getDate("Birthday") != null ? rs.getDate("Birthday").toLocalDate() : null,
                rs.getString("Gender"),
                rs.getDouble("Weight"),
                rs.getDouble("Height"),
                rs.getString("Target"),
                rs.getString("ActivityLevel"),
                (Integer) rs.getObject("TrainerId"), // Handles null
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime().toLocalDate() : null
        );
    }
}