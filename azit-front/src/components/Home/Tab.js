import { useState } from "react";
import styled from "styled-components";
import RecommendTab from "./RecommendTab";

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
    width: calc(100% / 3);
    padding: 10px;
    justify-content: center;
    align-items: center;
    transition: 0.5s;
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
  background-color: var(--background-color);
  min-height: calc(100vh - 35.5rem);
  padding:2rem 2rem 10rem;
`;

const Tab = () => {
  const [currentTab, clickTab] = useState(0);

  const menuArr = [
    { name: "추천", content: <RecommendTab /> },
    { name: "카테고리", content: <RecommendTab /> },
    { name: "날짜별", content: <RecommendTab /> },
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
