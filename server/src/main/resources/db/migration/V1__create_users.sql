create table users (
    id bigserial not null primary key,
    username varchar(255) unique,
    password_hash varchar(255)
);
