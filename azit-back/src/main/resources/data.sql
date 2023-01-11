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