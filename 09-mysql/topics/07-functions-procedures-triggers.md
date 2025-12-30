# MySQL Functions, Stored Procedures, and Triggers

## Introduction

MySQL provides powerful programming constructs that allow you to encapsulate business logic within the database itself. This module covers built-in scalar functions, user-defined functions, stored procedures, and triggers.

---

## Scalar Functions

Scalar functions operate on a single value and return a single value. Unlike aggregate functions that work on sets of rows, scalar functions process individual values.

### String Functions

String functions manipulate and transform text data.

#### Common String Functions

| Function | Description | Example |
|----------|-------------|---------|
| `CONCAT()` | Concatenate strings | `CONCAT('Hello', ' ', 'World')` → 'Hello World' |
| `CONCAT_WS()` | Concatenate with separator | `CONCAT_WS('-', '2024', '01', '15')` → '2024-01-15' |
| `LENGTH()` | String length in bytes | `LENGTH('Hello')` → 5 |
| `CHAR_LENGTH()` | String length in characters | `CHAR_LENGTH('Hello')` → 5 |
| `UPPER()` | Convert to uppercase | `UPPER('hello')` → 'HELLO' |
| `LOWER()` | Convert to lowercase | `LOWER('HELLO')` → 'hello' |
| `TRIM()` | Remove leading/trailing spaces | `TRIM('  hello  ')` → 'hello' |
| `LTRIM()` | Remove leading spaces | `LTRIM('  hello')` → 'hello' |
| `RTRIM()` | Remove trailing spaces | `RTRIM('hello  ')` → 'hello' |
| `SUBSTRING()` | Extract substring | `SUBSTRING('Hello', 2, 3)` → 'ell' |
| `LEFT()` | Extract left characters | `LEFT('Hello', 3)` → 'Hel' |
| `RIGHT()` | Extract right characters | `RIGHT('Hello', 3)` → 'llo' |
| `REPLACE()` | Replace occurrences | `REPLACE('Hello', 'l', 'x')` → 'Hexxo' |
| `REVERSE()` | Reverse string | `REVERSE('Hello')` → 'olleH' |
| `LOCATE()` | Find position of substring | `LOCATE('l', 'Hello')` → 3 |
| `INSTR()` | Find position (alias) | `INSTR('Hello', 'l')` → 3 |
| `LPAD()` | Left pad string | `LPAD('5', 3, '0')` → '005' |
| `RPAD()` | Right pad string | `RPAD('5', 3, '0')` → '500' |
| `REPEAT()` | Repeat string | `REPEAT('ab', 3)` → 'ababab' |
| `SPACE()` | Generate spaces | `SPACE(5)` → '     ' |

#### String Function Examples

```sql
-- Format employee names
SELECT
    CONCAT(UPPER(LEFT(first_name, 1)), LOWER(SUBSTRING(first_name, 2))) AS formatted_name,
    CHAR_LENGTH(first_name) AS name_length
FROM employees;

-- Clean and standardize data
SELECT
    TRIM(UPPER(email)) AS clean_email,
    REPLACE(phone, '-', '') AS phone_digits
FROM customers;

-- Extract domain from email
SELECT
    email,
    SUBSTRING(email, LOCATE('@', email) + 1) AS domain
FROM users;

-- Mask sensitive data
SELECT
    CONCAT(LEFT(credit_card, 4), REPEAT('*', 8), RIGHT(credit_card, 4)) AS masked_card
FROM payments;

-- Generate formatted ID
SELECT
    CONCAT('EMP-', LPAD(employee_id, 5, '0')) AS employee_code
FROM employees;
```

### Numeric Functions

Numeric functions perform mathematical operations on numeric values.

#### Common Numeric Functions

| Function | Description | Example |
|----------|-------------|---------|
| `ABS()` | Absolute value | `ABS(-5)` → 5 |
| `CEIL()` / `CEILING()` | Round up to nearest integer | `CEIL(4.2)` → 5 |
| `FLOOR()` | Round down to nearest integer | `FLOOR(4.8)` → 4 |
| `ROUND()` | Round to specified decimals | `ROUND(4.567, 2)` → 4.57 |
| `TRUNCATE()` | Truncate to specified decimals | `TRUNCATE(4.567, 2)` → 4.56 |
| `MOD()` | Modulus (remainder) | `MOD(10, 3)` → 1 |
| `POWER()` / `POW()` | Raise to power | `POWER(2, 3)` → 8 |
| `SQRT()` | Square root | `SQRT(16)` → 4 |
| `EXP()` | e raised to power | `EXP(1)` → 2.718... |
| `LOG()` | Natural logarithm | `LOG(2.718)` → 1 |
| `LOG10()` | Base-10 logarithm | `LOG10(100)` → 2 |
| `RAND()` | Random number (0-1) | `RAND()` → 0.123... |
| `SIGN()` | Sign of number | `SIGN(-5)` → -1 |
| `PI()` | Value of pi | `PI()` → 3.141592... |
| `GREATEST()` | Largest value | `GREATEST(1, 5, 3)` → 5 |
| `LEAST()` | Smallest value | `LEAST(1, 5, 3)` → 1 |

