import { useState } from "react";
import styled from "styled-components";
import testProfile from "../../images/testProfileImg.png";
import Category from "./Category";
import Tab from "./Tab";

const ProfileWrapper = styled.div`
  padding: 2rem 0;
`;

const ImgWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-left: 18.8rem;
  padding-right: 2rem;
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
`;
const InfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Name = styled.p`
  font-weight: bold;
`;
const Text = styled.p`
  margin: 1rem;
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
const TempWrapper = styled.div`
  display: flex;
  flex-direction: column-reverse;
  background-color: #d9d9d9;
`;
const MannerTemp = styled.div`
  width: 1rem;
  height: ${(props) => props.height}%;
  background-color: #bb2649;
`;

const Profile = () => {
  const [temp, setTemp] = useState(0);

  return (
    <ProfileWrapper>
      <ImgWrapper>
        <Img alt="testProfile" src={testProfile} />
        <TempWrapper>
          <MannerTemp height={60}>온도계</MannerTemp>
        </TempWrapper>
      </ImgWrapper>
      <InfoWrapper>
        <ButtonWrapper>
          <Button>팔로우</Button>
        </ButtonWrapper>
        <Name>닉네임</Name>
        <Text>
          매일 반복되는 일상을 특별하게 만들고 싶다.
          <br /> ㅁㄴㅇㄹㅁㄴㅇㄹ
        </Text>
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
      </InfoWrapper>
      <Category></Category>
      <Tab></Tab>
    </ProfileWrapper>
  );
};

export default Profile;
