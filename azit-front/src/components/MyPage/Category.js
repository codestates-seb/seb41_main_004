import { useEffect, useState } from "react";
import styled from "styled-components";
import { axiosInstance } from "../../util/axios";
import { useQuery } from "react-query";
const Container = styled.div`
  display: flex;
  justify-content: center;
  margin: 4rem;
  word-break: break-all;
  word-wrap: break-word;
  flex-wrap: wrap;
`;

const Categorys = styled.span`
  margin: 0 0.5rem 1rem;
  padding: 0.5rem 1.5rem;
  background-color: #bb2649;
  color: white;
  border-radius: 2rem;
  font-size: var(--main-font);
`;

const Category = ({ getCategoryList }) => {
  const [categoryList, setCategoryList] = useState([]);
  const smallCategory = async () => {
    const res = await axiosInstance.get("/api/categories/small");
    return res.data.data;
  };
  const { data: smallCategoryList } = useQuery("smallCategory", () =>
    smallCategory()
  );
  useEffect(() => {
    if (smallCategoryList) {
      setCategoryList(
        smallCategoryList.filter((item) =>
          getCategoryList.includes(item.categorySmallId)
        )
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [smallCategoryList, getCategoryList]);

  return (
    <Container>
      {categoryList.map((tag) => {
        return (
          <Categorys key={tag.categorySmallId}>{tag.categoryName}</Categorys>
        );
      })}
    </Container>
  );
};

export default Category;
