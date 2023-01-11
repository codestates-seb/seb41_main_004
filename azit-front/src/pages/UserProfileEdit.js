import styled from "styled-components";
import Button from "../components/Button";
import Header from "../components/Header";
import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";
import { Link } from "react-router-dom";

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
        padding-top: 0.5rem;
        & > .tag {
          border: solid 0.1rem var(--border-color);
          border-radius: 2rem;
          padding: 0.7rem 1rem 0.7rem 1rem;
          margin-right: 1rem;
          text-align: center;
        }
        & > .clicked {
          border: solid 0.1rem var(--point-color);
          border-radius: 2rem;
          padding: 0.7rem 1rem 0.7rem 1rem;
          margin-right: 1rem;
          text-align: center;
          color: var(--white-color);
          background-color: var(--point-color);
        }
      }
    }
  }
  & > .buttonWrap {
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
`

const ProfileImage = styled.img`
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
`

const ImageAddIcon = styled.img`
  cursor: pointer;
  margin-top: -2.7rem;
  margin-left: 5rem;
`

const interests = [
  {
    id: 1,
    subtitle: "문화/예술",
    tags: [
      "전시", "영화" , "뮤지컬" , "공연" , "디자인"
    ]
  },
  {
    id: 2,
    subtitle: "운동/액티비티",
    tags: [
      "클라이밍", "등산" , "헬스" , "필라테스" , "골프"
    ]
  },
  {
    id: 3,
    subtitle: "푸드/드링크",
    tags: [
      "맛집투어", "카페" , "와인" , "커피" , "디저트"
    ]
  },
  {
    id: 4,
    subtitle: "취미",
    tags: [
      "보드게임", "사진" , "방탈출" , "VR" , "음악감상"
    ]
  },
  {
    id: 5,
    subtitle: "여행/동행",
    tags: [
      "복합문화공간", "테마파크" , "피크닉" , "드라이브" , "캠핑"
    ]
  },
  {
    id: 6,
    subtitle: "창작",
    tags: [
      "글쓰기", "드로잉" , "영상편집" , "공예" , "DIY"
    ]
  },
  {
    id: 7,
    subtitle: "성장/자기계발",
    tags: [
      "습관만들기", "챌린지" , "독서" , "스터디" , "외국어"
    ]
  },
]

const UserProfileEdit = () => {

  return (
    <>
      <Header title="프로필 수정" />
      <ProfileEditForm>
        <ProfileImageWrap>
          <ProfileImage src={BasicProfileImgIcon}></ProfileImage>
          <ImageAddIcon src={ImgAddIcon}></ImageAddIcon>
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
            return  <div className="interestContainer" key={interest.id}>
                      <div className="subtitle">{interest.subtitle}</div>
                      <div className="tagContainer">
                        {interest.tags.map((tag, idx) => {
                          return <span key={idx} className="tag">{tag}</span> 
                        })}
                      </div>
                    </div> 
              }
            )
          }
        </article>
        {/*Link -> useNavigate 로 변환 필요 / */}
        <div className="buttonWrap"></div>
        <Link to="/">
          <Button title="수정 완료" state="active"></Button>
        </Link>
      </ProfileEditForm>
    </>
  );
}

export default UserProfileEdit;