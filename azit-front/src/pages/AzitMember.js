import Header from "../components/common/Header";
import WaitingMember from "../components/AzitMember/WaitingMember";
import styled from "styled-components";
import Member from "../components/AzitMember/Member";
import { axiosInstance } from "../util/axios";
import { useQuery } from "react-query";
import Loading from "../components/common/Loading";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const MemberWrap = styled.section`
  padding-top: 55px;
  > article:first-child {
    border-bottom: 10px solid var(--background-color);
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

const AzitMember = () => {
  const { id } = useParams();
  const [waitingMembers, setWaitingMembers] = useState([]);
  const [joinMembers, setJoinMembers] = useState([]);

  const azitMembers = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}/signups`, {
      headers: {
        Authorization: localStorage.getItem("accessToken"),
        "Content-Type": "application/json",
      },
    });
    return res.data.data;
  };

  const { isError, isLoading, data, error } = useQuery(
    "azitMembers",
    azitMembers
  );

  useEffect(() => {
    if (data) {
      let filterJoinMember = data.filter((member) => {
        return member.clubMemberStatus === "CLUB_JOINED";
      });
      setJoinMembers(filterJoinMember);
    }
    if (data) {
      let filterWaitingMember = data.filter((member) => {
        return member.clubMemberStatus === "CLUB_WAITING";
      });
      setWaitingMembers(filterWaitingMember);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data]);

  return (
    <>
      {isError && <EtcWrap>{error.message}</EtcWrap>}
      {isLoading && (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      )}
      {data && (
        <>
          <Header title="아지트 멤버 관리" />
          <MemberWrap>
            <WaitingMember waitingMembers={waitingMembers} />
            <Member joinMembers={joinMembers} />
          </MemberWrap>
        </>
      )}
    </>
  );
};

export default AzitMember;
