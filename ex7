1 A===TO FIND A NO IS EVEN OR ODD POSITIVE OR NEGATIVE OR ZERO AND ARMSTRONG OR NOT
==========================================================================================
SQL> SET SERVEROUTPUT ON;
SQL> DECLARE
  2     V_NUM NUMBER;
  3     L_LEN NUMBER :=0;
  4     N NUMBER :=0;
  5     REM NUMBER :=0;
  6     ANS NUMBER :=0;
  7  BEGIN
  8     V_NUM := &v_num;

  9     IF V_NUM = 0 THEN
 10         DBMS_OUTPUT.PUT_LINE('NUMBER IS ZERO');
 11     END IF;

 12     IF V_NUM > 0 THEN
 13         DBMS_OUTPUT.PUT_LINE('NUMBER IS POSITIVE');
 14     ELSE
 15         DBMS_OUTPUT.PUT_LINE('NUMBER IS NEGATIVE');
 16     END IF;

 17     IF MOD(V_NUM,2)=0 THEN
 18         DBMS_OUTPUT.PUT_LINE('EVEN');
 19     ELSE
 20         DBMS_OUTPUT.PUT_LINE('ODD');
 21     END IF;

 22     L_LEN := LENGTH(TO_CHAR(ABS(V_NUM)));
 23     N := (V_NUM);

 24     WHILE N <> 0 LOOP
 25         REM := MOD(N,10);
 26         ANS := ANS + POWER(REM,L_LEN);
 27         N := TRUNC(N/10);
 28     END LOOP;

 29     IF ANS = (V_NUM) THEN
 30         DBMS_OUTPUT.PUT_LINE('NUMBER IS ARMSTRONG');
 31     ELSE
 32         DBMS_OUTPUT.PUT_LINE('NUMBER IS NOT ARMSTRONG');
 33     END IF;

 34  END;
 35  /
Enter value for v_num: 153
NUMBER IS POSITIVE
ODD
NUMBER IS ARMSTRONG

PL/SQL procedure successfully completed.


Enter value for v_num: -24
NUMBER IS NEGATIVE
EVEN
NUMBER IS NOT ARMSTRONG

PL/SQL procedure successfully completed.

Enter value for v_num: 24
NUMBER IS POSITIVE
EVEN
NUMBER IS NOT ARMSTRONG

PL/SQL procedure successfully completed.


1 B==Fibonacci series========================================================================

SQL> SET SERVEROUTPUT ON;
SQL> DECLARE
  2     A NUMBER := -1;
  3     B NUMBER := 1;
  4     TEMP NUMBER := 0;
  5     NUM NUMBER;
  6  BEGIN
  7     NUM := &NUM;
  8
  9     FOR I IN 1..NUM LOOP
 10         TEMP := A + B;
 11         DBMS_OUTPUT.PUT_LINE(TEMP || ' ');
 12         A := B;
 13         B := TEMP;
 14     END LOOP;
 15  END;
 16  /
Enter value for num: 8
0
1
1
2
3
5
8
13

PL/SQL procedure successfully completed.

2 DECLARE CURSOR TO RETRIEVE STUDENT WHO NAME STARTS WITH 'M; AND DISPLAY CURSOR ATTRIBUTES
==========================================================================================================================================
SQL> declare
  2     v_name varchar(20);
  3     v_age number;
  4     cursor c_cursor is select student_name,age from student where student_name like 'm%';
  5  begin
  6     open c_cursor;

  7     fetch c_cursor into v_name,v_age;
  8     if c_cursor%found then
  9        dbms_output.put_line('first row fetched '|| v_name);
 10     else
 11        dbms_output.put_line('no row fetched');
 12     end if;

 13     fetch c_cursor into v_name,v_age;
 14     if c_cursor%found then
 15        dbms_output.put_line('second row fetched '|| v_name);
 16     else
 17        dbms_output.put_line('no row fetched');
 18     end if;

 19     if c_cursor%isopen then
 20        dbms_output.put_line('cursor open');
 21     else
 22        dbms_output.put_line('cursor not open');
 23     end if;

 24     dbms_output.put_line('the no of rows fetched: '||c_cursor%rowcount);

 25     close c_cursor;

 26     if c_cursor%isopen then
 27        dbms_output.put_line('cursor open');
 28     else
 29        dbms_output.put_line('cursor not open');
 30     end if;

 31  end;
 32  /
