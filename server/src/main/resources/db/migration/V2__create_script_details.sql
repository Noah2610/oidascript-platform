create table script_details (
    id bigserial not null primary key,
    name varchar(255) not null,
    description text,
    user_id bigserial references users(id),
    body_id varchar(255)
);
