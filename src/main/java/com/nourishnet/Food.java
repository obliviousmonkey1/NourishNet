package com.nourishnet;

public class Food {

    private String name;
    private int caloriesPer100g; 

    public Food(String name, int caloriesPer100g)
    {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
    }
    
    public int getCalories()
    {
        return this.caloriesPer100g;
    }
}
