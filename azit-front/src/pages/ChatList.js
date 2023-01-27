import Header from "../components/common/HeaderTypeGnb";
import Gnb from "../components/common/Gnb";
import Tab from "../components/ChatList/Tab";
import { useState } from "react";
import { IncompleteModal } from "../components/common/Modal";
import { useEffect } from "react";

const ChatList = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
    setModalOpen(!modalOpen);
  };
  useEffect(() => {
    setModalOpen(true);
  }, []);
  return (
    <>
      {modalOpen && <IncompleteModal modalHandler={modalHandler} />}
      <Header title="참여중인 채팅" />
      <Gnb />
      <Tab />
    </>
  );
};

export default ChatList;
