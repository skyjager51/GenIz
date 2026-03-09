import React from "react";
import {Routes, Route, Link} from "react-router-dom";

function Header(){
    return(
        <div>
            <div className="header">
                <ul>
                    <Link to={'/Home'}> <li>Home</li> </Link>
                    <Link to={'/Guide'}> <li>Guide</li> </Link>
                </ul>
            </div>
        </div>
    );
}

export default Header;