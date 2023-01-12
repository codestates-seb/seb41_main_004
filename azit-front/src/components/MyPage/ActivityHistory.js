import { Link } from "react-router-dom";
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
    background-position: 100%;
  }
  div.selectWrapper > select {
    border: 0.1rem solid #d9d9d9;
    border-radius: 0.5rem;
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
    color: var(--sub-font-color);
    font-size: var(--caption-font);
  }
  > div.ActivityReview {
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
              <option value="전체보기">전체보기</option>
            </select>
          </div>
        </div>
        {ClubData ? (
          ClubData.map((data) => <AzitList key={data.clubId} data={data} />)
        ) : (
          <></>
        )}
        <div className="ActivityDetail">
          <div className="ActivityView">
            <span>활동내역 보이기</span>
          </div>
          <Link to="">
            <div className="ActivityReview">
              <span>리뷰 작성하러 가기 〉 </span>
            </div>
          </Link>
        </div>
      </Container>
    </>
  );
};

export default ActivityHistory;
