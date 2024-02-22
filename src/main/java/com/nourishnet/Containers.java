package com.nourishnet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    

public class Containers {

    public static JPanel createContainer(Recipe recipe) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a black border to each container

        // Name label
        JLabel nameLabel = new JLabel("Name: " + recipe.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for name label
        container.add(nameLabel, BorderLayout.NORTH);

        // Description and tags panel
        JPanel descriptionTagsPanel = new JPanel();
        descriptionTagsPanel.setLayout(new BoxLayout(descriptionTagsPanel, BoxLayout.Y_AXIS));

        // Description label
        JLabel descriptionLabel = new JLabel("Description: " + recipe.getDescription());
        descriptionTagsPanel.add(descriptionLabel);

        // Diet label
        JLabel dietLabel = new JLabel("Diet: " + String.join(", ", recipe.getDiet()));
        descriptionTagsPanel.add(dietLabel);

        // Tags label
        JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", recipe.getTags()));
        descriptionTagsPanel.add(tagsLabel);


        container.add(descriptionTagsPanel, BorderLayout.WEST);

        // Image label
        String path = recipe.generateImagePath();
        ImageIcon imageIcon = Tools.getRecipeImage(path);
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH); // Scale down the image
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        container.add(imageLabel, BorderLayout.CENTER);

        // Button
        JButton button = new JButton("View Full Details");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open full details view for this recipe
            }
        });
        container.add(button, BorderLayout.SOUTH);

        return container;
    }
}
