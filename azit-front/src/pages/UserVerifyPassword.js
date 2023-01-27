import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import useAxios from "../util/useAxios";

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
  const axiosInstance = useAxios();

  const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()])[0-9a-zA-Z~!@#$%^&*()]{8,16}$/i;

  const { register, handleSubmit, formState: {errors, isValid} } = useForm({mode: "onChange"});
  const navigate = useNavigate();

  const accessToken = localStorage.getItem("accessToken");
  const memberId = localStorage.getItem("memberId");

  const verifyButtonClick = async(data) => {
    try {
      const res = await axiosInstance.post(
        `/api/auth/${memberId}/passwords/matchers`,
        data,
        {
          headers: { Authorization: accessToken }
        }
      );
      if (res.status === 200) {
        navigate("/userpage/resetpw")
      }
    } catch (e) {
      console.log(e)
      if (e.response.status === 500) {
        alert("요청하신 작업을 수행하지 못했습니다. 일시적인 현상이니 잠시 후 다시 시작해주세요.");
      } else {
        alert("사용자 정보가 일치하지 않습니다.")
      }
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