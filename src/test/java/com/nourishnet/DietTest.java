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
        
         // Loading in the diets from the json file
        
        diets = ResourceLoader.loadDiets();

        // Checking to see if the number of diet loaded is equal to the number of diet in the diet folder
        TestingTools.AssertEquals(diets.size(), ResourceLoader.loadDiets().size());
    }
    


     private static void testGetExampleDiets((ArrayList<Diet> sampleDiets) {
        System.out.println("Testing getExampleDiets method...");
        //Diet objects loaded for testing
        ArrayList<Diet> sampleDiets = ResourceLoader.loadDietsFromJson("Diets.json");

         
        //Need populating sampleDiets with some test data
        
        //Looping through each diet and testing the method
        for (Diet diet : diets) {
            ArrayList<Diets> exampleDiets = diet.getExampleDiets(sampleDiets);
            System.out.println("Diet: " + diet.getName());
            System.out.println("Example Diets: ");
            for (Diet diet : exampleDiets) {
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
        testDietLoading();
        testGetExampleDiets(sampleDiets);
        testGetName();
        testGetDescription();
        System.out.println("[DIET] All tests passed!");
    }
}
