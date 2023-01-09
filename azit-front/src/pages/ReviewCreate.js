import styled from "styled-components";
import Header from "../components/Header";
import TestAvatarUrl from "../images/testProfileImg.png";

const CreateWrap = styled.section`
  padding: 7.5rem 2rem 2rem;
  h3 {
    font-size: var(--big-font);
    margin-bottom: 1rem;
  }
  > .selectCell {
    margin-bottom: 2rem;
    > .selectWrap {
      overflow-x: scroll;
      padding-bottom: 1.5rem;
      display: flex;
      flex-direction: row;
      > li {
        margin-right: 1rem;
        text-align: center;
        > .imgWrap {
          width: 3.8rem;
          height: 3.8rem;
          margin: 0 auto;
          overflow: hidden;
          border-radius: 50%;
          img {
            width: 100%;
          }
        }
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
  label {
    margin-bottom:10px;
  }
`;
const ReviewCreate = () => {
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
    <>
      <Header title="리뷰 작성하기" />
      <CreateWrap>
        <div className="selectCell">
          <h3>참여 인원</h3>
          <ul className="selectWrap">
            {profileList.map((profile) => (
              <li>
                <div className="imgWrap">
                  <img alt={profile.userName} src={profile.userUrl} />
                </div>
                <p>유저닉네임</p>
              </li>
            ))}
          </ul>
        </div>
        <article className="createCell">
            <h3>리뷰 작성</h3>
            <div className="createWrap">
                <label htmlFor="password">테스트</label>
                <input id="password"></input>
            </div>
        </article>
      </CreateWrap>
    </>
  );
};

export default ReviewCreate;
