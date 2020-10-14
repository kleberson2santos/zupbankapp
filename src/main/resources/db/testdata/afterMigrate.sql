set foreign_key_checks = 0;

delete
from client;
delete
from proposal;
delete
from account;
delete
from cnh;
delete
from cnh_files;

set foreign_key_checks = 1;

alter table client
    auto_increment = 1;
alter table proposal
    auto_increment = 1;
alter table account
    auto_increment = 1;
alter table cnh
    auto_increment = 1;
alter table cnh_files
    auto_increment = 1;

INSERT INTO client (id, name, lastname, email, cnh, birth, address_road, address_cep, address_city, address_complement,
                    address_district, address_state)
VALUES (1, 'John', 'White', 'john@gmail.com', '99988877712', date_sub(sysdate(), interval 27 year), 'Tansversal Road',
        null, null, null, null, null);
INSERT INTO client (id, name, lastname, email, cnh, birth, address_road, address_cep, address_city, address_complement,
                    address_district, address_state)
VALUES (2, 'Zulu', 'Silva', 'kleberson2santos@gmail.com', '99988877712', date_sub(sysdate(), interval 18 year),
        'Avenida Paulista',
        null, null, null, null, null);


insert into proposal (id, status, accept, client_id)
values (1, 'PENDING', 'TO_ACCEPT', 1);

insert into proposal (id, status, accept, client_id)
values (2, 'PENDING', 'TO_ACCEPT', 2);


