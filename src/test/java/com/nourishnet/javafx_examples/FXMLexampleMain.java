package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLexampleMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("FXML Example");
        primaryStage.setScene(new Scene(root, 400, 300));
        
        primaryStage.setFullScreen(true); // Set to fullscreen

        // Set the controller defined in the FXML file
        FXMLexampleController controller = loader.getController();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
