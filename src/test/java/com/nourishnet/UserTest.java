//-----------------------------------------------------------------------------------
// Created on 23/2/2024 by Declan with contributions by Tom throughout
// 
// This class is to test the User class in main, having functions to test different 
// functions/features involved in the User class
//
//-----------------------------------------------------------------------------------


package com.nourishnet;

import java.util.ArrayList;

public class UserTest {
    /*  
     *  USER IDS 0000, 0001, 0002, 0003, 0004
     *  Get a user from the json file using        
     *  User user = ResourceLoader.UserManager(LogIn.getUserJsonPath("ID HERE"));
     *  Use 0000 for testing saving a user to a json file
     *  Test creating a new user as well
     */

    private static void testLoadUser(){

        // Test user loaded correctly
        User user = ResourceLoader.loadUser(UserManager.getUserJsonPath("0000"));
        TestingTools.AssertEquals("0000", user.getId());

        // Test getting username loaded correctly
        TestingTools.AssertEquals("Test", user.getUsername());

        // Test getting height loaded correctly
        TestingTools.AssertEquals(280.0, user.getHeight());

        // Test getting weight loaded correctly
        TestingTools.AssertEquals(76, user.getWeight());

        // Test getting diet loaded correctly
        TestingTools.AssertEquals("Vegan", user.getDiet());

        // Test savedRecipeIDs loaded correctly
        TestingTools.AssertEquals(0, user.getSavedRecipeIDs().get(0));
        TestingTools.AssertEquals(1, user.getSavedRecipeIDs().get(1));
        TestingTools.AssertEquals(3, user.getSavedRecipeIDs().get(2));

        // Test password
        TestingTools.AssertEquals("", user.getPassword());

    }


    private static void testSaveAndDeleteUser() {
        User user = new User();

        // Test saving user
        SerializeJsonData.serializeNewUser(user);
        TestingTools.assertFileExistence(Constants.usersPath + '/' + user.getId(), true);

        // Tesrt deleting user 
        UserManager.deleteUserFolder(user.getId());
        TestingTools.assertFileExistence(Constants.usersPath + '/' + user.getId(), false);
    }

    
    private static void testSettingAndGetters() {
        // Create a user
        User user = new User();

        // Test setting and getting username
        user.setUsername("testUser");
        TestingTools.AssertEquals("testUser", user.getUsername());

        // Test setting and getting height
        user.setHeight(180);
        TestingTools.AssertEquals(180.0, user.getHeight());

        // Test setting and getting weight
        user.setWeight(75);
        TestingTools.AssertEquals(75, user.getWeight());

        // Test setting and getting diet
        user.setDiet("Vegan");
        TestingTools.AssertEquals("Vegan", user.getDiet());

        // Test setting and getting savedRecipeIDs
        ArrayList<Integer> savedRecipeIDs = new ArrayList<>();
        savedRecipeIDs.add(1);
        savedRecipeIDs.add(2);
        user.setSavedRecipeIDs(savedRecipeIDs);
        TestingTools.AssertEquals(savedRecipeIDs, user.getSavedRecipeIDs());

        // Test setting and getting password
        user.setPassword("password123");
        TestingTools.AssertEquals("password123", user.getPassword());
    }

    public static void Entry() {
        testLoadUser();
        testSaveAndDeleteUser();
        testSettingAndGetters();

        System.out.println("[USER] All tests passed!");

    }
}
