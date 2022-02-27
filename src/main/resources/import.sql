insert into monthly_balance (uuid, reference_date) values ('3de67bc9-d50a-4b46-8f03-dedaa0eabd3b', '2022-03-01');

insert into monthly_balance (uuid, reference_date) values ('a9f8edb1-7ca0-4b9a-bb99-9ec72b532c75', '2022-04-01');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('5dbe4867-5969-4b1e-a8a8-521c61d02c67', 100.00, 'Conta Telefonica', '2022-03-01', 'LIQUIDATED', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('7fb00cba-fc0a-4355-923a-7f8e9d2a782a', 110.00, 'Conta Energia', '2022-03-05', 'PENDING', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('d016e3a2-c1bb-4333-a4da-808b259c89a2', 150.00, 'Conta Agua', '2022-03-05', 'LIQUIDATED', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('0c59af50-cda1-41e4-b022-725006eac3ab', 180.00, 'Conta Gás', '2022-03-05', 'PENDING', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('18d84daf-a5b4-41bf-be37-d1e096187471', 156.00, 'Conta Energia', '2022-03-05', 'PENDING', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('c6310862-f767-4e47-9260-dc7888c8d4b3', 210.00, 'Nubank', '2022-03-05', 'PENDING', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('aba80456-b46c-42e7-947f-06e172d97bae', 500.00, 'Original', '2022-03-05', 'LIQUIDATED', false, '3de67bc9-d50a-4b46-8f03-dedaa0eabd3b');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('15e1b990-47f1-4a8f-9489-20d814c37eb3', 500.00, 'C6', '2022-04-05', 'PENDING', false, 'a9f8edb1-7ca0-4b9a-bb99-9ec72b532c75');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('64461a43-882d-464b-a571-737c85c7ea44', 495.00, 'Trigg', '2022-04-05', 'PENDING', false, 'a9f8edb1-7ca0-4b9a-bb99-9ec72b532c75');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('d73de370-0aeb-4991-b7be-d3866093744b', 399.00, 'Empréstimo Santander', '2022-04-05', 'LIQUIDATED', false, 'a9f8edb1-7ca0-4b9a-bb99-9ec72b532c75');

insert into expense (uuid, amount, description, due_date, expense_status, recurrent, monthly_balance_uuid) values ('8d4f60b3-9dc2-4177-ab46-1640aa4d25af', 780.00, 'Provi Cursos', '2022-04-05', 'PENDING', false, 'a9f8edb1-7ca0-4b9a-bb99-9ec72b532c75');

insert into income (uuid, amount, description, receipt_date) values ('05111c30-64cb-4e5c-a581-d8e746de6257', 550.95, 'Aluguel', '2022-03-10');

insert into income (uuid, amount, description, receipt_date) values ('4d014989-091f-4ffb-8b9b-51489089ceec', 5896.95, 'Aluguel', '2022-03-10');

insert into income (uuid, amount, description, receipt_date) values ('06a681a8-055f-4701-ad7c-1c3b012ae4a1', 299.95, 'Juros', '2022-03-10');