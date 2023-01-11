import { ClubData } from "../../dummyData/ClubData";
import FollowList from "./FollowList";

const FollowData = () => {
  return (
    <>
      {ClubData ? (
        ClubData.map((data) => <FollowList key={data.clubId} data={data} />)
      ) : (
        <></>
      )}
    </>
  );
};

export default FollowData;
