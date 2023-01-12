import Header from "../components/common/Header";
import WaitingMember from "../components/AzitMember/WaitingMember";
import styled from "styled-components";
import Member from "../components/AzitMember/Member";

const MemberWrap = styled.section`
    padding-top: 55px;
    >article:first-child {
        border-bottom :10px solid var(--background-color);
    }
`;
const AzitMember = () => {
  return (
    <>
      <Header title="아지트 멤버 관리" />
      <MemberWrap>
        <WaitingMember />
        <Member />
      </MemberWrap>
    </>
  );
};

export default AzitMember;
