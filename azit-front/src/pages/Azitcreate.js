import styled from "styled-components";
import AzitcreateForm from "../components/AzitCreate/AzitcreateForm";
import Header from "../components/Header";
import BannerImg from "../images/BannerImg.png";

const AzitaddWrap = styled.div`
  display: flex;
  flex-direction: column;
  > img {
    margin-top: 5.5rem;
  }
`;

const Azitadd = () => {
  return (
    <AzitaddWrap>
      <Header title="아지트 생성" />
      <img alt="banner-img" src={BannerImg}></img>
      <AzitcreateForm />
    </AzitaddWrap>
  );
};

export default Azitadd;
