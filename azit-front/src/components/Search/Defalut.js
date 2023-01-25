import searchBackIcon from "../../images/searchBackIcon.png";

const Default = ({text, status}) => {
  return (
    <div className="default">
      <img alt="searchIcon" src={searchBackIcon} style={status && {display: 'none'}}/>
      <p>{text}</p>
    </div>
  );
};

export default Default