import styled from "styled-components";
import Gnb from "../components/common/Gnb";
import Header from "../components/common/HeaderTypeGnb";
import searchIcon from "../images/searchIcon.png";
import searchBackIcon from "../images/searchBackIcon.png";
import AzitList from "../components/common/AzitList";
import { ClubData } from "../dummyData/ClubData";
import { useState } from "react";
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
  top:0;
  left:50%;
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
`

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
        <FxiedHeader>
          <Header title="아지트 검색" />
          <article className="searchCell">
            <input
              placeholder="아지트를 검색해주세요."
              onKeyUp={(e) => {
                searchControlHandler(e);
              }}
            ></input>
          </article>
        </FxiedHeader>
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
