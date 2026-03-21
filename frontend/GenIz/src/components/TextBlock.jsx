import React from "react";

function TextBlock(props){
    return(
        <div className="text-block">
            <h2>{props.title}</h2>
            <br />
            {props.textList.map((text) => (
                <>
                <h3>{text.subtitle}</h3>
                <p>{text.content}</p>
                <br />
                </>
            ))}
        </div>
    );
}

export default TextBlock;