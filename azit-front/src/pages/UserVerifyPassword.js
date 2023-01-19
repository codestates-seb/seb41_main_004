import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { Navigate, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { axiosInstance } from "../util/axios";

const FormContainer = styled.form`
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
    & > .errorMessage {
    font-size: var(--caption-font);
    color: var(--point-color);
  }
  }
`;

const UserVerifyPassword = () => {

  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,16}$/i;

  const { register, handleSubmit, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const navigate = useNavigate();

  const accessToken = localStorage.getItem("accessToken");
  const memberId = localStorage.getItem("memberId");

  const verifyButtonClick = async(data) => {
    console.log(data);
    try {
      const res = await axiosInstance.post(
        `/api/auth/1/passwords/matchers?`,
        data,
        {
          headers: { Authorization: accessToken }
        }
      );
      console.log(res);
      if (res.status === 200) {
        navigate("/userpage/resetpw")
      }
    } catch (e) {
      console.log(e)
      alert("비밀번호 정보가 없습니다.")
    }
  };

  return (
    <>
      <Header title="비밀번호 인증"></Header>
      <FormContainer onSubmit={handleSubmit(data => verifyButtonClick(data))}>
        <div>
        <label htmlFor="password">현재 비밀번호를 입력해 주세요.</label>
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
        </div>
        <Button 
        onClick={() => verifyButtonClick()} 
        type="submit" 
        title="인증하기" 
        state={ !isValid ? "disabled" : "active"}></Button>
      </FormContainer>
    </>
  )
}

export default UserVerifyPassword;