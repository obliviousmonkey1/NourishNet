package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FlexboxExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");

        // Create an HBox container
        HBox hbox = new HBox();
        hbox.getChildren().addAll(button1, button2, button3);

        // Apply Flexbox properties
        hbox.setSpacing(10); // Set spacing between buttons
        hbox.setStyle("-fx-padding: 10;" +
                "-fx-background-color: #f0f0f0;" + // Background color
                "-fx-alignment: center;"); // Center align buttons

        Scene scene = new Scene(hbox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flexbox Layout Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

