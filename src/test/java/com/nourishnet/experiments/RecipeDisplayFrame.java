package com.nourishnet.experiments;
import javax.swing.*;

import com.nourishnet.DeserializeJsonData;
import com.nourishnet.Recipe;
import com.nourishnet.Tools;
import com.nourishnet.Recipe.Ingredient;
import com.nourishnet.Recipe.Nutrition;

import java.awt.*;
import java.util.ArrayList;

public class RecipeDisplayFrame extends JFrame {

    private DefaultListModel<String> recipeListModel;
    private JList<String> recipeList;
    private ImageDisplayFrame imageDisplayFrame; // Declare a field for ImageDisplayFrame

    public RecipeDisplayFrame(ArrayList<Recipe> recipes) {
        super("Recipe Display");

        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        imageDisplayFrame = new ImageDisplayFrame(); // Initialize ImageDisplayFrame

        // Add recipes to the list model
        for (Recipe recipe : recipes) {
            recipeListModel.addElement(getRecipeDisplayText(recipe));
        }

        // Set up the UI
        setupUI(recipes);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI(ArrayList<Recipe> recipes) {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(recipeList);
        add(scrollPane, BorderLayout.CENTER);

        add(imageDisplayFrame, BorderLayout.SOUTH); // Add the ImageDisplayFrame to the SOUTH

        // Set up the list selection listener
        recipeList.addListSelectionListener(e -> {
            int selectedIndex = recipeList.getSelectedIndex();
            if (selectedIndex != -1) {
                Recipe selectedRecipe = recipes.get(selectedIndex);
                ImageIcon imageIcon = Tools.getRecipeImage(selectedRecipe.generateImagePath());
                if (imageIcon != null) {
                    // Update the existing ImageDisplayFrame with the new image
                    imageDisplayFrame.updateImage(imageIcon);
                } else {
                    System.out.println("Image not loaded for recipe: " + selectedRecipe.getName());
                }
            }
        });
    }

    private String getRecipeDisplayText(Recipe recipe) {
        StringBuilder displayText = new StringBuilder();
        displayText.append("Recipe Name: ").append(recipe.getName()).append("\n");
        displayText.append("Ingredients:\n");
    
        // Iterate through ingredients and append them to displayText

        for (Recipe.Ingredient ingredient : recipe.getIngredients()) {
            StringBuilder ingredientText = new StringBuilder("- ");
            ingredientText.append(ingredient.getIngredientName()).append(": ");
            
            if (ingredient.getQuantity() != null) {
                ingredientText.append(ingredient.getQuantity()).append(" ")
                        .append(ingredient.getMeasurement());
            } else {
                ingredientText.append("to taste");
            }
            
            ingredientText.append("\n");
            displayText.append(ingredientText);
        }
    
        // Append nutrition information
        displayText.append("\nNutrition Information:\n");
        Recipe.Nutrition nutrition = recipe.getNutrition();
        if (nutrition != null) {
            displayText.append("- Calories: ").append(nutrition.getCalories()).append("\n");
            displayText.append("- Fat: ").append(nutrition.getFat()).append("g\n");
            displayText.append("- Saturates: ").append(nutrition.getSaturates()).append("g\n");
            displayText.append("- Carbs: ").append(nutrition.getCarbs()).append("g\n");
            displayText.append("- Sugars: ").append(nutrition.getSugars()).append("g\n");
            displayText.append("- Fibre: ").append(nutrition.getFibre()).append("g\n");
            displayText.append("- Protein: ").append(nutrition.getProtein()).append("g\n");
            displayText.append("- Salt: ").append(nutrition.getSalt()).append("g\n");
        } else {
            displayText.append("No nutrition information available\n");
        }
    
        return displayText.toString();
    }
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Recipe> recipes = getRecipesFromDataSource();

            new RecipeDisplayFrame(recipes);
        });
    }

    private static ArrayList<Recipe> getRecipesFromDataSource() {
        try {
            return DeserializeJsonData.initaliseRecipeClass();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

class ImageDisplayFrame extends JPanel { // Extend JPanel instead of JFrame for ImageDisplayFrame

    private JLabel imageLabel;

    public ImageDisplayFrame() {
        setLayout(new BorderLayout()); // Use BorderLayout for the panel

        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER); // Add the imageLabel to the CENTER of the panel

        setPreferredSize(new Dimension(300, 200)); // Set the preferred size of the panel
    }

    // Method to update the displayed image
    public void updateImage(ImageIcon imageIcon) {
        imageLabel.setIcon(imageIcon);
        repaint(); // Repaint the panel
    }
}

