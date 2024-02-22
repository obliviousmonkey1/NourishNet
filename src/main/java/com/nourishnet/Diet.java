package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Diet {

    private String name;
    private String descripton;
  
    @JsonCreator
    public Diet(@JsonProperty("name") String name,
                @JsonProperty("description") String description)
    {
        this.name = name; 
        this.descripton = description;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.descripton;
    }

    @JsonIgnore
    public ArrayList<Recipe> getExampleRecipes(ArrayList<Recipe> recipeHolder) {
        int number = 0;
        ArrayList<Recipe> recipeExamples = new ArrayList<Recipe>();
    
        for (int i = 0; i < recipeHolder.size(); i++) {

            ArrayList<String> tags = recipeHolder.get(i).getTags();
    
            for (String tag : tags) {
                if (tag.equals(this.name)) {
                    recipeExamples.add(recipeHolder.get(i));
                    number++;
    
                    if (number == Constants.numberOfRecipesReturned) {
                        return recipeExamples;
                    }
                    break;
                }
            }
        }

        return recipeExamples;
    }
    

}

    
