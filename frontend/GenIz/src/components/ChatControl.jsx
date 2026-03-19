import React, { useState } from "react";
import Discussion from "./Discussion";
import Chat from "./Chat";
import Switch from '@mui/material/Switch'
import { BsFillTrash3Fill } from "react-icons/bs";

function ChatControl(){
    //currently selected chat id (used by Chat item highlighting)
    const [selectId, setSelectedId] = useState(null);

    //current chat display name in discussion header
    const [chatName, setChatName] = useState('No Chat Selected');

    //switch control state: true=online model, false=local model
    const [checked, setChecked] = useState(true);

    //class name for the top panel style (switches between online/local style wrapper)
    const [warningOnlineModel, setWarningOnlineModel] = useState('model-selection')

    //active style for local model label
    const [localModelStyle, setLocalModelStyle] = useState('model-lable-s')

    //active style for online model label
    const [onlineModelStyle, setOnlineModelStyle] = useState('model-lable-ns')

    //toggle handler for the MUI Switch: updates multiple UI classes and checked state.
    const handleChange = (event) => {
        setChecked(event.target.checked);

        setWarningOnlineModel(event.target.checked ? 'model-selection' : 'online-model-selection');

        setLocalModelStyle(event.target.checked ? 'model-lable-s' : 'model-lable-ns');

        setOnlineModelStyle(event.target.checked ? 'model-lable-ns' : 'model-lable-s')
    };


    const chats = [
        { id: 1, name: "Chat One" },
        { id: 2, name: "Chat Two" },
        { id: 3, name: "Chat Three" },
        { id: 4, name: "Chat Four" },
        { id: 5, name: "Chat Five" },
        { id: 6, name: "Chat Six" },
        { id: 7, name: "Chat Seven" },
        { id: 8, name: "Chat Eight" },
        { id: 9, name: "Chat Nine" },
        { id: 10, name: "Chat Ten" },
    ];

    const quizzes = [
        {
            "discussion_id": 31,
            "quiz_content": "{\n    \"quizzes\": [\n        {\n            \"question\": \"Which of the following are core components the application should provide to the user?\",\n            \"options\": {\n                \"A\": \"UI, backend logic, AI for quiz generation, and database to save chats\",\n                \"B\": \"Only a UI and a backend logic with no AI or database\",\n                \"C\": \"A database for chats and an authentication layer only\",\n                \"D\": \"Cloud infrastructure for deployment and advanced networking\"\n            },\n            \"explanation\": \"The document states that the app should provide UI, backend logic, AI for quiz generation, and a DB to save chats as its core components.\",\n            \"correct_answer\": \"A\"\n        },\n        {\n            \"question\": \"How should the application's different services communicate with each other?\",\n            \"options\": {\n                \"A\": \"Through direct memory access\",\n                \"B\": \"Via API calls\",\n                \"C\": \"Using shared file systems\",\n                \"D\": \"By passing messages through a central queue service\"\n            },\n            \"explanation\": \"The text explicitly states that 'the microservices containers should be able to communicate to each other via api'.\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"What is the primary reason for choosing 'docker model runner' over Ollama as mentioned in the text?\",\n            \"options\": {\n                \"A\": \"Ollama does not support Nvidia GPUs.\",\n                \"B\": \"Docker model runner offers better performance when Ollama runs only over the CPU while containerized.\",\n                \"C\": \"Ollama requires a stable internet connection, which is not always available.\",\n                \"D\": \"Docker model runner provides advanced analytics for model performance.\"\n            },\n            \"explanation\": \"The document states: 'This is probably the better solution since ollama is too slow by running Only over the cpu while containerised'.\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"What mechanism is suggested for connecting the main application to the docker model runner?\",\n            \"options\": {\n                \"A\": \"A direct SSH tunnel\",\n                \"B\": \"Resolving the special DNS 'docker.internal.host'\",\n                \"C\": \"Using a dedicated VPN connection\",\n                \"D\": \"Via a message broker like Kafka\"\n            },\n            \"explanation\": \"The text mentions: 'The model runner uses the model by running it on the local host machine, this is because I have to resolve the special dns (docker.internal.host) to be able to connect to it.'\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"How should the application handle potentially slow responses from the AI model?\",\n            \"options\": {\n                \"A\": \"By implementing aggressive caching strategies.\",\n                \"B\": \"By closing the connection and retrying later.\",\n                \"C\": \"By handling 202 Accepted connections.\",\n                \"D\": \"By increasing the timeout for all API calls.\"\n            },\n            \"explanation\": \"The text specifies: 'the appliation should handle 202 accepted connections, since the response form the model may take a while.'\",\n            \"correct_answer\": \"C\"\n        },\n        {\n            \"question\": \"What is expected to happen automatically once the application has started up after the runner execution?\",\n            \"options\": {\n                \"A\": \"It should send a notification to the user's email.\",\n                \"B\": \"It should automatically open the web browser page for the home.\",\n                \"C\": \"It should display a command-line interface.\",\n                \"D\": \"It should prompt the user to configure network settings.\"\n            },\n            \"explanation\": \"The 'Description of the application' section states: 'once the applicaton started up, it should automatically open the web browser page for the home.'\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"Which file should allow the user to easily change the AI model being used?\",\n            \"options\": {\n                \"A\": \"The application's settings.json file\",\n                \"B\": \"The user interface configuration panel\",\n                \"C\": \"The docker-compose file\",\n                \"D\": \"A dedicated model management API\"\n            },\n            \"explanation\": \"The document states: 'the user should be ablke to easily change the model from the docker-compose file.'\",\n            \"correct_answer\": \"C\"\n        }\n    ]\n}",
            "user_pdf_name": "dev_docs.txt"
        },
        {
            "discussion_id": 32,
            "quiz_content": "{\n    \"quizzes\": [\n        {\n            \"question\": \"Which of the following are core components the application should provide to the user?\",\n            \"options\": {\n                \"A\": \"UI, backend logic, AI for quiz generation, and database to save chats\",\n                \"B\": \"Only a UI and a backend logic with no AI or database\",\n                \"C\": \"A database for chats and an authentication layer only\",\n                \"D\": \"Cloud infrastructure for deployment and advanced networking\"\n            },\n            \"explanation\": \"The document states that the app should provide UI, backend logic, AI for quiz generation, and a DB to save chats as its core components.\",\n            \"correct_answer\": \"A\"\n        },\n        {\n            \"question\": \"How should the application's different services communicate with each other?\",\n            \"options\": {\n                \"A\": \"Through direct memory access\",\n                \"B\": \"Via API calls\",\n                \"C\": \"Using shared file systems\",\n                \"D\": \"By passing messages through a central queue service\"\n            },\n            \"explanation\": \"The text explicitly states that 'the microservices containers should be able to communicate to each other via api'.\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"What is the primary reason for choosing 'docker model runner' over Ollama as mentioned in the text?\",\n            \"options\": {\n                \"A\": \"Ollama does not support Nvidia GPUs.\",\n                \"B\": \"Docker model runner offers better performance when Ollama runs only over the CPU while containerized.\",\n                \"C\": \"Ollama requires a stable internet connection, which is not always available.\",\n                \"D\": \"Docker model runner provides advanced analytics for model performance.\"\n            },\n            \"explanation\": \"The document states: 'This is probably the better solution since ollama is too slow by running Only over the cpu while containerised'.\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"What mechanism is suggested for connecting the main application to the docker model runner?\",\n            \"options\": {\n                \"A\": \"A direct SSH tunnel\",\n                \"B\": \"Resolving the special DNS 'docker.internal.host'\",\n                \"C\": \"Using a dedicated VPN connection\",\n                \"D\": \"Via a message broker like Kafka\"\n            },\n            \"explanation\": \"The text mentions: 'The model runner uses the model by running it on the local host machine, this is because I have to resolve the special dns (docker.internal.host) to be able to connect to it.'\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"How should the application handle potentially slow responses from the AI model?\",\n            \"options\": {\n                \"A\": \"By implementing aggressive caching strategies.\",\n                \"B\": \"By closing the connection and retrying later.\",\n                \"C\": \"By handling 202 Accepted connections.\",\n                \"D\": \"By increasing the timeout for all API calls.\"\n            },\n            \"explanation\": \"The text specifies: 'the appliation should handle 202 accepted connections, since the response form the model may take a while.'\",\n            \"correct_answer\": \"C\"\n        },\n        {\n            \"question\": \"What is expected to happen automatically once the application has started up after the runner execution?\",\n            \"options\": {\n                \"A\": \"It should send a notification to the user's email.\",\n                \"B\": \"It should automatically open the web browser page for the home.\",\n                \"C\": \"It should display a command-line interface.\",\n                \"D\": \"It should prompt the user to configure network settings.\"\n            },\n            \"explanation\": \"The 'Description of the application' section states: 'once the applicaton started up, it should automatically open the web browser page for the home.'\",\n            \"correct_answer\": \"B\"\n        },\n        {\n            \"question\": \"Which file should allow the user to easily change the AI model being used?\",\n            \"options\": {\n                \"A\": \"The application's settings.json file\",\n                \"B\": \"The user interface configuration panel\",\n                \"C\": \"The docker-compose file\",\n                \"D\": \"A dedicated model management API\"\n            },\n            \"explanation\": \"The document states: 'the user should be ablke to easily change the model from the docker-compose file.'\",\n            \"correct_answer\": \"C\"\n        }\n    ]\n}",
            "user_pdf_name": "dev_docs.txt"
        }
    ];

    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button className="add-chat">New Chat</button>
                <p id="chats-lable">Chats</p>
                
                {/*render the left chat list; each Chat component handles selection*/}
                {chats.map((chat) =>(
                    <Chat 
                        key={chat.id}
                        name={chat.name}
                        // selected class toggles by comparing state id
                        isSelected={selectId === chat.id}
                        onSelect={() => setSelectedId(chat.id)}
                        chatSelect={()=> setChatName(chat.name)}
                    />
                ))}
            </div>

            <div className="discussion-list">
                <div className={warningOnlineModel}>
                    <div className="delete-chat-block">
                        <p className="discussion-chat-name">{chatName}</p>
                        <button className="delete-discussion-button"><BsFillTrash3Fill color="#6141E8" size="18px"/></button>
                    </div>
                    

                    <div className="switch-button">
                        <p className={onlineModelStyle}>Online Model</p>
                        <Switch 
                        checked={checked}
                        onChange={handleChange}
                        sx={{
                            '& .MuiSwitch-switchBase.Mui-checked': {
                            color: '#6D28D9', // The circle color when ON
                            },
                            '& .MuiSwitch-switchBase.Mui-checked + .MuiSwitch-track': {
                            backgroundColor: '#6D28D9', // The bar color when ON
                            },
                        }}
                        />
                        <p className={localModelStyle}>Local Model</p>
                    </div>
                </div>
                <p className="ai-statement">- AI responses may include mistakes -</p>
                
                {/*render discussions by parsing quiz JSON and passing parsed quizzes to Discussion component*/}
                {quizzes.map((item) => {
                    const parseQuizzes = JSON.parse(item.quiz_content);
                    const returnedQuizzes = parseQuizzes.quizzes;

                    return(
                        <Discussion
                            id={item.discussion_id}
                            source={item.user_pdf_name}
                            quizzes={returnedQuizzes}
                        />
                    );
                })}
            </div>

        </div>
    );
}

export default ChatControl;