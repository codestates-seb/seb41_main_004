import styled from "styled-components";

const LogoutRequestModalWrap = styled.div`
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.25);
  }
  > .modal {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    min-width: 35rem;
    > .buttonWrap {
      margin: 3rem 0;
      display: flex;
      justify-content: center;
      gap: 1rem;
      > button {
        cursor: pointer;
        border: none;
        border-radius: 0.5rem;
        background-color: var(--border-color);
        color: var(--white-color);
        width: 15rem;
        height: 4.2rem;
      }
      > .accept {
          cursor: pointer;
          border: none;
          border-radius: 0.5rem;
          background-color: var(--point-color);
          color: var(--white-color);
          width: 15rem;
          height: 4.2rem;
          :hover {
            background-color: var(--hover-color);
          }
      }
    }
    > input {
      margin-top: 3.5rem;
      padding-left: 4.2rem;
      ::placeholder {
        color: var(--light-font-color);
      }
    }
    > img {
      position: absolute;
      width: 2.4rem;
      left: 9%;
      top: 11%;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 3rem;
      > .title {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .caption {
        color: var(--light-font-color);
      }
    }
  }
`;

export const LogoutRequestModal = ({ modalHandler, LogoutButtonClick }) => {
  return (
    <>
      <LogoutRequestModalWrap>
        <div className="modal">
          <div className="textWrap">
            <span className="title">로그아웃 하시겠습니까?</span>
          </div>
          <div className="buttonWrap">
            <button onClick={() => modalHandler()}>취소</button>
            <button onClick={()=> LogoutButtonClick()} className="accept">확인</button>
          </div>
        </div>
        <div className="background" onClick={() => modalHandler()} />
      </LogoutRequestModalWrap>
    </>
  );
};