insert into studentdata.roles (role) values ('ROLE_ADMIN');
insert into studentdata.roles (role) values ('ROLE_STUDENT');

insert into studentdata.students (last_name, degree, name, password) VALUES ('Dahit', 'computer science', 'Santosh', '$2a$12$L9mEBSS.Pmm0mAGzbhpkYuQfEUc7GesXavR8LaSImjgqmvky1ouyG');/*dahit*/
--insert into studentdata.student (last_name, degree, first_name, password) VALUES ('Ghale', 'Data Science', 'Kiran', '$2a$12$TK5/HCH19cVDinR5uf1/4.KMawTay/nhSTcKqJf6rvB9ioAGPxWcK');/*ghale*/

insert into studentdata.students_roles (student_id, roles_id) VALUES (1, 1);