package com.nourishnet;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private String name; 
    private ArrayList<String> diet;
    private int prepTime;
    private int cookTime;
    private String level;
    private int serves;
    private String description;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Quantity> quantities = new ArrayList<Quantity>();
    private ArrayList<String> instructions = new ArrayList<String>();
    private ArrayList<String> tags = new ArrayList<String>();

    @JsonCreator
    public Recipe(
            @JsonProperty("name") String name,
            @JsonProperty("diet") ArrayList<String> diet,
            @JsonProperty("prepTime") int prepTime,
            @JsonProperty("cookTime") int cookTime,
            @JsonProperty("level") String level,
            @JsonProperty("serves") int serves,
            @JsonProperty("description") String description,
            @JsonProperty("quantities") ArrayList<Quantity> quantities,
            @JsonProperty("instructions") ArrayList<String> instructions,
            @JsonProperty("tags") ArrayList<String> tags
    ) {
        this.name = name;
        this.diet = diet;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.level = level;
        this.serves = serves;
        this.description = description;
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

    public ArrayList<String> getDiet() {
        return this.diet;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getServes() {
        return this.serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    @JsonIgnore
    public ArrayList<String> getQuantitiesNames(){
        ArrayList<String> ingredientNames = new ArrayList<String>();
        for (int i =0; i< quantities.size();i++){
            ingredientNames.add(quantities.get(i).getIngredientName());
        }
        return ingredientNames;
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
        private String name;
        private int quantity; 
        private String measurement;

        public Quantity() {
            // default constructor
        }

        @JsonCreator
        public Quantity(@JsonProperty("name") String name, @JsonProperty("quantity") int quantity, @JsonProperty("measurement") String measurement) {
            this.name = name;
            this.quantity = quantity;
            this.measurement = measurement;
        }


        public String getIngredientName(){
            return this.name;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public String getMeasurement() {
            return this.measurement;
        }

    }
}
