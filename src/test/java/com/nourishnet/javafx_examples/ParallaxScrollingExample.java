package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ParallaxScrollingExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating background layers
        Rectangle backgroundLayer1 = createRectangle(400, 300, Color.LIGHTBLUE, -1);
        Rectangle backgroundLayer2 = createRectangle(400, 300, Color.LIGHTGREEN, -0.5);

        // Creating foreground content
        Label foregroundContent = new Label("Foreground Content");
        foregroundContent.setStyle("-fx-font-size: 24;");

        // Creating a stack pane to layer elements
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundLayer1, backgroundLayer2, foregroundContent);

        // Creating a VBox to contain the stack pane
        VBox root = new VBox();
        root.getChildren().add(stackPane);
        VBox.setMargin(stackPane, new Insets(50)); // Add margin for better visibility

        // Creating the scene
        Scene scene = new Scene(root, 400, 300);

        // Adding a scroll listener to create the parallax effect
        scene.setOnScroll(event -> {
            backgroundLayer1.setTranslateY(backgroundLayer1.getTranslateY() + event.getDeltaY() * 0.1);
            backgroundLayer2.setTranslateY(backgroundLayer2.getTranslateY() + event.getDeltaY() * 0.1);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Parallax Scrolling Example");
        primaryStage.show();
    }

    private Rectangle createRectangle(double width, double height, Color color, double translateY) {
        Rectangle rectangle = new Rectangle(width, height, color);
        rectangle.setTranslateY(translateY);
        return rectangle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

