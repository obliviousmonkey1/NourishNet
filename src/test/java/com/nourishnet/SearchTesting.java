package com.nourishnet;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

public class SearchTesting extends JFrame {

    private JTextField textField;
    private JLabel suggestionLabel;
    private ArrayList<String> suggestionList;
    private ArrayList<Recipe> recipes;
    private ArrayList<Ingredient> ingredients;
    private User user;

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
        suggestionList = new ArrayList<>();
        suggestionList.add("Java");
        suggestionList.add("JavaScript");
        suggestionList.add("Python");
        suggestionList.add("C++");
        suggestionList.add("C#");

        textField = new JTextField();
        suggestionLabel = new JLabel("Suggestions: ");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(textField);
        add(suggestionLabel);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                performSearch();
            }
        });
    }

    
    private void performSearch() {
        String query = textField.getText().toLowerCase();
        StringBuilder suggestions = new StringBuilder("Suggestions: ");
    
        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(query, recipes, user.getSavedRecipeIDs(), user.getDiet());
        
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
        user = ResourceLoader.loadUser(LogIn.getUserJsonPath("Tom"));
        ingredients = ResourceLoader.loadIngredients();
        recipes = ResourceLoader.loadRecipes();
        
        for (int i = 0; i< recipes.size(); i++){
            recipes.get(i).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipes.get(i).getQuantitiesNames(), ingredients));
        }
        
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SearchTesting();
        });
    }
}
