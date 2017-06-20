/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/20 21:42:24                           */
/*==============================================================*/

drop table if exists role_function;
drop table if exists user_role;
drop table if exists auth_function;
drop table if exists auth_role;

/*==============================================================*/
/* Table: auth_function                                         */
/*==============================================================*/
create table auth_function
(
   id                   varchar(32) not null,
   name                 varchar(255),
   code                 varchar(255),
   description          varchar(255),
   page                 varchar(255),
   generatemenu         varchar(255),
   zindex               int,
   pid                  varchar(32),
   primary key (id),
   key AK_Key_2 (name)
);

alter table auth_function comment '权限表';

/*==============================================================*/
/* Table: auth_role                                             */
/*==============================================================*/
create table auth_role
(
   id                   varchar(32) not null,
   name                 varchar(255),
   code                 varchar(255),
   description          varchar(255),
   primary key (id),
   key AK_Key_2 (name)
);

alter table auth_role comment '角色表';

/*==============================================================*/
/* Table: role_function                                         */
/*==============================================================*/
create table role_function
(
   role_id              varchar(32) not null,
   function_id          varchar(32) not null,
   primary key (role_id, function_id)
);

alter table role_function comment '角色权限关系表';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   user_id              varchar(32) not null,
   role_id              varchar(32) not null,
   primary key (user_id, role_id)
);

alter table user_role comment '用户角色关系表';

alter table auth_function add constraint FK_Reference_1a foreign key (pid)
      references auth_function (id) on delete restrict on update restrict;

alter table role_function add constraint FK_Reference_2a foreign key (function_id)
      references auth_function (id) on delete restrict on update restrict;

alter table role_function add constraint FK_Reference_3a foreign key (role_id)
      references auth_role (id) on delete restrict on update restrict;

alter table user_role add constraint FK_Reference_5a foreign key (role_id)
      references auth_role (id) on delete restrict on update restrict;

alter table user_role add constraint FK_Reference_6a foreign key (user_id)
      references t_user (id) on delete restrict on update restrict;

