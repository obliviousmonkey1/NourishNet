NourishNet

1. Needs Java Maven. Please install before running. (Requires Java)

    Mac
        1. Install Java : https://www.java.com/en/download/apple.jsp
        1. Install brew : https://brew.sh/
        2. In Terminal : brew install maven 
            Test by in terminal typing mvn

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
         

3. Maven Dependencies (should be there already and working in the pom.xml)
    1. Javafx : GUI
    2. jackson-core : JSON

4. Don't have VS code on fullscreen mode 

4. For dummy user Josh (User 0003), password: iliketom.


