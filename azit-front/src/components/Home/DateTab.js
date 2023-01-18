import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../common/AzitList";
import { useState } from "react";
import DatePicker from "./DatePicker";
import Null from "./Null";

const DateTab = () => {
  const [days, setDays] = useState(0);
  const handleDays = (num) => {
    setDays(num);
  };
  return (
    <>
    <DatePicker days={days} handleDays={handleDays}/>
      {ClubData ? (
        ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
      ) : (
        <Null text="해당 날짜에 아지트가 없습니다." />
      )}
    </>
  );
};

export default DateTab;
