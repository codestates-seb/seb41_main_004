import styled from "styled-components";
import UserProfile from "../../images/testProfileImg2.png";

const Container = styled.div`
  width: 100%;
  height: 7rem;
  background-color: #ffffff;
  display: flex;
  align-items: center;
`;
const ImgBox = styled.div`
  width: 15%;
  display: flex;
  justify-content: center;
`;
const UserImg = styled.img.attrs({
  src: `${UserProfile}`,
})`
  width: 5rem;
  height: 5rem;
  border-radius: 100%;
`;
const FollowBox = styled.div`
  width: 70%;
`;
const FollowName = styled.p`
  font-size: var(--main-font);
`;
const FollowText = styled.p`
  font-size: var(--caption-font);
  color: #aaaaaa;
`;
const ButtonBox = styled.div`
  width: 15%;
`;
const Button = styled.button`
  width: 5rem;
  height: 2rem;
  border-radius: 0.5rem;
  background-color: #bb2649;
  border: none;
  font-size: var(--caption-font);
  color: white;
`;

const FollowingList = ({ data }) => {
  // const [follow, SetFollow] = useState(true);

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
          <Button>팔로우</Button>
        </ButtonBox>
      </Container>
    </>
  );
};

export default FollowingList;
