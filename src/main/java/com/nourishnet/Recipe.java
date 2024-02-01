package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private int id;
    private String name; 
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    // private Diet diet;
    // images will be store via name , if not will use the defualt profile image 
    
    //add arraylists for description + quantities + instructions


    @JsonCreator
    public Recipe(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("ingredients") ArrayList<String> ingredients) {
        this.name = name;
        //this.ingredients = ingredients;
        //grabIngredients;  //to be coded
    }
    
    /*public int calculateRecipeCalories()
    {
        int sum=0; 

        for (int i=0; i<ingredients.size(); i++)
        {
            sum += ingredients.get(i).getCaloriesPer100g();
        }

        return sum;
    } */

    //to be fixed when grabIngredients method is made


    public String getName(){
        return this.name;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
}
    