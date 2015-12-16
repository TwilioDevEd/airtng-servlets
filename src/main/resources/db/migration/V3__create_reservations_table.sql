CREATE TABLE reservations (
    id                   SERIAL UNIQUE NOT NULL PRIMARY KEY,
    full_phone_number    VARCHAR(100)  NOT NULL,
    message              VARCHAR(100)  NOT NULL,
    status               INT2          NOT NULL,
    user_id              INT4          NOT NULL,
    vacation_property_id INT4          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (vacation_property_id) REFERENCES vacation_properties (id)
);