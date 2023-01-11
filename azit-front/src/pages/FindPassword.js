import styled from "styled-components";
import Header from "../components/Header";
import Button from "../components/Button";

const FormContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
  & > div {
    & > input {
      ::placeholder {
        color: var(--light-font-color);
      }
    }
  }
`;

const FindPassword = () => {
  return (
    <>
      <Header title="비밀번호 찾기"></Header>
      <FormContainer>
        <div>
          <label>이메일</label>
          <input placeholder="가입한 계정을 입력해 주세요."></input>
        </div>
        <Button title="인증 메일 보내기" state="active"></Button>
      </FormContainer>
    </>
  )
}

export default FindPassword;