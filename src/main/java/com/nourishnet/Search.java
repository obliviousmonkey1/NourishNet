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
}    
    