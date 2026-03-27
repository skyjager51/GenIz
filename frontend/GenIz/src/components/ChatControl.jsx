import React, { useEffect, useState } from "react";
import Discussion from "./Discussion";
import Chat from "./Chat";
import Switch from '@mui/material/Switch'
import { BsFillTrash3Fill } from "react-icons/bs";
import { PiExport } from "react-icons/pi";
import { IoIosSend } from "react-icons/io";
import axios from "axios";
import {generate} from 'random-words'

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
const CurrentDiscussions = ({selectId}) => {
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
    }, [selectId]);

    if(disc_loading) return<div>Loading...</div>;
    if (disc_data.length === 0) return null;

    return(
        disc_data.map((item) => {
            const parseQuizzes = JSON.parse(item.quiz_content);
            const returnedQuizzes = parseQuizzes.quizzes;

            return(
                <Discussion
                    key={item.discussion_id}
                    id={item.discussion_id}
                    source={item.user_pdf_name}
                    quizzes={returnedQuizzes}
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
            const paragrafo = document.querySelector('.message-input p');

            paragrafo.textContent = err.response?.data?.exceptionErrorMessage;

            function resetText(){
                paragrafo.textContent = 'Drag the pdf file here or click to open the file exlporer';
            };

            setTimeout(resetText, 10000);

            return;
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
            const paragrafo = document.querySelector('.message-input p');

            paragrafo.textContent = err.response?.data?.exceptionErrorMessage;

            function resetText(){
                paragrafo.textContent = 'Drag the pdf file here or click to open the file exlporer';
            };

            setTimeout(resetText, 10000);

            return;
        }
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
                const paragrafo = document.querySelector('.message-input p');

                paragrafo.textContent = err.response?.data?.exceptionErrorMessage;

                function resetText(){
                    paragrafo.textContent = 'Drag the pdf file here or click to open the file exlporer';
                };

                setTimeout(resetText, 10000);

                return;
            }
        }
    };


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
                        <button className="delete-button" onClick={() => deleteChat(setRefreshFlag, selectId, setChatName, setSelectedId)}>
                            <BsFillTrash3Fill color="#6141E8" size="18px"/></button>
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
                    refreshFlag={refreshFlag}
                />

                {/*input box for pdf elements*/}
                <div className="message-input">
                    <button className="export-button">{<PiExport size="20px" color="#6D28D9"/>}</button>
                    <p>Drag the pdf file here or click to open the file exlporer</p>
                    <button className="send-button">{<IoIosSend size='25px' color="#6D28D9"/>}</button>
                </div>
            </div>
        </div>
    );
}

export default ChatControl;