package com.nourishnet;

import java.util.ArrayList;

public class Recipe {

    private String name; 
    private ArrayList<Food> foods = new ArrayList<Food>();

    public Recipe(String name, ArrayList<Food> foods) {
        this.name = name;
        this.foods = foods;
    }
    
    public int calculateRecipeCalories()
    {
        int sum=0; 

        for (int i=0; i<foods.size(); i++)
        {
            sum += foods.get(i).getCalories();
        }

        return sum;
    }
}
    