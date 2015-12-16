CREATE TABLE vacation_properties (
    id          SERIAL UNIQUE NOT NULL PRIMARY KEY,
    description VARCHAR(100)  NOT NULL,
    image_url   VARCHAR(255)  NOT NULL,
    user_id     INT4          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);