CREATE DATABASE rentcar

WITH ENCODING = 'UTF8'
    OWNER = "natallia.vasilyeva";

CREATE SCHEMA IF NOT EXISTS car_rent;

SET search_path TO car_rent, public;

CREATE TABLE IF NOT EXISTS brandJpa
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS priceJpa
(
    id    BIGSERIAL PRIMARY KEY,
    priceJpa NUMERIC(10, 2) NOT NULL CHECK (priceJpa > 0)
);

CREATE TABLE IF NOT EXISTS categories
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) DEFAULT 'ECONOMY' NOT NULL,
    price_id BIGINT,
    CONSTRAINT category_price_fk
        FOREIGN KEY (price_id) REFERENCES priceJpa (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS modelJpa
(
    id           BIGSERIAL PRIMARY KEY,
    brand_id     BIGINT,
    category_id  BIGINT,
    name         VARCHAR(255) NOT NULL,
    transmission VARCHAR(128),
    engine_type  VARCHAR(128),
    CONSTRAINT model_brand_fk
        FOREIGN KEY (brand_id) REFERENCES brandJpa (id)
            ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT model_category_fk
        FOREIGN KEY (category_id) REFERENCES categories (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS car
(
    id          BIGSERIAL PRIMARY KEY,
    model_id    BIGINT,
    color       VARCHAR(255),
    year        VARCHAR(8),
    car_number  VARCHAR(16),
    vin         VARCHAR(255),
    is_repaired BOOLEAN,
    image       TEXT,
    CONSTRAINT car_model_fk
        FOREIGN KEY (model_id) REFERENCES modelJpa (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(32)  NOT NULL DEFAULT 'CLIENT',
    UNIQUE (email),
    UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS orders
(
    id           BIGSERIAL PRIMARY KEY,
    date         TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    user_id      BIGINT                                    NOT NULL,
    car_id       BIGINT                                    NOT NULL,
    passport     varchar(128)                              NOT NULL,
    insurance    BOOLEAN                                   NOT NULL,
    order_status VARCHAR(32)                               NOT NULL,
    sum          NUMERIC(10, 2)                            NOT NULL,
    CONSTRAINT order_user_fk
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT orders_car_fk
        FOREIGN KEY (car_id) REFERENCES car (id)
            ON UPDATE CASCADE ON DELETE SET NULL

);

CREATE TABLE IF NOT EXISTS accidentJpa
(
    id            BIGSERIAL PRIMARY KEY,
    order_id      BIGINT                      NOT NULL,
    accident_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description   TEXT,
    damage        NUMERIC(10, 2),
    CONSTRAINT accident_order_fk
        FOREIGN KEY (order_id) REFERENCES orders (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS carrentaltime
(
    id                BIGSERIAL PRIMARY KEY,
    order_id          BIGINT                      NOT NULL,
    start_rental_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_rental_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT carrentaltime_order_fk
        FOREIGN KEY (order_id) REFERENCES orders (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS userdetails
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT                      NOT NULL,
    name              VARCHAR(128)                NOT NULL,
    surname           VARCHAR(128)                NOT NULL,
    address           VARCHAR(255)                NOT NULL,
    phone             VARCHAR(32)                 NOT NULL,
    birthday          TIMESTAMP WITHOUT TIME ZONE NOT NULL ,
    registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT userdetails_user_fk
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS driverlicense
(
    id              BIGSERIAL PRIMARY KEY,
    user_details_id BIGINT                      NOT NULL,
    number          VARCHAR(32)                 NOT NULL,
    issue_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expired_date    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT driverlicense_user_details_fk
        FOREIGN KEY (user_details_id) references userdetails (id)
            ON UPDATE CASCADE ON DELETE SET NULL
);