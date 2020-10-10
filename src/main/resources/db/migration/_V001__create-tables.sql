create table client
(
    id              bigint auto_increment primary key,
    bairro          varchar(255) null,
    cep             varchar(255) null,
    cidade          varchar(255) null,
    cnh             varchar(255) null,
    complemento     varchar(255) null,
    data_nascimento date         null,
    email           varchar(255) null,
    estado          varchar(255) null,
    nome            varchar(255) null,
    rua             varchar(255) null,
    sobrenome       varchar(255) null
);

create table account
(
    id     bigint auto_increment primary key,
    number varchar(255) null
);

create table proposal
(
    id         bigint auto_increment primary key,
    status     varchar(255) DEFAULT 'PENDING',
    account_id bigint null,
    client_id  bigint null,
    constraint FKc6hhygtkctiqt5xg7nqh7k83e foreign key (client_id) references client (id),
    constraint FKj40pt9n65hvf8es8qalp2yuen foreign key (account_id) references account (id)
);

create table cnh
(
    client_id bigint not null
        primary key,
    constraint FK2kq155tst2jfbyw84t8scjt1k
        foreign key (client_id) references client (id)
);

create table cnh_files
(
    cnh_client_id bigint not null,
    content_type varchar(255) null,
    descricao varchar(255) null,
    nome_arquivo varchar(255) null,
    tamanho bigint null,
    constraint FKlavfk27cf4jc7ixa21p171ukp
        foreign key (cnh_client_id) references cnh (client_id)
);


