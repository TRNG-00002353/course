# Exercise: E-Commerce Database Design and Queries

## Objective
Design and implement a relational database for an e-commerce application, then write SQL queries to manage and analyze the data.

## Part 1: Database Design

### Requirements
Create a MySQL database called `ecommerce_db` with the following tables:

#### 1. customers
- customer_id (Primary Key, Auto Increment)
- first_name, last_name
- email (unique)
- phone
- created_at (timestamp)

#### 2. categories
- category_id (Primary Key)
- category_name
- description

#### 3. products
- product_id (Primary Key)
- product_name
- description
- price (decimal)
- stock_quantity
- category_id (Foreign Key)
- created_at

#### 4. orders
- order_id (Primary Key)
- customer_id (Foreign Key)
- order_date
- status (enum: 'pending', 'processing', 'shipped', 'delivered', 'cancelled')
- total_amount
- shipping_address

#### 5. order_items
- order_item_id (Primary Key)
- order_id (Foreign Key)
- product_id (Foreign Key)
- quantity
- unit_price

## Part 2: SQL Queries

Write SQL queries for the following tasks:

### Basic Queries
1. Insert sample data (at least 5 records per table)
2. Select all products with their category names
3. Find all orders for a specific customer
4. Update product stock after an order
5. Delete cancelled orders older than 30 days

### Intermediate Queries
6. Find the top 5 best-selling products
7. Calculate total revenue per category
8. List customers who haven't ordered in the last 6 months
9. Find products that are low in stock (quantity < 10)

### Advanced Queries
10. Create a view for order summary with customer and product details
11. Write a query using subquery to find customers who ordered above-average amounts
12. Use GROUP BY and HAVING to find categories with more than $1000 in sales
13. Write a transaction to process an order (insert order, order_items, update stock)

## Part 3: Functions, Stored Procedures, and Triggers

Using the e-commerce database from Parts 1 and 2, implement the following:

### Scalar Functions Practice
1. Write queries using built-in functions:
   - Format all customer names as "LASTNAME, Firstname" using string functions
   - Calculate the age of each order in days using date functions
   - Format product prices as currency with 2 decimals (e.g., "$99.99")
   - Categorize orders as 'Small' (<$50), 'Medium' ($50-$200), 'Large' (>$200) using CASE

### User-Defined Functions
2. Create a function `CalculateOrderTotal(order_id INT)` that:
   - Returns the total amount for an order including 8% tax
   - Returns 0 if order doesn't exist

3. Create a function `GetCustomerLevel(customer_id INT)` that:
   - Returns 'Gold' if total purchases > $1000
   - Returns 'Silver' if total purchases > $500
   - Returns 'Bronze' otherwise

4. Create a function `FormatProductCode(product_id INT)` that:
   - Returns product code in format "CAT-XXXXX" (category prefix + zero-padded ID)
   - Example: Electronics product #42 → "ELE-00042"

### Stored Procedures
5. Create procedure `ProcessOrder(IN customer_id INT, OUT order_id INT)` that:
   - Creates a new order for the customer
   - Returns the new order_id via OUT parameter
   - Sets initial status to 'pending'

6. Create procedure `AddItemToOrder(IN order_id INT, IN product_id INT, IN qty INT, OUT success BOOLEAN)` that:
   - Checks if sufficient stock exists
   - Adds item to order_items if stock available
   - Reduces product stock_quantity
   - Sets success = FALSE if insufficient stock

7. Create procedure `GetSalesReport(IN start_date DATE, IN end_date DATE)` that:
   - Returns daily sales totals between the dates
   - Includes: date, order_count, total_revenue, avg_order_value

8. Create procedure `UpdateOrderStatus(IN order_id INT, IN new_status VARCHAR(20))` that:
   - Validates status is one of the allowed values
   - Updates the order status
   - If status = 'cancelled', restores product stock quantities

### Triggers
9. Create trigger `trg_order_audit` (AFTER INSERT/UPDATE/DELETE on orders):
   - Logs all order changes to an `order_audit` table
   - Records: order_id, action, old_status, new_status, changed_at, changed_by

10. Create trigger `trg_validate_order_item` (BEFORE INSERT on order_items):
    - Prevents inserting if quantity > available stock
    - Prevents inserting if product doesn't exist
    - Uses SIGNAL to raise appropriate error messages

11. Create trigger `trg_update_order_total` (AFTER INSERT/UPDATE/DELETE on order_items):
    - Automatically recalculates and updates orders.total_amount

12. Create trigger `trg_low_stock_alert` (AFTER UPDATE on products):
    - When stock_quantity falls below 10, insert record into `stock_alerts` table
    - Include: product_id, product_name, current_quantity, alert_date

### Required Tables for Triggers
```sql
CREATE TABLE order_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    action VARCHAR(10),
    old_status VARCHAR(20),
    new_status VARCHAR(20),
    old_total DECIMAL(10,2),
    new_total DECIMAL(10,2),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(100)
);

CREATE TABLE stock_alerts (
    alert_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    product_name VARCHAR(100),
    current_quantity INT,
    alert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Expected Deliverables
1. `schema.sql` - Database and table creation scripts (including audit tables)
2. `seed_data.sql` - INSERT statements for sample data
3. `queries.sql` - All query solutions from Part 2
4. `functions.sql` - All user-defined functions
5. `procedures.sql` - All stored procedures
6. `triggers.sql` - All trigger definitions

## Skills Tested
- DDL: CREATE DATABASE, CREATE TABLE
- DML: INSERT, UPDATE, DELETE, SELECT
- Constraints: PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL
- Joins: INNER JOIN, LEFT JOIN
- Aggregations: COUNT, SUM, AVG, GROUP BY, HAVING
- Subqueries and Views
- Transactions
- Built-in Scalar Functions (String, Numeric, Date/Time)
- User-Defined Functions (CREATE FUNCTION, RETURNS, DETERMINISTIC)
- Stored Procedures (CREATE PROCEDURE, IN/OUT/INOUT parameters)
- Control Flow (IF, CASE, WHILE, LOOP)
- Triggers (BEFORE/AFTER, INSERT/UPDATE/DELETE, NEW/OLD)
- Error Handling (SIGNAL SQLSTATE)

## Bonus Challenge
- Add a cursor-based procedure that processes all pending orders older than 24 hours
- Create a function that calculates shipping cost based on total weight and destination
- Implement a trigger that prevents price changes greater than 20% in a single update
