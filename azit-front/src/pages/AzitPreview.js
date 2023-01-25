import styled from "styled-components";
import Header from "../components/common/Header";
import { Link } from "react-router-dom";
import HostIcon from "../images/AzitDetailHost.png";
import { useLocation } from "react-router-dom";
import {
  PriceFormat,
  genderConvert,
  isOnlineConvert,
  categoryConvert,
  MaxAgeConvert,
  MinAgeConvert,
  timeConvert,
} from "../util/azitPreviewDateConvert";
import { useEffect, useState } from "react";
import { axiosInstance } from "../util/axios";
import { useNavigate } from "react-router-dom";

const AzitPreviewWrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const AzitPreviewForm = styled.div`
  > button {
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
      padding: 0.2rem 0;
      width: 6rem;
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
          pointer-events: none;

          > img {
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
    margin-bottom: 1rem;
    height: 5.8rem;
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
        > a {
          pointer-events: none;
        }
        p {
          width: 5rem;
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
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
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

const AzitPreview = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const bannerImg = state.bannerImg;
  const birthYearMax =
    state.birthYearMax.length > 0 ? state.birthYearMax : null;
  const birthYearMin =
    state.birthYearMin.length > 0 ? state.birthYearMin : null;
  const categorySmallId = state.categorySmallId;
  const clubInfo = state.clubInfo;
  const clubName = state.clubName;
  const fee = state.fee;
  const genderRestriction = state.genderRestriction;
  const isOnline = state.isOnline;
  const joinQuestion = state.joinQuestion;
  const location = state.location;
  const meetingDate = state.meetingDate;
  const meetingTime = state.meetingTime;
  const memberLimit = state.memberLimit;

  const accessToken = localStorage.getItem("accessToken");
  const profileUrl = localStorage.getItem("profileUrl");
  const profileName = localStorage.getItem("profileName");

  const openAzit = async () => {
    const formData = new FormData();

    formData.append("image", bannerImg);

    let data = {
      birthYearMax,
      birthYearMin,
      categorySmallId,
      clubInfo,
      clubName,
      fee,
      genderRestriction,
      isOnline,
      joinQuestion,
      location,
      meetingDate,
      meetingTime,
      memberLimit,
      joinMethod: "APPROVAL",
    };

    formData.append(
      "data",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );

    try {
      const res = await axiosInstance.post(`/api/clubs`, formData, {
        headers: { Authorization: accessToken },
        "Content-Type": "multipart/form-data",
      });

      console.log(res);
      alert("아지트가 생성되었습니다.");
      navigate(`/azit/detail/${res.data.data.clubId}`, { replace: true });
    } catch (e) {
      console.log("아지트 생성 실패");
    }
  };

  // 배너 이미지 보여주기
  const [imgFile, setImgFile] = useState(state.bannerImg);

  useEffect(() => {
    const file = state.bannerImg;
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  }, [state.bannerImg]);

  return (
    <AzitPreviewWrap>
      <Header />
      <ImgWrap alt="exampleImg" imgSrc={imgFile}></ImgWrap>
      <AzitPreviewForm>
        <div className="azitTitle">{clubName}</div>
        <div className="desc">
          <span>{categoryConvert(categorySmallId)}</span>
          <p>1/{memberLimit}명</p>
        </div>
        <div className="azitInfo">
          <div className="hostInfo">
            <label>아지트 정보</label>
            <div>
              <Link to="/userpage">
                <img
                  alt="hostProfile"
                  src={`${process.env.REACT_APP_S3_URL}${profileUrl}/${profileName}`}
                />
                <img alt="HostIcon" src={HostIcon} className="hostIcon" />
              </Link>
              <div>
                <label>호스트</label>
                <span>{localStorage.getItem("nickname")}</span>
              </div>
            </div>
          </div>
          <div className="azitDetailInfo">
            <div>
              <label>참가방식</label>
              <span>{isOnlineConvert(isOnline, location)}</span>
            </div>
            <div>
              <label>날짜 및 시간</label>
              <span>
                {meetingDate} {timeConvert(meetingTime)}
              </span>
            </div>
          </div>
          <div className="azitDescription">
            <label>아지트 설명</label>
            <div>{clubInfo}</div>
          </div>
        </div>
        <div className="memberList">
          <h3>참여 멤버</h3>
          <ul className="selectWrap">
            <li>
              <UserImgWrap
                imgSrc={`${process.env.REACT_APP_S3_URL}${profileUrl}/${profileName}`}
              />
              <p>{localStorage.getItem("nickname")}</p>
            </li>
          </ul>
        </div>
        <div className="detailInfo">
          <h3>상세 안내</h3>
          <ul>
            <li>참가비 : {PriceFormat(String(fee))}원</li>
            <li>
              나이,성별 제한 :<span>{MinAgeConvert(birthYearMin)}</span>
              {MaxAgeConvert(birthYearMin) === "나이 제한없음" ? (
                <></>
              ) : (
                <span> ~ </span>
              )}
              <span>{MaxAgeConvert(birthYearMax)}, </span>
              <span>{genderConvert(genderRestriction)}</span>
            </li>
          </ul>
        </div>
        <button state="active" onClick={openAzit}>
          아지트 열기
        </button>
      </AzitPreviewForm>
    </AzitPreviewWrap>
  );
};

export default AzitPreview;
