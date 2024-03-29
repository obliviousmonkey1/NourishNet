package com.nourishnet.experiments;

import java.util.ArrayList;

import com.nourishnet.Recipe;
import com.nourishnet.ResourceLoader;
import com.nourishnet.Tools;

public class GetAmountRecipeForEachDiet {

    public static void main(String[] args) {

        String[] diets = {"Vegan", "Vegetarian", "Pescatarian", "Keto", "Medeterranean", "Paleo"};

        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();

        for (String diet : diets) {
            System.out.println("Diet: " +diet + ", Amount: " + Tools.getNumberOfRecipesForSpecificDiet(diet, recipes));

        }
    }
}
