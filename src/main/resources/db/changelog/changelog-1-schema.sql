--liquibase formatted sql
--changeset catherine:create-multiple-tables splitStatements:true endDelimiter:;

CREATE TABLE customers (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
customer_id BIGINT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
price DOUBLE PRECISION NOT NULL,
created_at TIMESTAMP NOT NULL
);

ALTER SEQUENCE customers_id_seq RESTART WITH 101;
ALTER SEQUENCE orders_id_seq RESTART WITH 101;