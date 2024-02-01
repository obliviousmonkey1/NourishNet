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

        // debug
        System.out.println(recipes.get(0).getTempIngredients());

        recipes.get(0).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipes.get(0).getTempIngredients(), ing));

        // debug
        System.out.println(recipes.get(0).calculateRecipeCalories());

    }



}


