package com.nourishnet;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;


public class Tools {


    public static ImageIcon getRecipeImage(String imageName){
        DataStructures.StringBooleanPair hImage = LogIn.hasImage(imageName, Constants.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Constants.recipeImagePath + "/" + imageName + ".png");
        }
        return new ImageIcon(Constants.recipeImagePath + "/default.png");
    }

    public static ImageIcon getDietImage(String dietName){
        DataStructures.StringBooleanPair hImage = LogIn.hasImage(dietName, Constants.dietImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Constants.dietImagePath + "/" + dietName + hImage.getExtension());
        }
        return new ImageIcon(Constants.dietImagePath + "/default.jpeg");
    }


    public static List<DataStructures.StringImagePair> linkTagAndDietIcons(Recipe recipe){
        List<DataStructures.StringImagePair> icons = new ArrayList<>();

        for(String diet : recipe.getDiet()){

            if (diet.equals("Vegetarian")) {
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.vegetarianIcon)));
            } else if (diet.equals("Vegan")) {
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.veganIcon)));
            } else if (diet.equals("Keto")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.freeFromIcon)));
            } else if (diet.equals("Mediterranean")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.freeFromIcon)));
            } else if (diet.equals("Paleo")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.freeFromIcon)));
            } else if (diet.equals("Pescatarian")){
                icons.add(new DataStructures.StringImagePair(diet, new ImageIcon(Constants.freeFromIcon)));
            }
        }
        for(String tag : recipe.getTags()){
            if(tag.equals("Healthy")){
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Constants.healthyIcon)));
            } else if (tag.equals("Gluten-free")) {
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Constants.glutenFreeIcon)));
            }else{
                icons.add(new DataStructures.StringImagePair(tag, new ImageIcon(Constants.freeFromIcon)));
            }
        }

        return icons;

    }

}
