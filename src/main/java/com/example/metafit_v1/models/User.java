package com.example.metafit_v1.models;

import java.time.LocalDate;

public class User {
    private int userID;
    private String username;
    private String name;
    private String email;
    private String password;
    private String imageUrl;
    private LocalDate birthday;
    private String gender;
    private double weight;
    private double height;
    private String target;
    private String activityLevel;
    private Integer trainerID; // Nullable
    private LocalDate createdAt;

    // Constructor (full)
    public User(int userID, String username, String name, String email, String password,
                String imageUrl, LocalDate birthday, String gender, double weight, double height,
                String target, String activityLevel, Integer trainerID, LocalDate createdAt) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.activityLevel = activityLevel;
        this.trainerID = trainerID;
        this.createdAt = createdAt;
    }

    // Constructor (for registration - no userID or createdAt yet)
    public User(String username, String name, String email, String password, String imageUrl,
                LocalDate birthday, String gender, double weight, double height,
                String target, String activityLevel) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.activityLevel = activityLevel;
        this.trainerID = null;
    }

    // Getters and Setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public Integer getTrainerID() { return trainerID; }
    public void setTrainerID(Integer trainerID) { this.trainerID = trainerID; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                ", trainerID=" + trainerID +
                '}';
    }
}