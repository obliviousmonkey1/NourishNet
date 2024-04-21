package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.io.File;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javafx.scene.control.Slider;
import javafx.geometry.Orientation;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.ColumnConstraints;
import javafx.event.ActionEvent; // Import the ActionEvent class
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;



public class App extends Application {

    private static ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
    private static ArrayList<Diet> dietHolder = new ArrayList<Diet>();
    private static final String BUTTON_CLICKED_COLOUR = "#FFD700"; // Button clicked colour
    
    private Button lastClickedButton;
    private User user;
    private String identifier = "recommended";
    private int nextFreeRow = 0;  // The next free row in the selected ingredients grid
    private Diet currentDiet = null;
    private Image image = null;


    @Override
    public void start(Stage primaryStage) {
        UserManager.clearTemporaryProfileImageHolder();

        
        //code for the drop shadow effect
        DropShadow mdropShadow = new DropShadow();

        //code for the lighting effect
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
 
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0f);

        //code for the left VBox
        VBox leftVBox = new VBox();
        leftVBox.setStyle("-fx-background-color: #343434;");
        leftVBox.setSpacing(20);
        leftVBox.setPadding(new Insets(10)); //sets the padding of the buttons

        Label leftTitle = new Label("          PROFILES          ");
        leftTitle.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null))); // Set Label background color
        Font leftTitleFont = Font.font("Arial", FontWeight.BOLD, 20); // Set font
        leftTitle.setStyle("-fx-text-fill: white;"); // Set text color
        leftTitle.setFont(leftTitleFont); // Set font weight
        leftTitle.setEffect(mdropShadow);
        

        leftVBox.getChildren().add(leftTitle);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        loadData();


// Scroll pane for profile buttons
ScrollPane scrollPane = new ScrollPane();

scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically
scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure the ScrollPane always shows a scrollbar
scrollPane.setContent(leftVBox); // Set the content of the ScrollPane to the left VBox


Button addNewUser = new Button();
addNewUser.setPrefSize(200, 200); // Set button size
addNewUser.setStyle("-fx-background-color: #343434;");

Image newUserImage = new Image("file:" + Constants.newUser);
ImageView newUserImageView = new ImageView(newUserImage);
newUserImageView.setFitHeight(100);
newUserImageView.setFitWidth(100);
newUserImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

Label newUserLabel = new Label("New User");
newUserLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

// Stack image and label vertically
VBox newUserVBox = new VBox(); // VBox to stack image and label
newUserVBox.getChildren().addAll(newUserImageView, newUserLabel);
newUserVBox.setAlignment(Pos.BASELINE_CENTER); // Center content
newUserVBox.getStyleClass().add("button-profile"); // Apply CSS class    



    addNewUser.setGraphic(newUserVBox);
    //profileButton.getStyleClass().add("button-profile"); // Apply CSS class
    addNewUser.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height


    addNewUser.setOnAction(e -> {

        addNewUser.setEffect(mdropShadow);

        if (lastClickedButton != null) {
            lastClickedButton.setEffect(null);
        }
        lastClickedButton = addNewUser;
       
        // Display the new user scene
        UserManager.clearTemporaryProfileImageHolder();
        displayNewUser(primaryStage, scrollPane, lighting);  //calls for the new user scene to be displayed

        addNewUser.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
    });
    leftVBox.getChildren().add(addNewUser);


// Add profile buttons
List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();
for (int i = 0; i < profiles.size(); i++) 
{
    DataStructures.StringImageIdPair profile = profiles.get(i);
    Button profileButton = new Button();
    profileButton.setStyle("-fx-background-color: #343434;");
    profileButton.setPrefSize(200, 200); // Set button size

    ImageView imageView = new ImageView();
    imageView.setImage(profile.getImage());
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);
    imageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

    // Profile name label
    Label nameLabel = new Label(profile.getText());
    nameLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

    // Stack image and label vertically
    VBox profileContent = new VBox(); // VBox to stack image and label
    profileContent.getChildren().addAll(imageView, nameLabel);
    profileContent.setAlignment(Pos.BASELINE_CENTER); // Center content
    profileContent.getStyleClass().add("button-profile"); // Apply CSS class


    profileButton.setGraphic(profileContent);
    //profileButton.getStyleClass().add("button-profile"); // Apply CSS class
    profileButton.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height



    profileButton.setOnAction(e -> {
        User tempUser = ResourceLoader.loadUser(UserManager.getUserJsonPath(profile.getId()));


        profileButton.setEffect(mdropShadow);

        if (lastClickedButton != null) {
            lastClickedButton.setEffect(null);
            lastClickedButton.setStyle("-fx-background-color: #343434;"); // Reset previous button color
        }
        lastClickedButton = profileButton;
       
        // Display the password field popup
        displayLoginField(primaryStage, scrollPane, tempUser.getHasPassword(), tempUser);

        profileButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
    });
    leftVBox.getChildren().add(profileButton);
}


