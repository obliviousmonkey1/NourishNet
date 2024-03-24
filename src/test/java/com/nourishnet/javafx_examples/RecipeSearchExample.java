package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.nourishnet.Recipe;
import com.nourishnet.ResourceLoader;
import com.nourishnet.Search;
import com.nourishnet.User;
import com.nourishnet.UserManager;
import java.util.ArrayList;
import javafx.scene.layout.HBox;



public class RecipeSearchExample extends Application {
    private ArrayList<Recipe> recipes;
    private VBox containerVBox;
    private User user;
    private boolean isShowAllRecipes;

    @Override
    public void start(Stage primaryStage) {
        recipes = ResourceLoader.loadRecipes();
        user = ResourceLoader.loadUser(UserManager.getUserJsonPath("0000"));
        isShowAllRecipes = true;
    
        primaryStage.setTitle("Search Recipes");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
    
        // Search field and button
        TextField searchField = new TextField();
        searchField.setPromptText("Search recipes");
        searchField.setStyle("-fx-font-size: 20px;");
        Button showAllRecipesButton = new Button("Show All Recipes");
        HBox searchBox = new HBox(10, searchField, showAllRecipesButton);
        searchBox.setAlignment(Pos.CENTER);
        root.setTop(searchBox);
    
        // Container VBox for recipe cards
        containerVBox = new VBox(10);
        containerVBox.setPadding(new Insets(10)); // Padding around the VBox
        ScrollPane scrollPane = new ScrollPane(containerVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide vertical scroll bar
        root.setCenter(scrollPane);
    
        // Add padding to the VBox to create a border between the sides of the window
        VBox.setMargin(containerVBox, new Insets(0, 100, 0, 100)); // Insets: top, right, bottom, left
    
        // Event handlers
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                updateDisplayedRecipes(newValue.toLowerCase()));
    
        showAllRecipesButton.setOnAction(event -> {
            isShowAllRecipes = !isShowAllRecipes;
            if (isShowAllRecipes) {
                showAllRecipesButton.setText("Show Diet Related Recipes");
            } else {
                showAllRecipesButton.setText("Show All Recipes");
            }
            updateDisplayedRecipes(searchField.getText().toLowerCase());
        });
    
        // Display the scene
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    
        // Initial display of recipes
        updateDisplayedRecipes("");
    }
    


    private void updateDisplayedRecipes(String query) {
        containerVBox.getChildren().clear();
        ArrayList<Recipe> returnedRecipes = Search.getRecipeSearchResults(
                query, recipes, user.getSavedRecipeIDs(), user.getDiet(), isShowAllRecipes);

        for (Recipe recipe : returnedRecipes) {
            VBox container = createContainer(recipe);
            containerVBox.getChildren().add(container);
        }
    }

    private VBox createContainer(Recipe recipe) {
        VBox container = new VBox(5);
        container.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
    
        // Name label
        Label nameLabel = new Label("Name: " + recipe.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");
    
        // Circular button
        ToggleButton starButton = new ToggleButton();
        starButton.setShape(new Circle(10)); // Set button shape to a circle
        starButton.setStyle("-fx-background-color: gray;"); // Default color
        starButton.setOnAction(event -> {
            if (starButton.isSelected()) {
                starButton.setStyle("-fx-background-color: yellow;"); // Change to yellow when clicked
            } else {
                starButton.setStyle("-fx-background-color: gray;"); // Change back to gray when unclicked
            }
        });
    
        HBox nameBox = new HBox(nameLabel, starButton);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setSpacing(10); // Spacing between name label and button
    
        // Description label
        Label descriptionLabel = new Label("Description: " + recipe.getDescription());
    
        // Diet label
        Label dietLabel = new Label("Diet: " + String.join(", ", recipe.getDiet()));
    
        // Tags label
        Label tagsLabel = new Label("Tags: " + String.join(", ", recipe.getTags()));

        Button viewRecipeButton = new Button("View Recipe");
        viewRecipeButton.setOnAction(event -> {
            // Create a new stage to display the recipe name
            Stage recipeStage = new Stage();
            recipeStage.initModality(Modality.APPLICATION_MODAL);
            recipeStage.setTitle(recipe.getName());
            
            // Create a label to display the recipe name
            Label recipeNameLabel = new Label(recipe.getName());
            recipeNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            
            // Create a VBox to hold the label and center it
            VBox recipeNameBox = new VBox(recipeNameLabel);
            recipeNameBox.setAlignment(Pos.CENTER);
            
            // Create the scene and set it in the stage
            Scene recipeScene = new Scene(recipeNameBox, 400, 300); // Adjust the size as needed
            recipeStage.setScene(recipeScene);
            
            // Show the stage
            recipeStage.show();
        });

    // Create an HBox to hold the button
    HBox buttonBox = new HBox(viewRecipeButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));
    container.getChildren().addAll(nameBox, descriptionLabel, dietLabel, tagsLabel);

// Add the buttonBox to the bottom of the container VBox
container.getChildren().add(buttonBox);
    
        return container;
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}

