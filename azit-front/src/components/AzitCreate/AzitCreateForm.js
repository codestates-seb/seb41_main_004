import styled from "styled-components";
import { useState } from "react";
import Button from "../common/Button";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const CreateFormWrap = styled.div`
  padding: 2rem 2rem 0;
  > .form {
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

      > .gender {
        width: 30%;
        > select {
          text-align: center;
        }
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
      > input {
        height: 3.4rem;
        margin-top: 0.9rem;
        padding: 0;
        font-size: var(--small-font);
        color: var(--border-color);
        border: none;
        border-bottom: 1px solid var(--border-color);
        border-radius: 0;
      }
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
        color: var(--border-color);
        margin-top: 1.5rem;
        padding-bottom: 1.2rem;
        border-bottom: 1px solid var(--border-color);
      }
    }
  }
  > .buttonWrap {
    margin-bottom: 2rem;
    margin-top: 3rem;
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

const AzitCreateForm = () => {
  let minYear = [];
  let maxYear = [];

  for (let i = 2023; i >= 1950; i--) {
    minYear.push(String(i));
    maxYear.push(String(i));
  }

  const minYearList = minYear;
  const maxYearList = maxYear;

  //  아지트 생성 폼 상태
  const [selected, setSelected] = useState("1"); //카테고리
  const [clubName, setClubName] = useState("");
  const [clubInfo, setClubInfo] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [meetingTime, setMeetingTime] = useState("");
  const [check, setCheck] = useState("offline");
  const [genderSelected, setGenderSelected] = useState("MALE-ONLY");
  const [minYearSelected, setMinYearSelected] = useState("");
  const [maxYearSelected, setMaxYearSelected] = useState("");
  const [memberLimit, SetMemberLimit] = useState(null);
  const [checked, setChecked] = useState(false);
  const [fee, setFee] = useState("");
  const [question, setQuestion] = useState("");
  const [location, setLocation] = useState("");

  const onChangeMemberLimit = (e) => {
    if (e.target.value >= 3) {
      return SetMemberLimit(e.target.value);
    } else {
      return;
    }
  };

  const inputPriceFormat = (str) => {
    const comma = (str) => {
      str = String(str);
      return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, "$1,");
    };
    const uncomma = (str) => {
      str = String(str);
      return str.replace(/[^\d]+/g, "");
    };
    return comma(uncomma(str));
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

  const handleChkChange = (e) => {
    setCheck(e.target.id);
  };

  const handleMinYearSelect = (e) => {
    setMinYearSelected(e.target.value);
  };

  const handleMaxYearSelect = (e) => {
    setMaxYearSelected(e.target.value);
  };
  const handleSelect = (e) => {
    setSelected(e.target.value);
  };

  const GenderhandleSelect = (e) => {
    setGenderSelected(e.target.value);
  };

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

  let categoryLargeId = Number(selected);
  let numberFee = Number(fee.split(",").join(""));

  let body = {
    fee: numberFee,
    categoryLargeId: categoryLargeId,
    genderRestriction: genderSelected,
    minYear: minYearSelected,
    maxYear: maxYearSelected,
    place: check,
    meetingDate,
    meetingTime,
  };
  console.log(body);

  const navigate = useNavigate();

  const onClick = () => {};

  return (
    <CreateFormWrap>
      <form className="form">
        <div className="inputContainer">
          <label>카테고리를 선택해주세요.</label>
          <div className="selectBox">
            <select onChange={handleSelect} value={selected}>
              <option value="1" key="1">
                문화/예술
              </option>
              <option value="2" key="2">
                운동/액티비티
              </option>
              <option value="3" key="3">
                푸드/드링크
              </option>
              <option value="4" key="4">
                취미
              </option>
              <option value="5" key="5">
                여행/동행
              </option>
              <option value="6" key="6">
                창작
              </option>
              <option value="7" key="7">
                성장/자기계발
              </option>
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="inputContainer">
          <label>아지트의 이름을 입력해주세요.</label>
          <input
            minLength={2}
            maxLength={24}
            onChange={onChangeClubName}
            value={clubName}
          ></input>
        </div>
        <div className="inputContainer">
          <label>아지트를 소개해주세요.(128자 이내)</label>
          <textarea
            placeholder="텍스트를 입력해주세요."
            maxLength={128}
            onChange={onChangeClubInfo}
            value={clubInfo}
          ></textarea>
        </div>
        <div className="inputContainer">
          <label>날짜와 시간을 정해볼까요?</label>
          <div className="wd70">
            <input type="date" onChange={onChangeDate} />
            <input type="time" onChange={onChangeTime} />
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
            <input
              placeholder="장소를 입력해주세요."
              defaultValue={location}
            ></input>
          ) : (
            <div className="selectPlace">온라인</div>
          )}
        </div>
        <div className="inputContainer">
          <label>멤버의 성별을 알려주세요.</label>
          <div className="selectBox gender">
            <select onChange={GenderhandleSelect} value={genderSelected}>
              <option value="MALE_ONLY" key="MALE_ONLY">
                남
              </option>
              <option value="FEMALE_ONLY" key="FEMALE_ONLY">
                여
              </option>
              <option value="ALL" key="ALL">
                제한없음
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
                value={minYearSelected}
                id="minAge"
              >
                {minYearList.map((item) => (
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
                value={maxYearSelected}
                id="maxAge"
              >
                {maxYearList.map((item) => (
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
        <div className="inputContainer people">
          <label>멤버의 인원 수를 정해주세요.(호스트 포함 3명 이상)</label>
          <input
            type="number"
            min="3"
            max="20"
            onChange={onChangeMemberLimit}
            value={memberLimit || ""}
          ></input>
        </div>
        <div className="inputContainer">
          <label>참가비가 있나요?(없으면 0을 입력해주세요.)</label>
          <input
            type="text"
            value={fee}
            onChange={(e) => setFee(inputPriceFormat(e.target.value))}
          ></input>
        </div>
        <div className="inputContainer">
          <label>참가 필수 질문을 정해주세요.</label>
          <input
            type="text"
            placeholder="ex) 커피를 좋아하세요?"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
          ></input>
        </div>
      </form>
      <div className="buttonWrap">
        {question && fee ? (
          <Button state="active" title="모임 미리보기" />
        ) : (
          <Button state="disabled" title="모임 미리보기" />
        )}
      </div>
    </CreateFormWrap>
  );
};

export default AzitCreateForm;