#### Numeric Function Examples

```sql
-- Calculate prices with tax
SELECT
    product_name,
    price,
    ROUND(price * 1.08, 2) AS price_with_tax
FROM products;

-- Calculate percentage
SELECT
    department,
    COUNT(*) AS emp_count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM employees), 2) AS percentage
FROM employees
GROUP BY department;

-- Generate random sample
SELECT * FROM products
ORDER BY RAND()
LIMIT 5;

-- Calculate distance between coordinates
SELECT
    store_name,
    SQRT(POWER(lat - 40.7128, 2) + POWER(lng - (-74.0060), 2)) AS distance
FROM stores
ORDER BY distance;

-- Categorize by ranges
SELECT
    product_name,
    price,
    FLOOR(price / 100) * 100 AS price_bracket
FROM products;
```

### Date and Time Functions

Date and time functions manipulate temporal data.

#### Common Date/Time Functions

| Function | Description | Example |
|----------|-------------|---------|
| `NOW()` | Current date and time | `NOW()` → '2024-01-15 10:30:00' |
| `CURDATE()` | Current date | `CURDATE()` → '2024-01-15' |
| `CURTIME()` | Current time | `CURTIME()` → '10:30:00' |
| `DATE()` | Extract date part | `DATE('2024-01-15 10:30:00')` → '2024-01-15' |
| `TIME()` | Extract time part | `TIME('2024-01-15 10:30:00')` → '10:30:00' |
| `YEAR()` | Extract year | `YEAR('2024-01-15')` → 2024 |
| `MONTH()` | Extract month | `MONTH('2024-01-15')` → 1 |
| `DAY()` | Extract day | `DAY('2024-01-15')` → 15 |
| `HOUR()` | Extract hour | `HOUR('10:30:00')` → 10 |
| `MINUTE()` | Extract minute | `MINUTE('10:30:00')` → 30 |
| `SECOND()` | Extract second | `SECOND('10:30:45')` → 45 |
| `DAYNAME()` | Day name | `DAYNAME('2024-01-15')` → 'Monday' |
| `MONTHNAME()` | Month name | `MONTHNAME('2024-01-15')` → 'January' |
| `DAYOFWEEK()` | Day of week (1=Sunday) | `DAYOFWEEK('2024-01-15')` → 2 |
| `DAYOFYEAR()` | Day of year | `DAYOFYEAR('2024-01-15')` → 15 |
| `WEEK()` | Week number | `WEEK('2024-01-15')` → 3 |
| `QUARTER()` | Quarter (1-4) | `QUARTER('2024-01-15')` → 1 |

#### Date Arithmetic Functions

| Function | Description | Example |
|----------|-------------|---------|
| `DATE_ADD()` | Add interval to date | `DATE_ADD('2024-01-15', INTERVAL 1 MONTH)` |
| `DATE_SUB()` | Subtract interval | `DATE_SUB('2024-01-15', INTERVAL 7 DAY)` |
| `DATEDIFF()` | Days between dates | `DATEDIFF('2024-01-15', '2024-01-01')` → 14 |
| `TIMEDIFF()` | Time difference | `TIMEDIFF('10:30:00', '08:00:00')` → '02:30:00' |
| `TIMESTAMPDIFF()` | Difference in units | `TIMESTAMPDIFF(MONTH, '2024-01-01', '2024-06-01')` → 5 |
| `LAST_DAY()` | Last day of month | `LAST_DAY('2024-01-15')` → '2024-01-31' |
| `ADDTIME()` | Add time | `ADDTIME('10:00:00', '02:30:00')` → '12:30:00' |

#### Date Formatting Functions

