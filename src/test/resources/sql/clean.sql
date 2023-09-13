-- 외래키 제약 조건 무시하고 데이터 삭제
SET REFERENTIAL_INTEGRITY FALSE;

DELETE FROM t_token;
DELETE FROM t_member;
DELETE FROM t_additional_info;
DELETE FROM t_image;
DELETE FROM t_like_review;
DELETE FROM t_review;
DELETE FROM t_product_option;
DELETE FROM t_promotion;
DELETE FROM t_product;

-- 외래키 제약 조건 다시 활성화
SET REFERENTIAL_INTEGRITY TRUE;
