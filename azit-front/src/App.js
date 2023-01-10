import GlobalStyle from "./GlobalStyle";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Search from "./pages/Search";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import ReviewCreate from "./pages/ReviewCreate";
import Azitcreate from "./pages/Azitcreate";

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
          <Route path="/review/Create" element={<ReviewCreate />}></Route>
          <Route path="/edit" element={<Azitcreate />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
