import { ClubData } from "../../dummyData/ClubData";
import FollowingList from "./FollowingList";

const FollowingData = () => {
  return (
    <>
      {ClubData ? (
        ClubData.map((data) => <FollowingList key={data.clubId} data={data} />)
      ) : (
        <></>
      )}
    </>
  );
};

export default FollowingData;
