import styled from "styled-components";
import AzitList from "../common/AzitList";
import React, { useEffect, useState } from "react";
import { useInfiniteQuery } from "react-query";
import { axiosInstance } from "../../util/axios";
import CategoryPicker from "./CategoryPicker";
import { useInView } from "react-intersection-observer";
import Loading from "../common/Loading";

const Null = styled.article`
  padding: 8rem 0 0;
  text-align: center;
  color: var(--sub-font-color);
`;
const CategoryTab = () => {
  const [selectCategory, setSelectCategory] = useState(1);
  const { ref, inView } = useInView();
  const handleSelectCategory = (num) => {
    setSelectCategory(num);
  };

  const fetchInfiniteList = async (pageParam, cl) => {
    const res = await axiosInstance.get(
      `/api/clubs/category?page=${pageParam}&size=5&cl=${cl}`
    );
    return {
      board_page: res.data.data,
      nextPage: pageParam + 1,
      isLast:
        res.data.data.length > 0
          ? res.data.pageInfo.page === res.data.pageInfo.totalPages
          : true,
    };
  };

  const { data, status, fetchNextPage, isFetchingNextPage } = useInfiniteQuery(
    ["get", selectCategory],
    ({ pageParam = 1 }) => fetchInfiniteList(pageParam, selectCategory),
    {
      enabled: !!selectCategory,
      staleTime: 6 * 10 * 1000,
      cacheTime: 6 * 10 * 1000,
      getNextPageParam: (lastPage) =>
        !lastPage.isLast ? lastPage.nextPage : undefined,
    }
  );

  useEffect(() => {
    if (inView) fetchNextPage();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [inView]);
  return (
    <>
      <CategoryPicker
        selectCategory={selectCategory}
        handleSelectCategory={handleSelectCategory}
      />
      <article className="resultCell">
        {status === "loading" && <Loading />}
        {status === "error" && <Null>Not Defined Error</Null>}
        {data?.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((searchData) => (
                <AzitList key={searchData.clubId} data={searchData} />
              ))
            ) : (
              <Null>해당 카테고리의 아지트가 없습니다.</Null>
            )}
          </React.Fragment>
        ))}
      </article>
      {isFetchingNextPage ? <Loading /> : <div ref={ref} />}
    </>
  );
};

export default CategoryTab;
