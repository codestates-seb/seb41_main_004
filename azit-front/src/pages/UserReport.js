import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { UserReportData } from "../dummyData/UserReportData";
import useAxios from "../util/useAxios";
import { useMutation } from "react-query";

const ReportWrap = styled.form`
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
const Select = styled.select`
  color: ${(props) =>
    props.status !== "" ? "var(--font-color)" : `var(--light-font-color)`};
`;

const UserReport = () => {
  const { id } = useParams();
  const axiosInstance = useAxios();
  const navigate = useNavigate();
  const [reportList, setReportList] = useState(UserReportData[0].value);
  const [reportReason, setReportReason] = useState("");

  const handleReportList = (e) => {
    setReportList(e.target.value);
  };
  const handleReportReason = (e) => {
    setReportReason(e.target.value);
  };

  const posting = async (e) => {
    e.preventDefault();
    try {
      const payload = { reportList, reportReason };
      await axiosInstance.post(`api/members/reports/${id}`, payload, {
        headers: { Authorization: localStorage.getItem("accessToken") },
      });
      navigate(-1);
      alert("신고가 완료되었습니다.");
      console.log("보내짐");
    } catch (error) {
      alert(error.message);
    }
  };
  const { mutate } = useMutation(posting);

  return (
    <ReportWrap onSubmit={mutate}>
      <Header title="멤버 신고하기"></Header>
      <div className="ReportForm">
        <div>
          <label>신고 항목</label>
          <div className="selectBox">
            <Select
              status={reportList}
              onChange={handleReportList}
              value={reportList}
            >
              {UserReportData.map((item) => (
                <option value={item.value} key={item.id}>
                  {item.name}
                </option>
              ))}
            </Select>
            <span className="selectArrow" />
          </div>
        </div>
        <div>
          <label>신고내용(선택)</label>
          <textarea
            maxLength={128}
            onChange={handleReportReason}
            placeholder="신고 사유를 간략하게 입력해 주세요.(128자 제한)"
          ></textarea>
        </div>
        <div></div>
      </div>
      {/* <Link to="/userpage"> */}
      <Button
        title="신고 하기"
        state={reportList.length > 0 ? "active" : "disabled"}
      ></Button>
      {/* </Link> */}
    </ReportWrap>
  );
};

export default UserReport;
