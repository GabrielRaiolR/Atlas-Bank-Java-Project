CREATE TABLE card (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      account_id UUID NOT NULL REFERENCES account(id),
                      credit_limit NUMERIC(19,4) NOT NULL CHECK (credit_limit >= 0),
                      available_limit NUMERIC(19,4) NOT NULL CHECK (available_limit >= 0),
                      status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                      pan_last_four VARCHAR(4) NOT NULL,
                      version BIGINT NOT NULL DEFAULT 0,
                      created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                      updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                      CONSTRAINT card_available_le_limit CHECK (available_limit <= credit_limit)
);

CREATE INDEX idx_card_account ON card(account_id);