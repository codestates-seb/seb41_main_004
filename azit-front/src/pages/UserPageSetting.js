import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import Header from "../components/common/Header";
import AzitSettingBarArrow from "../images/AzitSettingBarArrow.png";
import { LogoutRequestModal } from "../components/Logout/LogoutRequestModal";
import { useState } from "react";
import { removeCookie } from "../util/cookie/cookie";


const UserPageSetting = () => {

  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
  modalOpen ? setModalOpen(false) : setModalOpen(true);
  }
  const navigate = useNavigate();

  const LogoutButtonClick = () => {
    removeCookie('accessToken');
    localStorage.clear();
    navigate('/login');
  }

  return (
    <AzitSettingWrap>
      <Header title="설정" />
      <SettingBar>
        <Link to="/userpage/resetpw">
          <span className="resetPw">비밀번호 재설정</span>
          <div>
            <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
          </div>
        </Link>
      </SettingBar>
      <SettingBar>
        <div onClick={modalHandler}>
          <span className="logout">로그아웃</span>
          <div>
            <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
          </div>
        </div>
      </SettingBar>
      {modalOpen && <LogoutRequestModal LogoutButtonClick={LogoutButtonClick} modalHandler={modalHandler} />}
      <DeleteBtnWrap>
        <button>회원탈퇴</button>
      </DeleteBtnWrap>
    </AzitSettingWrap>
  );
};

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
  div {
    display: flex;
    border-bottom: 1px solid var(--border-color);
    height: 6rem;
    justify-content: space-between;
    padding: 0 2rem;
    align-items: center;
    cursor: pointer;
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

export default UserPageSetting;
