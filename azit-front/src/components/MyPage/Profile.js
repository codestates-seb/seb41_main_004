import { Link } from "react-router-dom";
import { useQuery } from "react-query";
import styled from "styled-components";
import Category from "./Category";
import Tab from "./Tab";
import Loading from "../common/Loading";
import { useMutation } from "react-query";
import useAxios from "../../util/useAxios";

const ProfileWrapper = styled.div`
  margin: 2rem 0 0;
  position: relative;
`;

const ImgWrapper = styled.div`
  display: flex;
  justify-content: center;
  padding-top: 2rem;
  position: relative;
`;

const Img = styled.div`
  width: 120px;
  height: 120px;
  border-radius: 100%;
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
  background-color: var(--background-color);
`;
const ButtonWrapper = styled.span`
  display: flex;
  justify-content: center;
  /* position: relative; */
`;
const Button = styled.button`
  border-radius: 0.5rem;
  border: none;
  /* position: absolute; */
  margin-top: -1rem;
  font-size: var(--caption-font);
  min-width: 5.5rem;
  height: 2rem;
  cursor: pointer;
  box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.3);
  &.active {
    background-color: #bb2649;
    color: white;
  }
  &.disabled {
    background-color: var(--background-color);
    color: var(--sub-font-color);
  }
`;
const InfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;
const Name = styled.p`
  font-weight: bold;
  font-size: var(--big-font);
  padding-top: 0.5rem;
`;
const Text = styled.p`
  margin: 1rem 0;
  max-width: 25rem;
  word-break: keep-all;
  text-align: center;
  font-size: var(--main-font);
`;
const FollowWrapper = styled.div`
  margin-top: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  > .countWrap {
    color: #777777;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    > .count {
      color: #222222;
      font-size: var(--main-font);
    }
  }
  > .line {
    display: inline-block;
    width: 1px;
    height: 3rem;
    margin: 0 1.5rem;
    background-color: var(--border-color);
  }
`;

const TempWrap = styled.div`
  position: absolute;
  right: 2rem;
  top: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 17rem;
  > .tempBack {
    flex: 1;
    display: flex;
    width: 1rem;
    flex-direction: column-reverse;
    background-color: #d9d9d9;
    border-radius: 0.5rem;
  }
`;
const MannerTemp = styled.div`
  height: ${(props) => props.height}%;
  background: linear-gradient(to top, #cf9ba7, #bb2649);
  border-radius: 0.5rem;
  transition: 0.5s all;
`;

const EtcWrap = styled.article`
  width: 100%;
  min-height: 100vh;
  position: absolute;
  top: -7.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const Profile = ({ myPage, id }) => {
  const axiosInstance = useAxios();
  // 유저 데이터를 받아오는 함수
  const userDataGet = async () => {
    const res = await axiosInstance.get(`/api/members/${id}`, {
      headers: { Authorization: localStorage.getItem("accessToken") },
    });
    return res.data.data;
  };

  const {
    data: userData,
    isError,
    isLoading,
    error,
  } = useQuery("userData", () => userDataGet());

  // 팔로우 여부 확인 함수
  const followStatusGet = async (id) => {
    const res = await axiosInstance.get(`api/members/${id}/follow-status`, {
      headers: { Authorization: localStorage.getItem("accessToken") },
    });
    return res.data.data.result;
  };

  const { data: followStatus } = useQuery(["followStatus", id], () =>
    followStatusGet(id)
  );

  // 팔로우 기능 함수
  const followPost = async (id) => {
    try {
      await axiosInstance.post(
        `api/members/${id}/follow`,
        { body: "follow" },
        {
          headers: { Authorization: localStorage.getItem("accessToken") },
        }
      );
      window.location.href = `/userpage/${id}`;
    } catch (error) {
      alert("팔로우 실패");
      console.log(error.message);
    }
  };
  const { mutate: followMutate } = useMutation(() => followPost(id));

  // 언팔로우 기능 함수
  const unfollowPost = async (id) => {
    try {
      await axiosInstance.post(
        `api/members/${id}/unfollow`,
        { body: "unfollow" },
        {
          headers: { Authorization: localStorage.getItem("accessToken") },
        }
      );
      window.location.href = `/userpage/${id}`;
    } catch (error) {
      alert("언팔로우 실패");
      console.log(error.message);
    }
  };
  const { mutate: unfollowMutate } = useMutation(() => unfollowPost(id));

  return (
    <ProfileWrapper>
      {isLoading ? (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      ) : isError ? (
        <EtcWrap>
          <p>{error.message}</p>
        </EtcWrap>
      ) : (
        <>
          <ImgWrapper>
            <Img
              alt="testProfile"
              imgSrc={`${process.env.REACT_APP_S3_URL}${userData.fileInfo.fileUrl}/${userData.fileInfo.fileName}`}
            />
          </ImgWrapper>
          <TempWrap>
            <div className="tempBack">
              <MannerTemp height={userData.reputation}></MannerTemp>
            </div>
            <span className="tempNum">{userData.reputation}°</span>
          </TempWrap>
          <InfoWrapper>
            <ButtonWrapper>
              {myPage ? (
                ""
              ) : (
                <Button
                  className={followStatus ? "disabled" : "active"}
                  onClick={() =>
                    followStatus ? unfollowMutate() : followMutate()
                  }
                >
                  {followStatus ? "언팔로우" : "팔로우"}
                </Button>
              )}
            </ButtonWrapper>
            <Name>{userData.nickname}</Name>
            <Text>{userData.aboutMe}</Text>
            <Link to={`/userpage/followcheck/${id}`} className="followCheck">
              <FollowWrapper>
                <div className="countWrap">
                  <span className="count">{userData.following}</span>
                  팔로워
                </div>
                <span className="line" />
                <div className="countWrap">
                  <span className="count">{userData.follower}</span>
                  팔로우
                </div>
              </FollowWrapper>
            </Link>
          </InfoWrapper>
          <Category getCategoryList={userData.categorySmallIdList} />
          <Tab myPage={myPage} />
        </>
      )}
    </ProfileWrapper>
  );
};

export default Profile;
