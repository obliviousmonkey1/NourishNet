package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;


public class LogIn {
	
    
    // 25/01/24 : TE : Gets the names and profile photos of users
    public static List<StringImagePair> getUserProfiles(){
        File userProfileDir = new File(Pointers.userDir + Pointers.usersPath);
        File[] listOfFiles = userProfileDir.listFiles();
        List<StringImagePair> profileList = new ArrayList<>();
        String imagePath;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){

                // create a temp user object to get the user's name
                String userId = listOfFiles[i].getName();
                String username = ResourceLoader.loadUser(getUserJsonPath(listOfFiles[i].getName())).getUsername();

                System.out.println(listOfFiles[i].getPath()); // debug

                StringBooleanPair imageData =  hasImage(userId, listOfFiles[i].getPath());
                if(imageData.getHasImage()){
                    imagePath = listOfFiles[i].getPath() +"/"+ userId + imageData.getExtension();
                }else{
                    imagePath = Pointers.userDir + Pointers.usersPath + "/default.png";
                }
                profileList.add(new StringImagePair(username, userId, new ImageIcon(imagePath)));
                
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
        File userProfileDir = new File(Pointers.userDir + Pointers.usersPath);
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
        return Pointers.userDir + Pointers.usersPath + "/" + userId + "/" + userId + ".json";
    }

    // 25/01/24 : TE : Checks if file name ends with specified extension
    private static boolean checkFileExtension(String fileName, String extension){
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase()); 
    }

    // 25/01/24 : TE : Checks if an image exists with that name 
    public static StringBooleanPair hasImage(String imageName, String path) {
        String[] fileExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for(String extension: fileExtensions){
            File file = new File(path + "/" + imageName + extension);
            if (file.exists()) {
                return new StringBooleanPair(extension, true);
            } 
        }
        return new StringBooleanPair("", false);
		
	}
}

// 25/01/24 : TE : Love you Josh <3
class StringImagePair {
    private String text;
    private String id;
    private ImageIcon image;

    public StringImagePair(String text, String id, ImageIcon image) {
        this.text = text;
        this.id = id;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public ImageIcon getImage() {
        return image;
    }
}

class StringBooleanPair {
    private String extension;
    private Boolean hasImage;

    public StringBooleanPair(String extension, Boolean hasImage){
        this.extension = extension;
        this.hasImage = hasImage;
    }

    public String getExtension() {
        return extension;
    }

    public Boolean getHasImage(){
        return hasImage;
    }
}