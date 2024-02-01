package com.nourishnet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    private int id;
    private String name;
    private int caloriesPer100g; 
    private int protienPer100g;
    private int carbsPer100g;
    private int fatPer100g;


    @JsonCreator
    public Ingredient(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("caloriesPer100g")int caloriesPer100g,@JsonProperty("protienPer100g")int protienPer100g,@JsonProperty("carbsPer100g")int carbsPer100g,@JsonProperty("fatPer100g")int fatPer100g)
    {
        this.id = id;
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.protienPer100g = protienPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
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