// Ensure the left VBox takes up a reasonable width
leftVBox.setMinWidth(200);


        // Create the right VBox
        VBox rightVBox = new VBox();
        rightVBox.setPrefWidth(600);
        rightVBox.setStyle("-fx-background-color: #8BB26B;");
        rightVBox.setPadding(new Insets(10));
        rightVBox.setSpacing(100);
        rightVBox.setAlignment(Pos.CENTER);



        Label rightTitle = new Label("NourishNet");
        rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightTitle.setStyle("-fx-font-size: 100px; -fx-text-fill: black;"); // Set text color
        rightTitle.setAlignment(Pos.CENTER);



        rightTitle.setEffect(lighting);

        Label rightLabel = new Label("Zero Hunger, One Recipe at a Time - Sustainably Nourishing Lives!");
        rightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        rightLabel.setAlignment(Pos.CENTER);

        rightLabel.setEffect(mdropShadow);

        //Adding an image to the right VBox
        Image homeImage = new Image("file:" + Constants.home);
        ImageView homeImageView = new ImageView(homeImage);
        homeImageView.setFitHeight(400);
        homeImageView.setFitWidth(400);
        homeImageView.setPreserveRatio(true);
        homeImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

        Label homeLabel = new Label("", homeImageView);

        rightVBox.getChildren().addAll(rightTitle, rightLabel, homeLabel);


        HBox root = new HBox(scrollPane, rightVBox);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("NourishNet");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void loadData(){
        dietHolder = ResourceLoader.loadDiets();

        System.out.println(dietHolder.get(0).getName());  // debug

        recipeHolder = ResourceLoader.loadRecipes();
    
        System.out.println(recipeHolder.get(0).getName());        // debug



    }


    private void displayNewUser(Stage primaryStage, ScrollPane scrollPane, Lighting lighting) {
        System.out.println("Displaying new user scene");
        
        //code for the drop shadow effect
        DropShadow mdropShadow = new DropShadow();

        VBox newUserVBox = new VBox();
        newUserVBox.setSpacing(50);
        newUserVBox.setAlignment(Pos.CENTER);
        newUserVBox.setStyle("-fx-background-color: #8BB26B;");

        Label newUserTitle = new Label("NourishNet");
        newUserTitle.setStyle("-fx-font-size: 100px; -fx-text-fill: white;"); // Set text color 
        newUserTitle.setEffect(mdropShadow);

        //Adding an image
        Image profileImage = new Image("file:" + Constants.profile);
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitHeight(200);
        profileImageView.setFitWidth(200);
        profileImageView.setPreserveRatio(true);
        profileImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        Label defaultLabel = new Label("", profileImageView);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // Center the grid

        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(nameLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        nameLabel.setEffect(mdropShadow);

        TextField name = new TextField();
        name.setPrefWidth(400); // Increase password field width
        name.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(name, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        name.setEffect(mdropShadow);

        Label dayLabel = new Label("Day of Birth");
        dayLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(dayLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        dayLabel.setEffect(mdropShadow);


        // Day ComboBox
        ComboBox<String> day = new ComboBox<>();
        for (int i = 1; i <= 31; i++) {
            day.getItems().add(String.valueOf(i));
        }
        day.setPrefWidth(400); // Increase password field width
        day.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(day, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        day.setEffect(mdropShadow);

        Label monthLabel = new Label("Month of Birth");
        monthLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(monthLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        monthLabel.setEffect(mdropShadow);

        // Month ComboBox
        ComboBox<String> month = new ComboBox<>();
        month.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        month.setPrefWidth(400); // Increase password field width
        month.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(month, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        month.setEffect(mdropShadow);

        // Event listener to update days based on the selected month
        month.setOnAction(event -> {
            String selectedMonth = month.getSelectionModel().getSelectedItem();
            if (selectedMonth != null) { //if a month is selected
                int daysInMonth = getDaysInMonth(selectedMonth); // Get the number of days in the selected month

                if (day.getItems().size() == daysInMonth) 
                { // If the number of days in the month is the same as the number of days in the ComboBox
                    return; // No need to update the days
                }
                else {
                    if (day.getValue() != null) { // If a day is selected
                        if (Integer.parseInt(day.getValue()) > daysInMonth) // If the selected day exceeds the days in the month they selected
                        { 
                            day.setValue(null); // Reset the day value if it exceeds the days in the month
                            for (int i = 1; i <= daysInMonth; i++) {
                                day.getItems().add(String.valueOf(i));
                            }
                        }
                        else //if the day they selected works fine with the month they selected
                        {
                            String tempDay = day.getValue(); // Store the selected day
                            day.getItems().clear(); // Clear the days
                            for (int i = 1; i <= daysInMonth; i++) {
                                day.getItems().add(String.valueOf(i));
                            }
                            day.setValue(tempDay); // Set the day value to the stored day

                            //this means that the combo box gets reset with the days of the month they selected (cus it may have changed), 
                            //but the day they selected is still the same if it doesn't exceed the days in the month
                        }
                    }

                }

            }
        });

        Label yearLabel = new Label("Year of Birth");
        yearLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(yearLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        yearLabel.setEffect(mdropShadow);

        // Year ComboBox
        ComboBox<String> year = new ComboBox<>();
        for (int i = 1940; i <= 2024; i++) {
            year.getItems().add(String.valueOf(i));
        }
        year.setPrefWidth(400); // Increase password field width
        year.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(year, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        year.setEffect(mdropShadow);

        // Set default values
        day.setValue(null);
        month.setValue(null);
        year.setValue(null);
        

        Label weightLabel = new Label("Weight (KG)");
        weightLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(weightLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        weightLabel.setEffect(mdropShadow);

        ComboBox<Integer> weight = new ComboBox<Integer>();
        weight.getItems().add(null);
        for (int i = 40; i <= 150; i++) {
            weight.getItems().add(i);
        }
        weight.setValue(null);
        weight.setPrefWidth(400); // Increase password field width
        weight.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(weight, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        weight.setEffect(mdropShadow);

        Label heightLabel = new Label("Height (CM)");
        heightLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(heightLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        heightLabel.setEffect(mdropShadow);

        ComboBox<Integer> height = new ComboBox<Integer>();
        height.getItems().add(null);
        for (int i = 100; i <= 250; i++) {
            height.getItems().add(i);
        }
        height.setValue(null);
        height.setPrefWidth(400); // Increase password field width
        height.setPrefHeight(40); // Increase password field height
        GridPane.setMargin(height, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        height.setEffect(mdropShadow);

        // Add elements to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(name, 1, 0);
        
        grid.add(dayLabel, 0, 1);
        grid.add(day, 1, 1);
        
        grid.add(monthLabel, 0, 2);
        grid.add(month, 1, 2);
        
        grid.add(yearLabel, 0, 3);
        grid.add(year, 1, 3);
        
        grid.add(weightLabel, 0, 4);
        grid.add(weight, 1, 4);
                
        grid.add(heightLabel, 0, 5);
        grid.add(height, 1, 5);
        
        grid.setVisible(true);


        Button uploadPhoto = new Button("Upload Photo");
        uploadPhoto.setEffect(mdropShadow);
        uploadPhoto.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        
        uploadPhoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    System.out.println(selectedFile.toURI().toURL().toExternalForm());
                    image = new Image(selectedFile.toURI().toURL().toExternalForm());

                    try{
                        BufferedImage img = ImageIO.read(selectedFile);
                        Tools.loadImage("", img);
                        profileImageView.setImage(image); // Update the ImageView with the new image

                    } catch (IOException ex){
                        ex.printStackTrace();
                    }


                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button submit = new Button("Submit");
        submit.setEffect(mdropShadow);
        submit.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");

        submit.setOnAction(e -> {
            

            String nameInput = name.getText();
            String dayInput = day.getValue();
            String monthInput = month.getValue();
            String yearInput = year.getValue();
            Integer weightInput = weight.getValue();
            Integer heightInput = height.getValue();

            if (nameInput.isEmpty() || dayInput == null || monthInput == null || yearInput == null || weightInput == null || heightInput == null) {
                
                //turn the fields that are empty red
                if (nameInput.isEmpty()) {
                    name.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        name.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                if (dayInput == null) {
                    day.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        day.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                if (monthInput == null) {
                    month.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        month.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                if (yearInput == null) {
                    year.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        year.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                if (weightInput == null) {
                    weight.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        weight.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                if (heightInput == null) {
                    height.setStyle("-fx-background-color: red");
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        height.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }
                
                return;
            }

            // Create a new user
            User tempUser = new User();
            tempUser.setUsername(nameInput);

            int intWeight = (int) weightInput; // Cast to int directly
            int intHeight = (int) heightInput; // Cast to int directly
            tempUser.setWeight(intWeight);
            tempUser.setHeight(intHeight);

            int monthIndex = month.getItems().indexOf(monthInput);

            int[] DOB = {Integer.parseInt(dayInput), monthIndex+1, Integer.parseInt(yearInput)};
            tempUser.setDOB(DOB);
            
           
            displayWelcomeNewUser(primaryStage, tempUser, image);
        });


        newUserVBox.getChildren().addAll(newUserTitle, defaultLabel, grid, uploadPhoto, submit); 



        HBox root = new HBox(scrollPane, newUserVBox);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        HBox.setHgrow(newUserVBox, Priority.ALWAYS);

        Scene newUserScene = new Scene(root);
        newUserScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(newUserScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        

    }

    //Displays the login scene when a profile is clicked
    private void displayLoginField(Stage primaryStage, ScrollPane scrollPane, boolean hasPassword, User tempUser) {
        
        //code for the lighting effect
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
 
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0f);

        System.out.println("Displaying login field");
        VBox loginVBox = new VBox();
        
        DropShadow mdropShadow = new DropShadow();
    
        loginVBox.setPadding(new Insets(10));
        loginVBox.setPrefHeight(500);
        loginVBox.setPrefWidth(500);
        loginVBox.setStyle("-fx-background-color: #8BB26B;");
        loginVBox.setSpacing(50);
        loginVBox.setAlignment(Pos.CENTER);


        //Adding an image 
        // Image welcome = new Image("file:" + Constants.welcome);
        // ImageView welcomeImage = new ImageView(welcome);
        // welcomeImage.setFitHeight(400);
        // welcomeImage.setFitWidth(400);
        // welcomeImage.setPreserveRatio(true);
        // welcomeImage.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect

        Label welcomeImageLabel = new Label("");
        loginVBox.getChildren().addAll(welcomeImageLabel);
    
        Label profileLabel = new Label("Hello, " + tempUser.getUsername() + "!");
        profileLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        profileLabel.setStyle("-fx-font-size: 100px; -fx-text-fill: white;"); // Set text color
        loginVBox.getChildren().add(profileLabel);
        profileLabel.setEffect(mdropShadow);
        


    
    
        if (hasPassword) {
            Label passwordLabel = new Label("Input Password Below");
            passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            passwordLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
            loginVBox.getChildren().add(passwordLabel);
            passwordLabel.setEffect(mdropShadow);
    
            PasswordField passwordInput = new PasswordField();
            passwordInput.setPrefWidth(400); // Increase password field width
            passwordInput.setPrefHeight(80); // Increase password field height
            loginVBox.getChildren().add(passwordInput);
            passwordInput.setEffect(mdropShadow);

            passwordInput.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (passwordInput.getText().equals(tempUser.getPassword())) {
                        user = tempUser;
                        displayMainScreen(primaryStage);
                    } else {
                        passwordInput.setStyle("-fx-background-color: red");
                        passwordLabel.setText("Incorrect Password. Please Try Again");

                        // Create a PauseTransition to delay reverting the background color
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(e -> { // Rename the lambda expression's parameter to 'e'
                            passwordInput.setStyle("-fx-background-color: white");
                        });
                        pause.play();
                    }
                }
            });
    
            Button loginButton = new Button("     LOGIN     ");
            loginButton.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
            loginButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            loginButton.getStyleClass().add("button-profile"); // Apply CSS class
            loginButton.setEffect(mdropShadow);
                        loginVBox.getChildren().add(loginButton);
    
            loginButton.setOnAction(e -> {
                // Handle login button click
                String password = passwordInput.getText();
                if(password.equals(tempUser.getPassword())){
                    user = tempUser;
                    displayMainScreen(primaryStage);
                }else{
                    passwordInput.setStyle("-fx-background-color: red");
                    passwordLabel.setText("Incorrect Password. Please Try Again");
    
                    // Create a PauseTransition to delay reverting the background color
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> {
                        passwordInput.setStyle("-fx-background-color: white");
                    });
                    pause.play();
                }

                
            });
    
        }
        else {
            Button loginButton = new Button("     LOGIN     ");
            loginButton.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
            loginButton.setEffect(mdropShadow);
            loginButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            loginButton.getStyleClass().add("button-profile"); // Apply CSS class
            loginVBox.getChildren().add(loginButton);

            loginButton.setOnAction(e -> {
                // Handle login button click
                user = tempUser;
                displayMainScreen(primaryStage);
            });

    
        }

    
        
        HBox root = new HBox(scrollPane, loginVBox);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        HBox.setHgrow(loginVBox, Priority.ALWAYS);

        Scene loginScene = new Scene(root);
        loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();




    }
    
    private void displayWelcomeNewUser(Stage primaryStage, User tempUser, Image image){


        //code for the drop shadow effect
        DropShadow mdropShadow = new DropShadow();

        //code for the left VBox
        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setPrefHeight(100);
        leftVBox.setPrefWidth(100);
        leftVBox.setStyle("-fx-background-color: #8BB26B;");
        leftVBox.setSpacing(100);
        leftVBox.setAlignment(Pos.CENTER);

        if (image == null) {
            image = new Image("file:" + Constants.profile);
        }

        //Code to add the user's profile image as a label 
        ImageView userImageView = new ImageView();
        userImageView.setImage(image);
        userImageView.setFitHeight(200);
        userImageView.setFitWidth(200);
        userImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        Label userImageLabel = new Label("", userImageView);

        Label leftTitle = new Label("Welcome, " + tempUser.getUsername());
        leftTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        leftTitle.setEffect(mdropShadow);

        Label heightLabel = new Label("Height: " + tempUser.getHeight() + " cm");
        heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        heightLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color

        Label weightLabel = new Label("Weight: " + tempUser.getWeight() + " kg");
        weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        weightLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color


        double BMIValue = getBMI(tempUser.getWeight(), tempUser.getHeight());

        Label BMI = new Label("BMI: " + BMIValue);
        BMI.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        BMI.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color

        Slider bmiSlider = new Slider(10, 60, BMIValue);
        bmiSlider.setShowTickLabels(true);
        bmiSlider.setShowTickMarks(true);
        bmiSlider.setMajorTickUnit(10);
        bmiSlider.setMinorTickCount(1);
        bmiSlider.setBlockIncrement(1);
        bmiSlider.setSnapToTicks(true);
        bmiSlider.setOrientation(Orientation.HORIZONTAL);
        bmiSlider.setPrefWidth(400);
        bmiSlider.setPrefHeight(40);
        bmiSlider.setEffect(new DropShadow());
        bmiSlider.setDisable(true);

        BMI.textProperty().bind(Bindings.format("Your BMI: %.2f", bmiSlider.valueProperty()));

        if (BMIValue < 18.5) {
            BMI.setStyle("-fx-text-fill: red;");
            //send a message saying they are underweight
            Label underweight = new Label("You Are UNDERWEIGHT");
            underweight.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            underweight.setEffect(mdropShadow);
            leftVBox.getChildren().addAll(leftTitle, userImageLabel, bmiSlider, BMI, underweight);

        } else if (BMIValue >= 18.5 && BMIValue < 25) {
            BMI.setStyle("-fx-text-fill: green;");
            //send a message saying they are healthy
            Label healthy = new Label("You Are HEALTHY");
            healthy.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            leftVBox.getChildren().addAll(leftTitle, userImageLabel, bmiSlider, BMI, healthy);
        } else if (BMIValue >= 25 && BMIValue < 30) {
            BMI.setStyle("-fx-text-fill: orange;");
            //send a message saying they are overweight
            Label overweight = new Label("You Are OVERWEIGHT");
            overweight.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            leftVBox.getChildren().addAll(leftTitle, userImageLabel, bmiSlider, BMI, overweight);
        } else {
            BMI.setStyle("-fx-text-fill: red;");
            //send a message saying they are obese
            Label obese = new Label("You Are OBESE");
            obese.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            leftVBox.getChildren().addAll(leftTitle, userImageLabel, bmiSlider, BMI, obese);
        }

  
        //NOTE: the rest of the code for the left VBox is after the code for the rightVBox because a button requires the code from the rightVBox


        //code for the right VBox
        VBox rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        rightVBox.setPrefHeight(160);
        rightVBox.setPrefWidth(500);
        rightVBox.setStyle("-fx-background-color: #8BB26B;");
        rightVBox.setSpacing(50);
        rightVBox.setAlignment(Pos.CENTER);


        Label rightTitle = new Label("NourishNet");
        rightTitle.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
        rightTitle.setEffect(mdropShadow);

        ArrayList<Diet> diets = ResourceLoader.loadDiets();
        Diet recommendedDietTemp = null;

        for (int i=0; i<diets.size(); i++){
            if (BMIValue <= diets.get(i).getBmiRecommendationThreshold()) {
                recommendedDietTemp = diets.get(i);
            }
        }

        if (recommendedDietTemp == null) {
            recommendedDietTemp = diets.get(0);
        }

        Diet recommendedDiet = recommendedDietTemp;

        GridPane dietsGrid = new GridPane();
        dietsGrid.setVgap(50);
        dietsGrid.setHgap(50);
        dietsGrid.setPadding(new Insets(10));
        dietsGrid.setAlignment(Pos.CENTER); // Center the grid
        Label subtitle1 = new Label("RECOMMENDED");
        subtitle1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        subtitle1.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(subtitle1, 1, 0);
        Label select = new Label("SELECT");
        select.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(select, 2, 0);
        Label name = new Label(recommendedDiet.getName());
        name.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        name.setEffect(mdropShadow);
        dietsGrid.add(name, 0, 1);
        Label content = new Label("Difficulty:  " + recommendedDiet.getDifficultyLevel() + "\nWeight Loss: " + recommendedDiet.getWeightLoss());
        content.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        content.setEffect(mdropShadow);
        dietsGrid.add(content, 1, 1);
        Button recommendedDietButton = new Button();
        recommendedDietButton.setGraphic(new ImageView("file:" + Constants.selected));
        recommendedDietButton.setStyle("-fx-background-color: #8BB26B;");
        dietsGrid.add(recommendedDietButton, 2, 1);
        Button learnMore = new Button("Learn More");
        learnMore.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        learnMore.getStyleClass().add("button-profile"); // Apply CSS class
        learnMore.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        learnMore.setOnAction(e -> {
            Stage dietInfoStage = new Stage();

            // Set the owner of the pop-up stage to be the primary stage
            dietInfoStage.initOwner(primaryStage);

            // Create a VBox to hold the diet information
            VBox dietInfoVBox = new VBox();
            dietInfoVBox.setPadding(new Insets(10));
            dietInfoVBox.setSpacing(10);
            dietInfoVBox.setAlignment(Pos.CENTER);

            // Create labels to display the diet information
            Label title = new Label(recommendedDiet.getName() + " Diet Info");
            title.setStyle("-fx-font-size: 45px; -fx-text-fill: black;"); // Set text color

            Label description = new Label(recommendedDiet.getDescription());
            description.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set text color
            description.setWrapText(true); // Enable text wrapping
            description.setMaxWidth(350); // Set maximum width for the label

            // Add the labels to the VBox
            dietInfoVBox.getChildren().addAll(title, description);

            // Create a scene with the VBox as the root
            Scene dietInfoScene = new Scene(dietInfoVBox, 600, 600);

            // Set the scene and show the stage
            dietInfoStage.setScene(dietInfoScene);
            dietInfoStage.show();
        });
        dietsGrid.add(learnMore, 3, 1);
        Label subtitle2 = new Label("OTHER DIETS");
        subtitle2.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(subtitle2, 1, 2);

        int row = 3;
        for (int i = 0; i < diets.size(); i++) {
            if (diets.get(i) != recommendedDiet) {
                String nameOfDiet = diets.get(i).getName();
                String dietDescription = diets.get(i).getDescription();
                Label dietName = new Label(nameOfDiet);
                dietName.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                dietName.setEffect(mdropShadow);
                dietsGrid.add(dietName, 0, row);
                Label dietContent = new Label("Difficulty:    " + diets.get(i).getDifficultyLevel() + "\nWeight Loss: " + diets.get(i).getWeightLoss());
                dietContent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                dietContent.setEffect(mdropShadow);
                dietsGrid.add(dietContent, 1, row);
                Button learnMoreButton = new Button("Learn More");
                learnMoreButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                learnMoreButton.getStyleClass().add("button-profile"); // Apply CSS class
                learnMoreButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
                learnMoreButton.setOnAction(e -> {
                    Stage dietInfoStage = new Stage();
        
                    // Set the owner of the pop-up stage to be the primary stage
                    dietInfoStage.initOwner(primaryStage);
        
                    // Create a VBox to hold the diet information
                    VBox dietInfoVBox = new VBox();
                    dietInfoVBox.setPadding(new Insets(10));
                    dietInfoVBox.setSpacing(10);
                    dietInfoVBox.setAlignment(Pos.CENTER);
        
                    // Create labels to display the diet information
                    Label title = new Label(nameOfDiet + " Diet Info");
                    title.setStyle("-fx-font-size: 45px; -fx-text-fill: black;"); // Set text color
        
                    Label description = new Label(dietDescription);
                    description.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set text color
                    description.setWrapText(true); // Enable text wrapping
                    description.setMaxWidth(350); // Set maximum width for the label
        
                    // Add the labels to the VBox
                    dietInfoVBox.getChildren().addAll(title, description);
        
                    // Create a scene with the VBox as the root
                    Scene dietInfoScene = new Scene(dietInfoVBox, 600, 600);
        
                    // Set the scene and show the stage
                    dietInfoStage.setScene(dietInfoScene);
                    dietInfoStage.show();
                });
                dietsGrid.add(learnMoreButton, 3, row);
                row++;
            }
        }


        
        //code for adding a select button for each row in the grid
        Button selectButton0 = new Button();
        selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton0.setStyle("-fx-background-color: #8BB26B;");
        selectButton0.setPrefSize(50, 50);
        dietsGrid.add(selectButton0, 2, 3);

        Button selectButton1 = new Button();
        selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton1.setStyle("-fx-background-color: #8BB26B;");
        selectButton1.setPrefSize(50, 50);
        dietsGrid.add(selectButton1, 2, 4);

        Button selectButton2 = new Button();
        selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton2.setStyle("-fx-background-color: #8BB26B;");
        selectButton2.setPrefSize(50, 50);
        dietsGrid.add(selectButton2, 2, 5);

        Button selectButton3 = new Button();
        selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton3.setStyle("-fx-background-color: #8BB26B;");
        selectButton3.setPrefSize(50, 50);
        dietsGrid.add(selectButton3, 2, 6);

        Button selectButton4 = new Button();
        selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton4.setStyle("-fx-background-color: #8BB26B;");
        selectButton4.setPrefSize(50, 50);
        dietsGrid.add(selectButton4, 2, 7);

        Button selectButton5 = new Button();
        selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton5.setStyle("-fx-background-color: #8BB26B;");
        selectButton5.setPrefSize(50, 50);
        dietsGrid.add(selectButton5, 2, 8);



        //code for the event handlers for the select buttons
        
        recommendedDietButton.setOnAction(e -> {
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.selected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "recommended";
            });

        selectButton0.setOnAction(e -> {
            selectButton0.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "0";
        });

        selectButton1.setOnAction(e -> {
            selectButton1.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "1";
        });

        selectButton2.setOnAction(e -> {
            selectButton2.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "2";
        });

        selectButton3.setOnAction(e -> {
            selectButton3.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "3";
        });

        selectButton4.setOnAction(e -> {
            selectButton4.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "4";
        });

        selectButton5.setOnAction(e -> {
            selectButton5.setGraphic(new ImageView("file:" + Constants.selected));
            recommendedDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "5";
        });




        rightVBox.getChildren().addAll(rightTitle, dietsGrid);




        //code for the rest of the left VBox

        for (int i=0; i<diets.size(); i++)
        {
            if (diets.get(i) == recommendedDiet)
            {
                diets.remove(recommendedDiet);
            }
        }


        Button temp = new Button();
        temp.setGraphic(new ImageView("file:" + Constants.selected));

        user = tempUser;
        Button next = new Button("Next");
        next.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        next.getStyleClass().add("button-profile"); // Apply CSS class
        next.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        next.setOnAction(e -> {
            if (identifier.equals("recommended")) {
                tempUser.setDiet(recommendedDiet.getName());
            } else if (identifier.equals("0")) {
                tempUser.setDiet(diets.get(0).getName());              
            } else if (identifier.equals("1")) {
                tempUser.setDiet(diets.get(1).getName());
            } else if (identifier.equals("2")) {
                tempUser.setDiet(diets.get(2).getName());
            } else if (identifier.equals("3")) {
                tempUser.setDiet(diets.get(3).getName());
            } else if (identifier.equals("4")) {
                tempUser.setDiet(diets.get(4).getName());
            } else if (identifier.equals("5")) {
                tempUser.setDiet(diets.get(5).getName());
            }
            SerializeJsonData.serializeNewUser(tempUser);
            UserManager.moveNewUserProfileToUserFolder(tempUser.getId());
            displayMainScreen(primaryStage);
        });

        leftVBox.getChildren().addAll(next);


        HBox root = new HBox(leftVBox, rightVBox);
        HBox.setHgrow(leftVBox, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        Scene welcomeScene = new Scene(root);
        welcomeScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(welcomeScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    private void displayMainScreen(Stage primaryStage) 
    {
        System.out.println("Displaying main screen");
        DropShadow mdropShadow = new DropShadow();


        //code for the left VBox
        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setPrefHeight(160);
        leftVBox.setPrefWidth(160);
        leftVBox.setStyle("-fx-background-color: #8BB26B;");
        leftVBox.setSpacing(100);
        leftVBox.setAlignment(Pos.CENTER);

        Label leftTitle = new Label("NourishNet");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        leftTitle.setAlignment(Pos.CENTER);
        leftTitle.setEffect(mdropShadow);

        Button findRecipes = new Button("       Find Recipes       ");
        findRecipes.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        findRecipes.getStyleClass().add("button-profile"); // Apply CSS class
        findRecipes.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        findRecipes.setOnAction(e -> {
            findRecipes.setEffect(mdropShadow);
            displayFindRecipes(primaryStage);
            findRecipes.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        Button help = new Button("How Else Can I Help?");
        help.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        help.getStyleClass().add("button-profile"); // Apply CSS class
        help.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        help.setOnAction(e -> {
            help.setEffect(mdropShadow);
            displayHelp(primaryStage);
            help.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        //add image design2 to the left VBox
        Image design2Image = new Image("file:" + Constants.design2);
        ImageView design2ImageView = new ImageView(design2Image);
        design2ImageView.setPreserveRatio(true);
        design2ImageView.setFitHeight(150); // Set a fixed width for the image
        design2ImageView.setPreserveRatio(true);
        design2ImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        Label design2Label = new Label("", design2ImageView);

        leftVBox.getChildren().addAll(leftTitle, findRecipes, help, design2Label);






        //code for the middle VBox
        VBox middleVBox = new VBox();
        middleVBox.setPadding(new Insets(10));
        middleVBox.setPrefHeight(160);
        middleVBox.setPrefWidth(160);
        middleVBox.setStyle("-fx-background-color: #8BB26B;");
        middleVBox.setSpacing(50);
        middleVBox.setAlignment(Pos.CENTER);

        Label middleTitle = new Label("Main Menu");
        middleTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        middleTitle.setStyle("-fx-font-size: 100px; -fx-text-fill: white;"); // Set text color
        middleTitle.setAlignment(Pos.CENTER);
        middleTitle.setEffect(mdropShadow);

        //Code to add the user's profile image as a label 
        ImageView userImageView = new ImageView();
        userImageView.setImage(UserManager.getUserProfileImage(user.getId()));
        userImageView.setFitHeight(200);
        userImageView.setFitWidth(200);
        userImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        Label userImageLabel = new Label("", userImageView);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // Center the grid

        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(nameLabel, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        nameLabel.setEffect(mdropShadow);

        Label name = new Label(user.getUsername());
        name.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(name, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        name.setEffect(mdropShadow);

        Label BMI = new Label("BMI:");
        BMI.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(BMI, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        BMI.setEffect(mdropShadow);

        Label BMIValue = new Label(String.valueOf(getBMI(user.getWeight(), user.getHeight()))); // Calculate BMI
        BMIValue.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(BMIValue, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        BMIValue.setEffect(mdropShadow);

        Label Diet = new Label("Diet:");
        Diet.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(Diet, new Insets(0, 10, 0, 0)); // Adjust the right margin to create space
        Diet.setEffect(mdropShadow);

        Label DietValue = new Label(user.getDiet());
        DietValue.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        GridPane.setMargin(DietValue, new Insets(0, 0, 0, 10)); // Adjust the left margin to create space
        DietValue.setEffect(mdropShadow);

        //add elements to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(name, 1, 0);
        grid.add(BMI, 0, 1);
        grid.add(BMIValue, 1, 1);
        grid.add(Diet, 0, 2);
        grid.add(DietValue, 1, 2);

        //code for nourishnet slogan as a label
        Label sloganLabel = new Label("Zero Hunger, One Recipe\nat a Time - Sustainably\nNourishing Lives!");
        sloganLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        sloganLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        sloganLabel.setAlignment(Pos.CENTER); // Center the text
        sloganLabel.setEffect(mdropShadow);

        //code for the home button
        Button homeButton = new Button("             Home             ");
        homeButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        homeButton.getStyleClass().add("button-profile"); // Apply CSS class
        homeButton.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        homeButton.setOnAction(e -> {
            start(primaryStage);
            homeButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        middleVBox.getChildren().addAll(middleTitle, userImageLabel, grid, sloganLabel, homeButton);




        //code for the right VBox
        VBox rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        rightVBox.setPrefHeight(160);
        rightVBox.setPrefWidth(160);
        rightVBox.setStyle("-fx-background-color: #8BB26B;");
        rightVBox.setSpacing(100);
        rightVBox.setAlignment(Pos.CENTER);

        Label rightTitle = new Label("NourishNet");
        rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        rightTitle.setAlignment(Pos.CENTER);
        rightTitle.setEffect(mdropShadow);

        //code for the View/Change Diets button
        Button viewChangeDiets = new Button("View/Change Diets");
        viewChangeDiets.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        viewChangeDiets.getStyleClass().add("button-profile"); // Apply CSS class
        
        viewChangeDiets.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        viewChangeDiets.setOnAction(e -> {
            viewChangeDiets.setEffect(mdropShadow);
            displayDiets(primaryStage);
            viewChangeDiets.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        //code for the View Family button
        Button viewFamily = new Button("View Family");
        viewFamily.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        viewFamily.getStyleClass().add("button-profile"); // Apply CSS class
        viewFamily.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        viewFamily.setOnAction(e -> {
            viewFamily.setEffect(mdropShadow);
            displayFamily(primaryStage);
            viewFamily.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        //add image design3 to the left VBox
        Image design3Image = new Image("file:" + Constants.design3);
        ImageView design3ImageView = new ImageView(design3Image);
        design3ImageView.setPreserveRatio(true);
        design3ImageView.setFitHeight(150); // Set a fixed width for the image
        design3ImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        Label design3Label = new Label("", design3ImageView);

        rightVBox.getChildren().addAll(rightTitle, viewChangeDiets, viewFamily, design3Label);




        HBox root = new HBox(leftVBox, middleVBox, rightVBox);
        HBox.setHgrow(leftVBox, Priority.ALWAYS);
        HBox.setHgrow(middleVBox, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void displayFindRecipes(Stage primaryStage) {
        
        System.out.println("Displaying find recipes screen");
        DropShadow mdropShadow = new DropShadow();

        //code for the left VBox
        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setPrefHeight(160);
        leftVBox.setPrefWidth(160);
        leftVBox.setStyle("-fx-background-color: #8BB26B;");
        leftVBox.setSpacing(100);
        leftVBox.setAlignment(Pos.CENTER);

        Label leftTitle = new Label("Search Recipes");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        leftTitle.setAlignment(Pos.CENTER);
        leftTitle.setEffect(mdropShadow);
        leftVBox.getChildren().add(leftTitle);

        ScrollPane recipeScroll = new ScrollPane();

        recipeScroll.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
        recipeScroll.setFitToHeight(true); // Allow the ScrollPane to resize vertically
        recipeScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure the ScrollPane always shows a scrollbar
        recipeScroll.setContent(leftVBox); // Set the content of the ScrollPane to the left VBox


        //a for loop that goes through recipeHolder and creates grid for each recipe 
        for (int i=0; i<recipeHolder.size(); i++)
        {
            Recipe recipe = recipeHolder.get(i);
            GridPane recipeGrid = new GridPane();
            recipeGrid.setVgap(10);
            recipeGrid.setHgap(10);
            recipeGrid.setAlignment(Pos.CENTER); // Center the grid

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setMinWidth(200);
            recipeGrid.getColumnConstraints().add(columnConstraints);

            Label recipeName = new Label(recipe.getName());
            recipeName.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
            recipeName.setEffect(mdropShadow);
            recipeName.setMaxWidth(Double.MAX_VALUE);
            recipeGrid.add(recipeName, 0, 0);

            Label recipeContent = new Label(recipe.getDescription());
            recipeContent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
            recipeContent.setEffect(mdropShadow);
            recipeContent.setWrapText(true); // Enable text wrapping
            recipeContent.setMaxWidth(400); // Set maximum width for the label
            recipeContent.setAlignment(Pos.CENTER);
            recipeGrid.add(recipeContent, 0, 1);

            //add image design2 to the left VBox
            Image recipeImage = Tools.getRecipeImage(recipe.generateImageName());
           
            ImageView recipeImageView = new ImageView(recipeImage);
            recipeImageView.setFitHeight(150); // Set a fixed height for the image
            recipeImageView.setFitWidth(150); // Set a fixed width for the image
            recipeImageView.setPreserveRatio(true);
            recipeImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            
            Label recipeImageLabel = new Label("", recipeImageView);
            recipeImageView.setEffect(mdropShadow);
           
            recipeGrid.add(recipeImageLabel, 1, 1);
            recipeGrid.setAlignment(Pos.BASELINE_RIGHT); // Align the image to the center-right of the cell

            
            
            Button recipeButton = new Button("View Recipe");
            recipeButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            recipeButton.getStyleClass().add("button-profile"); // Apply CSS class
            recipeButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
            recipeButton.setOnAction(e -> {
                Stage recipeStage = new Stage();

                // Set the owner of the pop-up stage to be the primary stage
                recipeStage.initOwner(primaryStage);

                // Create a VBox to hold the recipe information
                VBox recipeLeftVBox = new VBox();
                recipeLeftVBox.setPadding(new Insets(10));
                recipeLeftVBox.setSpacing(10);
                recipeLeftVBox.setAlignment(Pos.CENTER);
                recipeLeftVBox.setStyle("-fx-background-color: #8BB26B;");

                //add image design2 to the left VBox
                Image recipeImage2 = Tools.getRecipeImage(recipe.generateImageName());
           
                ImageView recipeImageView2 = new ImageView(recipeImage2);
                recipeImageView2.setFitHeight(150); // Set a fixed height for the image
                recipeImageView2.setFitWidth(150); // Set a fixed width for the image
                recipeImageView2.setPreserveRatio(true);
                recipeImageView2.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            
                Label recipeImageLabel2 = new Label("", recipeImageView2);
                recipeImageView2.setEffect(mdropShadow);

                recipeLeftVBox.getChildren().add(recipeImageLabel2);

                Label title = new Label("Ingredients");
                title.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                title.setEffect(mdropShadow);
                //set colour of the title

                recipeLeftVBox.getChildren().add(title);

                GridPane ingredientGrid = new GridPane();
                ingredientGrid.setVgap(10);
                ingredientGrid.setHgap(10);
                ingredientGrid.setEffect(mdropShadow);
                ingredientGrid.setAlignment(Pos.CENTER); // Center the grid
                //set colour of the grid
                ingredientGrid.setStyle("-fx-background-color: #558957;");

                // Create a label to display the recipe ingredients
                for (int j=0; j<recipe.getIngredients().size(); j++)
                {
                    Label ingredients = new Label(recipe.getIngredients().get(j).getQuantity() + recipe.getIngredients().get(j).getMeasurement() + " of " + recipe.getIngredients().get(j).getIngredientName());
                    ingredients.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    ingredients.setWrapText(true); // Enable text wrapping
                    ingredients.setMaxWidth(350); // Set maximum width for the label
                    ingredientGrid.add(ingredients, 0, j);
                }

                recipeLeftVBox.getChildren().add(ingredientGrid);

                VBox recipeRightVBox = new VBox();
                recipeRightVBox.setPadding(new Insets(10));
                recipeRightVBox.setSpacing(10);
                recipeRightVBox.setAlignment(Pos.CENTER);
                recipeRightVBox.setStyle("-fx-background-color: #8BB26B;");

                Label title2 = new Label(recipe.getName());
                title2.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                title2.setEffect(mdropShadow);

                recipeRightVBox.getChildren().add(title2);

                GridPane recipeGrid2 = new GridPane();
                recipeGrid2.setVgap(10);
                recipeGrid2.setHgap(10);
                recipeGrid2.setAlignment(Pos.CENTER); // Center the grid
    
                Label prepTime = new Label("Preparation Time: " + recipe.getPrepTime() + " minutes");
                prepTime.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label cookTime = new Label("Cook Time: " + recipe.getCookTime() + " minutes");
                cookTime.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label difficultyLevel = new Label("Difficulty Level: " + recipe.getLevel());
                difficultyLevel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label servings = new Label("Servings: " + recipe.getServes());
                servings.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color

                //adding elements to the grid
                recipeGrid2.add(prepTime, 0, 0);
                recipeGrid2.add(cookTime, 1, 0);
                recipeGrid2.add(difficultyLevel, 0, 1);
                recipeGrid2.add(servings, 1, 1);

                //set the colour of the grid 
                recipeGrid2.setStyle("-fx-background-color: #558957;");
                recipeGrid2.setEffect(mdropShadow);

                Label instructions = new Label("Instructions");
                instructions.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                instructions.setEffect(mdropShadow);

                GridPane recipeGrid3 = new GridPane();
                recipeGrid3.setEffect(mdropShadow);
                recipeGrid3.setAlignment(Pos.CENTER); // Center the grid
                //set the colour of the grid
                recipeGrid3.setStyle("-fx-background-color: #558957;");

                for (int j=0; j<recipe.getInstructions().size(); j++)
                {
                    Label instruction = new Label(recipe.getInstructions().get(j));
                    instruction.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    instruction.setWrapText(true); // Enable text wrapping
                    instruction.setPrefWidth(500);
                    recipeGrid3.add(instruction, 0, j);
                }

                recipeRightVBox.getChildren().addAll(recipeGrid2, instructions, recipeGrid3);



                // Create a scene with the VBox as the root
                Scene recipeScene = new Scene(new HBox(recipeLeftVBox, recipeRightVBox));
                recipeScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

                HBox.setHgrow(recipeLeftVBox, Priority.ALWAYS);
                HBox.setHgrow(recipeRightVBox, Priority.ALWAYS);

                // Set the scene and show the stage
                recipeStage.setScene(recipeScene);
                recipeStage.setWidth(1200);
                recipeStage.setHeight(1000);
                recipeStage.show();
            });
            recipeButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            recipeButton.setAlignment(Pos.CENTER);
            recipeGrid.add(recipeButton, 0, 2);


            leftVBox.getChildren().add(recipeGrid);
        }


        //code for the right VBox --------------------------------------------------------------------------------------------------------------

        VBox rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        rightVBox.setPrefHeight(250);
        rightVBox.setPrefWidth(250);
        rightVBox.setStyle("-fx-background-color: #558957;");
        rightVBox.setSpacing(10);
        rightVBox.setAlignment(Pos.CENTER);

        Label rightTitle = new Label("NourishNet");
        rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        rightTitle.setAlignment(Pos.CENTER);
        rightTitle.setEffect(mdropShadow);


        Button searchIngredient = new Button("Search by Ingredient");
        searchIngredient.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        searchIngredient.getStyleClass().add("button-profile"); // Apply CSS class
        searchIngredient.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        searchIngredient.setOnAction(e -> {
            searchIngredient.setEffect(mdropShadow);
            //display a pop up window that shows a list of all the ingredients from each recipe
            Stage ingredientStage = new Stage();

            // Set the owner of the pop-up stage to be the primary stage
            ingredientStage.initOwner(primaryStage);

            // Create a VBox to hold the ingredient information
            VBox ingredientVBox = new VBox();
            ingredientVBox.setPadding(new Insets(10));
            ingredientVBox.setSpacing(50); // Increase the gap between labels
            ingredientVBox.setAlignment(Pos.CENTER);
            ingredientVBox.setStyle("-fx-background-color: #8BB26B;");
            ingredientVBox.setEffect(mdropShadow);

            Label ingredientTitle = new Label("Ingredients");
            ingredientTitle.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
            ingredientTitle.setEffect(mdropShadow);
            ingredientVBox.getChildren().add(ingredientTitle);
            
            Label selected = new Label("Selected Ingredients");
            selected.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
            selected.setEffect(mdropShadow);
            ingredientVBox.getChildren().add(selected);

            GridPane ingredientGrid = new GridPane();
            ingredientGrid.setVgap(10);
            ingredientGrid.setHgap(10);
            ingredientGrid.setAlignment(Pos.CENTER); // Center the grid
            ingredientGrid.setStyle("-fx-background-color: #558957;");

            GridPane selectedGrid = new GridPane();
            selectedGrid.setVgap(10);
            selectedGrid.setHgap(10);
            selectedGrid.setAlignment(Pos.CENTER); // Center the grid
            selectedGrid.setStyle("-fx-background-color: #558957;");
            selectedGrid.setEffect(mdropShadow);
            ingredientVBox.getChildren().add(selectedGrid);


            int row = 0;
            for (int i=0; i<recipeHolder.size(); i++) {
                Recipe recipe = recipeHolder.get(i);
                for (int j=0; j<recipe.getIngredients().size(); j++) {

                    //make sure that the ingredient is not already in the list
                    boolean ingredientExists = false;
                    for (int k = 0; k < ingredientGrid.getChildren().size(); k++) {
                        if (k % 2 == 0 && recipe.getIngredients().get(j).getIngredientName().equals(((Label) ingredientGrid.getChildren().get(k)).getText())) {
                            ingredientExists = true;
                            break;
                        }
                    }

                    if (!ingredientExists) {
                        Label ingredient = new Label(recipe.getIngredients().get(j).getIngredientName());
                        ingredient.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                        ingredient.setEffect(mdropShadow);
                        ingredientGrid.add(ingredient, 0, row);

                        Button selectButton = new Button("SELECT");
                        selectButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                        selectButton.getStyleClass().add("button-profile"); // Apply CSS class
                        selectButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
                        selectButton.setOnAction(event -> {

                            //ensured nothing gets done if the button is already selected
                            if (selectButton.getStyle().equals("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;")) {
                                
                            }

                            //check to see if there haven't already been 5 ingredients selected
                            else if (selectedGrid.getChildren().size() == 5) {
                                //display a pop up window that says that the user can only select 5 ingredients
                                Stage errorStage = new Stage();
                                errorStage.initOwner(ingredientStage);
                                VBox errorVBox = new VBox();
                                errorVBox.setPadding(new Insets(10));
                                errorVBox.setSpacing(10);
                                errorVBox.setAlignment(Pos.CENTER);
                                errorVBox.setStyle("-fx-background-color: #8BB26B;");
                                errorVBox.setEffect(mdropShadow);

                                Label errorTitle = new Label("Error");
                                errorTitle.setStyle("-fx-font-size: 45px; -fx-text-fill: red;"); // Set text color
                                errorTitle.setEffect(mdropShadow);
                                errorVBox.getChildren().add(errorTitle);

                                Label errorContent = new Label("You can only select 5 ingredients");
                                errorContent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                                errorContent.setEffect(mdropShadow);
                                errorVBox.getChildren().add(errorContent);

                                errorStage.setHeight(400);
                                errorStage.setWidth(400);
                                errorStage.setScene(new Scene(errorVBox));
                                errorStage.show();
                                return;
                            }


                            else {
                                selectButton.setEffect(mdropShadow);
                                Label selectedIngredient = new Label(ingredient.getText());
                                selectedIngredient.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                                selectedIngredient.setEffect(mdropShadow);
                                selectedGrid.add(selectedIngredient, 0, nextFreeRow);
                                nextFreeRow++;
                                selectButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
                            }

                        });
                        ingredientGrid.add(selectButton, 1, row);
                    }

                    row++;
                }
            }

            Button clearSelectedIngredients = new Button("Clear Selected Ingredients");
            clearSelectedIngredients.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            clearSelectedIngredients.getStyleClass().add("button-profile"); // Apply CSS class
            clearSelectedIngredients.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
            clearSelectedIngredients.setOnAction(event -> {
                selectedGrid.getChildren().clear();
                nextFreeRow = 0;

                //reset the select buttons
                for (int i = 0; i < ingredientGrid.getChildren().size(); i++) {

                  //go through the grid and only the second column, and reset the style of the buttons
                    if (i % 2 != 0) {
                        Button selectButton = (Button) ingredientGrid.getChildren().get(i);
                        selectButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                        selectButton.getStyleClass().add("button-profile"); // Apply CSS class
                        selectButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");

                    }
                    
                }
            });
            ingredientVBox.getChildren().add(clearSelectedIngredients);

            Button searchForRecipe = new Button("Search for Recipe");
            searchForRecipe.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
            searchForRecipe.getStyleClass().add("button-profile"); // Apply CSS class
            searchForRecipe.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");

            searchForRecipe.setOnAction(event -> {
                
                //close the ingredientStage
                ingredientStage.close();

                //get the selected ingredients
                ArrayList<String> selectedIngredients = new ArrayList<String>();
                for (int i = 0; i < selectedGrid.getChildren().size(); i++) {
                    Label selectedIngredient = (Label) selectedGrid.getChildren().get(i);
                    selectedIngredients.add(selectedIngredient.getText());
                }

                ArrayList<Integer> recipeIngredientCounts = new ArrayList<Integer>();
                for (int i = 0; i < recipeHolder.size(); i++) {
                    Recipe recipe = recipeHolder.get(i);
                    int count = 0; // count of selected ingredients in the recipe
                    for (int j = 0; j < selectedIngredients.size(); j++) {
                        for (int k = 0; k < recipe.getIngredients().size(); k++) {
                            if (selectedIngredients.get(j).equals(recipe.getIngredients().get(k).getIngredientName())) {
                                count++;
                                break;
                            }
                        }
                    }
                    recipeIngredientCounts.add(count);
                }


                //sort the recipes by the number of selected ingredients
                for (int i = 0; i < recipeHolder.size(); i++) {
                    for (int j = i + 1; j < recipeHolder.size(); j++) {
                        if (recipeIngredientCounts.get(i) < recipeIngredientCounts.get(j)) {
                            Recipe tempRecipe = recipeHolder.get(i);
                            recipeHolder.set(i, recipeHolder.get(j));
                            recipeHolder.set(j, tempRecipe);

                            int tempCount = recipeIngredientCounts.get(i);
                            recipeIngredientCounts.set(i, recipeIngredientCounts.get(j));
                            recipeIngredientCounts.set(j, tempCount);
                        }
                    }
                }

                //now display the recipes in the order of the sorted list
                Stage recipeStage = new Stage();
                recipeStage.initOwner(primaryStage);
                VBox recipeVBox = new VBox();
                recipeVBox.setPadding(new Insets(10));
                recipeVBox.setSpacing(100);
                recipeVBox.setAlignment(Pos.CENTER);
                recipeVBox.setStyle("-fx-background-color: #8BB26B;");
                recipeVBox.setEffect(mdropShadow);

                Label recipeTitle = new Label("Recipes");
                recipeTitle.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                recipeTitle.setEffect(mdropShadow);
                recipeVBox.getChildren().add(recipeTitle);

                ScrollPane recipeScroll2 = new ScrollPane();
                recipeScroll2.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
                recipeScroll2.setFitToHeight(true); // Allow the ScrollPane to resize vertically
                recipeScroll2.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure the ScrollPane always shows a scrollbar
                recipeScroll2.setContent(recipeVBox); // Set the content of the ScrollPane to the left VBox


                for (int i = 0; i < recipeHolder.size(); i++) {

                    Recipe recipe = recipeHolder.get(i);
                    GridPane recipeGrid = new GridPane();
                    recipeGrid.setVgap(10);
                    recipeGrid.setHgap(10);
                    recipeGrid.setAlignment(Pos.CENTER); // Center the grid

                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setHgrow(Priority.ALWAYS);
                    columnConstraints.setMinWidth(200);
                    recipeGrid.getColumnConstraints().add(columnConstraints);

                    Label recipeName = new Label(recipe.getName());
                    recipeName.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
                    recipeName.setEffect(mdropShadow);
                    recipeName.setMaxWidth(Double.MAX_VALUE);
                    recipeGrid.add(recipeName, 0, 0);

                    Label recipeContent = new Label(recipe.getDescription());
                    recipeContent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    recipeContent.setEffect(mdropShadow);
                    recipeContent.setWrapText(true); // Enable text wrapping
                    recipeContent.setMaxWidth(400); // Set maximum width for the label
                    recipeContent.setAlignment(Pos.CENTER);
                    recipeGrid.add(recipeContent, 0, 1);

                    //add image design2 to the left VBox
                    Image recipeImage = new Image("file:" + Constants.recipeImagePath + "/" + recipe.generateImageName() + ".png");
                    //checks to see if there is an image for this recipe, and if there isn't, it adds a default 
                    if (recipeImage.isError())
                    {
                        recipeImage = new Image("file:" + Constants.defaultRecipeImage);
                    }
                    ImageView recipeImageView = new ImageView(recipeImage);
                    recipeImageView.setFitHeight(150); // Set a fixed height for the image
                    recipeImageView.setFitWidth(150); // Set a fixed width for the image
                    recipeImageView.setPreserveRatio(true);
                    recipeImageView.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                    Label recipeImageLabel = new Label("", recipeImageView);
                    recipeImageView.setEffect(mdropShadow);
           
                    recipeGrid.add(recipeImageLabel, 2, 1);
                    recipeGrid.setAlignment(Pos.BASELINE_RIGHT); // Align the image to the center-right of the cell

                    Button recipeButton = new Button("View Recipe");
                    recipeButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                    recipeButton.getStyleClass().add("button-profile"); // Apply CSS class
                    recipeButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
                    recipeButton.addEventHandler(ActionEvent.ACTION, evt -> {
                    //close previous stage
                    recipeStage.close();
                    //open new stage
                    Stage recipeStage2 = new Stage();

                    // Set the owner of the pop-up stage to be the primary stage
                    recipeStage2.initOwner(primaryStage);

                    // Create a VBox to hold the recipe information
                    VBox recipeLeftVBox = new VBox();
                    recipeLeftVBox.setPadding(new Insets(10));
                    recipeLeftVBox.setSpacing(10);
                    recipeLeftVBox.setAlignment(Pos.CENTER);
                    recipeLeftVBox.setStyle("-fx-background-color: #8BB26B;");

                    recipeLeftVBox.getChildren().add(recipeImageLabel);

                    Label title = new Label("Ingredients");
                    title.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                    title.setEffect(mdropShadow);
                    //set colour of the title

                    recipeLeftVBox.getChildren().add(title);

                    GridPane ingredientGrid2 = new GridPane();
                    ingredientGrid2.setVgap(10);
                    ingredientGrid2.setHgap(10);
                    ingredientGrid2.setEffect(mdropShadow);
                    ingredientGrid2.setAlignment(Pos.CENTER); // Center the grid
                    //set colour of the grid
                    ingredientGrid2.setStyle("-fx-background-color: #558957;");

                // Create a label to display the recipe ingredients
                for (int j=0; j<recipe.getIngredients().size(); j++)
                {
                    Label ingredients = new Label(recipe.getIngredients().get(j).getQuantity() + recipe.getIngredients().get(j).getMeasurement() + " of " + recipe.getIngredients().get(j).getIngredientName());
                    ingredients.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    ingredients.setWrapText(true); // Enable text wrapping
                    ingredients.setMaxWidth(350); // Set maximum width for the label
                    ingredientGrid2.add(ingredients, 0, j);
                }

                recipeLeftVBox.getChildren().add(ingredientGrid2);

                VBox recipeRightVBox = new VBox();
                recipeRightVBox.setPadding(new Insets(10));
                recipeRightVBox.setSpacing(10);
                recipeRightVBox.setAlignment(Pos.CENTER);
                recipeRightVBox.setStyle("-fx-background-color: #8BB26B;");

                Label title2 = new Label(recipe.getName());
                title2.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                title2.setEffect(mdropShadow);

                recipeRightVBox.getChildren().add(title2);

                GridPane recipeGrid2 = new GridPane();
                recipeGrid2.setVgap(10);
                recipeGrid2.setHgap(10);
                recipeGrid2.setAlignment(Pos.CENTER); // Center the grid
    
                Label prepTime = new Label("Preparation Time: " + recipe.getPrepTime() + " minutes");
                prepTime.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label cookTime = new Label("Cook Time: " + recipe.getCookTime() + " minutes");
                cookTime.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label difficultyLevel = new Label("Difficulty Level: " + recipe.getLevel());
                difficultyLevel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                Label servings = new Label("Servings: " + recipe.getServes());
                servings.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color

                //adding elements to the grid
                recipeGrid2.add(prepTime, 0, 0);
                recipeGrid2.add(cookTime, 1, 0);
                recipeGrid2.add(difficultyLevel, 0, 1);
                recipeGrid2.add(servings, 1, 1);

                //set the colour of the grid 
                recipeGrid2.setStyle("-fx-background-color: #558957;");
                recipeGrid2.setEffect(mdropShadow);

                Label instructions = new Label("Instructions");
                instructions.setStyle("-fx-font-size: 45px; -fx-text-fill: white;"); // Set text color
                instructions.setEffect(mdropShadow);

                GridPane recipeGrid3 = new GridPane();
                recipeGrid3.setEffect(mdropShadow);
                recipeGrid3.setAlignment(Pos.CENTER); // Center the grid
                //set the colour of the grid
                recipeGrid3.setStyle("-fx-background-color: #558957;");

                for (int j=0; j<recipe.getInstructions().size(); j++)
                {
                    Label instruction = new Label(recipe.getInstructions().get(j));
                    instruction.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    instruction.setWrapText(true); // Enable text wrapping
                    instruction.setPrefWidth(500);
                    recipeGrid3.add(instruction, 0, j);
                }

                recipeRightVBox.getChildren().addAll(recipeGrid2, instructions, recipeGrid3);



                // Create a scene with the VBox as the root
                Scene recipeScene = new Scene(new HBox(recipeLeftVBox, recipeRightVBox));
                recipeScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

                HBox.setHgrow(recipeLeftVBox, Priority.ALWAYS);
                HBox.setHgrow(recipeRightVBox, Priority.ALWAYS);

                // Set the scene and show the stage
                recipeStage2.setScene(recipeScene);
                recipeStage2.setWidth(1200);
                recipeStage2.setHeight(1000);
                recipeStage2.show();
                
            });
            recipeGrid.add(recipeButton, 0, 2);

                    //add a label which contains the selected ingredients present in the recipe
                    ArrayList<String> ingredientsPresentInRecipe = new ArrayList<String>();
                    for (int j = 0; j < selectedIngredients.size(); j++) {
                     for (int k = 0; k < recipe.getIngredients().size(); k++) {
                            if (selectedIngredients.get(j).equals(recipe.getIngredients().get(k).getIngredientName())) {
                                ingredientsPresentInRecipe.add(selectedIngredients.get(j));
                                break;
                            }
                        }
                    }
                    String ingredientsPresentInRecipeString = String.join(", ", ingredientsPresentInRecipe);
                    Label ingredientsPresent = new Label("Contains " + recipeIngredientCounts.get(i) + " selected ingredients:" + ingredientsPresentInRecipeString);
                    ingredientsPresent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                    ingredientsPresent.setEffect(mdropShadow);
                    ingredientsPresent.setWrapText(true); // Enable text wrapping
                    ingredientsPresent.setMaxWidth(400); // Set maximum width for the label
                    recipeGrid.add(ingredientsPresent, 1, 1);

                    recipeVBox.getChildren().add(recipeGrid);
                }



                // Create a scene with the VBox as the root
                Scene recipeScene = new Scene(recipeScroll2);
                recipeScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

                // Set the scene and show the stage
                recipeStage.setScene(recipeScene);
                recipeStage.setWidth(1100);
                recipeStage.setHeight(1000);
                recipeStage.show();
            });

            ingredientVBox.getChildren().add(searchForRecipe);

   


            //create a scroll pane for the ingredient list
            ScrollPane ing = new ScrollPane();
            ing.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
            ing.setFitToHeight(true); // Allow the ScrollPane to resize vertically
            ing.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure the ScrollPane always shows a scrollbar
            ing.setContent(ingredientGrid); // Set the content of the ScrollPane to the left VBox


            // Create a scene with the VBox as the root
            Scene recipeScene = new Scene(new HBox(ingredientVBox, ing));
            recipeScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            
            HBox.setHgrow(ingredientVBox, Priority.ALWAYS);
            HBox.setHgrow(ing, Priority.ALWAYS);
            
            // Set the scene and show the stage
            ingredientStage.setScene(recipeScene);
            ingredientStage.setWidth(1200);
            ingredientStage.setHeight(1000);
            ingredientStage.show();
        });

  
        Button mainMenu = new Button("Back To Main Menu");
        mainMenu.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        mainMenu.getStyleClass().add("button-profile"); // Apply CSS class
        mainMenu.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        mainMenu.setOnAction(e -> {
            displayMainScreen(primaryStage);
            mainMenu.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });



        
        rightVBox.getChildren().addAll(rightTitle, searchIngredient, mainMenu);





        HBox root = new HBox(recipeScroll, rightVBox);
        HBox.setHgrow(recipeScroll, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        }
    
    private void displayHelp(Stage primaryStage) {
       
        System.out.println("Displaying the help scene");
        DropShadow mdropShadow = new DropShadow();

        VBox firstMainVBox = new VBox();
        firstMainVBox.setPadding(new Insets(10));
        firstMainVBox.setSpacing(10);
        firstMainVBox.setAlignment(Pos.CENTER);
        firstMainVBox.setStyle("-fx-background-color: #8BB26B;");
        firstMainVBox.setEffect(mdropShadow);

        Label frameTitle = new Label("Nourishnet");
        frameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        frameTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        frameTitle.setAlignment(Pos.CENTER);
        frameTitle.setEffect(mdropShadow);

        firstMainVBox.getChildren().add(frameTitle);




        VBox firstVBox = new VBox();
        firstVBox.setPadding(new Insets(10));
        firstVBox.setPrefHeight(250);
        firstVBox.setPrefWidth(250);
        firstVBox.setStyle("-fx-background-color: #8BB26B;");
        firstVBox.setSpacing(10);
        firstVBox.setAlignment(Pos.CENTER);

        Label firstTitle = new Label("Resource Efficiency");
        firstTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        firstTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        firstTitle.setAlignment(Pos.CENTER);
        firstTitle.setEffect(mdropShadow);

        Label description = new Label("Sustainable foods often require fewer resources such as water, land, and energy to produce compared to conventional foods. By consuming these foods, we can use resources more efficiently, leaving more resources available for food production to meet the needs of a growing global population.\n\nAll our recipes contain ingredients that are nearly all sustainable!");
        description.setStyle("-fx-font-size: 20px; -fx-background-color: #558957; -fx-text-fill: white;"); // Set text color
        description.setEffect(mdropShadow);
        description.setWrapText(true); // Enable text wrapping
        description.setMaxWidth(300); // Set maximum width for the label
        description.setAlignment(Pos.CENTER);

        firstVBox.getChildren().addAll(firstTitle, description);

        VBox secondVBox = new VBox();
        secondVBox.setPadding(new Insets(10));
        secondVBox.setPrefHeight(250);
        secondVBox.setPrefWidth(250);
        secondVBox.setStyle("-fx-background-color: #8BB26B;");
        secondVBox.setSpacing(10);
        secondVBox.setAlignment(Pos.CENTER);

        Label secondTitle = new Label("Reducing Food Waste");
        secondTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        secondTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        secondTitle.setAlignment(Pos.CENTER);
        secondTitle.setEffect(mdropShadow);

        Label description2 = new Label("A significant portion of the food produced worldwide is lost or wasted due to inefficiencies in production, distribution, and consumption. By adopting a balanced diet and being mindful of food choices, individuals can help reduce food waste, ensuring that more food reaches those who need it.\n\nNourishNet has a function within the recipe page which will help minimise your food waste.");
        description2.setStyle("-fx-font-size: 20px; -fx-background-color: #558957; -fx-text-fill: white;"); // Set text color
        description2.setEffect(mdropShadow);
        description2.setWrapText(true); // Enable text wrapping
        description2.setMaxWidth(300); // Set maximum width for the label
        description2.setAlignment(Pos.CENTER);

        secondVBox.getChildren().addAll(secondTitle, description2);

        VBox thirdVBox = new VBox();
        thirdVBox.setPadding(new Insets(10));
        thirdVBox.setPrefHeight(250);
        thirdVBox.setPrefWidth(250);
        thirdVBox.setStyle("-fx-background-color: #8BB26B;");
        thirdVBox.setSpacing(10);
        thirdVBox.setAlignment(Pos.CENTER);

        Label thirdTitle = new Label("Nutritional Diversity");
        thirdTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        thirdTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        thirdTitle.setAlignment(Pos.CENTER);
        thirdTitle.setEffect(mdropShadow);

        Label description3 = new Label("A healthy and balanced diet consisting of a variety of nutrient-rich foods can help combat malnutrition and support overall health and well-being. By prioritizing nutrient-dense foods, individuals can improve their own health while also contributing to global efforts to address hunger and malnutrition.\n\nNourishNet promotes healthy eating in all diet plans and ensure that your eating the correct portions and types of foods.");
        description3.setStyle("-fx-font-size: 20px; -fx-background-color: #558957; -fx-text-fill: white;"); // Set text color
        description3.setEffect(mdropShadow);
        description3.setWrapText(true); // Enable text wrapping
        description3.setMaxWidth(300); // Set maximum width for the label
        description3.setAlignment(Pos.CENTER);

        thirdVBox.getChildren().addAll(thirdTitle, description3);

        VBox fourthVBox = new VBox();
        fourthVBox.setPadding(new Insets(10));
        fourthVBox.setPrefHeight(250);
        fourthVBox.setPrefWidth(250);
        fourthVBox.setStyle("-fx-background-color: #8BB26B;");
        fourthVBox.setSpacing(10);
        fourthVBox.setAlignment(Pos.CENTER);

        Label fourthTitle = new Label("Visit These Charity Websites");
        fourthTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        fourthTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); 
        fourthTitle.setAlignment(Pos.CENTER);
        fourthTitle.setEffect(mdropShadow);

        Label description4 = new Label("IF you wish to donate money to help achieve zero hunder, please visit these websites below. It can make a huge difference in the long run, so it would really help!\n\n\n\n");
        description4.setStyle("-fx-font-size: 20px; -fx-background-color: #558957; -fx-text-fill: white;"); // Set text color
        description4.setEffect(mdropShadow);
        description4.setWrapText(true); // Enable text wrapping
        description4.setMaxWidth(300); // Set maximum width for the label
        description4.setAlignment(Pos.CENTER);
        
        Button charity1 = new Button("Hunger Project");
        charity1.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        charity1.getStyleClass().add("button-profile"); // Apply CSS class
        charity1.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        charity1.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://thehungerproject.org.uk/"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        Button charity2 = new Button("Action Against Hunger");
        charity2.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        charity2.getStyleClass().add("button-profile"); // Apply CSS class
        charity2.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        charity2.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.actionagainsthunger.org/"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        Button charity3 = new Button("Save The Children");
        charity3.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        charity3.getStyleClass().add("button-profile"); // Apply CSS class
        charity3.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        charity3.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.savethechildren.org.uk/"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        

        fourthVBox.getChildren().addAll(fourthTitle, description4, charity1, charity2, charity3);


        
        VBox thirdMainVBox = new VBox();
        thirdMainVBox.setPadding(new Insets(10));
        thirdMainVBox.setSpacing(10);
        thirdMainVBox.setAlignment(Pos.CENTER);
        thirdMainVBox.setStyle("-fx-background-color: #8BB26B;");

        Button backButton = new Button("Back To Main Menu");
        backButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        backButton.getStyleClass().add("button-profile"); // Apply CSS class
        backButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        backButton.setOnAction(e -> {
            displayMainScreen(primaryStage);
            backButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        thirdMainVBox.getChildren().addAll(backButton);

        HBox secondMainBox = new HBox(firstVBox, secondVBox, thirdVBox, fourthVBox);
        HBox.setHgrow(firstVBox, Priority.ALWAYS);
        HBox.setHgrow(secondVBox, Priority.ALWAYS);
        HBox.setHgrow(thirdVBox, Priority.ALWAYS);
        HBox.setHgrow(fourthVBox, Priority.ALWAYS);

        VBox root = new VBox(firstMainVBox, secondMainBox, thirdMainVBox);
        VBox.setVgrow(firstMainVBox, Priority.ALWAYS);
        VBox.setVgrow(secondMainBox, Priority.ALWAYS);
        VBox.setVgrow(thirdMainVBox, Priority.ALWAYS);

        Scene helpScene = new Scene(root);
        helpScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(helpScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }

    private void displayDiets(Stage primaryStage) {
        
        System.out.println("Displaying diets screen");
        DropShadow mdropShadow = new DropShadow();

        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setPrefHeight(250);
        leftVBox.setPrefWidth(400);
        leftVBox.setStyle("-fx-background-color: #8BB26B;");
        leftVBox.setSpacing(10);
        leftVBox.setAlignment(Pos.CENTER);

        ArrayList<Diet> diets = ResourceLoader.loadDiets();
        for (int i=0; i<diets.size(); i++)
        {
            if (diets.get(i).getName().equals(user.getDiet()))
            {
                currentDiet = diets.get(i);
                break;
            }
        }

        GridPane dietsGrid = new GridPane();
        dietsGrid.setVgap(50);
        dietsGrid.setHgap(50);
        dietsGrid.setPadding(new Insets(10));
        dietsGrid.setAlignment(Pos.CENTER); // Center the grid
        Label subtitle1 = new Label("CURRENT DIET");
        subtitle1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        subtitle1.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(subtitle1, 1, 0);
        Label select = new Label("SELECT");
        select.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(select, 2, 0);
        Label name = new Label(currentDiet.getName());
        name.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        name.setEffect(mdropShadow);
        dietsGrid.add(name, 0, 1);
        Label content = new Label("Difficulty:    " + currentDiet.getDifficultyLevel() + "\nWeight Loss: " + currentDiet.getWeightLoss());
        content.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        content.setEffect(mdropShadow);
        dietsGrid.add(content, 1, 1);
        Button currentDietButton = new Button();
        currentDietButton.setGraphic(new ImageView("file:" + Constants.selected));
        currentDietButton.setStyle("-fx-background-color: #8BB26B;");
        dietsGrid.add(currentDietButton, 2, 1);
        Button learnMore = new Button("Learn More");
        learnMore.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        learnMore.getStyleClass().add("button-profile"); // Apply CSS class
        learnMore.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
        learnMore.setOnAction(e -> {
            Stage dietInfoStage = new Stage();

            // Set the owner of the pop-up stage to be the primary stage
            dietInfoStage.initOwner(primaryStage);

            // Create a VBox to hold the diet information
            VBox dietInfoVBox = new VBox();
            dietInfoVBox.setPadding(new Insets(10));
            dietInfoVBox.setSpacing(10);
            dietInfoVBox.setAlignment(Pos.CENTER);

            // Create labels to display the diet information
            Label title = new Label(currentDiet.getName() + " Diet Info");
            title.setStyle("-fx-font-size: 45px; -fx-text-fill: black;"); // Set text color

            Label description = new Label(currentDiet.getDescription());
            description.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set text color
            description.setWrapText(true); // Enable text wrapping
            description.setMaxWidth(350); // Set maximum width for the label

            // Add the labels to the VBox
            dietInfoVBox.getChildren().addAll(title, description);

            // Create a scene with the VBox as the root
            Scene dietInfoScene = new Scene(dietInfoVBox, 600, 600);

            // Set the scene and show the stage
            dietInfoStage.setScene(dietInfoScene);
            dietInfoStage.show();
        });
        dietsGrid.add(learnMore, 3, 1);
        Label subtitle2 = new Label("OTHER DIETS");
        subtitle2.setStyle("-fx-font-size: 30px; -fx-text-fill: black;"); // Set text color
        dietsGrid.add(subtitle2, 1, 2);

        int row = 3;
        for (int i = 0; i < diets.size(); i++) {
            if (diets.get(i) != currentDiet) {
                String nameOfDiet = diets.get(i).getName();
                String dietDescription = diets.get(i).getDescription();
                Label dietName = new Label(nameOfDiet);
                dietName.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                dietName.setEffect(mdropShadow);
                dietsGrid.add(dietName, 0, row);
                Label dietContent = new Label("Difficulty:    " + diets.get(i).getDifficultyLevel() + "\nWeight Loss: " + diets.get(i).getWeightLoss());
                dietContent.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
                dietContent.setEffect(mdropShadow);
                dietsGrid.add(dietContent, 1, row);
                Button learnMoreButton = new Button("Learn More");
                learnMoreButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
                learnMoreButton.getStyleClass().add("button-profile"); // Apply CSS class
                learnMoreButton.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-background-color: white;");
                learnMoreButton.setOnAction(e -> {
                    Stage dietInfoStage = new Stage();
        
                    // Set the owner of the pop-up stage to be the primary stage
                    dietInfoStage.initOwner(primaryStage);
        
                    // Create a VBox to hold the diet information
                    VBox dietInfoVBox = new VBox();
                    dietInfoVBox.setPadding(new Insets(10));
                    dietInfoVBox.setSpacing(10);
                    dietInfoVBox.setAlignment(Pos.CENTER);
        
                    // Create labels to display the diet information
                    Label title = new Label(nameOfDiet + " Diet Info");
                    title.setStyle("-fx-font-size: 45px; -fx-text-fill: black;"); // Set text color
        
                    Label description = new Label(dietDescription);
                    description.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Set text color
                    description.setWrapText(true); // Enable text wrapping
                    description.setMaxWidth(350); // Set maximum width for the label
        
                    // Add the labels to the VBox
                    dietInfoVBox.getChildren().addAll(title, description);
        
                    // Create a scene with the VBox as the root
                    Scene dietInfoScene = new Scene(dietInfoVBox, 600, 600);
        
                    // Set the scene and show the stage
                    dietInfoStage.setScene(dietInfoScene);
                    dietInfoStage.show();
                });
                dietsGrid.add(learnMoreButton, 3, row);
                row++;
            }
        }


        
        //code for adding a select button for each row in the grid
        Button selectButton0 = new Button();
        selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton0.setStyle("-fx-background-color: #8BB26B;");
        selectButton0.setPrefSize(50, 50);
        dietsGrid.add(selectButton0, 2, 3);

        Button selectButton1 = new Button();
        selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton1.setStyle("-fx-background-color: #8BB26B;");
        selectButton1.setPrefSize(50, 50);
        dietsGrid.add(selectButton1, 2, 4);

        Button selectButton2 = new Button();
        selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton2.setStyle("-fx-background-color: #8BB26B;");
        selectButton2.setPrefSize(50, 50);
        dietsGrid.add(selectButton2, 2, 5);

        Button selectButton3 = new Button();
        selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton3.setStyle("-fx-background-color: #8BB26B;");
        selectButton3.setPrefSize(50, 50);
        dietsGrid.add(selectButton3, 2, 6);

        Button selectButton4 = new Button();
        selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton4.setStyle("-fx-background-color: #8BB26B;");
        selectButton4.setPrefSize(50, 50);
        dietsGrid.add(selectButton4, 2, 7);

        Button selectButton5 = new Button();
        selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
        selectButton5.setStyle("-fx-background-color: #8BB26B;");
        selectButton5.setPrefSize(50, 50);
        dietsGrid.add(selectButton5, 2, 8);



        //code for the event handlers for the select buttons
        
        currentDietButton.setOnAction(e -> {
            currentDietButton.setGraphic(new ImageView("file:" + Constants.selected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "current";
            });

        selectButton0.setOnAction(e -> {
            selectButton0.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "0";
        });

        selectButton1.setOnAction(e -> {
            selectButton1.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "1";
        });

        selectButton2.setOnAction(e -> {
            selectButton2.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "2";
        });

        selectButton3.setOnAction(e -> {
            selectButton3.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "3";
        });

        selectButton4.setOnAction(e -> {
            selectButton4.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton5.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "4";
        });

        selectButton5.setOnAction(e -> {
            selectButton5.setGraphic(new ImageView("file:" + Constants.selected));
            currentDietButton.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton0.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton1.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton2.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton3.setGraphic(new ImageView("file:" + Constants.notSelected));
            selectButton4.setGraphic(new ImageView("file:" + Constants.notSelected));
            identifier = "5";
        });




        leftVBox.getChildren().addAll(dietsGrid);




        //code for the rest of the left VBox

        for (int i=0; i<diets.size(); i++)
        {
            if (diets.get(i) == currentDiet)
            {
                diets.remove(currentDiet);
            }
        }


        


        
        
        VBox rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        rightVBox.setPrefHeight(250);
        rightVBox.setMaxWidth(600);
        rightVBox.setStyle("-fx-background-color: #8BB26B;");
        rightVBox.setSpacing(200);
        rightVBox.setAlignment(Pos.CENTER);

        Label leftTitle = new Label("NourishNet");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTitle.setStyle("-fx-font-size: 70px; -fx-text-fill: white;"); // Set text color
        leftTitle.setAlignment(Pos.CENTER);
        leftTitle.setEffect(mdropShadow);

        Button selectButton = new Button("CONFIRM SELECTION");
        selectButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        selectButton.getStyleClass().add("button-profile"); // Apply CSS class
        selectButton.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white;");
        Button temp = new Button();
        temp.setGraphic(new ImageView("file:" + Constants.selected));
        selectButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        selectButton.getStyleClass().add("button-profile"); // Apply CSS class
        selectButton.setStyle("-fx-font-size: 50px; -fx-text-fill: black; -fx-background-color: white;");
        selectButton.setOnAction(e -> {
            if (identifier.equals("current")) {
                UserManager.changeUserDiet(user, currentDiet.getName());

            } else if (identifier.equals("0")) {
                UserManager.changeUserDiet(user, diets.get(0).getName());         
            } else if (identifier.equals("1")) {
                UserManager.changeUserDiet(user, diets.get(1).getName());
            } else if (identifier.equals("2")) {
                UserManager.changeUserDiet(user, diets.get(2).getName());
            } else if (identifier.equals("3")) {
                UserManager.changeUserDiet(user, diets.get(3).getName());
            } else if (identifier.equals("4")) {
                UserManager.changeUserDiet(user, diets.get(4).getName());
            } else if (identifier.equals("5")) {
                UserManager.changeUserDiet(user, diets.get(5).getName());
            }
            displayMainScreen(primaryStage);
        });

        Button backButton = new Button("Back To Main Menu");
        backButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        backButton.getStyleClass().add("button-profile"); // Apply CSS class
        backButton.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        backButton.setOnAction(e -> {
            displayMainScreen(primaryStage);
            backButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        rightVBox.getChildren().addAll(leftTitle, selectButton, backButton);

        HBox root = new HBox(leftVBox, rightVBox);
        HBox.setHgrow(leftVBox, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        Scene dietsScene = new Scene(root);
        dietsScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(dietsScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }

    private void displayFamily(Stage primaryStage) {
        
        System.out.println("Displaying family screen");
        DropShadow mdropShadow = new DropShadow();

        VBox topVBox = new VBox();
        topVBox.setPadding(new Insets(10));
        topVBox.setPrefHeight(100);
        topVBox.setPrefWidth(250);
        topVBox.setStyle("-fx-background-color: #558957;");
        topVBox.setSpacing(10);
        topVBox.setAlignment(Pos.CENTER);

        Label topTitle = new Label("NourishNet");
        topTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        topTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); // Set text color
        topTitle.setAlignment(Pos.CENTER);
        topTitle.setEffect(mdropShadow);

        Label topDescription = new Label("Family");
        topDescription.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        topDescription.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        topDescription.setAlignment(Pos.CENTER);
        topDescription.setEffect(mdropShadow);

        topVBox.getChildren().addAll(topTitle, topDescription);
        
        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setPrefHeight(250);
        leftVBox.setPrefWidth(100);
        leftVBox.setStyle("-fx-background-color: #8BB26B;");
        leftVBox.setSpacing(10);
        leftVBox.setAlignment(Pos.CENTER);

        Label leftTitle = new Label("Family Members");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;"); // Set text color
        leftTitle.setAlignment(Pos.CENTER);
        leftTitle.setEffect(mdropShadow);

        leftVBox.getChildren().add(leftTitle);

        // Scroll pane for profile buttons
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
        scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure the ScrollPane always shows a scrollbar
        scrollPane.setContent(leftVBox); // Set the content of the ScrollPane to the left VBox

        // Add profile buttons
        List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();
        for (int i = 0; i < profiles.size(); i++) 
        {
            DataStructures.StringImageIdPair profile = profiles.get(i);
            Button profileButton = new Button();
            profileButton.setStyle("-fx-background-color: #8BB26B;");
            profileButton.setPrefSize(200, 200); // Set button size

            ImageView imageView = new ImageView();
            imageView.setImage(profile.getImage());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            // Profile name label
            Label nameLabel = new Label(profile.getText());
            nameLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setEffect(mdropShadow);

            // Stack image and label vertically
            VBox profileContent = new VBox(); // VBox to stack image and label
            profileContent.getChildren().addAll(imageView, nameLabel);
            profileContent.setAlignment(Pos.BASELINE_CENTER); // Center content


            profileButton.setGraphic(profileContent);
            //profileButton.getStyleClass().add("button-profile"); // Apply CSS class
            profileButton.setMaxHeight(Double.MAX_VALUE); // Make buttons take up full height

            //add a label that shows the user's diet
            Label dietLabel = new Label("Diet: " + UserManager.getUserDiet(profile.getId()));
            dietLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
            dietLabel.setAlignment(Pos.CENTER);
            dietLabel.setEffect(mdropShadow);
            

            HBox row = new HBox(profileButton, dietLabel);


            leftVBox.getChildren().add(row);
        }

                
        scrollPane.setContent(leftVBox); // Set the content of the ScrollPane to the left VBox

        VBox rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        rightVBox.setPrefHeight(250);
        rightVBox.setPrefWidth(500);
        rightVBox.setStyle("-fx-background-color: #8BB26B;");
        rightVBox.setSpacing(10);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setEffect(mdropShadow);

        Label paragraph1 = new Label("At NourishNet, we firmly believe that family plays a pivotal role in achieving success, especially when it comes to health and wellness. We encourage you to leverage the power of family support to motivate each other on your journey towards a healthier lifestyle.");
        paragraph1.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        paragraph1.setEffect(mdropShadow);
        paragraph1.setWrapText(true); // Enable text wrapping
        paragraph1.setMinWidth(400); // Set maximum width for the label
        paragraph1.setAlignment(Pos.BASELINE_LEFT);

        Label paragraph2 = new Label("Moreover, through our program features such as limiting food waste, promoting the consumption of sustainable foods, and facilitating donations to charity, we aim to contribute to the bigger picture of Zero Hunger. Your active involvement in these initiatives not only benefits your own health but also extends a helping hand to those in need, aligning with our shared goal of creating a healthier and more sustainable future for all.");
        paragraph2.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Set text color
        paragraph2.setEffect(mdropShadow);
        paragraph2.setWrapText(true); // Enable text wrapping
        paragraph2.setMinWidth(400); // Set maximum width for the label
        paragraph2.setAlignment(Pos.BASELINE_LEFT);

        rightVBox.getChildren().addAll(paragraph1, paragraph2);

        HBox middleBox = new HBox(scrollPane, rightVBox);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);

        VBox bottomVBox = new VBox();
        bottomVBox.setPadding(new Insets(10));
        bottomVBox.setPrefHeight(250);
        bottomVBox.setPrefWidth(250);
        bottomVBox.setStyle("-fx-background-color: #558957;");
        bottomVBox.setSpacing(10);
        bottomVBox.setAlignment(Pos.CENTER);
        bottomVBox.setEffect(mdropShadow);

        Button backButton = new Button("Back To Main Menu");
        backButton.getStyleClass().add("image-highlight"); // Apply CSS class for highlight effect
        backButton.getStyleClass().add("button-profile"); // Apply CSS class
        backButton.setStyle("-fx-font-size: 30px; -fx-text-fill: black; -fx-background-color: white;");
        backButton.setOnAction(e -> {
            displayMainScreen(primaryStage);
            backButton.setStyle("-fx-background-color: " + BUTTON_CLICKED_COLOUR + "; -fx-background-insets: 0;");
        });

        bottomVBox.getChildren().add(backButton);

        VBox root = new VBox(topVBox, middleBox, bottomVBox);
        VBox.setVgrow(topVBox, Priority.ALWAYS);
        VBox.setVgrow(middleBox, Priority.ALWAYS);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);

        Scene familyScene = new Scene(root);
        familyScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(familyScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }




    private int getDaysInMonth(String month) {
        switch (month) {
            case "January":
            case "March":
            case "May":
            case "July":
            case "August":
            case "October":
            case "December":
                return 31;
            case "February":
                return 28; // Assuming non-leap year
            default:
                return 30;
        }
    }

    private double getBMI(int weight, double height) {
        return Math.round(weight / (height * height) * 10000);
    }
}