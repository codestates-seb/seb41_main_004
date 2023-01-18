import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import editIcon from "../../images/edit-icon.png";
import reportIcon from "../../images/reportIcon.png";

const HeaderWrap = styled.header`
  height: 5.5rem;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 2rem;
  border-bottom: 1px solid var(--border-color);
`;
const Header = styled.div``;
const Edit = styled.span`
  font-size: var(--caption-font);
`;
const Img = styled.img.attrs({ src: `${editIcon}` })`
  width: 3rem;
  height: 3rem;
`;
const ReportIcon = styled.img.attrs({ src: `${reportIcon}` })`
  width: 3rem;
  height: 3rem;
`;

const UserPageHeader = () => {
  const [myPage, setMyPage] = useState(true);

  return (
    <HeaderWrap>
      <Header />
      <Link to="/userpage/edit">
        {myPage ? (
          <Edit>프로필 수정</Edit>
        ) : (
          <Link to="/userpage/report">
            <ReportIcon alt="reportIcon" />
          </Link>
        )}
      </Link>
      <Link to="/userpage/setting">{myPage ? <Img alt="editIcon" /> : ""}</Link>
    </HeaderWrap>
  );
};

export default UserPageHeader;
