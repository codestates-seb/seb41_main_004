import styled from "styled-components";
import AzitDetailHeader from "../components/AzitDetail/AzitDetailHeader";
import { Link, useNavigate } from "react-router-dom";
import HostIcon from "../images/AzitDetailHost.png";
import { useParams } from "react-router-dom";
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
import axiosInstance from "../util/axios";

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

const HostImgWrap = styled.div`
  width: 5rem;
  height: 5rem;
  border-radius: 70%;
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
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
        // ???????????? ?????????, ?????? ????????? ?????? ??????, ???????????? ?????? ??????
      } else if (data.memberLimit === joinId.length) {
        setBtnStatus("full");
      } else if (
        memberId !== data.host.memberId &&
        !waitingId.includes(memberId) &&
        !joinId.includes(memberId)
      ) {
        setBtnStatus("join");
      } else if (memberId === data.host.memberId) {
        setBtnStatus("edit");
        // ???????????? ?????????, ?????? ????????? ???????????????, ?????? ????????? ?????? ??????
      } else if (
        memberId !== data.host.memberId &&
        waitingId.includes(memberId) &&
        !joinId.includes(memberId)
      ) {
        setBtnStatus("wating");
        // ???????????? ?????????, ?????? ????????? ??????, ?????? ????????? ????????? ?????? ??????
      } else if (
        memberId !== data.host.memberId &&
        !waitingId.includes(memberId) &&
        joinId.includes(memberId)
      ) {
        setBtnStatus("joined");
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [clubMember, waitingMembers]);

  const AzitCancel = async () => {
    try {
      await axiosInstance.delete(`/api/clubs/${id}/signups/${memberId}`);
      alert("????????? ?????????????????????.");
      navigate(`/`);
    } catch (e) {
      alert(e);
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
                {clubMember.length}/{data.memberLimit}???
              </p>
            </div>
            <div className="azitInfo">
              <div className="hostInfo">
                <label>????????? ??????</label>
                <div>
                  <Link to={`/userpage/${data.host.memberId}`}>
                    <HostImgWrap
                      imgSrc={`${process.env.REACT_APP_S3_URL}${data.host.fileInfo.fileUrl}/${data.host.fileInfo.fileName}`}
                    />
                    <img alt="HostIcon" src={HostIcon} className="hostIcon" />
                  </Link>
                  <div>
                    <label>?????????</label>
                    <span>{data.host.nickname}</span>
                  </div>
                </div>
              </div>
              <div className="azitDetailInfo">
                <div>
                  <label>????????????</label>
                  <span>{isOnlineConvert(data.isOnline, data.location)}</span>
                </div>
                <div>
                  <label>??????</label>
                  <span>
                    {data.meetingDate} {timeConvert(data.meetingTime)}
                  </span>
                </div>
              </div>
              <div className="azitDescription">
                <label>????????? ??????</label>
                <div>{data.clubInfo}</div>
              </div>
            </div>
            <div className="memberList">
              <h3>?????? ??????</h3>
              <ul className="selectWrap">
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
              <h3>?????? ??????</h3>
              <ul>
                <li>????????? : {PriceFormat(String(data.fee))}???</li>
                <li>
                  ??????,?????? ?????? :
                  <span>{MinAgeConvert(data.birthYearMin)}</span>
                  {MaxAgeConvert(data.birthYearMin) === "?????? ????????????" ? (
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
                ? "????????? ????????????"
                : btnStatus === "edit"
                ? "????????? ????????????"
                : btnStatus === "wating"
                ? "?????? ?????? ????????????"
                : btnStatus === "joined"
                ? "?????? ?????? ????????????"
                : btnStatus === "close"
                ? "?????? ????????? ??????????????????."
                : "?????? ????????? ??????????????????."}
            </button>
          </AzitDetailForm>
        </>
      )}
    </AzitDetailWrap>
  );
};

export default AzitDetail;
