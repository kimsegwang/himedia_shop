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

create table products
(
    id            int auto_increment
        primary key,
    category      varchar(30)                        not null,
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
    product_id      int         not null,
    user_id         varchar(30) not null,
    price           int         null,
    purchase_volume int         null
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

create table wishlist
(
    id           int auto_increment
        primary key,
    product_id   int         not null,
    user_id      varchar(30) not null,
    sales_status tinyint(1)  null
);
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');