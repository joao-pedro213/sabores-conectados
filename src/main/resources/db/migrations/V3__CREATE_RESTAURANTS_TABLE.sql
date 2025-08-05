CREATE TABLE restaurants (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    business_hours JSONB NOT NULL,
    owner_id UUID NOT NULL REFERENCES users(id),
    last_updated TIMESTAMP WITH TIME ZONE NOT NULL
);
