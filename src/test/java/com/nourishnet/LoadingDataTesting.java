package com.nourishnet;

import java.util.ArrayList;

public class LoadingDataTesting {
    

    public static void main(String[] args) {
        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("Tom"));
        System.out.println(user.getHasPassword());
        
        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
    
        System.out.println(recipes.get(0).getName());        // debug

        System.out.println(recipes.get(0).getServes()); // debug

        ArrayList<Diet> diets = ResourceLoader.loadDiets();

        System.out.println(diets.get(0).getName()); // debug

        ArrayList<Recipe> recipeExamples = diets.get(0).getExampleRecipes(recipes);
        System.out.println(recipeExamples.size()); // debug

    }



}
