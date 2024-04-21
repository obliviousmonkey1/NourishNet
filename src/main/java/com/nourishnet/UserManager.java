package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import java.io.File;


public class UserManager {


    public static Image getUserProfileImage(String userId){
        File userProfileDir = new File(Constants.usersPath + "/" + userId);
        File[] listOfFiles = userProfileDir.listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isFile()){

                if(listOfFiles[i].getName().equals(userId + ".png")){
                    return new Image(listOfFiles[i].toURI().toString());
                }
            }
        }

        return new Image(new File(Constants.usersPath + "/default.png").toURI().toString());
    }
  
    
    // 25/01/24 : TE : Gets the names and profile photos of users
    public static List<DataStructures.StringImageIdPair> getUserProfiles(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        List<DataStructures.StringImageIdPair> profileList = new ArrayList<>();

        for(int i=0; i < listOfFiles.length; i++){
            try{
                System.out.println("Json Path: " + listOfFiles[i].getPath());

                if(listOfFiles[i].isDirectory()){

                    // creates a temporary user object to get the user's name
                    String userId = listOfFiles[i].getName();
                    Image profileImage;

                    String username = ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())).getUsername();
                    System.out.println(listOfFiles[i].getName());

                    File profileImageFile = new File(listOfFiles[i], userId + ".png");
                    System.out.println("Image path : "+ profileImageFile.getPath());

                    if(!profileImageFile.exists()){
                        profileImage = new Image(new File(Constants.usersPath + "/default.png").toURI().toString());
                        
                    }else{
                        profileImage = new Image(profileImageFile.toURI().toString());
                    }
                   
                    profileList.add(new DataStructures.StringImageIdPair(username, userId, profileImage));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return profileList;
    }


    // 24/04/24 : JZ : Gets all the users in the system
    public static ArrayList<User> getAllUsers(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        ArrayList<User> userList = new ArrayList<>();

        for(int i=0; i < listOfFiles.length; i++){
            try{
                if(listOfFiles[i].isDirectory()){
                    userList.add(ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return userList;
    }

    // 21/04/24 : JZ : Fetches the diet of the user 
    public static String getUserDiet(String userId){
        return ResourceLoader.loadUser(getUserJsonPath(userId)).getDiet();
    }


    // 15/02/24 : TE : Gets the new user id 
    protected static String getNewUserId(){
        if(getNumberOfUserProfiles() == 0){
            return "0000";
        }else{
            System.out.println(String.format("%04d", getNumberOfUserProfiles()-1));
            return String.format("%04d", getNumberOfUserProfiles()-1);
        }
    }


    // 23/02/24 : TE : Called if the serialistion fails to generate a folder for the user due to duplicate id
    protected static void generateNewUserId(User user){
        for(int i = 0; i < UserManager.getNumberOfUserProfiles()+1; i++){
            String newId = String.format("%04d", i);
            if(!new File(Constants.usersPath + '/' + newId).exists()){
                user.setUserId(newId);
                SerializeJsonData.serializeNewUser(user);
                return;
            }
        }

    }

    // 25/01/24 : TE : Gets the number of user profiles if 0 then asks user to create one
    public static int getNumberOfUserProfiles(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        
        int numberOfProfiles = 0;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){
                numberOfProfiles ++;
            }
           
        }
        return numberOfProfiles;

    }

    // 28/03/24 : TE : When a new user is created, the user profile image is moved to the user folder
    public static void moveNewUserProfileToUserFolder(String userid){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().equals(".png")){
                    File newFile = new File(Constants.usersPath + '/' + userid + '/' + userid + ".png");
                    listOfFiles[i].renameTo(newFile);
                }
            }
        }
    }

    // 20/03/24 : TE : Called before new user Jframe is called
    public static void clearTemporaryProfileImageHolder(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().equals(".png")){
                    listOfFiles[i].delete();
                }
            }
        }
    }

    // 02/02/24 : TE : Gets the selectedUsersJsonPath
    public static String getUserJsonPath(String userId){
        return Constants.usersPath + "/" + userId + "/" + userId + ".json";
    }

    public static boolean changeUserPassword(User user, String currentPassword, String newPassword){
        
        if (user.checkPassword(currentPassword)){
            user.setPassword(newPassword);
            SerializeJsonData.serializeUser(user, getUserJsonPath(user.getId())); // save the new password
            return true;
        }
        return false; // invlaid password 
    }


    // TE : Deletes a user 
    public static void deleteUserFolder(String userId){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){
                if(listOfFiles[i].getName().equals(userId)){
                    for(File file: listOfFiles[i].listFiles()){
                        file.delete();
                    }
                    listOfFiles[i].delete();
                }
            }
        }
    }

    // 21/04/24 : JZ : Changes the user's diet
    public static void changeUserDiet(User user, String diet){
        user.setDiet(diet);
        SerializeJsonData.serializeUser(user, getUserJsonPath(user.getId()));
    }
}