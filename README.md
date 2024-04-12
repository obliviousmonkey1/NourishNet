*README FOR THE NOURISHNET PROGRAM. PLEASE READ ALL OF THESE INSTRUCTIONS AS THE PROGRAM WILL NOT RUN PROPERLY WITHOUT ABIDING TO THIS.*

1. We Strongly advice using the Visual Studio Code IDE, which is available for free at this site: 
https://code.visualstudio.com/download. If you are using a different IDE, please understand that some things may vary, and so you may have to do slightly different things. 

2. Maven dependencies states in the pom.xml file: 
    Javafx: GUI
    Jackson-core: JSON

3. Here are the passwords for any dummy users so you can login through them: 
    Josh (User 0003), Password: iliketom


4. Now please look below for the installation process of all the files necessary for the project to work. Steps vary for MAC and Windows, so follow the instructions to whichever OS you use:



Installation process of files to make sure the project works. This concerns java, javafx, and maven.
//New readme draft written by Josh for Tom to read

*WE STRONGLY ADVISE USING THE VISUAL STUDIO CODE IDE. THIS README IS CATERED TOWARDS THAT. IF YOU ARE USING ANOTHER IDE, SOME THINGS MAY BE SLIGHTLY DIFFERENT.*

[WINDOWS - PART 1 - Installing Files]
P.S. DO NOT place these files in your program files folder on your C Drive, as that is a protected folder. Just put it in your C Drive out of any folder. For the files that are zips, extract them.

1. Install the latest version of java. This should be found on the website: https://www.java.com/download/ie_manual.jsp

2. Now Install javafx. This should be found on the website: https://gluonhq.com/products/javafx/
Select version 22, snd select the download for windows

3. Finally install maven. This should be found on the website: https://maven.apache.org/download.cgi
Install the binary zip archive. 


[WINDOWS - Part 2 - Setting up your environment variables] 
P.S. When it's stated to insert or paste the exact file path of something, find it in your file manager app, then at the top it should display the path of it. You can select that and copy it.

1. You need to now add these files to your path. Press on the start menu and type in "environment", and it should pop up with an application titled "Edit the system environment variables". Click on that. Now click on the button in the bottom right called "environment variables".

2. Okay, now we will set up the path variable. Within the SYSTEM VARIABLES list (ignore the user variables list at the top), there should be your path. Double click on it and it will open all the files you have set to the path. Press the button "new", and paste the exact file path of the BIN folder of the java you installed. You can find the bin folder by clicking into the overall java folder, and it should be one of the subfolders. Now repeat this process for javafx and maven as well. You should have added all three files' bin folders into the path variable. 

3. Next, we will now create a new system variable. Press the "new" button below the list of system variables, and enter the variable name as JAVA_HOME. For the variable value, paste in the exact file path of the ENTIRE folder of the java you installed.

4. For the final step in this part, we will now add another system variable. Set the variable name as PATH_TO_FX and within the variable value, you paste in the exact file path of the LIB folder within your javafx file. If you don't know where the lib folder is, follow the same steps as to find the bin folder, and click into the javafx folder. Instead of clicking on bin, click on lib which should be another folder there.

Web site to help if stuck on this part: https://openjfx.io/openjfx-docs/#introduction 
Go through "Introduction" until the "Run HelloWorld via Maven" tab.

[WINDOWS - PART 3 - Setting up your config file] 

1. Okay now for the final step, towards the top of the project explorer for this program, you should find the launch.json file. Click into that as that is where the final step resides. 

2. Scroll to the bottom. There should be a config file that resembles these details: 
    "type": "java",
    "name": "App",
    "request": "launch",
    "mainClass": "com.nourishnet.App",
    "projectName": "nourishnet",
    "vmArgs": "--module-path C:\\javafx-sdk-22\\lib --add-modules javafx.controls,javafx.fxml"

3. If your file path for the javafx lib folder is different, add the correct path to points to the folder on your device. 

*END OF INSTRUCTIONS FOR WINDOWS*

[Mac] 

1. Install Java

    To determine which version of Java to install, check your Mac's chip type.
    - For intel based macs 
        - https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249545_4d245f941845490c91360409ecffb3b4
    - For Macs with Apple Silicon (M series chips): 
        - https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249543_4d245f941845490c91360409ecffb3b4

2. Install java JDK : https://adoptium.net/en-GB/

3. Install brew : In terminal run

    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

4. Install maven : Once / if brew is installed, in terminal run

    brew install maven

    Test maven has installed installed : in the same terminal type mvn

*END OF INSTRUCTIONS FOR MAC*