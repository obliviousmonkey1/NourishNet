package com.nourishnet;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private int id;
    private String name; 
    private String description;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> tempIngredients = new ArrayList<String>();
    private ArrayList<Quantity> quantities = new ArrayList<Quantity>();
    private ArrayList<String> instructions = new ArrayList<String>();
    private ArrayList<String> tags = new ArrayList<String>();

    // Default constructor for Jackson
    public Recipe() {
    }

    @JsonCreator
    public Recipe(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("ingredients") ArrayList<String> ingredients,
            @JsonProperty("quantities") ArrayList<Quantity> quantities,
            @JsonProperty("instructions") ArrayList<String> instructions,
            @JsonProperty("tags") ArrayList<String> tags
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tempIngredients = ingredients;
        this.quantities = quantities;
        this.instructions = instructions;
        this.tags = tags;
        this.ingredients = new ArrayList<Ingredient>();
    }

    @JsonIgnore
    public int calculateRecipeCalories() {
        int sum = 0; 

        for (int i = 0; i < ingredients.size(); i++) {
            sum += ingredients.get(i).getCaloriesPer100g();
        }

        return sum;
    } 

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTempIngredients() {
        return this.tempIngredients;
    }

    public void setTempIngredients(ArrayList<String> tempIngredients) {
        this.tempIngredients = tempIngredients;
    }

    @JsonIgnore
    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @JsonIgnore
    public void setIngredients(ArrayList<Ingredient> newIngredients) {
        this.ingredients = newIngredients;
    }

    public ArrayList<Quantity> getQuantities() {
        return this.quantities;
    }

    public void setQuantities(ArrayList<Quantity> quantities) {
        this.quantities = quantities;
    }

    public ArrayList<String> getInstructions() {
        return this.instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public static class Quantity {
        private int quantity; 
        private String measurement;

        public Quantity() {
            // default constructor
        }

        @JsonCreator
        public Quantity(@JsonProperty("quantity") int quantity, @JsonProperty("measurement") String measurement) {
            this.quantity = quantity;
            this.measurement = measurement;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getMeasurement() {
            return this.measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }
    }
}
