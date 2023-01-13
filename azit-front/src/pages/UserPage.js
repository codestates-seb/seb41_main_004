import Gnb from "../components/common/Gnb";
import UserPageHeader from "../components/MyPage/UserPageHeader";
import Profile from "../components/MyPage/Profile.js";

const UserPage = () => {
  return (
    <>
      <UserPageHeader />
      <Profile />
      <Gnb></Gnb>
    </>
  );
};

export default UserPage;
