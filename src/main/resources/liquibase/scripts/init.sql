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
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT,
    telegram_id BIGINT,
    request_text VARCHAR
);
--liquibase formatted sql

--changeset markovka77:3
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT,
    telegram_nick VARCHAR,
    full_name VARCHAR,
    phone_number VARCHAR,
    user_type VARCHAR, -- enum UserType
    ending_of_trial_period DATE,
    pet_id BIGINT
);
--liquibase formatted sql

--changeset markovka77:4
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    report_date DATE,
    photo_path VARCHAR,
    text VARCHAR,
    telegram_id BIGINT
);

--liquibase formatted sql

--changeset q100s:2
CREATE TABLE pet (
    id BIGSERIAL PRIMARY KEY,
    pet_type VARCHAR,
    pet_name VARCHAR,
    photo_url_path VARCHAR,
    users_id BIGINT
);