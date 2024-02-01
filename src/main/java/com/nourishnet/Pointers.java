package com.nourishnet;

import java.util.ArrayList;

public class Pointers {
    public static String usersPath = "/Data/Users";
    public static String tempName = "Data/tempName";
    public static String recipeImagePath = "Data/tempName/recipeImages";
    public static String ingredientImagePath = "Data/tempName/ingredientImages";
    public static String dietImagePath = "Data/tempName/dietImages";
    public static String userDir = System.getProperty("user.dir");
    public static ArrayList<Ingredient> ingredientHolder;

    public void setIngredientHolder(ArrayList<Ingredient> newIngredientHolder){
        ingredientHolder = newIngredientHolder;
    }
}
