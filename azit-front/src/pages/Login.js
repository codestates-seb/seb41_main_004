import styled from "styled-components";
import { Button } from "../components/LoginComponent/Button";
import Logo from "../images/logo.png";
import Kakao from "../images/kakao-login.png"
import Google from "../images/google-login.png"
import { Link , useNavigate } from "react-router-dom";

const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0rem 2rem 0rem 2rem;
  height: 100vh;
`

const LoginFormWrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  > a {
    width: 100%;
  }
`;

const InputWrap = styled.div`
  margin: 0rem 0rem 2rem 0rem;
  width: 100%;
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

const LogoImage = styled.img`
  margin: 12rem 11.4rem 5rem;
`
const LoginFooter = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin-top: 1rem;
  & > a {
    cursor: pointer;
    color: var(--sub-font-color);
  }
`

const SnsLoginButton = styled.img`
  width: 100%; 
  margin-bottom: 1rem;
`

const Line = styled.hr`
  width: 100%;
  margin: 2rem 0rem 2rem 0rem;
  border: solid 0.05rem var(--border-color);
`

const Login = () => {

  const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  const passwordRegExp = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[0-9a-zA-Z@$!%*#?&]{8,}$/;

  return (
    <LoginContainer>
      <LoginFormWrap>
        <LogoImage alt="LogoImg" src={Logo}/>
        <InputWrap>
          <label htmlFor='email'>이메일</label>
          <input id='email' placeholder="이메일 입력"/>
          <div className="errorMessage">올바른 이메일을 입력해주세요.</div>
        </InputWrap>
        <InputWrap>
          <label htmlFor='password'>비밀번호</label>
          <input id='password' placeholder="비밀번호 입력"/>
          <div className="errorMessage">특수문자, 문자, 숫자 포함 8~16자로 작성해 주세요.</div>
        </InputWrap>
        <Link to="/">
          <Button type="submit" disabled="">로그인</Button>
        </Link>
      </LoginFormWrap>
      <LoginFooter>
        <Link to="/find">비밀번호를 잊으셨나요?</Link>
        <Link to="/signup">회원가입하기</Link>
      </LoginFooter>
      <Line />
      <Link to="/">
        <SnsLoginButton src={Kakao}></SnsLoginButton>
      </Link>
        <SnsLoginButton src={Google}></SnsLoginButton>
    </LoginContainer>
  );
};

export default Login;
