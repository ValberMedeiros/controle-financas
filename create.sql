create table expense (uuid uuid not null, amount numeric(19, 2) not null, description varchar(255), due_date date not null, expense_status varchar(255) not null, recurrent boolean not null, monthly_balance_uuid uuid, primary key (uuid));
create table income (uuid uuid not null, amount numeric(19, 2) not null, description varchar(255), receipt_date date not null, primary key (uuid));
create table monthly_balance (uuid uuid not null, reference_date date, primary key (uuid));
alter table if exists expense add constraint FK9jne6032f6iljyvk80iittx3s foreign key (monthly_balance_uuid) references monthly_balance;
