import styled from "styled-components";

const HeaderWrap = styled.header`
  height: 5.5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 2rem;
  > h2 {
    font-size: var(--title-font);
    font-weight: var(--bold-weight);
  }
`;
const Header = styled.div`
  display: flex;
`;
const Edit = styled.span`
  display: flex;
  justify-content: end;
`;
const MyPage_Header = () => {
  return (
    <HeaderWrap>
      <Header>
        <Edit>프로필 수정</Edit>
      </Header>
    </HeaderWrap>
  );
};

export default MyPage_Header;
