import { Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect } from "react";

const RequireAuth = () => {
  const isLogin = useSelector((state) => state.loginStatus.isLogin);
  useEffect(() => {
    window.localStorage.setItem("isLocation", true)
  },[])

  return isLogin ? (
    <Outlet />
  ) : (
    <Navigate to="/" replace />
  );
};

export default RequireAuth;
