package com.nourishnet;

import java.util.ArrayList;

public class SaveTesting {
    public static void main(String[] args) {
        userSaving();
        recipeSaving();
    }

    public static void userSaving(){
        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("TestUser"));
        System.out.println(user.getUsername());

        System.out.println("Users age : " + user.getAge());

        user.setAge(user.getAge()+2);

        System.out.println("Users new age : " + user.getAge());

        SerializeJsonData.serializeUser(user, LogIn.getUserJsonPath("TestUser"));
    }

    public static void recipeSaving(){

        ArrayList<Ingredient> ing = ResourceLoader.loadIngredients();
        
        
        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
    
        recipes.get(0).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipes.get(0).getTempIngredients(), ing));

        recipes.get(0).setDescription("hello world");

        SerializeJsonData.serializeRecipes(recipes, Pointers.tempName + "/Recipes.json");
    }

}
