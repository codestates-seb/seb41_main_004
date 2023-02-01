import React from "react";
// import { useState } from "react";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery, useMutation } from "react-query";
import { useParams } from "react-router-dom";
import axiosInstance from "../../util/axios";
import Loading from "../common/Loading";
import Null from "../Home/Null";
import DataList from "./DataList";

const FollowingData = ({change, setChange}) => {
  const { ref, inView } = useInView();
  const { id } = useParams();
  // const [change, setChange] = useState(false);
  const fetchInfiniteList = async (pageParam, id) => {
    const res = await axiosInstance.get(
      `/api/members/${id}/follower?page=${pageParam}&size=15`
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
    data: followerData,
    status,
    fetchNextPage,
    isFetchingNextPage,
    error,
  } = useInfiniteQuery(
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

  // 팔로우 기능 함수
  const followPost = async (followerId) => {
    try {
      await axiosInstance.post(`api/members/${followerId}/follow`, {
        body: "follow",
      });
      window.location.href = `/userpage/followcheck/${id}`;
    } catch (error) {
      alert("팔로우 실패");
    }
  };
  const { mutate: followMutate } = useMutation((followerId) =>
    followPost(followerId)
  );
  // follower?.follower.memberId

  // 팔로워 삭제 함수
  const followerDelete = async (id, followerId) => {
    try {
      await axiosInstance.delete(`api/members/${id}/follower/${followerId}`);
      window.location.href = `/userpage/followcheck/${id}`;
    } catch (error) {
      alert("팔로워 삭제 실패");
    }
  };
  const { mutate: deleteMutate } = useMutation((followerId) =>
    followerDelete(id, followerId)
  );

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
                <DataList
                  key={follower.followId}
                  follower={follower}
                  followMutate={followMutate}
                  deleteMutate={deleteMutate}
                />
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