| Function | Description | Example |
|----------|-------------|---------|
| `DATE_FORMAT()` | Format date | `DATE_FORMAT('2024-01-15', '%M %d, %Y')` → 'January 15, 2024' |
| `TIME_FORMAT()` | Format time | `TIME_FORMAT('14:30:00', '%h:%i %p')` → '02:30 PM' |
| `STR_TO_DATE()` | Parse string to date | `STR_TO_DATE('15-01-2024', '%d-%m-%Y')` |

#### Common Format Specifiers

| Specifier | Description | Example |
|-----------|-------------|---------|
| `%Y` | 4-digit year | 2024 |
| `%y` | 2-digit year | 24 |
| `%M` | Month name | January |
| `%m` | Month (01-12) | 01 |
| `%D` | Day with suffix | 15th |
| `%d` | Day (01-31) | 15 |
| `%W` | Weekday name | Monday |
| `%H` | Hour (00-23) | 14 |
| `%h` | Hour (01-12) | 02 |
| `%i` | Minutes (00-59) | 30 |
| `%s` | Seconds (00-59) | 45 |
| `%p` | AM/PM | PM |

#### Date/Time Function Examples

```sql
-- Get employees hired in the last 30 days
SELECT * FROM employees
WHERE hire_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY);

-- Calculate employee tenure
SELECT
    employee_name,
    hire_date,
    TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) AS years_employed,
    TIMESTAMPDIFF(MONTH, hire_date, CURDATE()) AS months_employed
FROM employees;

-- Format dates for display
SELECT
    order_id,
    DATE_FORMAT(order_date, '%M %d, %Y') AS formatted_date,
    DATE_FORMAT(order_date, '%W') AS day_of_week
FROM orders;

-- Group sales by month
SELECT
    DATE_FORMAT(sale_date, '%Y-%m') AS month,
    SUM(amount) AS total_sales
FROM sales
GROUP BY DATE_FORMAT(sale_date, '%Y-%m');

-- Find orders due this week
SELECT * FROM orders
WHERE due_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY);

-- Calculate age from birthdate
SELECT
    name,
    birthdate,
    TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) AS age
FROM customers;
```

### Conditional Functions

Conditional functions return values based on conditions.

| Function | Description | Example |
|----------|-------------|---------|
| `IF()` | Simple if-else | `IF(score >= 60, 'Pass', 'Fail')` |
| `IFNULL()` | Replace NULL | `IFNULL(phone, 'N/A')` |
| `NULLIF()` | Return NULL if equal | `NULLIF(value, 0)` |
| `COALESCE()` | First non-NULL value | `COALESCE(phone, mobile, 'N/A')` |
| `CASE` | Multiple conditions | See examples below |

#### Conditional Function Examples

```sql
-- Simple IF
SELECT
    product_name,
    quantity,
    IF(quantity > 0, 'In Stock', 'Out of Stock') AS availability
FROM products;

-- Handle NULL values
SELECT
    customer_name,
    IFNULL(phone, 'Not provided') AS contact_phone,
    COALESCE(mobile, home_phone, work_phone, 'No phone') AS best_phone
FROM customers;

-- CASE expression
SELECT
    employee_name,
    salary,
    CASE
        WHEN salary < 30000 THEN 'Entry Level'
        WHEN salary < 50000 THEN 'Mid Level'
        WHEN salary < 80000 THEN 'Senior Level'
        ELSE 'Executive'
    END AS salary_grade
FROM employees;

-- CASE with specific values
SELECT
    order_id,
    status,
    CASE status
        WHEN 'P' THEN 'Pending'
        WHEN 'S' THEN 'Shipped'
        WHEN 'D' THEN 'Delivered'
        WHEN 'C' THEN 'Cancelled'
        ELSE 'Unknown'
    END AS status_description
FROM orders;
```

### Conversion Functions

| Function | Description | Example |
|----------|-------------|---------|
| `CAST()` | Convert data type | `CAST('123' AS SIGNED)` |
| `CONVERT()` | Convert data type | `CONVERT('123', SIGNED)` |
| `FORMAT()` | Format number with commas | `FORMAT(1234567.89, 2)` → '1,234,567.89' |

```sql
-- Convert string to number
SELECT CAST('123.45' AS DECIMAL(10,2));

-- Format currency
SELECT
    product_name,
    CONCAT('$', FORMAT(price, 2)) AS formatted_price
FROM products;

-- Convert to different character set
SELECT CONVERT(name USING utf8mb4) FROM customers;
```

---

## Stored Procedures

A stored procedure is a prepared SQL code that you can save and reuse. Stored procedures allow you to encapsulate complex business logic, improve performance, and enhance security.

