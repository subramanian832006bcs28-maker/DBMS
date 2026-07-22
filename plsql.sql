QL> DECLARE
  2     v_name  user1.username%TYPE;
  3     v_fname user1.first_name%TYPE;
  4  BEGIN
  5     SELECT username, first_name
  6     INTO v_name, v_fname
  7     FROM user1
  8     WHERE user_id = 1;
  9
 10     DBMS_OUTPUT.PUT_LINE('Username: ' || v_name);
 11     DBMS_OUTPUT.PUT_LINE('First Name: ' || v_fname);
 12  END;
 13  /
Username: ADMIN
First Name: Aravind

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_amt salesinvoice.total_amount%TYPE;
  3  BEGIN
  4     SELECT total_amount
  5     INTO v_amt
  6     FROM salesinvoice
  7     WHERE invoice_id=&iid;
  8
  9     IF v_amt>5000 THEN
 10        IF v_amt>10000 THEN
 11           DBMS_OUTPUT.PUT_LINE('Very High Amount');
 12        ELSE
 13           DBMS_OUTPUT.PUT_LINE('High Amount');
 14        END IF;
 15     ELSE
 16        DBMS_OUTPUT.PUT_LINE('Normal Amount');
 17     END IF;
 18  END;
 19  /
Enter value for iid: 1002
old   7:    WHERE invoice_id=&iid;
new   7:    WHERE invoice_id=1002;
Normal Amount

PL/SQL procedure successfully completed.

SQL> /
Enter value for iid: 1001
old   7:    WHERE invoice_id=&iid;
new   7:    WHERE invoice_id=1001;
High Amount

PL/SQL procedure successfully completed.

SQL> /
Enter value for iid: 104
old   7:    WHERE invoice_id=&iid;
new   7:    WHERE invoice_id=104;
Very High Amount

PL/SQL procedure successfully completed.

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_num NUMBER:=1;
  3  BEGIN
  4     LOOP
  5        DBMS_OUTPUT.PUT_LINE('Number: '||v_num);
  6        v_num:=v_num+1;
  7        EXIT WHEN v_num>5;
  8     END LOOP;
  9  END;
 10  /
Number: 1
Number: 2
Number: 3
Number: 4
Number: 5

PL/SQL procedure successfully completed.


SQL> BEGIN
  2     FOR i IN 1..5 LOOP
  3        DBMS_OUTPUT.PUT_LINE('User ID: '||i);
  4     END LOOP;
  5  END;
  6  /
User ID: 1
User ID: 2
User ID: 3
User ID: 4
User ID: 5

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     CURSOR user_cursor IS
  3        SELECT username,first_name FROM user1;
  4
  5     v_name user1.username%TYPE;
  6     v_fname user1.first_name%TYPE;
  7  BEGIN
  8     OPEN user_cursor;
  9
 10     LOOP
 11        FETCH user_cursor INTO v_name,v_fname;
 12        EXIT WHEN user_cursor%NOTFOUND;
 13
 14        DBMS_OUTPUT.PUT_LINE(v_name||' '||v_fname);
 15     END LOOP;
 16
 17     CLOSE user_cursor;
 18  END;
 19  /
ADMIN Aravind
cashier01
admin03 Arun
staff4 Meena
staff05 Kavi
staff06 Raja
staff07 Divya
staff05 Vijay
staff06 Hari
staff08 Anu
staff09 Bala

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     CURSOR inv_cursor IS
  3        SELECT invoice_id,total_amount
  4        FROM salesinvoice;
  5  BEGIN
  6     FOR rec IN inv_cursor LOOP
  7        DBMS_OUTPUT.PUT_LINE('Invoice:'||rec.invoice_id||
  8                             ' Amount:'||rec.total_amount);
  9     END LOOP;
 10  END;
 11  /
Invoice:1001 Amount:5500
Invoice:1002 Amount:3000
Invoice:1004 Amount:5000
Invoice:1006 Amount:3300
Invoice:1008 Amount:3600
Invoice:1009 Amount:4800

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     CURSOR c1 IS
  3        SELECT invoice_id,payment_status
  4        FROM salesinvoice;
  5  BEGIN
  6     FOR rec IN c1 LOOP
  7        IF rec.payment_status='PAID' THEN
  8           DBMS_OUTPUT.PUT_LINE('Invoice '||rec.invoice_id||' Paid');
  9        ELSE
 10           DBMS_OUTPUT.PUT_LINE('Invoice '||rec.invoice_id||' Pending');
 11        END IF;
 12     END LOOP;
 13  END;
 14  /
