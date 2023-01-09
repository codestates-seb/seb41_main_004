import styled from "styled-components";
import { useNavigate } from "react-router-dom";
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
  > h2 {
    display: inline-block;
    font-size: var(--big-font);
    font-weight: var(--bold-weight);
  }
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

const Header = ({ title }) => {
  const navigate = useNavigate();
  return (
    <HeaderWrap>
      <button onClick={() => navigate(-1)}>
        <span />
      </button>
      <h2>{title ? title : "test"}</h2>
    </HeaderWrap>
  );
};

export default Header;
