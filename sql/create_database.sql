create user finan with encrypted password 'finan';

create database personal_finances encoding 'utf-8' owner finan;

grant all privileges on database personal_finances to finan;

\c personal_finances;


-- public.usr definition

drop table if exists public.usr;

create table public.usr
(
    id       serial      not null
        constraint usr_pk primary key,
    "uuid"   uuid        not null
        constraint usr_guid_unique unique,
    username varchar(16) not null
);

alter table public.usr owner to finan;

insert into public.usr("uuid", username) values
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'john_doe');


-- income_category type definition
CREATE TYPE income_category AS ENUM (
    'Salary', 'Bonus',
    'Trading', 'Dividends',
    'Other'
);


-- public.income definition
create table public.income
(
    id         serial           not null
        constraint income_pk primary key,
    usr_id     uuid             not null
        constraint income_usr_guid_fk references usr ("uuid"),
    "date"     date             not null,
    amount     double precision not null,
    "category" income_category  not null,
    "comment"  varchar(256)
);

alter table public.income owner to finan;

insert into public.income(usr_id, "date", amount, "category", "comment") values
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-01-20', 10000, 'Salary', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-02-20', 10000, 'Salary', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-03-20', 10000, 'Salary', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-03-20', 2000, 'Bonus', 'First quarter bonus');


-- spending_category type definition
CREATE TYPE spending_category AS ENUM (
    'Bills', 'Rent', 'Food', 'Clothes',
    'Electronics', 'Transport',
    'Insurance', 'Taxes',
    'Other'
);


-- public.spending definition
create table public.spending
(
    id         serial            not null
        constraint spending_pk primary key,
    usr_id     uuid              not null
        constraint spending_usr_guid_fk references usr ("uuid"),
    "date"     date              not null,
    amount     double precision  not null,
    "category" spending_category not null,
    "comment"  varchar(256)
);

alter table public.spending owner to finan;

insert into public.spending(usr_id, "date", amount, "category", "comment") values
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-01-21', 1000, 'Rent', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-01-21', 100, 'Bills', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-02-21', 1000, 'Rent', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-02-21', 100, 'Bills', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-03-21', 1000, 'Rent', null),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-03-21', 100, 'Bills', null);
