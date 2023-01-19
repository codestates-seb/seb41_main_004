import styled from "styled-components";
import { interests } from "../../dummyData/Category";

const Container = styled.div`
  display: flex;
  justify-content: center;
  margin: 4rem;
  word-break: break-all;
  word-wrap: break-word;
  flex-wrap: wrap;
`;

const Categorys = styled.span`
  margin: 0.5rem;
  padding: 1rem;
  background-color: #bb2649;
  color: white;
  border-radius: 2rem;
  font-size: var(--main-font);
`;

const Category = () => {
  const inter = {
    Id: 1,
    tags: [
      "전시",
      "영화",
      "뮤지컬",
      "공연",
      "디자인",
      "클라이밍",
      "등산",
      "헬스",
      "필라테스",
      "골프",
    ],
  };

  return (
    <Container>
      <Categorys>{inter.tags.filter((tag, idx) => tag !== [7, 13])}</Categorys>
    </Container>
  );
};

export default Category;
