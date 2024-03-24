package com.nourishnet.javafx_examples;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class ShakingButtonExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 400, 300);

        Button button = new Button("Click Me!");
        button.setLayoutX(150);
        button.setLayoutY(100);

        button.setOnAction(event -> shakeButton(button));

        pane.getChildren().add(button);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Shaking Button");
        primaryStage.show();
    }

    private void shakeButton(Button button) {
        Random random = new Random();

        Timeline timeline = new Timeline();
        for (int i = 0; i < 20; i++) {
            int deltaX = random.nextInt(10) - 5;
            int deltaY = random.nextInt(10) - 5;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 50),
                    new KeyValue(button.translateXProperty(), deltaX),
                    new KeyValue(button.translateYProperty(), deltaY));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setOnFinished(event -> {
            button.setTranslateX(0);
            button.setTranslateY(0);
        });
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

