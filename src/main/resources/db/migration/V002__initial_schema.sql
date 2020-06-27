CREATE TABLE brand
(
    b_id        BIGSERIAL PRIMARY KEY,
    b_full_name TEXT NOT NULL,
    b_url       TEXT NOT NULL UNIQUE,
    b_email     TEXT NOT NULL
);

CREATE TABLE brand_token
(
    t_id    BIGSERIAL PRIMARY KEY,
    t_b_id  BIGINT REFERENCES brand (b_id) UNIQUE,
    t_token TEXT NOT NULL,
    t_scope TEXT NOT NULL
);

CREATE TABLE state_code
(
    sc_id  BIGSERIAL PRIMARY KEY,
    sc_url TEXT NOT NULL
);

CREATE TABLE brand_products
(
    bp_id              BIGSERIAL PRIMARY KEY,
    bp_b_id            BIGINT REFERENCES brand (b_id),
    bp_shopify_id      BIGINT NOT NULL,
    bp_title           TEXT,
    bp_body_html       TEXT,
    bp_vendor          TEXT,
    bp_product_type    TEXT,
    bp_created_at      TIMESTAMPTZ,
    bp_handle          TEXT,
    bp_updated_at      TIMESTAMPTZ,
    bp_published_at    TIMESTAMPTZ,
    bp_template_suffix TEXT,
    bp_published_scope TEXT,
    bp_tags            TEXT,
    bp_variants        JSONB,
    UNIQUE (bp_b_id, bp_shopify_id)
);

CREATE TABLE brand_payment
(
    p_id            BIGSERIAL PRIMARY KEY,
    p_b_id          BIGINT REFERENCES brand (b_id) UNIQUE,
    p_customer_id   TEXT NOT NULL UNIQUE,
    p_client_secret TEXT NOT NULL,
    p_commission    INT  NOT NULL CHECK ( p_commission > 0 AND p_commission < 100 )
);
