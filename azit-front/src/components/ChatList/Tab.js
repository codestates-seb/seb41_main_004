import { useState } from "react";
import styled from "styled-components";
import ChatWrap from "./ChatWrap";
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
  min-height: calc(100vh - 10.5rem); 
  padding:2rem 2rem 10rem;
`;

const Tab = () => {
  const [currentTab, clickTab] = useState(0);
    
  const menuArr = [
    { name: "아지트", content: <ChatWrap data={ChatData}/> },
    { name: "개인", content: <ChatWrap data={ChatData}/> },
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
