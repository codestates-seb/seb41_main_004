import { Link } from "react-router-dom";
import styled from "styled-components";
import { toDateFormatOfMonthDay } from "../../util/toDateFormatOfKR";

const ChatFlex = styled.ul`
  display: flex;
  flex-direction: column;
`;

const Null = styled.div`
  display: flex;
  padding: 10rem 2rem;
  justify-content: center;
  align-items: center;
  color: var(--light-font-color);
`;

const Chat = styled.li`
  > a {
    display: flex;
    padding: 1.5rem 2rem;
    position: relative;
    > .contentsWrap {
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow: hidden;
      > .titleWrap {
        display: flex;
        width: 100%;
        align-items: flex-end;
        > h3 {
          max-width: 60%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          font-size: var(--big-font);
          font-weight: var(--regular-weight);
          margin-right: 0.5rem;
        }
        > span {
          color: var(--light-font-color);
        }
        > span:last-child {
          font-size: var(--small-font);
          margin-left: auto;
        }
      }
      > .lastMsg {
        width: 100%;
        position: relative;
        margin-top: 0.6rem;
        font-size: var(--caption-font);
        color: var(--light-font-color);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }
`;

const ImgWrap = styled.div`
  width: 5rem;
  height: 5rem;
  border-radius: 50%;
  margin-right: 1rem;
  background-image: url(${(props) => props.chatImg});
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
`;

const LastViewBullet = styled.span`
  position: absolute;
  width: 1rem;
  height: 1rem;
  border-radius: 50%;
  background-color: var(--point-color);
  bottom: 1rem;
  right: 2rem;
  display: ${(props) => props.lastView};
`;
const ChatListWrap = ({ data }) => {
  return (
    <ChatFlex>
      {data ? (
        data.map((chat) => {
          let lastMsgDate;
          if (data) {
            lastMsgDate = toDateFormatOfMonthDay(chat.lastMsgDate);
          }
          return (
            <Chat key={chat.chatId}>
              <Link to="/chat/detail">
                <ImgWrap chatImg={chat.chatImg} />
                <div className="contentsWrap">
                  <div className="titleWrap">
                    <h3>{chat.chatName}</h3>
                    <span>{chat.chatPeople}</span>
                    <span>{lastMsgDate}</span>
                  </div>
                  <p className="lastMsg">{chat.lastMsg}</p>
                </div>
                <LastViewBullet lastView={chat.lastView ? "block" : "none"} />
              </Link>
            </Chat>
          );
        })
      ) : (
        <Null>참여중인 채팅이 없습니다.</Null>
      )}
    </ChatFlex>
  );
};

export default ChatListWrap;
