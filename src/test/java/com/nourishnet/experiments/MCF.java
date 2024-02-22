package com.nourishnet.experiments;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MCF extends JFrame {
    private JPanel outerPanel; // Outer panel to hold the scrollable inner panel
    private JPanel innerPanel; // Inner panel to hold multiple containers horizontally

    public MCF(ArrayList<r> recipes) {
        setTitle("Recipe Collection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));

        initUI(recipes);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI(ArrayList<r> recipes) {
        outerPanel = new JPanel(new BorderLayout()); // Outer panel uses BorderLayout
    
        // Add some text at the top of the main frame
        JLabel headerLabel = new JLabel("Recipe Collection");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outerPanel.add(headerLabel, BorderLayout.NORTH);
    
        innerPanel = new JPanel(); // Inner panel to hold multiple containers horizontally
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS)); // Use BoxLayout to arrange containers horizontally
        innerPanel.setBackground(Color.WHITE); // Set background color of the container
    
        // Add containers for each recipe
        for (r recipe : recipes) {
            JPanel container = createContainer(recipe);
            innerPanel.add(container);
        }
    
        JScrollPane innerScrollPane = new JScrollPane(innerPanel); // Scroll pane for the inner panel
        innerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
        // Set the preferred height of the inner panel to a smaller size
        Dimension smallerSize = new Dimension(innerPanel.getPreferredSize().width, 200);
        innerPanel.setPreferredSize(smallerSize);
    
        JTextArea textArea = new JTextArea(); // Text area for additional text
        textArea.setText("This is a collection of various recipes. Enjoy exploring!");
        textArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(textArea); // Scroll pane for the text area
        outerPanel.add(textScrollPane, BorderLayout.SOUTH); // Add the text area to the bottom of the outer panel
    
        outerPanel.add(innerScrollPane, BorderLayout.CENTER); // Add the inner scroll pane to the center of the outer panel
    
        add(outerPanel, BorderLayout.CENTER); // Add the outer panel to the center of the frame
    
        // Set the size of the frame
        setSize(800, 600);
    }
    
    
    
    
    private JPanel createContainer(r recipe) {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(200, 200)); // Set preferred size for each container
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border to each container

        // Add components for recipe details (name, image, etc.) to the container
        JLabel nameLabel = new JLabel(recipe.getName());
        container.add(nameLabel);

        // Add more components as needed for other recipe details

        return container;
    }

    public static void main(String[] args) {
        // Sample recipes
        ArrayList<r> recipes = new ArrayList<>();
        recipes.add(new r("Recipe 1"));
        recipes.add(new r("Recipe 2"));
        recipes.add(new r("Recipe 3"));
        recipes.add(new r("Recipe 4"));
        recipes.add(new r("Recipe 5"));
        recipes.add(new r("Recipe 6"));
        recipes.add(new r("Recipe 7"));
        recipes.add(new r("Recipe 8"));
        recipes.add(new r("Recipe 9"));
        recipes.add(new r("Recipe 10"));
        recipes.add(new r("Recipe 11"));
        recipes.add(new r("Recipe 12"));
        recipes.add(new r("Recipe 13"));
        recipes.add(new r("Recipe 14"));
        recipes.add(new r("Recipe 15"));

        SwingUtilities.invokeLater(() -> new MCF(recipes));
    }
}

class r {
    private String name;

    // Constructor with a single String parameter for the recipe name
    public r(String name) {
        this.name = name;
    }

    // Getter method for the recipe name
    public String getName() {
        return name;
    }
}
