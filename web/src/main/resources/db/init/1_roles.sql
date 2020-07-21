INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (role_name) VALUES ('ROLE_USER');

INSERT INTO users (provider, provider_id, role_id) VALUES ('Google', '01010', 1);
INSERT INTO users (provider, provider_id, role_id) VALUES ('Google', 'dsadasd', 2);

INSERT INTO boards (data_published_from, data_published_to, topic) VALUES ('2020-01-11', '2020-12-01', 'test1');
INSERT INTO boards (data_published_from, data_published_to, topic) VALUES ('2020-03-11', '2020-10-01', 'test2');
INSERT INTO boards (data_published_from, data_published_to, topic) VALUES ('2020-02-11', '2020-04-01', 'test3');

INSERT INTO recruitment (detail, is_recruiting, board_id, user_id) VALUES ('detail1', false, 1, 1);
INSERT INTO recruitment (detail, is_recruiting, board_id, user_id) VALUES ('detail1', false, 1, 1);
INSERT INTO recruitment (detail, is_recruiting, board_id, user_id) VALUES ('detail2', false, 1, 1);