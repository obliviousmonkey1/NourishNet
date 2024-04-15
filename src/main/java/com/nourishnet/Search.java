package com.nourishnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Search {
    
    // show all recipes is a button that allows the user to toggle whether they want to see all recipes or just the ones that correlate to their diet
    public static ArrayList<Recipe> getRecipeSearchResults(String query, ArrayList<Recipe> recipeHolder, ArrayList<Integer> userSavedRecipes, String userSavedDiet, Boolean showAllRecipes) {
        ArrayList<Recipe> returnedRecipes = new ArrayList<>();
        Set<Recipe> uniqueRecipes = new HashSet<>();
    
        for (Recipe recipe : recipeHolder) {
            // Check if query matches tags, name, level, or diet
            if (matchesQuery(recipe, query) && (showAllRecipes || sameDiet(userSavedDiet, recipe.getDiet()))) {
                uniqueRecipes.add(recipe);
            }
        }
    
        returnedRecipes.addAll(uniqueRecipes);
        return returnedRecipes;
    }

    private static boolean matchesQuery(Recipe recipe, String query) {
        String lowerCaseQuery = query.toLowerCase();
        return recipe.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(lowerCaseQuery))
                || recipe.getName().toLowerCase().contains(lowerCaseQuery)
                || recipe.getLevel().toLowerCase().contains(lowerCaseQuery)
                || recipe.getDiet().stream().anyMatch(diet -> diet.toLowerCase().contains(lowerCaseQuery));
    }
    
    public static Boolean sameDiet(String userDietPlan, ArrayList<String> recipeDiets) {
        return recipeDiets.stream().anyMatch(diet -> userDietPlan.equalsIgnoreCase(diet));
    }

    // search over the recipes and check if any ingredients and amounts in it 90% freshhold 
    // stored as a tuple with the recipe and the percentage of ingredients that match
    public static ArrayList<Recipe> getIngredientsSearch(ArrayList<Recipe> recipeHolder, ArrayList<DataStructures.StringIntPair> ingredientQuery, Boolean showAllRecipes , String userDietPlan) {
        ArrayList<Recipe> returnedRecipes = new ArrayList<>();
        Set<Recipe> uniqueRecipes = new HashSet<>();
    
        for (Recipe recipe : recipeHolder) {
            int count = 0;
            System.out.println(recipe.getName()); // debug
            for (int i = 0; i < ingredientQuery.size(); i++) {
                for (int j = 0; j < recipe.getIngredients().size(); j++) {
                    if (ingredientQuery.get(i).getText().equalsIgnoreCase(recipe.getIngredients().get(j).getIngredientName())) {
                        if (ingredientQuery.get(i).getNumber() >= recipe.getIngredients().get(j).getQuantity()) {
                            count++;
                        }
                    }
                }
            }
            System.out.println(count); // debug
            System.out.println("Calculation : " + (count >= ingredientQuery.size() * 0.8)); // debug
            if (count >= ingredientQuery.size() * 0.8 && (showAllRecipes || sameDiet(userDietPlan, recipe.getDiet()))) {
                uniqueRecipes.add(recipe);
            }
        }
    
        returnedRecipes.addAll(uniqueRecipes); // Add unique recipes to the list
        return returnedRecipes;
    }
    
       
}    
    