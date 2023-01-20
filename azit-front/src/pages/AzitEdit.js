import styled from "styled-components";
import AzitEditForm from "../components/AzitEdit/AzitEditForm";
import Header from "../components/common/Header";

const AzitEditWrap = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

const AzitEdit = () => {
  return (
    <AzitEditWrap>
      <Header title="아지트 수정" />
      <AzitEditForm />
    </AzitEditWrap>
  );
};

export default AzitEdit;
