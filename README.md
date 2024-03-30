NourishNet Setup


Below are the instructions for downloading the necessary components to get the app running. Follow along with the steps for your operating system, or go directly to the step relevant to you.

1. Mac
    1. Install Java (To check which one you have go to about this mac and look at what the chip says)
        - To determine which version of Java to install, check your Mac's chip type.
            - For intel based macs 
                https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249545_4d245f941845490c91360409ecffb3b4
            - For Macs with Apple Silicon (M series chips):
                https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249543_4d245f941845490c91360409ecffb3b4

    2. Install java JDK : https://adoptium.net/en-GB/

    3. Install brew : In terminal run 

        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

    4. In Terminal : brew install maven 
        Test maven has installed installed : in the same terminal type mvn

1. Windows (use powershell or commandline as admin)
    1. Install java : https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249535_4d245f941845490c91360409ecffb3b4
        Install java JDK : https://adoptium.net/en-GB/
    2. Install Chocolatey : Run this code in powershell 

        Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
    
    3. Close and re-open powershell (as admin) : choco install maven 
        Test by in command line or powershell typing mvn

2. Windows (Other method fails)
    1. Install java : https://www.java.com/download/ie_manual.jsp
    2. Install Chocolatey : https://chocolatey.org/install
    3. In command line or powershell : choco install maven
        Test by in command line or powershell typing mvn
    4. https://gluonhq.com/products/javafx/
        Version : 22, Opertaing System : Windows, Architecture : x64, Type : SDK
         

Maven Dependencies (Ensure they are in the pom.xml):
    Javafx
    jackson-core

Miscellaneous:
    Avoid Fullscreen Mode in VS Code.
    For Dummy User Josh (User 0003): Password: iliketom.

