package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Diet {

    private String name;
    private String descripton;
    private int bmiRecommendationThreshold;
    private String popularity;
    private String difficultyLevel;
    private String maintainability;
    private String strictness;
    private String weightloss;

  
    @JsonCreator
    public Diet(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("bmiRecommendationThreshold") int bmiRecommendationThreshold,
                @JsonProperty("popularity") String popularity,
                @JsonProperty("difficultyLevel") String difficultyLevel,
                @JsonProperty("maintainability") String maintainability,
                @JsonProperty("strictness") String strictness,
                @JsonProperty("weightloss") String weightloss)
    {
        this.name = name; 
        this.descripton = description;
        this.bmiRecommendationThreshold = bmiRecommendationThreshold;
        this.popularity = popularity;
        this.difficultyLevel = difficultyLevel; 
        this.maintainability = maintainability;
        this.strictness = strictness;
        this.weightloss = weightloss;

      
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

    public String getPopularity(){
        return this.popularity;
    }

    public String getDifficultyLevel(){
        return this.difficultyLevel;
    }

    public String getMaintainability(){
        return this.maintainability;
    }

    public String getStrictness(){
        return this.strictness;
    }

    public String getWeightLoss(){
        return this.weightloss;
    }

}

    

