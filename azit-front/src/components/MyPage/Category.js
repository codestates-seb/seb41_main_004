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
  const interests = [
    {
      id: 1,
      subtitle: "문화/예술",
      tags: ["전시", "영화", "뮤지컬", "공연", "디자인"],
    },
    {
      id: 2,
      subtitle: "운동/액티비티",
      tags: ["클라이밍", "등산", "헬스", "필라테스", "골프"],
    },
    {
      id: 3,
      subtitle: "푸드/드링크",
      tags: ["맛집투어", "카페", "와인", "커피", "디저트"],
    },
    {
      id: 4,
      subtitle: "취미",
      tags: ["보드게임", "사진", "방탈출", "VR", "음악감상"],
    },
    {
      id: 5,
      subtitle: "여행/동행",
      tags: ["복합문화공간", "테마파크", "피크닉", "드라이브", "캠핑"],
    },
    {
      id: 6,
      subtitle: "창작",
      tags: ["글쓰기", "드로잉", "영상편집", "공예", "DIY"],
    },
    {
      id: 7,
      subtitle: "성장/자기계발",
      tags: ["습관만들기", "챌린지", "독서", "스터디", "외국어"],
    },
  ];

  return (
    <Container>
      {interests.tags &&
        interests.tags.map((tag, idx) => (
          <Categorys key={idx}>{tag}</Categorys>
        ))}
    </Container>
  );
};

export default Category;
