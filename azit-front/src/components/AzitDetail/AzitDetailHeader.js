import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import reportIcon from "../../images/reportIcon.png";
import editIcon from "../../images/edit-icon.png";

const HeaderWrap = styled.header`
  width: 100%;
  max-width: 50rem;
  height: 5.5rem;
  background-color: var(--white-color);
  position: fixed;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid var(--border-color);
  z-index: 99;
  > button {
    position: absolute;
    left: 10px;
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

const ReportIcon = styled.img.attrs({ src: `${reportIcon}` })`
  position: absolute;
  right: 2rem;
  width: 2.4rem;
`;

const EditIcon = styled.img.attrs({ src: `${editIcon}` })`
  position: absolute;
  right: 2rem;
  width: 2.4rem;
`;

const AzitDetailHeader = ({ clubData }) => {
  const navigate = useNavigate();
  const nowUser = localStorage.getItem("nickname");

  return (
    <HeaderWrap>
      <button onClick={() => navigate(-1)}>
        <span />
      </button>
      {clubData ? (
        nowUser === clubData.host.nickname ? (
          <EditIcon></EditIcon>
        ) : (
          <ReportIcon></ReportIcon>
        )
      ) : (
        <></>
      )}
    </HeaderWrap>
  );
};

export default AzitDetailHeader;
