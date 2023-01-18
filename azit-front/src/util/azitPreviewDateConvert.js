export const genderConvert = (data) => {
  if (data === "MALE_ONLY ") {
    return "남자";
  } else if (data === "FEMALE_ONLY ") {
    return "여자";
  } else {
    return "제한없음";
  }
};

export const isOnlineConvert = (data, item) => {
  if (data === "offline") {
    return item;
  } else if (data === "online") {
    return "온라인";
  }
};

export const categoryConvert = (data) => {
  if (data === 1) {
    return "전시";
  } else if (data === 2) {
    return "영화";
  } else if (data === 3) {
    return "뮤지컬";
  } else if (data === 4) {
    return "공연";
  } else if (data === 5) {
    return "디자인";
  } else if (data === 6) {
    return "클라이밍";
  } else if (data === 7) {
    return "등산";
  } else if (data === 8) {
    return "헬스";
  } else if (data === 9) {
    return "필라테스";
  } else if (data === 10) {
    return "골프";
  } else if (data === 11) {
    return "맛집투어";
  } else if (data === 12) {
    return "카페";
  } else if (data === 13) {
    return "와인";
  } else if (data === 14) {
    return "커피";
  } else if (data === 15) {
    return "디저트";
  } else if (data === 16) {
    return "보드게임";
  } else if (data === 17) {
    return "사진";
  } else if (data === 18) {
    return "방탈출";
  } else if (data === 19) {
    return "VR";
  } else if (data === 20) {
    return "음악감상";
  } else if (data === 21) {
    return "복합문화공간";
  } else if (data === 22) {
    return "테마파크";
  } else if (data === 23) {
    return "피크닉";
  } else if (data === 24) {
    return "드라이브";
  } else if (data === 25) {
    return "캠핑";
  } else if (data === 26) {
    return "글쓰기";
  } else if (data === 27) {
    return "드로잉";
  } else if (data === 28) {
    return "영상편집";
  } else if (data === 29) {
    return "공예";
  } else if (data === 30) {
    return "DIY";
  } else if (data === 31) {
    return "습관만들기";
  } else if (data === 32) {
    return "챌린지";
  } else if (data === 33) {
    return "독서";
  } else if (data === 34) {
    return "스터디";
  } else if (data === 35) {
    return "외국어";
  }
};

export const MaxAgeConvert = (data) => {
  if (data) {
    return data + "이상";
  } else {
    return "제한없음";
  }
};

export const MinAgeConvert = (data) => {
  if (data) {
    return data + "이하";
  } else {
    return;
  }
};

// 숫자 단위 표현 함수
export const PriceFormat = (str) => {
  const comma = (str) => {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, "$1,");
  };
  const uncomma = (str) => {
    str = String(str);
    return str.replace(/[^\d]+/g, "");
  };
  return comma(uncomma(str));
};

export const timeConvert = (data) => {
  let time = Number(data.split(":")[0]); // 11
  let min = data.split(":")[1]; // "30"

  if (time <= 9) {
    return " 오전 0" + String(time) + " : " + min;
  } else if (time < 12) {
    return "오전 " + String(time) + " : " + min;
  } else if (time === 12) {
    return "오후 " + String(time) + " : " + min;
  } else if (time <= 21) {
    return "오후 0" + String(time - 12) + " : " + min;
  } else if (time >= 22) {
    return "오후 " + String(time - 12) + " : " + min;
  }
};
