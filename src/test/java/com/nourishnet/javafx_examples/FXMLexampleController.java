package com.nourishnet.javafx_examples;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;

import java.util.List;

import com.nourishnet.UserManager;
import com.nourishnet.DataStructures;


public class FXMLexampleController {

    @FXML
    private Label loginLabel;


    @FXML
    private VBox leftBox; // Reference to the left VBox


    @FXML
    public void initialize() {
        // Add 10 buttons to the leftBox
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(leftBox);
        scrollPane.setFitToWidth(true); // Make sure the width fits the content
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide vertical scrollbar


        System.out.println("Initializing FXMLexampleController");
        List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();
        for (DataStructures.StringImageIdPair profile : profiles) {
            Button profileButton = createProfileButton(profile);
            leftBox.getChildren().add(profileButton);
        }
       

        // Handle login label click event
        //loginLabel.setOnMouseClicked(e -> handleLoginLabelClick());

    }

    private Button createProfileButton(DataStructures.StringImageIdPair profile) {
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
        profileContent.setAlignment(javafx.geometry.Pos.BASELINE_CENTER); // Center content
        profileContent.getStyleClass().add("button-profile"); // Apply CSS class

        profileButton.setGraphic(profileContent);
        profileButton.getStyleClass().add("button-profile"); // Apply CSS class
        profileButton.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height

        DropShadow mdropShadow = new DropShadow();

        profileButton.setOnAction(e -> {
            // Handle profile button click

            // Change button color on click
            profileButton.setEffect(mdropShadow);

            // Update the left VBox to display "Login"
            loginLabel.setText("Login");
        });

        return profileButton;
    }

    private void handleLoginLabelClick() {
        // Handle login label click event
        loginLabel.setText("Login");
    }
}
