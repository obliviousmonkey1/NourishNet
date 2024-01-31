package com.nourishnet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;

public class DeserializeUserData {
    public static User initaliseUserClass(String userJsonPath)  throws Exception {
        File file = new File(userJsonPath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, User.class);

    }

    public static  ArrayList<Recipe> initaliseRecipeClass()throws Exception {
        File file = new File(System.getProperty("user.dir") + "/Data/tempName/Recipe.json");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<ArrayList<Recipe>>() {});
    }
}
