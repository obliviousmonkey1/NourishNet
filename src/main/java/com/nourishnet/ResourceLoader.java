package com.nourishnet;

import java.util.ArrayList;


public class ResourceLoader {
    

    public static ArrayList<Recipe> loadRecipes(){
        try{
             System.out.println("Deserialisation success");  //Debug
            return DeserializeJsonData.initaliseRecipeClass();
        }catch (Exception e1) {
            System.out.println("Deserialisation failed"); //Debug
            e1.printStackTrace();

            return new ArrayList<Recipe>();
        }
    }

    public static User loadUser(String userPath){
        try{
             System.out.println("Deserialisation success"); //Debug
            return DeserializeJsonData.initaliseUserClass(userPath);
        }catch (Exception e1) {
            System.out.println("Deserialisation failed"); //Debug
            e1.printStackTrace();

            return null;
        }
    }


    public static ArrayList<Diet> loadDiets(){
        try{
             
             System.out.println("Deserialisation success"); //Debug
            return DeserializeJsonData.initaliseDietClass();
        }catch (Exception e1) {
        
            System.out.println("Deserialisation failed"); //Debug
            e1.printStackTrace();

            return null;
        }
    }
}
