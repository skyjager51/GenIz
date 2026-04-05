import React from "react";
import Chat from "../components/Chat";  // Import your components
import Discussion from "../components/Discussion";
import Switch from '@mui/material/Switch';
import { genizApiCalls } from "./apiFunctions";  // Import the utilities

// Component wrapper for CurrentUserChats: Takes props, calls utility, returns JSX
export const CurrentUserChats = ({ selectId, setSelectedId, setChatName, refreshFlag }) => {
    return genizApiCalls.CurrentUserChats({ selectId, setSelectedId, setChatName, refreshFlag });
};

// Component wrapper for CurrentDiscussions: Takes props, calls utility, returns JSX
export const CurrentDiscussions = ({ selectId, setDiscFlag, discFlag }) => {
    return genizApiCalls.CurrentDiscussions({ selectId, setDiscFlag, discFlag });
};

// Component wrapper for CurrentModelUsageType: Takes props, calls utility, returns JSX
export const CurrentModelUsageType = ({ toggleModel, handleChange, checked }) => {
    return genizApiCalls.CurrentModelUsageType({ toggleModel, handleChange, checked });
};