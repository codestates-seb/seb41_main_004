import { useEffect, useState } from "react";
import { useMutation, useQuery } from "react-query";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import CreateItem from "../components/ReviewCreate/CreateItem";
import { axiosInstance } from "../util/axios";

const CreateWrap = styled.form`
  padding: 7.5rem 2rem 2rem;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  > .createCell {
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
          position: relative;
          > label {
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
            cursor: pointer;
          }
          > input {
            display: none;
          }
          > p {
            min-width: 50px;
          }
          > input[type="checkbox"]:checked + div {
            border: solid 0.2rem var(--point-color);
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
const Null = styled.article`
  width: 100%;
  text-align: center;
  color: var(--sub-font-color);
  padding: 8rem 0;
`;
const ReviewCreate = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const hostId = JSON.parse(window.localStorage.getItem("memberId"));
  const [profileArr, setProfileArr] = useState([]);
  const [selectMember, setSelectMember] = useState([]);
  const [postData, setPostData] = useState([]);
  const azitLookup = async () => {
    const res = await axiosInstance.get(`/api/clubs/${id}`);
    return res.data.data;
  };
  const { data: azitData } = useQuery("azitLookup", azitLookup);
  // console.log(postData)
  useEffect(() => {
    let selectMemberId = selectMember.map((el) => el.memberId);
    setPostData(
      postData.filter((el) => selectMemberId.includes(el.revieweeId))
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectMember]);

  useEffect(() => {
    if (azitData) {
      const joinedMembers = azitData.clubMembers
        .filter((member) => member.clubMemberStatus === "CLUB_JOINED")
        .map((member) => member.member);
      const combineProfileArr = [{ ...azitData.host }, ...joinedMembers].filter(
        (member) => member.memberId !== hostId
      );
      setProfileArr(combineProfileArr);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [azitData]);

  const handleSelectMember = (checked, id) => {
    if (checked) {
      setSelectMember([...selectMember, id]);
    } else {
      // 체크 해제
      setSelectMember(selectMember.filter((el) => el !== id));
    }
  };
  
  const posting = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post(`/api/reviews`, postData, {
        headers: { Authorization: localStorage.getItem("accessToken") },
      });
      navigate(-1);
    } catch (error) {
      alert(error.message);
    }
  };
  const { mutate } = useMutation(posting);
  return (
    <CreateWrap onSubmit={mutate}>
      <Header title="리뷰 작성하기"/>
      <div className="createCell">
        {/* SelectCell */}
        <div className="selectCell">
          <h3>참여 인원</h3>
          <ul className="selectWrap">
            {profileArr.map((profile) => {
              return (
                <li key={profile.memberId}>
                  <input
                    id={profile.memberId}
                    type="checkbox"
                    onChange={(e) => {
                      handleSelectMember(e.currentTarget.checked, profile);
                    }}
                    checked={selectMember.includes(profile) ? true : false}
                    name={profile.nickname}
                  ></input>
                  <UserImgWrap
                    userUrl={`${process.env.REACT_APP_S3_URL}${profile.fileInfo.fileUrl}/${profile.fileInfo.fileName}`}
                  />
                  <p>{profile.nickname}</p>
                  <label htmlFor={profile.memberId} />
                </li>
              );
            })}
          </ul>
        </div>
        {selectMember.length > 0 ? (
          selectMember.map((member) => (
            <CreateItem
              key={member.memberId}
              member={member}
              setPostData={setPostData}
              postData={postData}
              hostId={hostId}
              clubId={azitData.clubId}
            />
          ))
        ) : (
          <Null>리뷰를 남길 인원을 선택해주세요.</Null>
        )}
      </div>
      <Button
        title="리뷰 제출하기"
        state={postData.length > 0 ? "active" : "disabled"}
      />
    </CreateWrap>
  );
};

export default ReviewCreate;
