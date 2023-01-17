import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../common/AzitList";

const Container = styled.div`
  div.Box {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  div.selectWrapper > select {
    border: 0.1rem solid #d9d9d9;
    border-radius: 0.5rem;
    padding: 5px;
  }
  .checkContainer {
    position: relative;
    padding-left: 3rem;
    cursor: pointer;
    font-size: var(--caption-font);
    color: var(--font-color);
    line-height: 2.4rem;
    margin: 0;
    input {
      position: absolute;
      opacity: 0;
      height: 0;
      width: 0;
      :checked ~ .checkmark {
        background-color: var(--point-color);
        ::after {
          content: "";
          display: block;
          width: 1rem;
          height: 1rem;
          background-color: var(--white-color);
          border-radius: 50%;
        }
      }
      ~ .checkmark {
        position: absolute;
        display: flex;
        justify-content: center;
        align-items: center;
        top: 0;
        left: 0;
        height: 2.4rem;
        width: 2.4rem;
        background-color: var(--border-color);
        border-radius: 50%;
      }
    }
  }
  div.ActivityDetail {
    display: flex;
    justify-content: space-between;
  }
  > div span {
    color: var(--sub-font-color);
    font-size: var(--caption-font);
  }
`;
const ActivityHistory = () => {
  return (
    <>
      <Container>
        <div className="Box">
          <label className="checkContainer">
            호스트인 아지트만 보기
            <input type="checkbox" />
            <span className="checkmark" />
          </label>
          <div className="selectWrapper">
            <select>
              <option value="전체보기">전체보기</option>
              <option value="참여중인 모임">참여중인 모임</option>
              <option value="신청한 모임">신청한 모임</option>
              <option value="종료된 모임">종료된 모임</option>
            </select>
          </div>
        </div>
        {ClubData ? (
          ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
        ) : (
          <></>
        )}
      </Container>
    </>
  );
};

export default ActivityHistory;
