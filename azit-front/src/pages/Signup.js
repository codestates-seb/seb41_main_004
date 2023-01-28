import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useRef } from "react";
import { axiosInstance } from "../util/axios";
import { useState } from "react";


const Signup = () => {

  const nicknameRegExp = /^[a-zA-Z가-힣_-]{2,8}$/i
  const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()])[0-9a-zA-Z~!@#$%^&*()]{8,16}$/i;

  const { register, watch, handleSubmit, getValues, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const password = useRef();
  password.current = watch('password');

  const navigate = useNavigate();

  const SignupButtonClick = async(data) => {
    // eslint-disable-next-line
    // const { nickname, email, password, passwordCheck } = data;  
    // console.log(data);
    navigate('/signup/add', {state:data, replace: true});
  }

  const [nicknameChecked, setNicknameChecked] = useState(false);
  const [emailChecked, setEmailChecked] = useState(false);

  const nickname = getValues("nickname");
  const email = getValues("email");

  const nicknameCheckBtnClick = async () => {
    const data = { "nickname" : nickname }
    try {
      const res = await axiosInstance.get(
        `api/members/nickname`,
        data
      )
      if(res.status === 200 && nicknameRegExp.test(nickname)) {
        setNicknameChecked(true);
        alert("사용 가능한 닉네임입니다.");
      }
    } catch(e) {
      alert(e);
    }
  }

  const emailCheckBtnClick = async () => {
    const data = { "email" : email }
    try {
      const res = await axiosInstance.get(
        `api/members/email`,
        data
      )
      if(res.status === 200) {
        setEmailChecked(true);
        alert("사용 가능한 이메일입니다.");
      }
    } catch(e) {
      alert(e);
    }
  }

  return (
    <>
    <Header title="회원가입"/>
    <SignupForm onSubmit={handleSubmit(data => SignupButtonClick(data))}>
      <SignupInputContainer>
        <SignupInputWrap>
          <label htmlFor="nickname">사용할 닉네임을 입력해 주세요.</label>
          <input 
            type='text' 
            id='nickname' 
            autoComplete="off"
            placeholder="영어,한글,-,_을 포함한 2~8글자로 작성해 주세요."
            {...register('nickname', {
              required: true,
              pattern: nicknameRegExp
            })}></input>
            <button onClick={nicknameCheckBtnClick} className={nicknameRegExp.test(nickname) ? "nicknameCheckBtn" : "disabled"}>중복확인</button>
            <div className={nicknameChecked ? "hidden" : "errorMessage"}>닉네임 중복검사가 필요합니다.</div>
            {errors.nickname?.type === 'required' && <div className="errorMessage">닉네임을 입력해주세요.</div>}
            {errors.nickname?.type === 'pattern' && <div className="errorMessage">영어,한글,-,_을 포함한 2~8글자로 작성해 주세요.</div>}
        </SignupInputWrap>
        <SignupInputWrap>
          <label htmlFor="email">사용할 이메일을 입력해 주세요.</label>
          <input 
            type='text' 
            id='email'
            autoComplete="off"
            placeholder="test@gmail.com"
            {...register('email', {
              required: true,
              pattern: emailRegExp
            })}></input>
            <button onClick={emailCheckBtnClick} className={emailRegExp.test(email) ? "emailCheckBtn" : "disabled"}>중복확인</button>
            <div className={emailChecked ? "hidden" : "errorMessage"}>이메일 중복검사가 필요합니다.</div>
            {errors.email?.type === 'required' && <div className="errorMessage">이메일을 입력해주세요.</div>}
            {errors.email?.type === 'pattern' && <div className="errorMessage">이메일 형식이 아닙니다.</div>}
        </SignupInputWrap>
        <SignupInputWrap>
          <label htmlFor="password">사용할 비밀번호를 입력해 주세요.</label>
          <input 
            type='password' 
            id='password' 
            autoComplete="off"
            placeholder="특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요."
            {...register('password', {
              required: true,
              pattern: passwordRegExp
            })}></input>
            {errors.password?.type === 'required' && <div className="errorMessage">비밀번호를 입력해주세요.</div>}
            {errors.password?.type === 'pattern' && <div className="errorMessage">특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요.</div>}
        </SignupInputWrap>
        <SignupInputWrap>
          <label htmlFor="passwordCheck">비밀번호를 확인해 주세요.</label>
          <input 
            type='password' 
            id='passwordCheck' 
            autoComplete="off"
            placeholder="비밀번호를 확인해 주세요."
            {...register('passwordCheck', {
              required: true,
              validate: (value) => value === password.current
            })}
            />
          {errors.passwordCheck?.type === 'required' && <div className="errorMessage">비밀번호를 확인해 주세요.</div>}
          {errors.passwordCheck?.type === 'validate' && <div className="errorMessage">비밀번호가 일치하지 않습니다.</div>}
        </SignupInputWrap>
      </SignupInputContainer>
      <Button 
        onClick={SignupButtonClick} 
        type="submit" 
        title="추가 정보 입력" 
        state={ !isValid ? "disabled" : "active"}></Button>
    </SignupForm>
    </>
  ) 
};

const SignupForm = styled.form`
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
  position: relative;
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
  & > .hidden {
    display: none;
  }
  & > .nicknameCheckBtn {
    all: unset;
    color: var(--point-color);
    position: absolute;
    top: 3.7rem;
    right: 1.5rem;
    cursor: pointer;
  }
  & > .emailCheckBtn {
    all: unset;
    color: var(--point-color);
    position: absolute;
    top: 3.7rem;
    right: 1.5rem;
    cursor: pointer;
  }
  & > .disabled {
    all: unset;
    color: var(--sub-font-color);
    position: absolute;
    top: 3.7rem;
    right: 1.5rem;
    pointer-events: none;
  } 
`

export default Signup;
