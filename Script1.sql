create table if not exists Users(
user_id serial primary key,
username varchar unique,
passcode varchar
);

create table if not exists Accounts(
account_id serial primary key,
account_name varchar unique,
account_type varchar,
cows_quantity integer default 0,
sheep_quantity integer default 0,
goats_quantity integer default 0
);		

create table if not exists Permissions(
permit_id serial primary key,
user_id int references users(user_id),
account_id int references accounts(account_id)
);
select (select count(username) from Users where username = 'Alice') + (select count(account_name) from Accounts where account_name = 'Alice') as checkuser;
select user_id from users where username = 'Bob';


insert into users(username, passcode) values
('Alice', 'testpass1'),
('ValidUser', 'Swordfish'),
('SecondUser', 'P4&&w0rd'),
('OtherUser', 'Wh473v3r');


select * from users;
select * from accounts;
select * from permissions;
drop table accounts;
drop table users;
drop table permissions;


create role manager LOGIN password 'Swordfish';
grant all privileges on database postgres to manager;
grant all privileges on schema testing to manager;
grant all privileges on table Users to manager;
grant all privileges on table Accounts to manager;
grant all privileges on table Permissions to manager;


	
	
	
	


