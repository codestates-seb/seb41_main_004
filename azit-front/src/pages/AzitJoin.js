import styled from "styled-components";
import Header from "../components/Header";

const JoinWrap = styled.div`
  margin-right: 2rem;
  margin-left: 2rem;
  display: flex;
  flex-direction: column;
  min-height: 84.4rem;
  > .JoinForm {
    margin-top: 7.5rem;
    > div {
      > div {
        padding-left: 1.7rem;
        padding-top: 0.85rem;
        height: 4rem;
        background-color: var(--background-color);
        border-radius: 0.5rem;
        margin-bottom: 2rem;
      }
    }
  }
  > .submitButton {
    width: 100%;
    height: 5.5rem;
    margin-top: 55.2rem;
    margin-bottom: 2rem;
    border: none;
    border-radius: 0.5rem;
    background-color: var(--border-color);
    color: var(--light-font-color);
    font-size: var(--big-font);
  }
`;

const AzitJoin = () => {
  return (
    <JoinWrap>
      <Header title="참여 신청" />
      <div className="JoinForm">
        <div>
          <label>호스트의 질문</label>
          <div>생성시 작성한 참가 질문</div>
        </div>
        <div>
          <label>참여 답변</label>
          <input placeholder="답변을 입력해주세요."></input>
        </div>
      </div>
      <button className="submitButton">신청 하기</button>
    </JoinWrap>
  );
};

export default AzitJoin;
