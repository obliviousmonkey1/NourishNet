package com.nourishnet;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class convert {
    public static String location = "/Users/parzavel/Desktop/project/Data/tempImageConversion";
    public static String output = "/Users/parzavel/Desktop/project/Data/Content/recipeImages";

    public static void main(String[] args) {
        File[] listOfFiles = new File(location).listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            try{
                // Read the image file
                BufferedImage inputImage = ImageIO.read(listOfFiles[i]);

                // Write the BufferedImage to PNG format
                File outputFile = new File(output);
                ImageIO.write(inputImage, "png", outputFile);

                listOfFiles[i].delete();
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }

        }
    }
}
