//---------------------------------------------------------------
// Created on 8/04/2024 by Declan Roe 
// 
// This class is to test the Diet class in main, having functions 
// to test different scenarios applicable to the class
//
// Last Edit by Declan on 16/4/2024
//---------------------------------------------------------------


package com.nourishnet;

import java.util.ArrayList;




public class DietTest {

    // Loading in the diets from the json file
    private static ArrayList<Diet> diets = ResourceLoader.loadDiets();

    private static void testDietLoading() {
        
     

        // Checking to see if the number of diet loaded is equal to the number of diet in the diet folder
        TestingTools.AssertEquals(diets.size(), 7);
    }
    


    private static void testGetDietsNames() {
      

        // Testing the getExampleDiets method to ensure that the correct diets are returned
        TestingTools.AssertEquals(diets.get(0).getName(), "Keto");
        TestingTools.AssertEquals(diets.get(1).getName(), "Mediterranean");
        TestingTools.AssertEquals(diets.get(2).getName(), "Paleo");
        TestingTools.AssertEquals(diets.get(3).getName(), "Vegan");
        TestingTools.AssertEquals(diets.get(4).getName(), "Vegetarian");
        TestingTools.AssertEquals(diets.get(5).getName(), "Pescatarian");
    }


    //methods for Name and Description to showcase use of DRY and KISS principles
    private static void testGetNameAndDescription() {
        TestingTools.AssertEquals(diets.get(0).getDescription(), "The ketogenic diet, known as keto, promotes a metabolic state called ketosis by limiting carbohydrates and increasing fat consumption, encouraging the body to burn fat for energy. Primarily used for weight loss, the diet restricts high-carb foods and sugars, emphasizing meat, fish, eggs, dairy, healthy oils, and low-carb vegetables, and it shows promise in managing conditions like epilepsy and type 2 diabetes.");
        TestingTools.AssertEquals(diets.get(1).getDescription(), "The Mediterranean diet is a dietary pattern based on the traditional eating habits of Mediterranean countries, emphasizing fruits, vegetables, whole grains, nuts, seeds, olive oil, and moderate amounts of fish and poultry while limiting red meat and sweets. Linked with various health benefits, including reduced risk of heart disease and cancer, it promotes overall well-being and longevity.");
        TestingTools.AssertEquals(diets.get(2).getDescription(), "The Paleo diet, inspired by ancient dietary patterns, focuses on consuming foods presumed to be available during the Paleolithic era, such as meats, fish, fruits, and vegetables, while excluding grains, dairy, and processed foods. Advocates believe it may promote weight loss and improve overall health by mimicking the presumed eating habits of early humans.");
        TestingTools.AssertEquals(diets.get(3).getDescription(), "Veganism encompasses both dietary and lifestyle choices that reject the use of animal products for ethical, environmental, and health reasons, extending beyond food to include clothing, cosmetics, and entertainment. Vegans adhere to a plant-based diet and advocate for cruelty-free practices, driven by concerns about animal welfare, environmental sustainability, and personal health, aiming to minimize their ecological footprint and promote a more compassionate and sustainable world.");
        TestingTools.AssertEquals(diets.get(4).getDescription(), "A vegetarian diet excludes meat, poultry, and seafood but typically includes plant-based foods like fruits, vegetables, grains, legumes, nuts, and seeds. It can be further categorized into types like lacto-ovo vegetarian, lacto-vegetarian, ovo-vegetarian, vegan, and pescatarian, each with varying exclusions and inclusions of animal-derived products.");
        TestingTools.AssertEquals(diets.get(5).getDescription(), "A pescatarian diet involves consuming fish and seafood while avoiding other types of meat. It combines the health benefits associated with plant-based diets with the inclusion of fish as a source of protein and essential nutrients.");

    }

    public static void Entry() {
        testDietLoading();
        testGetDietsNames();
        testGetNameAndDescription();
        System.out.println("[DIETS] All tests passed!");

    }
   
}
