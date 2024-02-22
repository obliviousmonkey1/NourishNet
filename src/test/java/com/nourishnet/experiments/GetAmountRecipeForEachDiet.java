package com.nourishnet.experiments;

import java.util.ArrayList;

import com.nourishnet.Recipe;
import com.nourishnet.ResourceLoader;

public class GetAmountRecipeForEachDiet {

    public static void main(String[] args) {

        int totalRecipes = 0;
        String[] diets = {"Vegan", "Vegetarian", "Pescatarian", "Keto", "Medeterranean", "Paleo"};

        ArrayList<DietAmount> dietAmounts = new ArrayList<>();

        for (String diet : diets) {
            dietAmounts.add(new DietAmount(diet, 0));
        }

        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();

        for (Recipe recipe : recipes) {
            totalRecipes++;
            for (String recipeDiet : recipe.getDiet()) {
                for (DietAmount dietAmount : dietAmounts) {
                    if (recipeDiet.equals(dietAmount.getDiet())) {
                        dietAmount.setAmount(dietAmount.getAmount() + 1);
                    }
                }
            }
        }

        System.out.println("Total Recipes: " + totalRecipes);
        for (DietAmount dietAmount : dietAmounts) {
            System.out.println("Diet: " + dietAmount.getDiet() + ", Amount: " + dietAmount.getAmount());
        }
    }
}

class DietAmount {
    private String diet;
    private int amount;

    public DietAmount(String diet, int amount) {
        this.diet = diet;
        this.amount = amount;
    }

    public String getDiet() {
        return diet;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
