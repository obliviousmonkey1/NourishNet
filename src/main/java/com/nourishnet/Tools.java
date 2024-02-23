package com.nourishnet;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


public class Tools {


    public static ImageIcon getRecipeImage(String imageName){
        DataStructures.StringBooleanPair hImage = hasImage(imageName, Constants.recipeImagePath);
        System.out.println(hImage.getHasImage());
        if(hImage.getHasImage()){
            return new ImageIcon(Constants.recipeImagePath + "/" + imageName + ".png");
        }
        return new ImageIcon(Constants.recipeImagePath + "/default.png");
    }

    public static ImageIcon getDietImage(String dietName){
        DataStructures.StringBooleanPair hImage = hasImage(dietName, Constants.dietImagePath);
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

    // 25/01/24 : TE : Checks if file name ends with specified extension
    private static boolean checkFileExtension(String fileName, String extension){
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase()); 
    }

    // 25/01/24 : TE : Checks if an image exists with that name 
    public static DataStructures.StringBooleanPair hasImage(String imageName, String path) {
        String[] fileExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for(String extension: fileExtensions){
            File file = new File(path + "/" + imageName + extension);
            if (file.exists()) {
                return new DataStructures.StringBooleanPair(extension, true);
            } 
        }
        return new DataStructures.StringBooleanPair("", false);
		
	}

}
