import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import UserListIcon from "../../images/userListIcon.png";
import { toDateFormatOfMonthDay } from "../../util/toDateFormatOfKR";
import hostIcon from "../../images/ActivityListHostIcon.png";
const ListWrap = styled.article`
  margin-bottom: 1rem;
`;
const DetailWrap = styled.div`
  position: relative;
  padding: 1rem;
  border-radius: 5px;
  background-color: var(--white-color);
  > .hostIcon {
    position: absolute;
    left: 0;
    top: 0;
    width: 3rem;
  }
  > a {
    display: flex;
    flex-direction: row;
    > .infoCell {
      flex: 1;
      > .tagWrap {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        margin-bottom: 0.3rem;
        > span {
          font-size: var(--small-font);
          padding: 0 0.7rem;
          height: 2rem;
          align-items: center;
          justify-content: center;
          border-radius: 1.5rem;
          color: var(--white-color);
          background-color: var(--light-font-color);
        }
        > .category {
          background-color: var(--point-color);
          margin-right: 0.5rem;
        }
      }
      > .clubName {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .placeTime {
        color: var(--sub-font-color);
        font-size: var(--caption-font);
      }
      > .etcCell {
        display: flex;
        > .profileAvatar {
          display: flex;
          margin-right: 5px;
          > .imgWrap {
            width: 2rem;
            height: 2rem;
            border-radius: 50%;
            overflow: hidden;
            border: 1px solid var(--white-color);
            margin-left: -5px;
            background-color: var(--background-color);
            > img {
              max-width: 100%;
            }
          }
          > .imgWrap:first-child {
            margin: 0;
          }
        }
        > .limitCell {
          display: flex;
          align-items: center;
          > img {
            width: 2rem;
          }
          > .limitWrap {
            font-size: var(--small-font);
            color: var(--light-font-color);
          }
        }
        > .clubHost {
          margin-left: auto;
        }
      }
    }
  }
`;
const ImgWrap = styled.div`
  width: 8.5rem;
  margin-right: 10px;
  border-radius: 10px;
  background-color: var(--background-color);
  background-image: url(${(props) => props.clubImg});
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
`;
const Tag = styled.span`
  display: ${(props) => (props.tagDisplay ? props.tagDisplay : "flex")};
`;
const EtcWrap = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  button,
  a {
    cursor: pointer;
    font-size: var(--caption-font);
    color: var(--sub-font-color);
    border: none;
    background-color: transparent;
    margin: 0;
    padding: 0;
  }
`;
const Status = styled.div`
  position: absolute;
  right: 0;
  top: 0;
  display: flex;
  height: 2.2rem;
  justify-content: center;
  align-items: center;
  width: 7.2rem;
  background-color: ${(props) =>
    props.status === "?????????"
      ? "var(--point-color)"
      : props.status === "?????????"
      ? "var(--green-color)"
      : "var(--light-font-color)"};
  color: var(--white-color);
  border-radius: 0 5px 0 10px;
  > span {
    font-size: var(--caption-font);
  }
