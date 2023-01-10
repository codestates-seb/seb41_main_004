import styled from "styled-components";

const ButtonWrap = styled.button`
  height: 55px;
  background-color: var(--point-color);
  font-size: var(--big-font);
  color: var(--white-color);
  border-radius: 5px;
  border: none;
  margin-right: 2rem;
  margin-left: 2rem;
`;

const Button = ({ title }) => {
  return <ButtonWrap>{title}</ButtonWrap>;
};

export default Button;
