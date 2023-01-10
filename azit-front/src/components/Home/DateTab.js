import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../AzitList";

const Null = styled.article`
  padding: 8rem 0 0;
  text-align: center;
  color: var(--sub-font-color);
`;
const MoreBtn = styled.button`
  width: 100%;
  height: 4rem;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  font-size: var(--big-font);
  font-weight: var(--bold-weight);
  color: var(--sub-font-color);
  background-color: transparent;
  border: none;
  cursor: pointer;
`;
const DatePicker = styled.ul`
    display:flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
    padding-bottom: 1rem;
    margin-bottom: 1rem;
    border-bottom: 1px solid var(--border-color);
    >li {
        flex-basis: calc((100% / 7) - 6px);
        display: flex;
        padding: 0.5rem 0;
        height: 5rem;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background-color: var(--white-color);
        border-radius: 5px;
        transition:0.5s all;
        >span:nth-child(2) {
            font-size:var(--big-font);
            font-weight: var(--bold-weight);
        }
    }
    >li.active {
        background-color: var(--font-color);
        color : var(--white-color);
    }
    >li:hover {
        background-color: rgba(0,0,0,0.7);
        color : var(--white-color);
    }
`
const DateTab = () => {
    return (
        <>
        <DatePicker>
            <li className="active"><span>금</span><span>6</span></li>
            <li><span>토</span><span>7</span></li>
            <li><span>일</span><span>8</span></li>
            <li><span>월</span><span>9</span></li>
            <li><span>화</span><span>10</span></li>
            <li><span>수</span><span>11</span></li>
            <li><span>목</span><span>12</span></li>
        </DatePicker>
        {ClubData ? (
          ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
        ) : (
          <Null>해당 날짜의 아지트가 없습니다.</Null>
        )}
        {ClubData && <MoreBtn>더보기 +</MoreBtn>}
      </>
    )
}

export default DateTab