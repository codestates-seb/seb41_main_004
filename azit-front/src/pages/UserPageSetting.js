import styled from "styled-components";
import Header from "../components/Header";
import AzitSettingBarArrow from "../images/AzitSettingBarArrow.png";

const AzitSettingWrap = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const SettingBar = styled.div`
  width: 100%;
  max-width: 50rem;
  height: 6rem;
  margin-top: 5.5rem;
  background-color: var(--white-color);
  display: flex;
  border-bottom: 1px solid var(--border-color);

  > span {
    width: 88%;
    margin-top: 1.8rem;
    margin-left: 2rem;
    font-size: var(--big-font);
    font-weight: var(--bold-weight);
    color: var(--sub-font-color);
  }
  > div {
    margin-left: calc(100%-23.5rem);
    padding-top: 2rem;
    img {
      width: 2rem;
      height: 2rem;
    }
  }
`;

const MemberSettingBar = styled.div`
  width: 100%;
  max-width: 50rem;
  height: 6rem;
  background-color: var(--white-color);
  display: flex;
  border-bottom: 1px solid var(--border-color);
  > span {
    width: 88%;
    margin-top: 1.8rem;
    margin-left: 2rem;
    font-size: var(--big-font);
    font-weight: var(--bold-weight);
    color: var(--sub-font-color);
  }
  > div {
    padding-top: 2rem;
    img {
      width: 2rem;
      height: 2rem;
    }
  }
`;

const DeleteAzitButton = styled.div`
  margin-top: 2rem;
  text-align: center;
  color: var(--point-color);
  font-size: var(--big-font);
`;

const UserPageSetting = () => {
  return (
    <AzitSettingWrap>
      <Header title="설정" />
      <SettingBar>
        <span>비밀번호 재설정</span>
        <div>
          <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
        </div>
      </SettingBar>
      <MemberSettingBar>
        <span>로그아웃</span>
        <div>
          <img alt="azitRevisionBarArrow" src={AzitSettingBarArrow} />
        </div>
      </MemberSettingBar>
      <DeleteAzitButton>회원탈퇴</DeleteAzitButton>
    </AzitSettingWrap>
  );
};

export default UserPageSetting;
