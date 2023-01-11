import styled from "styled-components";
import Header from "../components/Header";
import Button from "../components/Button";
import { useState } from "react";
import { Link } from "react-router-dom";

const ReportWrap = styled.div`
  position: relative;
  margin-right: 2rem;
  margin-left: 2rem;
  display: flex;
  flex-direction: column;
  height: 100vh;
  > .ReportForm {
    height: 100%;
    margin-top: 7.5rem;
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
  > .buttonWrap {
    margin-bottom: 2rem;
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
      <div className="buttonWrap">
        <Link to="/userpage">
          <Button title="신고 하기" state="active"></Button>
        </Link>
      </div>
    </ReportWrap>
  );
};

export default UserReport;