### Benefits of Stored Procedures

1. **Reusability**: Write once, call multiple times
2. **Security**: Grant execute permission without exposing underlying tables
3. **Performance**: Compiled and cached by the server
4. **Maintainability**: Centralized business logic
5. **Reduced Network Traffic**: Single call instead of multiple statements

### Creating Stored Procedures

#### Basic Syntax

```sql
DELIMITER //

CREATE PROCEDURE procedure_name (
    [IN | OUT | INOUT] parameter_name datatype,
    ...
)
BEGIN
    -- SQL statements
END //

DELIMITER ;
```

#### Parameter Types

| Type | Description |
|------|-------------|
| `IN` | Input parameter (default) - value passed to procedure |
| `OUT` | Output parameter - value returned from procedure |
| `INOUT` | Both input and output |

### Simple Stored Procedure Examples

#### Procedure Without Parameters

```sql
DELIMITER //

CREATE PROCEDURE GetAllEmployees()
BEGIN
    SELECT employee_id, first_name, last_name, department, salary
    FROM employees
    ORDER BY last_name;
END //

DELIMITER ;

-- Call the procedure
CALL GetAllEmployees();
```

#### Procedure With IN Parameters

```sql
DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(
    IN dept_name VARCHAR(50)
)
BEGIN
    SELECT employee_id, first_name, last_name, salary
    FROM employees
    WHERE department = dept_name
    ORDER BY salary DESC;
END //

DELIMITER ;

-- Call with parameter
CALL GetEmployeesByDepartment('Engineering');
CALL GetEmployeesByDepartment('Sales');
```

#### Procedure With OUT Parameters

```sql
DELIMITER //

CREATE PROCEDURE GetEmployeeCount(
    IN dept_name VARCHAR(50),
    OUT emp_count INT
)
BEGIN
    SELECT COUNT(*) INTO emp_count
    FROM employees
    WHERE department = dept_name;
END //

DELIMITER ;

-- Call and retrieve output
CALL GetEmployeeCount('Engineering', @count);
SELECT @count AS employee_count;
```

#### Procedure With INOUT Parameters

```sql
DELIMITER //

CREATE PROCEDURE ApplyDiscount(
    INOUT price DECIMAL(10,2),
    IN discount_percent INT
)
BEGIN
    SET price = price - (price * discount_percent / 100);
END //

DELIMITER ;

-- Use INOUT parameter
SET @product_price = 100.00;
CALL ApplyDiscount(@product_price, 15);
SELECT @product_price AS discounted_price;  -- Returns 85.00
```

### Variables in Stored Procedures

```sql
DELIMITER //

CREATE PROCEDURE CalculateBonus(
    IN emp_id INT,
    OUT bonus DECIMAL(10,2)
)
BEGIN
    -- Declare local variables
    DECLARE emp_salary DECIMAL(10,2);
    DECLARE emp_years INT;
    DECLARE bonus_rate DECIMAL(5,2);

    -- Get employee data
    SELECT salary, TIMESTAMPDIFF(YEAR, hire_date, CURDATE())
    INTO emp_salary, emp_years
    FROM employees
    WHERE employee_id = emp_id;

    -- Calculate bonus rate based on tenure
    IF emp_years >= 10 THEN
        SET bonus_rate = 0.15;
    ELSEIF emp_years >= 5 THEN
        SET bonus_rate = 0.10;
    ELSE
        SET bonus_rate = 0.05;
    END IF;

    -- Calculate bonus
    SET bonus = emp_salary * bonus_rate;
END //

DELIMITER ;
```

### Control Flow Statements

#### IF-THEN-ELSE

```sql
DELIMITER //

CREATE PROCEDURE CheckInventory(
    IN product_id INT,
    OUT status_message VARCHAR(100)
)
BEGIN
    DECLARE qty INT;

    SELECT quantity INTO qty
    FROM products
    WHERE id = product_id;

    IF qty IS NULL THEN
        SET status_message = 'Product not found';
    ELSEIF qty = 0 THEN
        SET status_message = 'Out of stock';
    ELSEIF qty < 10 THEN
        SET status_message = 'Low stock - reorder needed';
    ELSE
        SET status_message = 'In stock';
    END IF;
END //

DELIMITER ;
```

#### CASE Statement

