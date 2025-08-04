INSERT INTO users (id, name, type, email, login, password, address, last_updated)
VALUES
    (gen_random_uuid(), 'Alice', 'Restaurant Owner', 'alice.9@domain.com', 'alice.9', '4l1c3P4ss!', '654 Davis Vista, MacGyverburgh, MD 43201', '2025-05-09');

INSERT INTO restaurants (id, name, address, type, business_hours, owner_id, last_updated) VALUES
(
    gen_random_uuid(),
    'Los Pollos Hermanos',
    'Rua das Laranjeiras, 321 - São Paulo',
    'Mexican',
    '',
    (select id from users where login = 'alice.9' and type = 'Restaurant Owner'),
    '2025-08-02'
);