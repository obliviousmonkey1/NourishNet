package com.nourishnet;

import java.util.ArrayList;


public class User {
    private int ID;
    private String username;
    private int age;
    private float height;
    private FitnessLevel fitnessLevel;
    private int weight;
    private int diet;
    private ArrayList<Integer> savedRecipeIDs;
    private String password;
    
    
    private static int nextID = 0;
    
    // 28/01/23 : TE : This is for when creating a user  
    public User(){
        this("user", "");
        //password = Tools.encrypt(password, secretKey);

        // need to call the createUserJson() in the SerializeUserData to save the new users details
    }

    // 28/01/23 : TE : This is for loading existing users 
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.ID = nextID;

        nextID++;
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

    private String getPassword(){
        return this.password;
    }

    public Boolean checkPassword(String enteredPassword){

        return getPassword().equals(enteredPassword);
        
    }

}
