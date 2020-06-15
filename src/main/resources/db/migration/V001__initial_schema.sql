CREATE TABLE brand
(
    b_id        BIGSERIAL PRIMARY KEY,
    b_full_name TEXT NOT NULL,
    b_url       TEXT NOT NULL UNIQUE,
    b_email     TEXT NOT NULL
);
