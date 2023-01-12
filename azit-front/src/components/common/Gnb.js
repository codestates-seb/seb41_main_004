import styled from "styled-components";
import { NavLink } from "react-router-dom";
import ChatIcon from "../../images/chat-icon.png";
import ChatIconRed from "../../images/chat-icon(red).png";
import ProfileIcon from "../../images/profile-icon.png";
import ProfileIconRed from "../../images/profile-icon(red).png";
import SearchIcon from "../../images/search-icon.png";
import SearchIconRed from "../../images/search-icon(red).png";
import HomeIcon from "../../images/home-icon.png";
import HomeIconRed from "../../images/home-icon(red).png";
import PlusIcon from "../../images/plus-icon.png";

const StyledGnbWrapper = styled.div`
  position: fixed;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  max-width: 50rem;
  height: 8rem;
  background-color: var(--white-color);
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.4);
  z-index: 99;
  > a {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    > .icon {
      width: 4rem;
      height: 4rem;
      background-size: cover;
      background-repeat: no-repeat;
      background-position: center center;
    }
    > span {
      color: var(--light-font-color);
    }
  }
  > a.active {
    > span {
      color: var(--point-color);
    }
  }
  > .home {
    > .icon {
      background-image: url(${HomeIcon});
    }
  }
  > .home.active {
    > .icon {
      background-image: url(${HomeIconRed});
    }
  }
  > .chat {
    > .icon {
      background-image: url(${ChatIcon});
    }
  }
  > .chat.active {
    > .icon {
      background-image: url(${ChatIconRed});
    }
  }
  > .search {
    > .icon {
      background-image: url(${SearchIcon});
    }
  }
  > .search.active {
    > .icon {
      background-image: url(${SearchIconRed});
    }
  }
  > .profile {
    > .icon {
      background-image: url(${ProfileIcon});
    }
  }
  > .profile.active {
    > .icon {
      background-image: url(${ProfileIconRed});
    }
  }
  > .create {
    img {
      width: 5rem;
      height: 5rem;
    }
  }
`;

const Gnb = () => {
  return (
    <StyledGnbWrapper>
      <NavLink to="/" className="home">
        <span className="icon" />
        <span>홈</span>
      </NavLink>
      <NavLink to="/chat" className="chat">
        <span className="icon" />
        <span>채팅</span>
      </NavLink>
      <NavLink to="/create" className="create">
        <img src={PlusIcon} alt="" />
      </NavLink>
      <NavLink to="/search" className="search">
        <span className="icon" />
        <span>검색</span>
      </NavLink>
      <NavLink to="/userpage" className="profile">
        <span className="icon" />
        <span>프로필</span>
      </NavLink>
    </StyledGnbWrapper>
  );
};
export default Gnb;
