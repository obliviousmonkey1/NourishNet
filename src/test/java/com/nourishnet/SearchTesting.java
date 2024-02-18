package com.nourishnet;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

public class SearchTesting extends JFrame {

    private JTextField textField;
    private JLabel suggestionLabel;
    private JButton showAllRecipesButton; // Added button declaration
    private ArrayList<Recipe> recipes;
    private User user;
    private boolean isShowAllRecipes;

    public SearchTesting() {
        loadData();

        setTitle("AutoComplete Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        textField = new JTextField();
        suggestionLabel = new JLabel("Suggestions: ");
        showAllRecipesButton = new JButton("Show All Recipes"); // Initialize button

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(textField);
        add(suggestionLabel);
        add(showAllRecipesButton); // Add button to the UI

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                performSearch();
            }
        });

        showAllRecipesButton.addActionListener(e -> {
            if(isShowAllRecipes){
                isShowAllRecipes = false;
                showAllRecipesButton.setText("Show All Recipes"); // shows all recipes regardless 
            } else {
                isShowAllRecipes = true;
                showAllRecipesButton.setText("Hide All Recipes"); // only shows recipes in users diet 
            }
            performSearch();
        });
    }

    private void performSearch() {
        String query = textField.getText().toLowerCase();
        StringBuilder suggestions = new StringBuilder("Suggestions: ");

        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(query, recipes, user.getSavedRecipeIDs(), user.getDiet(), isShowAllRecipes);

        System.out.println(returnedRecipes.size());
        for (Recipe recipe : returnedRecipes) {
            suggestions.append(recipe.getName()).append(", ");
        }

        if (suggestions.length() > 14) {
            suggestions.setLength(suggestions.length() - 2);
        }

        suggestionLabel.setText(suggestions.toString());
    }

   
    private void loadData(){
        user = ResourceLoader.loadUser(LogIn.getUserJsonPath("0004"));
        recipes = ResourceLoader.loadRecipes();

        System.out.println("Loaded all data");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchTesting::new);
    }
}
