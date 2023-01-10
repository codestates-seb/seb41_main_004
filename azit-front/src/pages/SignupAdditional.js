import styled from "styled-components";
import { Button } from "../components/LoginComponent/Button";
import Header from "../components/Header";
import BasicProfileImgIcon from "../images/basicProfileImgIcon.png";
import ImgAddIcon from "../images/imgAddIcon.png";

const SignupForm = styled.div`
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

const SignupAdditional = () => {

  return (
    <>
      <Header title="회원가입" />
      <SignupForm>
        <ProfileImageWrap>
          <ProfileImage src={BasicProfileImgIcon}></ProfileImage>
          <ImageAddIcon src={ImgAddIcon}></ImageAddIcon>
        </ProfileImageWrap>
        <article>
          <label>자기소개를 입력해주세요.</label>
          <textarea placeholder="텍스트를 입력해 주세요."></textarea>
        </article>
        <article>
          <label>나이 및 성별</label>
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
        <Button disabled={true}>회원가입</Button>
      </SignupForm>
    </>
  );
}

export default SignupAdditional;