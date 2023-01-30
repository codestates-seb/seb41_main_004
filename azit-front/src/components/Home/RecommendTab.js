import React, { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery } from "react-query";
import AzitList from "../common/AzitList";
import Null from "./Null";
import Loading from "../common/Loading";
import useAxios from "../../util/useAxios";

const RecommendTab = () => {
  const { ref, inView } = useInView();
  const axiosPrivate = useAxios();
  const [memberId, setMemberId] = useState(0);

  useEffect(() => {
    if (localStorage.getItem("memberId")) {
      setMemberId(localStorage.getItem("memberId"));
    }
  }, []);
  // 무한스크롤 함수 0번이 guest


  const fetchInfiniteList = async (pageParam, memberId) => {
    const res = await axiosPrivate.get(
      `/api/clubs/recommend/${memberId}?page=${pageParam}&size=5`,
      {
        headers: { Authorization: localStorage.getItem("accessToken") },
      }
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
      ["recommend", memberId],
      ({ pageParam = 1 }) => fetchInfiniteList(pageParam, memberId),
      {
        staleTime: 6 * 10 * 1000,
        cacheTime: 6 * 10 * 1000,
        getNextPageParam: (lastPage) =>
          !lastPage.isLast ? lastPage.nextPage : undefined,
      }
    );

  // 맨 밑에 도달했을 시 함수호출
  useEffect(() => {
    if (inView) fetchNextPage();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [inView]);
  
  return (
    <>
      <article className="resultCell">
        {status === "loading" && <Loading />}
        {status === "error" && <Null text={error.message} />}
        {data?.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((daysData) => (
                <AzitList key={daysData.clubId} data={daysData} />
              ))
            ) : (
              <Null text="관심 카테고리를 선택해주세요." />
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

export default RecommendTab;
