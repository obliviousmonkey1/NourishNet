package com.nourishnet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class SearchBar {
    private boolean isShowAllRecipes;
    private User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("Tom"));
    private ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
    private JTextField searchBar = new JTextField(20);
    private JTextArea queryDisplay = new JTextArea(5, 20);

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Search Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
    
        JPanel panel = new JPanel(new BorderLayout());
    
        searchBar.setText("Enter your search term");
    
        // Add FocusListener to clear text when search bar gains focus
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchBar.setText("");
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                // No action needed when focus is lost
            }
        });
    
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });
    
        JButton searchButton = new JButton("Search");
        queryDisplay.setEditable(false);
    
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    
        JButton clearButton = new JButton("Clear Query");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryDisplay.setText("");
            }
        });
    
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.add(searchBar);
        searchBarPanel.add(searchButton);
    
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(searchBarPanel, BorderLayout.NORTH);
        bottomPanel.add(queryDisplay, BorderLayout.CENTER);
        bottomPanel.add(clearButton, BorderLayout.SOUTH);
    
        panel.add(bottomPanel, BorderLayout.CENTER);
    
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    

    private void performSearch() {
        String query = searchBar.getText().toLowerCase();
        StringBuilder suggestions = new StringBuilder("Suggestions: ");

        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(query, recipes, user.getSavedRecipeIDs(), user.getDiet(), isShowAllRecipes);

        System.out.println(returnedRecipes.size());
        for (Recipe recipe : returnedRecipes) {
            suggestions.append(recipe.getName()).append(", ");
        }

        if (suggestions.length() > 14) {
            suggestions.setLength(suggestions.length() - 2);
        }

        queryDisplay.setText(suggestions.toString());
    }

    public static void main(String[] args) {
        SearchBar searchBar = new SearchBar();
        searchBar.createAndShowGUI();
    }}
