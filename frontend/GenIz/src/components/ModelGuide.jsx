import React from "react";
import TextBlock from "./TextBlock";

function ModelGuide(){
    return(
        <div>
           <TextBlock
            title="How to use GenIz"
            textList={[
            {subtitle : "1. Getting Started: Secure Access", content : `Google Authentication: Upon launch, 
                you must log in using your Google account. This creates a dedicated, private workspace on your host machine, 
                ensuring your generated content and settings remain confidential and associated only with your profile.`},

            {subtitle : "2. Managing Your Workspace", content : `The sidebar is your central hub for organizing your quiz projects.`},
            {subtitle : "", content : `- Creating Chats: Start a new project by creating a chat. Each chat serves as a specific container for the PDFs you wish to analyze.`},
            {subtitle : "", content : `- Modify Name: Click the Modify icon next to any chat name to rename it (e.g., from "New Chat" to "Physics Unit 1").`},
            {subtitle : "", content : `- Delete Chat: Use the Delete button to permanently remove a chat and its associated data when it is no longer needed.`},

            {subtitle : "3. Quiz Generation & AI Models", content : `GenIz offers control over the "brain" powering quiz generation via a toggle switch.`},
            {subtitle : "", content : `- Local Model (Default: Qwen 2.5 3b): This runs on your hardware, is private, and does 
                not require an internet connection or API keys. The model only loads into RAM during the first call to save resources.`},
            {subtitle : "", content : `- Online Model (Default: Gemini 2.5 Flash): This utilizes cloud-based power for potentially faster or more complex generations.`},
            
            {subtitle : "4. Settings & Configuration", content : `Access the Settings section to adjust your experience:`},
            {subtitle : "", content : `- Online Model Specs: Enter your custom URL, Model Name, and API Key.
                Important: you can update fields individually or all tougether, but you must restart the application for changes to take effect.`},
            {subtitle : "", content : `- Privacy & Reset: You can log out of your account or delete all stored online model information from this section. `},
            
            {subtitle : "5. Exporting and File Management", content : `After generating a quiz, you can export it.`},
            {subtitle : "", content : `- TXT Export: Click the Export button within a chat to save your quiz as a .txt file.`},
            {subtitle : "", content : `- Local Storage: All exported files are saved in the Documents/GenIz_txt folder on your host machine.`},

            {subtitle : "6. Advanced Customization (Outside the App)", content : `For those comfortable with technical adjustments, you can modify core behaviors via the Docker Compose file located at your root -> users -> [username] -> GenIzApp:`},
            {subtitle : "", content : `- Change Local Model: Search for the models: tag and update the model name. Also, update the LLM_MODEL variable under the backend: environment: section to match.`},
            {subtitle : "", content : `- Security: It is recommended to change the default database password in both the database: and backend: sections of this file.`},
            
            {subtitle : "||", content : ``},

            {subtitle : "How to choose a model", content : `The steps to follow to choose a local or online model are different,
                and this section will briefly explain how to do it.`},
            {subtitle : "", content : `To use an online AI provider within GenIz, the application requires three specific pieces 
                of information in the Settings section: a Base URL, a Model Name, and an API Key. Many providers now offer 
                "OpenAI-compatible" endpoints, which allow GenIz to communicate with them using a standardized format
                 (the application support only OpenAI-compatible endpoints).`},
            {subtitle : "", content : `Most of the bigger cloud provider support this kind of endpoint (like Gemini, OpenAi, Groq...), and
                to generate the needed info the user should visit their site and navigate to their "API" section (follow the guide of the provider 
                of your choice).`},
            {subtitle : "", content : `For the scope of GenIz, large, complex, and super intelligent models are not needed, this applicaiton 
                is more suited for cheaper models with higher troughput.`},
            {subtitle : "", content : `To choose a local model, the user should visit Docker hub and browse the available models. Thing that should
                be considered are: model size (billion of parameters), host machine memory size, presence of a GPU.`},
            {subtitle : "", content : `For computers with a shared memory of 8gb, 3b parameter models quantized to 4 is the bext choice`},
            
            {subtitle : "General guide for local model size depending on host config", content : ``},
            {subtitle : "", content : `- For machines with no GPU and 16GB of System RAM: We recommend using 1B to 3B parameter models with Q4_K_M quantization; since this relies entirely on your CPU, generation will be slower.`},
            {subtitle : "", content : `- For machines with 8GB of Shared Memory (Integrated Graphics): It is best to stick with 1B to 3B parameter models using Q4_K_M quantization for a stable and responsive experience.`},
            {subtitle : "", content : `- For machines with 16GB of Shared Memory (such as Mac M-Series): You can comfortably run 7B to 8B parameter models with Q4_K_M quantization, providing a great balance between speed and intelligence.`},
            {subtitle : "", content : `- For machines with a dedicated GPU and 4GB of VRAM: Use 1B to 3B parameter models; you can use high-quality Q8 quantization for smaller models or Q4 for a slightly larger 7B model if you don't mind slower speeds.`},
            {subtitle : "", content : `- For machines with a dedicated GPU and 8GB of VRAM: This is the "sweet spot"—we recommend 7B to 8B parameter models with Q4_K_M quantization for excellent speed and high accuracy.`},
            {subtitle : "", content : `- For high-end machines with a GPU and 12GB or more of VRAM: You can run 7B to 14B parameter models at high-precision Q8 quantization for the fastest and most accurate quiz generation.`},
            ]}
           />
        </div>
    );
}

export default ModelGuide;