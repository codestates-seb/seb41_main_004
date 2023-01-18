import styled from "styled-components";
import AzitDetailHeader from "../components/AzitDetail/AzitDetailHeader";
import ExampleImg from "../images/AzitExampleImg.png";
import testProfileImg from "../images/testProfileImg.png";
import Button from "../components/common/Button";
import { ProfileList } from "../dummyData/ProfileList";
import { Link } from "react-router-dom";
import HostIcon from "../images/AzitDetailHost.png";

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
      height: 2rem;
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
      height: 12rem;
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
      justify-content: space-between;
      > div {
        flex-direction: column;
        > label {
          margin-bottom: 0.2rem;
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
        height: 10rem;
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

const TestImg = styled.img.attrs({ src: `${testProfileImg}` })`
  width: 5rem;
  border-radius: 70%;
`;

const UserImgWrap = styled.div`
  width: 3.8rem;
  height: 3.8rem;
  margin: 0 auto;
  overflow: hidden;
  border-radius: 50%;
  background-image: url(${(props) => props.userUrl});
  background-size: cover;
`;

const AzitDetail = () => {
  return (
    <AzitDetailWrap>
      <AzitDetailHeader />
      <img alt="exampleImg" src={ExampleImg}></img>
      <AzitDetailForm>
        <div className="azitTitle">큰 제목</div>
        <div className="desc">
          <span>카테고리</span>
          <p>nn/nn명</p>
        </div>
        <div className="azitInfo">
          <div className="hostInfo">
            <label>아지트 정보</label>
            <div>
              <Link to="/userpage">
                <TestImg />
                <img alt="HostIcon" src={HostIcon} className="hostIcon" />
              </Link>
              <div>
                <label>호스트</label>
                <span>여덟자의닉네임</span>
              </div>
            </div>
          </div>
          <div className="azitDetailInfo">
            <div>
              <label>참가방식</label>
              <span>온라인</span>
            </div>
            <div>
              <label>날짜</label>
              <span>0000-00-00 00:00</span>
            </div>
          </div>
          <div className="azitDescription">
            <label>아지트 설명</label>
            <div>가나다라마바사</div>
          </div>
        </div>
        <div className="memberList">
          <h3>참여 멤버</h3>
          <ul className="selectWrap">
            {ProfileList.map((profile, idx) => (
              <li key={idx}>
                <Link to="/userpage">
                  <UserImgWrap userUrl={profile.userUrl} />
                  <p>유저 네임</p>
                </Link>
              </li>
            ))}
          </ul>
        </div>
        <div className="detailInfo">
          <h3>상세 안내</h3>
          <ul>
            <li>참가비 : 10000원</li>
            <li>나이,성별 제한 : 1997년 이상, 남자</li>
          </ul>
        </div>
        <Button state="active" title="아지트 가입하기" />
        {/* <Button state="disabled" title= "이미 종료된 아지트입니다" /> */}
      </AzitDetailForm>
    </AzitDetailWrap>
  );
};

export default AzitDetail;
