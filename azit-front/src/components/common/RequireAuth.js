import {  Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";

const RequireAuth = () => {
    const isLogin = useSelector((state) => state.loginStatus.isLogin);
//   const location = useLocation();
  console.log(isLogin)
  
  return isLogin ? (
    <Outlet />
  ) : (
    <Navigate to="/" replace />
    // state={{ from: location  }} 
  );
};

export default RequireAuth;
