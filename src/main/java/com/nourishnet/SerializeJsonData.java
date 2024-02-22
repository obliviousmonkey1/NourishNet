package com.nourishnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//Java object to JSON
public class SerializeJsonData {
    
    public static void serializeNewUser(User user){
       
        String folderPath = Constants.usersPath + '/' + user.getId(); 
        File folder = new File(Constants.usersPath + '/' + user.getId());

        boolean folderCreated = folder.mkdirs();

        if (folderCreated) {
            System.out.println("Folder created successfully at: " + folder.getAbsolutePath());
        } else {
            System.err.println("Failed to create folder.");
        }

        serializeUser(user, folderPath + '/' + user.getId() + ".json");  
    }

    public static void serializeUser(User user, String filePath){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(filePath), user);
            System.out.println("User data serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during user data serialization.");
        }
    }

    public static void serializeRecipes(ArrayList<Recipe> recipes, String filePath){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(filePath), recipes);
            System.out.println("Recipes data serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during recipes data serialization.");
        }
    }
}
