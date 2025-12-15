# JDBC Key Concepts for Application Developers

## Overview

This document covers essential JDBC (Java Database Connectivity) concepts that every Java application developer must master. JDBC is the standard Java API for connecting to relational databases, executing SQL queries, and managing database transactions. Understanding JDBC is critical for building robust, secure, and efficient database-driven applications.

---

## 1. JDBC Architecture

### Why It Matters
- Foundation for all Java database connectivity
- Understanding architecture helps debug connection issues
- Essential for designing scalable data access layers
- Basis for higher-level frameworks like Hibernate and Spring Data

### Key Concepts

| Component | Description | Developer Use Case |
|-----------|-------------|-------------------|
| JDBC API | Java interfaces and classes | Write database code |
| JDBC Driver Manager | Manages database drivers | Establish connections |
| JDBC Driver | Database-specific implementation | Connect to specific DBMS |
| Two-Tier Model | Client directly connects to database | Simple applications |
| Three-Tier Model | Client -> Application Server -> Database | Enterprise applications |

### Core JDBC Interfaces

```java
// The four primary JDBC interfaces
Driver          // Handles database communication
Connection      // Represents database session
Statement       // Executes SQL queries
ResultSet       // Holds query results
```

### Two-Tier vs Three-Tier Architecture

**Two-Tier Architecture:**
```
Java Application <---> Database
     (JDBC)
```

**Three-Tier Architecture:**
```
Client <---> Application Server <---> Database
              (JDBC Layer)
```

### Basic JDBC Workflow
```java
// 1. Load driver (automatic in JDBC 4.0+)
// 2. Establish connection
Connection conn = DriverManager.getConnection(url, user, password);

// 3. Create statement
Statement stmt = conn.createStatement();

// 4. Execute query
ResultSet rs = stmt.executeQuery("SELECT * FROM users");

// 5. Process results
while (rs.next()) {
    System.out.println(rs.getString("username"));
}

// 6. Close resources
rs.close();
stmt.close();
conn.close();
```

---

## 2. JDBC Driver Types

### Why It Matters
- Driver choice affects performance and portability
- Understanding types helps select right driver for project
- Critical for deployment and environment configuration

### Key Concepts

| Driver Type | Name | Architecture | Use Case |
|-------------|------|--------------|----------|
| Type 1 | JDBC-ODBC Bridge | Java -> ODBC -> DB | Legacy, testing only |
| Type 2 | Native-API Driver | Java -> Native Library -> DB | Better performance |
| Type 3 | Network Protocol | Java -> Middleware -> DB | Enterprise, flexible |
| Type 4 | Thin Driver | Java -> DB (direct) | Production (recommended) |

### Type 4 Driver Examples

```java
// PostgreSQL
Class.forName("org.postgresql.Driver");
String url = "jdbc:postgresql://localhost:5432/mydb";

// MySQL
Class.forName("com.mysql.cj.jdbc.Driver");
String url = "jdbc:mysql://localhost:3306/mydb";

// Oracle
Class.forName("oracle.jdbc.driver.OracleDriver");
String url = "jdbc:oracle:thin:@localhost:1521:orcl";

// SQL Server
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
String url = "jdbc:sqlserver://localhost:1433;databaseName=mydb";
```

### Driver Registration

```java
// Automatic (JDBC 4.0+, recommended)
Connection conn = DriverManager.getConnection(url, user, pass);

// Manual (legacy)
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection(url, user, pass);
```

### Maven Dependencies

```xml
<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>

<!-- MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

---

## 3. Connection Management

### Why It Matters
- Database connections are expensive resources
- Poor connection management causes performance issues
- Connection leaks can crash applications
- Essential for scalable applications

### Key Concepts

| Concept | Description | Best Practice |
|---------|-------------|---------------|
| Connection | Database session | Always close in finally |
| Connection String | Database URL | Use properties file |
| Connection Pool | Reusable connections | Use in production |
| Auto-commit | Automatic transaction commit | Disable for transactions |

### Connection URL Format

```java
// General format
jdbc:<driver>://<host>:<port>/<database>?<parameters>

// PostgreSQL with parameters
jdbc:postgresql://localhost:5432/mydb?ssl=true&connectTimeout=10

// MySQL with timezone
jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&useSSL=false
```

### Basic Connection

```java
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "dbuser";
    private static final String PASSWORD = "dbpass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### Connection with Properties

