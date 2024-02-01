package com.nourishnet;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private int id;
    private String name; 
    private String description;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private ArrayList<Quantity> quantities = new ArrayList<Quantity>();
    private ArrayList<String> instructions = new ArrayList<String>();
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    // images will be store via name , if not will use the defualt profile image 
    


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


    // 01/02/23 : JZ : Love you Tom <3

    class Quantity {
    
    private int quantity; 
    private String measurement;

    public Quantity(int quantity, String measurement) {
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getMeasurement() {
        return this.measurement;
    }
}
}
    