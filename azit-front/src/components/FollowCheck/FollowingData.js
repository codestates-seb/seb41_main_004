import React from "react";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery } from "react-query";
import { useParams } from "react-router-dom";
import { axiosInstance } from "../../util/axios";
import Loading from "../common/Loading";
import Null from "../Home/Null";
import DataList from "./DataList";


const FollowingData = () => {
  const { ref, inView } = useInView();
  const {id} = useParams();
  const fetchInfiniteList = async (pageParam, id) => {
    const res = await axiosInstance.get(
      `/api/members/${id}/follower?page=${pageParam}&size=15`,
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
  
  const { data:followerData, status, fetchNextPage, isFetchingNextPage, error } =
    useInfiniteQuery(
      ["followerList", id],
      ({ pageParam = 1 }) => fetchInfiniteList(pageParam, id),
      {
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
          followerData.pages.map((page, index) => (
            <React.Fragment key={index}>
              {page.board_page.length > 0 ? (
                page.board_page.map((follower) => (
                  <DataList key={follower.followId} follower={follower} />
                ))
              ) : (
                <Null text="팔로워가 없습니다." />
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

export default FollowingData;
