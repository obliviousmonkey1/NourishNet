package com.nourishnet;


import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;

public class Tools {


    public static ImageIcon getRecipeImage(String recipeName){
        System.out.println("Recipe Name: " + recipeName);
        StringBooleanPair hImage = LogIn.hasImage(recipeName, Pointers.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.recipeImagePath + "/" + recipeName + ".png");
        }
        return new ImageIcon(Pointers.recipeImagePath + "/default.png");
    }

    public static ImageIcon getDietImage(String dietName){
        StringBooleanPair hImage = LogIn.hasImage(dietName, Pointers.dietImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.dietImagePath + "/" + dietName + hImage.getExtension());
        }
        return new ImageIcon(Pointers.dietImagePath + "/default.jpeg");
    }


}

