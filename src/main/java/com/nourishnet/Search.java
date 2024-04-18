package com.nourishnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

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


    public static ArrayList<Recipe> getIngredientsSearch(ArrayList<Recipe> recipeHolder, ArrayList<DataStructures.StringIntPair> ingredientQuery, Boolean showAllRecipes, String userDietPlan) {
        ArrayList<Recipe> returnedRecipes = new ArrayList<>();
        Set<Recipe> uniqueRecipes = new HashSet<>();

        for (Recipe recipe : recipeHolder) {
            int count = 0;
            System.out.println(recipe.getName()); // debug
            for (int i = 0; i < ingredientQuery.size(); i++) {
                for (int j = 0; j < recipe.getIngredients().size(); j++) {
                    
                    if (ingredientQuery.get(i).getText().equalsIgnoreCase(recipe.getIngredients().get(j).getIngredientName())) {
                        System.out.println(ingredientQuery.get(i).getNumber() + " " + recipe.getIngredients().get(j).getQuantity()); // debug
                        if(ingredientQuery.get(i).getNumber() == 0 && recipe.getIngredients().get(j).getQuantity() == null){
                            count++;
                        }
                        else if (ingredientQuery.get(i).getNumber() >= recipe.getIngredients().get(j).getQuantity()) {
                            count++;
                        }

                    }
                }
            }

            System.out.println(count); // debug
            System.out.println("Calculation : " + (count >= ingredientQuery.size() * 0.1)); // debug
            if (count >= ingredientQuery.size() * 0.1 && ingredientQuery.size() != 0 && (showAllRecipes || sameDiet(userDietPlan, recipe.getDiet()))) {
                uniqueRecipes.add(recipe);
            }
        }

        returnedRecipes.addAll(uniqueRecipes); 

        Collections.sort(returnedRecipes, Comparator.comparingInt(recipe -> -getMatchedIngredientCount(recipe, ingredientQuery)));

        return returnedRecipes;
    }

    private static int getMatchedIngredientCount(Recipe recipe, ArrayList<DataStructures.StringIntPair> ingredientQuery) {
        int count = 0;
        for (DataStructures.StringIntPair queryPair : ingredientQuery) {
            for (Recipe.Ingredient ingredient : recipe.getIngredients()) {
                if(queryPair.getText().equalsIgnoreCase(ingredient.getIngredientName()) && queryPair.getNumber() == 0 && ingredient.getQuantity() == null){
                    count++;
                    break;
                }
                else if (queryPair.getText().equalsIgnoreCase(ingredient.getIngredientName()) && queryPair.getNumber() >= ingredient.getQuantity()) {
                    count++;
                    break; 
                }
            }
        }
        return count;
    }
    
    
       
}    
    