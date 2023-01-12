import styled from "styled-components";
import { useState } from "react";

const EditFormWrap = styled.div`
  flex:1;
  padding: 7.5rem 2rem 0;
  > form {
    > .inputContainer {
      margin-top: 2rem;
      > .ageSelect {
        display: flex;
        align-items: flex-end;
        >span:nth-child(2) {
          display: block;
          padding:0 1rem;
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
          <div className="ageSelect">
            <div className="selectBox">
              <select>
                <option>2000</option>
                <option>2001</option>
                <option>2002</option>
                <option>2003</option>
                <option>2004</option>
                <option>2005</option>
              </select>
              <span className="selectArrow" />
            </div>
            <span>~</span>
            <div className="selectBox">
              <select>
                <option>2000</option>
                <option>2001</option>
                <option>2002</option>
                <option>2003</option>
                <option>2004</option>
                <option>2005</option>
              </select>
              <span className="selectArrow" />
            </div>
            <input className="checkBox" type="checkbox" />
            <span>제한없음</span>
          </div>
        </div>
        <div className="inputContainer">
          <label>예상 참가비</label>
          <input></input>
        </div>
        <div className="inputContainer">
          <label>날짜 및 시간</label>
          <div className="wd70">
            <input type="date"></input>
            <input type="time"></input>
          </div>
        </div>
      </form>
    </EditFormWrap>
  );
};

export default AzitEditForm;
