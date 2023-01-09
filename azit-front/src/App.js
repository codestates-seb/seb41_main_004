import GlobalStyle from "./GlobalStyle";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Search from "./pages/Search";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
<<<<<<< HEAD
import MyPage from "./pages/MyPage";
=======
import ReviewCreate from "./pages/ReviewCreate"
>>>>>>> 46dbabd24fe02680c6e673a011fcde73817a91c8

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/chat" element={<Home />}></Route>
          <Route path="/search" element={<Search />}></Route>
          <Route path="/profile" element={<Home />}></Route>
          <Route path="/edit" element={<Home />}></Route>
          <Route path="/review/Create" element={<ReviewCreate />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/mypage" element={<MyPage />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
