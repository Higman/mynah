-- initialize roles table
COPY roles FROM '/docker-entrypoint-initdb.d/data/roles.csv' DELIMITER ',' CSV HEADER;

-- initialize users table
INSERT INTO users (role_id) SELECT r FROM roles r WHERE r.id = 0;
INSERT INTO users (role_id) SELECT r FROM roles r WHERE r.id = 1;
INSERT INTO users (role_id) SELECT r FROM roles r WHERE r.id = 1;
INSERT INTO users (role_id) SELECT r FROM roles r WHERE r.id = 2;

-- initialize board table
INSERT INTO boards(topic,data_published_to) values ('topic 1', '2020-01-11 00:00:00');

-- initialize recruitment table
INSERT INTO recruitment (user_id, board_id,  detail) SELECT u, b, 'recruitment 1' FROM users u, boards b WHERE u.id = 3, b.id = 1;
INSERT INTO recruitment (user_id, board_id,  detail) SELECT u, b, 'recruitment 2' FROM users u, boards b WHERE u.id = 4, b.id = 1;
INSERT INTO recruitment (user_id, board_id,  detail) SELECT u, b, 'recruitment 2' FROM users u, boards b WHERE u.id = 1, b.id = 1;
