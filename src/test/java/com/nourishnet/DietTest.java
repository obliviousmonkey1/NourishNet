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

    
    private static ArrayList<Diet> diets;

    private static void testDietLoading() {
        
        //Loading in the diets from the json file while also showing use of encapsulation in 
        // being only accessed in the DietTest class which is helping data integritiy 
        diets = ResourceLoader.loadDiets();

        // Checking to see if the number of diet loaded is equal to the number of diet in the diet folder
        TestingTools.AssertEquals(diets.size(), ResourceLoader.loadDiets().size());
    }
    


    private static void testGetExampleDiets(ArrayList<Diet> sampleDiets) {
        
        System.out.println("Testing getExampleDiets method...");
       
        //Looping through each diet and testing the method along with giving the diet recipe examples
        for (Diet diet : diets) {
            ArrayList<Recipe> exampleRecipes = diet.getExampleRecipes(sampleDiets);
            System.out.println("Diet: " + diet.getName());
            System.out.println("Example Recipes: ");
            
            for (Recipe recipe : exampleRecipes) {
            System.out.println("- " + recipe.getName());
        }
        System.out.println("---------------");
    }
    System.out.println("getExampleDiets method tested successfully.");
}



      private static void testGetNameAndDescription() {
        System.out.println("Testing getName and getDescription methods...");
        // Loop through each diet and test the methods for Name and Description to showcase use of DRY and KISS principles
        for (Diet diet : diets) {
            System.out.println("Diet Name: " + diet.getName());
            System.out.println("Description: " + diet.getDescription());
            System.out.println("---------------");
        }
        System.out.println("getName and getDescription methods tested successfully.");
    }
    
      public static void Entry() {
        
        // Loading diets from JSON file
        diets = ResourceLoader.loadDiets();
        
        // Loading sample diets from JSON file
        ArrayList<Diet> sampleDiets = ResourceLoader.loadDietsFromJson("Diets.json");

        testDietLoading();
        testGetExampleDiets(sampleDiets);
        testGetName();
        testGetDescription();
        System.out.println("[DIET] All tests passed!");
    }
}
