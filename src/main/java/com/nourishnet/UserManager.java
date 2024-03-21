package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;
import java.awt.Image;



public class UserManager {

    // 21/03/24 : TE : Gets the user profile of the user
    public static DataStructures.StringImagePair getUserProfile(String userId){
        File userProfileDir = new File(Constants.usersPath + "/" + userId);
        File[] listOfFiles = userProfileDir.listFiles();
        ImageIcon scaledImageIcon;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().equals(userId + ".png")){
                    ImageIcon imageIcon = new ImageIcon(listOfFiles[i].getPath());
                    scaledImageIcon = scaleProfileImage(imageIcon);
                    return new DataStructures.StringImagePair(userId, scaledImageIcon);
                }
            }
        }
        return null;
    }
    
    // 25/01/24 : TE : Gets the names and profile photos of users
    public static List<DataStructures.StringImageIdPair> getUserProfiles(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        List<DataStructures.StringImageIdPair> profileList = new ArrayList<>();
        String imagePath;
        ImageIcon scaledImageIcon;

        for(int i=0; i < listOfFiles.length; i++){
            try{
                if(listOfFiles[i].isDirectory()){

                    // creates a temporary user object to get the user's name
                    String userId = listOfFiles[i].getName();
                    String username = ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())).getUsername();

                    DataStructures.StringBooleanPair imageData =  Tools.hasImage(userId, listOfFiles[i].getPath());
                    if(imageData.getHasImage()){
                        ImageIcon imageIcon = new ImageIcon(listOfFiles[i].getPath() +"/"+ userId + imageData.getExtension());
                        scaledImageIcon = scaleProfileImage(imageIcon);

                    }else{
                        ImageIcon imageIcon = new ImageIcon(Constants.usersPath + "/default.png");
                        scaledImageIcon = scaleProfileImage(imageIcon);
                    }
                    profileList.add(new DataStructures.StringImageIdPair(username, userId, scaledImageIcon));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return profileList;
    }

    public static ImageIcon scaleProfileImage(ImageIcon imageIcon){
        Image scaledImage = imageIcon.getImage().getScaledInstance(Constants.scaledImageWidth, Constants.scaledImageHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // 15/02/24 : TE : Gets the new user id 
    protected static String getNewUserId(){
        if(getNumberOfUserProfiles() == 0){
            return "0000";
        }else{
            return String.format("%04d", getNumberOfUserProfiles());
        }
    }

    // 23/02/24 : TE : Called if the serialistion fails to generate a folder for the user due to duplicate id
    protected static void generateNewUserId(User user){
        for(int i = 0; i < UserManager.getNumberOfUserProfiles(); i++){
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

    public static boolean changeUserPassword(User user, String oldPassword, String newPassword){
        
        if (user.checkPassword(oldPassword)){
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
}

