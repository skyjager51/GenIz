import React from "react";
import {Routes, Route, Link} from "react-router-dom";
import geniz_logo from "../assets/geniz_logo.png";

function Header(){
    return(
        <div className="header">
            <div className="header-content">
                <div className="logo-container">
                    <Link to={'/Home'}> <img src={geniz_logo} alt="geniz logo" className="logo-image"/> </Link>
                    <p className="app-name">Gen<span id="span">Iz</span></p>
                </div>

                <div className="user-info">
                    <img src="" alt="user image"  className="user-image"/>
                    <p className="username">Hello, username!</p>
                </div>

                <div className="route-container">
                    {/*top navigation menu, using React Router <Link> components*/}
                    <ul className="routes">
                        <Link to={'/Home'}> <li>Home</li> </Link>
                        <Link to={'/Guide'}> <li>Guide</li> </Link>
                        <Link to={'/Privacy-Policy'}> <li>Privacy</li></Link>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Header;