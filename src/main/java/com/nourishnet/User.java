package com.nourishnet;

import javax.crypto.SecretKey;
import java.util.ArrayList;


public class User {
    private int ID;
    private String username;
    private Boolean hasPassword;
    private String password;
    private ArrayList<String> diets;
    private ArrayList<Integer> savedRecipeIDs;

    
    private static int nextID = 0;
    private SecretKey secretKey;
    
    // 28/01/23 : TE : This is for when creating a user  
    public User(){
        this("user", "", Tools .generateSecretKey());
        //password = Tools.encrypt(password, secretKey);

        // need to call the createUserJson() in the SerializeUserData to save the new users details
    }

    // 28/01/23 : TE : This is for loading existing users 
    public User(String username, String password, SecretKey secretKey){
        this.username = username;
        this.password = password;
        this.ID = nextID;
        this.secretKey = secretKey;

        nextID++;
    }
    // testing 
    public String getPassword3(){
        return password;
    }

    public void setPassword(String passowrd){
        try {
            this.password = Tools.encrypt(password, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public String getUsername(){
        return this.username;
    };


    public Boolean getHasPassword(){
        return this.hasPassword;
    }

    private String getPassword(){
        return this.password;
    }

    public Boolean checkPassword(String enteredPassword){
        String encryptedEnteredPassword = "";
        try{
            encryptedEnteredPassword = Tools.encrypt(enteredPassword, secretKey);
            System.out.println(encryptedEnteredPassword);
        } catch(Exception e){
            e.printStackTrace();
        }

        return getPassword().equals(encryptedEnteredPassword);
        
    }

}
