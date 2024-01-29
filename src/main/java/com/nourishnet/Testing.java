package com.nourishnet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*;
import java.util.List;


public class Testing extends JFrame {
    public Testing() {

        System.out.println(LogIn.getNumberOfUserProfiles());

        User user = new User();
        user.setPassword("hello");
        System.out.println(user.getPassword3());
        System.out.println(user.checkPassword("hello"));

        String userDir = System.getProperty("user.dir");
        setLayout(new FlowLayout());
        setSize(500, 500);
        //getContentPane().setBackground(Color.BLACK);
        List<StringImagePair> profiles = LogIn.getUserProfiles();

        for (int i = 0; i < profiles.size(); i++) {
            String buttonText = profiles.get(i).getText();
            JButton button = new JButton(buttonText);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button Clicked: " + buttonText);
                }
            });

            add(button);

            int newWidth = 200; 
            int newHeight = 150; 

            ImageIcon imageIcon = profiles.get(i).getImage();
            if (imageIcon != null) {
                
                Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                add(new JLabel(imageIcon));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Testing window = new Testing();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setBackground(Color.BLACK);

            window.setVisible(true);
        });
    }
}

