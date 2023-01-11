import styled from "styled-components";
import UserProfile from "../../images/testProfileImg.png";

const Container = styled.div`
  width: 100%;
  height: 7rem;
  background-color: #ffffff;
  display: flex;
`;
const ImgBox = styled.div`
  width: 15%;
  display: flex;
  justify-content: center;
`;
const UserImg = styled.img.attrs({
  src: `${UserProfile}`,
})`
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 100%;
`;
const FollowBox = styled.div`
  width: 70%;
`;
const FollowName = styled.p``;
const FollowText = styled.p`
  color: #aaaaaa;
`;
const ButtonBox = styled.div`
  width: 15%;
`;
const Button = styled.button`
  width: 5rem;
  border: none;
`;

const FollowList = ({ data }) => {
  console.log(data);
  return (
    <>
      <Container>
        <ImgBox>
          <UserImg src={UserProfile} />
        </ImgBox>
        <FollowBox>
          <FollowName>{data.user[0].userName}</FollowName>
          <FollowText>{data.clubInfo}</FollowText>
        </FollowBox>
        <ButtonBox>
          <Button>해제</Button>
        </ButtonBox>
      </Container>
    </>
  );
};

export default FollowList;
