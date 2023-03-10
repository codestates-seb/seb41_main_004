import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import Header from "../components/common/Header";
import AzitSettingBarArrow from "../images/AzitSettingBarArrow.png";
import { useParams } from "react-router-dom";
import { AzitDeleteModal } from "../components/AzitSetting/AzitDeleteModal";
import { useState } from "react";
import axiosInstance from "../util/axios";

const AzitSettingWrap = styled.div`
  display: flex;
  flex-direction: column;
  padding: 5.5rem 0 0 0;
`;

const SettingBar = styled.div`
  width: 100%;
  max-width: 50rem;
  background-color: var(--white-color);
  a {
    display: flex;
    border-bottom: 1px solid var(--border-color);
    height: 6rem;
    justify-content: space-between;
    padding: 0 2rem;
    align-items: center;
    > span {
      font-size: var(--big-font);
      font-weight: var(--bold-weight);
      color: var(--sub-font-color);
    }
    > div {
      img {
        width: 2rem;
        height: 2rem;
      }
    }
  }
`;

const DeleteBtnWrap = styled.div`
  margin-top: 2rem;
  text-align: center;
  > button {
    background-color: transparent;
    border: none;
    color: var(--point-color);
    font-size: var(--big-font);
    cursor: pointer;
  }
`;

const AzitSetting = () => {
  const { id } = useParams();
  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
  };
  const navigate = useNavigate();

  const DeleteButtonClick = async () => {
    try {
      await axiosInstance.delete(`/api/clubs/${id}`,);
      alert("아지트를 닫았습니다.");
      navigate(`/`);
    } catch (e) {
      alert("아지트 삭제 실패")
    }
  };

  return (
    <AzitSettingWrap>
      <Header title="아지트 설정" />
      <SettingBar>
        <Link to={`/azit/edit/${id}`}>
          <span>아지트 수정하기</span>
          <div>
            <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
          </div>
        </Link>
      </SettingBar>
      <SettingBar>
        <Link to={`/azit/member/${id}`}>
          <span>아지트 멤버관리</span>
          <div>
            <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
          </div>
        </Link>
      </SettingBar>
      {modalOpen && (
        <AzitDeleteModal
          DeleteButtonClick={DeleteButtonClick}
          modalHandler={modalHandler}
        />
      )}
      <DeleteBtnWrap>
        <button onClick={modalHandler}>모임삭제</button>
      </DeleteBtnWrap>
    </AzitSettingWrap>
  );
};

export default AzitSetting;
