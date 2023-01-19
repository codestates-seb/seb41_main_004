import styled from "styled-components";
import { useEffect, useState } from "react";
import DaumPostcode from "react-daum-postcode";
import { axiosInstance } from "../../util/axios";
import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import Loading from "../common/Loading";
import {
  PriceFormat,
  genderConvert,
  isOnlineConvert,
  MaxAgeConvert,
  MinAgeConvert,
  timeConvert,
} from "../../util/azitPreviewDateConvert";

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

  const azitLookup = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}`);
    return res.data.data;
  };

  const { isError, isLoading, data, error } = useQuery(
    "azitDetail",
    azitLookup
  );
  console.log(data);
  console.log(data.clubName);
  console.log(data.isOnline);

  const [clubName, setClubName] = useState("");
  const [clubInfo, setClubInfo] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [meetingTime, setMeetingTime] = useState("");
  const [genderSelected, setGenderSelected] = useState("");
  const [check, setCheck] = useState(data.isOnline);
  const [writeInfo, setWriteInfo] = useState(
    data.location ? data.location : "온라인"
  );
  const [minYearSelected, setMinYearSelected] = useState("");
  const [maxYearSelected, setMaxYearSelected] = useState("");
  const [memberLimit, SetMemberLimit] = useState(null);
  const [checked, setChecked] = useState(false);
  const [fee, setFee] = useState("");
  const [year, setYear] = useState({ minYear: [], maxYear: [] });
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    // 이하가 먼저 선택 됐을 때 > 이상은 이하 밑으론 내려가면 안된다.
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
    if (e.target.value >= 3) {
      return SetMemberLimit(e.target.value);
    } else if (e.target.value > 20) {
      return alert("최대 20명까지 가능합니다.");
    }
  };

  const onChangeDate = (e) => {
    setMeetingDate(e.target.value);
  };
  const onChangeTime = (e) => {
    setMeetingTime(e.target.value);
  };

  const onChangeClubName = (e) => {
    setClubName(e.target.value);
  };

  const onChangeClubInfo = (e) => {
    setClubInfo(e.target.value);
  };

  const handleMinYearSelect = (e) => {
    setMinYearSelected(e.target.value);
  };

  const handleMaxYearSelect = (e) => {
    setMaxYearSelected(e.target.value);
  };

  const GenderhandleSelect = (e) => {
    setGenderSelected(e.target.value);
  };

  const handleChkChange = (e) => {
    setCheck(e.target.id);
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
          <form>
            <div className="inputContainer">
              <label>아지트 이름</label>
              <input
                minLength={2}
                maxLength={24}
                onChange={onChangeClubName}
                value={data.clubName}
              ></input>
            </div>
            <div className="inputContainer">
              <label>아지트 설명</label>
              <textarea
                placeholder="텍스트를 입력해주세요."
                maxLength={128}
                onChange={onChangeClubInfo}
                value={data.clubInfo}
              ></textarea>
            </div>
            <div className="inputContainer people">
              <label>참가인원</label>
              <input
                type="number"
                min="3"
                max="20"
                onChange={onChangeMemberLimit}
                value={data.memberLimit || ""}
              ></input>
            </div>
            <div className="inputContainer">
              <label>성별</label>
              <div className="selectBox">
                <select
                  onChange={GenderhandleSelect}
                  value={genderConvert(data.genderRestriction)}
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
                    onChange={handleMinYearSelect}
                    value={data.birthYearMin}
                    id="minAge"
                  >
                    <option>부터</option>
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
                    onChange={handleMaxYearSelect}
                    value={data.birthYearMax}
                    id="maxAge"
                  >
                    <option>까지</option>
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
                  value={checked}
                  onClick={nolimit}
                />
                <span>제한없음</span>
              </div>
            </div>
            <div className="inputContainer">
              <label>예상 참가비</label>
              <input
                type="text"
                value={PriceFormat(String(data.fee))}
                onChange={(e) => setFee(PriceFormat(e.target.value))}
              ></input>
            </div>
            <div className="inputContainer">
              <label>날짜 및 시간</label>
              <div className="wd70">
                <input
                  type="date"
                  onChange={onChangeDate}
                  value={data.meetingDate}
                />
                <input
                  type="time"
                  onChange={onChangeTime}
                  value={data.meetingTime}
                />
              </div>
            </div>
            <div className="radioContainer">
              <label>장소를 정해볼까요?</label>
              <div>
                <input
                  onChange={handleChkChange}
                  id="offline"
                  type="radio"
                  name="place"
                />
                <Label check={check === "offline"} htmlFor="offline">
                  오프라인
                </Label>
                <input
                  onChange={handleChkChange}
                  id="online"
                  type="radio"
                  name="place"
                />
                <Label2 check={check === "online"} htmlFor="online">
                  온라인
                </Label2>
              </div>
              {check === "offline" ? (
                <>
                  <div
                    className="selectPlace"
                    onClick={() => setVisible(true)}
                    placeholder="장소를 입력해주세요."
                  >
                    {writeInfo ? writeInfo : "장소를 입력해주세요."}
                  </div>
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
          </form>
        </>
      )}
    </EditFormWrap>
  );
};

export default AzitEditForm;
