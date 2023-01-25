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
  > a {
    font-size: var(--caption-font);
  }
  > a:last-child {
    width: 3rem;
    height: 3rem;
    margin-left: 1rem;
    > img {
      width: 100%;
    }
  }
`;

const UserPageHeader = ({ myPage, id }) => {
  // console.log(myPage);
  return (
    <HeaderWrap>
      {myPage ? (
        <>
          <Link to={`/userpage/edit/${id}`}>프로필 수정</Link>
          <Link to={`/userpage/setting/${id}`}>
            <img alt="editIcon" src={editIcon} />
          </Link>
        </>
      ) : (
        <Link to={`/userpage/report/${id}`}>
          <img alt="reportIcon" src={reportIcon} />
        </Link>
      )}
    </HeaderWrap>
  );
};

export default UserPageHeader;
