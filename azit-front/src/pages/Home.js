import styled from "styled-components";
import Gnb from "../components/common/Gnb";
import Tab from "../components/Home/Tab";
import Logo from "../images/logo.png";
import MainImg from "../images/mainSlideImg01.png";
import { useEffect, useState } from "react";
import { LoginRequestModal } from "../components/common/Modal";
import { useSelector } from "react-redux";

const Header = styled.header`
  width: 100%;
  height: 5.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  > h1 {
    width: 7rem;
    height: 3rem;
    background-image: url(${Logo});
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    text-indent: -9999px;
  }
`;
const SlideCell = styled.div`
  width: 100%;
  > img {
    max-width: 100%;
  }
`;
const Home = () => {
  const isLogin = useSelector((state) => state.loginStatus.isLogin);
  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
  };

  useEffect(() => {
    let isLocation = JSON.parse(window.localStorage.getItem("isLocation"))

    if (!isLogin && isLocation) {
      modalHandler();
      window.localStorage.setItem("isLocation", false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <Gnb />
      <Header>
        <h1>azit logo</h1>
      </Header>
      <SlideCell>
        <img alt="mainImage" src={MainImg} />
      </SlideCell>
      <Tab />
      {modalOpen && <LoginRequestModal modalHandler={modalHandler} />}
    </>
  );
};

export default Home;
