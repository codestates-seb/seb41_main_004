-- 데이터 십입 : 문화/예술
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (1, '문화/예술');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('전시', 1);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('영화', 1);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('뮤지컬', 1);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('공연', 1);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('디자인', 1);

-- 소분류 데이터 십입 : 운동/액티비티
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (2, '운동/액티비티');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('클라이밍', 2);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('등산', 2);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('헬스', 2);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('필라테스', 2);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('골프', 2);

-- 소분류 데이터 십입 : 푸드/드링크
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (3, '푸드/드링크');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('맛집투어', 3);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('카페', 3);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('와인', 3);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('커피', 3);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('디저트', 3);

-- 소분류 데이터 십입 : 취미
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (4, '취미');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('보드게임', 4);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('사진', 4);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('방탈출', 4);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('VR', 4);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('음악감상', 4);

-- 소분류 데이터 십입 : 여행/동행
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (5, '여행/동행');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('복합문화공간', 5);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('테마파크', 5);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('피크닉', 5);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('드라이브', 5);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('캠핑', 5);

-- 소분류 데이터 십입 : 창작
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (6, '창작');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('글쓰기', 6);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('드로잉', 6);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('영상편집', 6);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('공예', 6);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('DIY', 6);

-- 소분류 데이터 십입 : 성장/자기계발
INSERT INTO CATEGORY_LARGE (CATEGORY_LARGE_ID, CATEGORY_NAME) VALUES (7, '성장/자기계발');
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('습관만들기', 7);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('챌린지', 7);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('독서', 7);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('스터디', 7);
INSERT INTO CATEGORY_SMALL (CATEGORY_NAME, CATEGORY_LARGE_ID) VALUES ('외국어', 7);

-- DUMMY FILE DATA
INSERT INTO FILE_INFO (FILE_NAME, FILE_URL) VALUES ('3b565335-585f-4beb-9396-6bffd665d92c.jpeg', '/images/club_banner');
INSERT INTO FILE_INFO (FILE_NAME, FILE_URL) VALUES ('6f536ea2-2fea-4461-bbdc-be6c50670948.png', '/images/club_banner');
INSERT INTO FILE_INFO (FILE_NAME, FILE_URL) VALUES ('4e512441-3121-ab12-adaa-ad6c1a3b3315.png', '/images/member_profile');
INSERT INTO FILE_INFO (FILE_NAME, FILE_URL) VALUES ('6f536ea2-2fea-4461-bbdc-be6c50670948.jpg', '/images/member_profile');
INSERT INTO FILE_INFO (FILE_NAME, FILE_URL) VALUES ('a7d3fdeeac6c9538b4b1372efa839e27631e284.jpeg', '/images/member_profile');

-- DUMMY USER
INSERT INTO MEMBER (EMAIL, PASSWORD, NICKNAME, MEMBER_STATUS, BIRTH_YEAR, REPUTATION, FILE_INFO_ID)
VALUES ('admin_test@hello.com', '{bcrypt}$2a$10$VG83rqZddIfFCzHu4zCIwulurZs4xUED/dLnnFdbiyHodunY8pDyC', 'admin_test', 'ACTIVE', 2000, 10, 4);
INSERT INTO MEMBER (EMAIL, PASSWORD, NICKNAME, MEMBER_STATUS, BIRTH_YEAR, REPUTATION, FILE_INFO_ID)
VALUES ('user_test@hello.com', '{bcrypt}$2a$10$VG83rqZddIfFCzHu4zCIwulurZs4xUED/dLnnFdbiyHodunY8pDyC', 'user_test', 'ACTIVE', 2000, 10, 5);
INSERT INTO MEMBER (EMAIL, PASSWORD, NICKNAME, MEMBER_STATUS, BIRTH_YEAR, ABOUT_ME, REPUTATION, FILE_INFO_ID)
VALUES ('kinstub1@hello.com', '{bcrypt}$2a$10$VG83rqZddIfFCzHu4zCIwulurZs4xUED/dLnnFdbiyHodunY8pDyC', 'kimstub1', 'ACTIVE', 1995,'kimstub1의 자기소개 뿌잉뿌잉', 10, 3);

-- DUMMY USER ROLE
INSERT INTO MEMBER_ROLES (MEMBER_MEMBER_ID, ROLES) VALUES (1, 'ADMIN');
INSERT INTO MEMBER_ROLES (MEMBER_MEMBER_ID, ROLES) VALUES (1, 'USER');
INSERT INTO MEMBER_ROLES (MEMBER_MEMBER_ID, ROLES) VALUES (2, 'USER');
INSERT INTO MEMBER_ROLES (MEMBER_MEMBER_ID, ROLES) VALUES (3, 'USER');

-- DUMMY USER MEMBER_CATEGORY
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (1,4);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (1,16);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (1,25);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (1,33);

INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (2,1);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (2,7);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (2,19);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (2,21);

INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (3,7);
INSERT INTO MEMBER_CATEGORY (MEMBER_ID, CATEGORY_SMALL_ID) VALUES (3,13);

