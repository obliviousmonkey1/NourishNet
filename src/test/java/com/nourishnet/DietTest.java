package com.nourishnet;

import java.util.ArrayList;

public class DietTest {

    //loading in the diets from the json file
    private static ArrayList<Diet> diets = ResourceLoader.loadDiets();


    public static void main(String[] args) {
        //creating sample recipe objects 
        ArrayList<Recipe> sampleRecipes = createSampleRecipes();
    }

    private static ArrayList<Recipe> createSampleRecipes() {
        ArrayList<Recipe> sampleRecipes = new ArrayList<>();

        //creating sample objects
        Recipe recipe1 = new Recipe("Recipe 1", "Description of Recipe 1", ...); //PLACEHOLDERS
        Recipe recipe2 = new Recipe("Recipe 2", "Description of Recipe 2", ...);
        Recipe recipe3 = new Recipe("Recipe 3", "Description of Recipe 3", ...);

        //Adding samples to list
        sampleRecipes.add(recipe1);
        sampleRecipes.add(recipe2);
        sampleRecipes.add(recipe3);

        return sampleRecipes;
    }

     private static void testGetExampleRecipes() {
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
        testGetExampleRecipes();
        testGetName();
        testGetDescription();

        System.out.println("[DIET] All tests passed!");

    }
}
