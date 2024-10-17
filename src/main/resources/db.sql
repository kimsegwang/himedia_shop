create database shoppingMall;
use shoppingMall;
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        password VARCHAR(255) NOT NULL,
                        user_id VARCHAR(30) NOT NULL,
                        my_location VARCHAR(30) NOT NULL,
                        phone VARCHAR(30) NOT NULL,
                        name VARCHAR(30) NOT NULL
);
CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          category varchar(30) not null ,
                          seller_id VARCHAR(50) NOT NULL,
                          content VARCHAR(50),
                          title VARCHAR(30),
                          content_img VARCHAR(255) NOT NULL,
                          created DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          sales_status TINYINT(1),
                          stock int(10),
                          price INT NOT NULl,
                          temperature int NOT NULl,
                          precipitation varchar(50) NOT NULl

);

CREATE TABLE review (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        product_id INT NOT NULL,
                        review VARCHAR(50) NULL,
                        review_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        score INT NULL
);

CREATE TABLE wishlist (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          product_id INT NOT NULL,
                          user_id VARCHAR(30) NOT NULL,
                          sales_status TINYINT(1)

);

CREATE TABLE purchasehistory (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 product_id INT NOT NULL,
                                 user_id VARCHAR(30) NOT NULL

);


CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES member(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