```java
// db.properties file
// db.url=jdbc:postgresql://localhost:5432/mydb
// db.username=dbuser
// db.password=dbpass

public class ConnectionUtil {
    private static Properties props = new Properties();

    static {
        try (InputStream input = ConnectionUtil.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.username"),
            props.getProperty("db.password")
        );
    }
}
```

### Try-with-Resources (Recommended)

```java
// Automatic resource management
public List<User> getAllUsers() {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();

    try (Connection conn = ConnectionUtil.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            users.add(new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email")
            ));
        }
    } catch (SQLException e) {
        throw new RuntimeException("Failed to fetch users", e);
    }

    return users;
}
```

---

## 4. Statement vs PreparedStatement

### Why It Matters
- PreparedStatement prevents SQL injection
- PreparedStatement improves performance for repeated queries
- Understanding difference is critical for security
- Best practice for all parameterized queries

### Key Concepts

| Feature | Statement | PreparedStatement |
|---------|-----------|-------------------|
| SQL Injection | Vulnerable | Protected |
| Performance | Slower (no caching) | Faster (pre-compiled) |
| Parameters | String concatenation | Placeholders (?) |
| Use Case | Static queries only | Dynamic queries |
| Reusability | No | Yes |

### Statement Example (DANGEROUS - Don't Use)

```java
// VULNERABLE TO SQL INJECTION!
public User getUserByUsername(String username) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = '" + username + "'";

    try (Connection conn = ConnectionUtil.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("username"));
        }
    }
    return null;
}

// Attack: getUserByUsername("admin' OR '1'='1")
// Results in: SELECT * FROM users WHERE username = 'admin' OR '1'='1'
// Returns all users!
```

### PreparedStatement Example (SECURE)

```java
// Safe from SQL injection
public User getUserByUsername(String username) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";

    try (Connection conn = ConnectionUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, username);  // Parameter binding

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email")
                );
            }
        }
    }
    return null;
}
```

### PreparedStatement Parameter Types

```java
public void demonstrateParameterTypes(PreparedStatement pstmt)
        throws SQLException {

    pstmt.setInt(1, 100);                    // Integer
    pstmt.setString(2, "John Doe");          // String
    pstmt.setDouble(3, 99.99);               // Double
    pstmt.setBoolean(4, true);               // Boolean
    pstmt.setDate(5, Date.valueOf("2024-01-01"));  // Date
    pstmt.setTimestamp(6, Timestamp.valueOf("2024-01-01 10:30:00"));
    pstmt.setNull(7, Types.VARCHAR);         // NULL value
}
```

### CRUD Operations with PreparedStatement

```java
public class UserDAO {

    // CREATE
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s)");
        }
    }

    // READ
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getId());

            pstmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
```

---

## 5. SQL Injection Prevention

### Why It Matters
- SQL injection is a top security vulnerability
- Can lead to data breaches, data loss, system compromise
- Required knowledge for secure application development
- Compliance requirement for many industries

### Key Concepts

| Technique | Security Level | Complexity |
|-----------|---------------|------------|
| PreparedStatement | High | Low |
| Stored Procedures | High | Medium |
| Input Validation | Medium | Low |
| Least Privilege | Medium | Low |
| String Concatenation | None | DO NOT USE |

### SQL Injection Attack Examples

```java
// VULNERABLE CODE
String username = request.getParameter("username");
String password = request.getParameter("password");

// Attack input: username = "admin' --"
String sql = "SELECT * FROM users WHERE username = '" + username +
             "' AND password = '" + password + "'";
// Results in: SELECT * FROM users WHERE username = 'admin' -- AND password = '...'
// The -- comments out password check!

// Attack input: username = "'; DROP TABLE users; --"
// Results in: SELECT * FROM users WHERE username = ''; DROP TABLE users; --'
// Deletes entire users table!
```

### Prevention with PreparedStatement

```java
// SECURE CODE
public boolean authenticateUser(String username, String password)
        throws SQLException {

    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

    try (Connection conn = ConnectionUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, username);  // Safely escaped
        pstmt.setString(2, password);  // Safely escaped

        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next();
        }
    }
}
```

### Additional Security Measures

