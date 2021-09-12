DROP TABLE IF EXISTS Orders;

CREATE TABLE Orders (
    id VARCHAR(255) PRIMARY KEY,
    created_timestamp TIMESTAMP NOT NULL,
    order_summary CLOB(1000) NOT NULL
);

--Sample stuff to work with
INSERT INTO Orders VALUES ('1', CURRENT_TIMESTAMP, '{\"some_key\":\"some_value\"}');
INSERT INTO Orders VALUES ('2', CURRENT_TIMESTAMP, '{\"some_key2\":\"some_value2\"}');