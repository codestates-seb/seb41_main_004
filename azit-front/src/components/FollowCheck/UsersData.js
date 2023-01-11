import { ClubData } from "../../dummyData/ClubData";
import FollowList from "./FollowList";

const UsersData = () => {
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

export default UsersData;
