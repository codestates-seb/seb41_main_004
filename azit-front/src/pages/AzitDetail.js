import styled from "styled-components";
import AzitDetailHeader from "../components/AzitDetailHeader";
import ExampleImg from "../images/AzitExampleImg.png";
import testProfileImg from "../images/testProfileImg.png";
import TestAvatarUrl from "../images/testProfileImg.png";
import Button from "../components/Button";

const AzitDetailWrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;

  > img {
    width: 100%;
    margin-top: 5.5rem;
  }
`;

const AzitDetailForm = styled.div`
  margin: 2rem;
  > div {
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
    > div {
      margin-right: 1rem;
      padding: 1.2rem;
      width: 100%;
      height: 12rem;
      display: flex;
      flex-direction: column;
      border: 1px solid var(--border-color);
      border-radius: 1rem;
      > div {
        display: flex;
        flex-direction: row;
        > label {
          font-size: var(--caption-font);
        }
        > div {
          > label {
            font-size: var(--small-font);
          }
          > span {
            font-size: var(--caption-font);
          }
        }
      }
      > label {
        font-size: var(--caption-font);
        color: var(--sub-font-color);
      }
    }
    > div:nth-child(2) {
      margin-right: 0;
      > div {
        display: flex;
        flex-direction: column;
        height: 100%;
        margin-bottom: 1rem;
        > label {
          margin-bottom: 0.2rem;
        }
      }
      > div:nth-child(2) {
        margin-bottom: 0;
      }
    }
  }
  > .azitTitle {
    font-size: var(--title-font);
    font-weight: var(--bold-weight);
    width: 100%;
    height: 5.8rem;
    word-break: keep-all;
  }
  > .azitDescription {
    padding: 1rem;
    border: 1px solid var(--border-color);
    border-radius: 1rem;
    display: flex;
    flex-direction: column;
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
        > p {
          width: 50px;
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
  margin-right: 2rem;
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
  const profileList = [
    {
      userName: "유저1",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저2",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저3",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저4",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저5",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저6",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저7",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저8",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저9",
      userUrl: TestAvatarUrl,
    },
    {
      userName: "유저10",
      userUrl: TestAvatarUrl,
    },
  ];
  return (
    <AzitDetailWrap>
      <AzitDetailHeader />
      <img alt="exampleImg" src={ExampleImg}></img>
      <AzitDetailForm>
        <div className="azitTitle">큰 제목</div>
        <div>
          <span>카테고리</span>
          <p>nn/nn명</p>
        </div>
        <div>
          <div>
            <label>아지트 정보</label>
            <div>
              <TestImg />
              <div>
                <label>호스트</label>
                <span>여덟자의닉네임</span>
              </div>
            </div>
          </div>
          <div>
            <div>
              <label>참가방식</label>
              <span>온라인</span>
            </div>
            <div>
              <label>날짜</label>
              <span>0000-00-00 00:00</span>
            </div>
          </div>
        </div>
        <div className="azitDescription">
          <label>아지트 설명</label>
          <div>가나다라마바사</div>
        </div>
        <div className="memberList">
          <h3>참여 멤버</h3>
          <ul className="selectWrap">
            {profileList.map((profile, idx) => (
              <li key={idx}>
                <UserImgWrap userUrl={profile.userUrl} />
                <p>유저네임</p>
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
