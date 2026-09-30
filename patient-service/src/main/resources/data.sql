INSERT INTO patient (id, name, email, address, dob, timestamp)
VALUES (gen_random_uuid(), 'Alice Smith', 'alice@example.com', '123 Health Ave', '1995-04-12', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO patient (id, name, email, address, dob, timestamp)
VALUES (gen_random_uuid(), 'Bob Jones', 'bob@example.com', '456 Care St', '1988-11-23', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
