import styled from "styled-components";
import AzitEditForm from "../components/AzitEdit/AzitEditForm";
import Header from "../components/Header";
import Button from "../components/Button";

const AzitEditWrap = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  > .buttonWrap {
    padding: 2rem;
  }
`;

const AzitEdit = () => {
  return (
    <AzitEditWrap>
      <Header title="아지트 수정" />
      <AzitEditForm />
      <div className="buttonWrap">
        <Button state="disabled" title="수정완료" />
      </div>
    </AzitEditWrap>
  );
};

export default AzitEdit;
