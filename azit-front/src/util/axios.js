import axios from "axios";
import jwtDecode from "jwt-decode";
import { getCookie } from "./cookie/cookie";

// axios.defaults.withCredentials = true;

export const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_BASE_URL
})

// axiosInstance.interceptors.request.use(async (req) => {
//   const accessToken = localStorage.getItem("accessToken");
//   const refreshToken = getCookie("refreshToken");
//   const email = localStorage.getItem("email");

//   const expired = Date.now() >= jwtDecode(accessToken).exp * 1000;
//   // console.log(accessToken);
//   // console.log(refreshToken);
//   // console.log(expired);

//   if (accessToken && !expired) {
//     return req;
//   } else {
//     console.log("intercept");

//     try {
//       const body = {
//         email: email,
//         accessToken: accessToken,
//       };
//       const res = await axios.post(
//         `${process.env.REACT_APP_BASE_URL}api/auth/reIssue`,
//         body,
//         {
//           headers: {
//             Refresh: refreshToken,
//           },
//           "Content-Type": "application/json"
//         }
//       );
//       console.log(res);

//       localStorage.setItem("accessToken", res.headers.get("Authorization"));
//     } catch (e) {
//       console.log(e.response.data);
//       console.log("에러");
//     }

//     return req;
//   }
// });


