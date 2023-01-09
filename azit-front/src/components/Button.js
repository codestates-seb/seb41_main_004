import styled from "styled-components";

const ButtonWrap = styled.button`
  width: 350px;
  height: 55px;
  background-color: var(--point-color);
  font-size: var(--big-font);
  color: var(--white-color);
  border-radius: 5px;
`;

const Button = ({ title }) => {
  return <ButtonWrap>{title}</ButtonWrap>;
};

export default Button;