Invoice 1001 Paid
Invoice 1002 Paid
Invoice 1004 Paid
Invoice 1006 Paid
Invoice 1008 Paid
Invoice 1009 Paid

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_total NUMBER;
  3  BEGIN
  4     SELECT SUM(total_amount)
  5     INTO v_total
  6     FROM salesinvoice
  7     WHERE payment_status='PAID';
  8
  9     DBMS_OUTPUT.PUT_LINE('Total Paid Amount: '||v_total);
 10  END;
 11  /
Total Paid Amount: 25200

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     CURSOR c1 IS
  3     SELECT u.username,s.total_amount
  4     FROM user1 u
  5     JOIN salesinvoice s
  6     ON u.user_id=s.user_id;
  7  BEGIN
  8     FOR rec IN c1 LOOP
  9        DBMS_OUTPUT.PUT_LINE(rec.username||
 10                             ' Amount:'||rec.total_amount);
 11     END LOOP;
 12  END;
 13  /
ADMIN Amount:5500
cashier01 Amount:3000
staff4 Amount:5000
staff06 Amount:3300
staff05 Amount:3600
staff06 Amount:4800

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_name user1.username%TYPE;
  3  BEGIN
  4     SELECT username
  5     INTO v_name
  6     FROM user1
  7     WHERE user_id=
  8     (SELECT user_id
  9      FROM salesinvoice
 10      WHERE total_amount=
 11      (SELECT MAX(total_amount) FROM salesinvoice));
 12
 13     DBMS_OUTPUT.PUT_LINE('Highest Invoice User: '||v_name);
 14  END;
 15  /
Highest Invoice User: ADMIN

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     CURSOR c1 IS
  3     SELECT username
  4     FROM user1
  5     WHERE user_id IN
  6     (SELECT user_id FROM salesinvoice);
  7  BEGIN
  8     FOR rec IN c1 LOOP
  9        DBMS_OUTPUT.PUT_LINE('User with invoice: '||rec.username);
 10     END LOOP;
 11  END;
 12  /
User with invoice: ADMIN
User with invoice: cashier01
User with invoice: staff4
User with invoice: staff06
User with invoice: staff05
User with invoice: staff06

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_id user1.user_id%TYPE:=10;
  3     v_name user1.username%TYPE:='newuser';
  4  BEGIN
  5     INSERT INTO user1(user_id,username)
  6     VALUES(v_id,v_name);
  7
  8     DBMS_OUTPUT.PUT_LINE('User inserted');
  9     COMMIT;
 10  END;
 11  /
User inserted

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_id owner1.owner_id%TYPE:=1;
  3  BEGIN
  4     UPDATE owner1
  5     SET owner_address='Madurai'
  6     WHERE owner_id=v_id;
  7
  8     DBMS_OUTPUT.PUT_LINE('Owner updated');
  9     COMMIT;
 10  END;
 11  /
Owner updated

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_id salesReport.report_id%TYPE:=10;
  3  BEGIN
  4     DELETE FROM salesReport
  5     WHERE report_id=v_id;
  6
  7     DBMS_OUTPUT.PUT_LINE('Report deleted');
  8     COMMIT;
  9  END;
 10  /
Report deleted

PL/SQL procedure successfully completed.

SQL> DECLARE
  2     v_amt NUMBER:=6000;
  3  BEGIN
  4     IF v_amt>5000 THEN
  5        INSERT INTO salesReport
  6        VALUES(20,SYSDATE,'DAILY',5,6000);
  7
  8        DBMS_OUTPUT.PUT_LINE('Report inserted');
  9     ELSE
 10        DBMS_OUTPUT.PUT_LINE('Amount too low');
 11     END IF;
 12
 13     COMMIT;
 14  END;
 15  /
Report inserted

PL/SQL procedure successfully completed.

SQL>
SQL> DECLARE
  2     CURSOR c1 IS
  3     SELECT salary_payment_id,net_salary
  4     FROM salarypayment;
  5  BEGIN
  6     FOR rec IN c1 LOOP
  7        UPDATE salarypayment
  8        SET net_salary=rec.net_salary+100
  9        WHERE salary_payment_id=rec.salary_payment_id;
 10     END LOOP;
 11
 12     DBMS_OUTPUT.PUT_LINE('Salary updated');
 13     COMMIT;
 14  END;
 15  /
Salary updated

PL/SQL procedure successfully completed.

SQL> DECLARE
  2  BEGIN
  3     DELETE FROM salesinvoice
  4     WHERE payment_status='CANCELLED';
  5
  6     DBMS_OUTPUT.PUT_LINE('Cancelled invoices deleted');
  7     COMMIT;
  8  END;
  9  /
Cancelled invoices deleted

