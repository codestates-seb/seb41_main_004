import styled from "styled-components";
import AzitCreateForm from "../components/AzitCreate/AzitCreateForm";
import Header from "../components/common/Header";
import BannerImg from "../images/BannerImg.png";
import { useState, useRef } from "react";
import { ImageModal } from "../components/AzitCreate/ImageModal";

const AzitaddWrap = styled.div`
  display: flex;
  flex-direction: column;
  > div {
    position: relative;

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
  const [imgFile, setImgFile] = useState(null);
  const [croppedImage, setCroppedImage] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const imgRef = useRef();

  const modalHandler = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
  };

  const resetImgFile = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
    setCroppedImage(null);
  };

  const acceptImgFile = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
    setImgFile(null);
  };

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    if (file.size > 1 * 1024 * 1024) {
      alert("1MB 미만의 파일을 첨부해주세요");
    } else {
      const reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setImgFile(reader.result);
      };
    }
  };

  const onCrop = () => {
    if (imgFile) {
      const imageElement = imgRef?.current;
      const cropper = imageElement?.cropper;
      setCroppedImage(cropper.getCroppedCanvas().toDataURL());
    }
  };

  return (
    <AzitaddWrap>
      <Header title="아지트 생성" />
      <div>
        {modalOpen && (
          <ImageModal
            modalHandler={modalHandler}
            resetImgFile={resetImgFile}
            saveImgFile={saveImgFile}
            acceptImgFile={acceptImgFile}
            imgRef={imgRef}
            onCrop={onCrop}
            imgFile={imgFile}
            setImgFile={setImgFile}
            setCroppedImage={setCroppedImage}
          ></ImageModal>
        )}
        <ImgWrap imgSrc={croppedImage ? croppedImage : BannerImg}></ImgWrap>
        <label className="bannerImgLabel" onClick={modalHandler}></label>
      </div>
      <AzitCreateForm croppedImage={croppedImage} />
    </AzitaddWrap>
  );
};

export default AzitCreate;
