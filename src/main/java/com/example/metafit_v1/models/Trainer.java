package com.example.metafit_v1.models;

import java.time.LocalDate;

public class Trainer {
    private int trainerID;
    private String username;
    private String name;
    private String email;
    private String password;
    private LocalDate createdAt;

    // Constructor (full - from DB)
    public Trainer(int trainerID, String username, String name, String email, String password, LocalDate createdAt) {
        this.trainerID = trainerID;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Constructor (for registration - no ID or createdAt)
    public Trainer(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getTrainerID() { return trainerID; }
    public void setTrainerID(int trainerID) { this.trainerID = trainerID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerID=" + trainerID +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