```sql
DELIMITER //

CREATE PROCEDURE GetShippingCost(
    IN order_total DECIMAL(10,2),
    IN shipping_method VARCHAR(20),
    OUT shipping_cost DECIMAL(10,2)
)
BEGIN
    CASE shipping_method
        WHEN 'standard' THEN
            SET shipping_cost = CASE
                WHEN order_total >= 100 THEN 0.00
                ELSE 5.99
            END;
        WHEN 'express' THEN
            SET shipping_cost = 12.99;
        WHEN 'overnight' THEN
            SET shipping_cost = 24.99;
        ELSE
            SET shipping_cost = 5.99;
    END CASE;
END //

DELIMITER ;
```

#### LOOP, WHILE, and REPEAT

```sql
-- WHILE loop
DELIMITER //

CREATE PROCEDURE GenerateSequence(
    IN max_num INT
)
BEGIN
    DECLARE counter INT DEFAULT 1;

    -- Create temporary table for results
    DROP TEMPORARY TABLE IF EXISTS sequence_numbers;
    CREATE TEMPORARY TABLE sequence_numbers (num INT);

    WHILE counter <= max_num DO
        INSERT INTO sequence_numbers VALUES (counter);
        SET counter = counter + 1;
    END WHILE;

    SELECT * FROM sequence_numbers;
END //

DELIMITER ;

-- REPEAT loop (executes at least once)
DELIMITER //

CREATE PROCEDURE ProcessBatch()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE batch_count INT DEFAULT 0;

    REPEAT
        -- Process batch logic here
        SET batch_count = batch_count + 1;

        -- Check if more batches to process
        SELECT COUNT(*) INTO done FROM pending_items WHERE processed = 0;

    UNTIL done = 0 OR batch_count >= 10
    END REPEAT;

    SELECT batch_count AS batches_processed;
END //

DELIMITER ;

-- LOOP with LEAVE (explicit exit)
DELIMITER //

CREATE PROCEDURE FindFirstMatch(
    IN search_term VARCHAR(100),
    OUT found_id INT
)
BEGIN
    DECLARE current_id INT DEFAULT 0;
    DECLARE max_id INT;

    SELECT MAX(id) INTO max_id FROM products;
    SET found_id = NULL;

    search_loop: LOOP
        SET current_id = current_id + 1;

        -- Exit if we've checked all records
        IF current_id > max_id THEN
            LEAVE search_loop;
        END IF;

        -- Check for match
        IF EXISTS (
            SELECT 1 FROM products
            WHERE id = current_id
            AND name LIKE CONCAT('%', search_term, '%')
        ) THEN
            SET found_id = current_id;
            LEAVE search_loop;
        END IF;
    END LOOP;
END //

DELIMITER ;
```

### Cursors

Cursors allow row-by-row processing of query results.

```sql
DELIMITER //

CREATE PROCEDURE ProcessAllOrders()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE order_id INT;
    DECLARE order_total DECIMAL(10,2);
    DECLARE processed_count INT DEFAULT 0;

    -- Declare cursor
    DECLARE order_cursor CURSOR FOR
        SELECT id, total FROM orders WHERE status = 'pending';

    -- Declare handler for end of data
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Open cursor
    OPEN order_cursor;

    -- Loop through results
    read_loop: LOOP
        FETCH order_cursor INTO order_id, order_total;

        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Process each order
        UPDATE orders SET status = 'processing' WHERE id = order_id;

        -- Apply loyalty points
        IF order_total >= 100 THEN
            INSERT INTO loyalty_points (order_id, points)
            VALUES (order_id, FLOOR(order_total / 10));
        END IF;

        SET processed_count = processed_count + 1;
    END LOOP;

    -- Close cursor
    CLOSE order_cursor;

    SELECT processed_count AS orders_processed;
END //

DELIMITER ;
```

### Error Handling

```sql
DELIMITER //

CREATE PROCEDURE TransferFunds(
    IN from_account INT,
    IN to_account INT,
    IN amount DECIMAL(10,2),
    OUT result VARCHAR(100)
)
BEGIN
    DECLARE from_balance DECIMAL(10,2);

    -- Declare error handler
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET result = 'Transfer failed - database error';
    END;

    -- Start transaction
    START TRANSACTION;

    -- Check source balance
    SELECT balance INTO from_balance
    FROM accounts
    WHERE account_id = from_account
    FOR UPDATE;

    IF from_balance IS NULL THEN
        SET result = 'Source account not found';
        ROLLBACK;
    ELSEIF from_balance < amount THEN
        SET result = 'Insufficient funds';
        ROLLBACK;
    ELSE
        -- Perform transfer
        UPDATE accounts SET balance = balance - amount WHERE account_id = from_account;
        UPDATE accounts SET balance = balance + amount WHERE account_id = to_account;

        -- Log transaction
        INSERT INTO transactions (from_acc, to_acc, amount, trans_date)
        VALUES (from_account, to_account, amount, NOW());

        COMMIT;
        SET result = 'Transfer successful';
    END IF;
END //

DELIMITER ;
```

