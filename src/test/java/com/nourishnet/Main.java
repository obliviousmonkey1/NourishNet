package com.nourishnet;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.animation.*;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.layout.*;
import javafx.scene.paint.*;

import java.io.File;


import java.util.Random;
import java.util.List;

public class Main extends Application {


    private static final String BACKGROUND_COLOUR = "#AFC4B0";  // Lighter green
    private static final String PANEL_COLOUR = "#ACB7AB"; // Panel colour
    private static final String USER_PANEL_COLOUR = "D9D9D9";
    private static final String BUTTON_CLICKED_COLOUR = "rgba(129, 171, 127, 0.8)"; // Light green with 80% opacity
    private static final String BUTTON_DEFAULT_COLOUR = "#ACB7AB"; // Light blue

    private Text introductionText;
    public static Label nourishnetLabel;
    private Button lastClickedButton;
    private int music = 0;

    private VBox rightBox;
    private VBox loginBox;
    private User user;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        
        String musicFile = "/Users/parzavel/Documents/GitHub/nourishnet/src/test/java/com/nourishnet/loginTheme1.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:" + BACKGROUND_COLOUR);

        // Creating the left side (profiles)
        VBox profileBox = new VBox();
        profileBox.setStyle("-fx-background-color:" + USER_PANEL_COLOUR); // Set the background color

        profileBox.setSpacing(20);
        profileBox.setPadding(new Insets(20, 10, 20, 10)); // Reduced bottom padding to 20



        // Add profile buttons
        List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            DataStructures.StringImageIdPair profile = profiles.get(i);
            Button profileButton = new Button();
            VBox profileContent = new VBox(); // VBox to stack image and label

            ImageView imageView = new ImageView();
            imageView.setImage(profile.getImage());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

            // Profile name label
            Label nameLabel = new Label(profile.getText());
            nameLabel.setStyle("-fx-font-size: 1.5em; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");

            // Stack image and label vertically
            profileContent.getChildren().addAll(imageView, nameLabel);
            profileContent.setAlignment(Pos.BASELINE_CENTER); // Center content
            profileContent.getStyleClass().add("button-profile"); // Apply CSS class

            profileButton.setGraphic(profileContent);
            profileButton.getStyleClass().add("button-profile"); // Apply CSS class
            profileButton.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height

            DropShadow mdropShadow = new DropShadow();

            profileButton.setOnAction(e -> {
                User tempUser = ResourceLoader.loadUser(UserManager.getUserJsonPath(profile.getId()));
                if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
                    mediaPlayer.stop();
                }
                // double randomInRange = Math.random() * 100;
                // int randomNumber = (int) randomInRange;
                // System.out.println(randomNumber);
                if(music == 1){
                    mediaPlayer.play();
                    handleProfileButtonClick();
                }
                
                // if(randomNumber == 20){
                //     mediaPlayer.play();

                // }
                // Handle profile button click
                introductionText.setVisible(false);
                profileButton.setEffect(mdropShadow);

                if (lastClickedButton != null) {
                    lastClickedButton.setEffect(null);
                   // lastClickedButton.setStyle("-fx-background-color: " + BUTTON_DEFAULT_COLOUR); // Reset previous button color
                }
                lastClickedButton = profileButton;
               
                // Display the password field popup
                displayLoginField(primaryStage, tempUser.getHasPassword(), tempUser);

                //profileButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
            });
            profileBox.getChildren().add(profileButton);
        }

        // Scroll pane for profile buttons
        ScrollPane scrollPane = new ScrollPane(profileBox);
        scrollPane.setFitToWidth(true); // Make sure the width fits the content
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide vertical scrollbar



        borderPane.setLeft(scrollPane);
        

        rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(20));
        rightBox.setSpacing(20);
        rightBox.setPrefWidth(200); // Adjust as needed

        nourishnetLabel = new Label("NourishNet");
        nourishnetLabel.setStyle(
                "-fx-font-size: 4em; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);"
        );
        rightBox.getChildren().add(nourishnetLabel);
        rightBox.setAlignment(Pos.TOP_CENTER);

        DropShadow dropShadow = new DropShadow();

        // Apply drop shadow effect on hover
        nourishnetLabel.setOnMouseEntered(e -> nourishnetLabel.setEffect(dropShadow));

        nourishnetLabel.setOnMouseClicked(e -> {
            mediaPlayer.play();
        });

    
        // Remove drop shadow effect when mouse exits
        nourishnetLabel.setOnMouseExited(e -> nourishnetLabel.setEffect(null));

        introductionText = new Text("NourishNet: Zero Hunger, One Recipe at a time - Sustainably Nourishing Lives!");
        introductionText.setStyle("-fx-font-size: 1.5em; -fx-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        rightBox.getChildren().add(introductionText);
        rightBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(rightBox);
        

        BorderPane.setAlignment(nourishnetLabel, Pos.TOP_CENTER);

       


        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

   private void handleProfileButtonClick() {
    // Apply pulse effect and change text color to red
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), nourishnetLabel);
    scaleTransition.setFromX(1);
    scaleTransition.setFromY(1);
    scaleTransition.setToX(1.2);
    scaleTransition.setToY(1.2);
    scaleTransition.setAutoReverse(true);
    scaleTransition.setCycleCount(Timeline.INDEFINITE); // Play indefinitely

    nourishnetLabel.setTextFill(Color.RED);

    scaleTransition.play();
}
    

    private void displayLoginField(Stage primaryStage, boolean hasPassword, User tempUser) {
        // Creating the password field popup


        DropShadow mdropShadow = new DropShadow();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: " + PANEL_COLOUR);

    
        Label profileLabel = new Label("Profile: " + tempUser.getUsername());
        profileLabel.setStyle("-fx-font-size: 1.5em; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        GridPane.setConstraints(profileLabel, 0, 0);
        GridPane.setHalignment(profileLabel, HPos.CENTER);
        GridPane.setColumnSpan(profileLabel, 2);

    
        if (hasPassword) {
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
            GridPane.setConstraints(passwordLabel, 0, 1);
            GridPane.setMargin(passwordLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
            passwordLabel.setEffect(mdropShadow);;

            PasswordField passwordInput = new PasswordField();
            passwordInput.setPrefWidth(400); // Increase password field width
            passwordInput.setPrefHeight(80); // Increase password field height
            GridPane.setConstraints(passwordInput, 1, 1);
            GridPane.setMargin(passwordInput, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
            passwordInput.setEffect(mdropShadow);


            Button loginButton = new Button("Login");
            loginButton.setPrefWidth(100);
            loginButton.setPrefHeight(40);
            GridPane.setConstraints(loginButton, 1, 2);
            GridPane.setColumnSpan(loginButton, 2);
            GridPane.setHalignment(loginButton, HPos.CENTER); // Center the login button
            loginButton.setEffect(mdropShadow);


           
            loginButton.setOnAction(e -> {
                // Handle login button click
                String password = passwordInput.getText();
                if(password.equals(tempUser.getPassword())){
                    user = tempUser;
                    displayMainScreen(primaryStage);
                }else{
                    passwordInput.setStyle("-fx-background-color: red");

                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(1)); 
                    pause.setOnFinished(event -> {
                        passwordInput.setStyle("-fx-background-color: white"); 
                    });
                    pause.play(); 
                }
            });

            grid.getChildren().addAll(profileLabel, passwordLabel, passwordInput, loginButton);
        } 
        else {
            Button loginButton = new Button("Login");
            loginButton.setPrefWidth(200);
            loginButton.setPrefHeight(40);
            GridPane.setConstraints(loginButton, 2, 1);
            GridPane.setColumnSpan(loginButton, 2);
            loginButton.setEffect(mdropShadow);
            loginButton.setOnAction(e -> {
                // Handle login button click
                user = tempUser;
                displayMainScreen(primaryStage);
            });
    
            grid.getChildren().addAll(profileLabel, loginButton);
        }
    
        // Wrap the grid in a VBox and align it in the center of rightBox
        if (rightBox.getChildren().contains(loginBox)) {
            rightBox.getChildren().remove(loginBox); // Remove the existing VBox
        }
        

        loginBox = new VBox(grid);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setMaxWidth(500);

      
        rightBox.getChildren().add(loginBox);
    
        // Animation to scale the grid
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.5), grid);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }
    

    private void displayMainScreen(Stage primaryStage) {
        // Implement your main screen display logic here
    }

    public static void main(String[] args) {
        launch(args);
    }

   
}

