package com.nourishnet.experiments;

import javax.swing.*;

import com.nourishnet.Diet;
import com.nourishnet.ResourceLoader;
import com.nourishnet.Tools;

import java.awt.*;
import java.util.ArrayList;

public class DietDisplay extends JFrame {

    private ArrayList<Diet> diets;

    public DietDisplay(ArrayList<Diet> diets) {
        this.diets = diets;

        setTitle("Diet Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane();
        setContentPane(scrollPane);

        // Create a panel to hold the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(diets.size(), 1));

        displayDiets(contentPanel);

        // Set the content panel as the viewport view
        scrollPane.setViewportView(contentPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayDiets(JPanel contentPanel) {
        for (Diet diet : diets) {
            JPanel dietPanel = new JPanel();
            dietPanel.setLayout(new BorderLayout());
    
            JLabel nameLabel = new JLabel("Name: " + diet.getName());
    
            // Use JTextArea for the description within a JScrollPane
            JTextArea descriptionTextArea = new JTextArea(diet.getDescription());
            descriptionTextArea.setEditable(false); // Set JTextArea as read-only
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
    
            ImageIcon imageIcon = Tools.getDietImage(diet.getName());
            JLabel imageLabel = new JLabel(imageIcon);
    
            dietPanel.add(nameLabel, BorderLayout.NORTH);
            dietPanel.add(descriptionScrollPane, BorderLayout.CENTER);
            dietPanel.add(imageLabel, BorderLayout.SOUTH);
    
            contentPanel.add(dietPanel);
        }
    }
    
    

    public static void main(String[] args) {
        ArrayList<Diet> diets = ResourceLoader.loadDiets();

        SwingUtilities.invokeLater(() -> new DietDisplay(diets));
    }
}
