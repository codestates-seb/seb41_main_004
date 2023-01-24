import axios from "axios";
import jwtDecode from "jwt-decode";
import { getCookie } from "./cookie/cookie";


export const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_BASE_URL
})

// axiosInstance.interceptors.request.use(async (req) => {

//     const accessToken = localStorage.getItem('accessToken');
//     const refreshToken = getCookie('refreshToken');
//     const expired = Date.now() > jwtDecode(accessToken).exp * 1000;
//     console.log(accessToken)
//     console.log(refreshToken)
//     console.log(expired)

//     if (accessToken && !expired) {
//         return req;
//     } 
//     else console.log('intercept')

//     try {
//       console.log('interceptors ran');

//       const res = await axios.get(
//         `${process.env.REACT_APP_BASE_URL}api/auth/reIssue`,
//         {
//           headers: {
//             Authorization: accessToken,
//             Refresh: refreshToken,
//           },
//         }
//       );

//       localStorage.setItem('accessToken', res.headers.get('Authorization'));
//     } catch (e) {
//       console.log(e.response.data);
//     }

//     return req;
//   });


