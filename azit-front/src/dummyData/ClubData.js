import TestAzitImg from "../images/testAzitImg.png";
import TestProfileImg from "../images/testProfileImg.png";

export const ClubData = [
  {
    clubId: 1,
    clubName: "재밌는 아지트",
    clubInfo: "재밌는 아지트입니다.",
    meetingDate: "2023-01-20",
    meetingTime: "23:59:00",
    clubImg: TestAzitImg,
    memberLimit: 20,
    categorySmall: {
      categoryLargeId: 1,
      categoryName: "전시",
      categorySmallId: 1,
    },
    host: {
      memberId: 1,
      nickname: "여덟글자의호스트",
    },
    fee: 10000,
    joinMethod: "APPROVAL",
    birthYearMin: "1990",
    birthYearMax: "2000",
    genderRestriction: "ALL",
    isOnline: "online",
    joinQuestion: "재밌게 즐기실거죠?",
    clubMembers: [
      {
        clubMemberId: 1,
        clubMemberStatus: "CLUB_WAITING",
        joinAnswer: "1번 아지트 저도 참가할래요!",
        member: {
          email: "user_test@hello.com",
          memberId: 2,
          nickname: "user_test",
        },
      },
    ],
  },
  {
    clubId: 2,
    clubName: "재밌는 아지트2",
    clubInfo: "재밌는 아지트입니다.",
    clubImg: TestAzitImg,
    memberLimit: 20,
    meetingDate: "2023-01-20",
    meetingTime: "23:59:00",
    location: "서울시 강남구",
    categorySmall: {
      categoryLargeId: 1,
      categoryName: "전시",
      categorySmallId: 1,
    },
    host: {
      memberId: 1,
      nickname: "여덟글자의호스트",
    },
    fee: 10000,
    joinMethod: "APPROVAL",
    birthYearMin: "1990",
    birthYearMax: "2000",
    genderRestriction: "ALL",
    isOnline: false,
    joinQuestion: "재밌게 즐기실거죠?",
    clubMembers: [
      {
        clubMemberId: 1,
        clubMemberStatus: "CLUB_WAITING",
        joinAnswer: "1번 아지트 저도 참가할래요!",
        member: {
          email: "user_test@hello.com",
          memberId: 2,
          nickname: "user_test",
        },
      },
    ],
  },
  {
    clubId: 3,
    clubName: "재밌는 아지트3",
    clubInfo: "재밌는 아지트입니다.",
    clubImg: TestAzitImg,
    memberLimit: 20,
    meetingDate: "2023-01-20",
    meetingTime: "23:59:00",
    location: "서울시 강남구",
    categorySmall: {
      categoryLargeId: 1,
      categoryName: "전시",
      categorySmallId: 1,
    },
    host: {
      memberId: 1,
      nickname: "여덟글자의호스트",
    },
    fee: 10000,
    joinMethod: "APPROVAL",
    birthYearMin: "1990",
    birthYearMax: "2000",
    genderRestriction: "ALL",
    isOnline: false,
    joinQuestion: "재밌게 즐기실거죠?",
    clubMembers: [
      {
        clubMemberId: 1,
        clubMemberStatus: "CLUB_WAITING",
        joinAnswer: "1번 아지트 저도 참가할래요!",
        member: {
          email: "user_test@hello.com",
          memberId: 2,
          nickname: "user_test",
        },
      },
    ],
  },
];
