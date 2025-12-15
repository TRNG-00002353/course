# MySQL Key Concepts for Application Developers

## Overview

This document outlines the essential MySQL and SQL concepts every application developer must understand. Database knowledge is critical for building data-driven applications, storing information persistently, and performing efficient data operations.

---

## 1. Database Fundamentals

### Why It Matters
- Persistent data storage
- Data integrity and consistency
- Foundation for all applications

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Database | Organized collection of data | `ecommerce_db` |
| Table | Structure to store data | `users`, `orders` |
| Row | Single record | One user |
| Column | Data field | `name`, `email` |
| Primary Key | Unique row identifier | `id` |
| Foreign Key | Reference to another table | `user_id` |

### RDBMS vs NoSQL

| Aspect | RDBMS (MySQL) | NoSQL |
|--------|---------------|-------|
| Structure | Tables with schema | Flexible (documents, key-value) |
| Relationships | Strong (joins) | Weak/embedded |
| Scaling | Vertical | Horizontal |
| Consistency | ACID | Eventually consistent |
| Use case | Structured data | Unstructured/big data |

### SQL Categories

| Category | Commands | Purpose |
|----------|----------|---------|
| DDL | CREATE, ALTER, DROP, TRUNCATE | Define structure |
| DML | INSERT, UPDATE, DELETE | Modify data |
| DQL | SELECT | Query data |
| DCL | GRANT, REVOKE | Security |
| TCL | COMMIT, ROLLBACK, SAVEPOINT | Transactions |

---

## 2. Data Types

### Why It Matters
- Proper data storage
- Validation at database level
- Storage efficiency

### Numeric Types

| Type | Range | Use Case |
|------|-------|----------|
| `TINYINT` | -128 to 127 | Small numbers, flags |
| `SMALLINT` | -32,768 to 32,767 | Small counts |
| `INT` | -2.1B to 2.1B | Most integers |
| `BIGINT` | -9.2×10¹⁸ to 9.2×10¹⁸ | Large numbers |
| `DECIMAL(p,s)` | Exact precision | Money |
| `FLOAT` | Approximate | Scientific |
| `DOUBLE` | Approximate, larger | Scientific |

### String Types

| Type | Max Size | Use Case |
|------|----------|----------|
| `CHAR(n)` | 255 | Fixed-length (e.g., codes) |
| `VARCHAR(n)` | 65,535 | Variable-length text |
| `TEXT` | 65,535 | Long text |
| `MEDIUMTEXT` | 16M | Larger text |
| `LONGTEXT` | 4GB | Very large text |
| `ENUM` | 65,535 values | Limited choices |

### Date/Time Types

| Type | Format | Example |
|------|--------|---------|
| `DATE` | YYYY-MM-DD | '2024-01-15' |
| `TIME` | HH:MM:SS | '14:30:00' |
| `DATETIME` | YYYY-MM-DD HH:MM:SS | '2024-01-15 14:30:00' |
| `TIMESTAMP` | YYYY-MM-DD HH:MM:SS | Auto-update capable |
| `YEAR` | YYYY | '2024' |

---

## 3. DDL - Data Definition Language

### Why It Matters
- Define database structure
- Create and modify tables
- Establish relationships

### Database Operations
```sql
-- Create database
CREATE DATABASE ecommerce;

-- Use database
USE ecommerce;

-- Show databases
SHOW DATABASES;

-- Drop database
DROP DATABASE ecommerce;
```

### Table Operations
```sql
-- Create table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Create table with foreign key
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('pending', 'processing', 'shipped', 'delivered') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Show tables
SHOW TABLES;

-- Describe table structure
DESCRIBE users;

-- Drop table
DROP TABLE IF EXISTS orders;

-- Truncate (delete all data, keep structure)
TRUNCATE TABLE users;
```

### Alter Table
```sql
-- Add column
ALTER TABLE users ADD COLUMN phone VARCHAR(20);

-- Modify column
ALTER TABLE users MODIFY COLUMN phone VARCHAR(25);

-- Rename column
ALTER TABLE users RENAME COLUMN phone TO phone_number;

-- Drop column
ALTER TABLE users DROP COLUMN phone_number;

-- Add constraint
ALTER TABLE users ADD CONSTRAINT email_check CHECK (email LIKE '%@%');

-- Add foreign key
ALTER TABLE orders ADD FOREIGN KEY (user_id) REFERENCES users(id);

-- Drop foreign key
ALTER TABLE orders DROP FOREIGN KEY orders_ibfk_1;

-- Rename table
ALTER TABLE users RENAME TO customers;
```

