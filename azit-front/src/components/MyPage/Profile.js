import styled from "styled-components";
import testProfile from "../../images/testProfileImg.png";
import Category from "./Category";
import Tab from "./Tab";

const ProfileWrapper = styled.div`
  padding: 3rem;
  display: flex;
  flex-direction: column;
`;

const ImgWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

const Img = styled.img`
  width: 120px;
  height: 120px;
  border-radius: 100%;
`;
const ButtonWrapper = styled.span`
  display: flex;
  justify-content: center;
`;
const Button = styled.button`
  background-color: #bb2649;
  border-radius: 0.5rem;
  border: none;
  color: white;
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

const Profile = () => {
  return (
    <ProfileWrapper>
      <ImgWrapper>
        <Img alt="testProfile" src={testProfile} />
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
