package com.nourishnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Search {
    
    // make it so they are specific to the diet selected unless selected otherwise
    public static ArrayList<Recipe> getRecipeSearchResults(String query, ArrayList<Recipe> recipeHolder, ArrayList<Integer> userSavedRecipes, String userSavedDiet) {
        ArrayList<Recipe> returnedRecipes = new ArrayList<>();
        Set<Recipe> uniqueRecipes = new HashSet<>();

        for (int i = 0; i < recipeHolder.size(); i++) {
            ArrayList<String> tags = recipeHolder.get(i).getTags();
    
            for (String tag : tags) {
                System.out.println(tag); // debug
                if (tag.toLowerCase().contains(query.toLowerCase())) {
                    uniqueRecipes.add(recipeHolder.get(i));
                    break;
                }
            }

            if(recipeHolder.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                uniqueRecipes.add(recipeHolder.get(i));
            }

            if(recipeHolder.get(i).getLevel().toLowerCase().contains(query.toLowerCase())){
                uniqueRecipes.add(recipeHolder.get(i));

            }
        }
        returnedRecipes.addAll(uniqueRecipes);
    
        return returnedRecipes;
    }

    
    
}
