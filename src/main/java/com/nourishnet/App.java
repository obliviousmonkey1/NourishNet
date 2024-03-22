package com.nourishnet;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*; 
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class App 
{
    private static ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
    private static ArrayList<Diet> dietHolder = new ArrayList<Diet>();

    private static Font titleFont = new Font("Arial", Font.PLAIN, 90);  //this will be the font (and size) for the title at the top of the Frames 
    private static String titleColour = "#81AB7F";  //darker green
    //private static String backgroundColour = "#AFC4B0";  //lighter green
    //private static String panelColour = "#ACB7AB"; //for panels in middle of Jframes that want to be a diff colour


    private static boolean allFieldsFilled;  //boolean used in the new user profile frame to check if all the fields have been filled in. DONT TOUCH THIS

    private static User user;

    public static void main( String[] args )
    {
        loadData();
        if(UserManager.getNumberOfUserProfiles()!=0){
            System.out.println("Users already in system"); // debug
            // go to the user selection screen
            createLoginMenu();
        }else{
            System.out.println("No users in the system"); // debug 
            // go to the set up account screen 
        }
    }

    // load the data 
    private static void loadData(){
        dietHolder = ResourceLoader.loadDiets();

        System.out.println(dietHolder.get(0).getName());  // debug

        recipeHolder = ResourceLoader.loadRecipes();
    
        System.out.println(recipeHolder.get(0).getName());        // debug



    }


    //method code for creating the login menu
    private static void createLoginMenu()
    {
        JFrame login = new JFrame("Login");  //creates the Jframe that will act as the login page
        login.setLayout(new FlowLayout());
        login.setSize(750, 750);
        login.getContentPane().setBackground(Color.BLACK);

        ArrayList<JButton> buttons = new ArrayList<JButton>();  //creates an ArrayList of all the buttons that will be on the frame to be formatted

        List<DataStructures.StringImageIdPair> profiles = UserManager.getUserProfiles();

        for (int i = 0; i < profiles.size(); i++) 
        {
            JButton button = new JButton(profiles.get(i).getText());
            button.putClientProperty("id", profiles.get(i).getId());

            button.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    try
                    {

                        String userJsonPath = UserManager.getUserJsonPath(button.getClientProperty("id").toString());
                        user = DeserializeJsonData.initaliseUserClass(userJsonPath);

                        if (user.getHasPassword())  //if the user's profile is password protected
                        {
                            login.setVisible(false);
                            createPasswordFrame(user, profiles);  //calls for the password frame to be made, 
                            //and passes user as a parameter so we keep the knowledge of which user was selected
                        }

                        else                        //if the user's profile is NOT password protected
                        {
                            login.setVisible(false);
                            createMainMenuFrame();  
                        }
                    }
                    catch(Exception e1)
                    {
                        e1.printStackTrace();
                    }

        
                }
            });

            login.add(button);
            buttons.add(button); //adds the button to the buttons ArrayList
        }

        //code for the "Create New User Profile" button 
        JButton createUserProfile = new JButton("Create New User Profile");
        login.add(createUserProfile);
        createUserProfile.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                login.setVisible(false);
                UserManager.clearTemporaryProfileImageHolder();
                newUserProfileFrame();
            }
        });

        buttons.add(createUserProfile);  //adds the createUserProfile button the the buttons ArrayList as it also needs to be formatted correctly in the frame


        formatLoginFrame(login, buttons, profiles);  //calls the function to add a title called "NourishNet" to the Jframe
   
    }

    private static void formatLoginFrame(JFrame frame, ArrayList<JButton> buttons, List<DataStructures.StringImageIdPair> profiles) {
        frame.setLayout(new BorderLayout()); // Set BorderLayout for the frame
        
        // Title at the top centered
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode(titleColour));
        JTextField titleField = new JTextField();
        titleField.setText("NourishNet");
        titleField.setBorder(null);  //removes the borders of the textfield
        titleField.setFont(titleFont);  // Set font for the title (titleFont is a Font object defined earlier in the code
        titleField.setBackground(Color.decode(titleColour));
        titleField.setEditable(false);
        titlePanel.add(titleField);
        frame.add(titlePanel, BorderLayout.NORTH);
    
        // Panel to hold the buttons and images
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.decode(backgroundColour)); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); // Insets for spacing
        
        // Add buttons and images to the panel
        for (int i = 0; i < profiles.size(); i++) {
            JButton button = buttons.get(i);
            ImageIcon imageIcon = profiles.get(i).getImage();
            
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.decode(backgroundColour)); // Set background color
           
            // Add the image on top of the button
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBackground(Color.decode(backgroundColour)); // Set background color
            panel.add(imageLabel, BorderLayout.CENTER);
            
            // Add the button below the image
            panel.add(button, BorderLayout.SOUTH);
            
            // Add the panel to the buttonPanel
            buttonPanel.add(panel, gbc);
            
            gbc.gridx++;
            if (gbc.gridx == 3) { // Adjust grid layout to 3 buttons per row
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        
        // Add gap between buttons and "Create New User Profile" button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        frame.add(buttonPanel, BorderLayout.CENTER);
        
        // Create New User Profile button at the bottom
        JButton createUserProfile = buttons.get(buttons.size() - 1); // Last button
        frame.add(createUserProfile, BorderLayout.SOUTH); // Add it to the last row
        
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    



    
    //method for creating the password menu for profiles that are password protected
    private static void createPasswordFrame(User user, List<DataStructures.StringImageIdPair> profiles)
    {
        System.out.println("Run the password frame"); //test


        //Creating the frame
        JFrame passwordFrame = new JFrame("Password JFrame");
        passwordFrame.setLayout(new BoxLayout(passwordFrame.getContentPane(), BoxLayout.Y_AXIS));  //sets the layout of the JFrame to be a BoxLayout
        passwordFrame.setSize(750, 750);  //sets the size of the JFrame 
        passwordFrame.getContentPane().setBackground(Color.decode(backgroundColour)); //sets the colour of the Jframe to be the green

        // Makes the Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode(titleColour)); //sets the colour of the JPanel to be the green
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));  //sets the maximise size of the panel

        //Makes and adds the textfield to the Title Panel
        JTextField titleField = new JTextField();
        titleField.setBorder(null);  //removes the borders of the textfield
        titleField.setText("NourishNet");
        titleField.setBackground(Color.decode(titleColour));
        titleField.setFont(titleFont);  //sets the font to the titleFont
        titleField.setEditable(false);
        titlePanel.add(titleField);

        passwordFrame.add(titlePanel);  //adds the panel to the JFrame

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        gapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel
        passwordFrame.add(gapPanel);  //adds the panel to the JFrame

        //Makes and adds a panel that will hold the picture of the user's profile picture, as well
        JPanel picturePanel = new JPanel(new GridLayout(2, 1));
        picturePanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        picturePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));  //sets the maximise size of the panel

        for (int i=0; i<profiles.size(); i++)  //this loop goes through the profiles ArrayList and finds the profile that matches the user's ID and then sets the image of the profile to be the image of the user's profile picture
        {
            if (profiles.get(i).getId().equals(user.getId()))
            {
                ImageIcon image = profiles.get(i).getImage();
                JLabel imageLabel = new JLabel(image);
                picturePanel.add(imageLabel, BorderLayout.CENTER);
                passwordFrame.add(picturePanel);  //adds the panel to the JFrame
                break;
            }
        }

        //Makes a panel to hold the user's name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel
        JTextField nameField = new JTextField();
        nameField.setBorder(null);  //removes the borders of the textfield
        nameField.setText(user.getUsername());
        nameField.setBackground(Color.decode(backgroundColour));
        nameField.setFont(new Font("Arial", Font.PLAIN, 30));  //sets the font to the titleFont
        nameField.setEditable(false);
        namePanel.add(nameField);
        passwordFrame.add(namePanel);  //adds the panel to the JFrame


        //Makes a panel to hold the password textfield
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setBackground(Color.decode(panelColour)); //sets the colour of the JPanel to be the green
        passwordPanel.setMaximumSize(new Dimension(800, 100));  //sets the maximise size of the panel
        JTextField passwordField = new JTextField(18);
        passwordField.setBorder(null);  //removes the borders of the textfield
        passwordField.setText("Input Password");
        passwordField.setBackground(Color.decode(panelColour));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 30));  //sets the font to the titleFont
        passwordField.setEditable(false);

        JTextField userInput = new JTextField(25);
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setText("");
                passwordField.setEditable(true);
            }
        });

        passwordPanel.add(passwordField);
        passwordPanel.add(userInput);  
        passwordFrame.add(passwordPanel);  //adds the panel to the JFrame

        //Makes a panel to hold the submit button
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        submitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getPassword().equals(userInput.getText()))  //if the password the user entered matches the user's password
                {
                    passwordFrame.setVisible(false);
                    createMainMenuFrame();  //calls the main menu frame to be created
                }
                else  //if the password the user entered does not match the user's password
                {
                    passwordField.setText("Incorrect. Try Again");
                    userInput.setBackground(Color.RED);
                }
            }
        });
        submitPanel.add(submitButton);
        passwordFrame.add(submitPanel);  //adds the panel to the JFrame

        //Code to create a back button that goes to the login menu
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        backButtonPanel.setBackground(Color.decode(backgroundColour));
        backButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        ImageIcon backButtonIcon = new ImageIcon(Constants.back);
        Image backButtonImage = backButtonIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton backButton = new JButton(new ImageIcon(backButtonImage));
        backButton.setBackground(Color.decode(backgroundColour));
        backButton.setBorder(null);  //removes the borders of the textfield
        backButton.setPreferredSize(new Dimension(200, 100));
        backButtonPanel.add(backButton);
        backButton.addActionListener(e -> {
            passwordFrame.setVisible(false);
            createLoginMenu();
        });
        passwordFrame.add(backButtonPanel);
        


        passwordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordFrame.pack();
        passwordFrame.setVisible(true);
        passwordFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 


    
    }

    

    private static void newUserProfileFrame()
    {

        System.out.println("Create new user profile."); //test

        JFrame createUser = new JFrame("Create User Profile");  //creates the Jframe
        createUser.setLayout(new BoxLayout(createUser.getContentPane(), BoxLayout.Y_AXIS));  //sets the layout of the JFrame to be a BoxLayout
        createUser.setSize(750, 750);  //sets the size of the JFrame 
        createUser.getContentPane().setBackground(Color.decode(backgroundColour)); //sets the colour of the Jframe to be the green

        // Makes the Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        titlePanel.setBackground(Color.decode(titleColour)); //sets the colour of the JPanel to be the green
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel 

        //Makes and adds the textfield to the Title Panel
        JTextField titleField = new JTextField();
        titleField.setBorder(null);  //removes the borders of the textfield
        titleField.setText("NourishNet");
        titleField.setBackground(Color.decode(titleColour));
        titleField.setFont(titleFont);  //sets the font to the titleFont
        titleField.setEditable(false);
        titlePanel.add(titleField);

        createUser.add(titlePanel);  //adds the panel to the JFrame

        
        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gapPanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        gapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel
        createUser.add(gapPanel);  //adds the panel to the JFrame


        //TO DO
        //Makes and adds a panel that will hold the photo that the user is to add for their pfp
        //They can choose to leave it as the default 
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        photoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));  //sets the maximise size of the panel
        String imagePath;
        imagePath = Constants.usersPath + "/default.png";
        
        createUser.add(photoPanel);  //adds the panel to the JFrame
        ImageIcon image = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(image);
        photoPanel.add(imageLabel);



        // Makes the TextFields Panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 1));  //creates a JPanel grid that will hold the textfields and their names
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        infoPanel.setBackground(Color.decode(backgroundColour)); //sets the colour of the JPanel to be the green
        infoPanel.setBackground(Color.decode(panelColour));
        infoPanel.setMaximumSize(new Dimension(650, 400));  //sets the maximise size of the panel 
       
        //code for creating the name text field and adding it to the TextField Panel
        JPanel namePanel = new JPanel(new GridLayout(1, 6));
        namePanel.setBackground(Color.decode(panelColour));
        namePanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(10);
        namePanel.add(nameField);
        JTextField dummy = new JTextField(10);
        dummy.setBorder(null);
        dummy.setBackground(Color.decode(panelColour));
        dummy.setEditable(false);
        namePanel.add(dummy);
        JTextField dummy2 = new JTextField(10);
        dummy2.setBorder(null);
        dummy2.setBackground(Color.decode(panelColour));
        dummy2.setEditable(false);
        namePanel.add(dummy2);
        JTextField dummy3 = new JTextField(10);
        dummy3.setBorder(null);
        dummy3.setEditable(false);
        dummy3.setBackground(Color.decode(panelColour));
        namePanel.add(dummy3);
        JTextField dummy4 = new JTextField(10);
        dummy4.setBorder(null);
        dummy4.setEditable(false);
        dummy4.setBackground(Color.decode(panelColour));
        namePanel.add(dummy4);

        infoPanel.add(namePanel);  //adds to main panel

        //code for creating the age text field and adding it to the TextField Panel
        JPanel agePanel = new JPanel(new GridLayout(2, 6));
        agePanel.setBackground(Color.decode(panelColour));
        JLabel empty = new JLabel("           ");  //adds an empty field to the grid as I want it left clear
        empty.setBorder(null);
        agePanel.add(empty);
        JPanel slashPanel = new JPanel();
        slashPanel.setBackground(Color.decode(panelColour));
        slashPanel.add(new JLabel("/"));
        JPanel slashPanel2 = new JPanel();
        slashPanel2.setBackground(Color.decode(panelColour));
        slashPanel2.add(new JLabel("/"));
        agePanel.add(new JLabel("dd"));
        agePanel.add(new JLabel(""));
        agePanel.add(new JLabel("mm"));
        agePanel.add(new JLabel(""));
        agePanel.add(new JLabel("yyyy"));
        agePanel.add(new JLabel("Date of Birth:"));
        
        JTextField dayField;
        JTextField monthField;
        JTextField yearField;
        if(System.getProperty("os.name").contains("Mac")){
            
            dayField = new JTextField(6);
            agePanel.add(dayField);
            agePanel.add(slashPanel);
            monthField = new JTextField(6);
            agePanel.add(monthField);
            agePanel.add(slashPanel2);
            yearField= new JTextField(6);
            agePanel.add(yearField);

        }else{
            dayField = new JTextField(10);
            agePanel.add(dayField);
            agePanel.add(slashPanel);
            monthField = new JTextField(10);
            agePanel.add(monthField);
            agePanel.add(slashPanel2);
            yearField= new JTextField(10);
            agePanel.add(yearField);
        }

        infoPanel.add(agePanel);  //adds to main panel


        //code for creating the weight field and adding it to the TextField Panel
        JPanel weightPanel = new JPanel(new GridLayout(1, 6));
        weightPanel.setBackground(Color.decode(panelColour));
        weightPanel.add(new JLabel("Weight:"));
        Object[] weightOptions = new Object[112]; //creates an array to hold the options for the drop down. It is of object type and not integer as the first value will be a String 
        weightOptions[0] = "Select a weight"; //the first option of the drop down is "Not Selected" which will be the default and represent the user not having selected a weight 
        int positionToAdd = 1; //this is used to keep track of the position in the array to add the next number as i starts at 40 so gotta do it separately
        for (int i = 40; i < 151; i++) {
            weightOptions[positionToAdd] = i;
            positionToAdd++;
        } //adds the numbers 40-150 to the array
        JComboBox<Object> weightField = new JComboBox<>(weightOptions); //creates the drop down
        weightField.addItemListener(new ItemListener() { //adds an item listener to the drop down
            @Override 
            public void itemStateChanged(ItemEvent e) {
            }
        });
        weightPanel.add(weightField); //adds the drop down to the panel
        weightPanel.add(new JLabel("kg"));
        JTextField dummy5 = new JTextField(10);
        dummy5.setBorder(null);
        dummy5.setBackground(Color.decode(panelColour));
        dummy5.setEditable(false);
        weightPanel.add(dummy5);
        JTextField dummy6 = new JTextField(10);
        dummy6.setBorder(null);
        dummy6.setBackground(Color.decode(panelColour));
        dummy6.setEditable(false);
        weightPanel.add(dummy6);
        JTextField dummy7 = new JTextField(10);
        dummy7.setBorder(null);
        dummy7.setEditable(false);
        dummy7.setBackground(Color.decode(panelColour));
        weightPanel.add(dummy7);
        infoPanel.add(weightPanel);  //adds to main panel



        //code for creating the height drop down and adding it to the TextField Panel
        JPanel heightPanel = new JPanel(new GridLayout(1, 6));
        heightPanel.setBackground(Color.decode(panelColour));
        heightPanel.add(new JLabel("Height:"));
        Object[] heightOptions = new Object[132]; //creates an array to hold the options for the drop down. It is of object type and not integer as the first value will be a String 
        heightOptions[0] = "Select a height"; //the first option of the drop down is "Select a height" which will be the default and represent the user not having selected a weight 
        int positionToAdd2 = 1; //this is used to keep track of the position in the array to add the next number as i starts at 40 so gotta do it separately
        for (int i = 120; i < 251; i++) {
            heightOptions[positionToAdd2] = i;
            positionToAdd2++;
        } //adds the numbers 120-250 to the array
        JComboBox<Object> heightField = new JComboBox<>(heightOptions); //creates the drop down
        heightField.addItemListener(new ItemListener() { //adds an item listener to the drop down
            @Override 
            public void itemStateChanged(ItemEvent e) {
            }
        });
        heightPanel.add(heightField); //adds the drop down to the panel
        heightPanel.add(new JLabel("cm"));
        infoPanel.add(heightPanel);  //adds to main panel

        // Code for creating the upload photo button and adding it to the infoPanel
        JTextField dummy8 = new JTextField(10);
        dummy8.setBorder(null);
        dummy8.setBackground(Color.decode(panelColour));
        dummy8.setEditable(false);
        infoPanel.add(dummy8);
        JTextField dummy9 = new JTextField(10);
        dummy9.setBorder(null);
        dummy9.setBackground(Color.decode(panelColour));
        dummy9.setEditable(false);
        infoPanel.add(dummy9);
        JTextField dummy10 = new JTextField(10);
        dummy10.setBorder(null);
        dummy10.setEditable(false);
        dummy10.setBackground(Color.decode(panelColour));
        infoPanel.add(dummy10);
        JTextField dummy11 = new JTextField(10);
        dummy11.setBorder(null);
        dummy11.setEditable(false);
        dummy11.setBackground(Color.decode(panelColour));
        infoPanel.add(dummy11);
        JTextField dummy12 = new JTextField(10);
        dummy12.setBorder(null);
        dummy12.setBackground(Color.decode(panelColour));
        dummy12.setEditable(false);
        infoPanel.add(dummy12);
        JTextField dummy13 = new JTextField(10);
        dummy13.setBorder(null);
        dummy13.setBackground(Color.decode(panelColour));
        dummy13.setEditable(false);
        infoPanel.add(dummy13);
        JTextField dummy14 = new JTextField(10);
        dummy14.setBorder(null);
        dummy14.setBackground(Color.decode(panelColour));
        dummy14.setEditable(false);
        infoPanel.add(dummy14);
        JTextField dummy15 = new JTextField(10);
        dummy15.setBorder(null);
        dummy15.setBackground(Color.decode(panelColour));
        dummy15.setEditable(false);
        infoPanel.add(dummy15);

        JButton uploadPhoto = new JButton("Upload Photo");
        infoPanel.add(uploadPhoto);  //adds to infoPanel
        

        // Variable to store the selected file path
        String[] selectedFilePath = new String[1];

        // Add action listener to the upload button
        uploadPhoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFilePath[0] = fileChooser.getSelectedFile().getPath();
                System.out.println("Selected file: " + selectedFilePath[0]);
                try{
                    BufferedImage img = ImageIO.read(new File(selectedFilePath[0]));

                    // Load image
                    Tools.loadImage("", img);

                    imageLabel.setIcon(UserManager.scaleProfileImage(new ImageIcon(img)));

                } catch(IOException ex){
                    ex.printStackTrace();

                }
            }
        });

        createUser.add(infoPanel);  //adds the JPanel to the JFrame


        
        //Code for the submission panel 
        JPanel submissionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        submissionPanel.setBackground(Color.decode(backgroundColour));
        JButton submitButton = new JButton("Submit Information");
        submissionPanel.add(submitButton);
        submissionPanel.setMaximumSize(new Dimension(400, 200));
        createUser.add(submissionPanel);  //adds the Submission Panel to the JFrame 

        String errorText = "Please fill in the fields highlighted red.";  //the error message for when the user does not fill in every field 
        JLabel errorLabel = new JLabel(errorText);  //creates an error message with the error text
        submissionPanel.add(errorLabel);  
        errorLabel.setVisible(false);  //keeps the error message invisible for now, and if the user does not fill a field, then it will be set to visible 
        

        submitButton.addActionListener(e -> {
            user = new User();
            allFieldsFilled = true;

            if (nameField.getText().isEmpty()) {
                nameField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                nameField.setBackground(Color.WHITE);
            }

            if (dayField.getText().isEmpty()) {
                dayField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                dayField.setBackground(Color.WHITE);
            }

            if (monthField.getText().isEmpty()) {
                monthField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                monthField.setBackground(Color.WHITE);
            }

            if (yearField.getText().isEmpty()) {
                yearField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                yearField.setBackground(Color.WHITE);
            }

            //working the drop down took agessssss
            Object weight = weightField.getSelectedItem();  //stores what the user selected from the drop down
            if ("Select a weight".equals(weight)) {
                weightField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                weightField.setBackground(Color.WHITE);
            }

            Object height = heightField.getSelectedItem();  //stores what the user selected from the drop down
            if ("Select a height".equals(height)) {
                heightField.setBackground(Color.RED);
                errorLabel.setVisible(true);
                allFieldsFilled = false;
            } else {
                heightField.setBackground(Color.WHITE);
            }

            if (allFieldsFilled) {   //if all the fields are filled in, then the user's profile will be created and saved to the system

                errorLabel.setVisible(false);  //removes the error message if it was needed


                String name = nameField.getText();
                String day = dayField.getText();
                String month = monthField.getText();
                String year = yearField.getText();

                user.setUsername(name);

                int intWeight = (Integer) weight;  //converts the weight from an object to an integer
                int intHeight = (Integer) height; //converts the height from an object to an integer
                user.setWeight(intWeight);
                user.setHeight(intHeight);

                int[] DOB = {Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)};
                user.setDOB(DOB);
                

                // You can use these values as needed
                System.out.println("Name: " + name); // debug
                System.out.println("DOB: " + day + "/" + month + "/" + year); // debug
                System.out.println("Weight: " + weight); // debug
                System.out.println("Height: " + height); // debug

                SerializeJsonData.serializeNewUser(user);
                UserManager.moveNewUserProfileToUserFolder(user.getId());

                createMainMenuFrame();

            }
        });

        //Code to create a back button that goes to the login menu
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        backButtonPanel.setBackground(Color.decode(backgroundColour));
        backButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        ImageIcon backButtonIcon = new ImageIcon(Constants.back);
        Image backButtonImage = backButtonIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton backButton = new JButton(new ImageIcon(backButtonImage));
        backButton.setBackground(Color.decode(backgroundColour));
        backButton.setBorder(null);  //removes the borders of the textfield
        backButton.setPreferredSize(new Dimension(200, 100));
        backButtonPanel.add(backButton);
        backButton.addActionListener(e -> {
            createUser.setVisible(false);
            createLoginMenu();
        });
        createUser.add(backButtonPanel);




        createUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Auto scale the frame based on screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);
        createUser.setSize(width, height);
        createUser.setLocationRelativeTo(null);

        createUser.setVisible(true);
        createUser.setExtendedState(JFrame.MAXIMIZED_BOTH); 



    }


    private static void createMainMenuFrame() {
        System.out.println("Run the main menu frame"); // test


        //Creates the JFrame that will act as the main menu
        JFrame mainMenuFrame = new JFrame("Main Menu Frame");
        mainMenuFrame.setLayout(new BoxLayout(mainMenuFrame.getContentPane(), BoxLayout.Y_AXIS));  //sets the layout of the JFrame to be a BoxLayout
        mainMenuFrame.setSize(750, 750);  //sets the size of the JFrame 
        mainMenuFrame.getContentPane().setBackground(Color.decode(backgroundColour)); //sets the colour of the Jframe to be the green


        // Makes the Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        titlePanel.setBackground(Color.decode(titleColour)); //sets the colour of the JPanel to be the green
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));  //sets the maximise size of the panel 

        //Makes and adds the textfield to the Title Panel
        JTextField titleField = new JTextField();
        titleField.setBorder(null);  //removes the borders of the textfield
        titleField.setText("NourishNet");
        titleField.setBackground(Color.decode(titleColour));
        titleField.setFont(titleFont);  //sets the font to the titleFont
        titleField.setEditable(false);
        titlePanel.add(titleField);

        mainMenuFrame.add(titlePanel);  //adds the panel to the JFrame


        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel.setBackground(Color.decode(backgroundColour));
        gapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        mainMenuFrame.add(gapPanel);

        // Creates the panel for the first two buttons
        JPanel buttonPanel1 = new JPanel(new GridLayout(1, 3));
        buttonPanel1.setBackground(Color.decode(panelColour));
        buttonPanel1.setMaximumSize(new Dimension(1000, 400));

        // Find Recipes button
        JButton findRecipesButton = new JButton("Find Recipes");
        findRecipesButton.setBackground(Color.decode(panelColour));
        findRecipesButton.setPreferredSize(new Dimension(100, 100));
        findRecipesButton.setBorder(null);  //removes the borders of the textfield
        buttonPanel1.add(findRecipesButton);

        //Dummy button to add a space between buttons
        JButton dummyButton = new JButton("");
        dummyButton.setBackground(Color.decode(backgroundColour));
        dummyButton.setBorder(null);  //removes the borders of the textfield
        dummyButton.setPreferredSize(new Dimension(50, 100));
        buttonPanel1.add(dummyButton);

        // View Diets button
        JButton viewDietsButton = new JButton("View Diets");
        viewDietsButton.setBackground(Color.decode(panelColour));
        viewDietsButton.setPreferredSize(new Dimension(200, 100));
        viewDietsButton.setBorder(null);  //removes the borders of the textfield
        buttonPanel1.add(viewDietsButton);

        mainMenuFrame.add(buttonPanel1);

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel2.setBackground(Color.decode(backgroundColour));
        gapPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        mainMenuFrame.add(gapPanel2);


        //The panel for the third button
        JPanel buttonPanel2 = new JPanel(new GridLayout(1, 1));
        buttonPanel2.setMaximumSize(new Dimension(1000, 400));
        JButton viewFamilyButton = new JButton("View Family");
        viewFamilyButton.setBackground(Color.decode(panelColour));
        viewFamilyButton.setPreferredSize(new Dimension(200, 100));
        viewFamilyButton.setBorder(null);  //removes the borders of the textfield
        buttonPanel2.add(viewFamilyButton);
        mainMenuFrame.add(buttonPanel2);

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel3 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel3.setBackground(Color.decode(backgroundColour));
        gapPanel3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        mainMenuFrame.add(gapPanel3);
 
        //The panel for the fourth button
        JPanel buttonPanel3 = new JPanel(new GridLayout(1, 1));
        buttonPanel3.setMaximumSize(new Dimension(1000, 400));
        JButton helpButton = new JButton("How else can I help?");
        helpButton.setBorder(null);
        helpButton.setBackground(Color.decode(panelColour));
        helpButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel3.add(helpButton);
        mainMenuFrame.add(buttonPanel3);

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel4 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel4.setBackground(Color.decode(backgroundColour));
        gapPanel4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        mainMenuFrame.add(gapPanel4);

        //The panel for the last few buttons
        JPanel buttonPanel4 = new JPanel(new GridLayout(1, 5));
        buttonPanel4.setMaximumSize(new Dimension(2000, 400));
        
        //Profile button
        ImageIcon profileIcon = new ImageIcon(Constants.profile);
        Image profileImage = profileIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton profileButton = new JButton(new ImageIcon(profileImage));
        profileButton.setBackground(Color.decode(backgroundColour));
        profileButton.setBorder(null);  //removes the borders of the textfield
        profileButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel4.add(profileButton);

        //Dummy button to add a space between buttons
        JButton dummyButton2 = new JButton("");
        dummyButton2.setBackground(Color.decode(backgroundColour));
        dummyButton2.setBorder(null);  //removes the borders of the textfield
        dummyButton2.setPreferredSize(new Dimension(50, 100));
        buttonPanel4.add(dummyButton2);

        //Home button
        ImageIcon homeIcon = new ImageIcon(Constants.home);
        Image homeImage = homeIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(homeImage));
        homeButton.setBackground(Color.decode(backgroundColour));
        homeButton.setBorder(null);  //removes the borders of the textfield
        homeButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel4.add(homeButton);

        //Dummy button to add a space between buttons
        JButton dummyButton3 = new JButton("");
        dummyButton3.setBackground(Color.decode(backgroundColour));
        dummyButton3.setBorder(null);  //removes the borders of the textfield
        dummyButton3.setPreferredSize(new Dimension(50, 100));
        buttonPanel4.add(dummyButton3);

        //Settings button
        ImageIcon settingsIcon = new ImageIcon(Constants.settings);
        Image settingsImage = settingsIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton settingsButton = new JButton(new ImageIcon(settingsImage));
        settingsButton.setBackground(Color.decode(backgroundColour));
        settingsButton.setBorder(null);  //removes the borders of the textfield
        settingsButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel4.add(settingsButton);

        mainMenuFrame.add(buttonPanel4);


        // Auto scale the frame based on screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);
        mainMenuFrame.setSize(width, height);
        mainMenuFrame.setLocationRelativeTo(null);



        //Now here are all the action listeners for the buttons

        //Action listener for the find recipes button
        findRecipesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createFindRecipesFrame();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the find recipes page"); // debug
            }
        });

        //Action listener for the view diets button
        viewDietsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createViewDietsFrame();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the view diets page"); // debug
            }
        });

        //Action listener for the view family button
        viewFamilyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createViewFamilyFrame();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the view family page"); // debug
            }
        });

        //Action listener for the help button
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createHelpFrame();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the help page"); // debug
            }
        });

        //Action listener for the profile button
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLoginMenu();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the profile page"); // debug
            }
        });

        //We dont need an action listener for the home button as we are already on the home page

        //Action listener for the settings button
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSettingsFrame();
                mainMenuFrame.setVisible(false);
                System.out.println("Open the settings page"); // debug
            }
        });

        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.pack();
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }



    public static void createSettingsFrame()
    {
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setLayout(new BoxLayout(settingsFrame.getContentPane(), BoxLayout.Y_AXIS));
        settingsFrame.setSize(750, 750);
        settingsFrame.getContentPane().setBackground(Color.decode(backgroundColour));

        // Makes the Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode(titleColour));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        //Makes and adds the textfield to the Title Panel
        JTextField titleField = new JTextField();
        titleField.setBorder(null);
        titleField.setText("Settings");
        titleField.setBackground(Color.decode(titleColour));
        titleField.setFont(titleFont);
        titleField.setEditable(false);
        titlePanel.add(titleField);

        settingsFrame.add(titlePanel);

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel.setBackground(Color.decode(backgroundColour));
        gapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        settingsFrame.add(gapPanel);

        // The panel for the first two buttons
        JPanel buttonPanel1 = new JPanel(new GridLayout(1, 2));
        buttonPanel1.setBackground(Color.decode(panelColour));
        buttonPanel1.setMaximumSize(new Dimension(1000, 400));

        //Change Password button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBackground(Color.decode(panelColour));
        changePasswordButton.setPreferredSize(new Dimension(200, 100));
        changePasswordButton.setBorder(null);
        buttonPanel1.add(changePasswordButton);

        //Dummy button to add a space between buttons
        JButton dummyButton = new JButton("");
        dummyButton.setBackground(Color.decode(backgroundColour));
        dummyButton.setBorder(null);  //removes the borders of the textfield
        dummyButton.setPreferredSize(new Dimension(50, 100));
        buttonPanel1.add(dummyButton);

        //Edit profile button
        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBackground(Color.decode(panelColour));
        editProfileButton.setPreferredSize(new Dimension(200, 100));
        editProfileButton.setBorder(null);
        buttonPanel1.add(editProfileButton);

        settingsFrame.add(buttonPanel1);

        //Makes and adds a panel in between the two panels to create a gap
        JPanel gapPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        gapPanel2.setBackground(Color.decode(backgroundColour));
        gapPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        settingsFrame.add(gapPanel2);

        // The panel for the home button
        JPanel buttonPanel3 = new JPanel(new GridLayout(1, 1));
        buttonPanel3.setMaximumSize(new Dimension(1000, 400));

        //Home button
        ImageIcon homeIcon = new ImageIcon(Constants.home);
        Image homeImage = homeIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(homeImage));
        homeButton.setBackground(Color.decode(backgroundColour));
        homeButton.setBorder(null);  //removes the borders of the textfield
        homeButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel3.add(homeButton);

        settingsFrame.add(buttonPanel3);

        // Auto scale the frame based on screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);
        settingsFrame.setSize(width, height);
        settingsFrame.setLocationRelativeTo(null);

        // Action listener for the change password button
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createChangePasswordFrame();
                settingsFrame.setVisible(false);
                System.out.println("Open the change password page"); // debug
            }
        });

        // Action listener for the edit profile button
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createEditProfileFrame();
                settingsFrame.setVisible(false);
                System.out.println("Open the edit profile page"); // debug
            }
        });

        // Action listener for the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMainMenuFrame();
                settingsFrame.setVisible(false);
                System.out.println("Open the home page"); // debug
            }
        });

        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.pack();
        settingsFrame.setVisible(true);
        settingsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }

}

                    
