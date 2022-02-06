DROP TABLE IF EXISTS CUSTOMER;

CREATE TABLE "customer"(
    id INTEGER AUTO_INCREMENT PRIMARY KEY ,
    name varchar(250) NOT NULL ,
    lastname varchar(250) NOT NULL ,
    email varchar(250) NOT NULL ,
    phone varchar(10) NOT NULL ,
    membership_date date NOT NULL
);

DROP TABLE IF EXISTS "book";

CREATE TABLE "book"(
                         id INT AUTO_INCREMENT PRIMARY KEY ,
                         book_name varchar(250) NOT NULL ,
                         isbn_number varchar(250) NOT NULL ,
                         author_name varchar(250) NOT NULL ,
                         publisher varchar(250) NOT NULL ,
                         record_date date NOT NULL,
                         publish_year INT NOT NULL,
                         stock_size INT NOT NULL,
                         is_available BOOLEAN DEFAULT FALSE,
                         create_date date NOT NULL,
                         update_date date NOT NULL
);

DROP TABLE IF EXISTS "order";

CREATE TABLE IF NOT EXISTS "order"(
                     id INTEGER PRIMARY KEY AUTO_INCREMENT ,
                     customer_id BIGINT NOT NULL ,
                     name varchar(250) NOT NULL ,
                     order_date date NOT NULL ,
                     deliver_date date NOT NULL ,
                     create_date date NOT NULL,
                     update_date date NOT NULL
);