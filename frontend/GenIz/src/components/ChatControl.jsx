import React, { useEffect, useState } from "react";
import Discussion from "./Discussion";
import Chat from "./Chat";
import Switch from '@mui/material/Switch'
import { BsFillTrash3Fill } from "react-icons/bs";
import { PiExport } from "react-icons/pi";
import { IoIosSend } from "react-icons/io";
import axios from "axios";

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

    //create base axios api 
    //axios request to get user data
    const api = axios.create({
        baseURL: 'http://localhost:8080',
        withCredentials: true
    });

    //retrieve current user chats from db
    const CurrentUserChats = () => {
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
        }, []);

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
    const CurrentDiscussions = () => {
        const [disc_data, setDiscData] = useState([]);
        const [disc_loading, setDiscLoading] = useState(true);

        useEffect(() => {
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
        }, []);

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


    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button className="add-chat">New Chat</button>
                <p id="chats-lable">Chats</p>
                
                {/*render the left chat list; each Chat component handles selection*/}
                <CurrentUserChats></CurrentUserChats>
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
                <CurrentDiscussions></CurrentDiscussions>

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