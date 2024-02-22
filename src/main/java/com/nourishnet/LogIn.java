package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;


public class LogIn {
	
    
    // 25/01/24 : TE : Gets the names and profile photos of users
    public static List<DataStructures.StringImageIdPair> getUserProfiles(){
        File userProfileDir = new File(Pointers.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        List<DataStructures.StringImageIdPair> profileList = new ArrayList<>();
        String imagePath;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){

                // create a temp user object to get the user's name
                String userId = listOfFiles[i].getName();
                String username = ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())).getUsername();

                System.out.println(listOfFiles[i].getPath()); // debug

                DataStructures.StringBooleanPair imageData =  hasImage(userId, listOfFiles[i].getPath());
                if(imageData.getHasImage()){
                    imagePath = listOfFiles[i].getPath() +"/"+ userId + imageData.getExtension();
                }else{
                    imagePath = Pointers.usersPath + "/default.png";
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
            return String.format("%04d", getNumberOfUserProfiles() + 1);
        }
    }

    // 25/01/24 : TE : Gets the number of user profiles if 0 then asks user to create one
    public static int getNumberOfUserProfiles(){
        File userProfileDir = new File(Pointers.usersPath);
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
        return Pointers.usersPath + "/" + userId + "/" + userId + ".json";
    }

    // 25/01/24 : TE : Checks if file name ends with specified extension
    private static boolean checkFileExtension(String fileName, String extension){
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase()); 
    }

    // 25/01/24 : TE : Checks if an image exists with that name 
    public static DataStructures.StringBooleanPair hasImage(String imageName, String path) {
        String[] fileExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for(String extension: fileExtensions){
            File file = new File(path + "/" + imageName + extension);
            if (file.exists()) {
                return new DataStructures.StringBooleanPair(extension, true);
            } 
        }
        return new DataStructures.StringBooleanPair("", false);
		
	}
}

