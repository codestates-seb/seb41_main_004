import styled from 'styled-components';
import CloseIcon from "../../images/closeIcon.png"
import ImgIcon from "../../images/imgIcon.png"
import SearchIcon from "../../images/searchIcon.png"
import { Link } from 'react-router-dom';

const ImgModalWrap = styled.div`
  position: fixed;
  z-index:100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0,0,0,0.25);
  }
  > .modal {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    min-width: 35rem;
    min-height: 37.4rem;
    > .buttonWrap {
      display: flex;
      justify-content: flex-end;
      position: absolute;
      top: 3%;
      right: 3%;
      > button {
        all: unset;
        cursor: pointer;
        > img{
          width: 2.4rem;
        }
      }
    }
    > input {
      margin-top: 2.5rem;
      padding-left: 4.2rem;
      ::placeholder {
        color: var(--light-font-color)
      }
    }
    > img {
      position: absolute;
      width: 2.4rem;
      left: 9%;
      top: 14.9%;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 7rem;
      > .title {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .caption {
        color: var(--light-font-color);
      }
    }
  }
`

const LocationModalWrap = styled.div`
  position: fixed;
  z-index:100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0,0,0,0.25);
  }
  > .modal {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    min-width: 35rem;
    min-height: 60rem;
    > .buttonWrap {
      display: flex;
      justify-content: flex-end;
      position: absolute;
      top: 3%;
      right: 3%;
      > button {
        all: unset;
        cursor: pointer;
        > img{
          width: 2.4rem;
        }
      }
    }
    > input {
      margin-top: 3.5rem;
      padding-left: 4.2rem;
      ::placeholder {
        color: var(--light-font-color)
      }
    }
    > img {
      position: absolute;
      width: 2.4rem;
      left: 9%;
      top: 11%;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 17rem;
      > .title {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .caption {
        color: var(--light-font-color);
      }
    }
  }
`

const LoginRequestModalWrap = styled.div`
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.25);
  }
  > .modal {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    min-width: 35rem;
    min-height: 17.5rem;
    > .buttonWrap {
      margin-top: 1rem;
      display: flex;
      justify-content: center;
      gap: 1rem;
      > button {
        cursor: pointer;
        border: none;
        border-radius: 0.5rem;
        background-color: var(--border-color);
        color: var(--white-color);
        width: 15rem;
        height: 4.2rem;
      }
      > a {
        > button {
          cursor: pointer;
          border: none;
          border-radius: 0.5rem;
          background-color: var(--point-color);
          color: var(--white-color);
          width: 15rem;
          height: 4.2rem;
          :hover {
            background-color: var(--hover-color);
          }
        }
      }
    }
    > input {
      margin-top: 3.5rem;
      padding-left: 4.2rem;
      ::placeholder {
        color: var(--light-font-color);
      }
    }
    > img {
      position: absolute;
      width: 2.4rem;
      left: 9%;
      top: 11%;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 3rem;
      > .title {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .caption {
        color: var(--light-font-color);
      }
    }
  }
`;

const IncompleteModalWrap = styled.div`
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  > .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(0, 0, 0, 0.25);
  }
  > .modal {
    position: relative;
    z-index: 1;
    background-color: var(--white-color);
    border-radius: 1rem;
    padding: 2rem;
    min-width: 35rem;
    > .buttonWrap {
      margin-top: 1rem;
      display: flex;
      justify-content: center;
      gap: 1rem;
      > button {
        cursor: pointer;
        border: none;
        border-radius: 0.5rem;
        background-color: var(--border-color);
        color: var(--white-color);
        width: 100%;
        height: 4.2rem;
      }
      > a {
        > button {
          cursor: pointer;
          border: none;
          border-radius: 0.5rem;
          background-color: var(--point-color);
          color: var(--white-color);
          width: 15rem;
          height: 4.2rem;
          :hover {
            background-color: var(--hover-color);
          }
        }
      }
    }
    > input {
      margin-top: 3.5rem;
      padding-left: 4.2rem;
      ::placeholder {
        color: var(--light-font-color);
      }
    }
    > img {
      position: absolute;
      width: 2.4rem;
      left: 9%;
      top: 11%;
    }
    .textWrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin:2rem 0;
      > .title {
        font-size: var(--big-font);
        font-weight: var(--bold-weight);
      }
      > .caption {
        color: var(--light-font-color);
      }
    }
  }
`;

// 모달 사용 시 react component 안에 아래 코드를 붙여넣어서 사용해주세요.

// const [modalOpen, setModalOpen] = useState(false);
// const modalHandler = () => {
//   modalOpen ? setModalOpen(false) : setModalOpen(true);
// }


export const ImgModal = ( {modalHandler} ) => {
  return (
  <>
    <ImgModalWrap>
      <div className='modal'>
        <div className='buttonWrap'>
          <button onClick={() => modalHandler()}>
            <img alt="closeIcon" src={CloseIcon} />
          </button>
        </div>
        <input placeholder='jpg, jpeg, png / 8MB 이내의 파일'></input>
        <img alt="imgIcon" src={ImgIcon} />
        <div className='textWrap'>
          <span className='title'>이미지를 선택해주세요.</span>
          <span className='caption'>jpg, jpeg, png / 8MB 이내의 파일</span>
        </div>
      </div>
      <div className='background' onClick={() => modalHandler()}/>
    </ImgModalWrap>
  </>
  )
};

export const LocationModal = ( {modalHandler} ) => {
  return (
    <>
      <LocationModalWrap>
        <div className='modal'>
          <div className='buttonWrap'>
            <button onClick={() => modalHandler()}>
              <img alt="closeIcon" src={CloseIcon} />
            </button>
          </div>
          <input placeholder='장소를 입력해 주세요.'></input>
          <img alt="searchIcon" src={SearchIcon} />
          <div className='textWrap'>
            <span className='title'>장소 또는 지역을 검색해보세요.</span>
          </div>
        </div>
        <div className='background' onClick={() => modalHandler()}/>
      </LocationModalWrap>
    </>
  )
}

export const LoginRequestModal = ({ modalHandler }) => {
  return (
    <>
      <LoginRequestModalWrap>
        <div className="modal">
          <div className="textWrap">
            <span className="title">로그인이 필요한 서비스입니다.</span>
            <span className="title">로그인을 하시겠습니까?</span>
          </div>
          <div className="buttonWrap">
            <button onClick={() => modalHandler()}>취소</button>
            <Link to="/login"><button>로그인하기</button></Link>
          </div>
        </div>
        <div className="background" onClick={() => modalHandler()} />
      </LoginRequestModalWrap>
    </>
  );
};

export const IncompleteModal = ({ modalHandler }) => {
  return (
    <>
      <IncompleteModalWrap>
        <div className="modal">
          <div className="textWrap">
            <span className="title">아직 미완성 된 기능입니다.</span>
          </div>
          <div className="buttonWrap">
            <button onClick={() => modalHandler()}>취소</button>
          </div>
        </div>
        <div className="background" onClick={() => modalHandler()} />
      </IncompleteModalWrap>
    </>
  );
};

