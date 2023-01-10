import styled from "styled-components";

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
`;

const Category = () => {
  return (
    <Container>
      <Categorys>습관만들기</Categorys>&nbsp;
      <Categorys>전시</Categorys>&nbsp;
      <Categorys>클라이밍</Categorys>&nbsp;
      <Categorys>복합문화공간</Categorys>&nbsp;
      <Categorys>영화</Categorys>&nbsp;
      <Categorys>뮤지컬</Categorys>&nbsp;
    </Container>
  );
};

export default Category;
