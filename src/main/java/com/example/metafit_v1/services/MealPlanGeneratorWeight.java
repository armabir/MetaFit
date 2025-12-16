package com.example.metafit_v1.services;

import com.example.metafit_v1.models.MealPlan;
import com.example.metafit_v1.util.ConnectionSingleton;
import java.sql.*;
import java.util.*;

public class MealPlanGeneratorWeight {

    /**
     * Food data structure to hold nutrition info
     */
    private static class Food {
        String name;
        double proteinPer100g;
        double carbPer100g;
        double fatPer100g;
        double fiberPer100g;
        double sugarPer100g;
        double caloriePer100g;
        double maxServingSize;

        Food(String name, double proteinPer100g, double carbPer100g, double fatPer100g,
             double fiberPer100g, double sugarPer100g, double caloriePer100g, double maxServingSize) {
            this.name = name;
            this.proteinPer100g = proteinPer100g;
            this.carbPer100g = carbPer100g;
            this.fatPer100g = fatPer100g;
            this.fiberPer100g = fiberPer100g;
            this.sugarPer100g = sugarPer100g;
            this.caloriePer100g = caloriePer100g;
            this.maxServingSize = maxServingSize;
        }
    }

    /**
     * Macro struct for returning 6 nutrition values
     */
    private static class MacroNutrients {
        double protein, carb, fat, fiber, sugar, calorie;

        MacroNutrients(double protein, double carb, double fat, double fiber, double sugar, double calorie) {
            this.protein = protein;
            this.carb = carb;
            this.fat = fat;
            this.fiber = fiber;
            this.sugar = sugar;
            this.calorie = calorie;
        }
    }

    /**
     * Nutrition goal structure
     */
    private static class NutritionGoal {
        double targetCalories;
        double targetProtein;
        String goalType; // "GAIN" or "LOSS"

        NutritionGoal(double targetCalories, double targetProtein, String goalType) {
            this.targetCalories = targetCalories;
            this.targetProtein = targetProtein;
            this.goalType = goalType;
        }
    }

