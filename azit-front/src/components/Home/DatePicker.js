import { useEffect, useState } from "react";
import styled from "styled-components";
import { numToDayOfTheWeek } from "../../util/toDateFormatOfKR";
const DatePickerWrap = styled.ul`
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: space-between;
  padding-bottom: 1rem;
  margin-bottom: 1rem;
  border-bottom: 1px solid var(--border-color);
`;
const DateItem = styled.li`
  flex-basis: calc((100% / 7) - 6px);
  display: flex;
  padding: 0.5rem 0;
  height: 5rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: var(--white-color);
  border-radius: 5px;
  transition: 0.5s all;
  cursor: pointer;
  > span:nth-child(2) {
    font-size: var(--big-font);
    font-weight: var(--bold-weight);
  }
  &&.active {
    background-color: var(--font-color);
    color: var(--white-color);
  }
  :hover {
    background-color: rgba(0, 0, 0, 0.7);
    color: var(--white-color);
  }
`;
const DatePicker = ({ days, handleDays }) => {
  const [dates, setDates] = useState([]);
  // 현재 날짜 포함 7일 구하는 로직
  useEffect(() => {
    let result = [];
    for (let i = 0; i < 7; i++) {
      const date = new Date();
      date.setDate(date.getDate() + i);
      let dateAndDateOfTheWeek = {};
      dateAndDateOfTheWeek.date = date.getDate();
      dateAndDateOfTheWeek.day = numToDayOfTheWeek(date.getDay());
      result.push(dateAndDateOfTheWeek);
    }
    setDates(result);
  }, []);
  
  return (
    <DatePickerWrap>
      {dates.map((date, idx) => (
        <DateItem
          key={idx}
          onClick={() => {
            handleDays(idx);
          }}
          className={days === idx && "active"}
        >
          <span>{date.day}</span>
          <span>{date.date}</span>
        </DateItem>
      ))}
    </DatePickerWrap>
  );
};

export default DatePicker;
