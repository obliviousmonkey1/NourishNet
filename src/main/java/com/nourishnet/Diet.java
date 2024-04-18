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


}

    
