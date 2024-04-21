package com.nourishnet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RecipeDisplayFX extends Application {

    private ListView<String> recipeListView;
     
    private ImageDisplayPane imageDisplayPane;

    @Override
    public void start(Stage primaryStage) {
        ArrayList<Recipe> recipes = getRecipesFromDataSource();

        recipeListView = new ListView<>();
        
        imageDisplayPane = new ImageDisplayPane();

        // Populate recipe list view
        for (Recipe recipe : recipes) {
            recipeListView.getItems().add(getRecipeDisplayText(recipe));
        }

        // Event handler for selecting a recipe
        recipeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = recipeListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Recipe selectedRecipe = recipes.get(selectedIndex);
                Image image = Tools.getRecipeImage(selectedRecipe.generateImageName());
                if (image != null) {
                    imageDisplayPane.updateImage(image);
                } else {
                    System.out.println("Image not loaded for recipe: " + selectedRecipe.getName());
                }
            }
        });

        VBox leftPane = new VBox(recipeListView);
        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setBottom(imageDisplayPane);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recipe Display");
        primaryStage.show();
    }

    private String getRecipeDisplayText(Recipe recipe) {
      return recipe.getName();
    }

    private ArrayList<Recipe> getRecipesFromDataSource() {
        return ResourceLoader.loadRecipes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ImageDisplayPane extends ImageView {
    public ImageDisplayPane() {
        setFitWidth(300);
        setFitHeight(200);
        setPreserveRatio(true);
    }

    public void updateImage(Image image) {
        setImage(image);
    }
}