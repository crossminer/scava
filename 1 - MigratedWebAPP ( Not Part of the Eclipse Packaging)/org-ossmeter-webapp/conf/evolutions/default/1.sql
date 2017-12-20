# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table events (
  id                        bigint not null,
  event_group_id            bigint,
  name                      varchar(255),
  date                      timestamp,
  constraint pk_events primary key (id))
;

create table eventgroup (
  id                        bigint not null,
  user_id                   bigint,
  name                      varchar(255),
  constraint pk_eventgroup primary key (id))
;

create table linked_account (
  id                        bigint not null,
  user_id                   bigint,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_account primary key (id))
;

create table notifications (
  id                        bigint not null,
  user_id                   bigint,
  project_id                varchar(255),
  metric_id                 varchar(255),
  threshold_value           double,
  above_threshold           boolean,
  constraint pk_notifications primary key (id))
;

create table project (
  id                        bigint not null,
  name                      varchar(255),
  short_name                varchar(255),
  url                       varchar(255),
  desc                      varchar(255),
  constraint pk_project primary key (id))
;

create table security_role (
  id                        bigint not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table sub_project (
  id                        bigint not null,
  name                      varchar(255),
  short_name                varchar(255),
  url                       varchar(255),
  desc                      varchar(255),
  constraint pk_sub_project primary key (id))
;

create table token_action (
  id                        bigint not null,
  token                     varchar(255),
  target_user_id            bigint,
  type                      varchar(2),
  created                   timestamp,
  expires                   timestamp,
  constraint ck_token_action_type check (type in ('EV','PR')),
  constraint uq_token_action_token unique (token),
  constraint pk_token_action primary key (id))
;

create table users (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  last_login                timestamp,
  active                    boolean,
  email_validated           boolean,
  constraint pk_users primary key (id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;


create table project_users (
  project_id                     bigint not null,
  users_id                       bigint not null,
  constraint pk_project_users primary key (project_id, users_id))
;

create table sub_project_users (
  sub_project_id                 bigint not null,
  users_id                       bigint not null,
  constraint pk_sub_project_users primary key (sub_project_id, users_id))
;

create table users_security_role (
  users_id                       bigint not null,
  security_role_id               bigint not null,
  constraint pk_users_security_role primary key (users_id, security_role_id))
;

create table users_user_permission (
  users_id                       bigint not null,
  user_permission_id             bigint not null,
  constraint pk_users_user_permission primary key (users_id, user_permission_id))
;
create sequence events_seq;

create sequence eventgroup_seq;

create sequence linked_account_seq;

create sequence notifications_seq;

create sequence project_seq;

create sequence security_role_seq;

create sequence sub_project_seq;

create sequence token_action_seq;

create sequence users_seq;

create sequence user_permission_seq;

alter table events add constraint fk_events_eventGroup_1 foreign key (event_group_id) references eventgroup (id) on delete restrict on update restrict;
create index ix_events_eventGroup_1 on events (event_group_id);
alter table eventgroup add constraint fk_eventgroup_user_2 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_eventgroup_user_2 on eventgroup (user_id);
alter table linked_account add constraint fk_linked_account_user_3 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_linked_account_user_3 on linked_account (user_id);
alter table notifications add constraint fk_notifications_user_4 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_notifications_user_4 on notifications (user_id);
alter table token_action add constraint fk_token_action_targetUser_5 foreign key (target_user_id) references users (id) on delete restrict on update restrict;
create index ix_token_action_targetUser_5 on token_action (target_user_id);



alter table project_users add constraint fk_project_users_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_users add constraint fk_project_users_users_02 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table sub_project_users add constraint fk_sub_project_users_sub_proj_01 foreign key (sub_project_id) references sub_project (id) on delete restrict on update restrict;

alter table sub_project_users add constraint fk_sub_project_users_users_02 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_security_role add constraint fk_users_security_role_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_security_role add constraint fk_users_security_role_securi_02 foreign key (security_role_id) references security_role (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_user_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_user_02 foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists events;

drop table if exists eventgroup;

drop table if exists linked_account;

drop table if exists notifications;

drop table if exists project;

drop table if exists project_users;

drop table if exists security_role;

drop table if exists sub_project;

drop table if exists sub_project_users;

drop table if exists token_action;

drop table if exists users;

drop table if exists users_security_role;

drop table if exists users_user_permission;

drop table if exists user_permission;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists events_seq;

drop sequence if exists eventgroup_seq;

drop sequence if exists linked_account_seq;

drop sequence if exists notifications_seq;

drop sequence if exists project_seq;

drop sequence if exists security_role_seq;

drop sequence if exists sub_project_seq;

drop sequence if exists token_action_seq;

drop sequence if exists users_seq;

drop sequence if exists user_permission_seq;

