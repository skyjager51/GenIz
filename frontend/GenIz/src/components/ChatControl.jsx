import React, { useEffect, useState } from "react";
import Discussion from "./Discussion";
import Chat from "./Chat";
import Switch from '@mui/material/Switch'
import { BsFillTrash3Fill } from "react-icons/bs";
import { PiExport } from "react-icons/pi";
import { IoIosSend } from "react-icons/io";
import { MdMode } from "react-icons/md";
import axios from "axios";
import {generate} from 'random-words'
import FileUploader from "./FileUploader";

//create base axios api 
//axios request to get user data
const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

//retrieve current user chats from db
const CurrentUserChats = ({selectId, setSelectedId, setChatName, refreshFlag}) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const chatContent = async() => {
            try{
                const response = await api.get('/database/interactions/retrieve-all-chats');
                setData(response.data);
            } catch (err) {
                if (err.response?.status === 401){
                    window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                    return;
                }
            } finally {
                setLoading(false);
            }
        };
        chatContent();
    }, [refreshFlag]);

    if(loading) return<div>Loading...</div>;
    if (data.length === 0) return null;

    return(
        data.map((chat) =>(
                <Chat 
                    key={chat.chat_id}
                    name={chat.chat_name}
                    // selected class toggles by comparing state id
                    isSelected={selectId === chat.chat_id}
                    onSelect={() => setSelectedId(chat.chat_id)}
                    chatSelect={()=> setChatName(chat.chat_name)}
                />
            ))
    );
}

//retrieve current chat discussion(s) from db
const CurrentDiscussions = ({selectId, setDiscFlag, discFlag}) => {
    const [disc_data, setDiscData] = useState([]);
    const [disc_loading, setDiscLoading] = useState(true);

    useEffect(() => {
        if (selectId === null) {
            setDiscData([]);
            setDiscLoading(false);
            return;
        };

        const discussionContent = async() => {
            try{
                const disc_response = await api.get('/database/interactions/retrieve-all-discussions/' + selectId);
                setDiscData(disc_response.data);

            } catch (err) {
                if (err.response?.status === 401){
                    window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                    return;
                }
            } finally {
                setDiscLoading(false);
            }
        };
        discussionContent();
    }, [selectId, discFlag]);

    if(disc_loading) return<div>Loading...</div>;
    if (disc_data.length === 0) return null;

    return(
        disc_data.map((item) => {
            const parseQuizzes = JSON.parse(item.quiz_content);
            // const returnedQuizzes = parseQuizzes.quizzes;
            const dataRoot = parseQuizzes.llmOutput ? parseQuizzes.llmOutput : parseQuizzes;
            const returnedQuizzes = dataRoot.quizzes || [];

            return(
                <Discussion
                    key={item.discussion_id}
                    id={item.discussion_id}
                    source={item.user_pdf_name}
                    quizzes={returnedQuizzes}
                    setDiscFlag={setDiscFlag}
                /> 
            );
        })
    );
}

