CREATE TABLE items (
    id UUID PRIMARY KEY,
    restaurant_id UID NOT NULL REFERENCES restaurants(id),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    available_only_at_restaurant BOOLEAN NOT NULL,
    photo_path VARCHAR(255) NOT NULL,
    last_updated TIMESTAMP WITH TIME ZONE NOT NULL
);
