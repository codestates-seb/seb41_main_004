import styled from "styled-components";
import CultureIcon from "../../images/categoryCultureIcon.png";
import ExerciseIcon from "../../images/categoryExerciseIcon.png";
import FoodIcon from "../../images/categoryFoodIcon.png";
import HobbyIcon from "../../images/categoryHobbyIcon.png";
import TravelIcon from "../../images/categoryTravelIcon.png";
import CreativeIcon from "../../images/categoryCreativeIcon.png";
import GrowthIcon from "../../images/categoryGrowthIcon.png";
const CategoryPickerWrap = styled.ul`
display: flex;
flex-direction: row;
flex-wrap: wrap;
justify-content: space-around;
`;
const Category = styled.li`
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
width: 8rem;
height: 8rem;
border-radius: 10px;
transition: 0.3s all;
margin-bottom: 2rem;
cursor: pointer;
> img {
  width: 4.2rem;
}
> span {
  font-size: var(--caption-font);
}
&&.active {
  background-color: var(--white-color);
}
:hover {
  background-color: rgba(255, 255, 255, 0.7);
}
@media screen and (min-width: 400px) {
  width: 10rem;
}
`;

const CategoryPicker = ({selectCategory, handleSelectCategory}) => {
    return (
        <CategoryPickerWrap>
        <Category
          className={selectCategory === 1 && "active"}
          onClick={() => handleSelectCategory(1)}
        >
          <img alt="CultureIcon" src={CultureIcon} />
          <span>문화 / 예술</span>
        </Category>
        <Category
          className={selectCategory === 2 && "active"}
          onClick={() => handleSelectCategory(2)}
        >
          <img alt="ExerciseIcon" src={ExerciseIcon} />
          <span>운동 / 액티비티</span>
        </Category>
        <Category
          className={selectCategory === 3 && "active"}
          onClick={() => handleSelectCategory(3)}
        >
          <img alt="FoodIcon" src={FoodIcon} />
          <span>푸드 / 드링크</span>
        </Category>
        <Category
          className={selectCategory === 4 && "active"}
          onClick={() => handleSelectCategory(4)}
        >
          <img alt="HobbyIcon" src={HobbyIcon} />
          <span>취미</span>
        </Category>
        <Category
          className={selectCategory === 5 && "active"}
          onClick={() => handleSelectCategory(5)}
        >
          <img alt="TravelIcon" src={TravelIcon} />
          <span>여행 / 동행</span>
        </Category>
        <Category
          className={selectCategory === 6 && "active"}
          onClick={() => handleSelectCategory(6)}
        >
          <img alt="CreativeIcon" src={CreativeIcon} />
          <span>창작</span>
        </Category>
        <Category
          className={selectCategory === 7 && "active"}
          onClick={() => handleSelectCategory(7)}
        >
          <img alt="GrowthIcon" src={GrowthIcon} />
          <span>성장 / 자기계발</span>
        </Category>
      </CategoryPickerWrap>
    )
}

export default CategoryPicker