import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";
import { useLocation, useNavigate } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import { interests } from "../dummyData/Category";
import { SignupYear } from "../dummyData/SignupYear";
import { axiosInstance } from "../util/axios";



const SignupAdditional = () => {
  const navigate = useNavigate()
  const {state} = useLocation()
  const [data, setData] = useState(state)
  const [checkedInputs, setCheckedInputs] = useState([]);
  const [aboutMe, setAboutMe] = useState("");
  const [imgFile, setImgFile] = useState("");
  const [birthYear, setBirthYear] = useState(SignupYear()[0])
  const [gender, setGender] = useState("MALE")
  const imgRef = useRef();
  
  useEffect(() => {
    setData((data) => ({...data, categorySmallId: checkedInputs, aboutMe, birthYear, gender}))
  },[checkedInputs, aboutMe, birthYear, gender])

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  };
  
  const changeCategoryHandler = (checked, id) => {
    if (checked) {
      setCheckedInputs([...checkedInputs, id]);
    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
    }
    if (checkedInputs.length >= 12) {
      alert("관심사는 최대 12개까지 선택 가능합니다.");
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
      checked = false;
    }
  };

  const handleData = (e) => {
    // console.log(e.target.id)
    if(e.target.id === "aboutMe") {
      setAboutMe(e.target.value);
    } else if(e.target.id === "birthYear") {
      setBirthYear(e.target.value);
    } else if(e.target.id === "gender") {
      setGender(e.target.value);
    }
  }
  
  // 이미지 base 64 를 image.file로 변환하는 함수
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

  const signUp = async (e) => {
    e.preventDefault()
    const formData = new FormData();
    let postImgFile = dataURLtoFile(imgFile, "sendImg");
    formData.append("image", postImgFile);
    formData.append("data", new Blob([JSON.stringify(data)], { type: "application/json"}))
    try {
      // const res =  
      await axiosInstance.post(`/api/members`, formData, {
        "Content-Type": "multipart/form-data",
      });

      alert("회원가입을 성공하였습니다.")
      navigate("/login", { replace: true })
      // console.log(res)
    } catch (e) {
      // console.log("회원가입을 실패하였습니다.")
      alert(e.message)
    }
  }
  return (
    <>
      <Header title="회원가입" />
      <SignupForm onSubmit={signUp}>
        <ProfileImageWrap>
            <input
            type="file"
            accept="image/jpg, image/jpeg, image/png"
            id="profileImg"
            onChange={saveImgFile}
            ref={imgRef}
            />
          <ProfileImage imgSrc={imgFile ? imgFile : BasicProfileImgIcon} />
          <label className="profileImgLabel" htmlFor="profileImg">
            <img alt="imgEditBtn" src={ImgAddIcon} />
          </label>
        </ProfileImageWrap>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea 
          id="aboutMe"
          onChange={(e) => handleData(e)} value={aboutMe} maxLength={128} placeholder="텍스트를 입력해 주세요."></textarea>
        </article>
        <article className="genderAge">
          <label>나이 및 성별</label>
          <div className="selectWrap">
            <div className="selectBox">
              <select
              id="birthYear"
                onChange={(e) => handleData(e)}
                value={birthYear}
              >
                {SignupYear().map((item) => (
                <option key={item}>{item}</option>
                ))}
              </select>
              <span className="selectArrow" />
            </div>
            <div className="selectBox">
              <select
              id="gender"
              onChange={(e) => handleData(e)}
              value={gender}
              >
                <option value="MALE">남</option>
                <option value="FEMALE">여</option>
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
                  {interest.tags.map((tag) => {
                    return (
                      <span className="tag" key={tag.tagId}>
                        <input
                          id={tag.tagId}
                          type="checkbox"
                          onChange={(e) => {
                            changeCategoryHandler(e.currentTarget.checked, tag.tagId);
                          }}
                          checked={checkedInputs.includes(tag.tagId) ? true : false}
                          name={tag}
                        ></input>
                        <label htmlFor={tag.tagId}>{tag.tagName}</label>
                      </span>
                    );
                  })}
                </div>
              </div>
            );
          })}
        </article>
            <Button title="회원가입" state={imgFile && aboutMe && checkedInputs.length > 0 ? "active" : "disabled"}></Button>
        {/* <div className="buttonWrap">
          <Link to="/login">
          </Link>
        </div> */}
      </SignupForm>
    </>
  );
};

const SignupForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items:center;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  position: relative;
  /* justify-content: space-between; */
  > .genderAge {
    margin-right:auto;
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
    width:100%;
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
  & button {
    margin-top: 3rem;
  }
`;

const ProfileImageWrap = styled.div`
  position:relative;
  > input {
    display:none;
  }
  > label {
    position: absolute;
    margin: 0;
    bottom: 0;
    right: 0;
    height:2.7 rem;
    cursor:pointer;
  }
`;

const ProfileImage = styled.div`
  background-image: url(${(props) => props.imgSrc});
  background-size:cover;
  background-position: center center;
  background-repeat : no-repeat;
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
`;



export default SignupAdditional;
