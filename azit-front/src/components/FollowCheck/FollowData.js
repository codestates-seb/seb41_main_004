import styled from "styled-components";
import { ClubData } from "../../dummyData/ClubData";
import FollowList from "./FollowList";

const Null = styled.div`
  text-align: center;
  padding: 2rem;
  color: var(--sub-font-color);
`;

const FollowData = () => {
  return (
    <>
      {ClubData ? (
        ClubData.map((data) => <FollowList key={data.clubId} data={data} />)
      ) : (
        <Null>팔로우 한 사람이 없습니다.</Null>
      )}
    </>
  );
};

export default FollowData;
