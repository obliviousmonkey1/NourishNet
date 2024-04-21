package com.nourishnet;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import java.util.HashSet;

public class Tools {


    public static Image getRecipeImage(String recipeName){
        DataStructures.StringBooleanPair hImage = hasImage(recipeName, Constants.recipeImagePath);
        System.out.println(hImage.getHasImage()); //Debug
        if(hImage.getHasImage()){
            return new Image(new File(Constants.recipeImagePath + "/" + recipeName + ".png").toURI().toString());
        }
        return new Image(new File(Constants.recipeImagePath + "/default.png").toURI().toString());

    }
   
    public static Image getDietImage(String dietName){
        DataStructures.StringBooleanPair hImage = hasImage(dietName, Constants.dietImagePath);
        System.out.println(hImage.getHasImage()); //Debug
        if(hImage.getHasImage()){
            return new Image(new File(Constants.dietImagePath + "/" + dietName + hImage.getExtension()).toURI().toString());

        }
        return new Image(new File(Constants.dietImagePath + "/default.jpeg").toURI().toString());

    }


    public static List<DataStructures.StringImagePair> linkTagAndDietIcons(Recipe recipe){
        List<DataStructures.StringImagePair> icons = new ArrayList<>();

        for(String diet : recipe.getDiet()){

            if (diet.equals("Vegetarian")) {
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.vegetarianIcon).toURI().toString())));
            } else if (diet.equals("Vegan")) {
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.veganIcon).toURI().toString())));
            } else if (diet.equals("Keto")){
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.freeFromIcon).toURI().toString())));
            } else if (diet.equals("Mediterranean")){
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.freeFromIcon).toURI().toString())));
            } else if (diet.equals("Paleo")){
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.freeFromIcon).toURI().toString())));
            } else if (diet.equals("Pescatarian")){
                icons.add(new DataStructures.StringImagePair(diet, new Image(new File(Constants.freeFromIcon).toURI().toString())));
            }
        }
        for(String tag : recipe.getTags()){
            if(tag.equals("Healthy")){
                icons.add(new DataStructures.StringImagePair(tag, new Image(new File(Constants.healthyIcon).toURI().toString())));
            } else if (tag.equals("Gluten-free")) {
                icons.add(new DataStructures.StringImagePair(tag, new Image(new File(Constants.glutenFreeIcon).toURI().toString())));
            }else{
                icons.add(new DataStructures.StringImagePair(tag, new Image(new File(Constants.freeFromIcon).toURI().toString())));
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

    public static Diet getRecommendedDiet(int bmi, ArrayList<Diet> diets){
       
        return null;
    }


    // diet page 
    public static int getNumberOfRecipesForSpecificDiet(String diet, ArrayList<Recipe> recipes){
        int count = 0;
        for(Recipe recipe : recipes){
            if(recipe.getDiet().contains(diet)){
                count++;
            }
        }
        return count;
    }

    public static int getAverageCaloriesPerMealForDiet(String diet, ArrayList<Recipe> recipes){
        int count = 0;
        int totalCalories = 0;
        for(Recipe recipe : recipes){
            if(recipe.getDiet().contains(diet)){
                count++;
                totalCalories += recipe.getNutrition().getCalories();
            }
        }
        return totalCalories/count;
    }

    public static ArrayList<Double> getRangeOfCaloriesForDiet(String diet, ArrayList<Recipe> recipes) {
        ArrayList<Double> range = new ArrayList<Double>();
        Double min = Double.POSITIVE_INFINITY;
        Double max = Double.NEGATIVE_INFINITY;
    
        for (Recipe recipe : recipes) {
            if (recipe.getDiet().contains(diet)) {
                double calories = recipe.getNutrition().getCalories(); // Assuming this is correct
                if (calories < min) {
                    min = calories;
                }
                if (calories > max) {
                    max = calories;
                }
            }
        }
        range.add(min);
        range.add(max);
        return range;
    }

    // 

    public static ArrayList<DataStructures.StringPair> getAllIngredients(ArrayList<Recipe> recipes) {
        HashSet<DataStructures.StringPair> uniqueIngredients = new HashSet<>();
        for (Recipe recipe : recipes) {
            for (Recipe.Ingredient ingredient : recipe.getIngredients()) {
                String measurement = "";
                if (ingredient.getMeasurement() != null && !ingredient.getMeasurement().isEmpty()) {
                    measurement = getMeasurement(ingredient.getMeasurement());
                }
                // Check if the ingredient already exists in the set
                boolean found = false;
                for (DataStructures.StringPair existingIngredient : uniqueIngredients) {
                    if (existingIngredient.getVar().equals(ingredient.getIngredientName()) && existingIngredient.getVar2().equals(measurement)) {
                        found = true;
                        break;
                    }
                }
                // Add the ingredient if not found
                if (!found) {
                    uniqueIngredients.add(new DataStructures.StringPair(ingredient.getIngredientName(), measurement));
                }
            }
        }
        // Convert the set back to an ArrayList
        ArrayList<DataStructures.StringPair> ingredients = new ArrayList<>(uniqueIngredients);
    
        for (DataStructures.StringPair ingredient : ingredients) {
            System.out.println(ingredient.getVar() + " : " + ingredient.getVar2());
        }
        return ingredients;
    }
    
    
    private static String getMeasurement(String measurement) {
        if (measurement.contains("tablespoons")  || measurement.contains("tablespoon")){
            return "tablespoons";
        } else if (measurement.contains("grams")  || measurement.contains("gram")){
            return "grams";
        } else if (measurement.contains("teaspoons")|| measurement.contains("teaspoon")) {
            return "teaspoons";
        } else if (measurement.contains("milliliters") || measurement.contains("milliliter") || measurement.equals("ml")){
            return "milliliters";
        } 
        return "";
    }
    

    // For cirulairising the image
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
