CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table todo_item
(
    id uuid constraint todo_item_pk primary key DEFAULT uuid_generate_v4(),
    content text not null,
    datetime_created timestamp not null
);
