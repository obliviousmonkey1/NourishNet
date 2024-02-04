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
        ingredientHolder = ResourceLoader.loadIngredients();

        System.out.println(ingredientHolder.get(0).getName());  // debug

        recipeHolder = ResourceLoader.loadRecipes();
    
        System.out.println(recipeHolder.get(0).getName());        // debug

        for (int i = 0; i< recipeHolder.size(); i++){
            recipeHolder.get(i).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipeHolder.get(i).getQuantitiesNames(), ingredientHolder));
        }

        System.out.println(recipeHolder.get(0).calculateRecipeCalories());   // debug

    }
}
