# Managing Connections and Statements

## Table of Contents
1. [Connection Lifecycle](#connection-lifecycle)
2. [Connection Properties](#connection-properties)
3. [Statement Types](#statement-types)
4. [Statement Execution Methods](#statement-execution-methods)
5. [Resource Management](#resource-management)
6. [Connection Pooling](#connection-pooling)
7. [Best Practices](#best-practices)
8. [Important Notes](#important-notes)

---

## Connection Lifecycle

### Fig. 1: Complete Connection Lifecycle

```
CONNECTION LIFECYCLE STATES
═══════════════════════════════════════════════════════════════

STATE 1: PRE-CONNECTION
┌───────────────────────────────┐
│ - Driver registered           │
│ - DriverManager ready         │
│ - No active connection        │
│ - Resources: None             │
└───────────────────────────────┘
            │
            │ DriverManager.getConnection()
            │ or DataSource.getConnection()
            ▼

STATE 2: CONNECTION PENDING
┌───────────────────────────────┐
│ - Connecting to database      │
│ - Authentication in progress  │
│ - Network socket opening      │
│ - Connection timeout may occur│
└───────────────────────────────┘
            │
            │ Success
            ▼

STATE 3: ACTIVE CONNECTION
┌───────────────────────────────┐
│ - Connected to database       │
│ - Ready for statements        │
│ - Session established         │
│ - Auto-commit: default        │
│ - Can create statements       │
│ - Can start transactions      │
│ - Resources: Connection + TCP │
└───────────────────────────────┘
            │
            ├─ createStatement()
            │   └─ Statement created
            ├─ prepareStatement()
            │   └─ PreparedStatement created
            ├─ prepareCall()
            │   └─ CallableStatement created
            ├─ setAutoCommit(false)
            │   └─ Manual transaction control
            ├─ getMetaData()
            │   └─ Database info retrieved
            │
            │ (Normal usage phase)
            │
            ├─ Exception occurs
            │   └─ Connection may become INVALID
            │
            │ close() called
            ▼

STATE 4: CLOSED CONNECTION
┌───────────────────────────────┐
│ - Connection terminated       │
│ - No statements can be used   │
│ - Resources released          │
│ - TCP socket closed           │
│ - Any operation throws exc.   │
│ - isClosed() returns true     │
└───────────────────────────────┘
            │
            │ (End of lifecycle)
            └─ Garbage collected

─────────────────────────────────────────────────────────────────

BASIC CONNECTION CODE FLOW
═════════════════════════════════════════════════════════════

try {
    // Step 1: Establish connection
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/mydb",
        "user",
        "password"
    );
    // CONNECTION STATE: ACTIVE
    
    // Step 2: Create statement
    Statement stmt = conn.createStatement();
    
    // Step 3: Execute queries
    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    
    // Step 4: Process results
    while (rs.next()) {
        // Use data
    }
    
    // Step 5: Close resources (REVERSE ORDER)
    rs.close();        // Close ResultSet first
    stmt.close();      // Close Statement second
    conn.close();      // Close Connection last
    // CONNECTION STATE: CLOSED
    
} catch (SQLException e) {
    // Handle error
    System.err.println("Error: " + e.getMessage());
}
```

---

## Connection Properties

### Fig. 2: Connection Configuration Options

```
CONNECTION PROPERTIES
═════════════════════════════════════════════════════════════

TRANSACTION CONTROL
───────────────────

Auto-Commit Mode (Default)
├─ Each statement is auto-committed
├─ Set via: conn.setAutoCommit(true)  [default]
├─ Behavior: Changes immediately permanent
├─ Performance: Slower (each stmt commits)
├─ Use: Single statements, auto-save required
└─ Code:
    Connection conn = DriverManager.getConnection(url, user, pwd);
    // Auto-commit is ON by default
    stmt.executeUpdate("UPDATE users SET age=30");  // Auto-committed

Manual Transaction Control
├─ Explicit commit/rollback control
├─ Set via: conn.setAutoCommit(false)
├─ Behavior: Changes grouped in transaction
├─ Performance: Faster (batch commits)
├─ Use: Multi-statement operations
└─ Code:
    conn.setAutoCommit(false);
    try {
        stmt.executeUpdate("UPDATE account1 SET balance = balance - 100");
        stmt.executeUpdate("UPDATE account2 SET balance = balance + 100");
        conn.commit();  // Both succeed together
    } catch (SQLException e) {
        conn.rollback();  // Both rollback on error
    }

─────────────────────────────────────────────────────────────────

TRANSACTION ISOLATION LEVELS
─────────────────────────────

Isolation Level          │ Dirty Read │ Non-Repeat │ Phantom
                        │            │    Read    │  Read
────────────────────────┼────────────┼────────────┼─────────
READ_UNCOMMITTED        │     ✓      │     ✓      │    ✓
READ_COMMITTED          │     ✗      │     ✓      │    ✓
REPEATABLE_READ         │     ✗      │     ✗      │    ✓
SERIALIZABLE            │     ✗      │     ✗      │    ✗
────────────────────────┴────────────┴────────────┴─────────

Dirty Read: Reading uncommitted changes from other transactions
Non-Repeatable Read: Same query returns different results
Phantom Read: New rows appear during transaction

Setting Isolation Level:
    conn.setTransactionIsolation(
        Connection.TRANSACTION_READ_COMMITTED
    );

Common Choices:
├─ READ_COMMITTED: Good balance (most databases default)
├─ REPEATABLE_READ: Stronger consistency (MySQL default)
├─ SERIALIZABLE: Strongest consistency (performance cost)
└─ READ_UNCOMMITTED: Rarely used (data integrity risk)

─────────────────────────────────────────────────────────────────

HOLD-ABILITY
─────────────

ResultSet Hold-Ability (after commit/rollback)

CLOSE_CURSORS_AT_COMMIT
├─ ResultSet closed after commit()
├─ Default behavior
├─ Set: createStatement(
     ResultSet.TYPE_SCROLL_INSENSITIVE,
     ResultSet.CONCURRENCY_READ_ONLY,
     ResultSet.CLOSE_CURSORS_AT_COMMIT
   )
└─ Use: Most common

HOLD_CURSORS_OVER_COMMIT
├─ ResultSet remains open after commit()
├─ Requires database support
├─ Performance overhead
└─ Use: Need data after commit (rare)

─────────────────────────────────────────────────────────────────

READ-ONLY MODE
───────────────

Read-Only Connection:
    conn.setReadOnly(true);

Effects:
├─ All statements are SELECT only
├─ INSERT/UPDATE/DELETE throw exception
├─ Database optimization possible
├─ Slightly better performance
├─ Query can only read data
└─ Use: Reporting, analysis applications

Usage:
    Connection readConn = ds.getConnection();
    readConn.setReadOnly(true);
    ResultSet rs = stmt.executeQuery("SELECT ...");
    // Insert will throw: "Connection is read-only"

─────────────────────────────────────────────────────────────────

TIMEOUT SETTINGS
─────────────────

Connection Timeout (via DriverManager)
├─ Applied to ALL connections from DriverManager
├─ Set: DriverManager.setLoginTimeout(seconds)
├─ Default: 0 (infinite wait)
├─ Example: DriverManager.setLoginTimeout(10)
└─ Thread-safe global setting

Statement Timeout (per statement)
├─ Time limit for single query
├─ Set: stmt.setQueryTimeout(seconds)
├─ Default: 0 (infinite)
├─ Example: stmt.setQueryTimeout(30)
└─ Throws SQLException on timeout

Network Timeout (per connection)
├─ For database reads/writes
├─ Set: conn.setNetworkTimeout(executor, timeoutMs)
├─ Default: 0 (infinite)
├─ Requires Executor
└─ Modern JDBC feature

Example:
    DriverManager.setLoginTimeout(10);  // 10 sec connection timeout
    stmt.setQueryTimeout(30);            // 30 sec per query
    conn.setNetworkTimeout(
        Executors.newScheduledThreadPool(1),
        5000  // 5 seconds network timeout
    );

─────────────────────────────────────────────────────────────────

CATALOG & SCHEMA
─────────────────

Catalog Selection:
    conn.setCatalog("mydb");      // MySQL: database
    conn.setCatalog("dbname");    // Other databases

Schema Selection:
    conn.setSchema("public");     // PostgreSQL schema
    conn.setSchema("dbo");        // SQL Server schema

Retrieve Current:
    String catalog = conn.getCatalog();
    String schema = conn.getSchema();

─────────────────────────────────────────────────────────────────

DIAGNOSTIC PROPERTIES
──────────────────────

Connection Validation:
    boolean isClosed = conn.isClosed();
    boolean isValid = conn.isValid(timeoutSeconds);
        // Checks actual connection validity
        // More expensive than isClosed()
        // Useful for pool health checks

Warning Handling:
    SQLWarning warning = conn.getWarnings();
    while (warning != null) {
        System.out.println(warning.getMessage());
        warning = warning.getNextWarning();
    }
    conn.clearWarnings();  // Clear all warnings
```

---

## Statement Types

### Fig. 3: JDBC Statement Type Hierarchy

```
JAVA.SQL.STATEMENT HIERARCHY
═════════════════════════════════════════════════════════════

                    ┌─────────────────┐
                    │  Statement      │ (Base interface)
                    │                 │
                    │ - executeQuery()│
                    │ - executeUpdate()
                    │ - execute()     │
                    │ - addBatch()    │
                    │ - executeBatch()│
                    └────────┬────────┘
                             │
                ┌────────────┼────────────┐
                │            │            │
                ▼            ▼            ▼
           Statement   PreparedStatement  CallableStatement
         (Simple SQL) (Parameterized SQL) (Stored Procs)

─────────────────────────────────────────────────────────────────

TYPE 1: STATEMENT (Simple SQL)
═════════════════════════════════════════════════════════════

Purpose: Execute static SQL queries without parameters

Characteristics:
├─ Created from Connection
├─ SQL embedded directly
├─ No parameter binding
├─ Limited reusability
├─ Slower for repeated queries
└─ Simple to use

Creation:
    Statement stmt = conn.createStatement();
    
    // Optional: Configure
    stmt.setFetchSize(100);        // Rows per fetch
    stmt.setQueryTimeout(30);      // 30-second timeout
    stmt.setMaxRows(1000);         // Max result rows

Execution:
    // SELECT query
    ResultSet rs = stmt.executeQuery(
        "SELECT id, name FROM users"
    );
    
    // INSERT/UPDATE/DELETE
    int rowsAffected = stmt.executeUpdate(
        "UPDATE users SET active=1 WHERE id=5"
    );
    
    // Any SQL
    boolean isResultSet = stmt.execute(
        "SELECT * FROM users"
    );

Cleanup:
    stmt.close();  // MUST close

Suitable For:
├─ One-time queries
├─ Non-parameterized SQL
├─ Simple selects
└─ Batch operations

─────────────────────────────────────────────────────────────────

TYPE 2: PREPAREDSTATEMENT (Parameterized)
═════════════════════════════════════════════════════════════

Purpose: Execute SQL with parameters (placeholders)

Characteristics:
├─ Pre-compiled by database
├─ Parameters substituted at execution
├─ Prevents SQL injection
├─ Better performance (reusable)
├─ Type-safe parameter binding
├─ More complex than Statement
└─ Preferred for dynamic queries

Creation:
    PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM users WHERE id = ? AND age > ?"
    );
    // ? = placeholder for parameters

Parameter Binding (1-indexed):
    pstmt.setInt(1, userId);           // First ?
    pstmt.setInt(2, 18);               // Second ?
    
    // Other setters:
    pstmt.setString(1, "John");
    pstmt.setDouble(2, 99.99);
    pstmt.setDate(3, java.sql.Date.valueOf("2024-01-15"));
    pstmt.setBoolean(4, true);
    pstmt.setNull(5, java.sql.Types.VARCHAR);

Execution:
    // Execute query (no SQL needed)
    ResultSet rs = pstmt.executeQuery();
    
    // Execute update
    int rows = pstmt.executeUpdate();
    
    // Multiple executions (change parameters)
    pstmt.setInt(1, 2);
    rs = pstmt.executeQuery();  // Same statement, different param

Batch Execution:
    pstmt.setInt(1, 1);
    pstmt.addBatch();
    pstmt.setInt(1, 2);
    pstmt.addBatch();
    pstmt.setInt(1, 3);
    pstmt.addBatch();
    int[] results = pstmt.executeBatch();

Cleanup:
    pstmt.close();  // MUST close

Suitable For:
├─ Parameterized queries
├─ Repeated execution (batch)
├─ User input handling
├─ Security (SQL injection prevention)
└─ Performance-critical code

─────────────────────────────────────────────────────────────────

TYPE 3: CALLABLESTATEMENT (Stored Procedures)
═════════════════════════════════════════════════════════════

Purpose: Execute stored procedures & functions

Characteristics:
├─ Calls database stored procs
├─ In/Out/InOut parameters
├─ Returns values from procedures
├─ Complex logic on server
├─ Better encapsulation
└─ Database-specific

Creation:
    CallableStatement cstmt = conn.prepareCall(
        "{call stored_proc(?, ?, ?)}"
    );
    
    // or for function
    CallableStatement cstmt = conn.prepareCall(
        "{? = call get_user_count()}"
    );

Parameter Setup:
    cstmt.setInt(1, userId);          // IN parameter
    cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);  // OUT
    cstmt.registerOutParameter(3, java.sql.Types.INTEGER);  // OUT

Execution:
    cstmt.execute();
    
    // Retrieve OUT parameters
    String outValue = cstmt.getString(2);
    int outInt = cstmt.getInt(3);

Example (MySQL):
    CallableStatement cstmt = conn.prepareCall(
        "{call get_user_info(?)}"
    );
    cstmt.setInt(1, 5);
    cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
    cstmt.execute();
    String userName = cstmt.getString(2);

Cleanup:
    cstmt.close();  // MUST close

Suitable For:
├─ Stored procedures
├─ Database functions
├─ Complex business logic on DB
└─ Legacy systems with procs

─────────────────────────────────────────────────────────────────

COMPARISON TABLE
═════════════════════════════════════════════════════════════

Feature          │ Statement │ PreparedStmt │ CallableStmt
─────────────────┼───────────┼──────────────┼─────────────
Purpose          │ Simple SQL│ Parameterized│ Stored Procs
SQL in code      │    ✓      │    ✓ (with ?)│   ✓ (call)
Pre-compiled     │    ✗      │    ✓         │    ✓
Type-safe params │    ✗      │    ✓         │    ✓
SQL injection risk│   HIGH   │    NONE      │    NONE
Reusability      │   Low     │    High      │    High
Performance      │   Poor    │   Good       │   Excellent
Complexity       │   Low     │   Medium     │   High
Common use       │ Rare      │   Most apps  │  Special use
Recommended      │    ✗      │    ✓         │   Specific
```

---

## Statement Execution Methods

### Fig. 4: Query Execution Patterns

```
QUERY EXECUTION METHODS
═════════════════════════════════════════════════════════════

METHOD 1: executeQuery() - SELECT Statements
─────────────────────────────────────────────

Purpose: Execute SELECT queries that return data

Return: ResultSet (rows of data)

Code:
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(
        "SELECT id, name, email FROM users WHERE active=1"
    );
    
    int rowCount = 0;
    while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString("name");
        String email = rs.getString(3);
        System.out.println(name + " (" + email + ")");
        rowCount++;
    }
    System.out.println("Retrieved " + rowCount + " rows");
    
    rs.close();
    stmt.close();

Throws: SQLException if:
├─ SQL syntax error
├─ Column name doesn't exist
├─ Connection is closed
└─ Other DB errors

Key Points:
├─ ONLY for SELECT statements
├─ Returns ResultSet (even if empty)
├─ Must close ResultSet & Statement
├─ Use while(rs.next()) to iterate
└─ Cannot call for INSERT/UPDATE/DELETE

─────────────────────────────────────────────────────────────────

METHOD 2: executeUpdate() - INSERT/UPDATE/DELETE
──────────────────────────────────────────────────

Purpose: Execute statements that modify data

Return: int (number of affected rows)

Code:
    Statement stmt = conn.createStatement();
    
    // INSERT
    int inserted = stmt.executeUpdate(
        "INSERT INTO users(name, email) VALUES('John', 'john@ex.com')"
    );
    System.out.println("Inserted: " + inserted + " row(s)");
    
    // UPDATE
    int updated = stmt.executeUpdate(
        "UPDATE users SET active=0 WHERE id > 100"
    );
    System.out.println("Updated: " + updated + " row(s)");
    
    // DELETE
    int deleted = stmt.executeUpdate(
        "DELETE FROM users WHERE age < 18"
    );
    System.out.println("Deleted: " + deleted + " row(s)");
    
    stmt.close();

Return Value Interpretation:
├─ > 0: Number of affected rows
├─ 0: No rows affected (valid)
└─ -1: Unknown number of rows (some DBs)

Throws: SQLException if:
├─ SQL syntax error
├─ Constraint violation (FK, PK, unique)
├─ Column doesn't exist
└─ Other DB errors

Key Points:
├─ NOT for SELECT (throws exception)
├─ Returns affected row count
├─ No ResultSet returned
├─ Fast and efficient
├─ Often used in transactions
└─ Check return count for validation

─────────────────────────────────────────────────────────────────

METHOD 3: execute() - Any SQL Statement
─────────────────────────────────────────

Purpose: Execute any SQL (when type unknown)

Return: boolean (true = ResultSet, false = update count)

Code:
    Statement stmt = conn.createStatement();
    String sql = getDynamicSQL();  // Unknown type
    
    if (stmt.execute(sql)) {
        // SQL returned ResultSet
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            // Process rows
        }
        rs.close();
    } else {
        // SQL was INSERT/UPDATE/DELETE
        int rowCount = stmt.getUpdateCount();
        System.out.println("Rows affected: " + rowCount);
    }
    
    stmt.close();

Return Value:
├─ true: ResultSet available (SELECT)
├─ false: Update count (INSERT/UPDATE/DELETE)
└─ -1: No result from query

Flow:
    execute() returns boolean
        │
        ├─ true → Use getResultSet()
        │
        └─ false → Use getUpdateCount()

Key Points:
├─ Use when SQL type is unknown
├─ More complex than specific methods
├─ Must check return value
├─ Handles multiple result sets (stored procs)
├─ Less common than executeQuery/executeUpdate
└─ Performance slightly slower

─────────────────────────────────────────────────────────────────

METHOD 4: Batch Operations (Multiple Statements)
──────────────────────────────────────────────────

Purpose: Execute multiple statements efficiently

Process:
    1. Add multiple statements to batch
    2. Execute all at once
    3. Get array of results

Code:
    Statement stmt = conn.createStatement();
    
    // Add to batch
    stmt.addBatch("INSERT INTO users VALUES(1, 'John')");
    stmt.addBatch("INSERT INTO users VALUES(2, 'Jane')");
    stmt.addBatch("INSERT INTO users VALUES(3, 'Bob')");
    stmt.addBatch("UPDATE users SET active=1");
    
    // Execute all
    int[] results = stmt.executeBatch();
    
    for (int i = 0; i < results.length; i++) {
        if (results[i] >= 0) {
            System.out.println("Statement " + i + ": " + results[i] + " rows");
        } else if (results[i] == Statement.EXECUTE_FAILED) {
            System.out.println("Statement " + i + ": FAILED");
        }
    }
    
    stmt.close();

PreparedStatement Batch:
    PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO users(id, name) VALUES(?, ?)"
    );
    
    for (int i = 1; i <= 1000; i++) {
        pstmt.setInt(1, i);
        pstmt.setString(2, "User" + i);
        pstmt.addBatch();
    }
    
    int[] results = pstmt.executeBatch();
    System.out.println("Inserted " + results.length + " rows");
    pstmt.close();

Performance:
├─ Much faster than individual executions
├─ Reduces network round-trips
├─ Useful for bulk operations
├─ Can handle failures per statement
└─ Returns int[] with counts per statement

─────────────────────────────────────────────────────────────────

EXECUTION FLOW SUMMARY
═════════────────════════════════════════════

Decision Tree:
    
    SQL Type Unknown?
        ├─ YES → Use execute()
        │        Check boolean return
        │        Call getResultSet() or getUpdateCount()
        │
        └─ NO
            │
            ├─ SELECT → Use executeQuery()
            │           Returns ResultSet
            │           Iterate with while(rs.next())
            │
            ├─ INSERT/UPDATE/DELETE → Use executeUpdate()
            │                          Returns int (row count)
            │
            └─ Multiple statements → Use addBatch() + executeBatch()
                                     Returns int[] (results per stmt)
```

---

## Resource Management

### Fig. 5: Try-With-Resources (AutoCloseable)

```
TRADITIONAL APPROACH (Manual Close)
═══════════════════════════════════════════════════════════════

Code:
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = DriverManager.getConnection(url, user, pwd);
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM users");
        
        while (rs.next()) {
            // Process data
        }
        
    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
    } finally {
        // Manual cleanup (REVERSE order)
        if (rs != null) {
            try { rs.close(); }
            catch (SQLException e) { e.printStackTrace(); }
        }
        if (stmt != null) {
            try { stmt.close(); }
            catch (SQLException e) { e.printStackTrace(); }
        }
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }

Problems:
├─ Verbose & error-prone
├─ Resource leak if exception in catch
├─ Hard to maintain
├─ Nested try-catches
└─ Easy to forget cleanup

─────────────────────────────────────────────────────────────────

MODERN APPROACH (Try-With-Resources) ⭐ RECOMMENDED
═══════════════════════════════════════════════════════════════

Syntax:
    try (Resource1 r1 = createR1(); 
         Resource2 r2 = createR2();
         Resource3 r3 = createR3()) {
        // Use resources
        // Cleanup happens automatically
    } catch (Exception e) {
        // Handle error
    }

Code (Much Simpler):
    try (Connection conn = DriverManager.getConnection(url, user, pwd);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
        
        while (rs.next()) {
            // Process data
        }
        
    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
    }
    // Resources automatically closed here (REVERSE order)

Benefits:
├─ Automatic resource cleanup (AutoCloseable)
├─ No nested try-catches
├─ Less boilerplate
├─ Cleaner code
├─ Guaranteed cleanup (even on exception)
├─ Suppressed exceptions handled properly
└─ Java 7+ feature

How It Works:
    1. Opens resource1 (constructor)
    2. Opens resource2
    3. Opens resource3
    4. Executes try block
    5. Exception? → Catch block
    6. FINALLY: Close resource3
    7. FINALLY: Close resource2
    8. FINALLY: Close resource1
    (REVERSE order!)

Multiple Resources:
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        // All three auto-close
    } catch (SQLException e) {
        e.printStackTrace();
    }

With DataSource (Connection Pooling):
    try (Connection conn = dataSource.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
        }
        
    } catch (SQLException e) {
        logger.error("Database error", e);
    }

─────────────────────────────────────────────────────────────────

RESOURCE CLOSING ORDER (CRITICAL)
═════════════════════════════════════════════════════════════════

CORRECT ORDER TO CLOSE:
┌──────────────────────────────────────┐
│ 1. ResultSet.close()    (If present) │
│ 2. Statement.close()    (Required)   │
│ 3. Connection.close()   (Critical)   │
└──────────────────────────────────────┘

Why this order?
├─ ResultSet depends on Statement
├─ Statement depends on Connection
├─ Close dependencies before dependents
└─ Reverse order of creation

WRONG ORDER (May cause issues):
    conn.close();   // ✗ Closes connection first
    stmt.close();   // May fail or hang
    rs.close();     // May fail

CORRECT MANUAL CLOSE:
    try {
        // Use resources
    } finally {
        if (rs != null) rs.close();      // ← Close first
        if (stmt != null) stmt.close();  // ← Close second
        if (conn != null) conn.close();  // ← Close last
    }

WITH TRY-WITH-RESOURCES (Order automatic):
    try (Connection conn = ...; 
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(...)) {
        // Use resources
    }
    // Automatic: rs.close() → stmt.close() → conn.close()
```

---

## Connection Pooling

### Fig. 6: Connection Pooling Concept

```
WITHOUT CONNECTION POOLING
═══════════════════════════════════════════════════════════════

Request 1:
    1. DriverManager.getConnection()
    2. Create TCP connection (slow!)
    3. Authenticate
    4. Execute query
    5. Close connection (release)

Request 2:
    1. DriverManager.getConnection()
    2. Create NEW TCP connection (slow again!)
    3. Authenticate
    4. Execute query
    5. Close connection

Request 3... N: Repeat above

Problems:
├─ Creating connections is expensive (100-500ms)
├─ Repeated connection creation is wasteful
├─ Slow application response
├─ Database connection exhaustion possible
└─ Not suitable for high-traffic apps

─────────────────────────────────────────────────────────────────

WITH CONNECTION POOLING ⭐ RECOMMENDED
═══════════════════════════════════════════════════════════════

Concept:
    ┌─────────────────────────────────────┐
    │   CONNECTION POOL                   │
    │                                     │
    │  ┌─────────┐  ┌─────────┐  ┌────┐   │
    │  │ Conn 1  │  │ Conn 2  │  │... │   │
    │  │ (FREE)  │  │ (IN USE)│  │    │   │
    │  └─────────┘  └─────────┘  └────┘   │
    │                                     │
    │  - Maintains 10-100 pre-created     │
    │    connections                      │
    │  - Reuses connections               │
    │  - Manages connection lifecycle     │
    │  - Monitors health                  │
    └─────────────────────────────────────┘

Process:
    Request 1:
        1. Pool.getConnection()
        2. Reuse pooled connection (FREE)
        3. Mark as IN USE
        4. Execute query (fast!)
        5. Return connection to pool
        6. Mark as FREE

    Request 2:
        1. Pool.getConnection()
        2. Reuse different pooled connection (instant)
        3. Execute query
        4. Return to pool

Benefits:
├─ Connection reuse (no creation overhead)
├─ Fast response times
├─ Controlled resource usage
├─ Connection health monitoring
├─ Thread-safe access
├─ Automatic connection validation
├─ Connection timeout handling
└─ Load balancing capability

Popular Pooling Libraries:
├─ HikariCP (⭐ Recommended - fastest, simplest)
├─ c3p0 (Legacy but reliable)
├─ DBCP (Apache Commons)
├─ Tomcat JDBC Pool
└─ Built-in app server pools

─────────────────────────────────────────────────────────────────

HIKARICP EXAMPLE (RECOMMENDED)
═══════════════════════════════════════════════════════════════

Maven Dependency:
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>5.0.1</version>
    </dependency>

Configuration:
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
    config.setUsername("root");
    config.setPassword("password");
    
    // Pool settings
    config.setMaximumPoolSize(20);       // Max connections
    config.setMinimumIdle(5);            // Min idle connections
    config.setConnectionTimeout(30000);  // 30 seconds
    config.setIdleTimeout(600000);       // 10 minutes idle timeout
    config.setMaxLifetime(1800000);      // 30 minutes max lifetime
    
    // Performance
    config.setAutoCommit(true);
    config.setLeakDetectionThreshold(60000); // Leak detection
    
    HikariDataSource dataSource = new HikariDataSource(config);

Usage:
    // Get connection from pool
    try (Connection conn = dataSource.getConnection()) {
        // Connection automatically returned to pool
        // when try block exits
        Statement stmt = conn.createStatement();
        // ... use connection
    } catch (SQLException e) {
        e.printStackTrace();
    }

Multiple Queries (Efficient):
    // Reuses same connection from pool if available
    for (int i = 0; i < 100; i++) {
        try (Connection conn = dataSource.getConnection()) {
            // Do work - very fast (reused connection)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

Shutdown:
    dataSource.close();  // Close all pooled connections
```

---

## Best Practices

### ✓ Connection & Statement Best Practices

```
1. ALWAYS USE TRY-WITH-RESOURCES
   ✓ DO:
       try (Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           // Use resources
       } catch (SQLException e) {
           // Handle error
       }
   
   ✗ DON'T:
       Connection conn = DriverManager.getConnection(url, user, pwd);
       // Forget to close → Resource leak!

─────────────────────────────────────────────────────────────────

2. USE CONNECTION POOLING IN PRODUCTION
   ✓ DO:
       HikariConfig config = new HikariConfig();
       // Configure pool
       HikariDataSource ds = new HikariDataSource(config);
       // Use ds.getConnection() for all queries
   
   ✗ DON'T:
       DriverManager.getConnection() for every query
       // Too slow, creates new connection each time

─────────────────────────────────────────────────────────────────

3. USE PREPAREDSTATEMENT FOR PARAMETERS
   ✓ DO:
       String sql = "SELECT * FROM users WHERE id = ? AND age > ?";
       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setInt(1, userId);
       pstmt.setInt(2, 18);
       ResultSet rs = pstmt.executeQuery();
   
   ✗ DON'T:
       String sql = "SELECT * FROM users WHERE id = " + userId 
                   + " AND age > " + age;  // SQL INJECTION RISK!

─────────────────────────────────────────────────────────────────

4. CLOSE RESOURCES IN CORRECT ORDER
   ✓ DO: ResultSet → Statement → Connection
   
   ✗ DON'T: Connection → Statement → ResultSet

─────────────────────────────────────────────────────────────────

5. VALIDATE CONNECTIONS
   ✓ DO:
       if (!conn.isClosed() && conn.isValid(2)) {
           // Connection is good
       }
   
   ✗ DON'T:
       Assume connection is valid without checking

─────────────────────────────────────────────────────────────────

6. HANDLE SQLEXCEPTION PROPERLY
   ✓ DO:
       catch (SQLException e) {
           System.err.println("Error: " + e.getMessage());
           System.err.println("SQL State: " + e.getSQLState());
           System.err.println("Error Code: " + e.getErrorCode());
           // Log exception
           // Retry or recover gracefully
       }
   
   ✗ DON'T:
       catch (SQLException e) {
           e.printStackTrace();  // Insufficient
       }

─────────────────────────────────────────────────────────────────

7. USE TRANSACTIONS FOR CONSISTENCY
   ✓ DO:
       conn.setAutoCommit(false);
       try {
           stmt.executeUpdate("...");
           stmt.executeUpdate("...");
           conn.commit();  // Both succeed or both fail
       } catch (SQLException e) {
           conn.rollback();
       }
   
   ✗ DON'T:
       Leave auto-commit on for multi-statement operations

─────────────────────────────────────────────────────────────────

8. SET APPROPRIATE TIMEOUTS
   ✓ DO:
       stmt.setQueryTimeout(30);  // 30 seconds per query
       // Prevents hanging on slow/blocked queries
   
   ✗ DON'T:
       Leave query timeout at 0 (infinite)

─────────────────────────────────────────────────────────────────

9. BATCH OPERATIONS FOR BULK INSERTS
   ✓ DO:
       PreparedStatement pstmt = conn.prepareStatement(sql);
       for (int i = 0; i < 10000; i++) {
           pstmt.setInt(1, i);
           pstmt.addBatch();
       }
       pstmt.executeBatch();  // Execute all at once
   
   ✗ DON'T:
       Loop with individual executeUpdate() calls
       // 10000 network round-trips is too slow

─────────────────────────────────────────────────────────────────

10. AVOID CONNECTION LEAKS
    ✓ DO:
        Use try-with-resources exclusively
        Monitor pool for max connections
        Set idleTimeout on pool
    
    ✗ DON'T:
        Create connections in loop without closing
        Catch exception and continue (skip close)
```

---

## Important Notes

### ✓ Key Concepts

1. **Connection is a Session**
   - Represents single database session
   - Maintains state (auto-commit, isolation level, etc.)
   - Expensive resource (time and memory)

2. **Statement is a Command**
   - Single SQL execution unit
   - Lightweight compared to Connection
   - Should be closed after use

3. **PreparedStatement is Compiled**
   - Pre-compiled by database
   - Better for repeated execution
   - Prevents SQL injection

4. **Resource Cleanup is Critical**
   - Leaking connections exhausts resources
   - Use try-with-resources always
   - REVERSE order of closure

5. **Connection Pooling is Essential**
   - Improves performance dramatically
   - Reduces connection creation overhead
   - Standard in all production apps

6. **Transactions Ensure Consistency**
   - Use setAutoCommit(false) for multi-op
   - Commit on success, rollback on error
   - Prevents partial updates

### ⚠ Common Mistakes

- Not closing resources (leaks)
- Closing in wrong order (crashes)
- Using Statement instead of PreparedStatement (injection risk)
- Not setting timeouts (hangs)
- Mixing connection pooling with DriverManager
- Ignoring SQLWarnings
- Not validating connection before use
- Creating too many connections

---
