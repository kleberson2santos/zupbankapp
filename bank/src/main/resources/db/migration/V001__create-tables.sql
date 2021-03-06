create table account
(
    id        bigint  not null auto_increment,
    agency    int(4),
    token     varchar(6),
    account   int(8),
    bank_code int(3),
    enabled   boolean not null default 0,
    sale      decimal(10, 2)   default 0,
    primary key (id)
) engine = InnoDB
  default charset = utf8;
create table client
(
    id                 bigint not null auto_increment,
    name               varchar(255),
    lastname           varchar(255),
    email              varchar(255),
    cnh                varchar(255),
    birth              date,
    address_road       varchar(255),
    address_cep        varchar(255),
    address_city       varchar(255),
    address_complement varchar(255),
    address_district   varchar(255),
    address_state      varchar(255),
    primary key (id)
) engine = InnoDB
  default charset = utf8;
create table cnh
(
    client_id bigint not null,
    primary key (client_id)
) engine = InnoDB
  default charset = utf8;
create table cnh_files
(
    cnh_id       bigint not null,
    content_type varchar(255),
    description  varchar(255),
    file_name    varchar(255),
    size         bigint
) engine = InnoDB
  default charset = utf8;
create table proposal
(
    id        bigint                          not null auto_increment,
    status    varchar(32) default 'PENDING'   not null,
    accept    varchar(32) default 'TO_ACCEPT' not null,
    acount_id bigint,
    client_id bigint                          not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table verification_token
(
    id          bigint not null auto_increment,
    expiry_date datetime,
    token       varchar(8),
    account_id  bigint not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

alter table cnh
    add constraint fk_cnh_client foreign key (client_id) references client (id);
alter table cnh_files
    add constraint fk_cnh_files_cnh foreign key (cnh_id) references cnh (client_id);
alter table proposal
    add constraint fk_proposal_account foreign key (acount_id) references account (id);
alter table proposal
    add constraint fk_proposal_client foreign key (client_id) references client (id);
alter table verification_token
    add constraint fk_verification_token_client foreign key (account_id) references account (id)