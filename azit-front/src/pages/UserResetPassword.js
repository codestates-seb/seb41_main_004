import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useRef } from "react";
import { useForm } from 'react-hook-form'; 
import { useNavigate } from "react-router-dom";



const UserResetPassword = () => {

  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,16}$/i;

  const { register, watch, handleSubmit, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const password = useRef();
  password.current = watch('password');

  const navigate = useNavigate();

  const ResetButtonClick = async(data) => {
    // eslint-disable-next-line
    const { nickname, email, password, passwordCheck } = data;  
    console.log(data);
    navigate('/');
  }
  return (
    <>
      <Header title="비밀번호 재설정"></Header>
      <ResetForm>
        <ResetInputContainer>
          <ResetInputWrap>
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
          </ResetInputWrap>
          <ResetInputWrap>
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
          </ResetInputWrap>
        </ResetInputContainer>
        <Button 
        onClick={ResetButtonClick} 
        type="submit" 
        title="비밀번호 재설정" 
        state={ !isValid ? "disabled" : "active"}></Button>
      </ResetForm>
    </>
  )
};

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

export default UserResetPassword;