package com.nourishnet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    private String name;
    private int caloriesPer100g; 

    @JsonCreator
    public Ingredient(@JsonProperty("name") String name, @JsonProperty("caloriesPer100g")int caloriesPer100g)
    {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
    }
    
    public static void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            System.out.println("- " + ingredient.getName() + ": " + ingredient.getCaloriesPer100g() + " calories per 100g");
        }
    }

    public String getName(){
        return this.name;
    }

    public int getCaloriesPer100g()
    {
        return this.caloriesPer100g;
    }
}
