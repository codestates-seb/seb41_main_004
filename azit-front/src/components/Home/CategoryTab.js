import AzitList from "../common/AzitList";
import React, { useEffect, useState } from "react";
import { useInfiniteQuery } from "react-query";
import CategoryPicker from "./CategoryPicker";
import { useInView } from "react-intersection-observer";
import Loading from "../common/Loading";
import Null from "./Null";
import axiosInstance from "../../util/axios";

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

  const { data, status, fetchNextPage, isFetchingNextPage, error } =
    useInfiniteQuery(
      ["get", selectCategory],
      ({ pageParam = 1 }) => fetchInfiniteList(pageParam, selectCategory),
      {
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
        {status === "error" && <Null text={error.message} />}
        {data?.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((categoryData) => (
                <AzitList key={categoryData.clubId} data={categoryData} />
              ))
            ) : (
              <Null text="해당 카테고리의 아지트가 없습니다." />
            )}
          </React.Fragment>
        ))}
      </article>
      {status !== "error" && status === "success" ? (
        isFetchingNextPage ? (
          <Loading />
        ) : (
          <div ref={ref} />
        )
      ) : (
        <></>
      )}
    </>
  );
};

export default CategoryTab;
