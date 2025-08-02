INSERT INTO users (id, name, type, email, login, password, address, last_updated)
VALUES
    (gen_random_uuid(), 'Ana Silva', 'Restaurant Owner', 'ana.silva@email.com', 'anasilva', 'senha123', 'Rua das Flores, 123, Centro', '2025-05-17'),
    (gen_random_uuid(), 'Pedro Oliveira', 'Customer', 'pedro.oliveira@example.org', 'pedroo', 'segredo456', 'Avenida Brasil, 456, Savassi', '2025-05-16'),
    (gen_random_uuid(), 'Mariana Souza', 'Customer', 'mariana.s@work.net', 'maris', 'pass789', 'Rua da Bahia, 789, Funcionários', '2025-05-10'),
    (gen_random_uuid(), 'Lucas Pereira', 'Restaurant Owner', 'lucas.p@domain.com', 'lucasp', 'forte101', 'Avenida Afonso Pena, 1010, Boa Viagem', '2025-05-01'),
    (gen_random_uuid(), 'Fernanda Costa', 'Customer', 'fernanda.c@provider.net', 'fernandac', 'secure22', 'Rua Sergipe, 222, Lourdes', '2025-04-25');
