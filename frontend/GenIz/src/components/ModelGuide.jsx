import React from "react";
import TextBlock from "./TextBlock";

function ModelGuide(){
    return(
        <div>
           <TextBlock
            title="How to use GenIz"
            textList={[
            {subtitle : "Intoduction", content : "GenIs is a quiz app that generate quizzes strarting from pdfs"},
            {subtitle : "Local model usage", content : "The local model uses local host resources but does not consume money and does not require internet"},
            ]}
           />
        </div>
    );
}

export default ModelGuide;