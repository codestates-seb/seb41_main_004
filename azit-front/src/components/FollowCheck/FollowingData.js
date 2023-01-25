import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import FollowerList from "./FollowerList";

const Null = styled.div`
  text-align: center;
  padding: 2rem;
  color: var(--sub-font-color);
`;

const FollowingData = () => {
  return (
    <>
      {ClubData ? (
        ClubData.map((data) => <FollowerList key={data.clubId} data={data} />)
      ) : (
        <Null>팔로우 한 사람이 없습니다.</Null>
      )}
    </>
  );
};

export default FollowingData;
