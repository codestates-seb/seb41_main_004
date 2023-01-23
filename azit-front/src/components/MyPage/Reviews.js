// import { useEffect } from "react";
// import { useInView } from "react-intersection-observer";
// import { useInfiniteQuery } from "react-query";
// import { useParams } from "react-router-dom";
// import { axiosInstance } from "../../util/axios";

import Review from "./Review";

const Reviews = () => {
  // const { ref, inView } = useInView();
  // const { id } = useParams();
  // useEffect(() => {
  //   const raw = JSON.stringify({
  //     "revieweeId": 2
  //   });
  //   const getAxios = async () => {
  //     const res = await axiosInstance.get(`/api/reviews?page=1&size=5`, {
  //       headers: { Authorization: localStorage.getItem("accessToken") },
  //       body: raw,
  //     });
  //     console.log(res);
  //   };
  //   getAxios();
  // }, []);

  // 무한스크롤 함수
  // const getInfiniteList = async (pageParam) => {
  //   let d
  //   const res = await axiosInstance.get(
  //     `/api/reviews?page=${pageParam}&size=5`,
  //     { 'revieweeId': id },
  //     {
  //       headers: { Authorization: localStorage.getItem("accessToken") },
  //     }
  //   );

  //   return {
  //     board_page: res.data.data,
  //     nextPage: pageParam + 1,
  //     isLast:
  //       res.data.data.length > 0
  //         ? res.data.pageInfo.page === res.data.pageInfo.totalPages
  //         : true,
  //   };
  // };

  // const { data, status, fetchNextPage, isFetchingNextPage, error } =
  //   useInfiniteQuery(
  //     "recommend",
  //     ({ pageParam = 1 }) => getInfiniteList(pageParam),
  //     {
  //       staleTime: 6 * 10 * 1000,
  //       cacheTime: 6 * 10 * 1000,
  //       getNextPageParam: (lastPage) =>
  //         !lastPage.isLast ? lastPage.nextPage : undefined,
  //     }
  //   );
  //     console.log(status)
  // // 맨 밑에 도달했을 시 함수호출
  // useEffect(() => {
  //   if (inView) fetchNextPage();
  //   // eslint-disable-next-line react-hooks/exhaustive-deps
  // }, [inView]);
  const data = [
    {
      reviewId: 1,
      revieweeId: 2,
      club: {
        clubId: 1,
        clubName: "재밌는 아지트",
        meetingDate: "2023-01-20",
      },
      commentCategory: "배려심이 있어요",
      commentBody: "마음씨가 너무 착해요.",
      reviewStatus: false,
    },
    {
      reviewId: 2,
      revieweeId: 2,
      club: {
        clubId: 1,
        clubName: "재밌는 아지트",
        meetingDate: "2023-01-20",
      },
      commentCategory: "배려심이 있어요",
      commentBody: "마음씨가 너무 착해요.",
      reviewStatus: false,
    },
  ];
  return (
    <>
      {data.map((data) => (
        <Review key={data.reviewId} data={data}/>
      ))}
    </>
  );
};

export default Reviews;
