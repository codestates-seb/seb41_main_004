import styled from "styled-components";
import AzitEditForm from "../components/AzitEdit/AzitEditForm";
import Header from "../components/Header";
import Button from "../components/Button";

const AzitEditWrap = styled.div`
  display: flex;
  flex-direction: column;
  > Button {
    margin-top: 9rem;
  }
`;

const AzitEdit = () => {
  return (
    <AzitEditWrap>
      <Header title="아지트 수정" />
      <AzitEditForm />
      <Button title="수정완료" />
    </AzitEditWrap>
  );
};

export default AzitEdit;
