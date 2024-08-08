--liquibase formatted sql
--changeset catherine:populate-tables-with-data splitStatements:true endDelimiter:;

INSERT INTO customers (id, name, email)
VALUES
(1, 'Smith Paul', 'paul.smith@gmail.com'),
(2, 'Doe Jane', 'jane.doe@gmail.com');

INSERT INTO orders (id, customer_id, price, created_at)
VALUES
(1, 1, 123.77, '2024-03-23T20:31:34.873822Z'),
(2, 1, 20.5, '2024-06-10T15:06:34.873822Z'),
(3, 2, 765.0, '2024-04-24T23:21:34.873822Z');
