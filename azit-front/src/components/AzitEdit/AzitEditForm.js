import styled from "styled-components";
import { useState } from "react";

const EditFormWrap = styled.div`
  margin-top: 5.5rem;
  margin-left: 2rem;
  margin-right: 2rem;
  > form {
    > .inputContainer {
      margin-top: 2rem;
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
    }
  }
`;

const AzitEditForm = () => {
  const selectGenderList = ["남", "여", "제한없음"];
  const [genderSelected, setGenderSelected] = useState("남");

  const GenderhandleSelect = (e) => {
    setGenderSelected(e.target.value);
  };

  return (
    <EditFormWrap>
      <form>
        <div className="inputContainer">
          <label>아지트 이름</label>
          <input></input>
        </div>
        <div className="inputContainer">
          <label>아지트 이름</label>
          <textarea></textarea>
        </div>
        <div className="inputContainer">
          <label>참가인원</label>
          <input className="participants"></input>
        </div>
        <div className="inputContainer">
          <label>성별</label>
          <div className="selectBox">
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
        <div>
          <label>예상 참가비</label>
          <input></input>
        </div>
        <div className="inputContainer">
          <label>날짜 및 시간</label>
          <div>
            <input placeholder="0000-00-00"></input>
            <input placeholder="오전 00:00"></input>
          </div>
        </div>
      </form>
    </EditFormWrap>
  );
};

export default AzitEditForm;
