import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useRef } from "react";
import axios from "axios";



const Signup = () => {

  const nickNameRegExp = /^[a-zA-Z가-힣_-]{2,8}$/i
  const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,16}$/i;

  const { register, watch, handleSubmit, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const password = useRef();
  password.current = watch('password');

  const navigate = useNavigate();

  const SignupButtonClick = async(data) => {
    // eslint-disable-next-line
    const { nickName, email, password, passwordCheck } = data;  
    // 서버로 data POST
    console.log(data);
    navigate('/signup/add');
  }

  return (
    <>
    <Header title="회원가입"/>
    <SignupForm onSubmit={handleSubmit(data => SignupButtonClick(data))}>
      <SignupInputContainer>
        <SignupInputWrap>
          <label htmlFor="nickName">사용할 닉네임을 입력해 주세요.</label>
          <input 
            type='text' 
            id='nickName' 
            autoComplete="off"
            placeholder="영어,한글,-,_을 포함한 2~8글자로 작성해 주세요."
            {...register('nickName', {
              required: true,
              pattern: nickNameRegExp
            })}></input>
            {errors.nickName?.type === 'required' && <div className="errorMessage">닉네임을 입력해주세요.</div>}
            {errors.nickName?.type === 'pattern' && <div className="errorMessage">영어,한글,-,_을 포함한 2~8글자로 작성해 주세요.</div>}
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
            })}></input>
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

export default Signup;
