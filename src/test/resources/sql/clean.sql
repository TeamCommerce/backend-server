SET FOREIGN_KEY_CHECKS = 0;

DELETE
FROM t_member;
DELETE
FROM t_additional_info;
DELETE
FROM t_image;
DELETE
FROM t_like_review;
DELETE
FROM t_review;
DELETE
FROM t_product_option;
DELETE
FROM t_promotion;
DELETE
FROM t_product;

SET FOREIGN_KEY_CHECKS = 1;
