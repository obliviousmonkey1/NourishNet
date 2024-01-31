package com.nourishnet;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecipeDisplayFrame extends JFrame {

    private DefaultListModel<String> recipeListModel;
    private JList<String> recipeList;
    private JPanel imagePanel; // Create a separate panel for images

    public RecipeDisplayFrame(ArrayList<Recipe> recipes) {
        super("Recipe Display");

        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use a flow layout for imagePanel

        // Add recipes to the list model
        for (Recipe recipe : recipes) {
            recipeListModel.addElement(getRecipeDisplayText(recipe));
        }

        // Set up the UI
        setupUI(recipes);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void setupUI(ArrayList<Recipe> recipes) {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(recipeList);
        add(scrollPane, BorderLayout.CENTER);

        add(imagePanel, BorderLayout.SOUTH); // Add the imagePanel to the SOUTH

        // Set up the list selection listener
        recipeList.addListSelectionListener(e -> {
            int selectedIndex = recipeList.getSelectedIndex();
            if (selectedIndex != -1) {
                Recipe selectedRecipe = recipes.get(selectedIndex);
                ImageIcon imageIcon = Tools.getRecipeImage(selectedRecipe.getName());
                if (imageIcon != null) {
                    SwingUtilities.invokeLater(() -> new ImageDisplayFrame(imageIcon));
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

        for (Ingredient ingredient : recipe.getIngredients()) {
            displayText.append("- ").append(ingredient.getName()).append(": ")
                    .append(ingredient.getCaloriesPer100g()).append(" calories per 100g\n");
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
            return DeserializeUserData.initaliseRecipeClass();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

class ImageDisplayFrame extends JFrame {

    public ImageDisplayFrame(ImageIcon imageIcon) {
        super("Image Display");

        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
