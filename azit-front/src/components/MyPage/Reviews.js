import styled from "styled-components";
import { toDateFormatOfKR } from "../../util/toDateFormatOfUS";

const Container = styled.div``;
const TitleBox = styled.div``;
const Title = styled.span``;

const Reviews = () => {
  return (
    <Container>
      <TitleBox>
        <Title>일이삼사오육칠팔구십일이삼사오육칠팔구십</Title> <br />
        <span>{` ${toDateFormatOfKR(new Date())}`}</span>
      </TitleBox>
    </Container>
  );
};

export default Reviews;
