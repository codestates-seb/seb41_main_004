import styled from "styled-components";
import Header from "../components/common/Header";
import Button from "../components/common/Button";
import { useQuery, useMutation } from "react-query";
import Loading from "../components/common/Loading";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axiosInstance from "../util/axios";

const JoinWrap = styled.div`
  position: relative;
  padding: 7.5rem 2rem 2rem;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  > .JoinForm {
    flex: 1;
    > div {
      > div {
        padding-left: 1.7rem;
        padding-top: 0.85rem;
        height: 4rem;
        background-color: var(--background-color);
        border-radius: 0.5rem;
        margin-bottom: 2rem;
      }
    }

    > Button {
      position: absolute;
      bottom: 0;
      max-width: 46rem;
      margin-bottom: 2rem;
    }
  }
`;

const EtcWrap = styled.div`
  width: 100%;
  height: 100vh;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const AzitJoin = () => {
  const { id } = useParams();
  const [joinQuestion, setJoinQuestion] = useState("");
  const [joinAnswer, setJoinAnswer] = useState("");

  const azitQuestion = async () => {
    const res = await axiosInstance.get(`api/clubs/${id}/join-question`,);
    return res.data.data;
  };

  const { isError, isLoading, data, error } = useQuery(
    "azitQuestion",
    azitQuestion
  );

  useEffect(() => {
    if (data) {
      setJoinQuestion(data.joinQuestion);
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }
  }, [data]);

  const navigate = useNavigate();
  const memberId = localStorage.getItem("memberId");

  let body = {
    memberId,
    joinAnswer,
  };

  const azitJoin = async (e) => {
    e.preventDefault();
    try {
      const payload = body;

      await axiosInstance.post(`api/clubs/${id}/signups`, payload,);
      alert("아지트 참가 신청이 완료되었습니다");
      navigate(-1);
    } catch (error) {
      const statusCode = error.response.status;
      if (statusCode === 409) {
        return alert("이미 가입했던 아지트입니다.");
      } else {
        alert(error.message);
      }
    }
  };

  const { mutate } = useMutation(azitJoin);

  return (
    <JoinWrap>
      {isError && <EtcWrap>{error.message}</EtcWrap>}
      {isLoading && (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      )}
      {data && (
        <>
          <Header title="참여 신청" />
          <form className="JoinForm" onSubmit={mutate}>
            <div>
              <label>호스트의 질문</label>
              <div>{joinQuestion}</div>
            </div>
            <div>
              <label>참여 답변</label>
              <input
                placeholder="답변을 입력해주세요."
                onChange={(e) => setJoinAnswer(e.target.value)}
              ></input>
            </div>
            {joinAnswer ? (
              <Button state="active" title="신청 하기" onSubmit={mutate} />
            ) : (
              <Button state="disabled" title="신청 하기" />
            )}
          </form>
        </>
      )}
    </JoinWrap>
  );
};

export default AzitJoin;
