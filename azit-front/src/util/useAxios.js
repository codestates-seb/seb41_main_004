import axios from "axios";
import jwtDecode from "jwt-decode";
import { axiosInstance } from "./axios";
import { getCookie } from "./cookie/cookie";

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
      console.log("intercept");

      const body = {
        email: email,
        accessToken: accessToken,
      };
      const res = await axios.post(
        `${process.env.REACT_APP_BASE_URL}api/auth/re-issue`,
        body,
        {
          headers: {
            Refresh: refreshToken,
          },
          "Content-Type": "application/json",
        }
      );
      console.log(res);

      localStorage.setItem("accessToken", res.headers.get("Authorization"));
    } catch (e) {
      console.log(e.response.data);
      console.log("에러");
    }

    return req;
  });

  return axiosInstance;
};

export default useAxios;
