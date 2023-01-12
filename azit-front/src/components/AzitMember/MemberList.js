import { Link } from "react-router-dom";
import styled from "styled-components";
import ExportIcon from "../../images/exportIcon.png";

const ListWrap = styled.li`
  margin-top: 1rem;
  > .userWrap {
    display: flex;
    align-items: center;
    margin-bottom: 1rem;
    > .avatarWrap {
      width: 5rem;
      height: 5rem;
      overflow: hidden;
      border-radius: 50%;
      margin-right: 1rem;
      > img {
        max-width: 100%;
      }
    }
    > .etcWrap {
      flex: 1;
      display: flex;
      > .profileWrap {
        display: flex;
        flex-direction: column;
        flex: 1;
        > .name {
          font-size: var(--main-font);
        }
        > .introduce {
          font-size: var(--caption-font);
          word-break: keep-all;
          overflow: hidden;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
      }
      > .btnWrap {
        display: flex;
        flex-direction: column;
        width: 5rem;
        margin-left: 1rem;
        > button {
          border: none;
          background-color: transparent;
          margin: 0;
          padding: 0;
          cursor: pointer;
          &.watingBtn {
            width: 100%;
            height: 2rem;
            border-radius: 5px;
            background-color: var(--border-color);
            font-size: var(--caption-font);
            margin-top: 0.5rem;
            &.accept {
              color: var(--white-color);
              background-color: var(--point-color);
            }
          }
          &.export {
            > img {
              max-width: 100%;
            }
          }
        }
      }
    }
  }
  > .answer {
    padding: 0.8rem 1.5rem;
    background-color: var(--background-color);
    border-radius: 5px;
  }
`;

const MemberList = ({ data, state }) => {
  return (
    <ListWrap>
      <div className="userWrap">
        <Link to="/userpage" className="avatarWrap">
          <img alt={data.userName} src={data.userAvatar} />
        </Link>
        <div className="etcWrap">
          <div className="profileWrap">
            <h3 className="name">{data.userName}</h3>
            <p className="introduce">{data.userIntroduce}</p>
          </div>
          <div className="btnWrap">
            {state === "wating" ? (
              <>
                <button className="watingBtn accept">수락</button>
                <button className="watingBtn reject">거절</button>
              </>
            ) : (
              <button className="export">
                <img alt="exportIcon" src={ExportIcon} />
              </button>
            )}
          </div>
        </div>
      </div>
      <div className="answer">{data.ClubAnswer}</div>
    </ListWrap>
  );
};

export default MemberList;
