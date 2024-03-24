package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CardLayoutExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create cards
        StackPane card1 = createCard("Card 1", Color.LIGHTBLUE);
        StackPane card2 = createCard("Card 2", Color.LIGHTGREEN);
        StackPane card3 = createCard("Card 3", Color.LIGHTCORAL);

        // Stack cards horizontally
        HBox hbox = new HBox(10); // 10 pixels spacing between cards
        hbox.getChildren().addAll(card1, card2, card3);

        // Add padding to the layout
        hbox.setPadding(new Insets(20));

        // Create the scene
        Scene scene = new Scene(hbox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Card Layout Example");
        primaryStage.show();
    }

    private StackPane createCard(String title, Color color) {
        Rectangle background = new Rectangle(100, 150, color); // Card size
        background.setArcWidth(20);
        background.setArcHeight(20);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        StackPane card = new StackPane();
        card.getChildren().addAll(background, titleLabel);

        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
