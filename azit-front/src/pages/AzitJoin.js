import styled from "styled-components";
import Header from "../components/Header";
import Button from "../components/Button";

const JoinWrap = styled.div`
  position: relative;
  margin-right: 2rem;
  margin-left: 2rem;
  display: flex;
  flex-direction: column;
  height: 100vh;
  > .JoinForm {
    height: 100%;
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
  > .buttonWrap {
    margin-bottom: 2rem;
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
      <div className="buttonWrap">
        <Button state="disabled" title="신청 하기" />
      </div>
    </JoinWrap>
  );
};

export default AzitJoin;
