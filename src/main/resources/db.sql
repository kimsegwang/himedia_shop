create table coordinate
(
    id int auto_increment
        primary key,
    nx int not null,
    ny int not null
);

create table member
(
    id          bigint auto_increment
        primary key,
    password    varchar(255) not null,
    user_id     varchar(30)  not null,
    my_location varchar(30)  not null,
    phone       varchar(30)  not null,
    name        varchar(30)  not null
);

create table payment
(
    id           int auto_increment
        primary key,
    user_id      varchar(20)                          null,
    deposit      int        default 0                 null,
    withdrawal   int        default 0                 null,
    balance      int                                  null,
    payment_date datetime   default CURRENT_TIMESTAMP null,
    is_purchased tinyint(1) default 0                 null
);

create table products
(
    id            int auto_increment
        primary key,
    category      varchar(30)                        not null,
    sub_category  varchar(255)                       null,
    seller_id     varchar(50)                        not null,
    content       varchar(50)                        null,
    title         varchar(30)                        null,
    content_img   varchar(255)                       not null,
    created       datetime default CURRENT_TIMESTAMP null,
    updated       datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    sales_status  tinyint(1)                         null,
    stock         int                                null,
    price         int                                not null,
    temperature   int                                not null,
    precipitation int                                not null
);

create table purchasehistory
(
    id              int auto_increment
        primary key,
    product_id      int                      not null,
    user_id         varchar(30)              not null,
    price           int                      null,
    purchase_volume int                      null,
    review_date     datetime default (now()) null
);

create table review
(
    id          int auto_increment
        primary key,
    product_id  int                                not null,
    user_id     varchar(30)                        not null,
    title       varchar(30)                        not null,
    review      varchar(50)                        null,
    review_date datetime default CURRENT_TIMESTAMP null,
    score       float                              null,
    review_img  varchar(255)                       null
);

create index idx_product_id_id
    on review (product_id, id);

create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(50) not null,
    constraint name
        unique (name)
);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint user_roles_ibfk_1
        foreign key (user_id) references member (id)
            on delete cascade,
    constraint user_roles_ibfk_2
        foreign key (role_id) references roles (id)
            on delete cascade
);

create index role_id
    on user_roles (role_id);

create table wheatherapi
(
    id          int auto_increment
        primary key,
    Information text not null,
    nx          int  null,
    ny          int  null,
    basedate    int  null
);

create table wishlist
(
    id         int auto_increment
        primary key,
    product_id int         not null,
    user_id    varchar(30) not null
);

 
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO user_roles (user_id, role_id) VALUES (5,1);



use shoppingmall;
insert into coordinate(id, nx, ny) values (1, 63,126);
insert into coordinate(id, nx, ny) values (2, 98,77);
insert into coordinate(id, nx, ny) values (3, 89,90);
insert into coordinate(id, nx, ny) values (4, 52,125);
insert into coordinate(id, nx, ny) values (5, 60,74);
insert into coordinate(id, nx, ny) values (6, 68,100);
insert into coordinate(id, nx, ny) values (7, 104,83);
insert into coordinate(id, nx, ny) values (8, 65,107);
insert into coordinate(id, nx, ny) values (9, 63,124);
insert into coordinate(id, nx, ny) values (10, 81,118);
insert into coordinate(id, nx, ny) values (11, 61,104);
insert into coordinate(id, nx, ny) values (12, 73,66);
insert into coordinate(id, nx, ny) values (13, 94,106);
insert into coordinate(id, nx, ny) values (14, 90,76);
insert into coordinate(id, nx, ny) values (15, 53,38);
insert into coordinate(id, nx, ny) values (16, 28,8);
insert into coordinate(id, nx, ny) values (17, 77,122);
insert into coordinate(id, nx, ny) values (18, 63,89);

