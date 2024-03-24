package com.nourishnet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Tools {


    public static ImageIcon getRecipeImage(String imageName){
        DataStructures.StringBooleanPair hImage = hasImage(imageName, Constants.recipeImagePath);
        System.out.println(hImage.getHasImage()); //Debug
        if(hImage.getHasImage()){
            return new ImageIcon(Constants.recipeImagePath + "/" + imageName + ".png");
        }
        return new ImageIcon(Constants.recipeImagePath + "/default.png");
    }

    public static ImageIcon getDietImage(String dietName){
        DataStructures.StringBooleanPair hImage = hasImage(dietName, Constants.dietImagePath);
        System.out.println(hImage.getHasImage()); //Debug
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
    public static DataStructures.StringBooleanPair hasImage(String userId, String path) {
        String[] fileExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for(String extension: fileExtensions){
            File file = new File(path + "/" + userId + extension);
            if (file.exists()) {
                return new DataStructures.StringBooleanPair(extension, true);
            } 
        }
        return new DataStructures.StringBooleanPair("", false);
		
	}


    public static void loadImage(String userId, BufferedImage image) {
        
        createCircularImage(image,  Constants.usersPath + '/' + userId + '/' + userId + ".png");

    }

    public static BufferedImage loadImage(String imagePath) {
        try {
             createCircularImage(ImageIO.read(new File(imagePath)), Constants.tempImagePath+"/output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void createCircularImage(BufferedImage originalImage, String path) {
        int diameter = Math.min(originalImage.getWidth(), originalImage.getHeight());

        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circularImage.createGraphics();

        // Set the clip to a circle
        Shape clip = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(clip);

        // Draw the original image inside the circle
        g2d.drawImage(originalImage, 0, 0, diameter, diameter, null);

        // // Draw the black border (a slightly larger circle)
        // g2d.setColor(Color.BLACK);
        // g2d.setStroke(new BasicStroke(4)); // Adjust border thickness as needed
        // g2d.draw(new Ellipse2D.Double(2, 2, diameter - 4, diameter - 4));

        g2d.dispose();

        // Save the resulting circular image
        saveImage(circularImage, path);
    }

    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            ImageIO.write(image, "png", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
