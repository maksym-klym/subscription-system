-- USERS --
INSERT INTO users(created_at, email, first_name, last_name, "password") VALUES
(NOW(), 'user1@mail.com', 'Jeth', 'Ellison', 'password'),
(NOW(), 'user2@mail.com', 'Eugine', 'Bryde', 'password'),
(NOW(), 'user3@mail.com', 'Foster', 'Ryan', 'password'),
(NOW(), 'user4@mail.com', 'Mike', 'Vincent', 'password'),
(NOW(), 'user5@mail.com', 'Arin', 'Pinnington', 'password'),
(NOW(), 'user6@mail.com', 'Andrew', 'Colorod', 'password'),
(NOW(), 'user7@mail.com', 'John', 'Doe', 'password'),
(NOW(), 'user8@mail.com', 'Jane', 'Kowalski', 'password'),
(NOW(), 'user9@mail.com', 'George', 'Smith', 'password'),
(NOW(), 'user10@mail.com', 'Bob', 'Vance', 'password');

-- SUBSCRIPTION_PLANS --
INSERT INTO subscription_plans(duration_days, "name", price) VALUES
(30, 'BASIC', 9.99),
(30, 'PRO', 19.99),
(30, 'ENTERPRISE', 49.99),
(90, 'BASIC', 24.99),
(90, 'PRO', 49.99),
(90, 'ENTERPRISE', 119.99),
(180, 'BASIC', 49.99),
(180, 'PRO', 79.99),
(180, 'ENTERPRISE', 199.99),
(365, 'BASIC', 99.99),
(365, 'PRO', 149.99),
(365, 'ENTERPRISE', 299.99);

-- SUBSCRIPTIONS --
INSERT INTO subscriptions(end_date, start_date, status, plan_id, user_id) VALUES
(NOW() + INTERVAL '30 day', NOW(), 'ACTIVE', 1, 1),
(NOW() + INTERVAL '30 day', NOW(), 'ACTIVE', 2, 2),
(NOW() + INTERVAL '30 day', NOW(), 'CANCELED', 3, 3),
(NOW() + INTERVAL '90 day', NOW(), 'ACTIVE', 4, 4),
(NOW() + INTERVAL '90 day', NOW(), 'ACTIVE', 5, 5),
(NOW() + INTERVAL '180 day', NOW(), 'ACTIVE', 6, 6),
(NOW() + INTERVAL '365 day', NOW(), 'ACTIVE', 7, 7),
(NOW() + INTERVAL '365 day', NOW(), 'ACTIVE', 8, 8),
(NOW() + INTERVAL '365 day', NOW(), 'PAST_DUE', 9, 9),
(NOW() + INTERVAL '7 day', NOW(), 'TRIAL', 10, 10);

-- INVOICES --
INSERT INTO invoices(amount, due_date, status, subscription_id) VALUES
(9.99, CURRENT_DATE + INTERVAL '5 day', 'PAID', 1),
(19.99, CURRENT_DATE + INTERVAL '5 day', 'PAID', 2),
(49.99, CURRENT_DATE - INTERVAL '2 day', 'OVERDUE', 3),
(24.99, CURRENT_DATE + INTERVAL '10 day', 'CREATED', 4),
(49.99, CURRENT_DATE + INTERVAL '10 day', 'PAID', 5),
(79.99, CURRENT_DATE + INTERVAL '10 day', 'PAID', 6),
(99.99, CURRENT_DATE + INTERVAL '20 day', 'CREATED', 7),
(149.99, CURRENT_DATE + INTERVAL '20 day', 'CREATED', 8),
(299.99, CURRENT_DATE - INTERVAL '5 day', 'OVERDUE', 9),
(0.00, CURRENT_DATE + INTERVAL '2 day', 'CREATED', 10);

-- PAYMENTS --
INSERT INTO payments(amount, paid_at, invoice_id) VALUES
(9.99, NOW(), 1),
(19.99, NOW(), 2),
(49.99, NOW(), 3),
(24.99, NOW(), 4),
(49.99, NOW(), 5),
(79.99, NOW(), 6),
(99.99, NOW(), 7),
(149.99, NOW(), 8),
(299.99, NOW(), 9),
(0.00, NOW(), 10);