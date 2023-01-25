import { useEffect } from 'react';
import Loading from '../components/common/Loading';
import { googleCallback } from '../util/oauthGoogle';

export default function OauthGoogle() {
  useEffect(() => {
    const url = new URL(window.location.href);
    const tokenArr = url.search.split('&');
    const accesstoken = tokenArr[0].split('=')[1].replace('%20', ' ');
    const refreshToken = tokenArr[1].split('=')[1];

    googleCallback(accesstoken, refreshToken);
  }, []);

  return (
    <>
      <div className="googleInner">
        <Loading />
      </div>
    </>
  );
}