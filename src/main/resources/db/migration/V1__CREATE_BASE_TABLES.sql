CREATE TABLE users (
  id            UUID PRIMARY KEY,
  email         VARCHAR(255) NOT NULL UNIQUE,
  name          VARCHAR(255) NOT NULL
  password      VARCHAR(255) NOT NULL,
  role          VARCHAR(50)  NOT NULL,
  created_at    TIMESTAMPTZ  NOT NULL,
  updated_at    TIMESTAMPTZ  NOT NULL
);

CREATE TABLE products (
  id               UUID PRIMARY KEY,
  name             VARCHAR(255) NOT NULL UNIQUE,
  description      TEXT,
  quantity         BIGINT       NOT NULL CHECK (quantity >= 0),
  minimum_quantity BIGINT       NOT NULL DEFAULT 0 CHECK (minimum_quantity >= 0),
  active           BOOLEAN      NOT NULL DEFAULT TRUE,
  created_at       TIMESTAMPTZ  NOT NULL,
  updated_at       TIMESTAMPTZ
);

CREATE TABLE stock_movements (
  id          UUID PRIMARY KEY,
  product_id  UUID        NOT NULL,
  type        VARCHAR(10) NOT NULL,
  quantity    BIGINT      NOT NULL CHECK (quantity > 0),
  reason      VARCHAR(255) NOT NULL,
  created_by  UUID        NOT NULL,
  created_at  TIMESTAMPTZ NOT NULL,

  CONSTRAINT fk_movements_product
    FOREIGN KEY (product_id) REFERENCES products(id),

  CONSTRAINT fk_movements_user
    FOREIGN KEY (created_by) REFERENCES users(id),

  CONSTRAINT chk_movement_type
    CHECK (type IN ('IN', 'OUT'))
);


