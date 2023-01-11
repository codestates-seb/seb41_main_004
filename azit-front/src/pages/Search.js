import styled from "styled-components";
import Gnb from "../components/Gnb";
import Header from "../components/HeaderTypeGnb";
import searchIcon from "../images/searchIcon.png";
import searchBackIcon from "../images/searchBackIcon.png";
import AzitList from "../components/AzitList";
import { ClubData } from "../dummyData/ClubData";
import { useState } from "react";
const SearchWrap = styled.section`
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
  > .resultCell {
    min-height: calc(100vh - 13rem);
    background-color: var(--background-color);
    padding: 2rem 2rem 10rem;
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
const Search = () => {
  const [SearchControl, setSearchControl] = useState(false);
  const searchControlHandler = (e) => {
    if (e.key === "Enter" && e.target.value.length > 0) {
      setSearchControl(true);
    } else if (e.key === "Enter" && e.target.value.length === 0) {
      setSearchControl(false);
    }
  };
  return (
    <>
      <Gnb />
      <SearchWrap>
        <Header title="아지트 검색" />
        <article className="searchCell">
          <input
            placeholder="아지트를 검색해주세요."
            onKeyUp={(e) => {
              searchControlHandler(e);
            }}
          ></input>
        </article>
        <article className="resultCell">
          {SearchControl ? (
            ClubData ? (
              ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
            ) : (
              <div className="default">
                <img alt="searchIcon" src={searchBackIcon} />
                <p>검색결과가 없습니다.</p>
              </div>
            )
          ) : (
            <div className="default">
              <img alt="searchIcon" src={searchBackIcon} />
              <p>아지트 이름을 검색해보세요.</p>
            </div>
          )}
        </article>
      </SearchWrap>
    </>
  );
};

export default Search;
