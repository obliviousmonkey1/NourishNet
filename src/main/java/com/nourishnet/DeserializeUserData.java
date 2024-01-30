package com.nourishnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class DeserializeUserData {
    public static User initaliseUserClass(String userJsonPath)  throws Exception {
        File file = new File(userJsonPath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, User.class);

    }
}