-- DUMMY CLUB DATA 35개
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 3, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate(),
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 1, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('신나는 아지트', '신나는 아지트 입니다! 모두들 어서 오세요.', 4, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 2, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('행복한 아지트', '행복한 아지트 입니다! 모두들 어서 오세요.', 5, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+2,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 3, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('좋은 아지트', '좋은 아지트 입니다! 모두들 어서 오세요.', 6, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+3,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 4, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('재밌는 아지트', '재밌는 아지트 입니다! 모두들 어서 오세요.', 7, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+4,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 5, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('착한 아지트', '착한 아지트 입니다! 모두들 어서 오세요.', 8, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+5,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 6, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('보고싶은 아지트', '보고싶은 아지트 입니다! 모두들 어서 오세요.', 9, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate(),
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 7, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 10, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 8, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('재밌는 아지트', '재밌는 아지트 입니다! 모두들 어서 오세요.', 11, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+2,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 9, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('행복한 아지트', '행복한 아지트 입니다! 모두들 어서 오세요.', 12, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+3,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 10, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('정겨운 아지트', '정겨운 아지트 입니다! 모두들 어서 오세요.', 13, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+4,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 11, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('신나는 아지트', '신나는 아지트 입니다! 모두들 어서 오세요.', 7, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+5,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 12, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('좋은 아지트', '좋은 아지트 입니다! 모두들 어서 오세요.', 14, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate(),
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 13, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('정겨운 아지트', '정겨운 아지트 입니다! 모두들 어서 오세요.', 15, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 14, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('맛있는 아지트', '맛있는 아지트 입니다! 모두들 어서 오세요.', 16, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+2,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 15, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('행복한 아지트', '행복한 아지트 입니다! 모두들 어서 오세요.', 7, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+3,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 16, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('재밌는 아지트', '재밌는 아지트 입니다! 모두들 어서 오세요.', 17, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+4,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 17, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 18, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+5,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 18, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 19, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate(),
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 19, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('신나는 아지트', '신나는 아지트 입니다! 모두들 어서 오세요.', 20, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 20, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('정겨운 아지트', '정겨운 아지트 입니다! 모두들 어서 오세요.', 3, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+2,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 21, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('보고싶은 아지트', '보고싶은 아지트 입니다! 모두들 어서 오세요.', 4, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+3,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 22, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('재밌는 아지트', '재밌는 아지트 입니다! 모두들 어서 오세요.', 5, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+4,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 23, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('행복한 아지트', '행복한 아지트 입니다! 모두들 어서 오세요.', 6, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+5,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 24, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 7, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate(),
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 25, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('신나는 아지트', '신나는 아지트 입니다! 모두들 어서 오세요.', 8, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 26, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('좋은 아지트', '좋은 아지트 입니다! 모두들 어서 오세요.', 9, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate()+2,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 27, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('맛있는 아지트', '맛있는 아지트 입니다! 모두들 어서 오세요.', 10, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+3,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 28, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('색다른 아지트', '색다른 아지트 입니다! 모두들 어서 오세요.', 7, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+4,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 29, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('색다른 아지트', '색다른 아지트 입니다! 모두들 어서 오세요.', 11, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+5,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 30, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('내맘대로 아지트', '내맘대로 아지트 입니다! 모두들 어서 오세요.', 12, 5000, 'APPROVAL', '1990', '2010', 'FEMALE_ONLY', curdate(),
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 31, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('너무많아 아지트', '너무많아 아지트 입니다! 모두들 어서 오세요.', 13, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+1,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 32, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('즐거운 아지트', '즐거운 아지트 입니다! 모두들 어서 오세요.', 14, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+2,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 33, 1, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('색다른 아지트', '색다른 아지트 입니다! 모두들 어서 오세요.', 15, 5000, 'APPROVAL', '1990', '2010', 'ALL', curdate()+3,
        '23:59:00', 'offline', '서울시 강남구', '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 34, 2, 1);
INSERT INTO CLUB (CLUB_NAME, CLUB_INFO, MEMBER_LIMIT, FEE, JOIN_METHOD, BIRTH_YEAR_MIN, BIRTH_YEAR_MAX, GENDER_RESTRICTION,
                  MEETING_DATE, MEETING_TIME, IS_ONLINE, LOCATION, JOIN_QUESTION, CLUB_STATUS,CATEGORY_SMALL_ID, FILE_INFO_ID, HOST_ID)
VALUES ('맛있는 아지트', '맛있는 아지트 입니다! 모두들 어서 오세요.', 16, 5000, 'APPROVAL', '1990', '2010', 'MALE_ONLY', curdate()+4,
        '23:59:00', 'online', null, '늦지말고 참여해주세요~', 'CLUB_ACTIVE', 35, 1, 1);

-- DUMMY CLUBMEMBER DATA
INSERT INTO CLUB_MEMBER (CLUB_MEMBER_STATUS, JOIN_ANSWER, CLUB_ID, MEMBER_ID) VALUES ('CLUB_WAITING', '1번 아지트 저도 참가할래요!', 1, 2);
INSERT INTO CLUB_MEMBER (CLUB_MEMBER_STATUS, JOIN_ANSWER, CLUB_ID, MEMBER_ID) VALUES ('CLUB_WAITING', '3번 아지트 저도 참가할래요!', 3, 2);
INSERT INTO CLUB_MEMBER (CLUB_MEMBER_STATUS, JOIN_ANSWER, CLUB_ID, MEMBER_ID) VALUES ('CLUB_WAITING', '5번 아지트 저도 참가할래요!', 5, 2);
