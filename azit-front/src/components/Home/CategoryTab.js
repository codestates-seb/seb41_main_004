import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../common/AzitList";
import { useState } from "react";
import { useQuery } from "react-query";
import { axiosInstance } from "../../util/axios";
import CategoryPicker from "./CategoryPicker";

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
const CategoryTab = () => {
  const [selectCategory, setSelectCategory] = useState(1);
  const handleSelectCategory = (num) => {
    setSelectCategory(num);
  };
  // Data를 카테고리별로 data를 받아오는 로직 필요
  const getSelectCategory = async (cl) => {
    try {
      const res = await axiosInstance.get(
        `/api/clubs/category?page=1&size=10&cl=${cl}`
      );

      return res.data;
    } catch (error) {
      console.log(error.response.data);
    }
  };
  const { data, isLoading, isError, error } = useQuery(
    ["selectData", selectCategory],
    () => getSelectCategory(selectCategory)
  );
  console.log(data)
  return (
    <>
    <CategoryPicker selectCategory={selectCategory} handleSelectCategory={handleSelectCategory}/>
      {data?.data ? (
        data.data.map((data) => <AzitList key={data.clubId} data={data} />)
      ) : (
        <Null>해당 카테고리의 아지트가 없습니다.</Null>
      )}
      {ClubData && <MoreBtn>더보기 +</MoreBtn>}
    </>
  );
};

export default CategoryTab;
