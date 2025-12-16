package com.example.metafit_v1.models;

public class ExerciseRoutine {
    private int routineExerciseId;
    private int routineId;
    private int exerciseId;
    private int UserID;
    private String routineName;
    private int sets;
    private int reps;
    private double weight;

    // Constructor with all fields
    public ExerciseRoutine(int routineExerciseId, int routineId, int exerciseId, int UserID,
                           String routineName, int sets, int reps, double weight) {
        this.routineExerciseId = routineExerciseId;
        this.routineId = routineId;
        this.exerciseId = exerciseId;
        this.UserID = UserID;
        this.routineName = routineName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Constructor without ID (for new entries)
    public ExerciseRoutine(int routineId, int exerciseId, int UserID, String routineName,
                           int sets, int reps, double weight) {
        this.routineId = routineId;
        this.exerciseId = exerciseId;
        this.UserID = UserID;
        this.routineName = routineName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters
    public int getRoutineExerciseId() {
        return routineExerciseId;
    }

    public int getRoutineId() {
        return routineId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getUserID() {
        return UserID;
    }

    public String getRoutineName() {
        return routineName;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    // Setters
    public void setRoutineExerciseId(int routineExerciseId) {
        this.routineExerciseId = routineExerciseId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RoutineExercise{" +
                "routineExerciseId=" + routineExerciseId +
                ", routineId=" + routineId +
                ", exerciseId=" + exerciseId +
                ", UserID=" + UserID +
                ", routineName='" + routineName + '\'' +
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
