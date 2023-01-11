import Gnb from "../components/Gnb";
import UserPageHeader from "../components/UserPageHeader";
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
