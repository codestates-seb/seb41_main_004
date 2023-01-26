import { useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import { axiosInstance } from "../../util/axios";
import AzitList from "../common/AzitList";
import Loading from "../common/Loading";

const Container = styled.div`
  div.Box {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  div.selectWrapper > select {
    border: 0.1rem solid #d9d9d9;
    border-radius: 0.5rem;
    padding: 5px;
  }
  .checkContainer {
    position: relative;
    padding-left: 3rem;
    cursor: pointer;
    font-size: var(--caption-font);
    color: var(--font-color);
    line-height: 2.4rem;
    margin: 0;
    input {
      position: absolute;
      opacity: 0;
      height: 0;
      width: 0;
      :checked ~ .checkmark {
        background-color: var(--point-color);
        ::after {
          content: "";
          display: block;
          width: 1rem;
          height: 1rem;
          background-color: var(--white-color);
          border-radius: 50%;
        }
      }
      ~ .checkmark {
        position: absolute;
        display: flex;
        justify-content: center;
        align-items: center;
        top: 0;
        left: 0;
        height: 2.4rem;
        width: 2.4rem;
        background-color: var(--border-color);
        border-radius: 50%;
      }
    }
  }
  div.ActivityDetail {
    display: flex;
    justify-content: space-between;
  }
  > div span {
    color: var(--sub-font-color);
    font-size: var(--caption-font);
  }
`;
const EtcWrap = styled.div`
  width: 100%;
  min-height: 100vh;
  display: flex;
  align-items: flex-start;
  justify-content: center;
`;

const ActivityHistory = ({ myPage }) => {
  const [hostCheck, setHostCheck] = useState(false);
  // eslint-disable-next-line no-unused-vars
  const [selectCheck, setSelectCheck] = useState(0);
  //const [filterList, setFilterList] = useState("");

  const { id } = useParams();
  //data = axios.get(api/club/usePage/1)
  const activeData = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}`);
    return res.data.data;
  };

  const { data, isError, isLoading, error } = useQuery("userActivityData", () =>
    activeData()
  );

  //console.log(data); 받아오는 데이터 확인하기

  const handleCheckInput = () => {
    setHostCheck(!hostCheck);
  };
  //console.log(hostCheck);  false <-> true

  const handleCheckSelect = (e) => {
    setSelectCheck(e.target.value);
  };
  //console.log(selectCheck);

  // useEffect(() => {
  //   if (hostCheck === true && selectCheck === true) {
  //     setFilterList(
  //       data.filter((data) => {
  //         data.host.memberId === id && data.data.selectValue === selectCheck;
  //       })
  //     );
  //   } else if (hostCheck === true) {
  //     setFilterList(
  //       data.filter((data) => {
  //         data.data.hostId === id;
  //       })
  //     );
  //   } else if (selectCheck) {
  //     setFilterList(
  //       data.filter((data) => {
  //         data.data.selectValue === selectCheck;
  //       })
  //     );
  //   }
  // }, [data]);

  return (
    <Container>
      {isLoading ? (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      ) : isError ? (
        <EtcWrap>
          <p>{error.message}</p>
        </EtcWrap>
      ) : (
        <>
          {myPage ? (
            <div className="Box">
              <label className="checkContainer">
                호스트인 아지트만 보기
                <input type="checkbox" onChange={handleCheckInput} />
                <span className="checkmark" />
              </label>
              <div className="selectWrapper">
                <select onChange={handleCheckSelect}>
                  <option value="전체보기">전체보기</option>
                  <option value="참여중인 모임">참여중인 모임</option>
                  <option value="신청한 모임">신청한 모임</option>
                  <option value="종료된 모임">종료된 모임</option>
                </select>
              </div>
            </div>
          ) : null}
          {ClubData ? (
            ClubData.map((data) => (
              <AzitList key={data.clubId} data={data} myPage={myPage} />
            ))
          ) : (
            <></>
          )}
        </>
      )}
    </Container>
  );
};

export default ActivityHistory;
