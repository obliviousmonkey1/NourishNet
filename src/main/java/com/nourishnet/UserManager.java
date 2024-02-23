package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;


public class UserManager {
	
    
    // 25/01/24 : TE : Gets the names and profile photos of users
    public static List<DataStructures.StringImageIdPair> getUserProfiles(){
        File userProfileDir = new File(Constants.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        List<DataStructures.StringImageIdPair> profileList = new ArrayList<>();
        String imagePath;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){

                // creates a temporary user object to get the user's name
                String userId = listOfFiles[i].getName();
                String username = ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())).getUsername();

                System.out.println(listOfFiles[i].getPath()); // debug

                DataStructures.StringBooleanPair imageData =  Tools.hasImage(userId, listOfFiles[i].getPath());
                if(imageData.getHasImage()){
                    imagePath = listOfFiles[i].getPath() +"/"+ userId + imageData.getExtension();
                }else{
                    imagePath = Constants.usersPath + "/default.png";
                }
                profileList.add(new DataStructures.StringImageIdPair(username, userId, new ImageIcon(imagePath)));
                
            }
        }
        return profileList;
    }

    // 15/02/24 : TE : Gets the new user id 
    public static String getNewUserId(){
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

    // 02/02/24 : TE : Gets the selectedUsersJsonPath
    public static String getUserJsonPath(String userId){
        return Constants.usersPath + "/" + userId + "/" + userId + ".json";
    }

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

