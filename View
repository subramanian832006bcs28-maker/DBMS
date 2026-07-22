L> CREATE VIEW user_view AS
  2  SELECT user_id, username, role_id
  3  FROM user1;

View created.
SQL> select * from user_view;

   USER_ID USERNAME                ROLE_ID
---------- -------------------- ----------
         1 ADMIN                         2
         2 cashier01                     3
         3 admin03                      11
         4 staff4                       12
         5 staff05                      13
         6 staff06                      14
         7 staff07                      15
         8 staff05                      19
         9 staff06                      18
        11 staff08                      43
        12 staff09                      44

11 rows selected.

Elapsed: 00:00:00.02
SQL>
SQL>  CREATE VIEW paid_invoice_view AS
  2  SELECT invoice_id, user_id, total_amount
  3  FROM salesinvoice
  4  WHERE payment_status = 'PAID';

View created.
SQL> select * from paid_invoice_view;

INVOICE_ID    USER_ID TOTAL_AMOUNT
---------- ---------- ------------
      1001          1         5500
      1002          2         3000
      1004          4         5000
      1006          6         3300
      1008          8         3600
      1009          9         4800

6 rows selected.

Elapsed: 00:00:00.00
SQL>
SQL>  CREATE VIEW invoice_user_view AS
  2  SELECT s.invoice_id,
  3         u.username,
  4         s.total_amount,
  5         s.invoice_date
  6  FROM salesinvoice s
  7  JOIN user1 u
  8  ON s.user_id = u.user_id;

View created.
SQL> select * from invoice_user_view;

INVOICE_ID USERNAME             TOTAL_AMOUNT INVOICE_D
---------- -------------------- ------------ ---------
      1001 ADMIN                        5500 12-FEB-26
      1002 cashier01                    3000 12-FEB-26
      1004 staff4                       5000 12-FEB-26
      1006 staff06                      3300 12-FEB-26
      1008 staff05                      3600 12-FEB-26
      1009 staff06                      4800 12-FEB-26

6 rows selected.

Elapsed: 00:00:00.00
SQL>
SQL>
SQL> CREATE VIEW invoice_detail_view AS
  2  SELECT s.invoice_id,
  3         u.username,
  4         d.medicine_id,
  5         d.quantity,
  6         d.unit_price
  7  FROM salesinvoice s
  8  JOIN user1 u
  9  ON s.user_id = u.user_id
 10  JOIN SalesInvoiceDetail d
 11  ON s.invoice_id = d.invoice_id;

View created.
SQL> select * from  invoice_detail_view;

INVOICE_ID USERNAME             MEDICINE_ID   QUANTITY UNIT_PRICE
---------- -------------------- ----------- ---------- ----------
      1001 ADMIN                        501          6        110
      1002 cashier01                    502          4        200
      1004 staff4                       504          7        120
      1006 staff06                      506          8        100
      1008 staff05                      508          6        130
      1009 staff06                      509          9         75

6 rows selected.

Elapsed: 00:00:00.01
SQL>
SQL>  SELECT * FROM user_view;

   USER_ID USERNAME                ROLE_ID
---------- -------------------- ----------
         1 rajesh01                      2
         2 cashier01                     3
         3 admin03                      11
         4 staff4                       12
         5 staff05                      13
         6 staff06                      14
         7 staff07                      15
         8 staff05                      19
         9 staff06                      18
        11 staff08                      43
        12 staff09                      44

11 rows selected.

SQL>  UPDATE user_view
  2  SET username = 'ADMIN'
  3  WHERE user_id = 1;

1 row updated.

SQL> DROP VIEW user_view;

View dropped.

SQL> SELECT invoice_id, user_id, total_amount
  2  FROM salesinvoice;

INVOICE_ID    USER_ID TOTAL_AMOUNT
---------- ---------- ------------
      1001          1         5500
      1002          2         3000
      1004          4         5000
      1006          6         3300
      1008          8         3600
      1009          9         4800

6 rows selected.

SQL> CREATE INDEX idx_salesinvoice_user
  2  ON salesinvoice(user_id);

Index created.


SQL> SELECT invoice_id, user_id, total_amount
  2  FROM salesinvoice;

INVOICE_ID    USER_ID TOTAL_AMOUNT
---------- ---------- ------------
      1001          1         5500
      1002          2         3000
      1004          4         5000
      1006          6         3300
      1008          8         3600
      1009          9         4800

6 rows selected.

Elapsed: 00:00:00.01

SQL> SELECT invoice_id, user_id, invoice_date
  2  FROM salesinvoice;

INVOICE_ID    USER_ID INVOICE_D
---------- ---------- ---------
      1001          1 12-FEB-26
      1002          2 12-FEB-26
      1004          4 12-FEB-26
      1006          6 12-FEB-26
      1008          8 12-FEB-26
      1009          9 12-FEB-26

6 rows selected.

Elapsed: 00:00:00.01

SQL>  CREATE INDEX idx_invoice_user_date
  2  ON salesinvoice(user_id, invoice_date);

Index created.

SQL> SELECT invoice_id, user_id, invoice_date
  2  FROM salesinvoice;

INVOICE_ID    USER_ID INVOICE_D
---------- ---------- ---------
      1001          1 12-FEB-26
      1002          2 12-FEB-26
      1004          4 12-FEB-26
      1006          6 12-FEB-26
      1008          8 12-FEB-26
      1009          9 12-FEB-26

6 rows selected.

Elapsed: 00:00:00.00
SQL>  SELECT owner_name, owner_email
  2  FROM owner1;

OWNER_NAME           OWNER_EMAIL
-------------------- --------------------------------------------------
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Life Owner           updated@gmail.com
City Owner           updated@gmail.com
Apollo Owner         updated@gmail.com

