package com.nourishnet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//Java object to JSON
public class SerializeJsonData {
    
    // pass the details
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
}
