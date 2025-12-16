package com.example.metafit_v1.models;

public class Food {
    private int id;
    private String name;
    private double protein;
    private double carb;
    private double fat;
    private double fiber;
    private double sugar;
    private double calorie;

    public Food(int id, String name, double protein, double carb, double fat, double fiber, double sugar, double calorie) {
        this.id = id;
        this.name = name;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
        this.fiber = fiber;
        this.sugar = sugar;
        this.calorie = calorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }
}
