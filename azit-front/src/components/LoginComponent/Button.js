import styled from "styled-components"

export const Button = styled.button`
  all: unset;
  width: 100%;
  height: 5.5rem;
  margin-top: 2rem;
  text-align: center;
  border-radius: 0.5rem;
  background-color: ${props => props.backgroundColor || "var(--point-color)"};
  border-color: ${props => props.borderColor || "var(--border-color)"};
  color: ${props => props.color || "var(--white-color)"};
  font-size: ${props => props.fontSize || "var(--main-font)"};
  cursor: pointer;
  :disabled {
    background-color: var(--border-color);
    color: var(--light-font-color);
    cursor: auto;
  }
  :hover:enabled {
    background-color: var(--hover-color);
  }
`
