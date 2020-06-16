CREATE TABLE brand
(
    b_id        BIGSERIAL PRIMARY KEY,
    b_full_name TEXT NOT NULL,
    b_url       TEXT NOT NULL UNIQUE,
    b_email     TEXT NOT NULL
);

CREATE TABLE brand_token
(
    t_id         BIGSERIAL PRIMARY KEY,
    t_b_id       BIGINT REFERENCES brand (b_id),
    t_token      TEXT NOT NULL,
    t_scope      TEXT NOT NULL,
    t_expires_in BIGINT
);

CREATE TABLE state_code
(
    sc_id   BIGSERIAL PRIMARY KEY,
    sc_b_id BIGINT REFERENCES brand (b_id)
);
