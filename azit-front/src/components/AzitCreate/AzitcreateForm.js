import styled from "styled-components";
import { useState } from "react";
import Button from "../Button";
import { LocationModal } from "../Modal";

const CreateFormWrap = styled.div`
  margin-top: 2rem;
  margin-left: 2rem;
  margin-right: 2rem;
  > .form {
    > .inputContainer {
      margin-top: 2rem;
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

const AzitcreateForm = () => {
  const selectList = [
    "문화/예술",
    "운동/액티비티",
    "푸드/드링크",
    " 취미",
    "여행/동행",
    "창작",
    "성장/자기계발",
  ];
  const selectGenderList = ["남", "여", "제한없음"];
  const [selected, setSelected] = useState("문화/예술");
  const [genderSelected, setGenderSelected] = useState("남");
  const [check, setCheck] = useState("offline");

  const handleChkChange = (e) => {
    setCheck(e.target.id);
  };

  const handleSelect = (e) => {
    setSelected(e.target.value);
  };

  const GenderhandleSelect = (e) => {
    setGenderSelected(e.target.value);
  };
  const [modalOpen, setModalOpen] = useState(false);
  const modalHandler = () => {
    modalOpen ? setModalOpen(false) : setModalOpen(true);
  };

  return (
    <CreateFormWrap>
      <form className="form">
        <div className="inputContainer">
          <label>카테고리를 선택해주세요.</label>
          <div className="selectBox">
            <select onChange={handleSelect} value={selected}>
              {selectList.map((item) => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="inputContainer">
          <label>아지트의 이름을 입력해주세요.</label>
          <input placeholder="영어 및 숫자를 포함한 10글자 이상"></input>
        </div>
        <div className="inputContainer">
          <label>아지트를 소개해주세요.(128자 이내)</label>
          <textarea placeholder="텍스트를 입력해주세요."></textarea>
        </div>
        <div className="inputContainer">
          <label>날짜와 시간을 정해볼까요?</label>
          <div className="wd70">
            <input placeholder="0000-00-00"></input>
            <input placeholder="오전 00:00"></input>
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
          <div className="selectPlace" onClick={() => modalHandler()}>
            장소를 입력해주세요.
          </div>
        </div>
        {modalOpen && <LocationModal modalHandler={modalHandler} />}
        <div className="inputContainer">
          <label>멤버의 성별을 알려주세요.</label>
          <div className="selectBox gender">
            <select onChange={GenderhandleSelect} value={genderSelected}>
              {selectGenderList.map((item) => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="inputContainer">
          <label>멤버의 나이를 알려주세요.</label>
          <div className="wd70">
            <input></input>
            <p>~</p>
            <input></input>
            <input className="checkBox" type="checkbox" />
            <span>제한없음</span>
          </div>
        </div>
        <div className="inputContainer">
          <label>멤버의 인원 수를 정해주세요.(호스트 포함)</label>
          <input></input>
        </div>
        <div className="inputContainer">
          <label>참가비가 있나요?(없으면 0을 입력해주세요.)</label>
          <input></input>
        </div>
        <div className="inputContainer">
          <label>참가 필수 질문을 정해주세요.</label>
          <input placeholder="ex) 커피를 좋아하세요?"></input>
        </div>
      </form>
      <div className="buttonWrap">
        <Button state="disabled" title="모임 미리보기" />
      </div>
    </CreateFormWrap>
  );
};

export default AzitcreateForm;
