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
const Img = styled.img`
  width: 3rem;
  height: 3rem;
`;
const ReportIcon = styled.img.attrs({ src: `${reportIcon}` })`
  width: 3rem;
  height: 3rem;
`;

const UserPageHeader = () => {
  return (
    <HeaderWrap>
      <Header />
      <Link to="/userpage/edit">
        <Edit>프로필 수정 </Edit>&nbsp;&nbsp;&nbsp;{" "}
      </Link>
      <Link to="/userpage/setting">
        <Img alt="editIcon" src={editIcon} />
      </Link>
      <Link to="/userpage/report">
        <ReportIcon alt="reportIcon" />
      </Link>
    </HeaderWrap>
  );
};

export default UserPageHeader;