```java
public class SecureDAO {

    // Input validation (defense in depth)
    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
            throw new IllegalArgumentException("Invalid username format");
        }
    }

    // Least privilege - use read-only connection for queries
    public List<User> searchUsers(String searchTerm) throws SQLException {
        validateUsername(searchTerm);

        String sql = "SELECT id, username, email FROM users WHERE username LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = getReadOnlyConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email")
                    ));
                }
            }
        }
        return users;
    }
}
```

---

## 6. ResultSet Navigation and Data Retrieval

### Why It Matters
- ResultSet is how you retrieve query results
- Understanding navigation enables efficient data processing
- Critical for reading database records into Java objects
- Basis for Object-Relational Mapping (ORM) concepts

### Key Concepts

| Method | Purpose | Returns |
|--------|---------|---------|
| `next()` | Move to next row | boolean |
| `previous()` | Move to previous row | boolean |
| `first()` | Move to first row | boolean |
| `last()` | Move to last row | boolean |
| `absolute(n)` | Move to row n | boolean |
| `getInt(index/name)` | Get integer value | int |
| `getString(index/name)` | Get string value | String |
| `getDate(index/name)` | Get date value | Date |

### ResultSet Types

```java
// TYPE_FORWARD_ONLY (default, fastest)
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// TYPE_SCROLL_INSENSITIVE (scrollable, static data)
Statement stmt = conn.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE,
    ResultSet.CONCUR_READ_ONLY
);

// TYPE_SCROLL_SENSITIVE (scrollable, dynamic data)
Statement stmt = conn.createStatement(
    ResultSet.TYPE_SCROLL_SENSITIVE,
    ResultSet.CONCUR_UPDATABLE
);
```

### Basic ResultSet Navigation

```java
public void demonstrateNavigation() throws SQLException {
    String sql = "SELECT id, username, email FROM users";

    try (Connection conn = ConnectionUtil.getConnection();
         Statement stmt = conn.createStatement(
             ResultSet.TYPE_SCROLL_INSENSITIVE,
             ResultSet.CONCUR_READ_ONLY);
         ResultSet rs = stmt.executeQuery(sql)) {

        // Forward iteration
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            System.out.println(id + ": " + username);
        }

        // Move to last row
        if (rs.last()) {
            System.out.println("Last user: " + rs.getString("username"));
        }

        // Move to first row
        if (rs.first()) {
            System.out.println("First user: " + rs.getString("username"));
        }

        // Move to specific row
        if (rs.absolute(3)) {
            System.out.println("Third user: " + rs.getString("username"));
        }
    }
}
```

### Data Retrieval Methods

```java
public User mapResultSetToUser(ResultSet rs) throws SQLException {
    User user = new User();

    // By column name (recommended for readability)
    user.setId(rs.getInt("id"));
    user.setUsername(rs.getString("username"));
    user.setEmail(rs.getString("email"));
    user.setAge(rs.getInt("age"));
    user.setBalance(rs.getDouble("balance"));
    user.setActive(rs.getBoolean("is_active"));
    user.setCreatedAt(rs.getTimestamp("created_at"));

    // By column index (faster but less readable)
    user.setId(rs.getInt(1));
    user.setUsername(rs.getString(2));

    // Handling NULL values
    int age = rs.getInt("age");
    if (rs.wasNull()) {
        user.setAge(null);  // Handle NULL appropriately
    }

    return user;
}
```

### ResultSet Metadata

```java
public void analyzeResultSet(ResultSet rs) throws SQLException {
    ResultSetMetaData metaData = rs.getMetaData();

    int columnCount = metaData.getColumnCount();

    System.out.println("Column Details:");
    for (int i = 1; i <= columnCount; i++) {
        String columnName = metaData.getColumnName(i);
        String columnType = metaData.getColumnTypeName(i);
        int columnSize = metaData.getColumnDisplaySize(i);
        boolean nullable = metaData.isNullable(i) == ResultSetMetaData.columnNullable;

        System.out.printf("%s (%s, size=%d, nullable=%b)%n",
            columnName, columnType, columnSize, nullable);
    }
}
```

---

## 7. Transaction Management

### Why It Matters
- Ensures data consistency and integrity
- Critical for operations involving multiple SQL statements
- Required for financial and critical business operations
- Understanding ACID properties is essential

### Key Concepts

