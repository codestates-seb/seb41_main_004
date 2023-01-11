import Header from "../components/HeaderTypeGnb";
import Gnb from "../components/Gnb";
import Tab from "../components/ChatList/Tab";

const ChatList = () => {
  return (
    <>
      <Header title="참여중인 채팅" />
      <Gnb />
      <Tab />
    </>
  );
};

export default ChatList;
