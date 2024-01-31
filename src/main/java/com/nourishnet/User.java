package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
    private String username;
    private int age;
    private float height;
    //private FitnessLevel fitnessLevel;
    private int weight;
    private int diet;
    private ArrayList<Integer> savedRecipeIDs;
    private String password;
    
    
    
    // 28/01/23 : TE : This is for when creating a user  
    // public User(){
    //     this("", -1, 0.0, -1, -1, new ArrayList<Integer>(), ""); // Note: Use null for password, and ArrayList<> instead of new ArrayList<Integer>()
    //     //password = Tools.encrypt(password, secretKey);

    //     // need to call the createUserJson() in the SerializeUserData to save the new users details
    //}

    // 28/01/23 : TE : This is for loading existing users 
    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("age") int age, 
                @JsonProperty("height") float height, @JsonProperty("weight") int weight, 
                @JsonProperty("diet") int diet, @JsonProperty("savedRecipeIDs") ArrayList<Integer> savedRecipeIDs, 
                @JsonProperty("password") String password) {
        this.username = username;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.diet = diet;
        this.savedRecipeIDs = savedRecipeIDs;
        this.password = password;
    }
 
    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    };


    public Boolean getHasPassword(){
        return !getPassword().equals("");
    }

    public float getHeight(){
        return this.height;
    }

    private String getPassword(){
        return this.password;
    }

    public Boolean checkPassword(String enteredPassword){

        return getPassword().equals(enteredPassword);
        
    }

    public void changeAge(int age){
        this.age = age;
    }

}
