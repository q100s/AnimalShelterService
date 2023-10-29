-- liquibase formatted sql

-- changeset avolgin:1
CREATE TABLE request (
    id  BIGSERIAL,
    chat_id BIGINT,
    request_time TIMESTAMP,
    request_text VARCHAR,
    PRIMARY KEY(id)
);