package com.nourishnet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;

import java.util.ArrayList;

public class IngredientSearchTest extends Application {

    private static ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();

    private static ArrayList<DataStructures.StringPair> ingredients = Tools.getAllIngredients(recipes);
    
    private static ArrayList<DataStructures.StringIntPair> selectedIngredients = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Application");
    
        // Create main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
    
        // Add New Recipe button
        Button addButton = new Button("Find Ingredient");
        Button searchButton = new Button("Search");
       
    
        // List view for selected ingredients
        ListView<String> selectedIngredientsListView = new ListView<>();
        mainLayout.getChildren().addAll(addButton,searchButton, selectedIngredientsListView);
    
        addButton.setOnAction(e -> openAddRecipeWindow(selectedIngredientsListView));
        searchButton.setOnAction(e -> search());

    
        // Enable deleting ingredients from the list
        selectedIngredientsListView.setCellFactory(param -> new ListCell<String>() {
            Button deleteButton = new Button("X");
        
            {
                deleteButton.setOnAction(event -> {
                    String ingredient = getItem();
                    getListView().getItems().remove(ingredient);
                    selectedIngredients.removeIf(pair -> pair.getText().equals(ingredient)); // Remove from selectedIngredients list

                });
            }
        
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setGraphic(deleteButton);
                }
            }
        });
        
        updateSelectedIngredientsListView(selectedIngredientsListView);
    
        Scene scene = new Scene(mainLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    private void openAddRecipeWindow( ListView<String> selectedIngredientsListView) {
        Stage addRecipeStage = new Stage();
        addRecipeStage.initModality(Modality.APPLICATION_MODAL);
        addRecipeStage.setTitle("Find Ingredient");

        // Create layout for the add recipe window
        VBox addRecipeLayout = new VBox(10);
        addRecipeLayout.setPadding(new Insets(10));

        // Search bar for ingredients
        Label searchLabel = new Label("Search Ingredients:");
        TextField searchField = new TextField();
        ListView<String> ingredientListView = new ListView<>();

        // Populate ListView with all ingredients initially
        ArrayList<String> ingredientNames = new ArrayList<>();
        for (DataStructures.StringPair ingredient : ingredients) {
            ingredientNames.add(ingredient.getVar());
        }
        ingredientListView.setItems(FXCollections.observableArrayList(ingredientNames));

        // Filter the ListView based on the search query
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ingredientListView.getItems().clear();
            for (DataStructures.StringPair ingredient : ingredients) {
                if (ingredient.getVar().toLowerCase().contains(newValue.toLowerCase())) {
                    ingredientListView.getItems().add(ingredient.getVar());
                }
            }
        });
        
        
        // Text fields for ingredient name and amount
        TextField ingredientNameField = new TextField();
        ingredientNameField.setEditable(false);
        ingredientNameField.setPromptText("Ingredient Name");
        TextField ingredientAmountField = new TextField();
        ingredientAmountField.setPromptText("Amount");
        
        ingredientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ingredientNameField.setText(newValue);
                for (DataStructures.StringPair ingredient : ingredients) {
                    if (ingredient.getVar().equals(newValue)) {
                        // Update the text field with the measurement
                        ingredientAmountField.clear(); // Clear any previous value
                        ingredientAmountField.setPromptText(ingredient.getVar2());
                        break; // No need to continue searching 
                    }
                }
                // Do something with the clicked ingredient
            }
        });
        // Button to add ingredient
        Button addButton = new Button("Add Ingredient");
        addButton.setOnAction(e -> {
            String ingredientName = ingredientNameField.getText();
            String amount = ingredientAmountField.getText();
            if (amount.isEmpty()) {
                amount = "-1";
                selectedIngredients.add(new DataStructures.StringIntPair(ingredientName, 0));
            } else {
                try {
                    selectedIngredients.add(new DataStructures.StringIntPair(ingredientName, Integer.parseInt(amount)));
                } catch (NumberFormatException p) {
                    // Handle the case where amount is not a valid integer
                    // You may want to log the exception or show an error message to the user
                }
            }

            // Update the list view and clear the text fields
            updateSelectedIngredientsListView(selectedIngredientsListView);
            ingredientNameField.clear();
            ingredientAmountField.clear();
            ((Stage) addButton.getScene().getWindow()).close();
            
        });

        addRecipeLayout.getChildren().addAll(searchLabel, searchField, ingredientListView, ingredientNameField, ingredientAmountField, addButton);

        Scene scene = new Scene(addRecipeLayout, 300, 400);
        addRecipeStage.setScene(scene);
        addRecipeStage.showAndWait(); // Show the window and wait for it to be closed before returning
    }

    private void updateSelectedIngredientsListView(ListView<String> listView) {
        ArrayList<String> selectedIngredientNames = new ArrayList<>();
        for (DataStructures.StringIntPair ingredient : selectedIngredients) {

            Integer amount = ingredient.getNumber();
            if (amount != null && amount > 0) {
                selectedIngredientNames.add(ingredient.getText() + " - " + amount);
            } else {
                selectedIngredientNames.add(ingredient.getText());
            }
                
        }
        listView.setItems(FXCollections.observableArrayList(selectedIngredientNames));

       
    }

    public static void search(){
        ArrayList<Recipe> results = Search.getIngredientsSearch(recipes, selectedIngredients, true, "Vegan");

        for (Recipe recipe : results) {
            System.out.println(recipe.getName());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
