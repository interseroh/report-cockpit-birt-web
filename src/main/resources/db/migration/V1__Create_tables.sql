--
-- Create all the tables.
--
-- Author: Lofi Dewanto
-- Created with Juplo - Hibernate 4 Maven Plugin from JPA
--

-- DROP
drop table rcb_report cascade constraints;

drop table rcb_role cascade constraints;

drop table rcb_user cascade constraints;

drop table rcb_user_role cascade constraints;

drop sequence hibernate_sequence;
    
-- CREATE
create table rcb_report (
  id number(19,0) not null,
  report_name varchar2(255 char),
  role_id number(19,0) not null,
  primary key (id)
);

create table rcb_role (
  id number(19,0) not null,
  role_name varchar2(255 char),
  primary key (id)
);

create table rcb_user (
  id number(19,0) not null,
  user_email varchar2(255 char),
  primary key (id)
);

create table rcb_user_role (
  id number(19,0) not null,
  role_id number(19,0) not null,
  user_id number(19,0) not null,
  primary key (id)
);

 alter table rcb_report 
  add constraint FK_RCB_REPORT 
  foreign key (role_id) 
  references rcb_role;

alter table rcb_user_role 
  add constraint FK_RCB_USER_ROLE_ROLE_ID 
  foreign key (role_id) 
  references rcb_role;

alter table rcb_user_role 
  add constraint FK_FCB_USER_ROLE_USER_ID 
  foreign key (user_id) 
  references rcb_user;

create sequence hibernate_sequence;
