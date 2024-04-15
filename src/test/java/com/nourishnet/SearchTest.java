package com.nourishnet;

import java.util.ArrayList;


public class SearchTest {

    public static void main(String[] args) {
        ArrayList<Recipe> recipeHolder = ResourceLoader.loadRecipes();
        ArrayList<Recipe> results = new ArrayList<>();
        ArrayList<DataStructures.StringIntPair> ingredientQuery = new ArrayList<>();
        ingredientQuery.add(new DataStructures.StringIntPair("Chicken thighs", 4));
        ingredientQuery.add(new DataStructures.StringIntPair("Aubergine", 1));
        ingredientQuery.add(new DataStructures.StringIntPair("Courgette", 2));
        ingredientQuery.add(new DataStructures.StringIntPair("Red bell pepper", 1));
        ingredientQuery.add(new DataStructures.StringIntPair("Red onion", 1));
        ingredientQuery.add(new DataStructures.StringIntPair("Cherry tomatoes", 200));
        ingredientQuery.add(new DataStructures.StringIntPair("Garlic cloves", 2));
        ingredientQuery.add(new DataStructures.StringIntPair("Fresh rosemary", 2));
        ingredientQuery.add(new DataStructures.StringIntPair("Olive oil", 3));
        ingredientQuery.add(new DataStructures.StringIntPair("Balsamic vinegar", 2));

        results = Search.getIngredientsSearch(recipeHolder, ingredientQuery, true, "Vegan");

        for (Recipe recipe : results) {
            System.out.println(recipe.getName());
        }
    }
}
