set foreign_key_checks = 0;

delete
from client;
delete
from proposal;
delete
from account;

set foreign_key_checks = 1;

alter table client
    auto_increment = 1;
alter table proposal
    auto_increment = 1;
alter table account
    auto_increment = 1;

insert into client (id, nome)
values (1, 'João');

insert into proposal (id, status, client_id)
values (1, 'PENDING', 1);