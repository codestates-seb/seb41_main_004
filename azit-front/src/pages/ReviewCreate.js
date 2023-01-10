import styled from "styled-components";
import Button from "../components/Button";
import Header from "../components/Header";
import CreateCell from "../components/ReviewCreate/CreateCell";
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
        {/* SelectCell */}
        <div className="selectCell">
          <h3>참여 인원</h3>
          <ul className="selectWrap">
            {profileList.map((profile, idx) => (
              <li key={idx}>
                <UserImgWrap userUrl={profile.userUrl} />
                <p>유저닉네임</p>
              </li>
            ))}
          </ul>
        </div>
        <CreateCell />
        <CreateCell />
        <Button title="리뷰 제출하기"/>
      </CreateWrap>
    </>
  );
};

export default ReviewCreate;
