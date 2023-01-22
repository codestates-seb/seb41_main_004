import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../util/axios";
import { useState } from "react";



const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
  & > .withdrawlFormWrap {
    & > .title {
      text-decoration: underline;
      font-size: var(--big-font);
      color: var(--sub-font-color);
      margin-bottom: 1rem;
    }
    & > .warningMsg {
      color: var(--point-color);
      margin-bottom: 1rem;
    }
    & > .agreedCheckWrap {
      display: flex;
      align-items: center;
      & > #agreedCheck {
        margin: 0 0.5rem 0 0;
        width: 1.5rem;
        height: 1.5rem;
      }
      & > label {
        margin: 0;
      }
    }
  }
`;

const UserWithdrawl = () => {

  const navigate = useNavigate();

  const accessToken = localStorage.getItem("accessToken");
  const memberId = localStorage.getItem("memberId");

  const [isAgreed, setAgree] = useState(false);

  const agreedCheckClick = () => {
    setAgree(!isAgreed);
  }

  const withdrawlButtonClick = async(e) => {
    e.preventDefault();
    try {
      // eslint-disable-next-line
      const res = await axiosInstance.delete(
        `/api/members/${memberId}`,
        {
          headers: { Authorization: accessToken }
        }
      )
      alert('회원 탈퇴가 완료되었습니다. azit를 이용해 주셔서 감사합니다.');
      navigate('/login', { replace: true });
    } catch (e) {
      alert(e.message);
    }
  }

  return (
    <>
      <Header title="회원탈퇴"></Header>
      <FormContainer onSubmit={withdrawlButtonClick}>
        <div className="withdrawlFormWrap">
          <div className="title">azit 탈퇴 전 확인하세요</div>
          <div className="warningMsg">
            회원 탈퇴 시 개인 정보 및 azit에 저장된 모든 데이터가 삭제됩니다.<br/>
            탈퇴 처리 후 삭제된 데이터는 복구가 불가능하며 현재 계정과 동일한 이메일로 재가입할 수 없습니다.
          </div>
          <div className="agreedCheckWrap">
            <input
              id="agreedCheck" 
              type="checkbox"
              onClick={agreedCheckClick}
            />
            <label htmlFor="agreedCheck">안내 사항을 모두 확인하였으며, 이에 동의 합니다.</label>
          </div>
        </div>
        <Button
        type="submit" 
        title="회원 탈퇴" 
        state={isAgreed ? "active" : "disabled"}></Button>
      </FormContainer>
    </>
  )
}

export default UserWithdrawl;