package com.nourishnet.experiments;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nourishnet.Containers;
import com.nourishnet.LogIn;
import com.nourishnet.Recipe;
import com.nourishnet.ResourceLoader;
import com.nourishnet.Search;
import com.nourishnet.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiContainerFrame extends JFrame {
    private ArrayList<Recipe> recipes;
    private JPanel containerPanel;
    private Map<Recipe, JPanel> recipeContainers; // Map to store recipe-container associations
    private User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("0004"));
    private boolean isShowAllRecipes;

    public MultiContainerFrame(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.recipeContainers = new HashMap<>();
        this.isShowAllRecipes = true; // Initialize isShowAllRecipes to true
    
        setTitle("Search Recipes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
    
        initUI();
    
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    

    private void initUI() {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        // JButton searchButton = new JButton("Search");
        JButton showAllRecipesButton = new JButton("Show All Recipes");
        searchPanel.add(searchField);
        //searchPanel.add(searchButton);
        searchPanel.add(showAllRecipesButton);
    
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        containerPanel.setBackground(Color.WHITE);
    
        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    
        // Add a document listener to the search field
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDisplayedRecipes(searchField.getText().toLowerCase());
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDisplayedRecipes(searchField.getText().toLowerCase());
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
    
        displayAllRecipes(); // Display all recipes initially
    
        // searchButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         updateDisplayedRecipes(searchField.getText().toLowerCase());
        //     }
        // });

        showAllRecipesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle showing all recipes or filtered recipes
                isShowAllRecipes = !isShowAllRecipes;
                if (isShowAllRecipes) {
                    showAllRecipesButton.setText("Show Diet Related Recipes");
                } else {
                    showAllRecipesButton.setText("Show All Recipes");
                }
                updateDisplayedRecipes(searchField.getText().toLowerCase());
            }
        });
    }
    


    private void updateDisplayedRecipes(String query) {
        containerPanel.removeAll(); // Remove all current containers
        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(query, recipes, user.getSavedRecipeIDs(), user.getDiet(), isShowAllRecipes);

        for (Recipe recipe : returnedRecipes) {
            JPanel container = recipeContainers.get(recipe);
            if (container == null) {
                container = Containers.createContainer(recipe);
                recipeContainers.put(recipe, container);
            }
            containerPanel.add(container);
            
        }

        revalidate(); // Revalidate the container panel to reflect changes
        repaint(); // Repaint the frame
    }

    private void displayAllRecipes() {
        containerPanel.removeAll(); // Remove all current containers
    
        // Sort recipes alphabetically by name
        recipes.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
    
        for (Recipe recipe : recipes) {
            JPanel container = Containers.createContainer(recipe);
            recipeContainers.put(recipe, container);
            containerPanel.add(container); 
        }
        System.out.println(recipeContainers.size());
    
        revalidate(); // Revalidate the container panel to reflect changes
        repaint(); // Repaint the frame
    }
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
                new MultiContainerFrame(recipes);
            }
        });
    }
}
