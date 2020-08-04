-- INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
-- INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO `roles` (id, name) VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

INSERT INTO `users` (id, created_at, updated_at, email, name, password, username) VALUES
    (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'dhvcars1@test.com', 'Dealer Name 1', '$2a$10$.ln8NbzHyF3BnBTgzUyUNO0ta4zgU8hlHkcMGwd707N7WSxumg/Sy', 'dhvcars1'),
    (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'dhvcars2@test.com', 'Dealer Name 2', '$2a$10$.ln8NbzHyF3BnBTgzUyUNO0ta4zgU8hlHkcMGwd707N7WSxumg/Sy', 'dhvcars2');

INSERT INTO `user_roles` (user_id, role_id) VALUES
    (1, 1),
    (2, 1);

INSERT INTO `stores` (id, created_at, updated_at, created_by, updated_by, name) VALUES
    (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Store Name 1');

INSERT INTO `cars` (id, created_at, updated_at, created_by, updated_by, brand, model, version, year_of_release, price, annual_maintenance_cost, fuel_consumption, store_id) VALUES
    ( 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Citroen', 'C1',   's1.u1', 2018, 1010, 100, 10, 1),
    ( 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Citroen', 'C1',   's1.u1', 2019, 1020, 100, 10, 1),
    ( 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Citroen', 'C2',   's1.u1', 2018, 1030, 100, 10, 1),
    ( 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Citroen', 'C2',   's1.u1', 2019, 1040, 100, 10, 1),
    ( 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Honda',   'Fit1', 's1.u1', 2018, 1050, 200, 10, 1),
    ( 6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Honda',   'Fit1', 's1.u1', 2019, 1060, 200, 10, 1),
    ( 7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Honda',   'Fit2', 's1.u1', 2018, 1070, 200, 10, 1),
    ( 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1, 'Honda',   'Fit2', 's1.u1', 2019, 1080, 200, 10, 1),
    ( 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Citroen', 'C3',   's1.u2', 2018, 1080, 100, 10, 1),
    (10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Citroen', 'C3',   's1.u2', 2019, 1070, 100, 10, 1),
    (11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Citroen', 'C4',   's1.u2', 2019, 1060, 100, 10, 1),
    (12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Citroen', 'C4',   's1.u2', 2020, 1050, 100, 10, 1),
    (13, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Honda',   'Fit2', 's1.u2', 2018, 1040, 200, 10, 1),
    (14, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Honda',   'Fit2', 's1.u2', 2019, 1030, 200, 10, 1),
    (15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Honda',   'Fit3', 's1.u2', 2019, 1020, 200, 10, 1),
    (16, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 2, 'Honda',   'Fit3', 's1.u2', 2020, 1010, 200, 10, 1);
