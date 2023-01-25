import styled from "styled-components";

const NullWrap = styled.article`
  padding: 8rem 0 0;
  text-align: center;
  color: var(--sub-font-color);
`;

const Null = ({text}) => {
    return (
        <NullWrap>{text}</NullWrap>
    )
}

export default Null