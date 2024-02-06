package com.nourishnet;

import java.util.ArrayList;

public class LoadingData {
    

    public static void main(String[] args) {
        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("Tom"));
        System.out.println(user.getHasPassword());
        
        ArrayList<Ingredient> ing = ResourceLoader.loadIngredients();
        // debug
        System.out.println(ing.get(0).getName());
        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
    
        // debug
        System.out.println(recipes.get(0).getName());


        recipes.get(0).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipes.get(0).getQuantitiesNames(), ing));

        System.out.println(recipes.get(0).calculateRecipeCalories()); // debug 

        System.out.println(recipes.get(0).getServes()); // debug

        ArrayList<Diet> diets = ResourceLoader.loadDiets();

        System.out.println(diets.get(0).getName()); // debug

        ArrayList<Recipe> recipeExamples = diets.get(0).getExampleRecipes(recipes);
        System.out.println(recipeExamples.size()); // debug

    }



}


