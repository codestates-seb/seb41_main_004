import { Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";

const RequireAuth = () => {
  const isLogin = useSelector((state) => state.loginStatus.isLogin);
  
  return isLogin ? (
    <Outlet />
  ) : (
    <Navigate to="/" replace />
  );
};

export default RequireAuth;
