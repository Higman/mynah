create table roles
(
    id      serial PRIMARY KEY UNIQUE,
    role_id text UNIQUE NOT NULL
);

create table users
(
    id         serial PRIMARY KEY UNIQUE,
    role_id    roles                    NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

create table recruitment
(
    id            serial PRIMARY KEY UNIQUE,
    user_id       users                    NOT NULL UNIQUE,
    board_id      boards                   NOT NULL,
    is_recruiting bool                              DEFAULT TRUE,
    detail        text,
    created_at    timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (board_id) REFERENCES boards (id)
);

create table boards
(
    id                  serial PRIMARY KEY UNIQUE,
    topic               text,
    data_published_from timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_published_to   timestamp with time zone NOT NULL
);

