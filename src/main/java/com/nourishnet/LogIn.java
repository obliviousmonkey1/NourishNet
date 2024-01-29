package com.nourishnet;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.swing.ImageIcon;


public class LogIn {
	static String userDir = System.getProperty("user.dir");
	static String extension = "json";

    // 25/01/23 : TE : Gets the names and profile photos of users
    public static List<StringImagePair> getUserProfiles(){
        File userProfileDir = new File(userDir + "/users");
        File[] listOfFiles = userProfileDir.listFiles();
        List<StringImagePair> profileList = new ArrayList<>();
        String imgPath;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){
                String username = listOfFiles[i].getName();
                System.out.println(listOfFiles[i].getPath());

                StringBooleanPair hImage =  hasImage(username, listOfFiles[i].getPath());
                if(hImage.getHasImage()){
                    imgPath = listOfFiles[i].getPath() +"/"+ username + hImage.getExtension();
                }else{
                    imgPath = userDir + "/users/default.png";
                }
                profileList.add(new StringImagePair(username, new ImageIcon(imgPath)));
                
            }
        }
        return profileList;
    }

    // 25/01/23 : TE : Gets the number of user profiles if 0 then asks user to create one
    public static int getNumberOfUserProfiles(){
        File userProfileDir = new File(userDir + "/users");
        File[] listOfFiles = userProfileDir.listFiles();
        
        int numberOfProfiles = 0;

        for(int i=0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isDirectory()){
                numberOfProfiles ++;
            }
           
        }
        return numberOfProfiles;

    }

    // 25/01/23 : TE : Checks if file name ends with specified extension
    private static boolean checkFileExtension(String fileName, String extension){
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase()); 
    }

    // 25/01/23 : TE : Checks if an image exists with that name 
    public static StringBooleanPair hasImage(String imageName, String path) {
        String[] fileExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for(String extension: fileExtensions){
            File file = new File(path +"/"+ imageName + extension);
            if (file.exists()) {
                return new StringBooleanPair(extension, true);
            } 
        }
        return new StringBooleanPair("", false);
		
	}
}

// 25/01/23 : TE : Love you Josh
class StringImagePair {
    private String text;
    private ImageIcon image;

    public StringImagePair(String text, ImageIcon image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
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