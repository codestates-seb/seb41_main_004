import styled from "styled-components";
import { useEffect, useState } from "react";
import DaumPostcode from "react-daum-postcode";
import { useNavigate } from "react-router-dom";
import { PriceFormat } from "../../util/azitPreviewDateConvert";

const CreateFormWrap = styled.div`
  padding: 2rem 2rem 0;
  > .form {
    > .inputContainer {
      > .selectBox {
        margin-bottom: 1rem;
      }
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
  > .buttonWrap {
    margin-bottom: 2rem;
    margin-top: 3rem;
    > .nextBtn {
      width: 100%;
      height: 55px;
      font-size: var(--big-font);
      border-radius: 5px;
      border: none;
      margin: 0;
      padding: 0;
      cursor: pointer;
      transition: 0.5s all;
      background-color: var(--point-color);
      color: var(--white-color);
      :hover {
        background-color: var(--hover-color);
      }
    }
    > .disabled {
      width: 100%;
      height: 55px;
      font-size: var(--big-font);
      border-radius: 5px;
      border: none;
      margin: 0;
      padding: 0;
      cursor: pointer;
      transition: 0.5s all;
      background-color: var(--border-color);
      color: var(--light-font-color);
      pointer-events: none;
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

const AzitCreateForm = ({ imgFile }) => {
  //  아지트 생성 폼 상태
  const [selected, setSelected] = useState("1"); //카테고리
  const [smallSelected, setSmallSeleted] = useState("1");
  const [clubName, setClubName] = useState("");
  const [clubInfo, setClubInfo] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [meetingTime, setMeetingTime] = useState("");
  const [check, setCheck] = useState("offline");
  const [genderSelected, setGenderSelected] = useState("ALL");
  const [minYearSelected, setMinYearSelected] = useState("");
  const [maxYearSelected, setMaxYearSelected] = useState("");
  const [memberLimit, SetMemberLimit] = useState(null);
  const [checked, setChecked] = useState(false);
  const [fee, setFee] = useState("");
  const [question, setQuestion] = useState("");
  const [writeInfo, setWriteInfo] = useState("");
  const [visible, setVisible] = useState(false);
  const [year, setYear] = useState({ minYear: [], maxYear: [] });

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
    } else {
      return;
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

  // 이미지 base 64 를 image.file로 변환하는 함수
  function dataURLtoFile(dataurl, filename) {
    if (!dataurl) {
      return;
    } else {
      var arr = dataurl.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }

      return new File([u8arr], filename, { type: mime });
    }
  }
  const navigate = useNavigate();

  let file = dataURLtoFile(imgFile, "sendImg");
  let categorySmallId = Number(smallSelected);
  let numberFee = Number(fee.split(",").join(""));
  let numberMemberLimit = Number(memberLimit);

  let body = {
    bannerImg: file,
    categorySmallId: categorySmallId,
    clubName: clubName,
    clubInfo,
    memberLimit: numberMemberLimit,
    meetingDate,
    meetingTime,
    fee: numberFee,
    genderRestriction: genderSelected,
    birthYearMin: minYearSelected,
    birthYearMax: maxYearSelected,
    isOnline: check,
    location: writeInfo,
    joinQuestion: question,
  };

  const move = () => {
    navigate("/azit/preview", { state: body, replace: true });
  };
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
          {selected === "1" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="1" key="1">
                  전시
                </option>
                <option value="2" key="2">
                  영화
                </option>
                <option value="3" key="3">
                  뮤지컬
                </option>
                <option value="4" key="4">
                  공연
                </option>
                <option value="5" key="5">
                  디자인
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "2" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="6" key="6">
                  클라이밍
                </option>
                <option value="7" key="7">
                  등산
                </option>
                <option value="8" key="8">
                  헬스
                </option>
                <option value="9" key="9">
                  필라테스
                </option>
                <option value="10" key="10">
                  골프
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "3" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="11" key="11">
                  맛집투어
                </option>
                <option value="12" key="12">
                  카페
                </option>
                <option value="13" key="13">
                  와인
                </option>
                <option value="14" key="14">
                  커피
                </option>
                <option value="15" key="15">
                  디저트
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "4" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="16" key="16">
                  보드게임
                </option>
                <option value="17" key="17">
                  사진
                </option>
                <option value="18" key="18">
                  방탈출
                </option>
                <option value="19" key="19">
                  VR
                </option>
                <option value="20" key="20">
                  음악감상
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "5" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="21" key="21">
                  복합문화공간
                </option>
                <option value="22" key="22">
                  테마파크
                </option>
                <option value="23" key="23">
                  피크닉
                </option>
                <option value="24" key="24">
                  드라이브
                </option>
                <option value="25" key="25">
                  캠핑
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "6" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="26" key="26">
                  글쓰기
                </option>
                <option value="27" key="27">
                  드로잉
                </option>
                <option value="28" key="28">
                  영상편집
                </option>
                <option value="29" key="29">
                  공예
                </option>
                <option value="30" key="30">
                  DIY
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
          {selected === "7" ? (
            <div className="selectBox">
              <select
                onChange={(e) => setSmallSeleted(e.target.value)}
                value={smallSelected}
              >
                <option>소분류를 선택해주세요.</option>
                <option value="31" key="31">
                  습관만들기
                </option>
                <option value="32" key="32">
                  챌린지
                </option>
                <option value="33" key="33">
                  독서
                </option>
                <option value="34" key="34">
                  스터디
                </option>
                <option value="35" key="35">
                  외국어
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
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
        <div className="inputContainer">
          <label>멤버의 성별을 알려주세요.</label>
          <div className="selectBox gender">
            <select onChange={GenderhandleSelect} value={genderSelected}>
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
                value={minYearSelected}
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
                value={maxYearSelected}
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
            onChange={(e) => setFee(PriceFormat(e.target.value))}
          ></input>
        </div>
        <div className="inputContainer">
          <label>참가 필수 질문을 정해주세요.</label>
          <input
            type="text"
            placeholder="ex) 커피를 좋아하세요?"
            value={question}
            maxLength={24}
            onChange={(e) => setQuestion(e.target.value)}
          ></input>
        </div>
      </form>
      <div className="buttonWrap">
        <button
          className={imgFile && question && fee ? "nextBtn" : "disabled"}
          onClick={move}
        >
          모임 미리보기
        </button>
      </div>
    </CreateFormWrap>
  );
};

export default AzitCreateForm;
