import React, { useState } from "react";
import { BsFillTrash3Fill } from "react-icons/bs";
import { PiExport } from "react-icons/pi";
import { IoIosSend } from "react-icons/io";
import { MdMode } from "react-icons/md";
import FileUploader from "./FileUploader";
import { genizApiCalls } from "../api/apiFunctions";
import { CurrentUserChats, CurrentDiscussions, CurrentModelUsageType } from "../api/apiComponents";
import api from "../api/apiClient";

function ChatControl({setErrorMessage, setErrorText}){
    //currently selected chat id (used by Chat item highlighting)
    const [selectId, setSelectedId] = useState(null);

    //current chat display name in discussion header
    const [chatName, setChatName] = useState('No Chat Selected');

    //switch control state: true=online model, false=local model
    const [checked, setChecked] = useState();

    //class name for the top panel style (switches between online/local style wrapper)
    const [warningOnlineModel, setWarningOnlineModel] = useState('model-selection')

    //active style for local model label
    const [localModelStyle, setLocalModelStyle] = useState('model-lable-s')

    //active style for online model label
    const [onlineModelStyle, setOnlineModelStyle] = useState('model-lable-ns')

    //current counter state for chat refresh
    const [refreshFlag, setRefreshFlag] = useState(0);

    //current counter for discussion refresh 
    const [discFlag, setDiscFlag] = useState(0);

    //current state to modify chat name 
    const [newChatName, setNewChatName] = useState("");
    const [isModifyng, setIsModifyng] = useState(false);

    //current state of the extracted pdf text
    const [pdfText, setPdfText] = useState("");
    const [pdfName, setPdfName] = useState("");

    //loading wheel state
    const [generating, setGenerating] = useState(false);

    //reusable toggle finction 
    const toggleModel = (isLocalModel) => {
        setChecked(isLocalModel);

        setWarningOnlineModel(isLocalModel ? 'model-selection' : 'online-model-selection');

        setLocalModelStyle(isLocalModel ? 'model-lable-s' : 'model-lable-ns');

        setOnlineModelStyle(isLocalModel ? 'model-lable-ns' : 'model-lable-s')
    }

    //toggle handler for the user, on change update the db.
    const handleChange = async (event) => {
        const newValue = event.target.checked;
        
        toggleModel(newValue);

        try {
            const response = await api.post('/database/interactions/running-model-setting', {
                modelSetting: newValue
            });
            //if the new value is different from the current one, change it
            if (response.data.use_local_model !== newValue) {
                toggleModel(response.data.use_local_model);
            }

        } catch (err) {
            if (err.response?.status === 401){
                window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                return;
            }

            if (err.response?.status === 500){
                return alert(err.response?.data?.exceptionErrorMessage);
            }
        }
    };

    //handle change for chat name 
    const handleNewChatName = () => {
        genizApiCalls.updateChatName(newChatName, selectId, setChatName, setRefreshFlag);
        setIsModifyng(false);
        setNewChatName("");
    }


    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button className="add-chat" onClick={() => genizApiCalls.saveNewChat(setRefreshFlag)}>New Chat</button>
                <p id="chats-lable">Chats</p>
                
                {/*render the left chat list; each Chat component handles selection*/}
                <CurrentUserChats
                    selectId={selectId}
                    setSelectedId={setSelectedId}
                    setChatName={setChatName}
                    refreshFlag={refreshFlag}
                />

                {/* button to logout the current user account */}
                <button type="submit" onClick={genizApiCalls.logout} className="logout-button">Logout</button> 
            </div>

            <div className="discussion-list">
                <div className={warningOnlineModel}>
                    <div className="delete-chat-block">
                        <p className="discussion-chat-name">{chatName}</p>
                        <button className="button" onClick={() => genizApiCalls.deleteChat(setRefreshFlag, selectId, setChatName, setSelectedId, setErrorMessage, setErrorText)}>
                            <BsFillTrash3Fill color="#6141E8" size="18px"/></button>
                        
                        {/*modify button to change chat name */}
                        <div className="modify-chat-name-container">
                            {!isModifyng ? (
                                <button className="button" onClick={() => setIsModifyng(true)}>
                                    <MdMode color="#6141E8" size="18px"/>
                                </button>
                            ) :
                            (
                                <div className="modify-chat-name">
                                <input 
                                    type="text" 
                                    onChange={(e) => setNewChatName(e.target.value)}
                                    placeholder="Write New Chat Name..."
                                    /> 

                                    <button className="button" onClick={() => handleNewChatName()}>
                                        <IoIosSend size='18px' color="#6D28D9"/>
                                    </button>
                                    <button className="button" onClick={() => setIsModifyng(false)}>
                                        <BsFillTrash3Fill color="#6141E8" size="18px"/>
                                    </button>
                                </div>
                            )}
                        </div>
                        
                    </div>
                    
                    {/*loading wheel to indicate the processing of the pdf content*/}
                    <div className="loading">
                        {
                            generating ?
                            (<div class="arc"></div>) :
                            (<div></div>)
                        }
                    </div>

                    <div className="switch-button">
                        <p className={onlineModelStyle}>Online Model</p>
                        <CurrentModelUsageType
                            toggleModel={toggleModel}
                            handleChange={handleChange}
                            checked={checked}
                            selectId={selectId}
                        />
                        <p className={localModelStyle}>Local Model</p>
                    </div>
                </div>
                <p className="ai-statement">- AI responses may include mistakes -</p>
                
                {/*render discussions by parsing quiz JSON and passing parsed quizzes to Discussion component*/}
                <CurrentDiscussions
                    selectId={selectId}
                    discFlag={discFlag}
                    setDiscFlag={setDiscFlag}
                />

                {/*input box for pdf elements*/}
                <div className="message-input">
                    <button className="export-button" onClick={() => genizApiCalls.exportTxt(selectId)}>
                    {<PiExport size="20px" color="#6D28D9"/>}</button>

                    {/* <p>Drag the pdf file here or click to open the file exlporer</p> */}
                    <FileUploader 
                        setPdfText={setPdfText}
                        setPdfName={setPdfName}
                    />

                    <button className="send-button"
                        onClick={() => genizApiCalls.createNewDiscussion(pdfText, setDiscFlag, selectId, pdfName, setPdfText, setPdfName, setGenerating)}
                    >{<IoIosSend size='24px' color="#6D28D9"/>}</button>
                </div>
            </div>
        </div>
    );
}

export default ChatControl;