PL/SQL procedure successfully completed.


SQL> DECLARE
  2     TYPE user_rec IS RECORD(
  3        uname user1.username%TYPE,
  4        fname user1.first_name%TYPE
  5     );
  6     v_user user_rec;
  7  BEGIN
  8     SELECT username,first_name
  9     INTO v_user
 10     FROM user1
 11     WHERE user_id=1;
 12
 13     DBMS_OUTPUT.PUT_LINE(v_user.uname||' '||v_user.fname);
 14  END;
 15  /
ADMIN Aravind

PL/SQL procedure successfully completed.
SQL> DECLARE
  2     v_name user1.username%TYPE;
  3  BEGIN
  4     SELECT username
  5     INTO v_name
  6     FROM user1
  7     WHERE last_name=&lastname;
  8
  9     DBMS_OUTPUT.PUT_LINE('User: '||v_name);
 10
 11  EXCEPTION
 12     WHEN NO_DATA_FOUND THEN
 13        DBMS_OUTPUT.PUT_LINE('No user found');
 14     WHEN TOO_MANY_ROWS THEN
 15        DBMS_OUTPUT.PUT_LINE('Multiple users found');
 16     WHEN OTHERS THEN
 17        DBMS_OUTPUT.PUT_LINE('Other Error');
 18  END;
 19  /
Enter value for lastname: 'man'
old   7:    WHERE last_name=&lastname;
new   7:    WHERE last_name='man';
No user found

PL/SQL procedure successfully completed.

SQL> /
Enter value for lastname: 'Das'
old   7:    WHERE last_name=&lastname;
new   7:    WHERE last_name='Das';
User: staff05

PL/SQL procedure successfully completed.

SQL> /
Enter value for lastname: 'Raj'
old   7:    WHERE last_name=&lastname;
new   7:    WHERE last_name='Raj';
Multiple users found

PL/SQL procedure successfully completed.

SQL>
SQL> DECLARE
  2     CURSOR c1 IS SELECT * FROM user1;
  3     v_rec user1%ROWTYPE;
  4  BEGIN
  5     OPEN c1;
  6
  7     IF c1%ISOPEN THEN
  8        FETCH c1 INTO v_rec;
  9
 10        IF c1%FOUND THEN
 11           DBMS_OUTPUT.PUT_LINE('User: '||v_rec.username);
 12        ELSIF c1%NOTFOUND THEN
 13           DBMS_OUTPUT.PUT_LINE('No Record');
 14        END IF;
 15     END IF;
 16
 17     CLOSE c1;
 18  END;
 19  /
User: ADMIN

PL/SQL procedure successfully completed.


SQL> DECLARE
  2  BEGIN
  3     UPDATE salesinvoice
  4     SET payment_status='PAID'
  5     WHERE payment_status='PENDING';
  6
  7     IF SQL%FOUND THEN
  8        DBMS_OUTPUT.PUT_LINE('Rows Updated: '||SQL%ROWCOUNT);
  9     ELSIF SQL%NOTFOUND THEN
 10        DBMS_OUTPUT.PUT_LINE('No rows updated');
 11     END IF;
 12
 13     COMMIT;
 14  END;
 15  /
Rows Updated: 6

PL/SQL procedure successfully completed.



 Loop increment by 2
SQL> declare
  2     n number;
  3     begin
  4     n :=&n;
  5     dbms_output.put('even no between 0 and '||n||' are:');
  6     for i in 0..n by 2 loop
  7         dbms_output.put(i||' ');
  8     end loop;
  9      dbms_output.new_line;
 10     end;
 11     /
Enter value for n: 10
old   4:    n :=&n;
new   4:    n :=10;
even no between 0 and 10 are:0 2 4 6 8 10

PL/SQL procedure successfully completed.
 Reverse Loop
SQL>  declare
  2     n number;e
  3      begin
  4      n :=&n;
  5      for i in reverse 0..n loop
  6         if mod(i,2)!=0 then
  7                 dbms_output.put(i||' ');
  8                 continue;
  9         end if;
 10        if i=0 then
 11                dbms_output.new_line;
 12                dbms_output.put_line('came to zero');
 13                exit;
 14        end if;
 15     end loop;
 16     end;
 17     /
Enter value for n: 5
old   4:     n :=&n;
new   4:     n :=5;
5 3 1
came to zero

PL/SQL procedure successfully completed.

SQL> /
Enter value for n: 10
old   4:     n :=&n;
new   4:     n :=10;
9 7 5 3 1
came to zero

PL/SQL procedure successfully completed.


Hacker Rank


HACKERANK QUESTIONS

