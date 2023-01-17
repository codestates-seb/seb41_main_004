import axios from "axios";
import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
// import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";
// import { Link, useParams } from "react-router-dom";
import { interests } from "../dummyData/Category";
import { useEffect, useRef, useState } from "react";
import { ImgModal } from "../components/common/Modal";
//import { useSelector } from "react-redux";
const URL = process.env.REACT_APP_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
//console.log(accessToken);

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
  > input {
    // 프로필이미지의 이미지첨부 input창 숨김
    display: none;
  }
`;

const ProfileImage = styled.div`
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
`;

const ImageAddIcon = styled.img`
  cursor: pointer;
  margin-top: -2rem;
  margin-left: 5rem;
`;

const UserProfileEdit = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const [checkedInputs, setCheckedInputs] = useState([]);
  const [nameValue, SetNameValue] = useState("");
  const [introValue, setIntroValue] = useState("");
  const [imgFile, setImgFile] = useState("");
  const [defaultName, SetdefaultName] = useState(""); //이름 get으로 받아오는것
  const [intro, setIntro] = useState(""); //소개 get으로 받아오는것
  const [profileImg, setprofileImg] = useState(""); //이미지 get으로 받아오는것
  const [category, setCategory] = useState(""); //관심사 get으로 받아오는것

  const imgRef = useRef();

  const saveImgFile = async (e) => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };

    const formData = new FormData();
    formData.append("file", file);

    axios
      .post(`${URL}api/members/1`, formData, {
        headers: {
          Authorization: accessToken,
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
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
  // console.log(file);

  useEffect(() => {
    axios
      .get(`${URL}api/members/1`, {
        headers: {
          Authorization: accessToken,
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res);
        SetdefaultName(res.data.data.nickname);
        setIntro(res.data.data.email);
        // setprofileImg(res.data.fileInfo.fileName);
      })
      .catch((error) => {
        console.log("error : ", error);
        // alert("중복된 닉네임입니다. 다시 시도하세요.");
        //alert창은 axios patch에서 에러뜨면 해야됨
      });
  }, []);

  const modalHandler = () => {
    setModalOpen(!modalOpen);
  };
  const changeHandler = (checked, id) => {
    //console.log(checked, id); //true , '전시'
    if (checked) {
      setCheckedInputs([...checkedInputs, id]);
    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
    }
    console.log(checkedInputs);
  };

  const handleEdit = (e) => {
    e.preventDefault();
    let body = {
      nickname: nameValue,
      aboutMe: introValue,
      fileName: imgFile,
    };
    console.log(body);
  };

  return (
    <>
      <Header title="프로필 수정" />
      <ProfileEditForm>
        <ProfileImageWrap>
          <input
            type="file"
            accept="jpg, jpeg, png"
            id="profileImg"
            onChange={saveImgFile}
            ref={imgRef}
          ></input>
          <ProfileImage imgSrc={imgFile ? imgFile : profileImg}></ProfileImage>
          <label className="profileImgLabel" htmlFor="profileImg">
            <ImageAddIcon
              // onClick={() => modalHandler()}
              src={ImgAddIcon}
            ></ImageAddIcon>
          </label>
          {/* {modalOpen && <ImgModal modalHandler={modalHandler} />} */}
        </ProfileImageWrap>
        <article>
          <label>닉네임</label>
          {/*input에 onChange 이벤트 적용 필요 / 서버 데이터에서 닉네임 불러오기 필요*/}
          <input
            onChange={(e) => {
              SetNameValue(e.target.value);
            }}
            defaultValue={defaultName}
          ></input>
        </article>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea
            onChange={(e) => {
              setIntroValue(e.target.value);
            }}
            placeholder="텍스트를 입력해 주세요."
            defaultValue={intro}
          ></textarea>
        </article>
        <article>
          <div className="title">관심사</div>
          {interests.map((interest) => {
            return (
              <div className="interestContainer" key={interest.id}>
                <div className="subtitle">{interest.subtitle}</div>
                <div className="tagContainer">
                  {interest.tags.map((tag, idx) => {
                    //console.log(tag, idx);
                    //전시,영화,뮤지컬,공연,디자인 등등,  0,1,2,3,4,5
                    return (
                      <span className="tag" key={idx}>
                        <input
                          id={tag}
                          type="checkbox"
                          onChange={(e) => {
                            console.log(e);
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
        {/* <Link to="/"> */}
        <form onSubmit={handleEdit}>
          <Button
            type="submit"
            title="수정 완료"
            state="active"
            // onClick={() => {
            //   handleEdit();
            // }}
          ></Button>
        </form>
        {/* </Link> */}
      </ProfileEditForm>
    </>
  );
};

export default UserProfileEdit;
