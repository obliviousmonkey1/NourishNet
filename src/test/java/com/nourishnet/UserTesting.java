package com.nourishnet;

public class UserTesting {
    public static void main(String[] args) {

        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("TestUser"));
        System.out.println(user.getUsername());

        System.out.println("Users age : " + user.getAge());

        user.setAge(user.getAge()+2);

        System.out.println("Users new age : " + user.getAge());

        SerializeJsonData.serializeUser(user, LogIn.getUserJsonPath("TestUser"));


    }

}
