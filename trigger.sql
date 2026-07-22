BEFOREE INSESRT
SQL> CREATE OR REPLACE TRIGGER before_insert_user1
  2  BEFORE INSERT ON user1
  3  FOR EACH ROW
  4  BEGIN
  5      IF :NEW.username IS NULL THEN
  6          RAISE_APPLICATION_ERROR(-20001,'Username cannot be null');
  7      END IF;
  8  END;
  9  /

SQL> INSERT INTO user1 (user_id,password_hash)
  2  VALUES (20,'p20');
ERROR at line 1:
ORA-20001: Username cannot be null
ORA-04088: error during execution of trigger 'BEFORE_INSERT_USER1'

SQL> INSERT INTO user1
  2  VALUES (10,'user10','p10','Ravi','S',2);

1 row created.

SQL> SELECT * FROM user1;

USER_ID  USERNAME  PASSWORD_HASH  FIRST_NAME  LAST_NAME  ROLE_ID
-------  --------  -------------  ----------  ---------  -------
1        admin     pass123        Arun        Kumar      1
2        cashier   pass234        Kavi        Raj        2
10       user10    p10            Ravi        S          2

2. BEFORE UPDATE
SQL> CREATE OR REPLACE TRIGGER before_update_salary
  2  BEFORE UPDATE ON salarypayment
  3  FOR EACH ROW
  4  BEGIN
  5      IF :NEW.net_salary > :NEW.basic_salary THEN
  6          RAISE_APPLICATION_ERROR(-20002,'Net salary cannot exceed basic salary');
  7      END IF;
  8  END;
  9  /

SQL> UPDATE salarypayment
  2  SET net_salary = 50000
  3  WHERE salary_payment_id = 1;
ERROR at line 1:
ORA-20002: Net salary cannot exceed basic salary
ORA-04088: error during execution of trigger 'BEFORE_UPDATE_SALARY'

SQL> UPDATE salarypayment
  2  SET net_salary = 19000
  3  WHERE salary_payment_id=1;

1 row updated.

SQL> SELECT salary_payment_id, basic_salary, net_salary
  2  FROM salarypayment WHERE salary_payment_id=1;

SALARY_PAYMENT_ID  BASIC_SALARY  NET_SALARY
-----------------  ------------  ----------
1                  20000         19000

3. AFTER INSERT
SQL> CREATE OR REPLACE TRIGGER after_insert_salesinvoice
  2  AFTER INSERT ON salesinvoice
  3  FOR EACH ROW
  4  BEGIN
  5      DBMS_OUTPUT.PUT_LINE('Invoice '|| :NEW.invoice_id ||' inserted');
  6  END;
  7  /

SQL> INSERT INTO salesinvoice
  2  VALUES (3001,401,2,SYSDATE,20,980,'PAID');

Invoice 3001 inserted

1 row created.

SQL> SELECT invoice_id, total_amount, payment_status
  2  FROM salesinvoice
  3  WHERE invoice_id = 3001;

INVOICE_ID  TOTAL_AMOUNT  PAYMENT_STATUS
----------  ------------  --------------
3001        980           PAID

4. BEFORE DELETE
SQL> CREATE OR REPLACE TRIGGER before_delete_user1
  2  BEFORE DELETE ON user1
  3  FOR EACH ROW
  4  DECLARE
  5      cnt NUMBER;
  6  BEGIN
  7      SELECT COUNT(*) INTO cnt FROM salarypayment
  8      WHERE user_id = :OLD.user_id;
  9      IF cnt > 0 THEN
 10          RAISE_APPLICATION_ERROR(-20003,'Cannot delete user');
 11      END IF;
 12  END;
 13  /

SQL> DELETE FROM user1 WHERE user_id = 1;

ORA-20003: Cannot delete user

SQL> DELETE FROM user1
  2  WHERE user_id = 20;

1 row deleted.

SQL> SELECT user_id, username FROM user1 WHERE user_id = 1;

USER_ID  USERNAME
-------  --------
1        admin

5. AFTER DELETE
SQL> CREATE OR REPLACE TRIGGER after_delete_owner
  2  AFTER DELETE ON owner1
  3  FOR EACH ROW
  4  BEGIN
  5      DBMS_OUTPUT.PUT_LINE('Owner '|| :OLD.owner_name ||' deleted');
  6  END;
  7  /