`;
const AzitList = ({ data, myPage, activityData }) => {
  const [meetDate, setMeetDate] = useState("00??? 00??? 00:00");
  const [clubMember, setClubMember] = useState([]);
  const [activeHide, setActiveHide] = useState(false);
  // eslint-disable-next-line no-unused-vars
  const [userStatus, setUserStatus] = useState("?????????");

  useEffect(() => {
    setUserStatus(
      activityData?.isClosed
        ? "?????????"
        : activityData?.clubMemberStatus === "CLUB_WAITING"
        ? "?????????"
        : "?????????"
    );
  }, [activityData]);

  const handleActiveHide = () => {
    setActiveHide(!activeHide);
  };

  // ????????? ???????????? ???????????? filter?????? ??????
  useEffect(() => {
    let filterMember = data.clubMembers
      ? data.clubMembers.filter((member) => {
          return member.clubMemberStatus === "CLUB_JOINED";
        })
      : data.participantList;
    setClubMember(filterMember);
    //console.log(filterMember) -> [{memberId: 1, nickname: 'admin', fileInfo: {???}}]
  }, [data]);

  useEffect(() => {
    setMeetDate(toDateFormatOfMonthDay(data.meetingDate, data.meetingTime));
  }, [data.meetingDate, data.meetingTime]);

  const repeatAvatar = (data) => {
    //console.log(data); // [{memberId: 2, nickname: 'user', fileInfo: {???}}]
    let result = [];
    // console.log(data);
    if (data.length >= 5) {
      for (let i = 0; i < 5; i++) {
        result.push(
          <div
            key={
              data[i]?.clubMemberId ? data[i].clubMemberId : data[i].memberId
            }
            className="imgWrap"
          >
            <img
              alt={data[i]?.member ? data[i].member.nickname : data[i].nickname}
              src={
                data[i]?.member
                  ? `${process.env.REACT_APP_S3_URL}${data[i].member.fileInfo.fileUrl}/${data[i].member.fileInfo.fileName}`
                  : `${process.env.REACT_APP_S3_URL}${data[i].fileInfo.fileUrl}/${data[i].fileInfo.fileName}`
              }
            />
          </div>
        );
      }
    } else {
      for (let i = 0; i < data.length; i++) {
        result.push(
          <div
            key={
              data[i]?.clubMemberId ? data[i].clubMemberId : data[i].memberId
            }
            className="imgWrap"
          >
            <img
              alt={data[i]?.member ? data[i].member.nickname : data[i].nickname}
              src={
                data[i]?.member
                  ? `${process.env.REACT_APP_S3_URL}${data[i].member.fileInfo.fileUrl}/${data[i].member.fileInfo.fileName}`
                  : `${process.env.REACT_APP_S3_URL}${data[i].fileInfo.fileUrl}/${data[i].fileInfo.fileName}`
              }
            />
          </div>
        );
      }
    }

    return <>{result}</>;
  };
  // console.log(activityData);
  return (
    <ListWrap>
      <DetailWrap>
        {activityData?.isHost && (
          <img className="hostIcon" src={hostIcon} alt="isHost" />
        )}
        <Link to={`/azit/detail/${data.clubId}`}>
          {/* ?????????????????? ??????????????? ???????????? ????????? ?????? ?????? display none ??????*/}
          {/* useState??? ????????? ?????? ???????????? ???????????? ?????? */}
          {myPage ? (
            <Status status={userStatus}>
              <span>{userStatus}</span>
            </Status>
          ) : (
            ""
          )}

          <ImgWrap
            clubImg={`${
              data.bannerImage
                ? `${process.env.REACT_APP_S3_URL}${data.bannerImage.fileUrl}/${data.bannerImage.fileName}`
                : null
            }`}
          />
          <div className="infoCell">
            <div className="tagWrap">
              {/* ???????????? ??? ????????? ????????? ??? tagDisplay??? none??? props??? ?????? ??? ????????? */}
              <Tag className="category">{data.categorySmall.categoryName}</Tag>
              {activeHide ? <Tag>?????????</Tag> : null}
            </div>
            <h2 className="clubName">{data.clubName}</h2>
            <div className="placeTime">
              <span className="place">
                {data.isOnline === "online" ? "?????????" : data.location}???
              </span>
              <span className="time">{meetDate}</span>
            </div>
            <div className="etcCell">
              <div className="profileAvatar">
                {/* <div className="imgWrap">
                  <img
                    alt={data.host.nickname}
                    src={`${process.env.REACT_APP_S3_URL}${data.host.fileInfo.fileUrl}/${data.host.fileInfo.fileName}`}
                  />
                </div> */}
                {clubMember ? repeatAvatar(clubMember) : null}
              </div>
              <div className="limitCell">
                <img src={UserListIcon} alt="limitIcon" />
                <div className="limitWrap">
                  <span className="current">{clubMember.length} </span>/
                  <span className="limit"> {data.memberLimit}</span>???
                </div>
              </div>
              <span className="clubHost">{data.host.nickname}</span>
            </div>
          </div>
        </Link>
      </DetailWrap>
      {/* ??????????????? ??? ?????? ????????? ??? ?????? ?????? ?????? display none ?????? */}
      {myPage && (
        <EtcWrap>
          <div className="ActivityView">
            <button type="button" onClick={handleActiveHide}>
              ???????????? {activeHide ? "?????????" : "?????????"}
            </button>
          </div>
          {/* ????????? ?????? ?????? ????????? ????????? ????????? */}
          {!activityData?.isReviewed && activityData?.isClosed && (
            <div className="ActivityReview">
              <Link to={`/review/create/${data.clubId}`}>
                ?????? ???????????? ?????? ???
              </Link>
            </div>
          )}
        </EtcWrap>
      )}
    </ListWrap>
  );
};

export default AzitList;
