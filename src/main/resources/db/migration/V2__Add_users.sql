--
-- Add a user for all the reports.
--
-- Author: Lofi Dewanto
--

--
-- Add authorization data
--

insert into rcb_user (id, user_email) values (1, 'birt@test.de');

insert into rcb_role (id, role_name) values (1, 'USER_INTERSEROH');

insert into rcb_user_role (id, role_id, user_id) values (1, 1, 1);

insert into rcb_report (id, report_name, role_id) values (1, 'cascade_parameters', 1);
insert into rcb_report (id, report_name, role_id) values (2, 'chart', 1);
insert into rcb_report (id, report_name, role_id) values (3, 'chartdate', 1);
insert into rcb_report (id, report_name, role_id) values (4, 'custom', 1);
insert into rcb_report (id, report_name, role_id) values (5, 'multiselect', 1);
insert into rcb_report (id, report_name, role_id) values (6, 'salesinvoice', 1);
