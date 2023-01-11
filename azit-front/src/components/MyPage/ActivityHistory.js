import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import AzitList from "../AzitList";

const Container = styled.div`
  div.Box {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  input#check_btn {
    display: none;
  }

  input#check_btn + label > span {
    color: #222222;
    /* vertical-align: middle;
    padding-left: 5px; */
  }

  /* label:before에 체크하기 전 상태 CSS */
  input#check_btn + label:before {
    content: "";
    display: inline-block;
    width: 2rem;
    height: 2rem;
    border: 0.2rem solid #d9d9d9;
    border-radius: 100%;
    vertical-align: middle;
  }

  /* label:before에 체크 된 상태 CSS */
  input#check_btn:checked + label:before {
    content: "✓";
    color: #ffffff;
    background-color: #d9d9d9;
    background-repeat: no-repeat;
    background-position: 50%;
  }
  div.selectWrapper > select {
    border: 0.2rem solid #d9d9d9;
    border-radius: 0.5rem;
  }
`;

const ActivityHistory = () => {
  return (
    <>
      <Container>
        <div className="Box">
          <div className="check_wrap">
            <input type="checkbox" id="check_btn" />
            <label htmlFor="check_btn">
              <span> &nbsp;호스트인 아지트만 보기</span>
            </label>
          </div>
          <div className="selectWrapper">
            <select>
              <option value="전체보기">전체보기</option>
              <option value="전체보기">전체보기</option>
            </select>
          </div>
        </div>
      </Container>
      {ClubData ? (
        ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
      ) : (
        <></>
      )}
    </>
  );
};

export default ActivityHistory;
