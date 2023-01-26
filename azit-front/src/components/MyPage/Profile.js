// import { useState } from "react";
import { Link } from "react-router-dom";
import { axiosInstance } from "../../util/axios";
import { useQuery } from "react-query";
import styled from "styled-components";
import Category from "./Category";
import Tab from "./Tab";
import Loading from "../common/Loading";
import { useMutation } from "react-query";

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
  background-color: #bb2649;
  border-radius: 0.5rem;
  border: none;
  color: white;
  /* position: absolute; */
  margin-top: -1rem;
  font-size: var(--caption-font);
  width: 5.5rem;
  height: 2rem;
  cursor: pointer;
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
    >.count {
      color: #222222;
      font-size: var(--main-font);
    }
  }
  >.line {
    display: inline-block;
    width: 1px;
    height: 3rem;
    margin:0 1.5rem;
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

  const followPost = async (id) => {
    try {
      await axiosInstance.post(`api/members/${id}/follow`,{"body" : "follow"}, {
        headers: { Authorization: localStorage.getItem("accessToken") }
      });
      // await fetch(`${process.env.REACT_APP_BASE_URL}api/members/${id}/follow`, {
      //   method: 'POST',
      //   headers: { Authorization: localStorage.getItem("accessToken") }
      // })
    } catch (error) {
      alert("팔로우 실패")
      console.log(error.message);
    }
    // const res = await axiosInstance.post(`api/members/${id}/follow`,
    // {
    //   headers: { Authorization: localStorage.getItem("accessToken") },
    // })
    // return res
  }
  const {mutate} = useMutation(() => followPost(id));
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
              {myPage ? "" : <Button onClick={mutate}>팔로우</Button>}
            </ButtonWrapper>
            <Name>{userData.nickname}</Name>
            <Text>{userData.aboutMe}</Text>
            <Link to={`/userpage/followcheck/${id}`} className="followCheck">
              <FollowWrapper>
                <div className="countWrap">
                  <span className="count">0</span>
                  팔로잉
                </div>
                <span className="line"/>
                <div className="countWrap">
                  <span className="count">0</span>
                  팔로워
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
