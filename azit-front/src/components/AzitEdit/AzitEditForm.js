import styled from "styled-components";
import { useEffect, useState } from "react";
import DaumPostcode from "react-daum-postcode";
import { useParams } from "react-router-dom";
import { useQuery, useMutation } from "react-query";
import Loading from "../common/Loading";
import { PriceFormat } from "../../util/azitPreviewDateConvert";
import Button from "../common/Button";
import { useNavigate } from "react-router-dom";
import useAxios from "../../util/useAxios";

const EditFormWrap = styled.div`
  flex: 1;
  padding: 7.5rem 2rem 0;
  > form {
    > .inputContainer {
      margin-top: 2rem;
      > .ageSelect {
        display: flex;
        align-items: flex-end;
        > span:nth-child(2) {
          display: block;
          padding: 0 1rem;
          line-height: 4.5rem;
        }
        > .selectBox {
          width: 20%;
        }
        > .checkBox {
          width: auto;
          height: auto;
        }
      }
      > .selectBox {
        width: 30%;
        select {
          text-align: center;
        }
      }
      > .wd70 {
        width: 70%;
        display: flex;
        flex-direction: row;
        > input {
          text-align: center;
        }
        > input:first-child {
          margin-right: 0.5rem;
        }
        > .checkBox {
          margin-top: 1.5rem;
          margin-left: 1rem;
          margin-right: 0.5rem;
          width: 1.5rem;
        }
        > span {
          width: 20rem;
          margin-top: 3rem;
          font-size: 1rem;
        }
        > p {
          padding-top: 1rem;
          margin-left: 1rem;
          margin-right: 1.5rem;
        }
      }
      > .participants {
        width: 13%;
        text-align: center;
      }
      &.people {
        > input {
          padding-right: 0.1rem;
          width: 6rem;
          text-align: center;
        }
      }
    }
    > .radioContainer {
      margin-top: 2rem;
      > div {
        width: 100%;
        display: flex;
        flex-direction: row;
        > input {
          display: none;
        }
      }
      > .selectPlace {
        font-size: var(--small-font);
        color: var(--sub-font-color);
        margin-top: 1.5rem;
        padding-bottom: 1.2rem;
        border-bottom: 1px solid var(--border-color);
      }
    }
    > .buttonWrap {
      margin-top: 2rem;
      margin-bottom: 2rem;
    }
  }
`;

const Label = styled.label`
  text-align: center;
  display: inline-block;
  width: 100%;
  border-radius: 5px;
  border: 1px solid var(--border-color);
  padding: 1rem;
  height: 4.5rem;
  margin-right: 1rem;
  ${(props) =>
    props.check ? "background-color: var(--point-color); color:white;" : ""}
`;

const Label2 = styled.label`
  text-align: center;
  display: inline-block;
  width: 100%;
  border-radius: 5px;
  border: 1px solid var(--border-color);
  padding: 1rem;
  height: 4.5rem;
  ${(props) =>
    props.check ? "background-color: var(--point-color); color:white;" : ""}
`;