### Managing Stored Procedures

```sql
-- View all procedures in current database
SHOW PROCEDURE STATUS WHERE Db = DATABASE();

-- View procedure definition
SHOW CREATE PROCEDURE procedure_name;

-- Drop procedure
DROP PROCEDURE IF EXISTS procedure_name;

-- Alter procedure (must drop and recreate)
DROP PROCEDURE IF EXISTS GetAllEmployees;
-- Then CREATE PROCEDURE again with changes
```

---

## User-Defined Functions (UDFs)

User-defined functions are similar to stored procedures but return a single value and can be used in SQL expressions.

### Functions vs Procedures

| Feature | Function | Procedure |
|---------|----------|-----------|
| Return value | Must return a value | Optional (via OUT params) |
| Use in SELECT | Yes | No |
| Use in WHERE | Yes | No |
| Transaction control | No (no COMMIT/ROLLBACK) | Yes |
| Output parameters | No | Yes |
| Call syntax | `SELECT func()` | `CALL proc()` |

### Creating Functions

#### Basic Syntax

```sql
DELIMITER //

CREATE FUNCTION function_name (
    parameter_name datatype,
    ...
)
RETURNS datatype
[DETERMINISTIC | NOT DETERMINISTIC]
[CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA]
BEGIN
    -- SQL statements
    RETURN value;
END //

DELIMITER ;
```

#### Function Characteristics

| Characteristic | Description |
|----------------|-------------|
| `DETERMINISTIC` | Always returns same result for same input |
| `NOT DETERMINISTIC` | May return different results (default) |
| `CONTAINS SQL` | Contains SQL but doesn't read/write data |
| `NO SQL` | Contains no SQL statements |
| `READS SQL DATA` | Reads but doesn't modify data |
| `MODIFIES SQL DATA` | May modify data |

### Function Examples

#### Simple Calculation Function

```sql
DELIMITER //

CREATE FUNCTION CalculateTax(
    amount DECIMAL(10,2),
    tax_rate DECIMAL(5,2)
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN ROUND(amount * tax_rate / 100, 2);
END //

DELIMITER ;

-- Use in queries
SELECT
    product_name,
    price,
    CalculateTax(price, 8.25) AS tax,
    price + CalculateTax(price, 8.25) AS total
FROM products;
```

#### String Formatting Function

```sql
DELIMITER //

CREATE FUNCTION FormatPhoneNumber(
    phone VARCHAR(20)
)
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    DECLARE clean_phone VARCHAR(20);

    -- Remove non-numeric characters
    SET clean_phone = REGEXP_REPLACE(phone, '[^0-9]', '');

    -- Format as (XXX) XXX-XXXX if 10 digits
    IF LENGTH(clean_phone) = 10 THEN
        RETURN CONCAT(
            '(', LEFT(clean_phone, 3), ') ',
            SUBSTRING(clean_phone, 4, 3), '-',
            RIGHT(clean_phone, 4)
        );
    ELSE
        RETURN phone;  -- Return original if not 10 digits
    END IF;
END //

DELIMITER ;

-- Use in queries
SELECT
    customer_name,
    FormatPhoneNumber(phone) AS formatted_phone
FROM customers;
```

#### Data Lookup Function

```sql
DELIMITER //

CREATE FUNCTION GetDepartmentName(
    dept_id INT
)
RETURNS VARCHAR(100)
READS SQL DATA
BEGIN
    DECLARE dept_name VARCHAR(100);

    SELECT name INTO dept_name
    FROM departments
    WHERE department_id = dept_id;

    RETURN IFNULL(dept_name, 'Unknown');
END //

DELIMITER ;

-- Use in queries
SELECT
    employee_name,
    GetDepartmentName(department_id) AS department
FROM employees;
```

#### Age Calculation Function

```sql
DELIMITER //

CREATE FUNCTION CalculateAge(
    birthdate DATE
)
RETURNS INT
DETERMINISTIC
BEGIN
    IF birthdate IS NULL THEN
        RETURN NULL;
    END IF;

    RETURN TIMESTAMPDIFF(YEAR, birthdate, CURDATE());
END //

DELIMITER ;

-- Use in queries
SELECT
    name,
    birthdate,
    CalculateAge(birthdate) AS age
FROM customers
WHERE CalculateAge(birthdate) >= 18;
```

