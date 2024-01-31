package com.nourishnet;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class RecipeDisplayFrame extends JFrame {

    private DefaultListModel<String> recipeListModel;
    private JList<String> recipeList;

    public RecipeDisplayFrame(List<Recipe> recipes) {
        super("Recipe Display");

        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);

        // Add recipes to the list model
        for (Recipe recipe : recipes) {
            recipeListModel.addElement(getRecipeDisplayText(recipe));
        }

        // Set up the UI
        setupUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(recipeList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private String getRecipeDisplayText(Recipe recipe) {
        StringBuilder displayText = new StringBuilder();
        displayText.append("Recipe Name: ").append(recipe.getName()).append("\n");
        displayText.append("Ingredients:\n");

        for (Ingredient ingredient : recipe.getIngredients()) {
            displayText.append("- ").append(ingredient.getName()).append(": ")
                    .append(ingredient.getCaloriesPer100g()).append(" calories per 100g\n");
        }

        displayText.append("---------------------------");

        return displayText.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Recipe> recipes = getRecipesFromDataSource();
            new RecipeDisplayFrame(recipes);
        });
    }

    private static ArrayList<Recipe> getRecipesFromDataSource() {
        
        try {
            return DeserializeUserData.initaliseRecipeClass();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Recipe>();
        }
    }
}
