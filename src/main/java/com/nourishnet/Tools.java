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
        return new ImageIcon(Pointers.userDir + "/" + Pointers.recipeImagePath + "/default.jpeg");
    }

    public static ImageIcon getDietImage(String dietName){
        StringBooleanPair hImage = LogIn.hasImage(dietName, Pointers.dietImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Pointers.userDir + "/" + Pointers.dietImagePath + "/" + dietName + hImage.getExtension());
        }
        return new ImageIcon(Pointers.userDir + "/" + Pointers.dietImagePath + "/default.jpeg");
    }

    // load all the data so no need for that in the app/ creat a data type for it as well that contains an arry of ingredients
    // and recipes 
    // public static void loadData(){
    //     //
    // }
}

