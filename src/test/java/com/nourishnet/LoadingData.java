package com.nourishnet;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoadingData {

   
  

    public static void main(String[] args) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try{
            ingredients = DeserializeJsonData.initaliseIngredientClass();
    
        }catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println(ingredients.get(0).getName());
    }
}
