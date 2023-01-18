import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";



const FindPassword = () => {
  return (
    <>
      <Header title="비밀번호 찾기"></Header>
      <FormContainer>
        <div>
          <div className="emailInputWrap">
            <div>
              <label>이메일</label>
              {/*이메일 보낸 후 비활성화되어야 함 -> disabled true*/}
              <input disabled={false} className="emailInput" placeholder="가입한 계정을 입력해 주세요."></input>
            </div>
              {/*이메일 보낸 후 버튼 글자색 바뀌어야 함 -> 클래스명에 disabled 추가*/}
              <button className={"emailSubmitButton"} type="submit">전송</button>
          </div>
          {/*이메일 전송 전에는 display none 이어야 함 -> 클래스명: verifyInputWrap*/}
          <div className={"verifyInputWrap"}>
            <input placeholder="전송 된 인증번호를 입력 해 주세요."></input>
          </div>
        </div>
        <Button type="submit" title="비밀번호 찾기" state={"disabled"}></Button>
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
        color: var(--light-color);
      }
    }
    > div {
      > input {
      ::placeholder {
      color: var(--light-font-color);
      }
      }
    }
    .verifyInputWrap {
      display: none;
    }
  }
`;

export default FindPassword;