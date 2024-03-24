package com.nourishnet.javafx_examples;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransformsAndTransitionsExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a button
        Button button = new Button("Click Me!");
        button.setPrefSize(100, 50);

        // Create translate transition
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), button);
        translateTransition.setFromX(0);
        translateTransition.setToX(200);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);

        // Create rotate transition
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), button);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        // Create scale transition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), button);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        // Start all transitions
        translateTransition.play();
        rotateTransition.play();
        scaleTransition.play();

        // Add button to layout
        StackPane root = new StackPane(button);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Transforms and Transitions Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

