

1--create table
create table students(student_id number primary key,name varchar2(20),age number);

2--alter table
ALTER TABLE students
ADD email VARCHAR(100);
ALTER TABLE students
ADD (
    address VARCHAR(255),
    phone VARCHAR(15)
);
ALTER TABLE students
MODIFY name VARCHAR(150);

ALTER TABLE students
CHANGE old_name new_name VARCHAR(100);

ALTER TABLE students
DROP COLUMN phone;
ALTER TABLE students
ADD PRIMARY KEY (student_id);

ALTER TABLE students
DROP PRIMARY KEY;
ALTER TABLE employees
ADD CONSTRAINT fk_dept
FOREIGN KEY (dept_id)
REFERENCES departments(dept_id);

ALTER TABLE employees
DROP FOREIGN KEY fk_dept;

3-- delete duplicate emails
# Write your MySQL query statement below
DELETE p1
FROM Person p1
JOIN Person p2
ON p1.email = p2.email
AND p1.id > p2.id;