    /**
     * Generates meal plan based on weight goal
     * @param currentWeight current weight in kg
     * @param targetWeight target weight in kg
     * @param durationInMonths duration in months (1, 3, or 6)
     * @return MealPlan object with 3 combinations aligned with weight goal
     */
    public static MealPlan generateWeightGoalMealPlan(double currentWeight, double targetWeight, int durationInMonths) {
        // Validate duration
        if (durationInMonths != 1 && durationInMonths != 3 && durationInMonths != 6) {
            System.out.println("Invalid duration. Please use 1, 3, or 6 months.");
            return new MealPlan(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        List<Food> foods = fetchFoodsFromDatabase();

        if (foods == null || foods.isEmpty()) {
            System.out.println("No foods found in database");
            return new MealPlan(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        // Calculate nutrition goal
        NutritionGoal goal = calculateNutritionGoal(currentWeight, targetWeight, durationInMonths);

        System.out.println("\n=== Weight Goal Analysis ===");
        System.out.printf("Current Weight: %.1f kg%n", currentWeight);
        System.out.printf("Target Weight: %.1f kg%n", targetWeight);
        System.out.printf("Duration: %d months%n", durationInMonths);
        System.out.printf("Goal Type: %s%n", goal.goalType);
        System.out.printf("Target Daily Calories: %.0f%n", goal.targetCalories);
        System.out.printf("Target Daily Protein: %.1f g%n", goal.targetProtein);
        System.out.println();

        List<String> sat_foods = new ArrayList<>();
        List<String> sat_weights = new ArrayList<>();
        List<String> sat_macros = new ArrayList<>();

        List<String> sun_foods = new ArrayList<>();
        List<String> sun_weights = new ArrayList<>();
        List<String> sun_macros = new ArrayList<>();

        List<String> mon_foods = new ArrayList<>();
        List<String> mon_weights = new ArrayList<>();
        List<String> mon_macros = new ArrayList<>();

        // Generate 3 different combinations based on calorie target
        MacroNutrients greedy_macros = generateGreedyMeal(foods, goal.targetCalories, goal.targetProtein, sat_foods, sat_weights);
        addMacrosToList(sat_macros, greedy_macros);

        MacroNutrients random_macros = generateRandomMeal(foods, goal.targetCalories, goal.targetProtein, sun_foods, sun_weights);
        addMacrosToList(sun_macros, random_macros);

        MacroNutrients balanced_macros = generateBalancedMeal(foods, goal.targetCalories, goal.targetProtein, mon_foods, mon_weights);
        addMacrosToList(mon_macros, balanced_macros);

        return new MealPlan(sat_foods, sun_foods, mon_foods, sat_weights, sun_weights, mon_weights, sat_macros, sun_macros, mon_macros);
    }

    /**
     * Calculate nutrition goal based on weight change and duration
     * Uses calorie surplus/deficit approach
     */
    private static NutritionGoal calculateNutritionGoal(double currentWeight, double targetWeight, int durationInMonths) {
        double weightDifference = Math.abs(targetWeight - currentWeight);
        String goalType = targetWeight > currentWeight ? "GAIN" : "LOSS";

        // Assume 3500 calories = 1 kg weight change
        double totalCalorieChange = weightDifference * 3500;
        double days = durationInMonths * 30; // approximate days
        double dailyCalorieAdjustment = totalCalorieChange / days;

        // Base caloric needs (approximate: 2000 cal/day for average person)
        double baseCalories = 2000;
        double targetCalories = baseCalories + dailyCalorieAdjustment;

        // Protein calculation: 1.6-2.2g per kg body weight for fitness goals
        double averageWeight = (currentWeight + targetWeight) / 2;
        double targetProtein;

        if (goalType.equals("GAIN")) {
            targetProtein = averageWeight * 2.2; // higher for muscle gain
        } else {
            targetProtein = averageWeight * 1.8; // maintain muscle during fat loss
        }

        return new NutritionGoal(targetCalories, targetProtein, goalType);
    }

    /**
     * Fetches all foods from database
     */
    private static List<Food> fetchFoodsFromDatabase() {
        List<Food> foods = new ArrayList<>();
        String query = "SELECT name, protein, carb, fat, fiber, sugar, calorie FROM food";

        try (Connection conn = ConnectionSingleton.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                double protein = rs.getDouble("protein");
                double carb = rs.getDouble("carb");
                double fat = rs.getDouble("fat");
                double fiber = rs.getDouble("fiber");
                double sugar = rs.getDouble("sugar");
                double calorie = rs.getDouble("calorie");

                foods.add(new Food(name, protein, carb, fat, fiber, sugar, calorie, 300.0));
            }

            System.out.println("Loaded " + foods.size() + " foods from database");

        } catch (SQLException e) {
            System.err.println("Database query error: " + e.getMessage());
            e.printStackTrace();
        }

        return foods;
    }

    /**
     * Greedy: High calorie density first while meeting protein target
     */
    private static MacroNutrients generateGreedyMeal(List<Food> foods, double targetCalories, double targetProtein,
                                                     List<String> foodList, List<String> weightList) {
        List<Food> sorted = new ArrayList<>(foods);
        sorted.sort((a, b) -> Double.compare(b.caloriePer100g, a.caloriePer100g));

        double protein = 0, carb = 0, fat = 0, fiber = 0, sugar = 0, calorie = 0;
        int itemCount = 0;

        for (Food food : sorted) {
            if (itemCount >= 8 || calorie >= targetCalories * 0.98) break;

            double remainingCalories = targetCalories - calorie;
            double weightNeeded = (remainingCalories / food.caloriePer100g) * 100;
            double weight = Math.min(weightNeeded, food.maxServingSize);

            if (weight >= 20) {
                double ratio = weight / 100.0;
                protein += ratio * food.proteinPer100g;
                carb += ratio * food.carbPer100g;
                fat += ratio * food.fatPer100g;
                fiber += ratio * food.fiberPer100g;
                sugar += ratio * food.sugarPer100g;
                calorie += ratio * food.caloriePer100g;

                foodList.add(food.name);
                weightList.add(String.format("%.0f g", weight));
                itemCount++;
            }
        }

        System.out.printf("Generated meal with algorithm 'greedy': %.0f calories, %.1fg protein (target: %.0f cal, %.1fg protein)%n",
                calorie, protein, targetCalories, targetProtein);

        return new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
    }

    /**
     * Random: Best of 10 random attempts based on calorie target
     */
    private static MacroNutrients generateRandomMeal(List<Food> foods, double targetCalories, double targetProtein,
                                                     List<String> foodList, List<String> weightList) {
        Random rand = new Random();
        double bestDifference = Double.MAX_VALUE;
        MacroNutrients bestMacros = new MacroNutrients(0, 0, 0, 0, 0, 0);
        List<String> bestFoods = new ArrayList<>();
        List<String> bestWeights = new ArrayList<>();

        for (int attempt = 0; attempt < 10; attempt++) {
            List<Food> shuffled = new ArrayList<>(foods);
            Collections.shuffle(shuffled);

            List<String> tempFoods = new ArrayList<>();
            List<String> tempWeights = new ArrayList<>();
            double protein = 0, carb = 0, fat = 0, fiber = 0, sugar = 0, calorie = 0;
            int itemCount = 0;

            for (Food food : shuffled) {
                if (itemCount >= 6) break;

                double weight = 40 + rand.nextDouble() * (food.maxServingSize - 40);
                double ratio = weight / 100.0;

                protein += ratio * food.proteinPer100g;
                carb += ratio * food.carbPer100g;
                fat += ratio * food.fatPer100g;
                fiber += ratio * food.fiberPer100g;
                sugar += ratio * food.sugarPer100g;
                calorie += ratio * food.caloriePer100g;

                tempFoods.add(food.name);
                tempWeights.add(String.format("%.0f g", weight));
                itemCount++;

                if (calorie >= targetCalories * 0.9) break;
            }

            double difference = Math.abs(calorie - targetCalories);
            if (difference < bestDifference) {
                bestDifference = difference;
                bestFoods = tempFoods;
                bestWeights = tempWeights;
                bestMacros = new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
            }
        }

        foodList.addAll(bestFoods);
        weightList.addAll(bestWeights);

        System.out.printf("Generated meal with algorithm 'random': %.0f calories, %.1fg protein (target: %.0f cal, %.1fg protein)%n",
                bestMacros.calorie, bestMacros.protein, targetCalories, targetProtein);

        return bestMacros;
    }

    /**
     * Balanced: Mix of calorie sources while maintaining protein
     */
    private static MacroNutrients generateBalancedMeal(List<Food> foods, double targetCalories, double targetProtein,
                                                       List<String> foodList, List<String> weightList) {
        Random rand = new Random();
        List<Food> proteinRich = new ArrayList<>();
        List<Food> other = new ArrayList<>();

        for (Food food : foods) {
            if (food.proteinPer100g > 15) {
                proteinRich.add(food);
            } else {
                other.add(food);
            }
        }

        double protein = 0, carb = 0, fat = 0, fiber = 0, sugar = 0, calorie = 0;

        // Add protein-rich foods first (50% of calorie target)
        Collections.shuffle(proteinRich);
        int proteinCount = Math.min(3, proteinRich.size());
        for (int i = 0; i < proteinCount && calorie < targetCalories * 0.5; i++) {
            Food food = proteinRich.get(i);
            double remainingCalories = (targetCalories * 0.5) - calorie;
            double weightNeeded = (remainingCalories / food.caloriePer100g) * 100;
            double weight = Math.min(weightNeeded, food.maxServingSize);

            if (weight >= 30) {
                double ratio = weight / 100.0;
                protein += ratio * food.proteinPer100g;
                carb += ratio * food.carbPer100g;
                fat += ratio * food.fatPer100g;
                fiber += ratio * food.fiberPer100g;
                sugar += ratio * food.sugarPer100g;
                calorie += ratio * food.caloriePer100g;

                foodList.add(food.name);
                weightList.add(String.format("%.0f g", weight));
            }
        }

        // Add complementary foods for remaining calories
        Collections.shuffle(other);
        int complementCount = Math.min(2, other.size());
        for (int i = 0; i < complementCount && calorie < targetCalories * 0.95; i++) {
            Food food = other.get(i);
            double weight = 80 + rand.nextDouble() * 100;
            double ratio = weight / 100.0;
            double foodCalorie = ratio * food.caloriePer100g;

            if (calorie + foodCalorie <= targetCalories * 1.1) {
                protein += ratio * food.proteinPer100g;
                carb += ratio * food.carbPer100g;
                fat += ratio * food.fatPer100g;
                fiber += ratio * food.fiberPer100g;
                sugar += ratio * food.sugarPer100g;
                calorie += foodCalorie;

                foodList.add(food.name);
                weightList.add(String.format("%.0f g", weight));
            }
        }

        System.out.printf("Generated meal with algorithm 'balanced': %.0f calories, %.1fg protein (target: %.0f cal, %.1fg protein)%n",
                calorie, protein, targetCalories, targetProtein);

        return new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
    }

    /**
     * Add individual macro values to list
     */
    private static void addMacrosToList(List<String> macroList, MacroNutrients macros) {
        macroList.add(String.format("%.1f g", macros.protein));
        macroList.add(String.format("%.1f g", macros.carb));
        macroList.add(String.format("%.1f g", macros.fat));
        macroList.add(String.format("%.1f g", macros.fiber));
        macroList.add(String.format("%.1f g", macros.sugar));
        macroList.add(String.format("%.0f cal", macros.calorie));
    }

    // Example usage
    public static void main(String[] args) {
        // Weight Loss Example: 80kg -> 75kg in 3 months
        MealPlan weightLossPlan = generateWeightGoalMealPlan(80, 70, 3);

        System.out.println("\n=== Saturday Meal (Weight Loss Goal) ===");
        printMeal(weightLossPlan.getSatFoodList(), weightLossPlan.getSatWeightList(), weightLossPlan.getSatMarcoWeightList());

        System.out.println("\n=== Sunday Meal ===");
        printMeal(weightLossPlan.getSunFoodList(), weightLossPlan.getSunWeightList(), weightLossPlan.getSunMarcoWeightList());

        System.out.println("\n=== Monday Meal ===");
        printMeal(weightLossPlan.getMonFoodList(), weightLossPlan.getMonWeightList(), weightLossPlan.getMonMarcoWeightList());
    }

    private static void printMeal(List<String> foods, List<String> weights, List<String> macros) {
        for (int i = 0; i < foods.size(); i++) {
            System.out.printf("%s: %s%n", foods.get(i), weights.get(i));
        }
        if (!macros.isEmpty()) {
            System.out.println("Total Macros: P=" + macros.get(0) + " | C=" + macros.get(1) +
                    " | F=" + macros.get(2) + " | Fiber=" + macros.get(3) +
                    " | Sugar=" + macros.get(4) + " | Cal=" + macros.get(5));
        }
    }
}