package com.nourishnet;

import java.util.ArrayList;

public class ResourceLoader {
    
    public static ArrayList<Ingredient> loadIngredients(){
        try{

            //Debug
            System.out.println("Deserialisation success");
            return DeserializeJsonData.initaliseIngredientClass();


        }catch (Exception e1) {
            //Debug
            System.out.println("Deserialisation failed");
            e1.printStackTrace();
        
            return new ArrayList<Ingredient>();
        }
}

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

    public static ArrayList<Ingredient> loadIngredientsIntoRecipes(ArrayList<String> ingredientNames, ArrayList<Ingredient> ingredientHolder){
        ArrayList<Ingredient> recipeIngredients = new ArrayList<Ingredient>();

        for (int i = 0; i< ingredientNames.size(); i++){
            for (int c = 0; c < ingredientHolder.size(); c++) {
                if (ingredientHolder.get(c).getName().equals(ingredientNames.get(i))) {
                    recipeIngredients.add(ingredientHolder.get(c));
                }
            }
        }

        return recipeIngredients;
       
    }
}
