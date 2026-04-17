# GenIz introduction 
Meet GenIz, the study companion designed for students who are drowning in notes but starving for practice. <br>
Built with a privacy-first mindset, GenIz app runs local LLMs directly on your machine, ensuring your study materials never leave your device. Simply upload your PDFs, and the app instantly transforms dense chapters into interactive quizzes. <br>
Whether you want to stay completely offline for maximum security or tap into public AI providers for extra horsepower, the choice is yours. Save your generated quizzes locally to build a personal question bank and conquer your exams—one practice session at a time! <br>
This software is provided for educational purposes and is used at your own risk. 
[Disclaimer](./DISCLAIMER.md)
<br>

# Download Guide 
Choose the instructions for your operating system below. GenIz uses Docker to manage its environment; the installer will handle the setup for you, but please follow the OS-specific steps to ensure a smooth launch.

### macOS Installation

Due to macOS's strict security protocols for third-party applications, you must run a specific command in your terminal to allow the app to run.

1.  **Download:** Download GenIz-installer.zip from the Releases section to your Downloads folder.
    
2.  **Extract:** Double-click the .zip file to extract it using the default macOS Archive Utility. You will see GenIz-Installer.app.
    
3.  **Authorize via Terminal:**
    
    *   Open **Terminal** (Press Cmd + Space, type "Terminal", and hit Enter).
        
    *   ```xattr -dr com.apple.quarantine ~/Downloads/GenIz-Installer.app```
        
    *   _Note: This command is strictly necessary to bypass macOS "Quarantine" settings for apps not downloaded from the App Store._
        
4.  **Install:** Double-click the GenIz-Installer.app. This will begin the installation and set up **Docker** if it isn't already on your system and the needed compose file.
    
    *   _If you already have Docker, please ensure you have the latest version of Docker Desktop and Compose installed._
        
5.  **Docker Setup:** When Docker opens for the first time, you will be asked to sign in. You can simply click **"Skip"** to continue without an account. **I recommend finishing the Docker setup/login before proceeding to open the GenIz app.**
    
6.  **Run:** After installation, a GenIz application icon will be created (you can move it where you want). Double-click it to start the app. If the GenIz app does not work, move the app back in the Download folder and run this command:
    
    *   ```xattr -dr com.apple.quarantine ~/Downloads/GenIz.app```
        
7.  **Launch:** Once running, wait a few moments. A browser tab will automatically open with the GenIz interface ready to use!
    

### Windows Installation

The Windows process is straightforward, though you may need to grant permission to the installer.

1.  **Download:** Download the .exe installer from the Releases section.
    
2.  **Browser Security:** Your browser (Chrome/Edge) might warn you about downloading an executable. Select **"Keep"** or **"Trust this file"** to proceed.
    
3.  **Install:** Double-click the installer. Follow the prompts to install the GenIz application (If Windows defender block the execution of the installer, click on 'more information' and then click on run anyway).
    
4.  **Docker Setup:** The installer will also initiate the **Docker** installation.
    
    *   Once Docker starts, it may ask you to log in or create an account. You can click **"Skip"** to continue.
        
    *   _Tip: It is best to complete the Docker setup (and restart if prompted) before launching GenIz for the first time._
        
5.  **Run:** Locate the **GenIz** app shortcut on your desktop or in your installation folder and double-click it.
    
6.  **Launch:** After a short wait while the containers initialize, a new tab will open in your default web browser displaying the GenIz app.
    

### Prerequisites Note

GenIz runs on Docker. Regardless of your OS, ensure that:

*   Docker Desktop is installed (it will be automatically installed by the installer if not present, if installation fails, install it manually from Docker).

*   The Docker compose is installed (on your root -> users -> your username, you will find a folder called GenIzApp, here the compose file is installed).
    
*   You have an active internet connection for the initial setup.
<br> 

# Usage
This is a brief guide on how to use GenIz.

### Inside the App
*   Upon launching the application, users will be prompted to log in with a Google account. This ensures each user on the host machine receives their own personal and private area for generating quizzes.

*   Within the application, users can browse and create chats.  These chats allow users to upload PDFs for quiz generation.

*   Within the applicaiton, users can find a toggle to freely chose if utilize the local model (default is qwen 2.5 3b), or use their online model (default is Gemini 2.5 flash with no api key defined).

*   Within the settings section, users can input their online model specifications (URL, model name, and api key). Each form field can be updated individually. To make online model changes effective, the user should restart the application.

*   Within the settings section, users can also logout from their current GenIz account and completely delete all the informations related to the defined online model.

*   By clicking the export button, uesrs can export txt files of their quizzes in their host machine.

### Outside the App
*   Users can change the local model by modifyng the model tag in the docker compose. To find the docker compose, navigate to 'your root -> users -> your username -> GenIzApp' and open it with your preferred file editor. Inside the docker compose, at the botton search for a tag like models: llm: model:, then, modify the model name with the desired one (look at docker hub to identify available models)(consider resource utilization from larger models). Then, users should also change with the same model name the voice inside backend: environment: LLM_MODEL. In any case, the model will not be loaded in RAM until it recieve it's first call.

*   Inside the compose file, users can also find default database password, it's highly reccomended to change it with a personal defined one, both in the database: and in the backend: sections.

*   The exported quizzes can be found inside the GenIz_txt folder generated inside the file system Documents folder.

<br>

# Key Features 
*   Local llm integration: Privacy-first quiz generation using local models (like qwen).
*   Hybrid mode: Option to toggle between local and public AI providers for extra performance.
*   Docker integration: Once Docker desktop is installed, the application can run on any host.
*   Custom possibilities: The user can avoid to use the runner and manually move and modify the docker compose as preferred.
*   Multiple Models: As long as the model (both local or online), supports OpenAi API formats, it can be used on GenIz. 
<br>

# Technologies
- **Backend**: Java, Springboot
- **Frontend**: React, Js, HTML, CSS
- **Database**: MySql
- **Auth**: [Auth0](https://auth0.com/)
- **Build**: Docker, Docker Compose, Model Runner
<br>

# Contributing 
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
<br>

<hr>
If the application does not open a browser tab by default, users can find the application by typing ```localhost:24991``` in a browser tab.

