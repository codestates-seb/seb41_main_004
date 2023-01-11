import styled from "styled-components";

import UserListIcon from "../images/userListIcon.png";

const ListWrap = styled.article`
  padding: 1rem;
  border-radius: 5px;
  background-color: var(--white-color);
  margin-bottom: 1rem;
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

const AzitList = ({ data }) => {
  console.log(data);
  let meetDate;
  if (data) {
    let month = data.meetingDate.slice(5, 7);
    let day = data.meetingDate.slice(8, 10);
    let time = data.meetingDate.slice(-5);
    meetDate = `${month}월${day}일 ${time}`;
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
      <ImgWrap clubImg={data.clubImg} />
      <div className="infoCell">
        <div className="tagWrap">
          {/* 카테고리 및 숨겨짐 들어갈 곳 tagDisplay에 none을 props로 넣을 시 사라짐 */}
          <Tag className="category">{data.category}</Tag>
          <Tag tagDisplay="">숨겨짐</Tag>
        </div>
        <h2 className="clubName">{data.clubName}</h2>
        <div className="placeTime">
          <span className="place">{data.location}∙</span>
          <span className="time">{meetDate}</span>
        </div>
        <div className="etcCell">
          <div className="profileAvatar">{repeatAvatar(data.user)}</div>
          <div className="limitCell">
            <img src={UserListIcon} alt="limitIcon" />
            <div className="limitWrap">
              <span className="current">{data.user.length} </span>/
              <span className="limit"> {data.memberLimit}</span>명
            </div>
          </div>
          <span className="clubHost">{data.clubHost}</span>
        </div>
      </div>
    </ListWrap>
  );
};

export default AzitList;
