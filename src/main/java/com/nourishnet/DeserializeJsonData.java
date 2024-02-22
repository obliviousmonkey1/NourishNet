package com.nourishnet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;

public class DeserializeJsonData {
    public static User initaliseUserClass(String userJsonPath)  throws Exception {
        File file = new File(userJsonPath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, User.class);

    }

    public static  ArrayList<Recipe> initaliseRecipeClass()throws Exception {
        File file = new File(Pointers.content + "/Recipes.json");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<ArrayList<Recipe>>() {});
    }


    public static ArrayList<Diet> initaliseDietClass()throws Exception{
        File file = new File(Pointers.content + "/Diets.json");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<ArrayList<Diet>>() {});
    }
}
