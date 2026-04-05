import React from "react";
import { BsFillTrash3Fill } from "react-icons/bs";
import axios from "axios";

//create base axios api 
//axios request to get user data
const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

//delete selected discussion 
const deleteDiscussion = async(discussion_id, setDiscFlag) => {
    try{
        const deletedDiscussion = await api.delete('/database/interactions/delete-discussion/' + discussion_id);
        console.log(deletedDiscussion);
        setDiscFlag(prev => prev + 1);

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
}

function Discussion({id, source, quizzes, setDiscFlag}){

    return(
        <div className="discussion-container">
            <div className="user-pdf-name">
                <p>{source}</p>
            </div>

            <div className="quiz-content">

                <div className="quizzes">
                    <p><span>Id: </span>{id}</p> 

                    {/*for each quiz item render question, options and hidden/show explanation with blur style*/}
                    {quizzes.map((quiz, index) => (
                        <><h3 key={index}>{quiz.question}</h3>
                        <ul>
                            {Object.entries(quiz.options).map(([key, value]) => (
                                <li className="quizOptions" key={key}>
                                    <p><span>{key}</span>: <span>{value}</span></p>
                                </li>
                            ))}
                        </ul>
                        
                        <div className="quiz-blur-element">
                            <p className=""><span>Correct Answer: </span> <span className="correct-answer">{quiz.correct_answer}</span></p>
                            <p><span>Explanation: </span> {quiz.explanation}</p>
                        </div>
                        
                        <br />
                        
                        </>
                    ))}

                    <button className="delete-discussion-button"><BsFillTrash3Fill color="#6141E8" size="18px"
                        onClick={() => deleteDiscussion(id, setDiscFlag)}
                    /></button>

                </div>
            </div>

            <hr className="end-chat-line"/>
        </div>
    );
}

export default Discussion;