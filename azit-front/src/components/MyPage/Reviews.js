import styled from "styled-components";
import { toDateFormatOfKR } from "../../util/toDateFormatOfKR";
// import { useEffect } from "react";
// import { useInView } from "react-intersection-observer";
// import { useInfiniteQuery } from "react-query";
// import { useParams } from "react-router-dom";
// import { axiosInstance } from "../../util/axios";

const Container = styled.div`
  width: 100%;
  height: 100%;
  background-color: #f1f1f1;
`;
const Box = styled.div`
  padding: 1.5rem;
  background-color: #ffffff;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  :last-child {
    margin: 0;
  }
`;
const TitleBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border-bottom: solid 1px #d9d9d9;
`;
const Title = styled.p`
  font-weight: bold;
  font-size: var(--big-font);
`;
const Time = styled.span`
  color: var(--sub-font-color);
  padding-top: 1rem;
  padding-bottom: 1rem;
`;
const ChoiceReview = styled.p`
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-weight: bold;
`;
const ReviewBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;
const Contents = styled.p`
  word-break: keep-all;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  color: var(--sub-font-color);
`;

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

  // 무한스크롤 함수 0번이 guest
  // const fetchInfiniteList = async (pageParam) => {
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
  //     ({ pageParam = 1 }) => fetchInfiniteList(pageParam),
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

  return (
    <Container>
      <Box>
        <TitleBox>
          <Title>모임이름모임이름모임이름모임이름모임이름</Title>
          <Time>{` ${toDateFormatOfKR(new Date())}`}</Time>
        </TitleBox>
        <ReviewBox>
          <ChoiceReview>배려심이 있어요!</ChoiceReview>
          <Contents>
            가나다라마바사아자차카타파하 가나다라마바사아자차카타파하
            가나다라마바사아자차카타파하가나다라마바사아자차카타파하
            가나다라마바사아자차카타파하가나다라마바사아자차카타파하
            가나다라마바사아자차카타파하가나다라마바사아자차카타파하
          </Contents>
        </ReviewBox>
      </Box>
    </Container>
  );
};

export default Reviews;
