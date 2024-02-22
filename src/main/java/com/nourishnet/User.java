package com.nourishnet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String id;
    private String username;
    private int height;
    private int weight;
    private String diet;
    private ArrayList<Integer> savedRecipeIDs;
    private String password;
    private int[] DOB = new int[3]; //15/02/24 : JZ : the user will have a DOB stored which makes it easier to calculate age especially when time passes and a birthday comes 

    @JsonCreator
    public User(@JsonProperty("id") String id, @JsonProperty("username") String username, @JsonProperty("DOB") int[] DOB,
                @JsonProperty("height") int height, @JsonProperty("weight") int weight,
                @JsonProperty("diet") String diet, @JsonProperty("savedRecipeIDs") ArrayList<Integer> savedRecipeIDs,
                @JsonProperty("password") String password) {
        this.id = id;
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
        this("", "", new int[]{0,0,0}, 0, -1, "", new ArrayList<>(), "");
        this.id = LogIn.getNewUserId();
    }

    public String getId() {
        return this.id;
    }

    // Only to be used if their is a folder creation error
    protected void setUserId(String userId){
        this.id = userId;
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