SQL> DELETE FROM owner1
  2  WHERE owner_id = 104;

Owner Area Owner deleted

1 row deleted.

SQL> SELECT owner_id, owner_name FROM owner1 WHERE owner_id = 104;

OWNER_ID  OWNER_NAME
--------  ----------
104       Area Owner

6. AFTER UPDATE
SQL> CREATE OR REPLACE TRIGGER after_update_salesinvoice
  2  AFTER UPDATE ON salesinvoice
  3  FOR EACH ROW
  4  BEGIN
  5      DBMS_OUTPUT.PUT_LINE('Updated total amount: '|| :NEW.total_amount);
  6  END;
  7  /

SQL> UPDATE salesinvoice
  2  SET total_amount = 1500
  3  WHERE invoice_id = 1001;

Updated total amount: 1500

1 row updated.

SQL> SELECT invoice_id, total_amount
  2  FROM salesinvoice
  3  WHERE invoice_id = 1001;

INVOICE_ID  TOTAL_AMOUNT
----------  ------------
1001        1500

7. COMPOUND TRIGGER
SQL> CREATE OR REPLACE TRIGGER compound_salesinvoice_trigger
  2  FOR INSERT OR UPDATE ON salesinvoice
  3  COMPOUND TRIGGER
  4
  5  BEFORE EACH ROW IS
  6  BEGIN
  7      IF :NEW.total_amount > 0 THEN
  8          :NEW.payment_status := 'PAID';
  9      ELSE
 10          :NEW.payment_status := 'PENDING';
 11      END IF;
 12  END BEFORE EACH ROW;
 13
 14  END;
 15  /

SQL> INSERT INTO salesinvoice
  2  VALUES (4001,501,3,SYSDATE,0,0,NULL);

1 row created.

SQL> SELECT invoice_id, payment_status
  2  FROM salesinvoice
  3  WHERE invoice_id = 4001;

INVOICE_ID  PAYMENT_STATUS
----------  --------------
4001        PENDING

SQL> INSERT INTO salesinvoice
  2  VALUES (4002,502,3,SYSDATE,10,1000,NULL);

1 row created.

SQL> SELECT invoice_id, payment_status
  2  FROM salesinvoice
  3  WHERE invoice_id = 4002;

INVOICE_ID  PAYMENT_STATUS
----------  --------------
4002        PAID
8. STATEMENT TRIGGER
SQL> CREATE OR REPLACE TRIGGER statement_trigger_salesinvoice
  2  AFTER INSERT OR DELETE ON salesinvoice
  3  DECLARE
  4      total NUMBER;
  5  BEGIN
  6      SELECT COUNT(*) INTO total FROM salesinvoice;
  7      DBMS_OUTPUT.PUT_LINE('Total invoices: '|| total);
  8  END;
  9  /

SQL> DELETE FROM salesinvoice WHERE invoice_id=1005;

Total invoices: 6

1 row deleted.
SQL> SELECT COUNT(*) AS TOTAL_INVOICES FROM salesinvoice;

TOTAL_INVOICES
--------------
6

9. LAST_MODIFIED STYLE (EXACT LIKE STAFF SAMPLE)
SQL> CREATE OR REPLACE TRIGGER trg_update_report_date
  2  BEFORE UPDATE ON salesreport
  3  FOR EACH ROW
  4  BEGIN
  5      :NEW.report_date := SYSDATE;
  6  END;
  7  /

SQL> UPDATE salesreport
  2  SET total_orders = 25
  3  WHERE report_id = 1;

1 row updated.
SQL> SELECT report_date FROM salesreport WHERE report_id=1;

REPORT_DATE
-----------
17-MAR-26

hackerank


A) Write a code in PL/SQL TO create a trigger that automatically updates a 'last_modified' timestamp whenever a row in a specific table is updated.

SQL> update inventory
  2  set stockout=stockout-50 where currentstock<0;

1 row updated.

SQL> select * from inventory;