#### Grade Calculation Function

```sql
DELIMITER //

CREATE FUNCTION GetLetterGrade(
    score DECIMAL(5,2)
)
RETURNS CHAR(2)
DETERMINISTIC
BEGIN
    IF score >= 90 THEN
        RETURN 'A';
    ELSEIF score >= 80 THEN
        RETURN 'B';
    ELSEIF score >= 70 THEN
        RETURN 'C';
    ELSEIF score >= 60 THEN
        RETURN 'D';
    ELSE
        RETURN 'F';
    END IF;
END //

DELIMITER ;

-- Use in queries
SELECT
    student_name,
    exam_score,
    GetLetterGrade(exam_score) AS grade
FROM students;
```

### Managing Functions

```sql
-- View all functions
SHOW FUNCTION STATUS WHERE Db = DATABASE();

-- View function definition
SHOW CREATE FUNCTION function_name;

-- Drop function
DROP FUNCTION IF EXISTS function_name;
```

---

## Triggers

A trigger is a database object that automatically executes (fires) when a specified event occurs on a table. Triggers are useful for enforcing business rules, auditing changes, and maintaining data integrity.

### Trigger Events and Timing

| Timing | Event | Description |
|--------|-------|-------------|
| `BEFORE` | `INSERT` | Before a new row is inserted |
| `BEFORE` | `UPDATE` | Before an existing row is modified |
| `BEFORE` | `DELETE` | Before a row is deleted |
| `AFTER` | `INSERT` | After a new row is inserted |
| `AFTER` | `UPDATE` | After an existing row is modified |
| `AFTER` | `DELETE` | After a row is deleted |

### Special Keywords

| Keyword | Description | Available In |
|---------|-------------|--------------|
| `NEW` | New row values being inserted/updated | INSERT, UPDATE |
| `OLD` | Existing row values before update/delete | UPDATE, DELETE |

### Creating Triggers

#### Basic Syntax

```sql
DELIMITER //

CREATE TRIGGER trigger_name
{BEFORE | AFTER} {INSERT | UPDATE | DELETE}
ON table_name
FOR EACH ROW
BEGIN
    -- Trigger logic
END //

DELIMITER ;
```

### Trigger Examples

#### Audit Trail Trigger

```sql
-- Create audit table
CREATE TABLE employee_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    action VARCHAR(10),
    old_salary DECIMAL(10,2),
    new_salary DECIMAL(10,2),
    changed_by VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger for INSERT
DELIMITER //

CREATE TRIGGER employee_after_insert
AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, action, new_salary, changed_by)
    VALUES (NEW.employee_id, 'INSERT', NEW.salary, USER());
END //

DELIMITER ;

-- Trigger for UPDATE
DELIMITER //

CREATE TRIGGER employee_after_update
AFTER UPDATE ON employees
FOR EACH ROW
BEGIN
    IF OLD.salary != NEW.salary THEN
        INSERT INTO employee_audit (employee_id, action, old_salary, new_salary, changed_by)
        VALUES (NEW.employee_id, 'UPDATE', OLD.salary, NEW.salary, USER());
    END IF;
END //

DELIMITER ;

-- Trigger for DELETE
DELIMITER //

CREATE TRIGGER employee_after_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, action, old_salary, changed_by)
    VALUES (OLD.employee_id, 'DELETE', OLD.salary, USER());
END //

DELIMITER ;
```

#### Data Validation Trigger

```sql
DELIMITER //

CREATE TRIGGER validate_employee_before_insert
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
    -- Validate email format
    IF NEW.email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid email format';
    END IF;

    -- Validate salary range
    IF NEW.salary < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary cannot be negative';
    END IF;

    -- Validate hire date
    IF NEW.hire_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Hire date cannot be in the future';
    END IF;

    -- Standardize email to lowercase
    SET NEW.email = LOWER(TRIM(NEW.email));
END //

DELIMITER ;
```

#### Auto-Update Timestamp Trigger

```sql
-- Add updated_at column
ALTER TABLE products ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Create trigger
DELIMITER //

CREATE TRIGGER products_update_timestamp
BEFORE UPDATE ON products
FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //

DELIMITER ;
```

#### Inventory Management Trigger

