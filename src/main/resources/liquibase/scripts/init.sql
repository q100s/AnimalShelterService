-- liquibase formatted sql

-- changeset q100s:1
CREATE TABLE shelter (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR,
    security_phone_number VARCHAR,
    schedule VARCHAR,
    safety_measures VARCHAR,
    direction_path_file VARCHAR,
    shelter_type VARCHAR
);
-- liquibase formatted sql

-- changeset avolgin:2
CREATE TABLE request (
    id  BIGSERIAL,
    chat_id BIGINT,
    request_time TIMESTAMP,
    request_text VARCHAR,
    PRIMARY KEY(id)
);