import React from "react";
import { BsFillTrash3Fill } from "react-icons/bs";

function Discussion({id, source, quizzes}){
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

                    <button className="delete-discussion-button"><BsFillTrash3Fill color="#6141E8" size="18px"/></button>

                </div>
            </div>

            <hr className="end-chat-line"/>
        </div>
    );
}

export default Discussion;