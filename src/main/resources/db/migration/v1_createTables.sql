CREATE table members if not exists (
id int,
name varchar,
password varchar,
role varchar,
memberships varchar,
description varchar
)
CREATE table memberships if not exists (
id int,
type varchar,
fromdate varchar,
todate varchar,
visits int,
owner int,
last_visit bigint
)