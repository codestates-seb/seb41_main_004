import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import Loading from '../components/common/Loading';
import { loginStatusSlice } from '../redux/loginSlice';
import { googleCallback } from '../util/oauthGoogle';


export default function OauthGoogle() {

  const dispatch = useDispatch();

  useEffect(() => {
    const url = new URL(window.location.href);
    const tokenArr = url.search.split('&');
    const accesstoken = tokenArr[0].split('=')[1].replace('%20', ' ');
    const refreshToken = tokenArr[1].split('=')[1];

    googleCallback(accesstoken, refreshToken);
    dispatch(loginStatusSlice.actions.login());
  }, []);

  return (
    <>
      <div className="googleInner">
        <Loading />
      </div>
    </>
  );
}