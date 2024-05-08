
alter table users add column owner_id UUID;
alter table users add foreign key (owner_id) references owners(id);