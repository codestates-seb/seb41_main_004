import axios from "axios";
import jwtDecode from "jwt-decode";
import { getCookie } from "./cookie/cookie";

// axios.defaults.withCredentials = true;

export const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_BASE_URL
})




