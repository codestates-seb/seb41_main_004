import styled from "styled-components";
import { Link, useNavigate, useParams } from "react-router-dom";
import reportIcon from "../../images/reportIcon.png";
import editIcon from "../../images/edit-icon.png";

const HeaderWrap = styled.header`
  width: 100%;
  max-width: 50rem;
  padding:0 2rem;
  height: 5.5rem;
  background-color: var(--white-color);
  position: fixed;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--border-color);
  z-index: 99;
  > button {
    /* position: absolute;
    left: 10px; */
    width: 3rem;
    height: 3rem;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: transparent;
    border: none;
    cursor: pointer;
    > span {
      width: 10px;
      height: 10px;
      border-top: 2px solid var(--font-color);
      border-left: 2px solid var(--font-color);
      transform: rotate(-45deg);
    }
  }
`;
const IconWrap = styled.div`
  width: 2.4rem;
  img {
    max-width: 100%;
  }
`

const AzitDetailHeader = ({ clubData }) => {
  const navigate = useNavigate();
  const nowUser = localStorage.getItem("memberId");
  const { id } = useParams();

  return (
    <HeaderWrap>
      <button onClick={() => navigate(-1)}>
        <span />
      </button>
      <IconWrap>
        {clubData ? (
          nowUser === String(clubData.host.memberId) ? (
            <Link to={`/azit/edit/${id}`}>
              <img alt="EditIcon" src={editIcon} />
            </Link>
          ) : (
            <Link to={`/azit/report/${id}`}>
            <img alt="ReportIcon" src={reportIcon} />
            </Link>
          )
        ) : (
          <></>
        )}
      </IconWrap>
    </HeaderWrap>
  );
};

export default AzitDetailHeader;
