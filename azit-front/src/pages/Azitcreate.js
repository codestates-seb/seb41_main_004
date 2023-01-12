import styled from "styled-components";
import AzitCreateForm from "../components/AzitCreate/AzitCreateForm";
import Header from "../components/common/Header";
import BannerImg from "../images/BannerImg.png";
import { ImgModal } from "../components/common/Modal";
import { useState } from "react";

const AzitaddWrap = styled.div`
  display: flex;
  flex-direction: column;
  > img {
    margin-top: 5.5rem;
  }
`;

const Azitcreate = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
  };

  return (
    <AzitaddWrap>
      <Header title="아지트 생성" />
      <img
        onClick={() => modalHandler()}
        alt="banner-img"
        src={BannerImg}
      ></img>
      {modalOpen && <ImgModal modalHandler={modalHandler} />}
      <AzitCreateForm />
    </AzitaddWrap>
  );
};

export default Azitcreate;
