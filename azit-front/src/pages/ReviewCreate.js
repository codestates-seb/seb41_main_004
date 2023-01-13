import { Link } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import CreateCell from "../components/ReviewCreate/CreateCell";
import { ProfileList } from "../dummyData/ProfileList";

const CreateWrap = styled.section`
  padding: 7.5rem 2rem 2rem;
  h3 {
    font-size: var(--big-font);
    margin-bottom: 1rem;
  }
  > .selectCell {
    margin-bottom: 2rem;
    > .selectWrap {
      overflow-x: scroll;
      padding-bottom: 1.5rem;
      display: flex;
      flex-direction: row;
      > li {
        margin-right: 1rem;
        text-align: center;
        >a {
          > p {
            width: 50px;
          }
        }
      }
      > li:last-child {
        margin: 0;
      }
    }
    .selectWrap::-webkit-scrollbar {
      height: 4px;
      background-color: var(--background-color);
      border-radius: 4px;
    }
    .selectWrap::-webkit-scrollbar-thumb {
      background-color: var(--point-color);
      border-radius: 4px;
    }
  }
`;
const UserImgWrap = styled.div`
  width: 3.8rem;
  height: 3.8rem;
  margin: 0 auto;
  overflow: hidden;
  border-radius: 50%;
  background-image: url(${(props) => props.userUrl});
  background-size: cover;
`;
const ReviewCreate = () => {

  return (
    <>
      <Header title="리뷰 작성하기" />
      <CreateWrap>
        {/* SelectCell */}
        <div className="selectCell">
          <h3>참여 인원</h3>
          <ul className="selectWrap">
            {ProfileList.map((profile, idx) => (
              <li key={idx}>
                <Link to="/userpage">
                <UserImgWrap userUrl={profile.userUrl} />
                <p>유저닉네임</p>
                </Link>
              </li>
            ))}
          </ul>
        </div>
        <CreateCell />
        <CreateCell />
        <Button title="리뷰 제출하기" state="disabled"/>
      </CreateWrap>
    </>
  );
};

export default ReviewCreate;
