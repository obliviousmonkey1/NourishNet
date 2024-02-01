package com.nourishnet;


import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;

public class Tools {


    public static ImageIcon getRecipeImage(String recipeName){
        StringBooleanPair hImage = LogIn.hasImage(recipeName, Pointers.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.userDir + "/" + Pointers.recipeImagePath + "/" + recipeName + hImage.getExtension());
        }
        return new ImageIcon(Pointers.userDir + "/" + Pointers.recipeImagePath + "/default.png");
    }

    public static ImageIcon getIngredientImage(String ingredientName){
        StringBooleanPair hImage = LogIn.hasImage(ingredientName, Pointers.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.userDir + "/" + Pointers.ingredientImagePath + "/" + ingredientName + hImage.getExtension());
        }
        return new ImageIcon(Pointers.userDir + "/" + Pointers.ingredientImagePath + "/default.png");
    }
}

