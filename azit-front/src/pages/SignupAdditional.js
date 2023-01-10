import styled from "styled-components";
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
`

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
  margin-top: -2.7rem;
  margin-left: 5rem;
`

const SignupAdditional = () => {
  return (
    <>
      <Header title="회원가입"/>
        <SignupForm>
          <ProfileImageWrap>
            <ProfileImage src={BasicProfileImgIcon}></ProfileImage>
            <ImageAddIcon src={ImgAddIcon}></ImageAddIcon>
          </ProfileImageWrap>
          <label>자기소개를 입력해주세요.</label>
          <textarea placeholder="텍스트를 입력해 주세요."></textarea>
          <label>나이 및 성별</label>
          <select>
            <option value>남</option>
            <option>여</option>
          </select>
        </SignupForm>
    </>
  )
}

export default SignupAdditional;