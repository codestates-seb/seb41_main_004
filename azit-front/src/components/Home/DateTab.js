import AzitList from "../common/AzitList";
import React, { useEffect, useState } from "react";
import DatePicker from "./DatePicker";
import Null from "./Null";
import { useInView } from "react-intersection-observer";
import { axiosInstance } from "../../util/axios";
import { useInfiniteQuery } from "react-query";
import Loading from "../common/Loading";

const DateTab = () => {
  const [days, setDays] = useState(0);
  const { ref, inView } = useInView();
  const handleDays = (num) => {
    setDays(num);
  };

  const fetchInfiniteList = async (pageParam, days) => {
    const res = await axiosInstance.get(
      `/api/clubs/date?page=${pageParam}&size=5&days=${days}`
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
    ["get", days],
    ({ pageParam = 1 }) => fetchInfiniteList(pageParam, days),
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
      <DatePicker days={days} handleDays={handleDays} />
      <article className="resultCell">
        {status === "loading" && <Loading />}
        {status === "error" && <Null text="Not Defined Error" />}
        {data?.pages.map((page, index) => (
          <React.Fragment key={index}>
            {page.board_page.length > 0 ? (
              page.board_page.map((daysData) => (
                <AzitList key={daysData.clubId} data={daysData} />
              ))
            ) : (
              <Null text="해당 날짜의 아지트가 없습니다." />
            )}
          </React.Fragment>
        ))}
      </article>
      {isFetchingNextPage ? <Loading /> : <div ref={ref} />}
    </>
  );
};

export default DateTab;
