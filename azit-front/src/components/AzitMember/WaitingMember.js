import { useState } from "react";
import styled from "styled-components";
import MemberList from "./MemberList";

const WaitingWrap = styled.article`
  > .title {
    display: flex;
    padding: 20px;
    > h3 {
      font-size: var(--big-font);
      margin-right: 0.5rem;
      font-weight: var(--bold-weight);
    }
    > .count {
      font-size: var(--big-font);
      font-weight: var(--bold-weight);
      color: var(--point-color);
    }
    > .viewBtn {
      width: 2rem;
      height: 2rem;
      display: flex;
      justify-content: center;
      align-items: center;
      border: none;
      background-color: transparent;
      margin: 0;
      padding: 0;
      margin-left: auto;
      transition: 0.3s all;
      &.active {
        transform: rotate(180deg);
      }
      > .arrow {
        display: block;
        width: 10px;
        height: 10px;
        border-bottom: 2px solid var(--sub-font-color);
        border-right: 2px solid var(--sub-font-color);
        transform: rotate(45deg);
      }
    }
  }
  > .watingList {
    padding: 0 2rem;
    max-height: 0;
    transition: 2s all cubic-bezier(0, 1, 0, 1);
    overflow: hidden;
    &.active {
      max-height: fit-content;
      padding: 0 2rem 2rem;
      transition: 2s all cubic-bezier(0, 1, 0, 1);
    }
  }
`;

const Null = styled.div`
  text-align: center;
  padding: 2rem 0;
  color: var(--sub-font-color);
`;

const WaitingMember = ({ waitingMembers }) => {
  const [view, setView] = useState(false);
  const viewHandler = () => {
    setView(!view);
  };
  return (
    <WaitingWrap>
      <div className="title">
        <h3>가입 대기 멤버</h3>
        <span className="count">{waitingMembers.length}</span>
        <button
          className={view ? "viewBtn active" : "viewBtn"}
          onClick={() => {
            viewHandler();
          }}
        >
          <span className="arrow" />
        </button>
      </div>
      <ul className={view ? "watingList active" : "watingList"}>
        {waitingMembers.length > 0 && waitingMembers ? (
          waitingMembers?.map((data) => {
            return (
              <MemberList
                key={data.member.memberId}
                data={data}
                state={data.clubMemberStatus}
              />
            );
          })
        ) : (
          <Null>가입 대기 멤버가 없습니다.</Null>
        )}
      </ul>
    </WaitingWrap>
  );
};

export default WaitingMember;
