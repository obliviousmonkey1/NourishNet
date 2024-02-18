package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String username;
    private int height;
    private int weight;
    private String diet;
    private ArrayList<Integer> savedRecipeIDs;
    private String password;
    private int[] DOB = new int[3]; //15/02/24 : JZ : the user will have a DOB stored which makes it easier to calculate age especially when time passes and a birthday comes 
    private static final int[] emptyDOB = {0,0,0}; //for the empty constructor

    // 28/01/23 : TE : This is for when creating a user  
    // public User(){
    //     this("", -1, 0.0, -1, -1, new ArrayList<Integer>(), ""); // Note: Use null for password, and ArrayList<> instead of new ArrayList<Integer>()
    //     //password = Tools.encrypt(password, secretKey);

    //     // need to call the createUserJson() in the SerializeUserData to save the new users details
    //}

    // 28/01/23( : TE : This is for loading existing users 

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("DOB") int[] DOB,
                @JsonProperty("height") int height, @JsonProperty("weight") int weight,
                @JsonProperty("diet") String diet, @JsonProperty("savedRecipeIDs") ArrayList<Integer> savedRecipeIDs,
                @JsonProperty("password") String password) {
        this.username = username;
        this.DOB = DOB;
        this.height = height;
        this.weight = weight;
        this.diet = diet;
        this.savedRecipeIDs = savedRecipeIDs;
        this.password = password;
    }

    @JsonIgnore
    public User() {
        this("", emptyDOB, 0, -1, "", new ArrayList<>(), "");
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int[] getDOB() {
        return this.DOB;
    }

    public void setDOB(int[] DOB) {
        this.DOB = DOB;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDiet() {
        return this.diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public ArrayList<Integer> getSavedRecipeIDs() {
        return this.savedRecipeIDs;
    }

    public void setSavedRecipeIDs(ArrayList<Integer> savedRecipeIDs) {
        this.savedRecipeIDs = savedRecipeIDs;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Boolean getHasPassword() {
        return !getPassword().equals("");
    }

    @JsonIgnore
    public Boolean checkPassword(String enteredPassword) {
        return getPassword().equals(enteredPassword);
    }
}
