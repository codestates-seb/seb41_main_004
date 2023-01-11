import styled from "styled-components";
import UserProfile from "../../images/testProfileImg.png";

const Container = styled.div``;
const ImgBox = styled.div``;
const UserImg = styled.img.attrs({
  src: `${UserProfile}`,
})`
  width: 3rem;
  height: 3rem;
  border-radius: 100%;
`;
const FollowBox = styled.div``;
const FollowName = styled.span``;
const FollowText = styled.span``;
const ButtonBox = styled.div``;
const Button = styled.button``;

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
