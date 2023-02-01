import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import styled from "styled-components";

const Container = styled.li`
  width: 100%;
  height: 7rem;
  padding: 0 2rem;
  background-color: #ffffff;
  display: flex;
  align-items: center;
  > a:first-child {
    margin-right: 1rem;
  }
`;
const UserImg = styled.div`
  display: block;
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
  background-color: var(--background-color);
  width: 5rem;
  height: 5rem;
  border-radius: 50%;
`;
const FollowBox = styled.div`
  flex: 1;
  > h3 {
    > a {
      font-size: var(--main-font);
    }
    > button {
      border: none;
      background-color: transparent;
      width: auto;
      height: auto;
      padding: 0;
      margin: 0 0 0 0.5rem;
      color: var(--point-color);
      cursor: pointer;
    }
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
    cursor: pointer;
  }
  button.disabled {
    color: var(--font-color);
    background-color: var(--border-color);
    pointer-events: none;
  }
`;

const DataList = ({
  follow,
  follower,
  unfollowMutate,
  followMutate,
  deleteMutate,
  refetch
}) => {
  const { id } = useParams();
  const myId = window.localStorage.getItem("memberId");
  // 마이페이지 여부
  const [myPage, setMyPage] = useState(false);
  // follow일 떈 true, follower일 땐 false
  const [isFollow, setIsFollow] = useState(true);
  const followerData = follower?.follower;
  const followId = follow?.memberId;
  const followerId = follower?.followId;

  useEffect(() => {
    if (follower) {
      setIsFollow(false);
    }
  }, [follow, follower]);
  useEffect(() => {
    if (id === myId) {
      setMyPage(true);
    }
  }, [id, myId]);

  return (
    <>
      <Container>
        <Link to={`/userpage/${isFollow ? followId : followerData.memberId}`}>
          <UserImg
            className="imgBox"
            imgSrc={
              isFollow && (follow || follower)
                ? `${process.env.REACT_APP_S3_URL}${follow?.fileInfo.fileUrl}/${follow?.fileInfo.fileName}`
                : `${process.env.REACT_APP_S3_URL}${followerData?.fileInfo.fileUrl}/${followerData?.fileInfo.fileName}`
            }
          ></UserImg>
        </Link>
        <FollowBox>
          <h3>
            <Link
              to={`/userpage/${isFollow ? followId : followerData.memberId}`}
            >
              {isFollow ? follow?.nickname : followerData?.nickname}
            </Link>
            {!isFollow && myPage && (
              <button onClick={() => deleteMutate(followerId)}>삭제</button>
            )}
          </h3>
          <p>{isFollow ? follow?.aboutMe : followerData?.aboutMe}</p>
        </FollowBox>
        {myPage && (
          <ButtonBox>
            {/* 상황에 따라 맞는 버튼이 오도록 하기 필요 */}
            {isFollow ? (
              <button onClick={() => {unfollowMutate(followId);}}>해제</button>
            ) : (
              <>
                {/* <button className="disabled">팔로잉</button> */}
                <button
                  className={follower.matpal ? "disabled" : "active"}
                  onClick={() => {
                    followMutate(follower?.follower.memberId);
                  }}
                >
                  {follower.matpal ? "팔로잉" : "팔로우"}
                </button>
              </>
            )}
          </ButtonBox>
        )}
      </Container>
    </>
  );
};

export default DataList;