7 rows selected.

Elapsed: 00:00:00.00
SQL>  SELECT owner_name, owner_email
  2  FROM owner1;

OWNER_NAME           OWNER_EMAIL
-------------------- --------------------------------------------------
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Updated Owner        updated@gmail.com
Life Owner           updated@gmail.com
City Owner           updated@gmail.com
Apollo Owner         updated@gmail.com

7 rows selected.
Elapsed: 00:00:00.01


SQL> SELECT user_id, salary_month, net_salary
  2  FROM salarypayment;

   USER_ID SALARY_MON NET_SALARY
---------- ---------- ----------
         1 JAN             22000
         2 JAN             20000
         3 JAN             19000
         4 JAN             27000
         5 JAN             18000

SQL> CREATE INDEX idx_net_salary
  2  ON salarypayment(net_salary);

Index created.


SQL> SELECT invoice_id, medicine_id, quantity
  2  FROM SalesInvoiceDetail;

INVOICE_ID MEDICINE_ID   QUANTITY
---------- ----------- ----------
      1001         501          6
      1002         502          4
      1003         503          3
      1004         504          7
      1005         505          5
      1006         506          8
      1008         508          6
      1009         509          9

8 rows selected.

SQL>  CREATE INDEX idx_medicine
  2  ON SalesInvoiceDetail(medicine_id);

Index created.


SQL> SELECT report_id, report_date, total_revenue
  2  FROM salesReport;

 REPORT_ID REPORT_DA TOTAL_REVENUE
---------- --------- -------------
       101 05-MAR-26         25200
         2 12-FEB-26        150000
         3 12-FEB-26        610000
         4 12-FEB-26       7500000

Elapsed: 00:00:00.01

SQL>  SELECT report_id, report_date, total_revenue
  2  FROM salesReport;

 REPORT_ID REPORT_DA TOTAL_REVENUE
---------- --------- -------------
       101 05-MAR-26         25200
         2 12-FEB-26        150000
         3 12-FEB-26        610000
         4 12-FEB-26       7500000

Elapsed: 00:00:00.01

SQL> CREATE INDEX idx_report_date
  2  ON salesReport(report_date);

Index created.

------------------------------------------
SQL>
SQL> SELECT invoice_id,
  2         to=tal_amount,
  3         CASE
  4              WHEN total_amount > 10000 THEN 'HIGH'
  5              WHEN total_amount BETWEEN 5000 AND 10000 THEN 'MEDIUM'
  6              ELSE 'LOW'
  7         END AS invoice_category
  8  FROM salesinvoice;

INVOICE_ID TOTAL_AMOUNT INVOIC
---------- ------------ ------
      1001         5500 MEDIUM
      1002         3000 LOW
      1004         5000 MEDIUM
      1006         3300 LOW
      1008         3600 LOW
      1009         4800 LOW

6 rows selected.

Elapsed: 00:00:00.01
SQL> INSERT INTO salesReport
  2  (report_id, report_date, report_type, total_orders, total_revenue)
  3  SELECT 101,
  4         SYSDATE,
  5         CASE
  6             WHEN SUM(total_amount) > 50000 THEN 'HIGH SALES'
  7             ELSE 'NORMAL SALES'
  8         END,
  9         COUNT(*),
 10         SUM(total_amount)
 11  FROM salesinvoice;

1 row created.





SQL> DELETE FROM salesinvoice
  2  WHERE invoice_id IN
  3  (
  4  SELECT invoice_id
  5  FROM salesinvoice
  6  WHERE
  7  CASE
  8      WHEN total_amount < 4000 THEN 1
  9      ELSE 0
 10  END = 1
 11  );

3 rows deleted.

SQL> select invoice_id,total_amount from salesinvoice;

INVOICE_ID TOTAL_AMOUNT
---------- ------------
      1001         5500
       101         5000
      1004         5000
       102         8000
       104        15000
      1009         4800

6 rows selected.

SQL> SELECT invoice_id,
  2         user_id,
  3         total_amount,
  4         ROW_NUMBER() OVER (ORDER BY total_amount DESC) AS rank_no
  5  FROM salesinvoice;

INVOICE_ID    USER_ID TOTAL_AMOUNT    RANK_NO
---------- ---------- ------------ ----------
      1001          1         5500          1
      1004          4         5000          2
      1009          9         4800          3
      1008          8         3600          4
      1006          6         3300          5
      1002          2         3000          6

6 rows selected.



SQL>  SELECT *
  2  FROM salesinvoice
  3  FETCH FIRST 5 ROWS ONLY;

INVOICE_ID CUSTOMER_ID    USER_ID INVOICE_D   DISCOUNT TOTAL_AMOUNT
---------- ----------- ---------- --------- ---------- ------------
PAYMENT_STATUS
--------------------
      1001         201          1 12-FEB-26         15         5500
PAID

      1002         202          2 12-FEB-26          5         3000
PAID

      1004         204          4 12-FEB-26         20         5000
PAID


INVOICE_ID CUSTOMER_ID    USER_ID INVOICE_D   DISCOUNT TOTAL_AMOUNT
---------- ----------- ---------- --------- ---------- ------------
PAYMENT_STATUS
--------------------
      1006         206          6 12-FEB-26          8         3300
PAID

      1008         208          8 12-FEB-26          6         3600
PAID


SQL>
SQL> SELECT invoice_id, total_amount
  2  FROM salesinvoice
  3  ORDER BY total_amount DESC
  4  FETCH FIRST 3 ROWS ONLY;

INVOICE_ID TOTAL_AMOUNT
---------- ------------
      1001         5500
      1004         5000
      1009         4800

