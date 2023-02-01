import React from "react";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery, useMutation } from "react-query";
import { useParams } from "react-router-dom";
import axiosInstance from "../../util/axios";
import Loading from "../common/Loading";
import Null from "../Home/Null";
import DataList from "./DataList";

const FollowData = () => {
  const { ref, inView } = useInView();
  const { id } = useParams();
  const fetchInfiniteList = async (pageParam, id) => {
    const res = await axiosInstance.get(
      `/api/members/${id}/following?page=${pageParam}&size=15`
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
    data: followingData,
    status,
    fetchNextPage,
    isFetchingNextPage,
    error,
  } = useInfiniteQuery(
    ["followingList", id],
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

  // 언팔로우 기능 함수
  const unfollowPost = async (followId) => {
    try {
      await axiosInstance.post(`api/members/${followId}/unfollow`, {
        body: "unfollow",
      });
      window.location.href = `/userpage/followcheck/${id}`;
    } catch (error) {
      alert(error);
    }
  };
  const { mutate: unfollowMutate } = useMutation((followId) =>
    unfollowPost(followId)
  );

  return (
    // </>
    <>
      {status === "loading" ? (
        <Loading />
      ) : status === "error" ? (
        <Null text={error.message} />
      ) : (
        followingData.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((follow) => (
                <DataList
                  key={follow.followId}
                  follow={follow.followee}
                  unfollowMutate={unfollowMutate}
                />
              ))
            ) : (
              <Null text="팔로우 한 사람이 없습니다." />
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

export default FollowData;
