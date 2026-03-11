import React from "react";

function Discussion({id, source, quizzes}){
    return(
        <div className="discussion-container">
            <div className="user-pdf-name">
                <p>{source}</p>
            </div>

            <div className="quiz-content">

                <div>
                    <p><span>Id: </span>{id}</p> 
                    {quizzes.map((quiz, index) => (
                        <><h3 key={index}>{quiz.question}</h3>
                        <ul>
                            {Object.entries(quiz.options).map(([key, value]) => (
                                <li>
                                    <p><span>{key}</span> : <span>{value}</span></p>
                                </li>
                            ))}
                        </ul>

                        <p><span>Correct Answer: </span> {quiz.correct_answer}</p>
                        <p><span>Explanation: </span> {quiz.explanation}</p>
                        
                        <br />
                        </>
                    ))}
                </div>

            </div>

            <hr className="end-chat-line"/>
        </div>
    );
}

export default Discussion;