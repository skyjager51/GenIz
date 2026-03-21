import React from "react";
import TextBlock from "./TextBlock";

function PrivacyPolicy(){
    return(
        <div>
            <TextBlock
                title="Privacy policies regarding user data."
                textList={[
                    {subtitle : "How does my data gets hadnled?", content : "Your data never leaves the machine, they are not used for any kind of activity."}
                ]}
            />
        </div>
    );
}

export default PrivacyPolicy;