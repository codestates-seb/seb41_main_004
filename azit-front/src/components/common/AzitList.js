import { Link } from "react-router-dom";
import styled from "styled-components";
import UserListIcon from "../../images/userListIcon.png";
import { toDateFormatOfMonthDay } from "../../util/toDateFormatOfKR";

const ListWrap = styled.article`
  margin-bottom: 1rem;
`;
const DetailWrap = styled.div`
  padding: 1rem;
  border-radius: 5px;
  background-color: var(--white-color);
  > a {
    display: flex;
    flex-direction: row;
    > .infoCell {
      flex: 1;
      > .tagWrap {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        margin-bottom: 0.3rem;
        > span {
          font-size: var(--small-font);
          padding: 0 0.7rem;
          height: 2rem;
          align-items: center;
          justify-content: center;
          border-radius: 1.5rem;
          color: var(--white-color);
          background-color: var(--light-font-color);
        }
        > .category {
          background-color: var(--point-color);
          margin-right: 0.5rem;
        }
      }
      > .clubName {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .placeTime {
        color: var(--sub-font-color);
        font-size: var(--caption-font);
      }
      > .etcCell {
        display: flex;
        > .profileAvatar {
          display: flex;
          margin-right: 5px;
          > .imgWrap {
            width: 2rem;
            height: 2rem;
            border-radius: 50%;
            overflow: hidden;
            border: 1px solid var(--white-color);
            margin-left: -5px;
            > img {
              max-width: 100%;
            }
          }
          > .imgWrap:first-child {
            margin: 0;
          }
        }
        > .limitCell {
          display: flex;
          align-items: center;
          > img {
            width: 2rem;
          }
          > .limitWrap {
            font-size: var(--small-font);
            color: var(--light-font-color);
          }
        }
        > .clubHost {
          margin-left: auto;
        }
      }
    }
  }
`;
const ImgWrap = styled.div`
  width: 8.5rem;
  height: 8.5rem;
  margin-right: 10px;
  border-radius: 10px;
  background-color: var(--background-color);
  background-image: url(${(props) => props.clubImg});
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
`;
const Tag = styled.span`
  display: ${(props) => (props.tagDisplay ? props.tagDisplay : "flex")};
`;
const EtcWrap = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  display: none;
  button, a {
    cursor: pointer;
    font-size: var(--caption-font);
    color: var(--sub-font-color);
    border:none;
    background-color: transparent;
    margin:0;
    padding:0;
  }
`;

const AzitList = ({ data }) => {
  let meetDate;
  console.log(data)
  if (data) {
    meetDate = toDateFormatOfMonthDay(data.meetingDate, data.meetingTime);
  }
  const repeatAvatar = (data) => {
    let result = [];
    if (data.length >= 5) {
      for (let i = 0; i < 5; i++) {
        result.push(
          <div key={data[i].userId} className="imgWrap">
            <img alt={data[i].userName} src={data[i].avatarUrl} />
          </div>
        );
      }
    } else {
      for (let i = 0; i < data.length; i++) {
        result.push(
          <div key={data[i].userId} className="imgWrap">
            <img alt={data[i].userName} src={data[i].avatarUrl} />
          </div>
        );
      }
    }
    return <>{result}</>;
  };
  return (
    <ListWrap>
      <DetailWrap>
        <Link to={`/azit/detail/${data.clubId}`}>
          <ImgWrap clubImg={`${data.bannerImage ?  `${process.env.REACT_APP_BASE_URL}${data.bannerImage.fileUrl.slice(1)}/${data.bannerImage.fileName}` : null}`} />
          <div className="infoCell">
            <div className="tagWrap">
              {/* 카테고리 및 숨겨짐 들어갈 곳 tagDisplay에 none을 props로 넣을 시 사라짐 */}
              <Tag className="category">{data.categorySmall.categoryName}</Tag>
              <Tag tagDisplay="">숨겨짐</Tag>
            </div>
            <h2 className="clubName">{data.clubName}</h2>
            <div className="placeTime">
              <span className="place">{data.isOnline === "online" ? "온라인" : data.location}∙</span>
              <span className="time">{meetDate}</span>
            </div>
            <div className="etcCell">
              <div className="profileAvatar">{data.clubMembers ? repeatAvatar(data.clubMembers) : null}</div>
              <div className="limitCell">
                <img src={UserListIcon} alt="limitIcon" />
                <div className="limitWrap">
                  <span className="current">{data.clubMembers.length} </span>/
                  <span className="limit"> {data.memberLimit}</span>명
                </div>
              </div>
              <span className="clubHost">{data.host.nickname}</span>
            </div>
          </div>
        </Link>
      </DetailWrap>
      {/* 마이페이지 일 때만 보이게 할 필요 있음 현재 */}
      <EtcWrap>
        <div className="ActivityView">
          <button type="button">활동내역 {true ? "보이기" : "숨기기"}</button>
        </div>
        <div className="ActivityReview">
          <Link to="/review/Create">리뷰 작성하러 가기 〉</Link>
        </div>
      </EtcWrap>
    </ListWrap>
  );
};

export default AzitList;