| Property | Meaning | Example |
|----------|---------|---------|
| Atomicity | All or nothing | Transfer money: debit AND credit |
| Consistency | Valid state to valid state | Balance never negative |
| Isolation | Concurrent transactions isolated | No dirty reads |
| Durability | Committed changes persist | Survives system crash |

### Transaction Control Methods

```java
// Disable auto-commit
connection.setAutoCommit(false);

// Commit transaction
connection.commit();

// Rollback transaction
connection.rollback();

// Set savepoint
Savepoint savepoint = connection.setSavepoint("savepoint1");
connection.rollback(savepoint);
```

### Basic Transaction Example

```java
public void transferMoney(int fromAccount, int toAccount, double amount)
        throws SQLException {

    Connection conn = null;
    try {
        conn = ConnectionUtil.getConnection();
        conn.setAutoCommit(false);  // Start transaction

        // Debit from source account
        String debitSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(debitSql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, fromAccount);
            pstmt.executeUpdate();
        }

        // Credit to destination account
        String creditSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(creditSql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, toAccount);
            pstmt.executeUpdate();
        }

        conn.commit();  // Success - commit both operations
        System.out.println("Transfer successful");

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();  // Error - rollback both operations
                System.out.println("Transfer failed, rolled back");
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed", ex);
            }
        }
        throw e;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);  // Restore default
                conn.close();
            } catch (SQLException e) {
                // Log error
            }
        }
    }
}
```

### Transaction with Savepoints

```java
public void complexTransaction() throws SQLException {
    Connection conn = null;
    Savepoint savepoint1 = null;

    try {
        conn = ConnectionUtil.getConnection();
        conn.setAutoCommit(false);

        // First operation
        executeUpdate(conn, "INSERT INTO orders (customer_id) VALUES (1)");

        savepoint1 = conn.setSavepoint("after_order");

        try {
            // Risky operation
            executeUpdate(conn, "UPDATE inventory SET quantity = quantity - 10 WHERE id = 1");

        } catch (SQLException e) {
            // Rollback to savepoint, keep order insertion
            conn.rollback(savepoint1);
            System.out.println("Inventory update failed, but order saved");
        }

        conn.commit();

    } catch (SQLException e) {
        if (conn != null) {
            conn.rollback();  // Rollback everything
        }
        throw e;
    } finally {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}
```

### Isolation Levels

```java
public void demonstrateIsolationLevels(Connection conn) throws SQLException {

    // READ_UNCOMMITTED (lowest isolation, highest performance)
    conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

    // READ_COMMITTED (prevents dirty reads)
    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

    // REPEATABLE_READ (prevents dirty and non-repeatable reads)
    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

    // SERIALIZABLE (highest isolation, lowest performance)
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
}
```

---

## 8. CallableStatement and Stored Procedures

### Why It Matters
- Execute stored procedures and functions
- Leverage database logic and optimization
- Improve performance for complex operations
- Support legacy database systems

### Key Concepts

```java
// Basic stored procedure call
CallableStatement cstmt = conn.prepareCall("{call procedure_name(?, ?)}");

// Function call with return value
CallableStatement cstmt = conn.prepareCall("{? = call function_name(?)}");

// IN parameter
cstmt.setInt(1, value);

// OUT parameter
cstmt.registerOutParameter(2, Types.VARCHAR);

// INOUT parameter
cstmt.setString(3, inputValue);
cstmt.registerOutParameter(3, Types.VARCHAR);
```

### Calling Stored Procedure

```java
// PostgreSQL stored procedure
// CREATE PROCEDURE update_user_email(user_id INT, new_email VARCHAR)
// LANGUAGE plpgsql
// AS $$
// BEGIN
//     UPDATE users SET email = new_email WHERE id = user_id;
// END;
// $$;

public void updateUserEmail(int userId, String newEmail) throws SQLException {
    String sql = "{call update_user_email(?, ?)}";

    try (Connection conn = ConnectionUtil.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        cstmt.setInt(1, userId);
        cstmt.setString(2, newEmail);

        cstmt.execute();
    }
}
```

### Calling Function with OUT Parameters

