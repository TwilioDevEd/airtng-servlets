CREATE TABLE users (
    id           SERIAL UNIQUE NOT NULL PRIMARY KEY,
    name         VARCHAR(100)  NOT NULL,
    email        VARCHAR(100)  NOT NULL,
    password     VARCHAR(100)  NOT NULL,
    area_code    VARCHAR(3)    NULL,
    phone_number VARCHAR(15)   NOT NULL
);