import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useRef } from "react";
import { useForm } from 'react-hook-form'; 
import { useNavigate } from "react-router-dom";
import axiosInstance from "../util/axios";

const ResetForm = styled.form`
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

  const newPasswordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()])[0-9a-zA-Z~!@#$%^&*()]{8,16}$/i;
  // eslint-disable-next-line
  const { register, watch, handleSubmit, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const newPassword = useRef();
  newPassword.current = watch('newPassword');

  const navigate = useNavigate();

  const memberId = localStorage.getItem("memberId");

  const ResetButtonClick = async(data) => {
    try {
      await axiosInstance.patch(
        `/api/auth/${memberId}/passwords`,
        data,
      );
      navigate("/login", { replace: true });
    } catch (e) {
      console.log(e);
    }
  }
  return (
    <>
      <Header title="비밀번호 재설정"></Header>
      <ResetForm onSubmit={handleSubmit(data => ResetButtonClick(data))}>
        <ResetInputContainer>
          <ResetInputWrap>
            <label htmlFor="newPassword">사용할 비밀번호를 입력해 주세요.</label>
            <input 
              type='password' 
              id='newPassword' 
              autoComplete="off"
              placeholder="특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요."
              {...register('newPassword', {
                required: true,
                pattern: newPasswordRegExp
              })}></input>
              {errors.newPassword?.type === 'required' && <div className="errorMessage">비밀번호를 입력해주세요.</div>}
              {errors.newPassword?.type === 'pattern' && <div className="errorMessage">특수문자, 문자, 숫자를 포함한 8~16자로 작성해 주세요.</div>}
          </ResetInputWrap>
          <ResetInputWrap>
            <label htmlFor="newPasswordCheck">비밀번호를 확인해 주세요.</label>
            <input 
              type='password' 
              id='newPasswordCheck' 
              autoComplete="off"
              placeholder="비밀번호를 확인해 주세요."
              {...register('newPasswordCheck', {
                required: true,
                validate: (value) => value === newPassword.current
              })}></input>
            {errors.newPasswordCheck?.type === 'required' && <div className="errorMessage">비밀번호를 확인해 주세요.</div>}
            {errors.newPasswordCheck?.type === 'validate' && <div className="errorMessage">비밀번호가 일치하지 않습니다.</div>}
          </ResetInputWrap>
        </ResetInputContainer>
        <Button 
        onClick={() => ResetButtonClick()} 
        type="submit" 
        title="비밀번호 재설정" 
        state={ !isValid ? "disabled" : "active"}></Button>
      </ResetForm>
    </>
  )
};


export default UserResetPassword;