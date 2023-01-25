import React, { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery } from "react-query";
import { useParams } from "react-router-dom";
import { axiosInstance } from "../../util/axios";
import Loading from "../common/Loading";
import Null from "../Home/Null";

import Review from "./Review";

const Reviews = () => {
  const { ref, inView } = useInView();
  const { id } = useParams();

  // 무한스크롤 함수
  const getInfiniteList = async (pageParam, id) => {
    const res = await axiosInstance.get(
      `/api/reviews/reviewee/${id}?page=${pageParam}&size=5`,
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

  const {
    data: reviewsData,
    status,
    fetchNextPage,
    isFetchingNextPage,
    error,
  } = useInfiniteQuery(
    ["reviewsGet", id],
    ({ pageParam = 1 }) => getInfiniteList(pageParam, id),
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
      {status === "loading" ? (
        <Loading />
      ) : status === "error" ? (
        <Null text={error.message} />
      ) : (
        reviewsData.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((review) => (
                <Review key={review.reviewId} review={review} />
              ))
            ) : (
              <Null text="작성 된 리뷰가 없습니다." />
            )}
          </React.Fragment>
        ))
      )}
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

export default Reviews;
