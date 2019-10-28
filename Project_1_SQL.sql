create table ERS_User_Roles(
	ers_user_role_id integer primary key,
	user_role varchar(10)
);

create table ERS_Users(
	ers_user_id integer primary key, 
	ers_username varchar(50), 
	ers_password varchar(50), 
	user_first_name varchar(100), 
	user_last_name varchar(100), 
	user_email varchar(150), 
	user_role_id integer references ERS_User_Roles (ers_user_role_id)
);
create table ERS_Reimbursement_Type(
	reimb_type_id integer primary key,
	reimb_type varchar(10)
);

create table ERS_Reimbursement_Status(
	reimb_status_id integer primary key,
	reimb_status varchar(10)
);

create table ERS_Reimbursement(
	reimb_id integer primary key,
	reimb_amount money,
	reimb_submitted timestamp,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_receipt bytea,
	reimb_author integer references ERS_Users(ers_user_id),
	reimb_resolver integer references ERS_Users(ers_user_id),
	reimb_status integer references ERS_Reimbursement_Status(reimb_status_id),
	reimb_type integer references ERS_Reimbursement_Type(reimb_type_id)
);

insert into ers_reimbursement_type(reimb_type_id, reimb_type) values
	(1, 'Lodging'),
	(2, 'Travel'),
	(3, 'Food'),
	(4, 'Other');

insert into ers_user_roles(ers_user_role_id, user_role) values
	(1, 'Employee'),
	(2, 'Manager');

insert into ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values
	(1, 'ATanner', 'testpass1', 'Albert', 'Tanner', 'alberttanner@email.com', 2),
	(2, 'BPotter', 'testpass2', 'Betty', 'Potter', 'bettypotter@email.com', 2),
	(3, 'CBaker', 'testpass3', 'Charles', 'Baker', 'charlesbaker@email.com', 1),
	(4, 'DSmith', 'testpass4', 'Derek', 'Smith', 'dereksmith@email.com', 1),
	(5, 'EWeber', 'testpass5', 'Edward', 'Weber', 'edwardweber@email.com', 1),
	(6, 'FCobbler', 'testpass6', 'Florence', 'Cobbler', 'florencecobbler@email.com', 1),
	(7, 'GChandler', 'testpass7', 'George', 'Chandler', 'georgechandler@email.com', 1);

create role employee LOGIN password 'userpass';
grant insert on table ERS_Reimbursement to employee;
grant select on table ERS_Reimbursement to employee;

create role finance_manager password 'manager_access';
grant update on table ERS_Reimbursement to finance_manager;
grant insert on table ERS_Reimbursement to finance_manager;
grant select on table ERS_Reimbursement to finance_manager;

