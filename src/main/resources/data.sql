-- USERS --
INSERT INTO users(created_at, email, first_name, last_name, "password") VALUES
(NOW(), 'u1@mail.com', 'User1', 'Test', 'pass'),
(NOW(), 'u2@mail.com', 'User2', 'Test', 'pass'),
(NOW(), 'u3@mail.com', 'User3', 'Test', 'pass'),
(NOW(), 'u4@mail.com', 'User4', 'Test', 'pass'),
(NOW(), 'u5@mail.com', 'User5', 'Test', 'pass'),
(NOW(), 'u6@mail.com', 'User6', 'Test', 'pass'),
(NOW(), 'u7@mail.com', 'User7', 'Test', 'pass'),
(NOW(), 'u8@mail.com', 'User8', 'Test', 'pass'),
(NOW(), 'u9@mail.com', 'User9', 'Test', 'pass'),
(NOW(), 'u10@mail.com', 'User10', 'Test', 'pass');

-- PLANS --
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