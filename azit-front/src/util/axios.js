import axios from "axios";
import { getCookie, removeCookie } from "./cookie/cookie";

// axios.defaults.withCredentials = true;

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  timeout: 1000,
});

axiosInstance.interceptors.request.use(
  function (config) {

    const accessToken = localStorage.getItem('accessToken');
    axiosInstance.defaults.headers.common.Authorization = `${accessToken}`;

    return config;
  },
  function (error) {
    // 요청 에러 처리를 작성합니다.
    return Promise.reject(error);
  },
);

axiosInstance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    const { config, response: { status }} = err;
    // console.dir(err)
    if(status === 401 && !config._retry) {
      const originalRequest = config;
      const refreshToken = await getCookie('refreshToken');
      const email = await localStorage.getItem('email');
      axios.defaults.headers.common['Authorization'] = `Bearer ${refreshToken}`;
      try {
        const res = await axios.get(
          `${process.env.REACT_APP_BASE_URL}api/auth/re-issue/${email}`, // token refresh api
          {
            headers: {Refresh: refreshToken,}
          }
        ) 
        const newAccessToken = res.headers.get("Authorization");
        await localStorage.setItem('accessToken', `${newAccessToken}`);
        axiosInstance.defaults.headers.common.Authorization = `${newAccessToken}`;
        axiosInstance.defaults.headers.common.refreshToken = `${refreshToken}`;

        originalRequest.headers.Authorization = `${newAccessToken}`;

        return axios(originalRequest);
    } catch(e) {
      console.dir(e);
      localStorage.removeItem('accessToken');
      localStorage.clear();
      removeCookie('refreshToken');
      window.location.href = '/';
    }
  }
  return Promise.reject(err)
},
);

export default axiosInstance;