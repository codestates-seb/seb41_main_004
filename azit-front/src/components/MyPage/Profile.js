// import { useState } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import testProfile from "../../images/testProfileImg.png";
import Category from "./Category";
import Tab from "./Tab";

const ProfileWrapper = styled.div`
  margin: 2rem 0;
  position: relative;
`;

const ImgWrapper = styled.div`
  display: flex;
  justify-content: center;
  padding-top: 2rem;
`;

const Img = styled.img`
  width: 120px;
  height: 120px;
  border-radius: 100%;
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
`;
const InfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
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
  width: 100%;
`;
const FollowingCount = styled.span`
  display: flex;
  justify-content: center;
  color: #222222;
  font-size: var(--main-font);
`;
const Following = styled.span`
  color: #777777;
  border-right: 1px solid #d9d9d9; ;
`;
const FolllowerCount = styled.span`
  display: flex;
  justify-content: center;
  color: #222222;
`;
const Follower = styled.span`
  color: #777777;
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
`;

const Profile = () => {
  // const [temp, setTemp] = useState(0);
  const [myPage, setMyPage] = useState(true);

  return (
    <ProfileWrapper>
      <ImgWrapper>
        <Img alt="testProfile" src={testProfile} />
      </ImgWrapper>
      <TempWrap>
        <div className="tempBack">
          <MannerTemp height={60}></MannerTemp>
        </div>
        <span className="tempNum">60°</span>
      </TempWrap>
      <InfoWrapper>
        <ButtonWrapper>{myPage ? "" : <Button>팔로우</Button>}</ButtonWrapper>
        <Name>닉네임</Name>
        <Text>매일 반복되는 일상을 특별하게 만들고 싶다. ㅁㄴㅇㄹㅁㄴㅇㄹ</Text>
        <Link to="/userpage/followcheck" className="followCheck">
          <FollowWrapper>
            <Following>
              <FollowingCount>0</FollowingCount>
              팔로잉&nbsp;&nbsp;
            </Following>
            <Follower>
              <FolllowerCount>3</FolllowerCount>
              &nbsp;&nbsp; 팔로워
            </Follower>
          </FollowWrapper>
        </Link>
      </InfoWrapper>
      <Category></Category>
      <Tab></Tab>
    </ProfileWrapper>
  );
};

export default Profile;
