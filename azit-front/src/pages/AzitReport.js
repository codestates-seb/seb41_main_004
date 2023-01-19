import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useState } from "react";
import { ReportData } from "../dummyData/ReportData";
import { axiosInstance } from "../util/axios";
import { useNavigate, useParams } from "react-router-dom";
import { useMutation } from "react-query";

const ReportWrap = styled.form`
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 7.5rem 2rem 2rem;
  min-height: 100vh;
  > .ReportForm {
    flex: 1;
    > div {
      > label span {
        font-size: var(--caption-font);
        margin-left: 0.2rem;
        color: var(--light-font-color);
        &.essential {
          color: var(--point-color);
        }
      }
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
`;

const AzitReport = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [reportCategory, setReportCategory] = useState(ReportData[0].value);
  const [reportReason, setReportReason] = useState("");
  const handleReportCategory = (e) => {
    setReportCategory(e.target.value);
  };
  const handleReportReason = (e) => {
    setReportReason(e.target.value);
  };
  const posting = async (e) => {
    e.preventDefault();
    try {
      const payload = { reportCategory, reportReason };
      // const res = 
      await axiosInstance.post(
        `/api/clubs/reports/${id}`,
        payload,
        {
          headers: { Authorization: localStorage.getItem("accessToken") },
        }
      );
      // console.log(res);
      navigate(-1);
    } catch (error) {
      alert("신고 내용을 보내지 못했습니다.")
      // console.log(error);
    }
  };
  const { mutate } = useMutation(posting);
  return (
    <ReportWrap onSubmit={mutate}>
      <Header title="아지트 신고"></Header>
      <div className="ReportForm">
        <div>
          <label>
            사유 선택
            <span className="essential">(필수)</span>
          </label>
          <div className="selectBox">
            <select onChange={handleReportCategory} value={reportCategory}>
              {ReportData.map((item) => (
                <option value={item.value} key={item.id}>
                  {item.name}
                </option>
              ))}
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div>
          <label>
            신고내용<span>(선택)</span>
          </label>
          <textarea
            maxLength={128}
            onChange={handleReportReason}
            value={reportReason}
          ></textarea>
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
      <Button
        state={reportCategory.length > 0 ? "active" : "disabled"}
        title="신고 하기"
      />
    </ReportWrap>
  );
};

export default AzitReport;
