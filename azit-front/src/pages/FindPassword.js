import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../util/axios";

const FindPassword = () => {
  const navigate = useNavigate();

  const [isClicked, setClick] = useState(false);
  const [email, setEmail] = useState('');
  const [verifyNum, setVerifyNum] = useState('');

  const storeEmail = (e) => {
    setEmail(e.target.value);
  }

  const storeVerifyNum = (e) => {
    setVerifyNum(e.target.value)
  }

  const emailSubmitClick = async (e) => {
    setClick(true);
    e.preventDefault();
    const payload = {"email":`${email}`}
    try {
      // eslint-disable-next-line
      const res = await axiosInstance.post(
        `/api/auth/refresh/passwords/email`,
        payload,
      )
      alert('이메일이 전송되었습니다. 전송된 인증 번호를 입력해주세요.');
    } catch (error) {
      if (error.response.status === 404) {
        alert('존재하지 않는 회원입니다.');
      }
      else {
        alert("요청하신 작업을 수행하지 못했습니다. 일시적인 현상이니 잠시 후 다시 시작해주세요.");
      }
    }
  };

  const verifyNumSubmitClick = async (e) => {
    e.preventDefault();
    const payload = {
      "email":`${email}`,
      "authNum":`${verifyNum}`
    }
    try {
      // eslint-disable-next-line
      const res = await axiosInstance.post(
        `/api/auth/refresh/passwords`,
        payload,
      )
      alert('이메일이 전송되었습니다. 전송된 비밀번호로 로그인 후 비밀번호를 변경해주세요.');
      navigate('/login');
    } catch (error) {
      if (error.response.status === 404) {
        alert("인증 번호가 일치하지 않습니다.")
      } else {
        alert("요청하신 작업을 수행하지 못했습니다. 일시적인 현상이니 잠시 후 다시 시작해주세요.")
      }
    }
  };

  return (
    <>
      <Header title="비밀번호 찾기"></Header>
      <FormContainer onSubmit={verifyNumSubmitClick}>
        <div>
          <div className="emailInputWrap">
            <div>
              <label>이메일</label>
              <input 
                onChange={storeEmail} 
                disabled={isClicked ? true : false} 
                className="emailInput" 
                placeholder="가입한 계정을 입력해 주세요."
                />
            </div>
              <button 
                onClick={emailSubmitClick} 
                className={isClicked ? "disabled" : "emailSubmitButton"}
              >전송</button>
          </div>
          <div className={isClicked ? "" : "verifyInputHidden"}>
            <input
              onChange={storeVerifyNum} 
              placeholder="전송 된 인증번호를 입력 해 주세요."></input>
          </div>
        </div>
        <Button
          title="비밀번호 찾기" 
          state={email && verifyNum.length > 0 ? "active" : "disabled"}></Button>
      </FormContainer>
    </>
  )
};

const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
  > div {
    > .emailInputWrap {
      position: relative;
      > div {
        .emailInput {
          margin-bottom: 1rem;
          ::placeholder {
          color: var(--light-font-color);
          }
        }
      }
      > .emailSubmitButton {
        all: unset;
        position: absolute;
        right: 2rem;
        top: 3.5rem;
        color: var(--point-color);
        cursor: pointer;
      }
      > .disabled {
        display: none;
      }
    }
    > div {
      > input {
      ::placeholder {
      color: var(--light-font-color);
      }
      }
    }
    .verifyInputHidden {
      display: none;
    }
  }
`;

export default FindPassword;