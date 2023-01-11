import styled from "styled-components";

const ButtonWrap = styled.button`
width: 100%;
  height: 55px;
  font-size: var(--big-font);
  border-radius: 5px;
  border: none;
  margin:0;
  padding: 0;
  cursor: pointer;
  transition:0.5s all;
  &.active {
    background-color: var(--point-color);
    color: var(--white-color);
  }
  &.disabled {
    background-color: var(--border-color);
    color: var(--light-font-color);
    pointer-events: none;
  }
  :hover {
    background-color: var(--hover-color);
  }
`;

const Button = ({ title, state }) => {
  return <ButtonWrap className={state}>{title}</ButtonWrap>;
};

export default Button;
