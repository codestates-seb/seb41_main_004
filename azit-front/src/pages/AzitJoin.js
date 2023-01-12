import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";

const JoinWrap = styled.div`
  position: relative;
  padding: 7.5rem 2rem 2rem;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  > .JoinForm {
    flex: 1;
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
      <Button state="disabled" title="신청 하기" />
    </JoinWrap>
  );
};

export default AzitJoin;
