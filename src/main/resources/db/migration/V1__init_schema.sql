CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE customer (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          document VARCHAR(14) NOT NULL UNIQUE,
                          full_name VARCHAR(160) NOT NULL,
                          email VARCHAR(160) NOT NULL UNIQUE,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE account (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         customer_id UUID NOT NULL REFERENCES customer(id),
                         number VARCHAR(20) NOT NULL UNIQUE,
                         balance NUMERIC(19,4) NOT NULL DEFAULT 0,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         version BIGINT NOT NULL DEFAULT 0,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                         updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_account_customer ON account(customer_id);

CREATE TABLE transactions (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              from_account_id UUID REFERENCES account(id),
                              to_account_id UUID REFERENCES account(id),
                              amount NUMERIC(19,4) NOT NULL,
                              type VARCHAR(20) NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              idempotency_key VARCHAR(80) UNIQUE,
                              description VARCHAR(255),
                              created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_tx_from ON transactions(from_account_id);
CREATE INDEX idx_tx_to ON transactions(to_account_id);
CREATE INDEX idx_tx_idem ON transactions(idempotency_key) WHERE idempotency_key IS NOT NULL;