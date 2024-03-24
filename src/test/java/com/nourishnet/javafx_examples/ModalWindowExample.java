package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalWindowExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main window content
        Button openModalButton = new Button("Open Modal");
        openModalButton.setOnAction(e -> openModal());

        StackPane root = new StackPane(openModalButton);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Window");
        primaryStage.show();
    }

    private void openModal() {
        // Create the modal stage
        Stage modalStage = new Stage();
        modalStage.initOwner(null); // Null owner, so the modal can be shown independently
        modalStage.initModality(Modality.APPLICATION_MODAL); // Application modal

        // Create modal content
        Rectangle background = new Rectangle(300, 200, Color.LIGHTGRAY);
        VBox modalContent = new VBox(10);
        modalContent.setAlignment(Pos.CENTER);
        modalContent.getChildren().addAll(new Button("Close Modal"), new Button("Save"));

        StackPane modalRoot = new StackPane(background, modalContent);

        // Styling and settings
        modalRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background
        modalStage.initStyle(StageStyle.TRANSPARENT); // No window decorations

        // Close button action
        ((Button) modalContent.getChildren().get(0)).setOnAction(e -> modalStage.close());

        // Scene and show
        Scene modalScene = new Scene(modalRoot);
        modalStage.setScene(modalScene);
        modalStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

