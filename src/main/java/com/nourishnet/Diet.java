package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Diet {

    private String name;
    private String descripton;
    private int bmiRecommendationThreshold;
  
    @JsonCreator
    public Diet(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("bmiRecommendationThreshold") int bmiRecommendationThreshold)
    {
        this.name = name; 
        this.descripton = description;
        this.bmiRecommendationThreshold = bmiRecommendationThreshold;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.descripton;
    }

    public int getBmiRecommendationThreshold(){
        return this.bmiRecommendationThreshold;
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
    
                    if (number == Constants.numberOfRecipesReturnedDiets) {
                        return recipeExamples;
                    }
                    break;
                }
            }
        }

        return recipeExamples;
    }
    

}

    
