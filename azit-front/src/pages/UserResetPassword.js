import styled from "styled-components";
import Header from "../components/Header";
import Button from "../components/Button";
import { Link } from "react-router-dom";

const ResetForm = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
`

const ResetInputContainer = styled.div`
  display: flex;
  flex-direction: column;
`

const ResetInputWrap = styled.div`
  margin-bottom: 2rem;
  & > label {
    color: var(--sub-font-color);
  }
  & > input {
    ::placeholder{
      color: var(--light-font-color);
    }
  }
  & > .errorMessage {
    font-size: var(--caption-font);
    color: var(--point-color);
  }
`

const UserResetPassword = () => {
  return (
    <>
      <Header title="비밀번호 재설정"></Header>
      <ResetForm>
        <ResetInputContainer>
          <ResetInputWrap>
            <label>사용할 비밀번호를 입력해 주세요.</label>
            <input placeholder="특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요."></input>
            <div className="errorMessage">특수문자, 문자, 숫자를 포함 8~16자로 작성해 주세요.</div>
          </ResetInputWrap>
          <ResetInputWrap>
            <label>비밀번호를 확인해 주세요.</label>
            <input placeholder="비밀번호를 확인해 주세요."></input>
            <div className="errorMessage">비밀번호와 맞지 않습니다.</div>
          </ResetInputWrap>
        </ResetInputContainer>
        <Link to="/">
          <Button title="비밀번호 재설정" state="active"></Button>
        </Link>
      </ResetForm>
    </>
  )
}

export default UserResetPassword;