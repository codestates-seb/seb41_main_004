import { useEffect, useState } from "react";
import styled from "styled-components";
import UserProfile from "../../images/testProfileImg.png";

const Container = styled.li`
  width: 100%;
  height: 7rem;
  padding: 0 2rem;
  background-color: #ffffff;
  display: flex;
  align-items: center;
  > .imgBox {
    display: flex;
    justify-content: center;
    margin-right: 1rem;
  }
`;
const UserImg = styled.img.attrs({
  src: `${UserProfile}`,
})`
  width: 5rem;
  height: 5rem;
  border-radius: 100%;
`;
const FollowBox = styled.div`
  flex: 1;
  > h3 {
    font-size: var(--main-font);
  }
  > p {
    font-size: var(--caption-font);
    color: var(--light-font-color);
  }
`;
const ButtonBox = styled.div`
  margin-left: 1rem;
  button {
    width: 5rem;
    height: 2rem;
    border-radius: 0.5rem;
    border: none;
    font-size: var(--caption-font);
    color: var(--white-color);
    background-color: var(--point-color);
  }
  button.disabled {
    color: var(--font-color);
    background-color: var(--border-color);
  }
`;

const DataList = ({ data, follower }) => {
  // follow일 떈 true, follower일 땐 false
  const [follow, setFollow] = useState(true);
  useEffect(() => {
    if (follower) {
      setFollow(false);
    }
  }, [follower]);
  console.log(follower)
  return (
    <>
      <Container>
        <div className="imgBox">
          <UserImg src={UserProfile} />
        </div>
        <FollowBox>
          <h3>{follow ? data?.host.nickname : follower?.followee.nickname}</h3>
          <p>{follow ? data?.clubInfo : follower?.followee?.aboutMe}</p>
        </FollowBox>
        <ButtonBox>
          {/* 상황에 따라 맞는 버튼이 오도록 하기 필요 */}
          {follow ? (
            <button>해제</button>
          ) : (
            <>
              <button className="disabled">팔로잉</button>
              <button>팔로우</button>
            </>
          )}
        </ButtonBox>
      </Container>
    </>
  );
};

export default DataList;
