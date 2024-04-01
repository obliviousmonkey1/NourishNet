NourishNet Setup


Below are the instructions for downloading the necessary components to get the app running. Follow along with the steps for your operating system, or go directly to the step relevant to you.

1. Mac
    1. Install Java 
        - To determine which version of Java to install, check your Mac's chip type.
            - For intel based macs 
                https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249545_4d245f941845490c91360409ecffb3b4
            - For Macs with Apple Silicon (M series chips):
                https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249543_4d245f941845490c91360409ecffb3b4

    2. Install java JDK : https://adoptium.net/en-GB/

    3. Install brew : In terminal run 

        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

    4. Install maven : Once brew is installed/ if brew is already installed in terminal run

        brew install maven 

    5. Test maven has installed installed : in the same terminal type mvn

1. Windows (use powershell or commandline as admin)
    1. Install java : https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249535_4d245f941845490c91360409ecffb3b4
        Install java JDK : https://adoptium.net/en-GB/
    2. Install Chocolatey : Run this code in powershell 

        Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
    
    3. Close and re-open powershell (as admin) : choco install maven 
        Test by in command line or powershell typing mvn

2. Windows (Other method fails)
    
    Josh's 
         

Maven Dependencies (Ensure they are in the pom.xml):
    
    1. groupId : com.fasterxml.jackson.core 
        artifact id : jackson-core : version 2.13.0
        artifact id : jackson-databind : version 2.13.0
    2. groupId : org.openjfx
        artifact id : javafx-controls : version 13
        artifact id : javafx-base : version 13
        artifact id : javafx-fxml : version 13
        artifact id : javafx-graphics : version 13
    3 groupId : junit 
         artifact id : junit : version 4.11

Miscellaneous:
    Avoid Fullscreen Mode in VS Code.
    For Dummy User Josh (User 0003): Password: iliketom.

