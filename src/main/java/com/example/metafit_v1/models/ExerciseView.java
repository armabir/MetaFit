package com.example.metafit_v1.models;

public class ExerciseView {
    private int exerciseId;
    private String name;
    private String muscle;
    private String imageUrl;
    private int sets;
    private int reps;
    private double weight;

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ExerciseView(int exerciseId, String name, String muscle, String imageUrl, int sets, int reps, double weight) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.muscle = muscle;
        this.imageUrl = imageUrl;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}