const EtcWrap = styled.div`
  width: 100%;
  height: 100vh;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const AzitEditForm = () => {
  const { id } = useParams();
  const axiosInstance = useAxios();
  const [clubName, setClubName] = useState("");
  const [clubInfo, setClubInfo] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [meetingTime, setMeetingTime] = useState("");
  const [genderSelected, setGenderSelected] = useState("ALL");
  const [check, setCheck] = useState("");
  const [writeInfo, setWriteInfo] = useState("");
  const [minYearSelected, setMinYearSelected] = useState("");
  const [maxYearSelected, setMaxYearSelected] = useState("");
  const [memberLimit, SetMemberLimit] = useState(0);
  const [checked, setChecked] = useState(false);
  const [fee, setFee] = useState("");
  const [year, setYear] = useState({ minYear: [], maxYear: [] });
  const [visible, setVisible] = useState(false);
  const [memberStatus, setmemberStatus] = useState([]);

  const azitLookup = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}`);
    return res.data.data;
  };

  const { isError, isLoading, data, error } = useQuery(
    "azitDetail",
    azitLookup
  );

  useEffect(() => {
    if (data) {
      setClubName(data.clubName);
      setClubInfo(data.clubInfo);
      SetMemberLimit(data.memberLimit);
      setGenderSelected(data.genderRestriction);
      setMinYearSelected(data.birthYearMin ? data.birthYearMin : "");
      setMaxYearSelected(data.birthYearMax ? data.birthYearMax : "");
      data.birthYearMin ? setChecked(false) : nolimit();
      setFee(data.fee);
      setMeetingDate(data.meetingDate);
      setMeetingTime(data.meetingTime);
      setCheck(data.isOnline);
      setWriteInfo(data.location ? data.location : "");
      setmemberStatus(data.clubMembers);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data]);

  useEffect(() => {
    let resetMaxYear = [];
    for (let i = minYearSelected; i <= 2023; i++) {
      resetMaxYear.push(`${String(i)}`);
    }
    setYear((originYear) => ({ ...originYear, maxYear: resetMaxYear }));
  }, [minYearSelected]);

  useEffect(() => {
    let resetMinYear = [];
    for (let i = 1950; i <= maxYearSelected; i++) {
      resetMinYear.push(String(i));
    }
    setYear((originYear) => ({ ...originYear, minYear: resetMinYear }));
  }, [maxYearSelected]);

  useEffect(() => {
    let year = { minYear: [], maxYear: [] };
    for (let i = 2023; i >= 1950; i--) {
      year.minYear.push(String(i));
    }
    for (let i = 1950; i <= 2023; i++) {
      year.maxYear.push(String(i));
    }
    setYear(year);
  }, [checked]);

  const onChangeMemberLimit = (e) => {
    if (e.target.value >= 3 && e.target.value <= 20) {
      return SetMemberLimit(e.target.value);
    } else if (e.target.value >= 20) {
      alert("최대 20명까지 가능합니다.");
      return SetMemberLimit(20);
    }
  };

  const handleData = (e) => {
    if (e.target.id === "meetingDate") {
      setMeetingDate(e.target.value);
    } else if (e.target.id === "meetingTime") {
      setMeetingTime(e.target.value);
    } else if (e.target.id === "clubName") {
      setClubName(e.target.value);
    } else if (e.target.id === "clubInfo") {
      setClubInfo(e.target.value);
    } else if (e.target.id === "minAge") {
      setMinYearSelected(e.target.value);
    } else if (e.target.id === "maxAge") {
      setMaxYearSelected(e.target.value);
    } else if (e.target.id === "genderSelected") {
      setGenderSelected(e.target.value);
    } else if (e.target.id === "online" || e.target.id === "offline") {
      setCheck(e.target.id);
    }
  };

  // 제한없음 체크박스 클릭시 멤버 나이 Select 창 비활성화
  const nolimit = () => {
    setChecked(!checked);

    if (!checked) {
      document.getElementById("minAge").disabled = true;
      document.getElementById("maxAge").disabled = true;
      setMinYearSelected("");
      setMaxYearSelected("");
    } else {
      document.getElementById("minAge").disabled = false;
      document.getElementById("maxAge").disabled = false;
    }
  };

  // daum 주소 찾기
  const handleComplete = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }
    setWriteInfo(fullAddress);
    setVisible(false);
  };

  const clubId = Number(`${id}`);
  let numberFee = Number(fee.toString().split(",").join(""));

  // 오늘 기준으로 이전 날짜 선택 제한
  let today = new Date();
  let Today = today.toISOString().split("T")[0];

  let body = {
    clubId,
    clubName,
    clubInfo,
    memberLimit,
    meetingDate,
    meetingTime,
    fee: numberFee,
    genderRestriction: genderSelected,
    birthYearMin: minYearSelected ? minYearSelected : null,
    birthYearMax: maxYearSelected ? maxYearSelected : null,
    isOnline: check,
    location: writeInfo,
  };

  const navigate = useNavigate();

  const azitEdit = async (e) => {
    e.preventDefault();
    try {
      const payload = body;

      await axiosInstance.patch(`api/clubs/${id}`, payload, {
        headers: {
          Authorization: localStorage.getItem("accessToken"),
          "Content-Type": "application/json",
        },
      });

      navigate(-2);
    } catch (error) {
      alert(error.message);
    }
  };

  const { mutate } = useMutation(azitEdit);

  return (
    <EditFormWrap>
      {isError && <EtcWrap>{error.message}</EtcWrap>}
      {isLoading && (
        <EtcWrap>
          <Loading />
        </EtcWrap>
      )}
      {data && (
        <>
          <form onSubmit={mutate}>
            <div className="inputContainer">
              <label>아지트 이름</label>
              <input
                minLength={2}
                maxLength={24}
                onChange={handleData}
                value={clubName}
                disabled={memberStatus.length === 0 ? false : true}
                id="clubName"
              ></input>
            </div>
            <div className="inputContainer">
              <label>아지트 설명</label>
              <textarea
                placeholder="텍스트를 입력해주세요."
                maxLength={128}
                onChange={handleData}
                value={clubInfo}
                id="clubInfo"
              ></textarea>
            </div>
            <div className="inputContainer people">
              <label>참가인원</label>
              <input
                type="number"
                min="3"
                max="20"
                onChange={onChangeMemberLimit}
                value={memberLimit}
                disabled={memberStatus.length === 0 ? false : true}
                id="memberLimit"
              ></input>
            </div>
            <div className="inputContainer">
              <label>성별</label>
              <div className="selectBox">
                <select
                  onChange={handleData}
                  value={genderSelected}
                  id="genderSelected"
                >
                  <option value="ALL" key="ALL">
                    제한없음
                  </option>
                  <option value="MALE_ONLY" key="MALE_ONLY">
                    남
                  </option>
                  <option value="FEMALE_ONLY" key="FEMALE_ONLY">
                    여
                  </option>
                </select>
                <span className="selectArrow" />
              </div>
            </div>
            <div className="inputContainer">
              <label>멤버의 나이를 알려주세요.</label>
              <div className="ageSelect">
                <div className="selectBox">
                  <select
                    onChange={handleData}
                    value={minYearSelected}
                    id="minAge"
                  >
                    <option value={""}>부터</option>
                    {year.minYear.map((item) => (
                      <option value={item} key={item}>
                        {item}
                      </option>
                    ))}
                  </select>
                  <span className="selectArrow" />
                </div>
                <span>~</span>
                <div className="selectBox">
                  <select
                    onChange={handleData}
                    value={maxYearSelected}
                    id="maxAge"
                  >
                    <option value={""}>까지</option>
                    {year.maxYear.map((item) => (
                      <option value={item} key={item}>
                        {item}
                      </option>
                    ))}
                  </select>
                  <span className="selectArrow" />
                </div>
                <input
                  className="checkBox"
                  type="checkbox"
                  checked={checked}
                  onChange={nolimit}
                />
                <span>제한없음</span>
              </div>
            </div>
            <div className="inputContainer">
              <label>예상 참가비</label>
              <input
                type="text"
                value={PriceFormat(String(fee))}
                onChange={(e) => setFee(e.target.value)}
              ></input>
            </div>
            <div className="inputContainer">
              <label>날짜 및 시간</label>
              <div className="wd70">
                <input
                  type="date"
                  min={Today}
                  id="meetingDate"
                  onChange={handleData}
                  value={meetingDate}
                  disabled={memberStatus.length === 0 ? false : true}
                />
                <input
                  type="time"
                  onChange={handleData}
                  id="meetingTime"
                  value={meetingTime}
                  disabled={memberStatus.length === 0 ? false : true}
                />
              </div>
            </div>
            <div className="radioContainer">
              <label>장소를 정해볼까요?</label>
              <div>
                <input
                  onChange={handleData}
                  id="offline"
                  type="radio"
                  name="place"
                  disabled={memberStatus.length === 0 ? false : true}
                />
                <Label check={check === "offline"} htmlFor="offline">
                  오프라인
                </Label>
                <input
                  onChange={handleData}
                  id="online"
                  type="radio"
                  name="place"
                  disabled={memberStatus.length === 0 ? false : true}
                />
                <Label2 check={check === "online"} htmlFor="online">
                  온라인
                </Label2>
              </div>
              {check === "offline" ? (
                <>
                  {memberStatus.length === 0 ? (
                    <div
                      className="selectPlace"
                      onClick={() => setVisible(true)}
                      placeholder="장소를 입력해주세요."
                    >
                      {writeInfo ? writeInfo : "장소를 입력해주세요."}
                    </div>
                  ) : (
                    <div className="selectPlace">
                      {writeInfo ? writeInfo : "장소를 입력해주세요."}
                    </div>
                  )}
                  {visible ? (
                    <div>
                      <DaumPostcode onComplete={handleComplete} height={700} />
                    </div>
                  ) : (
                    <></>
                  )}
                </>
              ) : (
                <div className="selectPlace">온라인</div>
              )}
            </div>
            <div className="buttonWrap">
              <Button state="active" title="수정완료" />
            </div>
          </form>
        </>
      )}
    </EditFormWrap>
  );
};

export default AzitEditForm;
