import React from "react";
import { FiFileText } from "react-icons/fi";
import { IoIosArrowForward } from "react-icons/io";

function Chat({name, isSelected, onSelect, id}){

    const currentClass = isSelected ? 'selected-class' : 'chat-element'
    let chat_id = {id};
    
    return(
        <div className={currentClass} onClick={onSelect}>
            <FiFileText></FiFileText>
            <p> <span>{name}</span> </p>
            <IoIosArrowForward></IoIosArrowForward>
        </div>
    );
}

export default Chat;