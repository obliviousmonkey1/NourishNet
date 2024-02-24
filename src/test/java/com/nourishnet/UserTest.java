package com.nourishnet;

public class UserTest {
    /*  
     *  USER IDS 0000, 0001, 0002, 0003, 0004
     *  Get a user from the json file using        
     *  User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("ID HERE"));
     *  Use 0000 for testing saving a user to a json file
     *  Test creating a new user as well
     */

    private static void testLoadUser(){
        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("0000"));
        TestingTools.AssertEquals("0000", user.getId());
    }


    private static void testCreateAndSaveUser() {
        User user = new User();
        System.out.println(user.getId());
        SerializeJsonData.serializeNewUser(user);
    }


 private static void testSaveUser() {
        User user = new User();
        user.setId("0001"); // Assuming you're saving an existing user with ID "0001"
        System.out.println("Saving User ID: " + user.getId());
        SerializeJsonData.serializeNewUser(user);
    }
    
    private static void testSettingAndGetters() {
        // Create a user
        User user = new User();

        // Test setting and getting username
        user.setUsername("testUser");
        TestingTools.AssertEquals("testUser", user.getUsername());

        // Test setting and getting height
        user.setHeight(180);
        TestingTools.AssertEquals(180, user.getHeight());

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
    }

    private static void testPasswordFunctionality() {
        // Create a user with a password
        User user = new User();
        user.setPassword("password123");

        // Test checking password
        TestingTools.AssertTrue(user.checkPassword("password123"));
        TestingTools.AssertFalse(user.checkPassword("wrongpassword"));
    }

    public static void Entry() {
        testLoadUser();
        testCreateAndSaveUser();
        testSaveUser();
        testSettingAndGetters();
        testPasswordFunctionality();
    }
}

