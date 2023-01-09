import styled from "styled-components";
import Gnb from "../components/Gnb";
import Header from "../components/HeaderTypeGnb";

const SearchWrap = styled.section`
    >.searchArticle {
        padding: 10px 20px 20px;
    }
`;
const Search = () => {
  return (
    <>
      <Gnb />
      <SearchWrap>
        <Header title="아지트 검색" />
        <article className="searchArticle">
          <input placeholder="아지트를 검색해주세요."></input>
        </article>
        <article></article>
      </SearchWrap>
    </>
  );
};

export default Search;