```java
// PostgreSQL function
// CREATE FUNCTION get_user_count() RETURNS INTEGER
// AS $$
// BEGIN
//     RETURN (SELECT COUNT(*) FROM users);
// END;
// $$ LANGUAGE plpgsql;

public int getUserCount() throws SQLException {
    String sql = "{? = call get_user_count()}";

    try (Connection conn = ConnectionUtil.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        cstmt.registerOutParameter(1, Types.INTEGER);
        cstmt.execute();

        return cstmt.getInt(1);
    }
}
```

### INOUT Parameters

```java
// Stored procedure with INOUT parameter
public void processDiscount(int userId) throws SQLException {
    String sql = "{call calculate_discount(?, ?)}";

    try (Connection conn = ConnectionUtil.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // IN parameter
        cstmt.setInt(1, userId);

        // INOUT parameter
        cstmt.setDouble(2, 100.00);  // Set input value
        cstmt.registerOutParameter(2, Types.DOUBLE);  // Register as output

        cstmt.execute();

        double discountedPrice = cstmt.getDouble(2);  // Get output value
        System.out.println("Discounted price: " + discountedPrice);
    }
}
```

---

## 9. Batch Processing

### Why It Matters
- Dramatically improves performance for bulk operations
- Reduces network round-trips
- Essential for data import/migration
- Critical for high-throughput applications

### Key Concepts

```java
// Add SQL to batch
preparedStatement.addBatch();

// Execute all batched statements
int[] results = preparedStatement.executeBatch();

// Clear batch
preparedStatement.clearBatch();
```

### Batch Insert Example

```java
public void insertUsersBatch(List<User> users) throws SQLException {
    String sql = "INSERT INTO users (username, email) VALUES (?, ?)";

    try (Connection conn = ConnectionUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        conn.setAutoCommit(false);  // Disable auto-commit for performance

        for (User user : users) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.addBatch();

            // Execute batch every 1000 records
            if (users.indexOf(user) % 1000 == 0) {
                pstmt.executeBatch();
                pstmt.clearBatch();
            }
        }

        // Execute remaining records
        pstmt.executeBatch();
        conn.commit();

    } catch (SQLException e) {
        conn.rollback();
        throw e;
    }
}
```

---

## Quick Reference Card

### Essential JDBC Operations

```java
// Connection
Connection conn = DriverManager.getConnection(url, user, pass);

// PreparedStatement (always use for user input)
PreparedStatement pstmt = conn.prepareStatement(
    "SELECT * FROM users WHERE username = ?");
pstmt.setString(1, username);
ResultSet rs = pstmt.executeQuery();

// Transaction
conn.setAutoCommit(false);
try {
    // Multiple operations
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
}

// ResultSet
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
}

// Always close resources (use try-with-resources)
try (Connection conn = getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql);
     ResultSet rs = pstmt.executeQuery()) {
    // Use resources
}
```

### Common Pitfalls to Avoid

```java
// DON'T: String concatenation with user input
String sql = "SELECT * FROM users WHERE name = '" + userInput + "'";  // SQL INJECTION!

// DO: Use PreparedStatement
String sql = "SELECT * FROM users WHERE name = ?";
pstmt.setString(1, userInput);

// DON'T: Forget to close resources
Connection conn = getConnection();  // May leak!

// DO: Use try-with-resources
try (Connection conn = getConnection()) {
    // Automatically closed
}

// DON'T: Use Statement for dynamic queries
Statement stmt = conn.createStatement();
stmt.executeQuery("SELECT * FROM users WHERE id = " + userId);  // VULNERABLE!

// DO: Always use PreparedStatement for user input
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
pstmt.setInt(1, userId);
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand JDBC architecture and components
- [ ] Select and configure appropriate JDBC drivers
- [ ] Establish and manage database connections
- [ ] Use PreparedStatement for all parameterized queries
- [ ] Prevent SQL injection attacks
- [ ] Navigate and extract data from ResultSet
- [ ] Implement transaction management with proper error handling
- [ ] Use try-with-resources for automatic resource cleanup
- [ ] Execute stored procedures with CallableStatement
- [ ] Implement batch processing for bulk operations
- [ ] Handle NULL values correctly
- [ ] Manage connection properties and configuration
- [ ] Debug common JDBC issues

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 12: Testing with JUnit and Mockito](../12-testing/) - Learn to test database code
- Practice building a complete DAO (Data Access Object) layer
- Explore connection pooling with HikariCP
- Learn JPA/Hibernate for higher-level database access
- Study Spring Data JDBC for modern Java database development
