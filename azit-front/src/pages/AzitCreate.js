import styled from "styled-components";
import AzitCreateForm from "../components/AzitCreate/AzitCreateForm";
import Header from "../components/common/Header";
import BannerImg from "../images/BannerImg.png";
import { useState, useRef } from "react";

const AzitaddWrap = styled.div`
  display: flex;
  flex-direction: column;
  > div {
    > input {
      display: none;
    }
    > img {
      width: 100%;
      height: 19.23rem;
      margin-top: 5.5rem;
    }
    > label {
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

  function dataURLtoFile(dataurl, filename) {
    if (!dataurl) {
      return;
    } else {
      var arr = dataurl.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }

      return new File([u8arr], filename, { type: mime });
    }
  }

  let file = dataURLtoFile(imgFile, "sendImg");
  console.log(file);

  return (
    <AzitaddWrap>
      <Header title="아지트 생성" />
      <div>
        <input
          type="file"
          accept="jpg, jpeg, png"
          id="bannerImg"
          onChange={saveImgFile}
          ref={imgRef}
        ></input>
        <ImgWrap imgSrc={imgFile ? imgFile : BannerImg}></ImgWrap>
        {/* <img alt="banner-img" src={imgFile ? imgFile : BannerImg}></img> */}
        <label className="bannerImgLabel" htmlFor="bannerImg">
          배너 이미지 추가
        </label>
      </div>
      <AzitCreateForm />
    </AzitaddWrap>
  );
};

export default AzitCreate;
