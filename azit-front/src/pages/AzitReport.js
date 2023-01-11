import styled from "styled-components";
import Header from "../components/Header";
import Button from "../components/Button";
import { useState } from "react";

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
      }
      > li {
        margin-left: 0.6rem;
        font-size: var(--caption-font);
        color: var(--light-font-color);
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

const AzitReport = () => {
  const reportList = ["사유1", "사유2", "사유3", "사유4", "사유5"];
  const [reportSelected, setReportSelected] = useState("사유1");
  const reportHandleSelect = (e) => {
    setReportSelected(e.target.value);
  };

  return (
    <ReportWrap>
      <Header title="아지트 신고"></Header>
      <div className="ReportForm">
        <div>
          <label>사유 선택</label>
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
          <label>신고내용</label>
          <textarea></textarea>
        </div>
        <div>
          <li>
            정상적인 게시물을 신고하는 경우 본인이 제재를 당할 수 있습니다.
          </li>
          <li>
            신고하게 된 이유를 자세히 써 주시면 운영자의 결정에 도움이 됩니다.
          </li>
        </div>
      </div>
      <div className="buttonWrap">
        <Button state="disabled" title="신고 하기" />
      </div>
    </ReportWrap>
  );
};

export default AzitReport;
