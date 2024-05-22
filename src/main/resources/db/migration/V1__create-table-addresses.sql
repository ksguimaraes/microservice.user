CREATE TABLE addresses (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number int NOT NULL,
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(8) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);