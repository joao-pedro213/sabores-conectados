INSERT INTO restaurants (id, name, address, type, business_hours, owner_id, last_updated) VALUES
(
    gen_random_uuid(),
    'La Trattoria Italiana',
    'Rua das Oliveiras, 123 - São Paulo',
    'Italian',
    '{
        "dailySchedules": {
            "MONDAY": {"openingTime": "11:00:00", "closingTime": "22:00:00"},
            "TUESDAY": {"openingTime": "11:00:00", "closingTime": "22:00:00"},
            "WEDNESDAY": {"openingTime": "11:00:00", "closingTime": "22:00:00"},
            "THURSDAY": {"openingTime": "11:00:00", "closingTime": "22:00:00"},
            "FRIDAY": {"openingTime": "11:00:00", "closingTime": "23:00:00"},
            "SATURDAY": {"openingTime": "12:00:00", "closingTime": "23:00:00"},
            "SUNDAY": {"openingTime": "12:00:00", "closingTime": "21:00:00"}
        }
    }',
    (SELECT id FROM users WHERE login = 'anasilva' AND type = 'Restaurant Owner'),
    '2025-08-02'
),
(
    gen_random_uuid(),
    'El Gringo Burritos',
    'Avenida Paulista, 1000 - São Paulo',
    'Mexican', '{
        "dailySchedules": {
            "MONDAY": {"openingTime": "10:00:00", "closingTime": "22:00:00"},
            "TUESDAY": {"openingTime": "10:00:00", "closingTime": "22:00:00"},
            "WEDNESDAY": {"openingTime": "10:00:00", "closingTime": "22:00:00"},
            "THURSDAY": {"openingTime": "10:00:00", "closingTime": "22:00:00"},
            "FRIDAY": {"openingTime": "10:00:00", "closingTime": "23:00:00"},
            "SATURDAY": {"openingTime": "11:00:00", "closingTime": "23:00:00"}
        }
    }',
    (SELECT id FROM users WHERE login = 'anasilva' AND type = 'Restaurant Owner'),
    '2025-08-02'
),
(
    gen_random_uuid(),
    'Temakeria Sabor Oriental',
    'Rua Augusta, 500 - São Paulo',
    'Japanese', '{
        "dailySchedules": {
            "MONDAY": {"openingTime": "18:00:00", "closingTime": "00:00:00"},
            "TUESDAY": {"openingTime": "18:00:00", "closingTime": "00:00:00"},
            "WEDNESDAY": {"openingTime": "18:00:00", "closingTime": "00:00:00"},
            "THURSDAY": {"openingTime": "18:00:00", "closingTime": "00:00:00"},
            "FRIDAY": {"openingTime": "18:00:00", "closingTime": "01:00:00"},
            "SATURDAY": {"openingTime": "18:00:00", "closingTime": "01:00:00"}
        }
    }',
    (SELECT id FROM users WHERE login = 'lucasp' AND type = 'Restaurant Owner'),
    '2025-08-02'
);