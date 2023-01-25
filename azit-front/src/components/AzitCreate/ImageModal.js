import styled from "styled-components";
import Cropper from "react-cropper";
import "cropperjs/dist/cropper.css";

export const ImageModal = ({
  resetImgFile,
  modalHandler,
  imgFile,
  saveImgFile,
  imgRef,
  onCrop,
}) => {
  return (
    <>
      <ImageModalWrap>
        <div className="modal">
          <input
            type="file"
            accept="image/jpg, image/jpeg, image/png"
            id="bannerImg"
            onChange={saveImgFile}
            ref={imgRef}
          ></input>
          {imgFile ? (
            <Cropper
              src={imgFile}
              crop={onCrop}
              ref={imgRef}
              style={{ height: 300, width: "100%" }}
            />
          ) : (
            <label className="bannerImgLabel" htmlFor="bannerImg" />
          )}
          <div className="buttonWrap">
            <button onClick={() => modalHandler()}>취소</button>
            <button className="accept" onClick={resetImgFile}>
              확인
            </button>
          </div>
        </div>
        <div className="background" onClick={() => modalHandler()} />
      </ImageModalWrap>
    </>
  );
};

const ImageModalWrap = styled.div`
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.25);
  }
  > .modal {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    height: 40rem;
    min-width: 60rem;
    > .buttonWrap {
      margin: 2rem 0;
      display: flex;
      justify-content: center;
      gap: 1rem;
      > button {
        cursor: pointer;
        border: none;
        border-radius: 0.5rem;
        background-color: var(--border-color);
        color: var(--white-color);
        width: 15rem;
        height: 4.2rem;
      }
      > .accept {
        cursor: pointer;
        border: none;
        border-radius: 0.5rem;
        background-color: var(--point-color);
        color: var(--white-color);
        width: 15rem;
        height: 4.2rem;
        :hover {
          background-color: var(--hover-color);
        }
      }
    }
    > input {
      margin-top: 3.5rem;
      padding-left: 4.2rem;
      display: none;
    }
    > label {
      width: 50rem;
      height: 20rem;
      border: 1px dashed var(--border-color);
      border-radius: 0.5rem;
    }
  }
`;