INVENTORYID MEDICINEID    STOCKIN   STOCKOUT CURRENTSTOCK UPDATEDDA
----------- ---------- ---------- ---------- ------------ ---------
         11          1         90         60           30 08-FEB-26
         12          2         57         32           35 11-FEB-26
         13          3         79         62           17 13-FEB-26
         14          5         40         21            9 15-FEB-26
         15          4         30          5           35 15-FEB-26
         10          4         30          5           35 15-FEB-26

6 rows selected.

SQL> create or replace trigger trg_inventory_timestamp
  2  before update on inventory
  3  for each row
  4  begin
  5  :new.updateddate := sysdate;
  6  end;
  7  /

Trigger created.

SQL> update inventory
  2  set stockout=stockout-10 where currentstock>30;

3 rows updated.

SQL> select * from inventory;

INVENTORYID MEDICINEID    STOCKIN   STOCKOUT CURRENTSTOCK UPDATEDDA
----------- ---------- ---------- ---------- ------------ ---------
         11          1         90         60           30 08-FEB-26
         12          2         57         22           35 26-MAR-26
         13          3         79         62           17 13-FEB-26
         14          5         40         21            9 15-FEB-26
         15          4         30         -5           35 26-MAR-26
         10          4         30         -5           35 26-MAR-26

6 rows selected.

B)	Write a code in PL/SQL to create a trigger that prevents updates on a certain column during specific hours of the day.

SQL> select systimestamp from dual;

SYSTIMESTAMP
---------------------------------------------------------------------------
26-MAR-26 10.27.17.633000 PM +05:30

SQL> update employee
  2  set salary=salary+10;

13 rows updated.

SQL> create or replace trigger trg_restrict_update
  2  before update of salary on employee
  3  for each row
  4  begin
  5  if to_char(sysdate,'hh24') between 18 and 23
  6  or to_char(sysdate,'hh24') between 0 and 9 then
  7  raise_application_error(-20001,'updates not allowed now');
  8  end if;
  9  end;
 10  /

Trigger created.

SQL> update employee
  2  set salary=salary+10;
update employee
       *
ERROR at line 1:
ORA-20001: updates not allowed now
ORA-06512: at "SYSTEM.TRG_RESTRICT_UPDATE", line 4
ORA-04088: error during execution of trigger 'SYSTEM.TRG_RESTRICT_UPDATE'

Self - 2
A)	Write a code in PL/SQL to develop a trigger that enforces referential integrity by preventing the deletion of a parent record if child records exist.

SQL> create or replace trigger trg_no_delete_role
  2  before delete on role
  3  for each row
  4  declare
  5  v_count number;
  6  begin
  7  select count(*) into v_count
  8  from employee
  9  where role_id = :old.role_id;
 10
 11  if v_count > 0 then
 12  raise_application_error(-20002,'cannot delete role, employees exist');
 13  end if;
 14  end;
 15  /

Trigger created.

SQL> delete from role where role_id=1;
delete from role where role_id=1
            *
ERROR at line 1:
ORA-20002: cannot delete role, employees exist
ORA-06512: at "SYSTEM.TRG_NO_DELETE_ROLE", line 9
ORA-04088: error during execution of trigger 'SYSTEM.TRG_NO_DELETE_ROLE'

B)	Write a code in PL/SQL to create a trigger that checks for duplicate values in a specific column and raises an exception if found.

SQL> create or replace trigger trg_no_duplicate_email
  2  before insert or update on employee
  3  for each row
  4  declare
  5  v_count number;
  6  begin
  7  select count(*) into v_count
  8  from employee
  9  where emp_email = :new.emp_email;
 10
 11  if v_count > 0 then
 12  raise_application_error(-20003,'duplicate email not allowed');
 13  end if;
 14  end;
 15  /

Trigger created.

SQL> update employee'
  2
SQL> update employee
  2  set emp_email='manoj@gmail.com' where emp_email is null;
update employee
       *
ERROR at line 1:
ORA-04091: table SYSTEM.EMPLOYEE is mutating, trigger/function may not see it
ORA-06512: at "SYSTEM.TRG_NO_DUPLICATE_EMAIL", line 4
ORA-04088: error during execution of trigger 'SYSTEM.TRG_NO_DUPLICATE_EMAIL'

Self - 3

A)	Write a code in PL/SQL to create a trigger that validates the availability of items before allowing an order to be placed, considering stock levels and pending orders.

SQL> select * from purchasedetails;

