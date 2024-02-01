package com.nourishnet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    private int id;
    private String name;
    private int caloriesPer100g; 
    private int proteinPer100g;
    private int carbsPer100g;
    private int fatPer100g;

    @JsonCreator
    public Ingredient(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("caloriesPer100g")int caloriesPer100g, @JsonProperty("proteinPer100g")int proteinPer100g, @JsonProperty("carbsPer100g")int carbsPer100g, @JsonProperty("fatPer100g")int fatPer100g)
    { 
        this.id = id;
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
    }
    
    public static void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            System.out.println("- " + ingredient.getName() + ": " + ingredient.getCaloriesPer100g() + " calories per 100g, protein per 100g: " + ingredient.getProteinPer100g() + ", carbs per 100g, " + ingredient.getCarbsPer100g() + ", fat per 100g: " + ingredient.getFatPer100g());
        }
    }

    public String getName(){
        return this.name;
    }

    public int getCaloriesPer100g()
    {
        return this.caloriesPer100g;
    }

    public int getProteinPer100g()
    {
        return this.proteinPer100g;
    }

    public int getCarbsPer100g()
    {
        return this.carbsPer100g;
    }

    public int getFatPer100g()
    {
        return this.fatPer100g;
    }
}
