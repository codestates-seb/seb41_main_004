import styled from "styled-components";
import { AzitMemberData } from "../../dummyData/AzitMemberData";
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
  padding:2rem 0;
  color:var(--sub-font-color);
`;

const WaitingMember = () => {
  return (
    <WaitingWrap>
      <div className="title">
        <h3>가입 멤버</h3>
        <span className="count">{AzitMemberData.length}</span>
      </div>
      <ul className="list">
        {AzitMemberData.length > 0 && AzitMemberData ? (
          AzitMemberData?.map((data) => {
            return <MemberList key={data.userId} data={data} state="member" />;
          })
        ) : (
          <Null>아지트 멤버가 없습니다.</Null>
        )}
      </ul>
    </WaitingWrap>
  );
};

export default WaitingMember;
