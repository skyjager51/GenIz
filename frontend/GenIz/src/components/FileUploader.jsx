import React, { useState, ChangeEvent} from "react";

function FileUploader(){

    //current state of the file
    const [file, setFile] = useState(null);

    //handle change in the input of the file
    function handleChange(event){
        if (event.target.files){
            setFile(event.target.files[0]);
        }
    }

    return(
        <div>
            <input type="file" onChange={handleChange}/>
            {file && (<p>Type : {file.type}</p>)}
        </div>
    );
}

export default FileUploader;