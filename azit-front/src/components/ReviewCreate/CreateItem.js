import { useEffect, useState } from "react";
import styled from "styled-components";
import { ReviewData } from "../../dummyData/ReviewData";

const CreateCellWrap = styled.article`
  #userName {
    pointer-events: none;
    height: 3.2rem;
    background-color: var(--background-color);
    border: none;
  }
  .createWrap {
    padding-bottom: 2rem;
    border-bottom: 1px solid var(--border-color);
    margin-bottom: 2rem;
    .userNameWrap,
    .reviewSelectWrap {
      margin-bottom: 1rem;
    }
  }
`;

const CreateItem = ({ member, setPostData, postData, hostId, clubId }) => {
  const [review, setReview] = useState({
    reviewerId: hostId,
    clubId,
    revieweeId: member.memberId,
    commentCategory: "CONSIDERATE",
    commentBody: "",
  });
  // console.log(member)
  const handleReview = (e) => {
    if (e.target.id === "category") {
      setReview({ ...review, commentCategory: e.target.value });
    } else if (e.target.id === "body") {
      setReview({ ...review, commentBody: e.target.value });
    }
  };
  useEffect(() => {
    if (postData.filter((el) => el.revieweeId === member.memberId).length === 0) {
      setPostData([...postData, review]);
    } else {
      // 체크 해제
      let findIndex = postData.findIndex(item => item.revieweeId === member.memberId);
      let copiedData = [...postData];
      copiedData[findIndex] = review;
        setPostData(copiedData);
      // setPostData(postData.filter((el) => el !== id));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [review]);
  // console.log(review)
  return (
    <CreateCellWrap>
      <h3>리뷰 작성</h3>
      <div className="createWrap">
        <div className="userNameWrap">
          <label htmlFor="userName">유저 닉네임</label>
          <input id="userName" defaultValue={member.nickname}></input>
        </div>
        <div className="reviewSelectWrap">
          <label htmlFor="reviewSelect">리뷰 선택</label>
          <div className="selectBox">
            <select
              name="review"
              id="category"
              onChange={(e) => handleReview(e)}
              value={review.commentCategory}
            >
              {ReviewData.map((data, idx) => (
                <option key={idx} value={data.value}>
                  {data.name}
                </option>
              ))}
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="detailReviewWrap">
          <label htmlFor="body">자세한 리뷰 작성하기(선택)</label>
          <textarea id="body" 
          maxLength={128}
          onChange={(e) => handleReview(e)} />
        </div>
      </div>
    </CreateCellWrap>
  );
};

export default CreateItem;
