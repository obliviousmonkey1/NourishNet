package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private int id;
    private String name; 
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    @JsonCreator
    public Recipe(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("ingredients") ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    
    public int calculateRecipeCalories()
    {
        int sum=0; 

        for (int i=0; i<ingredients.size(); i++)
        {
            sum += ingredients.get(i).getCaloriesPer100g();
        }

        return sum;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
}
    