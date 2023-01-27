import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { axiosInstance } from "../../util/axios";
import AzitList from "../common/AzitList";
import Null from "../Home/Null";

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

const ActivityHistory = ({ myPage }) => {
  const [hostCheck, setHostCheck] = useState(false); //host 체크될때
  const [selectCheck, setSelectCheck] = useState("전체보기"); //select 박스 선택한값 가져오기
  const [getData, setGetData] = useState([]);
  const [getFilterData, setGetFilterData] = useState([]);
  const [closedClubId, setClosedClubId] = useState([]);

  const { id } = useParams();

  const handleCheckInput = () => {
    setHostCheck(!hostCheck); // false <-> true
  };

  const handleCheckSelect = (e) => {
    setSelectCheck(e.target.value);
  };

  useEffect(() => {
    const isClosedData = async () => {
      await axiosInstance
        .get(`api/members/${id}/clubs/2`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setClosedClubId(res.data.map((data) => data.clubMemberId));
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    };
    isClosedData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    let filterList = [];
    const setFilterList = (arr) => {
      filterList = [...arr];
    };
    const isClosed = {
      isClosed: true,
    };
    if (hostCheck === false && selectCheck === "전체보기") {
      axiosInstance
        .get(`api/members/${id}/clubs`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setGetData(
            res.data.map((data) =>
              closedClubId.includes(data.clubMemberId)
                ? Object.assign(data, isClosed)
                : data
            )
          );
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    } else if (hostCheck === false && selectCheck === "신청한 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/0`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setGetData(res.data);
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    } else if (hostCheck === false && selectCheck === "참여중인 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/1`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setGetData(res.data);
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    } else if (hostCheck === false && selectCheck === "종료된 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/2`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setGetData(res.data.map((data) => Object.assign(data, isClosed)));
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    } else if (hostCheck === true && selectCheck === "전체보기") {
      axiosInstance
        .get(`api/members/${id}/clubs/`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setFilterList(
            res.data.filter((member) => {
              return member.isHost === true;
            })
          );
          setGetData(
            filterList.map((data) =>
              closedClubId.includes(data.clubMemberId)
                ? Object.assign(data, isClosed)
                : data
            )
          );
        })
        .catch((error) => {
          console.log(error);
        });
    } else if (hostCheck === true && selectCheck === "신청한 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/0`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setFilterList(
            res.data.filter((member) => {
              return member.isHost === true;
            })
          );
          setGetData(filterList);
        })
        .catch((error) => {
          console.log(error);
        });
    } else if (hostCheck === true && selectCheck === "참여중인 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/1`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setFilterList(
            res.data.filter((member) => {
              return member.isHost === true;
            })
          );
          setGetData(filterList);
        })
        .catch((error) => {
          console.log(error);
        });
    } else if (hostCheck === true && selectCheck === "종료된 모임") {
      axiosInstance
        .get(`api/members/${id}/clubs/2`, {
          headers: {
            Authorization: localStorage.getItem("accessToken"),
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setGetData(res.data);
          setFilterList(
            res.data.filter((member) => {
              return member.isHost === true;
            })
          );
          setGetData(filterList.map((data) => Object.assign(data, isClosed)));
        })
        .catch((error) => {
          console.log(error);
        });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectCheck, hostCheck, closedClubId]);

  useEffect(() => {
    setGetFilterData(
      getData.filter(
        (data) =>
          data.clubMemberStatus !== "CLUB_KICKED" &&
          data.clubMemberStatus !== "CLUB_REJECTED"
      )
    );
  }, [getData]);
  return (
    <Container>
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
                <option value="신청한 모임">신청한 모임</option>
                <option value="참여중인 모임">참여중인 모임</option>
                <option value="종료된 모임">종료된 모임</option>
              </select>
            </div>
          </div>
        ) : null}
        {getFilterData?.length > 0 ? (
          getFilterData.map((data) => (
            <AzitList
              key={data.clubInfoResponse.clubId}
              data={data.clubInfoResponse}
              myPage={myPage}
              activityData={data}
            />
          ))
        ) : (
          <Null text={"참여내역이 없습니다."} />
        )}
      </>
      {/* )} */}
    </Container>
  );
};

export default ActivityHistory;
