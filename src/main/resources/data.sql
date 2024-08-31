-- 제품들 생성
insert into tb_product(product_id, name, price, stock, delete_yn)
values (1, '사과', 1000, 6, 'N');

insert into tb_product(product_id, name, price, stock, delete_yn)
values (2, '배', 2000, 6, 'N');

insert into tb_product(product_id, name, price, stock, delete_yn)
values (3, '오렌지', 3000, 6, 'N');

insert into tb_country(country_id, name)
values(1, '한국');

-- 사용자1 생성
insert into tb_user(user_id, country_id, name, registration_number, birth_ymd, phone_number, point)
values(1, 1, '구매자1', 1,'19960101', '01012345678', 0);

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(1, 1, 1, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(2, 1, 2, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(3, 1, 3, 3, 'PAY_BEFORE');

-- 사용자2 생성
insert into tb_user(user_id, country_id, name, registration_number, birth_ymd, phone_number, point)
values(2, 1, '구매자2', 2,'19960101', '01012345678', 0);

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(4, 2, 1, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(5, 2, 2, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(6, 2, 3, 3, 'PAY_BEFORE');

-- 사용자3 생성
insert into tb_user(user_id, country_id, name, registration_number, birth_ymd, phone_number, point)
values(3, 1, '구매자3', 3,'19960101', '01012345678', 0);

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(7, 3, 1, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(8, 3, 2, 3, 'PAY_BEFORE');

insert into tb_order(order_id, user_id, product_id, quantity, order_status)
values(9, 3, 3, 3, 'PAY_BEFORE');
