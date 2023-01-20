import Gnb from "../components/common/Gnb";
import UserPageHeader from "../components/MyPage/UserPageHeader";
import Profile from "../components/MyPage/Profile.js";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

const UserPage = () => {
  const [myPage, setMyPage] = useState(false);
  const { id } = useParams();
  useEffect(() => {
    id === localStorage.getItem("memberId")
      ? setMyPage(true)
      : setMyPage(false);
  }, [id]);

  return (
    <>
      <UserPageHeader myPage={myPage} id={id} />
      <Profile myPage={myPage} id={id} />
      <Gnb></Gnb>
    </>
  );
};

export default UserPage;
