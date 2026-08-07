CREATE TABLE company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(255) NOT NULL,

    phone_number_id VARCHAR(100) NOT NULL UNIQUE,

    business_account_id VARCHAR(100) NOT NULL,

    access_token TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);