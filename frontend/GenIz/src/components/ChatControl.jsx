import React, { useState } from "react";
import Discussion from "./Discussion";
import Chat from "./Chat";

function ChatControl(){
    const [selectId, setSelectedId] = useState(null);

    const chats = [
        { id: 1, name: "Chat One" },
        { id: 2, name: "Chat Two" },
        { id: 3, name: "Chat Three" }
    ];

    return(
        <div className="chat-discussion-panel">
            <div className="chat-list">
                <button className="add-chat">new chat</button>
                <p id="chats-lable">Chats</p>
                
                {chats.map((chat) =>(
                    <Chat 
                        key={chat.id}
                        name={chat.name}
                        isSelected={selectId === chat.id}
                        onSelect={() => setSelectedId(chat.id)}
                    />
                ))}
            </div>

            <div className="discussion-list">
                <div className="model-selection">
                    <p>chat name</p>
                    <button>model toggle</button>
                </div>
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