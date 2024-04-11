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

        // Check if the number of recipes loaded is equal to the number of recipes in the recipes folder
        TestingTools.AssertEquals(recipes.size(), ResourceLoader.loadRecipes().size());
    }

    private static void testRecipeGetters() {
        // Create a sample recipe
        Recipe recipe = ResourceLoader.loadRecipes().get(0);

        // Test getting recipe name
        TestingTools.AssertEquals("Salt and Pepper Tofu", recipe.getName());

        // Test getting diet
        TestingTools.AssertEquals("Vegan", recipe.getDiet().get(0));
        TestingTools.AssertEquals("Vegetarian", recipe.getDiet().get(1));

        // Test getting prep time
        TestingTools.AssertEquals(15, recipe.getPrepTime());

        // Test getting cook time
        TestingTools.AssertEquals(20, recipe.getCookTime());

        // Test getting level
        TestingTools.AssertEquals("Easy", recipe.getLevel());

        // Test getting serves
        TestingTools.AssertEquals(4, recipe.getServes());

        // Test getting description
        TestingTools.AssertEquals("Crispy and flavorful tofu seasoned with salt and pepper.", recipe.getDescription());

        // Test getting instructions
        TestingTools.AssertEquals("Cut the tofu into cubes and toss in cornflour until coated.", recipe.getInstructions().get(0));
        TestingTools.AssertEquals("Heat the oil in a large non-stick frying pan over medium-high heat.", recipe.getInstructions().get(1));
        TestingTools.AssertEquals("Fry the tofu cubes in batches for 8-10 minutes, turning occasionally until golden and crispy.", recipe.getInstructions().get(2));
        TestingTools.AssertEquals("Remove tofu from the pan and drain excess oil on paper towels.", recipe.getInstructions().get(3));
        TestingTools.AssertEquals("Season with salt and plenty of ground black pepper.", recipe.getInstructions().get(4));
        TestingTools.AssertEquals("Garnish with sliced spring onions and serve hot.", recipe.getInstructions().get(5));

        // Test getting tags
        TestingTools.AssertEquals("Dairy-free", recipe.getTags().get(0));
        TestingTools.AssertEquals("Egg-free", recipe.getTags().get(1));

    }

    public static void testRecipeNutritionGetters(){
        // Load in a recipe
        Recipe recipe = ResourceLoader.loadRecipes().get(0);

        // Test getting calories
        TestingTools.AssertEquals(610.0, recipe.getNutrition().getCalories());

        // Test getting fat
        TestingTools.AssertEquals(29.0, recipe.getNutrition().getFat());

        // Test getting saturates
        TestingTools.AssertEquals(4.0, recipe.getNutrition().getSaturates());

        // Test getting carbs
        TestingTools.AssertEquals(62.0, recipe.getNutrition().getCarbs());

        // Test getting sugars
        TestingTools.AssertEquals(14.0, recipe.getNutrition().getSugars());

        // Test getting fibre
        TestingTools.AssertEquals(13.0, recipe.getNutrition().getFibre());

        // Test getting protein
        TestingTools.AssertEquals(19.0, recipe.getNutrition().getProtein());

        // Test getting salt
        TestingTools.AssertEquals(1.6, recipe.getNutrition().getSalt());

    }

    public static void testRecipeIngredientGetters() {
        // Load in a recipe
        Recipe recipe = ResourceLoader.loadRecipes().get(0);

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
        testRecipeNutritionGetters();
        testRecipeIngredientGetters();

        System.out.println("[RECIPES] All tests passed!");

    }
}

