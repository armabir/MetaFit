package com.example.metafit_v1.models;

public class Exercise {
    private int exerciseId;
    private String name;
    private String muscle;
    private String imageUrl;

    // Constructor with all fields
    public Exercise(int exerciseId, String name, String muscle, String imageUrl) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.muscle = muscle;
        this.imageUrl = imageUrl;
    }

    // Constructor without ID (for new entries)
    public Exercise(String name, String muscle, String imageUrl) {
        this.name = name;
        this.muscle = muscle;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getExerciseId() {
        return exerciseId;
    }

    public String getName() {
        return name;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseId=" + exerciseId +
                ", name='" + name + '\'' +
                ", muscle='" + muscle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}