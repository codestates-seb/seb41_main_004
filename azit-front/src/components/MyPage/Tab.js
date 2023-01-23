import { useState } from "react";
import styled from "styled-components";
import ActivityHistory from "./ActivityHistory";
import Reviews from "./Reviews";

// Styled-Component 라이브러리를 활용해 TabMenu 와 Desc 컴포넌트의 CSS를 구현.

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

const Desc = styled.div`
  background-color: var(--background-color);
  min-height: calc(100vh - 47.5rem);
  padding: 2rem 2rem 10rem;
`;

const Tab = ({ myPage }) => {
  // Tab Menu 중 현재 어떤 Tab이 선택되어 있는지 확인하기 위한 currentTab 상태와 currentTab을 갱신하는 함수가 존재해야 하고, 초기값은 0.
  const [currentTab, clickTab] = useState(0);

  const menuArr = [
    { name: "활동내역", content: <ActivityHistory /> },
    { name: "리뷰보기", content: <Reviews /> },
  ];

  const selectMenuHandler = (index) => {
    // parameter로 현재 선택한 인덱스 값을 전달해야 하며, 이벤트 객체(event)는 쓰지 않는다
    // 해당 함수가 실행되면 현재 선택된 Tab Menu 가 갱신.
    clickTab(index);
  };

  return (
    <>
      <div>
        <TabMenu>
          {/* // 아래 하드코딩된 내용 대신에, map을 이용한 반복으로 코드를 수정
         // li 엘리먼트의 class명의 경우 선택된 tab 은 'submenu focused', 나머지 2개의 tab은 'submenu'  */}
          {/* <li className="submenu">{menuArr[0].name}</li>
          <li className="submenu">{menuArr[1].name}</li>
          <li className="submenu">{menuArr[2].name}</li> */}
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
      </div>
    </>
  );
};

export default Tab;
