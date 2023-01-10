import styled from "styled-components";

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
  .selectBox {
    position: relative;
    > .selectArrow {
      position: absolute;
      width: 10px;
      height: 10px;
      border-bottom: 2px solid var(--border-color);
      border-right: 2px solid var(--border-color);
      top: 35%;
      transform: rotate(45deg);
      right: 15px;
      transition: 0.5s all;
    }
    > select:focus + .selectArrow {
      transform: rotate(225deg);
      border-bottom: 2px solid var(--point-color);
      border-right: 2px solid var(--point-color);
    }
  }
  select {
    width: 100%;
    border-radius: 5px;
    border: 1px solid var(--border-color);
    padding: 1.5rem;
    appearance: none;
    position: relative;
  }
  textarea {
    width: 100%;
    height: 10rem;
    border-radius: 5px;
    border: 1px solid var(--border-color);
  }
  textarea:focus,
  select:focus {
    outline: none;
    border: 1px solid var(--point-color);
  }
`;

const CreateCell = () => {
  return (
    <CreateCellWrap>
      <h3>리뷰 작성</h3>
      <div className="createWrap">
        <div className="userNameWrap">
          <label htmlFor="userName">유저 닉네임</label>
          <input id="userName" defaultValue={"유저 닉네임"}></input>
        </div>
        <div className="reviewSelectWrap">
          <label htmlFor="reviewSelect">리뷰 선택</label>
          <div className="selectBox">
            <select name="review" id="reviewSelect">
              <option value="배려심이 있어요!">배려심이 있어요!</option>
              <option value="적극적으로 모임에 참여해요!">
                적극적으로 모임에 참여해요!
              </option>
              <option value="시간약속을 잘 지켜요!">
                시간약속을 잘 지켜요!
              </option>
              <option value="다음에 만나면 더 재밌게 놀아요!">
                다음에 만나면 더 재밌게 놀아요!
              </option>
              <option value="사람을 편하게 해줘요!">
                사람을 편하게 해줘요!
              </option>
              <option value="분위기를 즐겁게 만들어줘요!">
                분위기를 즐겁게 만들어줘요!
              </option>
            </select>
            <span className="selectArrow" />
          </div>
        </div>
        <div className="detailReviewWrap">
          <label htmlFor="detailReview">자세한 리뷰 작성하기(선택)</label>
          <textarea id="detailReview" />
        </div>
      </div>
    </CreateCellWrap>
  );
};

export default CreateCell;
