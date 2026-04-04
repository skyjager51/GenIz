import React, { useState } from "react";
import ChatControl from "./ChatControl";
import ErrorMessage from "./ErrorMessage";

function Home(){
    //state for the error message
    const [errormessage, setErrorMessage] = useState(true);
    const [errorText, setErrorText] = useState('');

    // Home page renders main chat control panel
    return(
        <div>
            {
                errormessage ? 
                (<ErrorMessage text={errorText}></ErrorMessage>) :
                (null)
            }
            
            <ChatControl setErrorMessage={setErrorMessage} setErrorText={setErrorText}></ChatControl>
        </div>
    );
}

export default Home;