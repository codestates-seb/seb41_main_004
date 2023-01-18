import searchBackIcon from "../../images/searchBackIcon.png";

const Default = ({text}) => {
  return (
    <div className="default">
      <img alt="searchIcon" src={searchBackIcon} />
      <p>{text}</p>
    </div>
  );
};

export default Default