//retrieve current model type usage from db
const CurrentModelUsageType = ({toggleModel, handleChange, checked}) => {
    const [model_data, set_model_data] = useState(true);
    const [model_loading, set_model_loading] = useState(true);

    //retrieve
    useEffect(() => {
        const getCurrentModelSetting = async() => {
            try{
                const modelSettingResponse = await api.get('/database/interactions/retrieve-model-setting');
                set_model_data(modelSettingResponse.data);
                toggleModel(modelSettingResponse.data.use_local_model);
            } catch (err) {
                if (err.response?.status === 401){
                    window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                    return;
                }
            } finally {
                set_model_loading(false);
            }
        };
        getCurrentModelSetting();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    if(model_loading) return<div>Loading...</div>;
    if (model_data === null) return null;

    return(
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
    );
}

//create new chat
const saveNewChat = async(setRefreshFlag) => {
    try{
        const createChat = await api.post('/database/interactions/save-new-chat',
            {chatName : 'New Chat ' + generate({ minLength: 3, maxLength: 8 })}
        );
        console.log(createChat);
        setRefreshFlag(prev => prev + 1);

    } catch(err) {
        if (err.response?.status === 401){
            window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
            return;
        }

        if (err.response?.status === 500){
            return alert(err.response?.data?.exceptionErrorMessage);

        }
    }
}

//delete current chat
const deleteChat = async(setRefreshFlag, chat_id, setChatName, setSelectedId) => {
    try{
        const deletedChat = await api.delete('/database/interactions/delete-chat/' + chat_id);
        console.log(deletedChat);
        setRefreshFlag(prev => prev + 1);
        setChatName('No Chat Selected');
        setSelectedId(null);

    } catch(err) {
        if (err.response?.status === 401){
            window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
            return;
        }

        if (err.response?.status === 500){
            return alert(err.response?.data?.exceptionErrorMessage);
        }
    }
}

//update chat name 
const updateChatName = async(newChatName, selectId, setChatName, setRefreshFlag) => {
    try{
        const patchChatName = await api.patch('/database/interactions/update-chat-name',
            {chatName : newChatName, chat_id : selectId}
        );
        setRefreshFlag(prev => prev + 1);
        setChatName(newChatName);
        console.log(patchChatName);

    } catch(err) {
       if (err.response?.status === 401){
            window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
            return;
        }

        if (err.response?.status === 500){
            return alert(err.response?.data?.exceptionErrorMessage);
        } 
    }
}

//create new discussion from pdf
const createNewDiscussion = async(pdfText, setDiscFlag, selectId, pdfName, setPdfText, setPdfName, setGenerating) => {
    if (pdfText === "") return;
    if (pdfName === "") return;

    try{
        //create the loading wheel
        setGenerating(true);

        //retrieve current model usage
        const modelSettingResponse = await api.get('/database/interactions/retrieve-model-setting');

        //generate the quiz from user text
        const generateQuiz = await api.post('/generate-quizzes/generate',
            {userText: pdfText, useLocalModel : modelSettingResponse.data.use_local_model}
        )

        const actualQuizJson = generateQuiz.data.llmOutput;

        //save the generated quiz
        const createDiscussion = await api.post('/database/interactions/save-new-discussion',
            {chat_id : selectId, user_pdf_name : pdfName, quiz_content : actualQuizJson}
        );
        console.log(createDiscussion);

        setDiscFlag(prev => prev + 1);
        setPdfText("");
        setPdfName("");

    } catch(err) {
        return alert(err.response?.data?.exceptionErrorMessage);

    } finally {
        setGenerating(false);
    }
}


function ChatControl(){
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
        updateChatName(newChatName, selectId, setChatName, setRefreshFlag);
        setIsModifyng(false);
        setNewChatName("");
    }


    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button className="add-chat" onClick={() => saveNewChat(setRefreshFlag)}>New Chat</button>
                <p id="chats-lable">Chats</p>
                
                {/*render the left chat list; each Chat component handles selection*/}
                <CurrentUserChats
                    selectId={selectId}
                    setSelectedId={setSelectedId}
                    setChatName={setChatName}
                    refreshFlag={refreshFlag}
                />
            </div>

            <div className="discussion-list">
                <div className={warningOnlineModel}>
                    <div className="delete-chat-block">
                        <p className="discussion-chat-name">{chatName}</p>
                        <button className="button" onClick={() => deleteChat(setRefreshFlag, selectId, setChatName, setSelectedId)}>
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
                            (<div className="loading-wheel"></div>) :
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
                    <button className="export-button">{<PiExport size="20px" color="#6D28D9"/>}</button>

                    {/* <p>Drag the pdf file here or click to open the file exlporer</p> */}
                    <FileUploader 
                        setPdfText={setPdfText}
                        setPdfName={setPdfName}
                    />

                    <button className="send-button"
                        onClick={() => createNewDiscussion(pdfText, setDiscFlag, selectId, pdfName, setPdfText, setPdfName, setGenerating)}
                    >{<IoIosSend size='24px' color="#6D28D9"/>}</button>
                </div>
            </div>
        </div>
    );
}

export default ChatControl;