import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../common/AzitList";
import Null from "./Null";

const MoreBtn = styled.button`
  width: 100%;
  height: 4rem;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  font-size: var(--big-font);
  font-weight: var(--bold-weight);
  color: var(--sub-font-color);
  background-color: transparent;
  border: none;
  cursor: pointer;
`;
const RecommendTab = () => {
  // 추천목록 불러오는 코드를 써 map을 돌린 후 props로 내린다.
  return (
    <>
      {ClubData ? (
        ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
      ) : (
        <Null text="추천 아지트가 없습니다." />
      )}
      {ClubData && <MoreBtn>더보기 +</MoreBtn>}
    </>
  );
};

export default RecommendTab;
