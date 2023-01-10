import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../AzitList";
import CultureIcon from "../../images/categoryCultureIcon.png";
import ExerciseIcon from "../../images/categoryExerciseIcon.png";
import FoodIcon from "../../images/categoryFoodIcon.png";
import HobbyIcon from "../../images/categoryHobbyIcon.png";
import TravelIcon from "../../images/categoryTravelIcon.png";
import CreativeIcon from "../../images/categoryCreativeIcon.png";
import GrowthIcon from "../../images/categoryGrowthIcon.png";

const Null = styled.article`
  padding: 8rem 0 0;
  text-align: center;
  color: var(--sub-font-color);
`;
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
const CategoryPicker = styled.ul`
    display:flex;
    flex-direction:row;
    flex-wrap:wrap;
    justify-content: space-around;
    >li {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 8rem;
      height: 8rem;
      border-radius: 10px;
      transition:0.3s all;
      margin-bottom: 2rem;
      >img {
        width: 4.2rem;
      }
      >span {
        font-size:var(--caption-font);
      }
    }
    @media screen and (min-width:400px) {
      li {
        width: 10rem;
      }
    }
    >li.active {
      background-color: var(--white-color);
    }
    >li:hover {
      background-color: rgba(255,255,255,0.7);
    }
`;

const CategoryTab = () => {
  // Data를 카테고리별로 data를 받아오는 로직 필요
  return (
    <>
      <CategoryPicker>
        <li className="active">
          <img alt="CultureIcon" src={CultureIcon} />
          <span>문화 / 예술</span>
        </li>
        <li>
          <img alt="ExerciseIcon" src={ExerciseIcon} />
          <span>운동 / 액티비티</span>
        </li>
        <li>
          <img alt="FoodIcon" src={FoodIcon} />
          <span>푸드 / 드링크</span>
        </li>
        <li>
          <img alt="HobbyIcon" src={HobbyIcon} />
          <span>취미</span>
        </li>
        <li>
          <img alt="TravelIcon" src={TravelIcon} />
          <span>여행 / 동행</span>
        </li>
        <li>
          <img alt="CreativeIcon" src={CreativeIcon} />
          <span>창작</span>
        </li>
        <li>
          <img alt="GrowthIcon" src={GrowthIcon} />
          <span>성장 / 자기계발</span>
        </li>
      </CategoryPicker>
      {ClubData ? (
        ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
      ) : (
        <Null>해당 카테고리의 아지트가 없습니다.</Null>
      )}
      {ClubData && <MoreBtn>더보기 +</MoreBtn>}
    </>
  );
};

export default CategoryTab;