PURCHASEID    BATCHID MEDICINEID   QUANTITY  UNITPRICE   SUBTOTAL
---------- ---------- ---------- ---------- ---------- ----------
                               3         20         40
                               5         20         40

SQL> select * from inventory;

INVENTORYID MEDICINEID    STOCKIN   STOCKOUT CURRENTSTOCK UPDATEDDA
----------- ---------- ---------- ---------- ------------ ---------
         11          1         90         60           30 08-FEB-26
         12          2         57         22           35 26-MAR-26
         13          3         79         62           17 13-FEB-26
         14          5         40         21            9 15-FEB-26
         15          4         30          5           35 26-MAR-26
         10          4         30          5           35 26-MAR-26

6 rows selected.

SQL> create or replace trigger trg_check_stock
  2  before insert on purchasedetails
  3  for each row
  4  declare
  5  v_stock number;
  6  begin
  7  select currentstock into v_stock
  8  from inventory
  9  where medicineid = :new.medicineid;
 10
 11  if v_stock < :new.quantity then
 12  raise_application_error(-20004,'insufficient stock');
 13  end if;
 14  end;
 15  /

Trigger created.

SQL> insert into purchasedetails values(4,4,2,10,30,300)
  2  ;

1 row created.

SQL> select * from inventory;

INVENTORYID MEDICINEID    STOCKIN   STOCKOUT CURRENTSTOCK UPDATEDDA
----------- ---------- ---------- ---------- ------------ ---------
         11          1         90         60           30 08-FEB-26
         12          2         67         22           45 26-MAR-26
         13          3         79         62           17 13-FEB-26
         14          5         40         21            9 15-FEB-26
         15          4         30          5           35 26-MAR-26
         10          4         30          5           35 26-MAR-26

6 rows selected.

SQL> insert into purchasedetails values(5,4,3,1000,3,3000);
insert into purchasedetails values(5,4,3,1000,3,3000)
            *
ERROR at line 1:
ORA-20004: insufficient stock
ORA-06512: at "SYSTEM.TRG_CHECK_STOCK", line 9
ORA-04088: error during execution of trigger 'SYSTEM.TRG_CHECK_STOCK'

B)	Design a trigger that sends an email notification to a predefined address whenever an error occurs during a specific operation.

SQL> create or replace trigger trg_salary_alert
  2  before insert or update on employee
  3  for each row
  4  begin
  5  if :new.salary < 0 then
  6  dbms_output.put_line(
  7  'user ' || user || ' tried invalid salary update on employee');
  8  raise_application_error(-20005,'operation not allowed');
  9  end if;
 10  end;
 11  /

Trigger created.

SQL> update employee
  2  set salary=salary-50000 where emp_id=101;
Starting salary update batch...
user SYSTEM tried invalid salary update on employee
update employee
       *
ERROR at line 1:
ORA-20005: operation not allowed
ORA-06512: at "SYSTEM.TRG_SALARY_ALERT", line 8
ORA-04088: error during execution of trigger 'SYSTEM.TRG_SALARY_ALERT'

qus
7A)
A)

SQL> update enroll set marks = marks * 1.10 where course_id in ( select course_id  from teaches where lower(instructor_name) = 'dr.kumar');

2 rows updated.

SQL> select * from enroll where course_id=201;

STUDENT_ID  COURSE_ID      MARKS
---------- ---------- ----------
       104        201       93.5
       109        201         99
B)
SQL> SELECT student_name FROM Student WHERE student_id IN ( SELECT student_id FROM Enroll WHERE marks = ( SELECT MAX(marks) FROM Enroll WHERE marks < (SELECT MAX(marks) FROM Enroll)));

STUDENT_NAME
--------------------
kabes

C)

SQL> SELECT DISTINCT c.course_name FROM Course c JOIN Enroll e ON c.course_id = e.course_id WHERE e.marks > (SELECT AVG(marks) FROM Enroll);
   
COURSE_NAME
--------------------------------------------------
ds
dbms
D)

SQL> SELECT s.student_name, s.city FROM Student s JOIN Enroll e ON s.student_id = e.student_id WHERE e.marks = (SELECT MAX(marks) FROM Enroll);

