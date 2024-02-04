package com.nourishnet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchTesting extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;

    public SearchTesting() {
        // Set up the JFrame
        setTitle("Search Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create components
        searchField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the JFrame
        add(searchField, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);


        // Add key listener to the search field to listen for "Enter" key
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not needed for this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not needed for this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Perform search action when Enter key is pressed
                    String query = searchField.getText();
                    performSearch(query);
                }
            }
        });
    }

    private void performSearch(String query) {
        // TODO: Implement your search logic here
        // For simplicity, let's just display the query in the result area

        // call search class which will search for recipes then diets then ingredients and pool the most likely ones together 
        // unless we decide to have them all seperate in there on jframes then we just search over which frame the
        // user is in at that moment  
        resultArea.setText("Search results for: " + query);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SearchTesting().setVisible(true);
            }
        });
    }
}
