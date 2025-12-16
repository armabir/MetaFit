package com.example.metafit_v1.models;

import java.util.ArrayList;
import java.util.List;

public class MealPlan {
    private List<String> satFoodList = new ArrayList<>();
    private List<String> sunFoodList = new ArrayList<>();
    private List<String> monFoodList = new ArrayList<>();
    private List<String> satWeightList = new ArrayList<>();
    private List<String> sunWeightList = new ArrayList<>();
    private List<String> monWeightList = new ArrayList<>();
    private List<String> satMarcoWeightList = new ArrayList<>();
    private List<String> sunMarcoWeightList = new ArrayList<>();
    private List<String> monMarcoWeightList = new ArrayList<>();

    public MealPlan(List<String> satFoodList, List<String> sunFoodList, List<String> monFoodList, List<String> satWeightList, List<String> sunWeightList, List<String> monWeightList, List<String> satMarcoWeightList, List<String> sunMarcoWeightList, List<String> monMarcoWeightList) {
        this.satFoodList = satFoodList;
        this.sunFoodList = sunFoodList;
        this.monFoodList = monFoodList;
        this.satWeightList = satWeightList;
        this.sunWeightList = sunWeightList;
        this.monWeightList = monWeightList;
        this.satMarcoWeightList = satMarcoWeightList;
        this.sunMarcoWeightList = sunMarcoWeightList;
        this.monMarcoWeightList = monMarcoWeightList;
    }

    public List<String> getSatFoodList() {
        return satFoodList;
    }

    public void setSatFoodList(List<String> satFoodList) {
        this.satFoodList = satFoodList;
    }

    public List<String> getSunFoodList() {
        return sunFoodList;
    }

    public void setSunFoodList(List<String> sunFoodList) {
        this.sunFoodList = sunFoodList;
    }

    public List<String> getMonFoodList() {
        return monFoodList;
    }

    public void setMonFoodList(List<String> monFoodList) {
        this.monFoodList = monFoodList;
    }

    public List<String> getSatWeightList() {
        return satWeightList;
    }

    public void setSatWeightList(List<String> satWeightList) {
        this.satWeightList = satWeightList;
    }

    public List<String> getSunWeightList() {
        return sunWeightList;
    }

    public void setSunWeightList(List<String> sunWeightList) {
        this.sunWeightList = sunWeightList;
    }

    public List<String> getMonWeightList() {
        return monWeightList;
    }

    public void setMonWeightList(List<String> monWeightList) {
        this.monWeightList = monWeightList;
    }

    public List<String> getSatMarcoWeightList() {
        return satMarcoWeightList;
    }

    public void setSatMarcoWeightList(List<String> satMarcoWeightList) {
        this.satMarcoWeightList = satMarcoWeightList;
    }

    public List<String> getSunMarcoWeightList() {
        return sunMarcoWeightList;
    }

    public void setSunMarcoWeightList(List<String> sunMarcoWeightList) {
        this.sunMarcoWeightList = sunMarcoWeightList;
    }

    public List<String> getMonMarcoWeightList() {
        return monMarcoWeightList;
    }

    public void setMonMarcoWeightList(List<String> monMarcoWeightList) {
        this.monMarcoWeightList = monMarcoWeightList;
    }
}
