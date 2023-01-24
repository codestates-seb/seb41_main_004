import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import UserListIcon from "../../images/userListIcon.png";
import { toDateFormatOfMonthDay } from "../../util/toDateFormatOfKR";

const ListWrap = styled.article`
  margin-bottom: 1rem;
`;
const DetailWrap = styled.div`
  position: relative;
  padding: 1rem;
  border-radius: 5px;
  background-color: var(--white-color);
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
  /* display: none; */
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
    props.status === "참여중"
      ? "var(--point-color)"
      : props.status === "신청중"
      ? "var(--green-color)"
      : "var(--light-font-color)"};
  color: var(--white-color);
  border-radius: 0 5px 0 10px;
  > span {
    font-size: var(--caption-font);
  }
`;
const AzitList = ({ data, myPage }) => {
  // console.log(data); //console.log 지우지 말아주세요.
  const [meetDate, setMeetDate] = useState("00월 00일 00:00");
  const [clubMember, setClubMember] = useState([]);

  // 상태가 대기중이 아닌사람 filter하는 로직
  useEffect(() => {
    let filterMember = data.clubMembers.filter((member) => {
      return member.clubMemberStatus === "CLUB_JOINED";
    });
    setClubMember(filterMember);
  }, [data]);
  //console.log(clubMember);
  //이런식으로 들어옴[{clubMemberId: 1, clubMemberStatus: 'CLUB_JOINED', joinAnswer: '1번 아지트 저도 참가할래요!', member: {…}}]

  useEffect(() => {
    setMeetDate(toDateFormatOfMonthDay(data.meetingDate, data.meetingTime));
  }, [data.meetingDate, data.meetingTime]);
  //console.log(meetDate); //01월20일 23:59

  const repeatAvatar = (data) => {
    let result = [];
    if (data.length >= 4) {
      for (let i = 0; i < 4; i++) {
        result.push(
          <div key={data[i].clubMemberId} className="imgWrap">
            <img
              alt={data[i].member.nickname}
              src={`${process.env.REACT_APP_S3_URL}${data[i].member.fileInfo.fileUrl}/${data[i].member.fileInfo.fileName}`}
            />
          </div>
        );
      }
    } else {
      for (let i = 0; i < data.length; i++) {
        result.push(
          <div key={data[i].clubMemberId} className="imgWrap">
            <img
              alt={data[i].member.nickname}
              src={`${process.env.REACT_APP_S3_URL}${data[i].member.fileInfo.fileUrl}/${data[i].member.fileInfo.fileName}`}
            />
          </div>
        );
      }
    }

    return <>{result}</>;
  };
  // console.log(`${process.env.REACT_APP_S3_URL}${data.bannerImage.fileUrl}/${data.bannerImage.fileName}`);

  return (
    <ListWrap>
      <DetailWrap>
        <Link to={`/azit/detail/${data.clubId}`}>
          {/* 마이페이지의 활동내역일 경우에만 보이게 수정 필요 display none 상태*/}
          {myPage ? (
            <Status status={"신청중"}>
              <span>참여중</span>
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
              {/* 카테고리 및 숨겨짐 들어갈 곳 tagDisplay에 none을 props로 넣을 시 사라짐 */}
              <Tag className="category">{data.categorySmall.categoryName}</Tag>
              <Tag tagDisplay="none">숨겨짐</Tag>
            </div>
            <h2 className="clubName">{data.clubName}</h2>
            <div className="placeTime">
              <span className="place">
                {data.isOnline === "online" ? "온라인" : data.location}∙
              </span>
              <span className="time">{meetDate}</span>
            </div>
            <div className="etcCell">
              <div className="profileAvatar">
                <div className="imgWrap">
                  <img
                    alt={data.host.nickname}
                    src={`${process.env.REACT_APP_S3_URL}${data.host.fileInfo.fileUrl}/${data.host.fileInfo.fileName}`}
                  />
                </div>
                {clubMember ? repeatAvatar(clubMember) : null}
              </div>
              <div className="limitCell">
                <img src={UserListIcon} alt="limitIcon" />
                <div className="limitWrap">
                  <span className="current">{clubMember.length + 1} </span>/
                  <span className="limit"> {data.memberLimit}</span>명
                </div>
              </div>
              <span className="clubHost">{data.host.nickname}</span>
            </div>
          </div>
        </Link>
      </DetailWrap>
      {/* 마이페이지 일 때만 보이게 할 필요 있음 현재 display none 상태 */}
      <EtcWrap>
        <div className="ActivityView">
          <button type="button">
            {myPage ? "활동내역 보이기" : true ? null : "활동내역 숨기기"}
          </button>
        </div>
        {/* 리뷰를 쓰지 않은 모임만 보이게 해야함 */}
        <div className="ActivityReview">
          <Link to="/review/Create">
            {myPage ? "리뷰 작성하러 가기 〉" : null}
          </Link>
        </div>
      </EtcWrap>
    </ListWrap>
  );
};

export default AzitList;
