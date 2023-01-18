import { useState } from "react";
import styled from "styled-components";
import ChatListWrap from "./ChatListWrap";
import {ChatData} from "../../dummyData/ChatData"

const TabMenu = styled.ul`
  color: var(--light-font-color);
  font-weight: var(--bold-weight);
  display: flex;
  font-size: var(--big-font);
  flex-direction: row;
  height: 5rem;

  .submenu {
    position: relative;
    display: flex;
    width: calc(100% / 2);
    padding: 10px;
    justify-content: center;
    align-items: center;
    transition: 0.5s;
    cursor: pointer;
  }
  .submenu::after {
    content: "";
    display: block;
    width: calc(100% - 4px);
    height: 2px;
    background-color: var(--light-font-color);
    position: absolute;
    bottom: 0;
    transition: 0.5s;
  }

  .focused {
    color: var(--font-color);
  }
  .focused::after {
    content: "";
    background-color: var(--font-color);
  }
`;

const Desc = styled.article`  
  padding:0 0 10rem;
`;

const Tab = () => {
  const [currentTab, clickTab] = useState(0);
    
  const menuArr = [
    { name: "아지트", content: <ChatListWrap data={ChatData}/> },
    { name: "개인", content: <ChatListWrap data={""}/> },
  ];

  const selectMenuHandler = (index) => {
    clickTab(index);
  };

  return (
    <>
      <TabMenu>
        {menuArr.map((el, index) => (
          <li
            key={index}
            className={index === currentTab ? "submenu focused" : "submenu"}
            onClick={() => selectMenuHandler(index)}
          >
            {el.name}
          </li>
        ))}
      </TabMenu>
      <Desc>{menuArr[currentTab].content}</Desc>
    </>
  );
};

export default Tab;
