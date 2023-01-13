import styled from "styled-components";
import LogoWhite from "../images/logoWhite.png";

const LoadingContainer = styled.div`
  background-color: var(--point-color);
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  & > img {
    width: 16.182rem;
    height: 7rem;
  }
`

const Loading = () => {
  return (
    <>
      <LoadingContainer>
        <img alt="logoWhite" src={LogoWhite}></img>
      </LoadingContainer>
    </>
  )
}

export default Loading;