import axios from 'axios';
import { setCookie } from './cookie/cookie';

axios.defaults.withCredentials = true;

export const googleLogin = `${process.env.REACT_APP_BASE_URL}oauth2/authorization/google`;

export const googleCallback = async (accesstoken, refreshtoken) => {
  if (accesstoken && refreshtoken) {
    localStorage.setItem('accessToken', accesstoken);
    setCookie('refreshToken', refreshtoken);
    window.location.replace('/');
  }
};