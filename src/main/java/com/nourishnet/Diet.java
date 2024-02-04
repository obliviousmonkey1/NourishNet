package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
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

}

    
