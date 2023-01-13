import styled from "styled-components";
import Button from "../components/common/Button";
import Logo from "../images/logo.png";
import google from "../images/googleLogo.png";
// eslint-disable-next-line no-unused-vars
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useForm } from 'react-hook-form';


const Login = () => {

  const { register, handleSubmit } = useForm();

  const loginButtonClick = async (data) => {
  const { email, password } = data;
  console.log(data);
  if (!email || !password) {
    alert('아이디와 비밀번호를 입력해주세요!');
    return;
  }
  }

  return (
    <LoginContainer>
      <LoginFormWrap onSubmit={handleSubmit(data => loginButtonClick(data))}>
        <LogoImage alt="LogoImg" src={Logo} />
        <InputWrap>
          <label htmlFor="email">이메일</label>
          <input 
            id="email" 
            type='text' 
            placeholder="이메일 입력" 
            {...register("email")}
          />
        </InputWrap>
        <InputWrap>
          <label htmlFor="password">비밀번호</label>
          <input 
            id="password" 
            type='password' 
            placeholder="비밀번호 입력"
            {...register("password")}
          />
        </InputWrap>
        {/* <Link to="/"> */}
          <Button type="submit" title="로그인" state="active"></Button>
        {/* </Link> */}
      </LoginFormWrap>
      <LoginFooter>
        <Link to="/findpw">비밀번호를 잊으셨나요?</Link>
        <Link to="/signup">회원가입하기</Link>
      </LoginFooter>
      <Line />
      <Link to="/" className="snsWrap">
        <SnsLoginButton>Sign in with Google</SnsLoginButton>
        {/* <SnsLoginButton src={Kakao}>카카오 로그인</SnsLoginButton> */}
      </Link>
    </LoginContainer>
  );
};


const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0rem 2rem 0rem 2rem;
  height: 100vh;
  > .snsWrap {
    width: 100%;
  }
`;

const LoginFormWrap = styled.form`
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
    ::placeholder {
      color: var(--light-font-color);
    }
  }
  & > .errorMessage {
    font-size: var(--caption-font);
    color: var(--point-color);
  }
`;

const LogoImage = styled.img`
  margin: 12rem auto 5rem;
  height: 7rem;
`;
const LoginFooter = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin-top: 1rem;
  & > a {
    cursor: pointer;
    color: var(--sub-font-color);
  }
`;

const SnsLoginButton = styled.button`
  width: 100%;
  height: 5.5rem;
  border: none;
  border-radius: 5px;
  /* 카카오 버튼 디자인 */
  /* background-color: #fee500;
  color: #181600; */
  border:1px solid var(--border-color);
  background-color: #fff;
  color: #181600;
  font-size: var(--big-font);
  background-image: url(${google});
  background-repeat: no-repeat;
  background-position: 2.5rem center;
  background-size: 2.5rem;
  cursor: pointer;
  transition: 0.3s all;
  :hover {
    opacity: 0.7;
  }
`;

const Line = styled.hr`
  width: 100%;
  margin: 2rem 0rem 2rem 0rem;
  border: solid 0.05rem var(--border-color);
`;

export default Login;
