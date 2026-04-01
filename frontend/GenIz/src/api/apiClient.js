import axios from "axios";

//create base axios api 
//axios request to get user data
const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

export default api;