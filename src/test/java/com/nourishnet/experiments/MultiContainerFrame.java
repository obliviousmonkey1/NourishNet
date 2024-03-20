package com.nourishnet.experiments;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nourishnet.UserManager;
import com.nourishnet.Recipe;
import com.nourishnet.ResourceLoader;
import com.nourishnet.Search;
import com.nourishnet.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MultiContainerFrame extends JFrame {
    private ArrayList<Recipe> recipes;
    private JPanel containerPanel;
    private User user;
    private boolean isShowAllRecipes;

    public MultiContainerFrame(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.user = ResourceLoader.loadUser(UserManager.getUserJsonPath("0004"));
        this.isShowAllRecipes = true;

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
        JButton showAllRecipesButton = new JButton("Show All Recipes");
        searchPanel.add(searchField);
        searchPanel.add(showAllRecipesButton);

        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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
            }
        });

        showAllRecipesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        containerPanel.removeAll();
        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(query, recipes, user.getSavedRecipeIDs(), user.getDiet(), isShowAllRecipes);

        for (Recipe recipe : returnedRecipes) {
            JPanel container = Containers.createContainer(recipe);
            containerPanel.add(container);
        }

        revalidate();
        repaint();
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
