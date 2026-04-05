import React, { useEffect, useState } from "react";
import {Routes, Route, Link} from "react-router-dom";
import geniz_logo from "../assets/geniz_logo.png";
import axios from 'axios';


//axios request to get user data
const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

//function to make an api call to the backend and retrieve user profile pic and name 
const UserDataBlock = () =>{
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const userInfo = async() => {
            try{
                const response = await api.get('/user-info');
                setData(response.data);

            } catch (err) {
                if (err.response?.status === 401){
                    window.location.replace('http://localhost:8080/oauth2/authorization/auth0');
                    return;
                }
            } finally {
                setLoading(false);
            }
        };
        userInfo();
    }, []);

    if(loading) return<div>Loading...</div>;
    if (!data) return null;

    return(
        <div className="user-info">
            <img src={data.userPicture} alt="user image"  className="user-image"/>
            <p className="username">Hello, <span>{data.userName}</span></p>
        </div>
    );
}

function Header(){
    return(
        <div className="header">
            <div className="header-content">
                <div className="logo-container">
                    <Link to={'/Home'}> <img src={geniz_logo} alt="geniz logo" className="logo-image"/> </Link>
                    <p className="app-name">Gen<span id="span">Iz</span></p>
                </div>

                {/*render current user info*/}
                <UserDataBlock></UserDataBlock>

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