CREATE SCHEMA IF NOT EXISTS "url-shortener";
SET SCHEMA "url-shortener";
CREATE TABLE IF NOT EXISTS urls (
    id SERIAL PRIMARY KEY,
    original_url VARCHAR(1000),
    created_on timestamp
);

CREATE SEQUENCE IF NOT EXISTS seq_unique_id
INCREMENT 1
START WITH 10000000;

