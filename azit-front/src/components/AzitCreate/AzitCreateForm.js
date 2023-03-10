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

const DaumPostcodeWrap = styled.div`
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.25);
  }
  > .location {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
  }
`;

const AzitCreateForm = ({ croppedImage }) => {
  //  ????????? ?????? ??? ??????
  const [selected, setSelected] = useState("1"); //????????????
  const [smallSelected, setSmallSeleted] = useState("1");
  const [clubName, setClubName] = useState("");
  const [clubInfo, setClubInfo] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [meetingTime, setMeetingTime] = useState("");
  const [check, setCheck] = useState("offline");
  const [genderSelected, setGenderSelected] = useState("ALL");
  const [minYearSelected, setMinYearSelected] = useState("");
  const [maxYearSelected, setMaxYearSelected] = useState("");
  const [memberLimit, SetMemberLimit] = useState(3);
  const [checked, setChecked] = useState(false);
  const [fee, setFee] = useState("");
  const [question, setQuestion] = useState("");
  const [writeInfo, setWriteInfo] = useState("");
  const [visible, setVisible] = useState(false);
  const [year, setYear] = useState({ minYear: [], maxYear: [] });

  useEffect(() => {
    // ????????? ?????? ?????? ?????? ??? > ????????? ?????? ????????? ???????????? ?????????.
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
      alert("?????? 20????????? ???????????????.");
      return SetMemberLimit(20);
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

  // ???????????? ???????????? ????????? ?????? ?????? Select ??? ????????????
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

  // daum ?????? ??????
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

  // daum ?????? style props??? ??????
  const DaumPostcodeStyle = {
    width: "48rem",
    height: "50rem",
  };

  // ????????? base 64 ??? image.file??? ???????????? ??????
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

  let file = dataURLtoFile(croppedImage, "sendImg");
  let categorySmallId = Number(smallSelected);
  let numberFee = Number(fee.split(",").join(""));
  let numberMemberLimit = Number(memberLimit);

  // ?????? ???????????? ?????? ?????? ?????? ??????
  let today = new Date();
  let Today = today.toISOString().split("T")[0];

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
          <label>??????????????? ??????????????????.</label>
          <div className="selectBox">
            <select onChange={handleSelect} value={selected}>
              <option value="1" key="1">
                ??????/??????
              </option>
              <option value="2" key="2">
                ??????/????????????
              </option>
              <option value="3" key="3">
                ??????/?????????
              </option>
              <option value="4" key="4">
                ??????
              </option>
              <option value="5" key="5">
                ??????/??????
              </option>
              <option value="6" key="6">
                ??????
              </option>
              <option value="7" key="7">
                ??????/????????????
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
                <option>???????????? ??????????????????.</option>
                <option value="1" key="1">
                  ??????
                </option>
                <option value="2" key="2">
                  ??????
                </option>
                <option value="3" key="3">
                  ?????????
                </option>
                <option value="4" key="4">
                  ??????
                </option>
                <option value="5" key="5">
                  ?????????
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
                <option>???????????? ??????????????????.</option>
                <option value="6" key="6">
                  ????????????
                </option>
                <option value="7" key="7">
                  ??????
                </option>
                <option value="8" key="8">
                  ??????
                </option>
                <option value="9" key="9">
                  ????????????
                </option>
                <option value="10" key="10">
                  ??????
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
                <option>???????????? ??????????????????.</option>
                <option value="11" key="11">
                  ????????????
                </option>
                <option value="12" key="12">
                  ??????
                </option>
                <option value="13" key="13">
                  ??????
                </option>
                <option value="14" key="14">
                  ??????
                </option>
                <option value="15" key="15">
                  ?????????
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
                <option>???????????? ??????????????????.</option>
                <option value="16" key="16">
                  ????????????
                </option>
                <option value="17" key="17">
                  ??????
                </option>
                <option value="18" key="18">
                  ?????????
                </option>
                <option value="19" key="19">
                  VR
                </option>
                <option value="20" key="20">
                  ????????????
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
                <option>???????????? ??????????????????.</option>
                <option value="21" key="21">
                  ??????????????????
                </option>
                <option value="22" key="22">
                  ????????????
                </option>
                <option value="23" key="23">
                  ?????????
                </option>
                <option value="24" key="24">
                  ????????????
                </option>
                <option value="25" key="25">
                  ??????
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
                <option>???????????? ??????????????????.</option>
                <option value="26" key="26">
                  ?????????
                </option>
                <option value="27" key="27">
                  ?????????
                </option>
                <option value="28" key="28">
                  ????????????
                </option>
                <option value="29" key="29">
                  ??????
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
                <option>???????????? ??????????????????.</option>
                <option value="31" key="31">
                  ???????????????
                </option>
                <option value="32" key="32">
                  ?????????
                </option>
                <option value="33" key="33">
                  ??????
                </option>
                <option value="34" key="34">
                  ?????????
                </option>
                <option value="35" key="35">
                  ?????????
                </option>
              </select>
              <span className="selectArrow" />
            </div>
          ) : (
            <></>
          )}
        </div>
        <div className="inputContainer">
          <label>???????????? ????????? ??????????????????.</label>
          <input
            minLength={2}
            maxLength={24}
            onChange={onChangeClubName}
            value={clubName}
          ></input>
        </div>
        <div className="inputContainer">
          <label>???????????? ??????????????????.(128??? ??????)</label>
          <textarea
            placeholder="???????????? ??????????????????."
            maxLength={128}
            onChange={onChangeClubInfo}
            value={clubInfo}
          ></textarea>
        </div>
        <div className="inputContainer">
          <label>????????? ????????? ????????????????</label>
          <div className="wd70">
            <input type="date" id="Date" min={Today} onChange={onChangeDate} />
            <input type="time" id="Time" onChange={onChangeTime} />
          </div>
        </div>
        <div className="radioContainer">
          <label>????????? ????????????????</label>
          <div>
            <input
              onChange={handleChkChange}
              id="offline"
              type="radio"
              name="place"
            />
            <Label check={check === "offline"} htmlFor="offline">
              ????????????
            </Label>
            <input
              onChange={handleChkChange}
              id="online"
              type="radio"
              name="place"
            />
            <Label2 check={check === "online"} htmlFor="online">
              ?????????
            </Label2>
          </div>
          {check === "offline" ? (
            <>
              <div
                className="selectPlace"
                onClick={() => setVisible(true)}
                placeholder="????????? ??????????????????."
              >
                {writeInfo ? writeInfo : "????????? ??????????????????."}
              </div>
              {visible ? (
                <DaumPostcodeWrap>
                  <DaumPostcode
                    className="location"
                    onComplete={handleComplete}
                    style={DaumPostcodeStyle}
                  />
                  <div
                    className="background"
                    onClick={() => setVisible(false)}
                  ></div>
                </DaumPostcodeWrap>
              ) : (
                <></>
              )}
            </>
          ) : (
            <div className="selectPlace">?????????</div>
          )}
        </div>
        <div className="inputContainer">
          <label>????????? ????????? ???????????????.</label>
          <div className="selectBox gender">
            <select onChange={GenderhandleSelect} value={genderSelected}>
              <option value="ALL" key="ALL">
                ????????????
              </option>
              <option value="MALE_ONLY" key="MALE_ONLY">
                ???
              </option>
              <option value="FEMALE_ONLY" key="FEMALE_ONLY">
                ???
              </option>
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="inputContainer">
          <label>????????? ????????? ???????????????.</label>
          <div className="ageSelect">
            <div className="selectBox">
              <select
                onChange={handleMinYearSelect}
                value={minYearSelected}
                id="minAge"
              >
                <option>??????</option>
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
                <option>??????</option>
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
            <span>????????????</span>
          </div>
        </div>
        <div className="inputContainer people">
          <label>????????? ?????? ?????? ???????????????.(????????? ?????? 3??? ??????)</label>
          <input
            type="number"
            min="3"
            max="20"
            onChange={onChangeMemberLimit}
            value={memberLimit || 3}
          ></input>
        </div>
        <div className="inputContainer">
          <label>???????????? ??????????(????????? 0??? ??????????????????.)</label>
          <input
            type="text"
            value={fee}
            onChange={(e) => setFee(PriceFormat(e.target.value))}
          ></input>
        </div>
        <div className="inputContainer">
          <label>?????? ?????? ????????? ???????????????.</label>
          <input
            type="text"
            placeholder="ex) ????????? ????????????????"
            value={question}
            maxLength={24}
            onChange={(e) => setQuestion(e.target.value)}
          ></input>
        </div>
      </form>
      <div className="buttonWrap">
        <button
          className={
            croppedImage &&
            clubName &&
            clubInfo &&
            question &&
            fee &&
            meetingDate &&
            meetingTime &&
            (checked === true || (minYearSelected && maxYearSelected))
              ? "nextBtn"
              : "disabled"
          }
          onClick={move}
        >
          ?????? ????????????
        </button>
      </div>
    </CreateFormWrap>
  );
};

export default AzitCreateForm;
