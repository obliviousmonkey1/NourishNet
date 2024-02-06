package com.nourishnet;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    private static User user = new User();
    private static ArrayList<Ingredient> ingredientHolder = new ArrayList<Ingredient>();
    private static ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
    private static ArrayList<Diet> dietHolder = new ArrayList<Diet>();

    public static void main( String[] args )
    {
        loadData();
        if(LogIn.getNumberOfUserProfiles()!=0){
            System.out.println("Users already in system"); // debug
            // go to the user selection screen
        }else{
            System.out.println("No users in the system"); // debug 
            // go to the set up account screen 
        }
    }

    // load the data 
    private static void loadData(){
        dietHolder = ResourceLoader.loadDiets();

        System.out.println(dietHolder.get(0).getName());  // debug

        recipeHolder = ResourceLoader.loadRecipes();
    
        System.out.println(recipeHolder.get(0).getName());        // debug

       
    }
}
