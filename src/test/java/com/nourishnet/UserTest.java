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

    private static void testSaveUser(){
	     System.out.println(user.getId());
        SerializeJsonData.serializeNewUser(user);
    }
    

    public static void Entry() {
        testLoadUser();
		testCreateAndSaveUser();
	}
}
