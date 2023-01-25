import styled from "styled-components";
import AzitDetailHeader from "../components/AzitDetail/AzitDetailHeader";
import { Link, useNavigate } from "react-router-dom";
import HostIcon from "../images/AzitDetailHost.png";
import { useParams } from "react-router-dom";
import { axiosInstance } from "../util/axios";
import { useQuery } from "react-query";
import {
  PriceFormat,
  genderConvert,
  isOnlineConvert,
  MaxAgeConvert,
  MinAgeConvert,
  timeConvert,
} from "../util/azitPreviewDateConvert";
import Loading from "../components/common/Loading";
import { useState, useEffect } from "react";

const AzitDetailWrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;

  > img {
    height: 20rem;
    width: 100%;
    margin-top: 5.5rem;
  }
`;

const AzitDetailForm = styled.div`
  > .active {
    width: 100%;
    height: 55px;
    font-size: var(--big-font);
    border-radius: 5px;
    border: none;
    margin: 0;
    padding: 0;
    cursor: pointer;
    transition: 0.5s all;
    background-color: var(--point-color);
    color: var(--white-color);
    :hover {
      background-color: var(--hover-color);
    }
  }
  > .disabled {
    width: 100%;
    height: 55px;
    font-size: var(--big-font);
    border-radius: 5px;
    border: none;
    margin: 0;
    padding: 0;
    cursor: pointer;
    transition: 0.5s all;
    background-color: var(--border-color);
    color: var(--light-font-color);
    pointer-events: none;
  }
  padding: 2rem;
  min-height: calc(100vh - 25.5rem);
  display: flex;
  flex-direction: column;
  > button {
    margin-top: auto;
  }
  > .desc {
    margin-bottom: 2rem;
    display: flex;
    flex-direction: row;
    > span {
      text-align: center;
      padding: 0.2rem 1rem;
      min-width: 6rem;
      line-height: 2rem;
      background-color: var(--point-color);
      color: var(--white-color);
      border-radius: 5rem;
      font-size: var(--caption-font);
      margin-right: 1rem;
    }
    > p {
      color: var(--sub-font-color);
    }
  }
  > .azitInfo {
    margin-bottom: 2rem;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
    > div {
      padding: 1.2rem;
      min-height: 12rem;
      display: flex;
      flex-direction: column;
      border: 1px solid var(--border-color);
      border-radius: 1rem;
      flex-basis: calc(50% - 0.5rem);
      > div {
        display: flex;
        flex-direction: row;
        > label {
          font-size: var(--caption-font);
        }
      }
    }
    > .hostInfo {
      height: 14rem;
      > label {
        font-size: var(--caption-font);
        color: var(--sub-font-color);
      }
      > div {
        > a {
          position: relative;
          margin-right: 1rem;
          > img:first-child {
            width: 5rem;
            border-radius: 70%;
          }
          > .hostIcon {
            top: 0;
            right: 0;
            width: 1.8rem;
            height: 1.8rem;
            position: absolute;
          }
        }
        > div {
          flex: 1;
          label {
            font-size: var(--small-font);
          }
          span {
            font-size: var(--caption-font);
          }
        }
      }
    }
    > .azitDetailInfo {
      height: 14rem;
      justify-content: space-between;
      > div {
        flex-direction: column;
        > label {
          margin-bottom: 0.2rem;
        }
        > span {
          font-size: var(--caption-font);
        }
      }
    }
    > .azitDescription {
      flex-basis: 100%;
      margin-top: 1rem;
      > label {
        font-size: var(--caption-font);
        color: var(--sub-font-color);
      }
      > div {
        padding: 0;
        border: none;
      }
    }
  }
  > .azitTitle {
    font-size: var(--title-font);
    font-weight: var(--bold-weight);
    width: 100%;
    min-height: 5.8rem;
    margin-bottom: 1rem;
    word-break: keep-all;
  }

  > .memberList {
    display: flex;
    flex-direction: column;
    margin-bottom: 2rem;
    > h3 {
      font-size: var(--main-font);
      font-weight: var(--regular-weight);
      margin-bottom: 1rem;
    }
    > ul {
      > li {
        font-size: var(--caption-font);
      }
    }
    > .selectWrap {
      overflow-x: scroll;
      padding-bottom: 1.5rem;
      display: flex;
      flex-direction: row;
      > li {
        margin-right: 1rem;
        text-align: center;
        p {
          min-width: 5rem;
        }
      }
      > li:last-child {
        margin: 0;
      }
    }
    .selectWrap::-webkit-scrollbar {
      height: 4px;
      background-color: var(--background-color);
      border-radius: 4px;
    }
    .selectWrap::-webkit-scrollbar-thumb {
      background-color: var(--point-color);
      border-radius: 4px;
    }
  }
  > .detailInfo {
    display: flex;
    flex-direction: column;
    margin-top: 2rem;
    > h3 {
      font-size: var(--main-font);
      font-weight: var(--regular-weight);
      margin-bottom: 1rem;
    }
    > ul {
      > li {
        margin-left: 2rem;
        font-size: var(--caption-font);
        margin-bottom: 1rem;
        list-style: disc;
      }
    }
  }