---

## 4. Constraints

### Why It Matters
- Enforce data integrity
- Prevent invalid data
- Define relationships

### Key Constraints

| Constraint | Purpose | Example |
|------------|---------|---------|
| `PRIMARY KEY` | Unique identifier | `id INT PRIMARY KEY` |
| `FOREIGN KEY` | Reference to other table | `FOREIGN KEY (user_id) REFERENCES users(id)` |
| `UNIQUE` | No duplicates | `email VARCHAR(100) UNIQUE` |
| `NOT NULL` | Required field | `name VARCHAR(50) NOT NULL` |
| `CHECK` | Validate values | `CHECK (age >= 18)` |
| `DEFAULT` | Default value | `status VARCHAR(20) DEFAULT 'active'` |
| `AUTO_INCREMENT` | Auto-generate value | `id INT AUTO_INCREMENT` |

### Constraint Examples
```sql
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(50) UNIQUE NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    quantity INT DEFAULT 0 CHECK (quantity >= 0),
    category_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Composite primary key
CREATE TABLE order_items (
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### CASCADE Options

| Option | On Delete | On Update |
|--------|-----------|-----------|
| `CASCADE` | Delete related rows | Update related values |
| `SET NULL` | Set FK to NULL | Set FK to NULL |
| `SET DEFAULT` | Set to default value | Set to default value |
| `RESTRICT` | Prevent if related exist | Prevent update |
| `NO ACTION` | Same as RESTRICT | Same as RESTRICT |

---

## 5. DML - Data Manipulation Language

### Why It Matters
- Add, modify, delete data
- Core database operations
- Application data management

### INSERT
```sql
-- Single row
INSERT INTO users (username, email, password_hash)
VALUES ('john_doe', 'john@example.com', 'hash123');

-- Multiple rows
INSERT INTO users (username, email, password_hash)
VALUES
    ('jane_doe', 'jane@example.com', 'hash456'),
    ('bob_smith', 'bob@example.com', 'hash789');

-- Insert from select
INSERT INTO archived_users (id, username, email)
SELECT id, username, email FROM users WHERE is_active = FALSE;

-- Insert with default values
INSERT INTO users (username, email, password_hash)
VALUES ('new_user', 'new@example.com', 'hash000');
-- Other columns get their default values
```

### UPDATE
```sql
-- Update single row
UPDATE users
SET email = 'newemail@example.com'
WHERE id = 1;

-- Update multiple columns
UPDATE users
SET email = 'updated@example.com',
    username = 'updated_user',
    updated_at = NOW()
WHERE id = 1;

-- Update multiple rows
UPDATE products
SET price = price * 1.1
WHERE category_id = 5;

-- Update with join
UPDATE orders o
JOIN users u ON o.user_id = u.id
SET o.status = 'cancelled'
WHERE u.is_active = FALSE;

-- ⚠️ Always use WHERE clause to avoid updating all rows!
```

### DELETE
```sql
-- Delete single row
DELETE FROM users WHERE id = 1;

-- Delete multiple rows
DELETE FROM users WHERE is_active = FALSE;

-- Delete with join
DELETE o FROM orders o
JOIN users u ON o.user_id = u.id
WHERE u.username = 'deleted_user';

-- Delete all rows (use TRUNCATE instead for better performance)
DELETE FROM logs;

-- ⚠️ Always use WHERE clause to avoid deleting all rows!
```

---

## 6. DQL - SELECT Queries

### Why It Matters
- Retrieve data from database
- Most common SQL operation
- Foundation for reporting

### Basic SELECT
```sql
-- Select all columns
SELECT * FROM users;

-- Select specific columns
SELECT username, email FROM users;

-- Aliases
SELECT username AS name, email AS contact FROM users;

-- Calculated columns
SELECT
    name,
    price,
    quantity,
    price * quantity AS total_value
FROM products;

-- Distinct values
SELECT DISTINCT category_id FROM products;
```

### WHERE Clause
```sql
-- Comparison operators
SELECT * FROM products WHERE price > 100;
SELECT * FROM products WHERE price >= 100;
SELECT * FROM products WHERE price < 50;
SELECT * FROM products WHERE price <> 100;  -- Not equal
SELECT * FROM products WHERE price != 100;  -- Not equal

