create database PosSystem;
show databases;
use PosSystem;

CREATE TABLE customer(
                         id VARCHAR(15) NOT NULL PRIMARY KEY ,
                         name VARCHAR(100) NOT NULL );

CREATE TABLE order_table
(
    order_id      INT AUTO_INCREMENT PRIMARY KEY,
    cusId VARCHAR(20) NOT NULL ,
    order_details VARCHAR(100) NOT NULL,

    CONSTRAINT nic_fk FOREIGN KEY (cusId) REFERENCES customer(id)
);


CREATE TABLE items(
                      item_id VARCHAR(15) NOT NULL PRIMARY KEY ,
                      item_name VARCHAR(50) NOT NULL ,
                      order_id INT NOT NULL,
                      CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES order_table(order_id)

);
alter table order_table
    add date DATE NOT NULL ;
