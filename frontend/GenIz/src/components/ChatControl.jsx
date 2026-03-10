import React from "react";
import Discussion from "./Discussion";

function ChatControl(){
    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button>toggle model</button>
                <p>chats</p>
            </div>

            <div className="discussion-list">
                <p>chat name</p>
                <p>discussions</p>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
                <Discussion></Discussion>
            </div>

        </div>
    );
}

export default ChatControl;