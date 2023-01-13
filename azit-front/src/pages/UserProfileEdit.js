import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";
import { Link } from "react-router-dom";
import { interests } from "../dummyData/Category";
import { useState } from "react";
import { ImgModal } from "../components/common/Modal";

const ProfileEditForm = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  /* justify-content: space-between; */
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
            padding: 0.5rem 1.5rem;
            margin: 0;
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

const UserProfileEdit = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const [checkedInputs, setCheckedInputs] = useState([]);
  const modalHandler = () => {
    setModalOpen(!modalOpen);
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
      <Header title="프로필 수정" />
      <ProfileEditForm>
        <ProfileImageWrap>
          <ProfileImage src={BasicProfileImgIcon}></ProfileImage>
          <ImageAddIcon
            onClick={() => modalHandler()}
            src={ImgAddIcon}
          ></ImageAddIcon>
          {modalOpen && <ImgModal modalHandler={modalHandler} />}
        </ProfileImageWrap>
        <article>
          <label>닉네임</label>
          {/*input에 onChange 이벤트 적용 필요 / 서버 데이터에서 닉네임 불러오기 필요*/}
          <input defaultValue="김아무개"></input>
        </article>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea placeholder="텍스트를 입력해 주세요."></textarea>
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
        {/*Link -> useNavigate 로 변환 필요 / */}
        <div className="buttonWrap"></div>
        <Link to="/">
          <Button title="수정 완료" state="active"></Button>
        </Link>
      </ProfileEditForm>
    </>
  );
};

export default UserProfileEdit;