```sql
-- When order is placed, reduce inventory
DELIMITER //

CREATE TRIGGER reduce_inventory_after_order
AFTER INSERT ON order_items
FOR EACH ROW
BEGIN
    UPDATE products
    SET quantity = quantity - NEW.quantity
    WHERE product_id = NEW.product_id;

    -- Check for low stock alert
    IF (SELECT quantity FROM products WHERE product_id = NEW.product_id) < 10 THEN
        INSERT INTO stock_alerts (product_id, alert_type, alert_date)
        VALUES (NEW.product_id, 'LOW_STOCK', NOW());
    END IF;
END //

DELIMITER ;

-- When order is cancelled, restore inventory
DELIMITER //

CREATE TRIGGER restore_inventory_after_cancel
AFTER UPDATE ON orders
FOR EACH ROW
BEGIN
    IF OLD.status != 'cancelled' AND NEW.status = 'cancelled' THEN
        UPDATE products p
        INNER JOIN order_items oi ON p.product_id = oi.product_id
        SET p.quantity = p.quantity + oi.quantity
        WHERE oi.order_id = NEW.order_id;
    END IF;
END //

DELIMITER ;
```

#### Cascading Updates Trigger

```sql
DELIMITER //

CREATE TRIGGER update_order_total
AFTER INSERT ON order_items
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total = (
        SELECT SUM(quantity * unit_price)
        FROM order_items
        WHERE order_id = NEW.order_id
    )
    WHERE order_id = NEW.order_id;
END //

DELIMITER ;
```

#### Prevent Certain Operations

```sql
-- Prevent deletion of admin users
DELIMITER //

CREATE TRIGGER prevent_admin_delete
BEFORE DELETE ON users
FOR EACH ROW
BEGIN
    IF OLD.role = 'admin' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot delete admin users';
    END IF;
END //

DELIMITER ;

-- Prevent salary reduction greater than 10%
DELIMITER //

CREATE TRIGGER prevent_large_salary_cut
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    IF NEW.salary < OLD.salary * 0.9 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary reduction cannot exceed 10%';
    END IF;
END //

DELIMITER ;
```

### Managing Triggers

```sql
-- View all triggers
SHOW TRIGGERS;

-- View triggers for specific table
SHOW TRIGGERS LIKE 'employees';

-- View trigger in specific database
SELECT * FROM information_schema.TRIGGERS
WHERE TRIGGER_SCHEMA = 'your_database';

-- Drop trigger
DROP TRIGGER IF EXISTS trigger_name;
```

### Trigger Best Practices

1. **Keep triggers simple**: Complex logic should be in stored procedures
2. **Avoid recursive triggers**: Be careful with triggers that modify the same table
3. **Document triggers**: They're "hidden" logic that can surprise developers
4. **Test thoroughly**: Triggers affect every INSERT/UPDATE/DELETE
5. **Consider performance**: Triggers add overhead to every affected operation
6. **Use SIGNAL for errors**: Proper error messages help debugging

---

## Comparing Procedures, Functions, and Triggers

| Feature | Stored Procedure | Function | Trigger |
|---------|-----------------|----------|---------|
| Invocation | Explicit (CALL) | In expressions | Automatic |
| Return value | Optional (OUT) | Required | None |
| Use in SELECT | No | Yes | No |
| Parameters | IN/OUT/INOUT | IN only | N/A |
| Transactions | Yes | Limited | Within parent |
| Multiple statements | Yes | Yes | Yes |

### When to Use What

**Use Stored Procedures when:**
- Performing multiple operations
- Need transactions
- Complex business logic
- Need OUT parameters

**Use Functions when:**
- Need to return a single value
- Want to use result in SELECT/WHERE
- Simple calculations or lookups

**Use Triggers when:**
- Need automatic execution
- Audit/logging requirements
- Enforcing business rules
- Maintaining derived data

---

## Summary

| Topic | Key Points |
|-------|------------|
| **Scalar Functions** | Operate on single values; includes string, numeric, date/time, and conditional functions |
| **Stored Procedures** | Reusable SQL code blocks; support IN/OUT/INOUT parameters; can use control flow |
| **User-Defined Functions** | Return single value; usable in SQL expressions; DETERMINISTIC for optimization |
| **Triggers** | Automatic execution on INSERT/UPDATE/DELETE; use NEW/OLD for row values |

### Next Steps

- Practice creating functions for common calculations in your application
- Build stored procedures for complex business operations
- Implement audit triggers for sensitive tables
- Explore advanced cursor techniques for batch processing
