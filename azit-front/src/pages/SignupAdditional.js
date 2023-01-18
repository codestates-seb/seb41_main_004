import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import { ImgModal } from "../components/common/Modal";
import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";
import { Link } from "react-router-dom";
import { useState, useRef } from "react";
import { interests } from "../dummyData/Category";



const SignupAdditional = () => {

  const [checkedInputs, setCheckedInputs] = useState([]);
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


  const changeHandler = (checked, id) => {
    if (checked) {
      setCheckedInputs([...checkedInputs, id]);
    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
    }
  };

  return (
    <>
      <Header title="회원가입" />
      <SignupForm>
        <ProfileImageWrap>
          <ProfileImage src={BasicProfileImgIcon}></ProfileImage>
          <ImageAddIcon src={ImgAddIcon}>
            <input
            type="file"
            accept="jpg, jpeg, png"
            id="bannerImg"
            onChange={saveImgFile}
            ref={imgRef}
            />
          </ImageAddIcon>
          {/* {modalOpen && <ImgModal modalHandler={modalHandler} />} */}
        </ProfileImageWrap>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea placeholder="텍스트를 입력해 주세요."></textarea>
        </article>
        <article className="genderAge">
          <label>나이 및 성별</label>
          <div className="selectWrap">
            <div className="selectBox">
              <select>
                <option>2023</option>
                {/*생년 구하는 함수 필요*/}
              </select>
              <span className="selectArrow" />
            </div>
            <div className="selectBox">
              <select>
                <option>남</option>
                <option>여</option>
                <option>제한 없음</option>
              </select>
              <span className="selectArrow" />
            </div>
          </div>
        </article>
        <article>
          <div className="title">관심사</div>
          {interests.map((interest) => {
            return (
              <div className="interestContainer" key={interest.id}>
                <div className="subtitle">{interest.subtitle}</div>
                <div className="tagContainer">
                  {interest.tags.map((tag, idx) => {
                    return (
                      <span className="tag" key={idx}>
                        <input
                          id={tag}
                          type="checkbox"
                          onChange={(e) => {
                            changeHandler(e.currentTarget.checked, tag);
                          }}
                          checked={checkedInputs.includes(tag) ? true : false}
                          name={tag}
                        ></input>
                        <label htmlFor={tag}>{tag}</label>
                      </span>
                    );
                  })}
                </div>
              </div>
            );
          })}
        </article>
        <div className="buttonWrap">
          <Link to="/login">
            <Button title="회원가입" state="active"></Button>
          </Link>
        </div>
      </SignupForm>
    </>
  );
};

const SignupForm = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  position: relative;
  /* justify-content: space-between; */
  > .genderAge {
    width: 40%;
    > .selectWrap {
      display: flex;
      > .selectBox {
        flex-basis: 50%;
      }
      > .selectBox:first-child {
        margin-right: 1rem;
      }
    }
  }
  & > article {
    margin-top: 2rem;
    & > .selectBox {
      margin-bottom: 1rem;
    }
    & > .title {
      font-size: var(--title-font);
      font-weight: var(--bold-weight);
    }
    & > .interestContainer {
      margin-bottom: 2rem;
      padding-top: 0.5rem;
      & > .subtitle {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
        margin: 1rem 0rem 1rem;
      }
      & > .tagContainer {
        display: flex;
        padding-top: 0.5rem;
        flex-wrap: wrap;

        & > .tag {
          margin-right: 1rem;
          text-align: center;
          margin-bottom: 1rem;

          > label {
            border: solid 0.1rem var(--border-color);
            border-radius: 2rem;
            padding:.5rem 1.5rem;
            margin:0;
            cursor: pointer;
          }
          > input[type="checkbox"]:checked + label {
            background-color: var(--point-color);
            border: solid 0.1rem var(--border-color);
            color: var(--white-color);
          }
          > input {
            display: none;
          }
        }
      }
    }
  }
  & > .buttonWrap {
    margin-top: 3rem;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    justify-content: flex-end;
  }
`;

const ProfileImageWrap = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ProfileImage = styled.img`
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
`;

const ImageAddIcon = styled.img`
  cursor: pointer;
  margin-top: -2.7rem;
  margin-left: 5rem;
`;

export default SignupAdditional;
