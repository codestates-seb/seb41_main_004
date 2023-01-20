import styled from "styled-components";
import AzitCreateForm from "../components/AzitCreate/AzitCreateForm";
import Header from "../components/common/Header";
import BannerImg from "../images/BannerImg.png";
import { useState, useRef } from "react";

const AzitaddWrap = styled.div`
  display: flex;
  flex-direction: column;
  > div {
    position: relative;
    > input {
      display: none;
    }

    > label {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      color: var(--point-color);
      text-align: center;
    }
  }
`;

const ImgWrap = styled.div`
  width: 100%;
  height: 20rem;
  margin-top: 5.5rem;
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
`;

const AzitCreate = () => {
  const [imgFile, setImgFile] = useState("");
  const imgRef = useRef();

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  };

  return (
    <AzitaddWrap>
      <Header title="아지트 생성" />
      <div>
        <input
          type="file"
          accept="image/jpg, image/jpeg, image/png"
          id="bannerImg"
          onChange={saveImgFile}
          ref={imgRef}
        ></input>
        <ImgWrap imgSrc={imgFile ? imgFile : BannerImg}></ImgWrap>
        <label className="bannerImgLabel" htmlFor="bannerImg"></label>
      </div>
      <AzitCreateForm imgFile={imgFile} />
    </AzitaddWrap>
  );
};

export default AzitCreate;
