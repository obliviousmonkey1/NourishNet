package com.nourishnet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircularImageFrame {


    public static BufferedImage loadImage(String imagePath) {
        try {
             createCircularImage(ImageIO.read(new File(imagePath)), imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void createCircularImage(BufferedImage originalImage, String path) {
        int diameter = Math.min(originalImage.getWidth(), originalImage.getHeight());

        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circularImage.createGraphics();

        Shape clip = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(clip);

        g2d.drawImage(originalImage, 0, 0, diameter, diameter, null);

        g2d.dispose();

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
