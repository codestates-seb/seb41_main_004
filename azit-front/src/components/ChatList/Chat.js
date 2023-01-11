import styled from "styled-components";
import Header from "../Header";
import SendIconActive from "../../images/chatSendBtnActive.png";
import SendIcon from "../../images/chatSendBtn.png";
import TestProfileImg from "../../images/testProfileImg.png";
const SendBtnWrap = styled.div`
  position: absolute;
  width: 100%;
  left: 0;
  bottom: 0;
  height: 8.5rem;
  background-color: var(--white-color);
  padding: 2rem;
  > .sendInput {
    border-radius: 4.5rem;
    padding: 1.5rem 4.5rem 1.5rem 2rem;
  }
`;
const SendBtn = styled.button`
  position: absolute;
  top: 2rem;
  right: 2rem;
  width: 4.5rem;
  height: 4.5rem;
  border: none;
  margin: 0;
  padding: 0;
  background-color: transparent;
  background-image: url(${(props) =>
    props.btnActive ? SendIconActive : SendIcon});
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
`;
const ChatCell = styled.div`
  height: 100vh;
  padding: 5.5rem 2rem 8.5rem;
  position: relative;
  > .chatWrap {
    height: 100%;
    width: 100%;
    padding: 2rem 0;
    overflow-x: scroll;
    display: flex;
    flex-direction: column-reverse;
    > div:last-child {
      margin: 0;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      > .text {
        border: 1px solid var(--border-color);
        padding: 1.5rem 2rem;
        border-radius: 30px;
        margin-bottom: 10px;
      }
      > .text:last-child {
        margin: 0;
      }
    }
    > .myChat {
      margin-bottom: 1rem;
      > .textWrap {
        align-items: flex-end;
        > .text {
          border: none;
          background-color: var(--background-color);
        }
      }
    }
    > .otherChat {
      margin-bottom: 1rem;
      display: flex;
      align-items: flex-end;
      > .avatarWrap {
        margin-right: 1rem;
        width: 2.4rem;
        height: 2.4rem;
        border-radius: 50%;
        overflow: hidden;
        > img {
          width: 100%;
        }
      }
    }
  }
`;
const Chat = () => {
  // 데이터를 받아와 해당데이터에 맞게 넣어주는 작업 필요
  // 데이터의 순서 : 최신 데이터가 배열의 가장 앞으로 와서 그대로 나열해야 함
  // [[최신대화, 과거대화], [과거대화중 최신대화, 과거대화], [과거대화]]
  return (
    <>
      <Header title="아지트 or 닉네임" />
      <ChatCell>
        <div className="chatWrap">
          {/* 본인채팅 */}
          <div className="myChat">
            <ul className="textWrap">
              <li className="text">안녕하세요.</li>
              <li className="text">
                안녕하세요.최대글자 테스트 최대글자 테스트 최대글자 테스트
                최대글자 테스트 안녕하세요.최대글자 테스트 최대글자 테스트
                최대글자 테스트 최대글자 테스트 안녕하세요.최대글자 테스트
                최대글자 테스트 최대글자 테스트 최대글자 테스트
                안녕하세요.최대글자 테스트 최대글자 테스트 최대글자 테스트
                최대글자 테스트 안녕하세요.최대글자 테스트 최대글자 테스트
                최대글자 테스트 최대글자 테스트
              </li>
            </ul>
          </div>
          {/* 상대방 채팅 */}
          <div className="otherChat">
            <div className="avatarWrap">
              <img alt="이름" src={TestProfileImg} />
            </div>
            <ul className="textWrap">
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
            </ul>
          </div>
          {/* 본인채팅 */}
          <div className="myChat">
            <ul className="textWrap">
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
              <li className="text">안녕하세요.</li>
            </ul>
          </div>
        </div>
        <SendBtnWrap>
          <input className="sendInput" placeholder="메시지 입력..."></input>
          <SendBtn />
        </SendBtnWrap>
      </ChatCell>
    </>
  );
};

export default Chat;
