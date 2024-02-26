package com.nourishnet.experiments;

import javax.swing.*;

import com.nourishnet.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class IconDisplay extends JFrame {

    private static final int ICON_WIDTH = 50;
    private static final int ICON_HEIGHT = 50;

    public IconDisplay() {
        setTitle("Icons Display");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 4));

        addIcon(Constants.cookingLevelIcon);
        addIcon(Constants.glutenFreeIcon);
        addIcon(Constants.healthyIcon);
        addIcon(Constants.cookingLevelIcon);
        addIcon(Constants.servingSizeIcon);
        addIcon(Constants.timeIcon);
        addIcon(Constants.veganIcon);
        addIcon(Constants.vegetarianIcon);

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void addIcon(String imagePath) {
        try {
            BufferedImage image = loadImage(imagePath);
            Image scaledImage = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel label = new JLabel(icon);
            add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IconDisplay());
    }
}
