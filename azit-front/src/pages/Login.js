import styled from "styled-components";
import Button from "../components/common/Button";
import Logo from "../images/logo.png";
// import google from "../images/googleLogo.png";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from 'react-hook-form';
import { loginStatusSlice } from "../redux/loginSlice";
import { setCookie } from "../util/cookie/cookie";
import { useDispatch } from "react-redux";
// import { googleLogin } from "../util/oauthGoogle";
import axios from "axios";
import axiosInstance from "../util/axios";

const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0rem 2rem 0rem 2rem;
  height: 100vh;
  > a {
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

// const SnsLoginButton = styled.button`
//   width: 100%;
//   height: 5.5rem;
//   border: none;
//   border-radius: 5px;
//   /* 카카오 버튼 디자인 */
//   /* background-color: #fee500;
//   color: #181600; */
//   border:1px solid var(--border-color);
//   background-color: #fff;
//   color: #181600;
//   font-size: var(--big-font);
//   background-image: url(${google});
//   background-repeat: no-repeat;
//   background-position: 2.5rem center;
//   background-size: 2.5rem;
//   cursor: pointer;
//   transition: 0.3s all;
//   :hover {
//     opacity: 0.7;
//   }
// `;

const Line = styled.hr`
  width: 100%;
  margin: 2rem 0rem 2rem 0rem;
  border: solid 0.05rem var(--border-color);
`;


const Login = () => {

  const dispatch = useDispatch();
  const navigate = useNavigate();
  // useForm의 isSubmitting으로 로그인 로딩 구현
  const { register, handleSubmit, formState: { errors } } = useForm({mode: "onchange"});

  // const oAuthHandler = () => {
  //   window.location.assign('http://ec2-13-209-243-35.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google');
  // }

  const loginButtonClick = async (data) => {
    // eslint-disable-next-line
    const { email, password } = data;
      try {
        const res = await axiosInstance.post(
          `api/auth/login`,
          data
        );
        const accessToken = res.headers.get('Authorization');
        const refreshToken = res.headers.get('Refresh');
        const nickname = res.data.nickname;
        const email = res.data.email;
        const profileUrl = res.data.profileUrl;
        const memberId = res.data.memberId;
        const profileName = res.data.profileImageName;
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('email', email);
        localStorage.setItem('nickname', nickname);
        localStorage.setItem('profileUrl', profileUrl);
        localStorage.setItem('memberId', memberId);
        localStorage.setItem('profileName', profileName);
        setCookie('refreshToken', refreshToken);
        axios.defaults.headers.common['Authorization'] = `${accessToken}`
        dispatch(loginStatusSlice.actions.login());
        navigate('/');
      } catch (e) {
        if (e.response.status === 401) {
          alert("유효하지 않은 유저 정보입니다.");
        } else if(e.response.status === 500) {
          alert("요청하신 작업을 수행하지 못했습니다. 일시적인 현상이니 잠시 후 다시 시작해주세요.")
        }
        // console.log(e);
      }
    };

  return (
    <LoginContainer>
      <LoginFormWrap onSubmit={handleSubmit(data => loginButtonClick(data))}>
        <LogoImage alt="LogoImg" src={Logo} />
        <InputWrap>
          <label htmlFor="email">이메일</label>
          <input 
            id="email" 
            type='text' 
            autoComplete="off"
            placeholder="이메일 입력" 
            {...register("email", {
              required: true,
            })}
          />
          {errors.email?.type === 'required' && <div className="errorMessage">이메일을 입력해주세요.</div>}
        </InputWrap>
        <InputWrap>
          <label htmlFor="password">비밀번호</label>
          <input 
            id="password" 
            type='password' 
            autoComplete="off"
            placeholder="비밀번호 입력"
            {...register("password", {
              required: true,
            })}
          />
          {errors.password?.type === 'required' && <div className="errorMessage">비밀번호를 입력해주세요.</div>}
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
      {/* <a href={googleLogin}>
        <SnsLoginButton>Sign in with Google</SnsLoginButton>
      </a> */}
    </LoginContainer>
  );
};

export default Login;
