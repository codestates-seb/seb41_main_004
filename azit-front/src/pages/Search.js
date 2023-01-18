import styled from "styled-components";
import Gnb from "../components/common/Gnb";
import Header from "../components/common/HeaderTypeGnb";
import searchIcon from "../images/searchIcon.png";
import AzitList from "../components/common/AzitList";
import React, { useEffect, useState } from "react";
import { useInfiniteQuery } from "react-query";
import { axiosInstance } from "../util/axios";
import Loading from "../components/common/Loading";
import { useInView } from "react-intersection-observer";
import Default from "../components/Search/Defalut";

const SearchWrap = styled.section`
  > .resultCell {
    min-height: 100vh;
    background-color: var(--background-color);
    padding: 15rem 2rem 10rem;
    > .default {
      padding-top: 8rem;
      text-align: center;
      > img {
        width: 8rem;
        height: 8rem;
      }
      > p {
        margin-top: 2rem;
        color: var(--sub-font-color);
      }
    }
  }
`;
const FxiedHeader = styled.div`
  position: fixed;
  z-index: 99;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  max-width: 50rem;
  background-color: var(--white-color);
  > .searchCell {
    padding: 1rem 2rem 2rem;
    > input {
      background-image: url(${searchIcon});
      background-repeat: no-repeat;
      background-size: 2.4rem;
      background-position: 1rem center;
      padding: 1.5rem 1.5rem 1.5rem 4rem;
    }
  }
`;

const Search = () => {
  const [value, setValue] = useState(null);
  const { ref, inView } = useInView();

  const changeValue = (e) => {
    if (e.target.value.length === 0) {
      setValue(null);
    } else {
      setValue(e.target.value);
    }
  };

  const fetchInfiniteList = async (pageParam, keyword) => {
    const res = await axiosInstance.get(
      `/api/clubs/search?page=${pageParam}&size=10&keyword=${keyword}`
    );
    return {
      // 실제 데이터
      board_page: res.data.data,
      nextPage: pageParam + 1,
      // 페이지가 마지막인지 알려주는 서버에서 넘겨준 true/false 값
      isLast:
        res.data.data.length > 0
          ? res.data.pageInfo.page === res.data.pageInfo.totalPages
          : true,
    };
  };
  
  const { data, status, fetchNextPage, isFetchingNextPage } = useInfiniteQuery(
    ["get", value],
    ({ pageParam = 1 }) => fetchInfiniteList(pageParam, value),
    {
      enabled: !!value,
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
      <Gnb />
      <SearchWrap>
        <FxiedHeader>
          <Header title="아지트 검색" />
          <article className="searchCell">
            <input
              placeholder="아지트를 검색해주세요."
              onChange={(e) => {
                changeValue(e);
              }}
            ></input>
          </article>
        </FxiedHeader>
        <article className="resultCell">
          {status === "loading" && <Loading />}
          {status === "idle" && <Default text="검색어를 입력해 주세요." />}
          {status === "error" && <Default text="Not Defined Error" status="error"/>}
          {data?.pages.map((page, index) => (
            <React.Fragment key={index}>
              {page.board_page.length > 0
                ? page.board_page.map((searchData) => (
                    <AzitList key={searchData.clubId} data={searchData} />
                  ))
                : <Default text="검색 결과가 없습니다." />}
            </React.Fragment>
          ))}
        </article>
        {isFetchingNextPage ? <Loading /> : <div ref={ref} />}
      </SearchWrap>
    </>
  );
};

export default Search;
