package com.nourishnet;

public class Constants {
    

    // VARIABLES 
    public static int numberOfRecipesReturnedDiets = 4; 
    public static int numberOfRecipesReturnedUser = 4;
    public static int scaledImageWidth = 148; //Default 148
    public static int scaledImageHeight = 148; //Default 148

    // FILE PATHS
    public static String userDir = System.getProperty("user.dir");
    public static String tempImagePath = userDir + "/Data/tempImagesHolder";
    public static String usersPath = userDir + "/Data/Users";
    public static String content = userDir + "/Data/content";
    public static String recipeImagePath = userDir + "/Data/content/recipeImages";
    public static String dietImagePath = userDir + "/Data/content/dietImages";
    public static String iconsPath = userDir + "/Data/content/icons";

    // User image paths
    public static String userDefaultImagepath = userDir + "/Data/Users/default.png";
    public static String createUserImagepath = userDir + "/Data/Users/createUser.png";


    // Colours for app
    // load this in from guiSettings.json

    /*public static UIsettings uiSettings = UserManager.getUserUiSettings();
    asign ui settings to appropriate variables 
    */

    // Icons for app
    public static String back = userDir + "/Data/content/icons/back.png";
    public static String home = userDir + "/Data/content/icons/home.png";
    public static String settings = userDir + "/Data/content/icons/settings.png";
    public static String profile = userDir + "/Data/content/icons/profile.png"; 
    public static String newUser = userDir + "/Data/content/icons/newUser.png";
    public static String welcome = userDir + "/Data/content/icons/welcome.png";
    public static String selected = userDir + "/Data/content/icons/selected.png";
    public static String notSelected = userDir + "/Data/content/icons/notSelected.png";
    public static String design2 = userDir + "/Data/content/icons/design2.png";
    public static String design3 = userDir + "/Data/content/icons/design3.png";
    public static String defaultRecipeImage = userDir + "/Data/content/recipeImages/defaultRecipeImage.png";
    public static String banner = userDir + "/Data/content/icons/banner.png";
    public static String banner2 = userDir + "/Data/content/icons/banner2.png";


    // Icons for recipes
    public static String freeFromIcon = userDir + "/Data/content/icons/freeFrom.png"; 
    public static String glutenFreeIcon = userDir + "/Data/content/icons/glutenFree.png";
    public static String healthyIcon = userDir + "/Data/content/icons/healthy.png";
    public static String cookingLevelIcon = userDir + "/Data/content/icons/level.png";
    public static String servingSizeIcon = userDir + "/Data/content/icons/serves.png";
    public static String timeIcon = userDir + "/Data/content/icons/time.png";
    public static String veganIcon = userDir + "/Data/content/icons/vegan.png";
    public static String vegetarianIcon = userDir + "/Data/content/icons/vegetarian.png";
    
}
