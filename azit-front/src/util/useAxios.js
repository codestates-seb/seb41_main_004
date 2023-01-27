import axios from "axios";
import jwtDecode from "jwt-decode";
import { axiosInstance } from "./axios";
import { getCookie } from "./cookie/cookie";

// axios.defaults.withCredentials = true;

const useAxios = () => {
  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = getCookie("refreshToken");
  const email = localStorage.getItem("email");

  axiosInstance.interceptors.request.use(async (req) => {
    const expired = Date.now() >= jwtDecode(accessToken).exp * 1000;

    // console.log(refreshToken);
    // console.log(expired);

    if (!expired) return req;

    try {
      const res = await axios.get(
        `api/auth/re-issue/${email}`,
        {
          headers: {
            Refresh: refreshToken,
          },
        },
        { withCredentials : true }
      );
      console.log(res);

      localStorage.setItem("accessToken", res.headers.get("Authorization"));
    } catch (e) {
      console.dir("에러" , e);
    }

    return req;
  });

  return axiosInstance;
};

export default useAxios;
