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
  }
  > .list {
    padding: 0 2rem 2rem;
    overflow: hidden;
  }
`;

const Null = styled.div`
  text-align: center;
  padding: 2rem 0;
  color: var(--sub-font-color);
`;

const WaitingMember = ({ joinMembers }) => {
  return (
    <WaitingWrap>
      <div className="title">
        <h3>가입 멤버</h3>
        <span className="count">{joinMembers.length}</span>
      </div>
      <ul className="list">
        {joinMembers.length > 0 && joinMembers ? (
          joinMembers?.map((data) => {
            return (
              <MemberList
                key={data.memberId}
                data={data}
                state={data.clubMemberStatus}
              />
            );
          })
        ) : (
          <Null>아지트 멤버가 없습니다.</Null>
        )}
      </ul>
    </WaitingWrap>
  );
};

export default WaitingMember;
