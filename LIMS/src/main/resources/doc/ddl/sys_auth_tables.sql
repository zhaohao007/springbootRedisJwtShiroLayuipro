SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- table structure for tb_sys_admin_user
-- ----------------------------
drop table if exists tb_sys_admin_user;
create table  tb_sys_admin_user(
  id int(11) not null auto_increment comment 'id',
  account  varchar(100) not null comment '登录账户',
  password varchar(500) not null comment '密码',
  salt varchar(500) not null comment '盐值',
  inner_type varchar(20) not null comment '是否内置账户(内置)INNER (普通)NORMAL',
  name varchar(50) default null comment '姓名',
  status varchar(20) default null comment 'USABLE可用 DISABLE不可用',
  description varchar(1000) default null comment '描述',
  token varchar(500) default null,
  login_time datetime default null comment '登录时间',
  when_created         timestamp        not  null DEFAULT CURRENT_TIMESTAMP,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id),
 unique key unique_account(account)
) engine=innodb auto_increment=1 default charset=utf8
  comment '后台用户表';

-- ----------------------------
-- table structure for tb_sys_user_role
-- 用户角色表
-- ----------------------------
drop table if exists tb_sys_user_role;
create table tb_sys_user_role (
  id int(11) not null auto_increment,
  user_id int(11) not null,
  role_id int(11) not null,
  when_created         timestamp          null,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id),
  unique key unique_user_role(user_id,role_id)
) engine=innodb auto_increment=1 default charset=utf8 comment '用户角色表';

-- ----------------------------
-- table structure for tb_sys_role_menu
-- 角色与菜单对应关系
-- ----------------------------
drop table if exists tb_sys_role_menu;
create table tb_sys_role_menu (
  id int(11)   not null auto_increment,
  role_id int(11)   default null comment '角色id',
  menu_id int(11)   default null comment '菜单id',
  when_created         timestamp          null,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id),
  unique key unique_role_menu(menu_id,role_id)
) engine=innodb auto_increment=1 default charset=utf8 comment='角色与菜单对应关系';

-- ----------------------------
-- table structure for tb_sys_role
-- ----------------------------
drop table if exists tb_sys_role;
create table tb_sys_role (
  id int(11)  not null auto_increment,
  role_name varchar(100) default null comment '角色名称',
  inner_type varchar(20) not null comment '是否内置角色不可删除(内置)INNER (普通)NORMAL',
  remark varchar(1000) default null comment '备注',
  when_created         timestamp        not  null,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id)
) engine=innodb auto_increment=1 default charset=utf8 comment='角色';

-- ----------------------------
-- table structure for tb_sys_menu
-- ----------------------------
drop table if exists tb_sys_menu;
create table tb_sys_menu (
  id int(11) not null auto_increment,
  parent_id int(11) not null comment '父菜单id，一级菜单为0',
  name varchar(50) default null comment '菜单名称',
  server_url varchar(200) default null comment '服务端url',
  front_url varchar(200) default null comment '前端url',
  perms varchar(500) default null comment '授权(多个用逗号分隔，如：user:list,user:create)',
  type varchar(20) default null comment '类型目录TOP_MENU;菜单SECOND_MENU;按钮BUTTON',
  icon varchar(50) default null comment '菜单图标',
  inner_type varchar(20) not null comment '是否内置资源不可删除(内置)INNER (普通)NORMAL',
  order_num int(11) default null comment '排序',
  when_created         timestamp       not   null,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id)
) engine=innodb auto_increment=1  default charset=utf8 comment='菜单资源';


-- ----------------------------
-- table structure for tb_sys_log
-- ----------------------------
drop table if exists tb_sys_log;
create table tb_sys_log (
  id int(11) not null auto_increment,
  user_name varchar(50) default null comment '操作用户名',
  ip varchar(200) default null comment 'ip地址',
  url varchar(200) default null comment '请求url',
  type varchar(20) default null comment '日志级别INFO,ERROR,DEBUG',
  title varchar(500) default null comment '日志标题',
  method varchar(100) default null comment '方法名',
  arguments varchar(10000) default null comment '日志入参',
  description varchar(500) default null comment '日志记录描述',
  exception varchar(500) default null comment '异常',
  when_created         timestamp       not   null,
  when_modified        timestamp          null,
  who_created          int(11)            null,
  who_modified         int(11)            null,
  primary key (id)
) engine=innodb auto_increment=1  default charset=utf8 comment='后台操作日志';