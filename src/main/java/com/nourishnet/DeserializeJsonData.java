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
        File file = new File(System.getProperty("user.dir") + "/" + Pointers.content + "/Recipes.json");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<ArrayList<Recipe>>() {});
    }

    public static ArrayList<Ingredient> initaliseIngredientClass() throws Exception {
        File file = new File(System.getProperty("user.dir") +  "/" + Pointers.content + "/Ingredients.json");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<ArrayList<Ingredient>>() {});
    }
}
