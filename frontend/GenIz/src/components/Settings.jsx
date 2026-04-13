import React, { useState } from "react";
import { genizApiCalls } from "../api/apiFunctions";


function Settings(){
    const [modelUrl, setModelUrl] = useState("");
    const [modelName, setModelName] = useState("");
    const [apiKey, setApiKey] = useState("");

    const handleSubmit = () => {
        genizApiCalls.saveOnlineModelSettings(modelUrl, modelName, apiKey);
        setApiKey("");
    }

    return(
        <div className="settings-page">
            <h2>Settings</h2>
            <p>Configure your Api Key and Model info locally in your machine.</p>
            <div className="new-online-model-settings">
                <input type="text" placeholder="Model Url..." onChange={(e) => setModelUrl(e.target.value)}/>
                <input type="text" placeholder="Model Name..." onChange={(e) => setModelName(e.target.value)}/>
                <input type="password" autoComplete="off" placeholder="Api Key..." onChange={(e) => setApiKey(e.target.value)}/>
                <button className="online-model-button" onClick={() => handleSubmit()}>Send</button>
            </div>

            <div className="logout-section">
                <h3>Logout</h3>
                <p>Click the button to logout from your GenIz account</p>
                {/* button to logout the current user account */}
                <button type="submit" onClick={()=>genizApiCalls.logout()} className="logout-button">Logout</button> 
            </div>

            <div className="delete-section">
                <h3>Delete</h3>
                <p>Click the button to delete the info related to the online model</p>
                <button className="delete-button" onClick={() => genizApiCalls.deleteOnlineModelSettings()}>Delete</button>
            </div>
        </div>
    );
}

export default Settings;