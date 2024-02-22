package com.nourishnet;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
    private static ArrayList<Diet> dietHolder = new ArrayList<Diet>();

    private static Font titleFont = new Font("Arial", Font.PLAIN, 90);  //this will be the font (and size) for the title at the top of the Frames 
    private static String titleColour = "#81AB7F";  //darker green
    private static String backgroundColour = "#AFC4B0";  //lighter green
    private static String panelColour = "#ACB7AB"; //for panels in middle of Jframes that want to be a diff colour

    private static boolean allFieldsFilled;  //boolean used in the new user profile frame to check if all the fields have been filled in. DONT TOUCH THIS

    public static void main( String[] args )
    {
        loadData();
        if(LogIn.getNumberOfUserProfiles()!=0){
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
        JFrame login = new JFrame("Login JFrame");  //creates the Jframe that will act as the login page
        login.setLayout(new FlowLayout());
        login.setSize(750, 750);
        login.getContentPane().setBackground(Color.BLACK);

        ArrayList<JButton> buttons = new ArrayList<JButton>();  //creates an ArrayList of all the buttons that will be on the frame to be formatted

        List<StringImagePair> profiles = LogIn.getUserProfiles();



        for (int i = 0; i < profiles.size(); i++) 
        {
            JButton button = new JButton(profiles.get(i).getText());
            button.putClientProperty("id", profiles.get(i).getId());

            int newWidth = 200; 
            int newHeight = 150; 

            button.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    try
                    {

                        String userJsonPath = LogIn.getUserJsonPath(button.getClientProperty("id").toString());
                        User user = DeserializeJsonData.initaliseUserClass(userJsonPath);

                        if (user.getHasPassword())  //if the user's profile is password protected
                        {
                            login.setVisible(false);
                            createPasswordFrame(user);  //calls for the password frame to be made, 
                            //and passes user as a parameter so we keep the knowledge of which user was selected
                        }

                        else                        //if the user's profile is NOT password protected
                        {
                            login.setVisible(false);
                            createMainMenuFrame(user);  
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
                newUserProfileFrame();
            }
        });

        buttons.add(createUserProfile);  //adds the createUserProfile button the the buttons ArrayList as it also needs to be formatted correctly in the frame


        formatLoginFrame(login, buttons, profiles);  //calls the function to add a title called "NourishNet" to the Jframe
   
    }

    private static void formatLoginFrame(JFrame frame, ArrayList<JButton> buttons, List<StringImagePair> profiles) {
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
    }
    
    
    
    
    

    

    



    //method for creating the password menu for profiles that are password protected
    private static void createPasswordFrame(User user)
    {
        System.out.println("Run the password frame"); //test


        JFrame passwordFrame = new JFrame("Password JFrame");
        JButton button = new JButton("Button");
        // Create a text field for user input
        JTextField textField = new JTextField(20);
        passwordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to close only the second JFrame
        passwordFrame.setSize(300, 200);
        passwordFrame.add(textField);

        JLabel label = new JLabel("Input password.");

        // Set layout for the second JFrame
        passwordFrame.setLayout(new FlowLayout());
        passwordFrame.add(label);
        passwordFrame.setVisible(true);

    

        // Add KeyListener to the text field to listen for Enter key events
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) 
            {
            }
                        
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    // Enter key is pressed, trigger the action for button2
                    button.doClick();
                                
                }
            }

            @Override
            public void keyReleased(KeyEvent e) 
            {
            }
        });

        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Retrieve user input from the text field
                String userInput = textField.getText();
                
                if (user.checkPassword(userInput))
                {
                    passwordFrame.setVisible(false);
                    createMainMenuFrame(user);

                }
            }
        });




    }


    

    private static void newUserProfileFrame()
    {
        User newUser = new User(); //Initialises a new user with all its attributes empty. Through this method, the user's attributes will be filled in (except for diets and recipes), and then the user will be saved to the system.


        System.out.println("Create new user profile."); //test

        JFrame createUser = new JFrame("Create User Profile Frame");  //creates the Jframe
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
        imagePath = Pointers.userDir + Pointers.usersPath + "/default.png";
        
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
        JTextField dayField = new JTextField(10);
        agePanel.add(dayField);
        agePanel.add(slashPanel);
        JTextField monthField = new JTextField(10);
        agePanel.add(monthField);
        agePanel.add(slashPanel2);
        JTextField yearField = new JTextField(10);
        agePanel.add(yearField);
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

                newUser.setUsername(name);

                int intWeight = (Integer) weight;  //converts the weight from an object to an integer
                int intHeight = (Integer) height; //converts the height from an object to an integer
                newUser.setWeight(intWeight);
                newUser.setHeight(intHeight);

                int[] DOB = {Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)};
                newUser.setDOB(DOB);
                



                // You can use these values as needed
                System.out.println("Name: " + name);
                System.out.println("DOB: " + day + "/" + month + "/" + year);
                System.out.println("Weight: " + weight);
                System.out.println("Height: " + height);
            }
        });

        createUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUser.setVisible(true);
    }


    //method for creatine the main menu Jframe
    private static void createMainMenuFrame(User user)
    {
        System.out.println("Run the main menu frame"); //test

        JFrame mainMenuFrame = new JFrame("Main Menu Frame");



    }

}

                    