1)a). Fibonacci Series
SQL> declare
  2  a number := 0;
  3  b number := 1;
  4  c number;
  5  i number := 1;
  6  n number := 10;
  7  begin
  8  dbms_output.put_line(a);
  9  dbms_output.put_line(b);
 10  while i <= n loop
 11  c := a + b;
 12  dbms_output.put_line(c);
 13  a := b;
 14  b := c;
 15  i := i + 1;
 16  end loop;
 17  end;
 18  /
0
1
1
2
3
5
8
13
21
34
55
89
PL/SQL procedure successfully completed.
1)b). odd or even or positive or negative
SQL> declare
  2  n number := &n;
  3  temp number;
  4  rem number;
  5  sum1 number := 0;
  6  begin
  7  if n > 0 then
  8  dbms_output.put_line('positive number');
  9  else
 10  dbms_output.put_line('negative number');
 11  end if;
 12  if mod(n,2)=0 then
 13  dbms_output.put_line('even number');
 14  else
 15  dbms_output.put_line('odd number');
 16  end if;
 17  temp := n;
 18  while temp > 0 loop
 19  rem := mod(temp,10);
 20  sum1 := sum1 + rem*rem*rem;
 21  temp := trunc(temp/10);
 22  end loop;
 23  if sum1 = n then
 24  dbms_output.put_line('armstrong number');
 25  else
 26  dbms_output.put_line('not armstrong');
 27  end if;
 28  end;
 29  /
Enter value for n: 153
old   2: n number := &n;
new   2: n number := 153;
positive number
odd number
armstrong number
PL/SQL procedure successfully completed.
SQL> /
Enter value for n: 222
old   2: n number := &n;
new   2: n number := 222;
positive number
even number
not armstrong

PL/SQL procedure successfully completed.

SQL> /
Enter value for n: -1
old   2: n number := &n;
new   2: n number := -1;
negative number

PL/SQL procedure successfully completed.

SQL> declare
  2    n number := &num;
  3    counter number := 0;
  4  begin
  5    if n <= 1 then
  6      dbms_output.put_line(n || ' is not prime');
  7    else
  8      for i in 2..n/2 loop
  9        if mod(n, i) = 0 then
 10          counter := counter + 1;
 11          exit;
 12        end if;
 13      end loop;
 14
 15      if counter = 0 then
 16        dbms_output.put_line(n || ' is prime');
 17      else
 18        dbms_output.put_line(n || ' is not prime');
 19      end if;
 20    end if;
 21  end;
 22  /
Enter value for num: 32
old   2:   n number := &num;
new   2:   n number := 32;
32 is not prime

PL/SQL procedure successfully completed.

SQL> /
Enter value for num: 43
old   2:   n number := &num;
new   2:   n number := 43;
43 is prime

PL/SQL procedure successfully completed.

SQL> /
Enter value for num: 1
old   2:   n number := &num;
new   2:   n number := 1;
1 is not prime

PL/SQL procedure successfully completed.


2.Declare cursor to count number of employees residing in ‘sivakasi’ and earning salary higher than average salary of their departments wit necessary exception handling.
PL/SQL procedure successfully completed.
SQL> declare
  2  cursor c1 is
  3  select emp_id from employee
  4  where lower(emp_address)='sivakasi'
  5  and salary > (select avg(salary) from employee);
  6  v_id employee.emp_id%type;
  7  v_count number := 0;
  8  begin
  9  open c1;
 10  loop
 11  fetch c1 into v_id;
 12  exit when c1%notfound;
 13  v_count := v_count + 1;
 14  end loop;
 15  dbms_output.put_line('total employees: '||v_count);
 16  close c1;
 17  exception
 18  when others then
 19  dbms_output.put_line('error occurred');
 20  end;
 21  /
total employees: 3

PL/SQL procedure successfully completed.

3.Declare cursor to retrieve employee whose name starts with ‘a’ and display the values of  cursor attributes 
SQL> declare
  2  cursor c1 is
  3  select * from employee where lower(name) like 'a%';
  4  v_emp employee%rowtype;
  5  begin
  6  open c1;
  7  if c1%isopen then
  8  dbms_output.put_line('cursor is open');
  9  end if;
 10  loop
 11  fetch c1 into v_emp;
 12  exit when c1%notfound;
 13  dbms_output.put_line(v_emp.name);
 14  dbms_output.put_line('rowcount: '||c1%rowcount);
 15  if c1%found then
 16  dbms_output.put_line('record found');
 17  end if;
 18  end loop;
 19  close c1;
 20  end;
 21  /
cursor is open
arikaran
rowcount: 1
record found
arun
rowcount: 2
record found
