INSERT INTO users (id, name, type, email, login, password, address, last_updated)
VALUES
    (gen_random_uuid(), 'Gus Fring', 'Restaurant Owner', 'gus@domain.com', 'gus.fring', '4l1c3P4ss!', '654 Davis Vista, MacGyverburgh, MD 43201', now());

INSERT INTO restaurants (id, name, address, type, business_hours, owner_id, last_updated) VALUES
(
    'faa47066-ea9d-4dab-94b1-4f25db411a4e ',
    'Los Pollos Hermanos',
    'Rua das Laranjeiras, 321 - São Paulo',
    'Mexican',
    '',
    (select id from users where login = 'gus.fring' and type = 'Restaurant Owner'),
    now()
);

INSERT INTO items (id, restaurant_id, name, description, price, available_only_at_restaurant, photo_path, last_updated) VALUES
    (
        gen_random_uuid(),
        (SELECT id FROM restaurants WHERE name = 'Los Pollos Hermanos'),
        'Pollos Special Burger',
        'A delicious grilled chicken burger with our secret recipe sauce and fresh vegetables.',
        12.99,
        true,
        '/photos/los-pollos/special-burger.jpg',
        now()
    ),
    (
        gen_random_uuid(),
        (SELECT id FROM restaurants WHERE name = 'Los Pollos Hermanos'),
        'Spicy Fried Chicken',
        'Crispy fried chicken pieces with a fiery hot coating, served with a side of fries.',
        9.50,
        false,
        '/photos/los-pollos/spicy-chicken.jpg',
        now()
    ),
    (
        gen_random_uuid(),
        (SELECT id FROM restaurants WHERE name = 'Los Pollos Hermanos'),
        'Kids Chicken Nuggets',
        'Six tender chicken nuggets with a mild flavor, perfect for kids.',
        6.75,
        false,
        '/photos/los-pollos/nuggets.jpg',
        now()
    );
