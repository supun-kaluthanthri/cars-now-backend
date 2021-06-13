
-- client details table

create table oauth_client_details (
  client_id varchar2(255) not null,
  client_secret varchar2(255) not null,
  web_server_redirect_uri varchar2(512) default null,
  scope varchar2(255) default null,
  access_token_validity number(10) default null,
  refresh_token_validity number(10) default null,
  resource_ids varchar2(512) default null,
  authorized_grant_types varchar2(512) default null,
  authorities varchar2(512) default null,
  additional_information varchar2(512) default null,
  autoapprove varchar2(255) default null,
  primary key (client_id)
);

-- permission table

CREATE SEQUENCE seq_permission MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 10;

create table permission (
    id number(10) not null,
    name varchar2(512) default null,
    primary key (id),
    constraint name unique (name)
);

CREATE TRIGGER permission_bi
    BEFORE INSERT ON permission
    FOR EACH ROW
BEGIN
    SELECT SEQ_PERMISSION.nextval
    INTO :new.id
    FROM dual;
END;

-- role table
create table role (
  id number(10) not null,
  name varchar2(512) default null,
  primary key (id),
  constraint role_name unique (name)
);

CREATE SEQUENCE seq_role MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 10;

CREATE TRIGGER role_bi
    BEFORE INSERT ON role
    FOR EACH ROW
BEGIN
    SELECT SEQ_ROLE.nextval
    INTO :new.id
    FROM dual;
END;

-- user table
create table users (
  id number(10) not null,
  username varchar2(255) not null,
  password varchar2(512) not null,
  email varchar2(51) not null,
  enabled number(1) not null,
  account_non_expired number(1) not null,
  credentials_non_expired number(1) not null,
  account_non_locked number(1) not null,
  primary key (id),
  CONSTRAINT users_username UNIQUE (username)
);

CREATE SEQUENCE seq_user MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 10;

CREATE TRIGGER user_bi
    BEFORE INSERT ON users
    FOR EACH ROW
BEGIN
    SELECT SEQ_USER.nextval
    INTO :new.id
    FROM dual;
END;

-- permission_role table
create table  permission_role (
  permission_id number(10) default null,
  role_id number(10) default null,
  constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
  constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
) ;

create index permission_id on permission_role (permission_id);
create index role_id on permission_role (role_id);

-- role_user table
create table role_user (
  role_id number(10) default null,
  user_id number(10) default null,
  constraint role_user_ibfk_1 foreign key (role_id) references role (id),
  constraint role_user_ibfk_2 foreign key (user_id) references users (id)
);

create index role_id on role_user (role_id);
 create index user_id on role_user (user_id);


-- oauth_client_token table
create table oauth_client_token (
  token_id VARCHAR2(256),
  token BLOB,
  authentication_id VARCHAR2(256) PRIMARY KEY,
  user_name VARCHAR2(256),
  client_id VARCHAR2(256)
);

-- oauth_access_token
create table oauth_access_token (
  token_id VARCHAR2(256),
  token BLOB,
  authentication_id VARCHAR2(256) PRIMARY KEY,
  user_name VARCHAR2(256),
  client_id VARCHAR2(256),
  authentication BLOB,
  refresh_token VARCHAR2(256)
);

-- oauth_refresh_token table
create table oauth_refresh_token (
  token_id VARCHAR2(256),
  token BLOB,
  authentication BLOB
);

-- oauth_code table
create table oauth_code (
  code VARCHAR2(256), authentication BLOB
);

-- oauth_approvals table
create table oauth_approvals (
  userId VARCHAR2(256),
  clientId VARCHAR2(256),
  scope VARCHAR2(256),
  status VARCHAR2(10),
  expiresAt TIMESTAMP(0),
  lastModifiedAt TIMESTAMP(0)
);