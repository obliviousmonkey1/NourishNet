//---------------------------------------------------------------
// Created on 8/04/2024 by Declan Roe 
// 
// This class is to test the Diet class in main, having functions 
// to test different scenarios applicable to the class
//
//---------------------------------------------------------------


package com.nourishnet;

import java.util.ArrayList;

public class DietTest {

    private static void testDietLoading() {
        
         // Loading in the diets from the json file
        
        private static ArrayList<Diet> diets = ResourceLoader.loadDiets();

        // Checking to see if the number of recipes loaded is equal to the number of recipes in the recipes folder
        TestingTools.AssertEquals(recipes.size(), ResourceLoader.loadDiets().size());
    }


    
   
    
    public static void Entry() {
        // Load recipes from JSON file
        
        ArrayList<Recipe> sampleRecipes = RecipeLoader.loadRecipesFromJson("Recipes.json");
        
        testGetExampleRecipes(sampleRecipes);
        testGetName();
        testGetDescription();
        System.out.println("[DIET] All tests passed!");
    }
       
    


     private static void testGetExampleRecipes((ArrayList<Recipe> sampleRecipes) {
        System.out.println("Testing getExampleRecipes method...");
        //recipe objects loaded for testing
        ArrayList<Recipe> sampleRecipes = new ArrayList<>();

         
        //need populating sampleRecipes with some test data
        
        //looping through each diet and testing the method
        for (Diet diet : diets) {
            ArrayList<Recipe> exampleRecipes = diet.getExampleRecipes(sampleRecipes);
            System.out.println("Diet: " + diet.getName());
            System.out.println("Example Recipes: ");
            for (Recipe recipe : exampleRecipes) {
                System.out.println("- " + recipe.getName());
            }
            System.out.println("---------------");
        }
        System.out.println("getExampleRecipes method tested successfully.");
    }

     private static void testGetName() {
        System.out.println("Testing getName method...");
        //looping through each diet and test the method
        for (Diet diet : diets) {
            System.out.println("Diet Name: " + diet.getName());
            System.out.println("---------------");
        }
        System.out.println("getName method tested successfully.");
    }

    private static void testGetDescription() {
        System.out.println("Testing getDescription method...");
        //loop through each diet and test the method
        for (Diet diet : diets) {
            System.out.println("Diet: " + diet.getName());
            System.out.println("Description: " + diet.getDescription());
            System.out.println("---------------");
        }
        System.out.println("getExampleRecipes method tested successfully.");
}


     public static void Entry() {
        testDietLoading();
        testGetExampleRecipes();
        testGetName();
        testGetDescription();

        System.out.println("[DIET] All tests passed!");

    }
}
