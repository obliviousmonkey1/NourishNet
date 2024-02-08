package com.nourishnet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*; 
import java.util.List;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Testing extends JFrame {
    public Testing() {

        System.out.println(LogIn.getNumberOfUserProfiles());

        // User user = new User();
        // user.setPassword("hello");
        // System.out.println(user.checkPassword("hello"));

        String userDir = System.getProperty("user.dir");
        setLayout(new FlowLayout());
        setSize(500, 500);
        getContentPane().setBackground(Color.BLACK);
        List<StringImagePair> profiles = LogIn.getUserProfiles();

        for (int i = 0; i < profiles.size(); i++) {
            String buttonText = profiles.get(i).getText();
            JButton button = new JButton(buttonText);

            int newWidth = 200; 
            int newHeight = 150; 

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String userJsonPath = LogIn.getUserJsonPath(buttonText);
                    try {
                        User user = DeserializeJsonData.initaliseUserClass(userJsonPath);
                        System.out.println(user.getHeight());


//------------------ 01/02/23: JZ: Runs the password Frame if the user's profile is password locked, if not it goes directly to the Main Menu JFrame
                    setVisible(false);

                    if (user.getHasPassword())  //if the user profile is password locked, so the user needs to first input a password to log in 
                    {

                        JFrame passwordFrame = new JFrame("Second JFrame");
                        JButton button2 = new JButton("Button 2");
                        // Create a text field for user input
                        JTextField textField = new JTextField(20);
                        passwordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to close only the second JFrame
                        passwordFrame.setSize(300, 200);
                        passwordFrame.add(textField);

                        JLabel label = new JLabel("You have to input a password");

    

                        // Add KeyListener to the text field to listen for Enter key events
                        textField.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                        }
                        

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                // Enter key is pressed, trigger the action for button2
                                button2.doClick();
                                
                            }
                        }

                        

                        @Override
                        public void keyReleased(KeyEvent e) {
                        }
                    });

                    button2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Retrieve user input from the text field
                            String userInput = textField.getText();
                            if (user.checkPassword(userInput))
                            {
                                passwordFrame.setVisible(false);
                                createMainMenu();

                            }
                        }
                    });

                        

                        // Set layout for the second JFrame
                        passwordFrame.setLayout(new FlowLayout());
                        passwordFrame.add(label);

                        passwordFrame.setVisible(true);

                    }

                    else 
                    {
                        createMainMenu();
                    }

                    
//------------------End of JZ's code --------------------------------------------------------------------------------------------------------------------



                        //user.changeAge(69);
                        //SerializeUserData.serializeUser(user, userJsonPath);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    

                    
                }
            });



            // GridBagConstraints gbc = new GridBagConstraints();
            // gbc.gridx = 0;
            // gbc.gridy = 0;
            // gbc.insets = new Insets(10, 10, 10, 10); // Padding

            // try{
            //     ArrayList<Recipe> recipe = DeserializeUserData.initaliseRecipeClass();
            //     ImageIcon imageIcon = Tools.getRecipeImage(recipe.get(0).getName());
            //     if(imageIcon!=null){
            //         Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            //         imageIcon = new ImageIcon(scaledImage);
            //         add(new JLabel(imageIcon));
    
            //     }else{
            //         System.out.println("No recipe image");
            //     }
               

            // } catch (Exception e1) {
            //     e1.printStackTrace();
            // }

            ImageIcon imageIcon = profiles.get(i).getImage();
            if (imageIcon != null) {
                
                Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                add(new JLabel(imageIcon));
            }

            // gbc.gridy = -1;
            add(button);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Testing window = new Testing();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setBackground(Color.BLACK);

            window.setVisible(true);
        });
    }

    //01/02/23: JZ: This creates and displays the Main Menu JFrame
    public void createMainMenu()
    {
        JFrame mainFrame = new JFrame("Second JFrame");
                    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to close only the second JFrame
                    mainFrame.setSize(300, 200);

                    JLabel label = new JLabel("This is just a test.");
                    mainFrame.add(label);

                    // Create buttons for the second JFrame
                    JButton button1 = new JButton("Button 1");  //creates the first button for the second Jframe
                    JButton button2 = new JButton("Button 2");  //creates the second button for the second Jframe 
                    JButton button3 = new JButton("Button 3");  //creates the third button for the second Jframe 
                    JButton button4 = new JButton("Button 4");  //creates the fourth button for the second Jframe 
                    JButton button5 = new JButton("Button 5");  //creates the fifth button for the second Jframe 

                    // Add ActionListener for the buttons
                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mainFrame, "Button 1 clicked!");
                        }
                    });

                    button2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mainFrame, "Button 2 clicked!");
                        }
                    });

                    button3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mainFrame, "Button 3 clicked!");
                        }
                    });

                    button4.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mainFrame, "Button 4 clicked!");
                        }
                    });

                    button5.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(mainFrame, "Button 5 clicked!");
                        }
                    });

                    // Set layout for the second JFrame
                    mainFrame.setLayout(new FlowLayout());

                    // Add buttons to the second JFrame
                    mainFrame.add(button1);  
                    mainFrame.add(button2);
                    mainFrame.add(button3);
                    mainFrame.add(button4);
                    mainFrame.add(button5);


                    mainFrame.setVisible(true);

    }
}

