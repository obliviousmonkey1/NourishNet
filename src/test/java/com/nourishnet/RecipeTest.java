//-----------------------------------------------------------------------------------
// Created on 22/2/2024 by Declan with contributions by Tom 
// 
// This class is to test the Recipe class in main, having functions to test different 
// functions/features involved in the Recipe class
//
//-----------------------------------------------------------------------------------


package com.nourishnet;

import java.util.ArrayList;

public class RecipeTest {

    private static void testRecipeLoading() {
        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();

        // Checking to see if the number of recipes loaded is equal to the number of recipes in the recipes folder
        TestingTools.AssertEquals(recipes.size(), ResourceLoader.loadRecipes().size());
    }

    private static void testRecipeGetters() {
        // Creating a sample recipe
        Recipe recipe = ResourceLoader.loadRecipes().get(0);    
        // Testing recipe properties getters
        TestingTools.AssertEquals("Salt and Pepper Tofu", recipe.getName());
        TestingTools.AssertEquals(Arrays.asList("Vegan", "Vegetarian"), recipe.getDiet());
        TestingTools.AssertEquals(15, recipe.getPrepTime());
        TestingTools.AssertEquals(20, recipe.getCookTime());
        TestingTools.AssertEquals("Easy", recipe.getLevel());
        TestingTools.AssertEquals(4, recipe.getServes());
        TestingTools.AssertEquals("Crispy and flavorful tofu seasoned with salt and pepper.", recipe.getDescription());
        TestingTools.AssertEquals(Arrays.asList(
            "Cut the tofu into cubes and toss in cornflour until coated.",
            "Heat the oil in a large non-stick frying pan over medium-high heat.",
            "Fry the tofu cubes in batches for 8-10 minutes, turning occasionally until golden and crispy.",
            "Remove tofu from the pan and drain excess oil on paper towels.",
            "Season with salt and plenty of ground black pepper.",
            "Garnish with sliced spring onions and serve hot."), recipe.getInstructions());
        TestingTools.AssertEquals(Arrays.asList("Dairy-free", "Egg-free"), recipe.getTags());
}

    }


    public static void testRecipeNutritionIngredientGetters() {
        // Loading in a recipe
        Recipe recipe = ResourceLoader.loadRecipes().get(0);

        // Testing both Nutrition and Ingredient in the one function which helps adhere to the DRY principle 
        // First we are testing the Nutrition getters

        // Testing calories getter
        TestingTools.AssertEquals(610.0, recipe.getNutrition().getCalories());
        // Testing fat getter
        TestingTools.AssertEquals(29.0, recipe.getNutrition().getFat());
        // Testing saturates getter
        TestingTools.AssertEquals(4.0, recipe.getNutrition().getSaturates());
        // Testing carbs getter 
        TestingTools.AssertEquals(62.0, recipe.getNutrition().getCarbs());
        // Testing sugars getter 
        TestingTools.AssertEquals(14.0, recipe.getNutrition().getSugars());
        // Testing fibre getters 
        TestingTools.AssertEquals(13.0, recipe.getNutrition().getFibre());
        // Testing protein getters 
        TestingTools.AssertEquals(19.0, recipe.getNutrition().getProtein());
        // Testing salt getters 
        TestingTools.AssertEquals(1.6, recipe.getNutrition().getSalt());

        // Then testing the Ingredient getters

        // Test getting ingredient name
        TestingTools.AssertEquals("Tofu", recipe.getIngredients().get(0).getIngredientName());
        // Test getting ingredient quantity
        TestingTools.AssertEquals(400, recipe.getIngredients().get(0).getQuantity());
        // Test getting ingredient unit
        TestingTools.AssertEquals("grams, drained and pressed", recipe.getIngredients().get(0).getMeasurement());
    }

    public static void Entry() {
        testRecipeLoading();
        testRecipeGetters();
        testRecipeNutritionIngredientGetters();
        System.out.println("[RECIPES] All tests passed!");

    }
}

