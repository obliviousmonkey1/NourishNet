package com.nourishnet;

import java.util.ArrayList;


public class ResourceLoader {
    

    public static ArrayList<Recipe> loadRecipes(){
        try{
             //Debug
             System.out.println("Deserialisation success");
            return DeserializeJsonData.initaliseRecipeClass();
        }catch (Exception e1) {
            //Debug
            System.out.println("Deserialisation failed");
            e1.printStackTrace();

            return new ArrayList<Recipe>();
        }
    }

    public static User loadUser(String userPath){
        try{
             //Debug
             System.out.println("Deserialisation success");
            return DeserializeJsonData.initaliseUserClass(userPath);
        }catch (Exception e1) {
            //Debug
            System.out.println("Deserialisation failed");
            e1.printStackTrace();

            return null;
        }
    }


    public static ArrayList<Diet> loadDiets(){
        try{
             //Debug
             System.out.println("Deserialisation success");
            return DeserializeJsonData.initaliseDietClass();
        }catch (Exception e1) {
            //Debug
            System.out.println("Deserialisation failed");
            e1.printStackTrace();

            return null;
        }
    }
}
