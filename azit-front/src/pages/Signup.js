import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { Link } from "react-router-dom";

const SignupForm = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
`

const SignupInputContainer = styled.div`
  display: flex;
  flex-direction: column;
`

const SignupInputWrap = styled.div`
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

const Signup = () => {
  return (
    <>
    <Header title="회원가입"/>
    <SignupForm>
      <SignupInputContainer>
        <SignupInputWrap>
          <label>사용할 닉네임을 입력해 주세요.</label>
          <input placeholder="영어,한글,-,_을 포함한 2~8글자로 작성해 주세요."></input>
          <div className="errorMessage">영어,한글,-,_을 포함한 2~8글자로 작성해 주세요.</div>
        </SignupInputWrap>
        <SignupInputWrap>
          <label>사용할 이메일을 입력해 주세요.</label>
          <input placeholder="test@gmail.com"></input>
          <div className="errorMessage">이메일 형식이 아닙니다.</div>
        </SignupInputWrap>
        <SignupInputWrap>
          <label>사용할 비밀번호를 입력해 주세요.</label>
          <input placeholder="특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요."></input>
          <div className="errorMessage">특수문자, 문자, 숫자를 포함 8~16자로 작성해 주세요.</div>
        </SignupInputWrap>
        <SignupInputWrap>
          <label>비밀번호를 확인해 주세요.</label>
          <input placeholder="비밀번호를 확인해 주세요."></input>
          <div className="errorMessage">비밀번호와 맞지 않습니다.</div>
        </SignupInputWrap>
      </SignupInputContainer>
      <Link to="/signup/add">
      <Button title="추가 정보 입력" state="active"></Button>
      </Link>
    </SignupForm>
    </>
  ) 
};

export default Signup;
