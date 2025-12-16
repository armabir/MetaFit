package com.example.metafit_v1.models;

import java.time.LocalDate;

public class ExerciseLog {
    private int workoutLogId;
    private int UserID;
    private LocalDate exerciseDate;
    private String muscle;

    // Constructor with all fields
    public ExerciseLog(int workoutLogId, int UserID, LocalDate exerciseDate, String muscle) {
        this.workoutLogId = workoutLogId;
        this.UserID = UserID;
        this.exerciseDate = exerciseDate;
        this.muscle = muscle;
    }

    // Constructor without ID (for new entries)
    public ExerciseLog(int UserID, LocalDate exerciseDate, String muscle) {
        this.UserID = UserID;
        this.exerciseDate = exerciseDate;
        this.muscle = muscle;
    }

    // Getters
    public int getWorkoutLogId() {
        return workoutLogId;
    }

    public int getUserID() {
        return UserID;
    }

    public LocalDate getExerciseDate() {
        return exerciseDate;
    }

    public String getMuscle() {
        return muscle;
    }

    // Setters
    public void setWorkoutLogId(int workoutLogId) {
        this.workoutLogId = workoutLogId;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setExerciseDate(LocalDate exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "workoutLogId=" + workoutLogId +
                ", UserID=" + UserID +
                ", exerciseDate=" + exerciseDate +
                ", muscle='" + muscle + '\'' +
                '}';
    }
}
