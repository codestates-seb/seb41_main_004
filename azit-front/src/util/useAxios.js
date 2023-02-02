import axios from "axios";
import jwtDecode from "jwt-decode";
import { axiosInstance } from "./axios";
import { getCookie } from "./cookie/cookie";

// axios.defaults.withCredentials = true;

const useAxios = () => {
  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = getCookie("refreshToken");
  const email = localStorage.getItem("email");

  if(accessToken) {
    axiosInstance.interceptors.request.use(async (req) => {
      const expired = Date.now() >= jwtDecode(accessToken).exp * 1000;
      
      if (!expired) return req;
  
      try {
        const res = await axios.get(
          `${process.env.REACT_APP_BASE_URL}api/auth/re-issue/${email}`,
          {
            headers: {
              Refresh: refreshToken,
            },
          },
          { withCredentials: true }
        );
        // console.log(res);
  
        localStorage.setItem("accessToken", res.headers.get("Authorization"));
<<<<<<< HEAD
        window.location.replace();
=======
        window.location.replace()
>>>>>>> e7d8333c9546a31e6203e14cfc8ee9ec4eaaf8b6
      } catch (e) {
        console.log("에러", e);
      }
  
      return req;
    });
  }

  return axiosInstance;
};

export default useAxios;
