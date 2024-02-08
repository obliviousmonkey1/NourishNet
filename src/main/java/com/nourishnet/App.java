package com.nourishnet;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.List;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ArrayList<Ingredient> ingredientHolder = new ArrayList<Ingredient>();
    private static ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
    private static ArrayList<Diet> dietHolder = new ArrayList<Diet>();

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
        login.setSize(500, 500);
        login.getContentPane().setBackground(Color.BLACK);
        List<StringImagePair> profiles = LogIn.getUserProfiles();
        login.setVisible(true);

        for (int i = 0; i < profiles.size(); i++) 
        {
            String buttonText = profiles.get(i).getText();
            JButton button = new JButton(buttonText);

            int newWidth = 200; 
            int newHeight = 150; 

            button.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    try
                    {

                        String userJsonPath = LogIn.getUserJsonPath(buttonText);
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

            ImageIcon imageIcon = profiles.get(i).getImage();
            if (imageIcon != null) {
                
                Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                login.add(new JLabel(imageIcon));
            }

            login.add(button);
        }


        //code for the "Create New User Profile" button 
        JButton createUserProfile = new JButton("Create New User Profile");
        login.add(createUserProfile);
        createUserProfile.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                newUserProfileFrame();
            }
        });
   
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
        System.out.println("Create new user profile."); //test
    }


    //method for creatine the main menu Jframe
    private static void createMainMenuFrame(User user)
    {
        System.out.println("Run the main menu frame"); //test

        JFrame mainMenuFrame = new JFrame("Main Menu Frame");


    }

}

                    
