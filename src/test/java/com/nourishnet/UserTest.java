//-----------------------------------------------------------------------------------
// Created on 23/2/2024 by Declan with contributions by Tom throughout
// 
// This class is to test the User class in main, having functions to test different 
// functions/features involved in the User class
//
// Last Edit was on 15/4/2024 by Declan 
//-----------------------------------------------------------------------------------

package com.nourishnet;

import java.util.ArrayList;

public class UserTest {

    private static void testLoadUser(){

        // Test user loaded correctly
        User user = ResourceLoader.loadUser(UserManager.getUserJsonPath("0000"));
        TestingTools.AssertEquals("0000", user.getId());

        // Test getting username loaded correctly
        TestingTools.AssertEquals("Josh", user.getUsername());

        // Test getting height loaded correctly
        TestingTools.AssertEquals(183.0, user.getHeight());

        // Test getting weight loaded correctly
        TestingTools.AssertEquals(75, user.getWeight());

        // Test getting diet loaded correctly
        TestingTools.AssertEquals("Pescatarian", user.getDiet());      

        // Test password
        TestingTools.AssertEquals("iLikeComputerScience", user.getPassword());

    }


    private static void testSaveAndDeleteUser() {
        User user = new User();

        // Testing both saving and deleting user in same function to stick to good OOD structure (DRY)

        // Test saving user
        SerializeJsonData.serializeNewUser(user);
        TestingTools.assertFileExistence(Constants.usersPath + '/' + user.getId(), true);

        // Tesrt deleting user 
        UserManager.deleteUserFolder(user.getId());
        TestingTools.assertFileExistence(Constants.usersPath + '/' + user.getId(), false);
    }

    private static void testChangePassword(){
        User user = new User();
        user.setPassword("password123");

        // Test changing password
        TestingTools.AssertEquals(true, UserManager.changeUserPassword(user, "password123", "newPassword"));
        TestingTools.AssertEquals("newPassword", user.getPassword());

        // Test changing password with incorrect old password
        TestingTools.AssertEquals(false, UserManager.changeUserPassword(user, "wrongPassword", "newPassword"));
        TestingTools.AssertEquals("newPassword", user.getPassword());

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

        // Test setting and getting password
        user.setPassword("password123");
        TestingTools.AssertEquals("password123", user.getPassword());
    }

    public static void Entry() {

        testLoadUser();
        testSaveAndDeleteUser();
        testChangePassword();
        testSettingAndGetters();

        System.out.println("[USER] All tests passed!");

    }
}