`;

const UserImgWrap = styled.div`
  width: 3.8rem;
  height: 3.8rem;
  margin: 0 auto;
  overflow: hidden;
  border-radius: 50%;
  background-image: url(${(props) => props.userUrl});
  background-size: cover;
  background-color: var(--background-color);
`;

const ImgWrap = styled.div`
  width: 100%;
  height: 20rem;
  margin-top: 5.5rem;
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
`;

const EtcWrap = styled.div`
  width: 100%;
  height: 100vh;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const AzitDetail = () => {
  const { id } = useParams();
  const [clubMember, setClubMember] = useState([]);
  const [waitingMembers, setWaitingMembers] = useState([]);
  const memberId = Number(localStorage.getItem("memberId"));
  const navigate = useNavigate();

  const [btnStatus, setBtnStatus] = useState("join");

  const azitLookup = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}`);
    return res.data.data;
  };

  const { isError, isLoading, data, error } = useQuery(
    "azitDetail",
    azitLookup
  );

  useEffect(() => {
    if (data) {
      setClubMember(
        data.clubMembers.filter(
          (member) => member.clubMemberStatus === "CLUB_JOINED"
        )
      );
      setWaitingMembers(
        data.clubMembers.filter(
          (member) => member.clubMemberStatus === "CLUB_WAITING"
        )
      );
    }
  }, [data]);

  useEffect(() => {
    if (data) {
      let joinId = clubMember.map((member) => member.member.memberId);
      let waitingId = waitingMembers.map((member) => member.member.memberId);

      if (data.clubStatus !== "CLUB_ACTIVE") {
        setBtnStatus("close");
        // 호스트가 아니고, 참가 신청을 하지 않고, 참가하지 않은 사람
      } else if (data.memberLimit === joinId.length + 1) {
        setBtnStatus("full");
      } else if (
        memberId !== data.host.memberId &&
        !waitingId.includes(memberId) &&
        !joinId.includes(memberId)
      ) {
        setBtnStatus("join");
      } else if (memberId === data.host.memberId) {
        setBtnStatus("edit");
        // 호스트가 아니고, 신청 유저에 들어가있고, 참여 유저가 아닐 경우
      } else if (
        memberId !== data.host.memberId &&
        waitingId.includes(memberId) &&
        !joinId.includes(memberId)
      ) {
        setBtnStatus("wating");
        // 호스트가 아니고, 신청 유저에 없고, 참여 유저에 들어와 있을 경우
      } else if (
        memberId !== data.host.memberId &&
        !waitingId.includes(memberId) &&
        joinId.includes(memberId)
      ) {
        setBtnStatus("joined");
      }
    }
  }, [clubMember, waitingMembers]);

  const AzitCancel = async () => {
    try {
      const res = await axiosInstance.delete(
        `/api/clubs/${id}/signups/${memberId}`,
        {
          headers: { Authorization: localStorage.getItem("accessToken") },
        }
      );
      console.log(res);
      alert("가입을 취소하였습니다.");
      navigate(`/`);
    } catch (e) {
      console.log("가입 취소 실패");
    }
  };

  return (
    <AzitDetailWrap>
      <AzitDetailHeader clubData={data} />
      {isError && <EtcWrap>{error.message}</EtcWrap>}
      {isLoading && (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      )}
      {data && (
        <>
          <ImgWrap
            alt="exampleImg"
            imgSrc={
              data.bannerImage &&
              `${process.env.REACT_APP_S3_URL}${data.bannerImage.fileUrl}/${data.bannerImage.fileName}`
            }
          ></ImgWrap>
          <AzitDetailForm>
            <div className="azitTitle">{data.clubName}</div>
            <div className="desc">
              <span>{data.categorySmall.categoryName}</span>
              <p>
                {clubMember.length + 1}/{data.memberLimit}명
              </p>
            </div>
            <div className="azitInfo">
              <div className="hostInfo">
                <label>아지트 정보</label>
                <div>
                  <Link to={`/userpage/${data.host.memberId}`}>
                    <img
                      alt="hostImg"
                      src={`${process.env.REACT_APP_S3_URL}${data.host.fileInfo.fileUrl}/${data.host.fileInfo.fileName}`}
                    />
                    <img alt="HostIcon" src={HostIcon} className="hostIcon" />
                  </Link>
                  <div>
                    <label>호스트</label>
                    <span>{data.host.nickname}</span>
                  </div>
                </div>
              </div>
              <div className="azitDetailInfo">
                <div>
                  <label>참가방식</label>
                  <span>{isOnlineConvert(data.isOnline, data.location)}</span>
                </div>
                <div>
                  <label>날짜</label>
                  <span>
                    {data.meetingDate} {timeConvert(data.meetingTime)}
                  </span>
                </div>
              </div>
              <div className="azitDescription">
                <label>아지트 설명</label>
                <div>{data.clubInfo}</div>
              </div>
            </div>
            <div className="memberList">
              <h3>참여 멤버</h3>
              <ul className="selectWrap">
                <li>
                  <Link to={`/userpage/${data.host.memberId}`}>
                    <UserImgWrap
                      userUrl={`${process.env.REACT_APP_S3_URL}${data.host.fileInfo.fileUrl}/${data.host.fileInfo.fileName}`}
                    />
                    <p>{data.host.nickname}</p>
                  </Link>
                </li>
                {clubMember.map((member) => (
                  <li key={member.clubMemberId}>
                    <Link to={`/userpage/${member.member.memberId}`}>
                      <UserImgWrap
                        userUrl={`${process.env.REACT_APP_S3_URL}${member.member.fileInfo.fileUrl}/${member.member.fileInfo.fileName}`}
                      />
                      <p>{member.member.nickname}</p>
                    </Link>
                  </li>
                ))}
              </ul>
            </div>
            <div className="detailInfo">
              <h3>상세 안내</h3>
              <ul>
                <li>참가비 : {PriceFormat(String(data.fee))}원</li>
                <li>
                  나이,성별 제한 :
                  <span>{MinAgeConvert(data.birthYearMin)}</span>
                  {MaxAgeConvert(data.birthYearMin) === "나이 제한없음" ? (
                    <></>
                  ) : (
                    <span> ~ </span>
                  )}
                  <span>{MaxAgeConvert(data.birthYearMax)}, </span>
                  <span>{genderConvert(data.genderRestriction)}</span>
                </li>
              </ul>
            </div>
            <button
              className={
                btnStatus === "join" ||
                btnStatus === "edit" ||
                btnStatus === "wating" ||
                btnStatus === "joined"
                  ? "active"
                  : "disabled"
              }
              onClick={
                btnStatus === "wating" || btnStatus === "joined"
                  ? () => {
                      AzitCancel();
                    }
                  : () =>
                      navigate(
                        btnStatus === "join"
                          ? `/azit/join/${id}`
                          : btnStatus === "edit"
                          ? `/azit/edit/${id}`
                          : ""
                      )
              }
            >
              {btnStatus === "join"
                ? "아지트 가입하기"
                : btnStatus === "edit"
                ? "아지트 수정하기"
                : btnStatus === "wating"
                ? "가입 신청 취소하기"
                : btnStatus === "joined"
                ? "가입 신청 취소하기"
                : btnStatus === "close"
                ? "이미 종료된 아지트입니다."
                : "이미 마감된 아지트입니다."}
            </button>
          </AzitDetailForm>
        </>
      )}
    </AzitDetailWrap>
  );
};

export default AzitDetail;
