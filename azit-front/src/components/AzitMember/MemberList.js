import { Link } from "react-router-dom";
import styled from "styled-components";
import ExportIcon from "../../images/exportIcon.png";
import { useParams } from "react-router-dom";
import useAxios from "../../util/useAxios";

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
  const { id } = useParams();
  const axiosInstance = useAxios();
  
  const azitMemberAccept = async () => {
    try {
      const payload = { status: "CLUB_JOINED" };
      await axiosInstance.patch(
        `api/clubs/${id}/signups/${data.member.memberId}`,
        payload,
        {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        }
      );
      window.location.href = `/azit/member/${id}`;
      alert("아지트 참가 수락 완료");
    } catch (e) {
      alert("아지트 참가 수락 실패");
    }
  };

  const azitMemberDeny = async () => {
    try {
      const payload = { status: "CLUB_REJECTED" };
      await axiosInstance.patch(
        `api/clubs/${id}/signups/${data.member.memberId}`,
        payload,
        {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        }
      );
      window.location.href = `/azit/member/${id}`;
      alert("아지트 참가 거절 완료");
    } catch (e) {
      alert("아지트 참가 거절 실패");
    }
  };

  const azitMemberKicks = async () => {
    try {
      await axiosInstance.patch(
        `api/clubs/${id}/kicks/${data.member.memberId}`,
        { body: "hello" },
        {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        }
      );
      window.location.href = `/azit/member/${id}`;
      alert("아지트 강퇴 완료");
    } catch (e) {
      alert("아지트 강퇴 실패");
    }
  };

  return (
    <ListWrap key={data.clubMemberId}>
      <div className="userWrap">
        <Link to={`/userpage/${data.member.memberId}`} className="avatarWrap">
          <img
            alt={data.userName}
            src={`${process.env.REACT_APP_S3_URL}${data.member.fileInfo.fileUrl}/${data.member.fileInfo.fileName}`}
          />
        </Link>
        <div className="etcWrap">
          <div className="profileWrap">
            <h3 className="name">{data.member.nickname}</h3>
            <p className="introduce">{data.member.aboutMe}</p>
          </div>
          <div className="btnWrap">
            {state === "CLUB_WAITING" ? (
              <>
                <button className="watingBtn accept" onClick={azitMemberAccept}>
                  수락
                </button>
                <button className="watingBtn reject" onClick={azitMemberDeny}>
                  거절
                </button>
              </>
            ) : (
              <button className="export" onClick={azitMemberKicks}>
                <img alt="exportIcon" src={ExportIcon} />
              </button>
            )}
          </div>
        </div>
      </div>
      <div className="answer">{data.joinAnswer}</div>
    </ListWrap>
  );
};

export default MemberList;
