package com.nourishnet.experiments;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MovableScene extends Application {

    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) {
        // Create a rectangle to represent the movable panel
        Rectangle rectangle = new Rectangle(100, 100, Color.BLUE);

        // Create a Pane to hold the rectangle
        Pane pane = new Pane(rectangle);

        // Set event handlers for mouse pressed and dragged events
        rectangle.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX() - rectangle.getTranslateX();
            yOffset = event.getSceneY() - rectangle.getTranslateY();
        });

        rectangle.setOnMouseDragged((MouseEvent event) -> {
            double newX = event.getSceneX() - xOffset;
            double newY = event.getSceneY() - yOffset;

            // Make sure the panel doesn't move outside the scene bounds
            if (newX >= 0 && newX <= pane.getWidth() - rectangle.getWidth()) {
                rectangle.setTranslateX(newX);
            }
            if (newY >= 0 && newY <= pane.getHeight() - rectangle.getHeight()) {
                rectangle.setTranslateY(newY);
            }
        });

        // Create a scene with the pane
        Scene scene = new Scene(pane, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Movable Scene");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