first row fetched mohammed aslam
second row fetched mohammed yousuf
cursor open
the no of rows fetched: 2
cursor not open

PL/SQL procedure successfully completed.
==========================================================================================================================================
 3 DECLARE CURSOR TO COUNT NO OF STUDENTS IN CHENNAI AND AGE HIGHER THAN AVG AGE OS THEIR CITY
==============================================================================================
SQL> SELECT COUNT(*)
  2  FROM student
  3  WHERE age >
  4        (SELECT AVG(age)
  5         FROM student
  6         WHERE city='chennai')
  7  AND city='chennai';

  COUNT(*)
------------
         1

SQL> DECLARE
2     c_count NUMBER;
3     cursor_open EXCEPTION;
4     CURSOR c_cursor IS
5        SELECT COUNT(*)
6        FROM student
7        WHERE age >
8        (SELECT AVG(age)
9         FROM student
10         WHERE city='chennai')
11        AND city='chennai';
12  BEGIN
13     OPEN c_cursor;
14
15     FETCH c_cursor INTO c_count;
16
17     IF c_cursor%ISOPEN THEN
18        DBMS_OUTPUT.PUT_LINE('No of students in Chennai greater than average age: ' || c_count);
19     ELSE
20        RAISE cursor_open;
21     END IF;
22
23     CLOSE c_cursor;
24
25     if c_cursor%ISOPEN THEN
26        DBMS_OUTPUT.PUT_LINE('Cursor closed');
27     END IF;
28
29  EXCEPTION
30     WHEN cursor_open THEN
31        DBMS_OUTPUT.PUT_LINE('Error: Cursor closed');
32  END;
33  /
No of students in Chennai greater than average age: 1
Cursor closed

PL/SQL procedure successfully completed

4====IF ELSIF,ELSE WITH COMMAND PROMPT INPUT TO FIND A STUDENT IS JUNIOR ,SENIOR, MIDDLER
==========================================================================================================================================
SQL> DECLARE
2     v_name student.student_name%TYPE;
3     v_age student.age%TYPE;
4
5     v_input1 VARCHAR2(30);
6     v_input2 VARCHAR2(30);
7     v_input3 VARCHAR2(30);
8
9  BEGIN
10
11     v_input1 := '&name1';
12     v_input2 := '&name2';
13     v_input3 := '&name3';
14
15     FOR rec IN (SELECT student_name, age
16                 FROM student
17                 WHERE student_name IN (v_input1, v_input2, v_input3))
18     LOOP
19
20        IF rec.age < 20 THEN
21           DBMS_OUTPUT.PUT_LINE('NAME: '||rec.student_name||' AGE: '||rec.age||' ADULT');
22
23        ELSIF rec.age >=20 AND rec.age <=24 THEN
24           DBMS_OUTPUT.PUT_LINE('NAME: '||rec.student_name||' AGE: '||rec.age||' MIDDLE AGE');
25
26        ELSE
27           DBMS_OUTPUT.PUT_LINE('NAME: '||rec.student_name||' AGE: '||rec.age||' SENIOR');
28
29        END IF;
30
31     END LOOP;
32
33  END;
34  /
Enter value for name1: mohammed aslam
Enter value for name2: kmano
Enter value for name3: mohammed yousuf
NAME: mohammed aslam AGE: 19 ADULT
NAME: kmano AGE: 24 MIDDLE AGE
NAME: mohammed yousuf AGE: 29 SENIOR.

PL/SQL procedure successfully completed.
