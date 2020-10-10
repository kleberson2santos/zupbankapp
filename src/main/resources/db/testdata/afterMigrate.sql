set foreign_key_checks = 0;

delete
from client;
delete
from proposal;
delete
from account;

delete from cnh;
delete from cnh_files;

set foreign_key_checks = 1;

alter table client
    auto_increment = 1;
alter table proposal
    auto_increment = 1;
alter table account
    auto_increment = 1;
# alter table cnh auto_increment = 1;
# alter table cnh_files auto_increment = 1;

insert into client (id, nome)
values (1, 'João');

insert into proposal (id, status, client_id)
values (1, 'PENDING', 1);

insert into cnh (client_id) values (1);
insert into cnh_files (cnh_client_id) values (1);

