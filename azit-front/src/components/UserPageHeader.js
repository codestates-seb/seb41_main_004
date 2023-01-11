import { NavLink } from "react-router-dom";
import styled from "styled-components";
import editIcon from "../images/edit-icon.png";

const HeaderWrap = styled.header`
  height: 5.5rem;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 2rem;
  border-bottom: 1px solid var(--border-color);
`;
const Header = styled.div``;
const Edit = styled.span``;
const Img = styled.img`
  width: 2rem;
  height: 2rem;
`;

const UserPageHeader = () => {
  return (
    <HeaderWrap>
      <Header />
      <NavLink to="/profile">
        <Edit>프로필 수정 </Edit>&nbsp;&nbsp;&nbsp;{" "}
      </NavLink>
      <NavLink to="/userpage/setting">
        <Img alt="editIcon" src={editIcon} />
      </NavLink>
    </HeaderWrap>
  );
};

export default UserPageHeader;
