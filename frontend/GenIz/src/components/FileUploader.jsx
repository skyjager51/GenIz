import React from "react";
import pdfToText from "react-pdftotext";

function FileUploader({setPdfText, setPdfName}){

    //function to extract the pdf text
    function extractText(event){
        const text = event.target.files[0];

        if(text){
            setPdfName(text.name);

            pdfToText(text)
                .then((pdfText) => setPdfText(pdfText))
                .catch((error) => alert('failed to extract text from pdf ' + error));            
        }
    }

    return(
        <div>
            <input type="file" accept="application/pdf" onChange={extractText} className="input-pdf"/>
        </div>
    );
}

export default FileUploader;