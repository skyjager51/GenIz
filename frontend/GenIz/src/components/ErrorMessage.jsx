import React from "react";

function ErrorMessage({text}){

    return(
        <div className="error-container">
            <p>{text}</p>
        </div>
    );
}

export default ErrorMessage;