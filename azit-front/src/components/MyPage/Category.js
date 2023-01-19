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
  font-size: var(--main-font);
`;

const Category = () => {
  const inter = [
    {
      id: 1,
      tags: "전시",
    },
    {
      id: 2,
      tags: "영화",
    },
    {
      id: 3,
      tags: "뮤지컬",
    },
    {
      id: 4,
      tags: "공연",
    },
    {
      id: 5,
      tags: "디자인",
    },
    {
      id: 6,
      tags: "클라이밍",
    },
    {
      id: 7,
      tags: "등산",
    },
    {
      id: 8,
      tags: "헬스",
    },
    {
      id: 9,
      tags: "헬스",
    },
    {
      id: 10,
      tags: "헬스",
    },
    {
      id: 11,
      tags: "헬스",
    },
    {
      id: 12,
      tags: "헬스",
    },
    {
      id: 13,
      tags: "헬스",
    },
  ];
  return (
    <Container>
      {inter
        .filter((tag) => tag.id === 7)
        .map((tag) => {
          return <Categorys key={tag.id}>{tag.tags}</Categorys>;
        })}
    </Container>
  );
};

export default Category;