STUDENT_NAME    CITY
--------------- ---------------
kabes          tenkasi
E)
SQL> CREATE OR REPLACE FUNCTION get_max_enroll_course
  2  RETURN VARCHAR2
  3  IS
  4      v_course_name Course.course_name%TYPE;
  5  BEGIN
  6      SELECT c.course_name
  7      INTO v_course_name
  8      FROM Course c
  9      WHERE c.course_id = (
 10          SELECT course_id
 11          FROM (
 12              SELECT course_id, COUNT(*) AS total
 13              FROM Enroll
 14              GROUP BY course_id
 15              ORDER BY total DESC
 16          )
 17          WHERE ROWNUM = 1
 18      );
 19
 20      RETURN v_course_name;
 21  END;
 22  /
Function created.

SQL> SELECT get_max_enroll_course FROM dual;

GET_MAX_ENROLL_COURSE
--------------------------
ds
7 
A)

SQL> select * from staff;


  STAFF_ID STAFF_NAME      BLOCK      BUILDING
---------- --------------- ---------- ----------
       201 mana            a          main
       202 kabes           b          main
       203 vinoth          c          sub
       204 vasanth         a          primary

SQL> SELECT staff_name FROM Staff WHERE LOWER(building) = 'a' OR LOWER(block) = 'c';

STAFF_NAME
---------------
Vijay

B)

SQL> select b.*, m.* from books b join borrow br on b.book_id = br.book_id join members m on m.member_id = br.member_id join fine f on f.member_id = m.member_id where b.category in ('programming', 'database') and f.amount > 20;

   BOOK_ID BOOK_TITLE      AUTHOR          CATEGORY             PRICE  MEMBER_ID
---------- --------------- --------------- --------------- ---------- ----------
MEMBER_NAME     DEPT            ADDRESS         PHONE
--------------- --------------- --------------- ---------------
       101 data structures bhuvi           programming            450          5
bala            cse             coimbatore      9090909090


C)

SQL> CREATE OR REPLACE VIEW member_book_view AS
  2  SELECT m.member_id, m.member_name, b.book_title
  3  FROM Members m
  4  JOIN Borrow br ON m.member_id = br.member_id
  5  JOIN Books b ON b.book_id = br.book_id;

View created.

SQL> SELECT * FROM member_book_view;

 MEMBER_ID MEMBER_NAME     BOOK_TITLE
---------- --------------- -----------------
         1 kabes           data structures
         4 manoj           data structures
         5 bala            data structures
         2 akash           database systems
         3 ram             operating systems

D)

SQL> CREATE OR REPLACE PROCEDURE add_book (
  2      p_id NUMBER,
  3      p_title VARCHAR2,
  4      p_author VARCHAR2,
  5      p_category VARCHAR2,
  6      p_price NUMBER
  7  )
  8  IS
  9  BEGIN
 10      INSERT INTO Books
 11      VALUES (p_id, p_title, p_author, p_category, p_price);
 12
 13      COMMIT;
 14  END;
 15  /

Procedure created.

SQL> select * from books;

   BOOK_ID BOOK_TITLE      AUTHOR          CATEGORY             PRICE
---------- --------------- --------------- --------------- ----------
       101 data structures bhuvi           programming            450
       102 database system valar           database               550
           s

       103 operating syste charles         os                     600
           ms

       104 computer networ bala            network                500
           ks

       105 software engine prem            software               480

   BOOK_ID BOOK_TITLE      AUTHOR          CATEGORY             PRICE
---------- --------------- --------------- --------------- ----------
           ering


SQL> BEGIN
  2      add_book(106, 'ai basics', 'russell', 'ai', 700);
  3  END;
  4  /

PL/SQL procedure successfully completed.

SQL> select * from books where book_id=106;

   BOOK_ID BOOK_TITLE      AUTHOR          CATEGORY             PRICE
---------- --------------- --------------- --------------- ----------
       106 ai basics       virat         ai                     700

E)

SQL> select member_name from members;

MEMBER_NAME
---------------
kabes
akash
ram
manoj
bala

Elapsed: 00:00:00.03

SQL> Create index idx_member_name on Members(member_name);

Index created.

Elapsed: 00:00:00.01

SQL> select member_name from members;

MEMBER_NAME
---------------
kabes
akash
ram
manoj
bala

Elapsed: 00:00:00.00