-- Logical operators
SELECT * FROM products WHERE price > 100 AND quantity > 0;
SELECT * FROM products WHERE category_id = 1 OR category_id = 2;
SELECT * FROM products WHERE NOT is_deleted;

-- Range
SELECT * FROM products WHERE price BETWEEN 50 AND 100;

-- List
SELECT * FROM products WHERE category_id IN (1, 2, 3);
SELECT * FROM products WHERE category_id NOT IN (4, 5);

-- Pattern matching
SELECT * FROM users WHERE email LIKE '%@gmail.com';
SELECT * FROM users WHERE username LIKE 'john%';
SELECT * FROM users WHERE username LIKE '%doe';
SELECT * FROM users WHERE username LIKE '%_admin%';

-- NULL checking
SELECT * FROM products WHERE description IS NULL;
SELECT * FROM products WHERE description IS NOT NULL;
```

### ORDER BY
```sql
-- Ascending (default)
SELECT * FROM products ORDER BY price;
SELECT * FROM products ORDER BY price ASC;

-- Descending
SELECT * FROM products ORDER BY price DESC;

-- Multiple columns
SELECT * FROM products ORDER BY category_id ASC, price DESC;

-- By alias or position
SELECT name, price, price * quantity AS total
FROM products
ORDER BY total DESC;

SELECT name, price FROM products ORDER BY 2 DESC; -- Order by second column
```

### LIMIT and OFFSET
```sql
-- First 10 rows
SELECT * FROM products LIMIT 10;

-- Skip first 20, get next 10 (pagination)
SELECT * FROM products LIMIT 10 OFFSET 20;
-- Or shorthand:
SELECT * FROM products LIMIT 20, 10;

-- Get top N
SELECT * FROM products ORDER BY price DESC LIMIT 5;
```

---

## 7. Aggregate Functions and GROUP BY

### Why It Matters
- Summarize data
- Statistical analysis
- Reporting and dashboards

### Aggregate Functions

| Function | Purpose | Example |
|----------|---------|---------|
| `COUNT()` | Count rows | `COUNT(*)` |
| `SUM()` | Add values | `SUM(price)` |
| `AVG()` | Average | `AVG(price)` |
| `MIN()` | Minimum | `MIN(price)` |
| `MAX()` | Maximum | `MAX(price)` |

```sql
-- Basic aggregates
SELECT
    COUNT(*) AS total_products,
    SUM(quantity) AS total_inventory,
    AVG(price) AS average_price,
    MIN(price) AS cheapest,
    MAX(price) AS most_expensive
FROM products;

-- Count non-null values
SELECT COUNT(description) FROM products;

-- Count distinct values
SELECT COUNT(DISTINCT category_id) FROM products;
```

### GROUP BY
```sql
-- Group by single column
SELECT category_id, COUNT(*) AS product_count
FROM products
GROUP BY category_id;

-- Group by multiple columns
SELECT category_id, status, COUNT(*) AS count
FROM products
GROUP BY category_id, status;

-- Aggregate with group
SELECT
    category_id,
    COUNT(*) AS products,
    SUM(quantity) AS total_stock,
    AVG(price) AS avg_price
FROM products
GROUP BY category_id;
```

### HAVING Clause
```sql
-- Filter grouped results
SELECT category_id, COUNT(*) AS product_count
FROM products
GROUP BY category_id
HAVING COUNT(*) > 10;

-- WHERE vs HAVING:
-- WHERE: filters before grouping
-- HAVING: filters after grouping

SELECT category_id, AVG(price) AS avg_price
FROM products
WHERE is_active = TRUE           -- Filter rows first
GROUP BY category_id
HAVING AVG(price) > 50;          -- Filter groups after
```

---

## 8. JOINs

### Why It Matters
- Combine data from multiple tables
- Establish relationships
- Complex queries across tables

### JOIN Types

| Type | Returns |
|------|---------|
| INNER JOIN | Only matching rows |
| LEFT JOIN | All from left + matching from right |
| RIGHT JOIN | All from right + matching from left |
| FULL OUTER JOIN | All rows from both (not in MySQL directly) |
| CROSS JOIN | Cartesian product |
| SELF JOIN | Table joined to itself |

### INNER JOIN
```sql
-- Only matching rows from both tables
SELECT
    o.id AS order_id,
    u.username,
    o.total_amount,
    o.created_at
FROM orders o
INNER JOIN users u ON o.user_id = u.id;

-- Multiple joins
SELECT
    o.id AS order_id,
    u.username,
    p.name AS product_name,
    oi.quantity,
    oi.price
