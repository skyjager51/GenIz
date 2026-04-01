import api from "./apiClient";
import React, {useState, useEffect} from "react";
import {generate} from 'random-words'
import Switch from '@mui/material/Switch'
import Chat from "../components/Chat";
import Discussion from "../components/Discussion";

export const genizApiCalls = {

    //retrieve current user chats from db
    CurrentUserChats : ({selectId, setSelectedId, setChatName, refreshFlag}) => {
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
    },
    
    //retrieve current chat discussion(s) from db
    CurrentDiscussions : ({selectId, setDiscFlag, discFlag}) => {
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
    },
    
    //retrieve current model type usage from db
    CurrentModelUsageType : ({toggleModel, handleChange, checked}) => {
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
    },
    
    //create new chat
    saveNewChat : async(setRefreshFlag) => {
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
    },
    
    //delete current chat
    deleteChat : async(setRefreshFlag, chat_id, setChatName, setSelectedId) => {
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
    },
    
    //update chat name 
    updateChatName : async(newChatName, selectId, setChatName, setRefreshFlag) => {
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
    },
    
    //create new discussion from pdf
    createNewDiscussion : async(pdfText, setDiscFlag, selectId, pdfName, setPdfText, setPdfName, setGenerating) => {
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
            if (err.response?.status === 401){
                window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                return;
            }
    
            if (err.response?.status === 500){
                return alert(err.response?.data?.exceptionErrorMessage);
            } 
    
        } finally {
            setGenerating(false);
        }
    },

    logout : async() => {
        try{
            const response = await api.get('http://localhost:8080/logout');
            console.log(response);
            window.location.reload();
            
        } catch(err) {
            if (err.response?.status === 500){
                return alert(err.response?.data?.exceptionErrorMessage);
            } 
        }
    },
}