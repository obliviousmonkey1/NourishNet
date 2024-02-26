package com.nourishnet;

import java.util.ArrayList;

public class RecipeTest {

    private static void testRecipeCreation() {
        // Create a sample nutrition object
        Recipe.Nutrition nutrition = new Recipe.Nutrition(200, 10, 5, 20, 10, 5, 15, 2);

        // Create a sample ingredient list
        ArrayList<Recipe.Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Recipe.Ingredient("Flour", 200, "grams"));
        ingredients.add(new Recipe.Ingredient("Sugar", 50, "grams"));

        // Create a sample recipe
        Recipe recipe = new Recipe("Chocolate Cake", new ArrayList<String>(), 30, 60, "Easy", 8, nutrition, "Delicious chocolate cake recipe", ingredients, new ArrayList<String>(), new ArrayList<String>());

        // Assert recipe attributes
        TestingTools.AssertEquals("Chocolate Cake", recipe.getName());
        TestingTools.AssertEquals(30, recipe.getPrepTime());
        TestingTools.AssertEquals(60, recipe.getCookTime());
        TestingTools.AssertEquals("Easy", recipe.getLevel());
        TestingTools.AssertEquals(8, recipe.getServes());
        TestingTools.AssertEquals(nutrition, recipe.getNutrition());
        TestingTools.AssertEquals("Delicious chocolate cake recipe", recipe.getDescription());
        TestingTools.AssertEquals(ingredients, recipe.getIngredients());
    }

    private static void testSettingAndGetters() {
        // Create a sample recipe
        Recipe recipe = new Recipe("", new ArrayList<String>(), 0, 0, "", 0, null, "", new ArrayList<Recipe.Ingredient>(), new ArrayList<String>(), new ArrayList<String>());

        // Test setting and getting recipe name
        recipe.setName("Test Recipe");
        TestingTools.AssertEquals("Test Recipe", recipe.getName());

        // Test setting and getting prep time
        recipe.setPrepTime(20);
        TestingTools.AssertEquals(20, recipe.getPrepTime());

        // Test setting and getting cook time
        recipe.setCookTime(40);
        TestingTools.AssertEquals(40, recipe.getCookTime());

        // Test setting and getting level
        recipe.setLevel("Intermediate");
        TestingTools.AssertEquals("Intermediate", recipe.getLevel());

        // Test setting and getting serves
        recipe.setServes(4);
        TestingTools.AssertEquals(4, recipe.getServes());

        // Test setting and getting description
        recipe.setDescription("This is a test recipe description.");
        TestingTools.AssertEquals("This is a test recipe description.", recipe.getDescription());

        // Test setting and getting ingredients
        ArrayList<Recipe.Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Recipe.Ingredient("Test Ingredient", 100, "grams"));
        recipe.setIngredients(ingredients);
        TestingTools.AssertEquals(ingredients, recipe.getIngredients());
    }

    public static void Entry() {
        testRecipeCreation();
        testSettingAndGetters();
    }
}

