import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useState } from "react";
import { Link } from "react-router-dom";

const ReportWrap = styled.div`
  position: relative;
  margin-top: 5.5rem;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 5.5rem);
  justify-content: space-between;
  > .ReportForm {
    height: 100%;
    > div {
      > textarea {
        margin-bottom: 0.4rem;
        color: var(--light-font-color);
      }
      > li {
        margin-left: 0.6rem;
        font-size: var(--caption-font);
        color: var(--border-color);
      }
    }
    > div:first-child {
      margin-bottom: 2rem;
    }
  }
`;

const UserReport = () => {
  const reportList = ["사유1", "사유2", "사유3", "사유4", "사유5"];
  const [reportSelected, setReportSelected] = useState("사유1");
  const reportHandleSelect = (e) => {
    setReportSelected(e.target.value);
  };

  return (
    <ReportWrap>
      <Header title="멤버 신고하기"></Header>
      <div className="ReportForm">
        <div>
          <label>신고 항목</label>
          <div className="selectBox">
            <select onChange={reportHandleSelect} value={reportSelected}>
              {reportList.map((item) => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div>
          <label>신고내용(선택)</label>
          <textarea placeholder="신고 사유를 간략하게 입력해 주세요."></textarea>
        </div>
        <div></div>
      </div>
      <Link to="/userpage">
        <Button title="신고 하기" state="active"></Button>
      </Link>
    </ReportWrap>
  );
};

export default UserReport;
