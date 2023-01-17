import styled from "styled-components";
import LoadingGif from "../../images/loading.gif";

const LoadingWrap = styled.article`
    position: relative;
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
`
const Loading = () => {
  return (
    <LoadingWrap>
      <img alt="LoadingImg" src={LoadingGif} />
    </LoadingWrap>
  );
};

export default Loading;
