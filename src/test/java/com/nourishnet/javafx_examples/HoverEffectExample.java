package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HoverEffectExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Create a label
        Label titleLabel = new Label("NourishNet");

        // Apply initial style
        titleLabel.setStyle(
                "-fx-font-size: 4em; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);"
        );

        // Add the label to the top of the BorderPane
        borderPane.setTop(titleLabel);

        // Create a drop shadow effect
        DropShadow dropShadow = new DropShadow();

        // Apply drop shadow effect on hover
        titleLabel.setOnMouseEntered(e -> titleLabel.setEffect(dropShadow));

        // Remove drop shadow effect when mouse exits
        titleLabel.setOnMouseExited(e -> titleLabel.setEffect(null));

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hover Effect Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
