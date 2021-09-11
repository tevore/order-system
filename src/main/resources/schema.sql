CREATE TABLE Orders (
    id VARCHAR(255) PRIMARY KEY,
    created_timestamp TIMESTAMP NOT NULL,
    order_summary JSON NOT NULL
);