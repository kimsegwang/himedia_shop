create database shoppingMall;
use shoppingMall;
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       user_id VARCHAR(30) NOT NULL,
                       my_location VARCHAR(30) NOT NULL,
                       phone VARCHAR(30) NOT NULL
);

CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          seller_id INT NOT NULL,
                          content VARCHAR(50),
                          title VARCHAR(30),
                          content_img VARCHAR(50),
                          created DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          sales_status TINYINT(1),
                          stock int(10),
                          price INT NOT NULl

);
CREATE TABLE review (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        product_id INT NOT NULL,
                        review VARCHAR(50) NULL,
                        review_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        score INT NULL,
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
show tables;
