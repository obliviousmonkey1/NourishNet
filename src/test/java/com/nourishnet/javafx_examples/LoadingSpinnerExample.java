package com.nourishnet.javafx_examples;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingSpinnerExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create an Arc (part of a circle) for the spinner
        Arc arc = new Arc();
        arc.setCenterX(50);
        arc.setCenterY(50);
        arc.setRadiusX(30);
        arc.setRadiusY(30);
        arc.setStartAngle(45);
        arc.setLength(270);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLUE);
        arc.setStrokeWidth(4);

        // Create a timeline for the spinner animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(arc.startAngleProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(arc.startAngleProperty(), 360))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Start the animation
        timeline.play();

        // Create a StackPane to hold the spinner
        StackPane root = new StackPane(arc);
        root.setPrefSize(100, 100);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Loading Spinner Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

