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

        // Checking to see if the number of diet loaded is equal to the number of diet in the diet folder
        TestingTools.AssertEquals(recipes.size(), ResourceLoader.loadDiets().size());
    }


    
   
    
    public static void Entry() {
        // Load diets from JSON file
        
        ArrayList<Diet> sampleDiets = RecipeLoader.loadDietsFromJson("Diets.json");
        
        testGetExampleDiets(sampleDiets);
        testGetName();
        testGetDescription();
        System.out.println("[DIET] All tests passed!");
    }
       
    


     private static void testGetExampleDiets((ArrayList<Diet> sampleDiet) {
        System.out.println("Testing getExampleDiets method...");
        //Diet objects loaded for testing
        ArrayList<Diets> sampleDiets = new ArrayList<>();

         
        //Need populating sampleDiets with some test data
        
        //Looping through each diet and testing the method
        for (Diet diet : diets) {
            ArrayList<Recipe> exampleDiets = diet.getExampleDiets(sampleDiets);
            System.out.println("Diet: " + diet.getName());
            System.out.println("Example Diets: ");
            for (Diet diet : exampleDiets) {
                System.out.println("- " + recipe.getName());
            }
            System.out.println("---------------");
        }
        System.out.println("getExampleDiets method tested successfully.");
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
        //Loop through each diet and test the method
        for (Diet diet : diets) {
            System.out.println("Diet: " + diet.getName());
            System.out.println("Description: " + diet.getDescription());
            System.out.println("---------------");
        }
        System.out.println("getExampleRecipes method tested successfully.");
}


     public static void Entry() {
        testDietLoading();
        testGetExampleDiets();
        testGetName();
        testGetDescription();

        System.out.println("[DIET] All tests passed!");

    }
}
