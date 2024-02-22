package com.nourishnet;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;


public class Tools {


    public static ImageIcon getRecipeImage(String recipeName){
        System.out.println("Recipe Name: " + recipeName);
        DataStructures.StringBooleanPair hImage = LogIn.hasImage(recipeName, Pointers.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.recipeImagePath + "/" + recipeName + ".png");
        }
        return new ImageIcon(Pointers.recipeImagePath + "/default.png");
    }

    public static ImageIcon getDietImage(String dietName){
        DataStructures.StringBooleanPair hImage = LogIn.hasImage(dietName, Pointers.dietImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.dietImagePath + "/" + dietName + hImage.getExtension());
        }
        return new ImageIcon(Pointers.dietImagePath + "/default.jpeg");
    }


    public static List<DataStructures.StringImagePair> linkTagAndDietIcons(Recipe recipe){
        List<DataStructures.StringImagePair> icons = new ArrayList<>();

        for(String diet : recipe.getDiet()){

            if (diet.equals("Vegetarian")) {
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.vegetarianIcon)));
            } else if (diet.equals("Vegan")) {
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.veganIcon)));
            } else if (diet.equals("Keto")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.freeFromIcon)));
            } else if (diet.equals("Mediterranean")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.freeFromIcon)));
            } else if (diet.equals("Paleo")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.freeFromIcon)));
            } else if (diet.equals("Pescatarian")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Pointers.freeFromIcon)));
            }
        }
        for(String tag : recipe.getTags()){
            if(tag.equals("Healthy")){
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Pointers.healthyIcon)));
            } else if (tag.equals("Gluten-free")) {
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Pointers.glutenFreeIcon)));
            }else{
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Pointers.freeFromIcon)));
            }
        }

        return icons;

    }

}
