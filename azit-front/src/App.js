import GlobalStyle from "./GlobalStyle";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Search from "./pages/Search";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import ReviewCreate from "./pages/ReviewCreate";
import Azitcreate from "./pages/Azitcreate";
import UserPage from "./pages/UserPage";
import AzitSetting from "./pages/AzitSetting";
import AzitEdit from "./pages/AzitEdit";
import AzitJoin from "./pages/AzitJoin";
import UserPageSetting from "./pages/UserPageSetting";
import FollowCheck from "./pages/FollowCheck";
import FindPassword from "./pages/FindPassword";
import SignupAdditional from "./pages/SignupAdditional";
import Loading from "./pages/Loading";
import AzitReport from "./pages/AzitReport";
import ChatList from "./pages/ChatList";
import UserProfileEdit from "./pages/UserProfileEdit";
import UserResetPassword from "./pages/UserResetPassword";
import UserReport from "./pages/UserReport";
import Chat from "./components/ChatList/Chat";
import AzitDetail from "./pages/AzitDetail";
import AzitPreview from "./pages/AzitPreview";


function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/chat" element={<ChatList />}></Route>
          <Route path="/chat/detail" element={<Chat />}></Route>
          <Route path="/search" element={<Search />}></Route>
          <Route path="/profile" element={<Home />}></Route>
          <Route path="/review/Create" element={<ReviewCreate />}></Route>
          <Route path="/create" element={<Azitcreate />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/find" element={<FindPassword />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/signupadd" element={<SignupAdditional />}></Route>
          <Route path="/userpage" element={<UserPage />}></Route>
          <Route path="/userpage/setting" element={<UserPageSetting />}></Route>
          <Route path="/userpage/userreport" element={<UserReport />}></Route>
          <Route path="/azitsetting" element={<AzitSetting />}></Route>
          <Route path="/azitedit" element={<AzitEdit />}></Route>
          <Route path="/azitjoin" element={<AzitJoin />}></Route>
          <Route path="/followcheck" element={<FollowCheck />}></Route>
          <Route path="/useredit" element={<UserProfileEdit />}></Route>
          <Route path="/userreset" element={<UserResetPassword />}></Route>
          <Route path="/azitsetting" element={<AzitSetting />}></Route>
          <Route path="/azitedit" element={<AzitEdit />}></Route>
          <Route path="/azitjoin" element={<AzitJoin />}></Route>
          <Route path="/loading" element={<Loading />}></Route>
          <Route path="/azitreport" element={<AzitReport />}></Route>
          <Route path="/azitdetail" element={<AzitDetail />}></Route>
          <Route path="/azitpreview" element={<AzitPreview />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
