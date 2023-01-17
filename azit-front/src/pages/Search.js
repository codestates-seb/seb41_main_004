import styled from "styled-components";
import Gnb from "../components/common/Gnb";
import Header from "../components/common/HeaderTypeGnb";
import searchIcon from "../images/searchIcon.png";
import searchBackIcon from "../images/searchBackIcon.png";
import AzitList from "../components/common/AzitList";
import { useState } from "react";
import { useQuery } from "react-query";
import { axiosInstance } from "../util/axios";
import Loading from "../components/common/Loading";

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
  const [value, setValue] = useState("");

  const fetchList = async (keyword) => {
    try {
      const res = await axiosInstance.get(
        `/api/clubs/search?page=1&size=10&keyword=${keyword}`
      );
      return res.data;
    } catch (error) {
      console.log(error.response.data);
    }
  };
  
  const {
    isLoading,
    isError,
    data: searchList,
    error,
  } = useQuery(["search", value], () => fetchList(value), {
    // input에 검색어가 입력되지 않으면 query가 호출되지 않는다.
    enabled: !!value,
    staleTime: 6 * 10 * 1000,
    cacheTime: 6 * 10 * 1000,
  });
  console.log(searchList)
  const changeValue = (e) => {
    setValue(e.target.value);
  };

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
          {isLoading && <Loading /> }
          {isError && <p>Error: {error.message}</p>}
          {searchList?.data?.length > 0 ? (
            searchList.data.map((data) => (
              <AzitList key={data.clubId} data={data} />
            ))
          ) : (
            <div className="default">
              <img alt="searchIcon" src={searchBackIcon} />
              <p>{value.length > 0 ? '검색결과가 없습니다.' : "검색어를 입력해 주세요."}</p>
            </div>
          )}
        </article>
      </SearchWrap>
    </>
  );
};

export default Search;
