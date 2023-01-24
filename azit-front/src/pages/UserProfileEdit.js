import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import ImgAddIcon from "../images/imgAddIcon.png";
import { interests } from "../dummyData/Category";
import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { axiosInstance } from "../util/axios";
import { useFirstRender } from "../util/useDidMountEffect";

const accessToken = localStorage.getItem("accessToken");

const ProfileEditForm = styled.form`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  margin-top: 5.5rem;
  min-height: calc(100vh - 5.5rem);
  align-items: center;
  /* justify-content: space-between; */
  & > article {
    margin-top: 2rem;
    width: 100%;
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
  & button {
    margin-top: 3rem;
  }
`;

const ProfileImageWrap = styled.div`
  position: relative;
  > input {
    // 프로필이미지의 이미지첨부 input창 숨김
    display: none;
  }
  > label {
    position: absolute;
    margin: 0;
    bottom: 0;
    right: 0;
    height: 2.7rem;
    cursor: pointer;
  }
`;

const ProfileImage = styled.div`
  background-image: url(${(props) => props.imgSrc});
  background-size: cover;
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
  background-color: var(--background-color);
`;

const UserProfileEdit = () => {
  const { id } = useParams();
  const [checkedInputs, setCheckedInputs] = useState([]);
  const [imgFile, setImgFile] = useState("");
  const [getImgFile, setGetImgFile] = useState(""); //이미지 get으로 받아오는것
  const [defaultName, SetdefaultName] = useState(""); //이름 get으로 받아오는것
  const [intro, setIntro] = useState(""); //소개 get으로 받아오는것
  const imgRef = useRef();
  const navigate = useNavigate();

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  };

  // 이미지 변환 함수
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

  const firstRender = useFirstRender();
  useEffect(() => {
    if (!firstRender && imgFile) {
      let file = dataURLtoFile(imgFile, "sendImg");
      const formData = new FormData();
      formData.append("image", file);
      const postFile = async () => {
        try {
          const res = await axiosInstance.post(`api/members/${id}`, formData, {
            headers: {
              Authorization: accessToken,
              "Content-Type": "multipart/form-data",
            },
          });
          localStorage.setItem("profileUrl", res.data.data.fileInfo.fileUrl);
          localStorage.setItem("profileName", res.data.data.fileInfo.fileName);

          alert("프로필 이미지 수정이 완료되었습니다.");
        } catch (e) {
          alert("프로필 이미지 수정이 실패되었습니다.");
        }
      };
      postFile();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [firstRender, imgFile]);

  useEffect(() => {
    const startGet = async () => {
      await axiosInstance
        .get(`api/members/${id}`, {
          headers: {
            Authorization: accessToken,
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          SetdefaultName(res.data.data.nickname);
          setIntro(res.data.data.aboutMe);
          setGetImgFile(
            `${process.env.REACT_APP_S3_URL}${res.data.data.fileInfo.fileUrl}/${res.data.data.fileInfo.fileName}`
          );

          let categoryList = [];
          res.data.data.categorySmallIdList.map((category) => {
            return categoryList.push(category);
          });
          setCheckedInputs(categoryList);
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    };
    startGet();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const changeHandler = (checked, id) => {
    //console.log(checked, id); //true , '전시'
    if (checked) {
      setCheckedInputs([...checkedInputs, id]);
    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
    }
  };

  const handleEdit = (e) => {
    e.preventDefault();
    let body = {
      nickname: defaultName,
      aboutMe: intro,
      // fileName: imgFile,
      categorySmallId: checkedInputs,
    };

    axiosInstance
      .patch(`api/members/${id}`, body, {
        headers: {
          Authorization: accessToken,
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        if (res.status >= 200 && res.status < 300) {
          navigate(`/userpage/${id}`);
        }
        console.log(res);
      })
      .catch((error) => {
        alert("닉네임이 중복되었습니다. 다시 시도하세요.");
      });
  };

  return (
    <>
      <Header title="프로필 수정" />
      <ProfileEditForm onSubmit={handleEdit}>
        <ProfileImageWrap>
          <input
            type="file"
            accept="image/jpg,image/jpeg,image/png"
            //리액트에서는 image/를 해줘야 특정 파일형식만 업로드되게 적용됨
            id="profileImg"
            onChange={saveImgFile}
            ref={imgRef}
          ></input>
          <ProfileImage
            imgSrc={imgFile ? imgFile : `${getImgFile}`}
          ></ProfileImage>
          <label className="profileImgLabel" htmlFor="profileImg">
            <img alt="imgEditBtn" src={ImgAddIcon} />
          </label>
          {/* {modalOpen && <ImgModal modalHandler={modalHandler} />} */}
        </ProfileImageWrap>
        <article>
          <label>닉네임</label>
          {/*input에 onChange 이벤트 적용 필요 / 서버 데이터에서 닉네임 불러오기 필요*/}
          <input
            onChange={(e) => {
              SetdefaultName(e.target.value);
            }}
            defaultValue={defaultName}
          ></input>
        </article>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea
            onChange={(e) => {
              setIntro(e.target.value);
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
                  {interest.tags.map((tag) => {
                    //console.log(tag, idx);
                    //전시,영화,뮤지컬,공연,디자인 등등,  0,1,2,3,4,5
                    return (
                      <span className="tag" key={tag.tagId}>
                        <input
                          id={tag.tagId}
                          type="checkbox"
                          onChange={(e) => {
                            changeHandler(e.currentTarget.checked, tag.tagId);
                          }}
                          checked={
                            checkedInputs.includes(tag.tagId) ? true : false
                          }
                          name={tag.tagName}
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
        <Button type="submit" title="수정 완료" state="active"></Button>
      </ProfileEditForm>
    </>
  );
};

export default UserProfileEdit;
