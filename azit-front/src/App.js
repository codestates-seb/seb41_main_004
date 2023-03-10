import GlobalStyle from "./GlobalStyle";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Search from "./pages/Search";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import ReviewCreate from "./pages/ReviewCreate";
import AzitCreate from "./pages/AzitCreate";
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
import AzitMember from "./pages/AzitMember.js";
import AzitPreview from "./pages/AzitPreview";
import UserVerifyPassword from "./pages/UserVerifyPassword";
import RequireAuth from "./components/common/RequireAuth";
import UserWithdrawl from "./pages/UserWithdrawl";
import Missing from "./components/common/Missing";
import OauthGoogle from "./pages/OauthGoogle";

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/loading" element={<Loading />}></Route>
          <Route path="/" element={<Home />}></Route>

          {/* 로그인 관련 */}
          <Route path="/login" element={<Login />}></Route>
          <Route path="/oauth" element={<OauthGoogle />}></Route>
          <Route path="/findpw" element={<FindPassword />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/signup/add" element={<SignupAdditional />}></Route>

          {/* 로그인 해야만 가능 */}
          <Route element={<RequireAuth />}>
            {/* 채팅 */}
            <Route path="/chat" element={<ChatList />}></Route>
            <Route path="/chat/detail" element={<Chat />}></Route>

            {/* 검색 */}
            <Route path="/search" element={<Search />}></Route>

            {/* 아지트 */}
            <Route path="/azit/create" element={<AzitCreate />}></Route>
            <Route path="/azit/setting/:id" element={<AzitSetting />}></Route>
            <Route path="/azit/edit/:id" element={<AzitEdit />}></Route>
            <Route path="/azit/join/:id" element={<AzitJoin />}></Route>
            <Route path="/azit/report/:id" element={<AzitReport />}></Route>
            <Route path="/azit/detail/:id" element={<AzitDetail />}></Route>
            <Route path="/azit/member/:id" element={<AzitMember />}></Route>
            <Route path="/azit/preview" element={<AzitPreview />}></Route>

            {/* 리뷰 */}
            <Route path="/review/create/:id" element={<ReviewCreate />}></Route>

            {/* 유저페이지 */}
            <Route path="/userpage/:id" element={<UserPage />}></Route>
            <Route
              path="/userpage/setting/:id"
              element={<UserPageSetting />}
            ></Route>
            <Route
              path="/userpage/verifypw"
              element={<UserVerifyPassword />}
            ></Route>
            <Route
              path="/userpage/resetpw"
              element={<UserResetPassword />}
            ></Route>
            <Route
              path="/userpage/withdrawl"
              element={<UserWithdrawl />}
            ></Route>
            <Route path="/userpage/report/:id" element={<UserReport />}></Route>
            <Route
              path="/userpage/followcheck/:id"
              element={<FollowCheck />}
            ></Route>
            <Route
              path="/userpage/edit/:id"
              element={<UserProfileEdit />}
            ></Route>
          </Route>

          {/* catch all */}
          <Route path="*" element={<Missing />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