FROM orders o
JOIN users u ON o.user_id = u.id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id;
```

### LEFT JOIN
```sql
-- All users, even without orders
SELECT
    u.username,
    COUNT(o.id) AS order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id, u.username;

-- Find users with no orders
SELECT u.*
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE o.id IS NULL;
```

### RIGHT JOIN
```sql
-- All orders, even if user is deleted
SELECT
    o.id,
    o.total_amount,
    u.username
FROM users u
RIGHT JOIN orders o ON u.id = o.user_id;
```

### SELF JOIN
```sql
-- Employees with their managers
SELECT
    e.name AS employee,
    m.name AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

### CROSS JOIN
```sql
-- Cartesian product (all combinations)
SELECT
    colors.name AS color,
    sizes.name AS size
FROM colors
CROSS JOIN sizes;
```

---

## 9. Subqueries

### Why It Matters
- Complex nested queries
- Dynamic filtering
- Advanced data retrieval

### Types of Subqueries
```sql
-- Scalar subquery (returns single value)
SELECT *
FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- Column subquery (returns single column)
SELECT *
FROM users
WHERE id IN (SELECT DISTINCT user_id FROM orders);

-- Row subquery (returns single row)
SELECT *
FROM products
WHERE (category_id, price) = (SELECT category_id, MAX(price) FROM products);

-- Table subquery (returns table)
SELECT *
FROM (
    SELECT category_id, AVG(price) AS avg_price
    FROM products
    GROUP BY category_id
) AS category_avgs
WHERE avg_price > 100;
```

### Correlated Subqueries
```sql
-- Subquery references outer query
SELECT *
FROM products p1
WHERE price > (
    SELECT AVG(price)
    FROM products p2
    WHERE p2.category_id = p1.category_id
);

-- EXISTS
SELECT *
FROM users u
WHERE EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
);

-- NOT EXISTS
SELECT *
FROM users u
WHERE NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
);
```

---

## 10. Transactions and ACID

### Why It Matters
- Data consistency
- Atomic operations
- Error recovery

### ACID Properties

| Property | Description |
|----------|-------------|
| Atomicity | All or nothing |
| Consistency | Valid state to valid state |
| Isolation | Transactions don't interfere |
| Durability | Committed data persists |

### Transaction Control
```sql
-- Start transaction
START TRANSACTION;

-- Perform operations
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

-- Commit if successful
COMMIT;

-- Or rollback if error
ROLLBACK;

-- Savepoints
START TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
SAVEPOINT after_debit;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
-- If second update fails:
ROLLBACK TO after_debit;
-- Continue or full rollback
COMMIT;
```

### Isolation Levels

| Level | Dirty Read | Non-Repeatable | Phantom |
|-------|------------|----------------|---------|
| READ UNCOMMITTED | Yes | Yes | Yes |
| READ COMMITTED | No | Yes | Yes |
| REPEATABLE READ | No | No | Yes |
| SERIALIZABLE | No | No | No |

```sql
-- Set isolation level
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
```

---

## Quick Reference Card

### Create Table
```sql
CREATE TABLE table_name (
    id INT PRIMARY KEY AUTO_INCREMENT,
    column_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### CRUD Operations
```sql
-- Create
INSERT INTO table (col1, col2) VALUES (val1, val2);

-- Read
SELECT * FROM table WHERE condition;

-- Update
UPDATE table SET col = val WHERE condition;

-- Delete
DELETE FROM table WHERE condition;
```

### Common Query Pattern
```sql
SELECT columns
FROM table1
JOIN table2 ON table1.id = table2.fk
WHERE condition
GROUP BY column
HAVING aggregate_condition
ORDER BY column
LIMIT count;
```

### Join Pattern
```sql
SELECT t1.*, t2.*
FROM table1 t1
LEFT JOIN table2 t2 ON t1.id = t2.foreign_key;
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand relational database concepts
- [ ] Choose appropriate data types
- [ ] Create tables with constraints
- [ ] Write INSERT, UPDATE, DELETE statements
- [ ] Query data with SELECT
- [ ] Use WHERE, ORDER BY, LIMIT clauses
- [ ] Apply aggregate functions with GROUP BY
- [ ] Join multiple tables
- [ ] Write subqueries
- [ ] Manage transactions

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 10: Core Java](../10-java-core/) - Server-side programming
- Practice complex queries
- Learn database optimization techniques
