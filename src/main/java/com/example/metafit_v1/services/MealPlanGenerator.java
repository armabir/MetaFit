package com.example.metafit_v1.services;

import com.example.metafit_v1.models.MealPlan;
import com.example.metafit_v1.util.ConnectionSingleton;
import java.sql.*;
import java.util.*;

public class MealPlanGenerator {

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

        double[] toArray() {
            return new double[]{protein, carb, fat, fiber, sugar, calorie};
        }
    }

    /**
     * Generates a meal plan with 3 different combinations meeting target protein
     */
    public static MealPlan generateMealPlan(double targetProtein) {
        List<Food> foods = fetchFoodsFromDatabase();

        if (foods == null || foods.isEmpty()) {
            System.out.println("No foods found in database");
            return new MealPlan(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        List<String> sat_foods = new ArrayList<>();
        List<String> sat_weights = new ArrayList<>();
        List<String> sat_macros = new ArrayList<>();

        List<String> sun_foods = new ArrayList<>();
        List<String> sun_weights = new ArrayList<>();
        List<String> sun_macros = new ArrayList<>();

        List<String> mon_foods = new ArrayList<>();
        List<String> mon_weights = new ArrayList<>();
        List<String> mon_macros = new ArrayList<>();

        // Generate 3 different combinations
        MacroNutrients random_macros = generateGreedyMeal(foods, targetProtein, sat_foods, sat_weights);
        addMacrosToList(sat_macros, random_macros);

        MacroNutrients  greedy_macros = generateRandomMeal(foods, targetProtein, sun_foods, sun_weights);
        addMacrosToList(sun_macros, greedy_macros);

        MacroNutrients balanced_macros = generateBalancedMeal(foods, targetProtein, mon_foods, mon_weights);
        addMacrosToList(mon_macros, balanced_macros);

        return new MealPlan(sat_foods, sun_foods, mon_foods, sat_weights, sun_weights, mon_weights, sat_macros, sun_macros, mon_macros);
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
     * Greedy: High protein density first
     */
    private static MacroNutrients generateGreedyMeal(List<Food> foods, double targetProtein,
                                                     List<String> foodList, List<String> weightList) {
        List<Food> sorted = new ArrayList<>(foods);
        sorted.sort((a, b) -> Double.compare(b.proteinPer100g, a.proteinPer100g));

        double protein = 0, carb = 0, fat = 0, fiber = 0, sugar = 0, calorie = 0;
        int itemCount = 0;

        for (Food food : sorted) {
            if (itemCount >= 8 || protein >= targetProtein * 0.98) break;

            double remainingProtein = targetProtein - protein;
            double weightNeeded = (remainingProtein / food.proteinPer100g) * 100;
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

        System.out.printf("Generated meal with algorithm 'greedy': %.2fg protein, %.2fg carbs, %.2fg fats, %.2fg fiber, %.2fg sugar, %.0f calories (target: %.2fg protein)%n",
                protein, carb, fat, fiber, sugar, calorie, targetProtein);

        return new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
    }

    /**
     * Random: Best of 10 random attempts
     */
    private static MacroNutrients generateRandomMeal(List<Food> foods, double targetProtein,
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

                if (protein >= targetProtein * 0.9) break;
            }

            double difference = Math.abs(protein - targetProtein);
            if (difference < bestDifference) {
                bestDifference = difference;
                bestFoods = tempFoods;
                bestWeights = tempWeights;
                bestMacros = new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
            }
        }

        foodList.addAll(bestFoods);
        weightList.addAll(bestWeights);

        System.out.printf("Generated meal with algorithm 'random': %.2fg protein, %.2fg carbs, %.2fg fats, %.2fg fiber, %.2fg sugar, %.0f calories (target: %.2fg protein)%n",
                bestMacros.protein, bestMacros.carb, bestMacros.fat, bestMacros.fiber, bestMacros.sugar, bestMacros.calorie, targetProtein);

        return bestMacros;
    }

    /**
     * Balanced: Mix of protein-rich and carb/fat sources
     */
    private static MacroNutrients generateBalancedMeal(List<Food> foods, double targetProtein,
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

        // Add protein-rich foods
        Collections.shuffle(proteinRich);
        int proteinCount = Math.min(3, proteinRich.size());
        for (int i = 0; i < proteinCount && protein < targetProtein * 0.7; i++) {
            Food food = proteinRich.get(i);
            double remainingProtein = targetProtein - protein;
            double weightNeeded = (remainingProtein * 0.6 / food.proteinPer100g) * 100;
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

        // Add complementary foods
        Collections.shuffle(other);
        int complementCount = Math.min(2, other.size());
        for (int i = 0; i < complementCount && protein < targetProtein * 0.95; i++) {
            Food food = other.get(i);
            double weight = 80 + rand.nextDouble() * 100;
            double ratio = weight / 100.0;
            double foodProtein = ratio * food.proteinPer100g;

            if (protein + foodProtein <= targetProtein * 1.1) {
                protein += foodProtein;
                carb += ratio * food.carbPer100g;
                fat += ratio * food.fatPer100g;
                fiber += ratio * food.fiberPer100g;
                sugar += ratio * food.sugarPer100g;
                calorie += ratio * food.caloriePer100g;

                foodList.add(food.name);
                weightList.add(String.format("%.0f g", weight));
            }
        }

        System.out.printf("Generated meal with algorithm 'balanced': %.2fg protein, %.2fg carbs, %.2fg fats, %.2fg fiber, %.2fg sugar, %.0f calories (target: %.2fg protein)%n",
                protein, carb, fat, fiber, sugar, calorie, targetProtein);

        return new MacroNutrients(protein, carb, fat, fiber, sugar, calorie);
    }

    /**
     * Format macros for display
     */
    private static String formatMacros(MacroNutrients macros) {
        return String.format("P: %.1fg | C: %.1fg | F: %.1fg | Fiber: %.1fg | Sugar: %.1fg | Cal: %.0f",
                macros.protein, macros.carb, macros.fat, macros.fiber, macros.sugar, macros.calorie);
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
        macroList.add("");
        macroList.add(String.format("%.0f cal", macros.calorie));
    }

    public static void main(String[] args) {
        MealPlan plan = generateMealPlan(100);

        System.out.println("\n=== Saturday Meal ===");
        printMeal(plan.getSatFoodList(), plan.getSatWeightList(), plan.getSatMarcoWeightList());

        System.out.println("\n=== Sunday Meal ===");
        printMeal(plan.getSunFoodList(), plan.getSunWeightList(), plan.getSunMarcoWeightList());

        System.out.println("\n=== Monday Meal ===");
        printMeal(plan.getMonFoodList(), plan.getMonWeightList(), plan.getMonMarcoWeightList());
    }

    private static void printMeal(List<String> foods, List<String> weights, List<String> macros) {
        for (int i = 0; i < foods.size(); i++) {
            System.out.printf("%s: %s%n", foods.get(i), weights.get(i));
        }
        if (!macros.isEmpty()) {
            System.out.println("Total Macros: " + macros.get(0));
        }
    }
}