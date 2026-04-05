import React from "react";
import { FiFileText } from "react-icons/fi";
import { IoIosArrowForward } from "react-icons/io";

function Chat({name, isSelected, onSelect, id, chatSelect}){

    //chooses style based on parent-provided selection state
    const currentClass = isSelected ? 'selected-class' : 'chat-element'

    //id is available for selection logic
    let chat_id = {id};
    console.log(chat_id);

    return(
        <div className={currentClass} onClick={() =>{onSelect(); chatSelect();}}>
            <FiFileText></FiFileText>
            <p className="chat-name"> <span>{name}</span> </p>
            <IoIosArrowForward></IoIosArrowForward>
        </div>
    );
}

export default Chat;