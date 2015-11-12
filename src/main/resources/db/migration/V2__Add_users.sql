--
-- Add a user for all the reports.
--
-- Author: Lofi Dewanto
--

--
-- Add authorization data
--

insert into rcb_user (id, user_email) values (1, 'birt@test.de');

insert into rcb_role (id, role_name) values (1, 'ROLE_SALESINVOICE');
insert into rcb_role (id, role_name) values (2, 'ROLE_MULTISELECT');
insert into rcb_role (id, role_name) values (3, 'ROLE_CUSTOM');
insert into rcb_role (id, role_name) values (4, 'ROLE_CHARTDATE');
insert into rcb_role (id, role_name) values (5, 'ROLE_CHART');
insert into rcb_role (id, role_name) values (6, 'ROLE_CASCADE_PARAMETERS');

insert into rcb_user_role (id, role_id, user_id) values (1, 1, 1);
insert into rcb_user_role (id, role_id, user_id) values (2, 2, 1);
insert into rcb_user_role (id, role_id, user_id) values (3, 3, 1);
insert into rcb_user_role (id, role_id, user_id) values (4, 4, 1);
insert into rcb_user_role (id, role_id, user_id) values (5, 5, 1);
insert into rcb_user_role (id, role_id, user_id) values (6, 6, 1);

insert into rcb_report (id, report_name, role_id) values (1, 'cascade_parameters', 6);
insert into rcb_report (id, report_name, role_id) values (2, 'chart', 5);
insert into rcb_report (id, report_name, role_id) values (3, 'chartdate', 4);
insert into rcb_report (id, report_name, role_id) values (4, 'custom', 3);
insert into rcb_report (id, report_name, role_id) values (5, 'multiselect', 2);
insert into rcb_report (id, report_name, role_id) values (6, 'salesinvoice', 1);
