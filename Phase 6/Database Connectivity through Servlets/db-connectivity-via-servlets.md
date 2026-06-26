# Database Connectivity through Servlets

---

## Table of Contents
1. [JDBC Overview](#jdbc-overview)
2. [JDBC Architecture](#jdbc-architecture)
3. [Database Connection Methods](#database-connection-methods)
4. [JDBC Core Classes and Methods](#jdbc-core-classes-and-methods)
5. [CRUD Operations](#crud-operations)
6. [Connection Pooling](#connection-pooling)
7. [Resource Management](#resource-management)
8. [Common Exceptions](#common-exceptions)
9. [Code Examples](#code-examples)
10. [Important Notes](#important-notes)

---

## JDBC Overview

### Purpose
- **JDBC** = Java Database Connectivity
- Bridge between Java applications and relational databases
- Standard API for database operations
- Supports all major databases (MySQL, PostgreSQL, Oracle, SQL Server, etc.)

### Key Benefits
- Database independence (switch databases with driver change)
- Standard interface for CRUD operations
- Connection management and pooling
- Transaction support
- Exception handling

---

## JDBC Architecture

### Component Hierarchy

```
┌─────────────────────────────────────────────┐
│  Java Application (Servlet)                 │
│  ├─ DriverManager / DataSource              │
│  └─ Connection / Statement / ResultSet      │
└─────────────────────────────────────────────┘
              ↓ (JDBC API)
┌─────────────────────────────────────────────┐
│  JDBC Driver (Database-Specific)            │
│  ├─ MySQL Connector/J                       │
│  ├─ PostgreSQL Driver                       │
│  └─ Oracle JDBC Driver                      │
└─────────────────────────────────────────────┘
              ↓ (Database Protocol)
┌─────────────────────────────────────────────┐
│  Database Server                            │
│  ├─ MySQL / PostgreSQL / Oracle             │
│  └─ SQL Server / MariaDB / SQLite           │
└─────────────────────────────────────────────┘
```

### Step-by-Step Connection Flow

```
1. LOAD DRIVER
   Class.forName("com.mysql.cj.jdbc.Driver")
        ↓
2. CREATE CONNECTION
   Connection conn = DriverManager.getConnection(url, user, pass)
        ↓
3. CREATE STATEMENT
   Statement stmt = conn.createStatement()
   PreparedStatement pstmt = conn.prepareStatement(sql)
        ↓
4. EXECUTE QUERY
   ResultSet rs = stmt.executeQuery(sql)
   OR
   stmt.executeUpdate(sql)
        ↓
5. PROCESS RESULTS
   while (rs.next()) { ... }
        ↓
6. CLOSE RESOURCES (Important!)
   rs.close()
   stmt.close()
   conn.close()
```

---

## Database Connection Methods

### Method 1: DriverManager (Simple, Not Recommended for Production)

```java
// Load driver
Class.forName("com.mysql.cj.jdbc.Driver");

// Get connection (new connection each time - expensive)
String url = "jdbc:mysql://localhost:3306/mydb";
String user = "root";
String password = "password123";

Connection conn = DriverManager.getConnection(url, user, password);
```

### Method 2: DataSource with Connection Pool (Recommended)

```java
// Configure in servlet container (better)
import javax.sql.DataSource;
import javax.naming.InitialContext;

DataSource ds = (DataSource) new InitialContext()
    .lookup("java:comp/env/jdbc/mydb");

Connection conn = ds.getConnection();  // Reuses from pool
```

### Connection String Formats

| Database | Connection String |
|----------|-------------------|
| **MySQL** | `jdbc:mysql://localhost:3306/dbname` |
| **PostgreSQL** | `jdbc:postgresql://localhost:5432/dbname` |
| **Oracle** | `jdbc:oracle:thin:@localhost:1521:dbname` |
| **SQL Server** | `jdbc:sqlserver://localhost;databaseName=dbname` |
| **SQLite** | `jdbc:sqlite:path/to/database.db` |

---

## JDBC Core Classes and Methods

### Connection

| Method | Purpose | Return |
|--------|---------|--------|
| **createStatement()** | Create statement for SQL query | Statement |
| **prepareStatement(String sql)** | Create prepared statement (with ?) | PreparedStatement |
| **commit()** | Commit transaction | void |
| **rollback()** | Rollback transaction | void |
| **setAutoCommit(boolean)** | Enable/disable auto-commit | void |
| **close()** | Close connection | void |
| **isClosed()** | Check if closed | boolean |

### Statement

| Method | Purpose | Return |
|--------|---------|--------|
| **executeQuery(String sql)** | Execute SELECT query | ResultSet |
| **executeUpdate(String sql)** | Execute INSERT/UPDATE/DELETE | int (rows affected) |
| **execute(String sql)** | Execute any SQL | boolean |
| **close()** | Close statement | void |

### PreparedStatement (Extends Statement)

| Method | Purpose |
|--------|---------|
| **setString(int, String)** | Set ? parameter as String |
| **setInt(int, int)** | Set ? parameter as int |
| **setDouble(int, double)** | Set ? parameter as double |
| **setDate(int, java.sql.Date)** | Set ? parameter as Date |
| **setBoolean(int, boolean)** | Set ? parameter as boolean |
| **setObject(int, Object)** | Set ? parameter as Object |
| **executeQuery()** | Execute SELECT (no SQL param) |
| **executeUpdate()** | Execute INSERT/UPDATE/DELETE |

### ResultSet

| Method | Purpose | Return |
|--------|---------|--------|
| **next()** | Move to next row | boolean |
| **getString(String columnName)** | Get String column value | String |
| **getInt(String columnName)** | Get int column value | int |
| **getDouble(String columnName)** | Get double column value | double |
| **getBoolean(String columnName)** | Get boolean column value | boolean |
| **getDate(String columnName)** | Get Date column value | java.sql.Date |
| **getObject(String columnName)** | Get Object column value | Object |
| **close()** | Close result set | void |

---

## CRUD Operations

### Database Operation Flow

```
CREATE (INSERT)
┌──────────────────┐
│ Prepare INSERT   │
│ INSERT INTO ...  │
│ VALUES (?, ?, ?) │
└──────────────────┘
        ↓
┌──────────────────┐
│ Set parameters   │
│ setString(1, x)  │
│ setInt(2, y)     │
└──────────────────┘
        ↓
┌──────────────────┐
│ Execute update   │
│ int rows = ...   │
│ .executeUpdate() │
└──────────────────┘


READ (SELECT)
┌──────────────────┐
│ Execute query    │
│ SELECT * FROM    │
└──────────────────┘
        ↓
┌──────────────────┐
│ Process results  │
│ while(rs.next()) │
│ getData()        │
└──────────────────┘


UPDATE (UPDATE)
┌──────────────────┐
│ Prepare UPDATE   │
│ UPDATE ...       │
│ SET col = ?      │
└──────────────────┘
        ↓
┌──────────────────┐
│ Set parameters   │
│ setString(1, x)  │
└──────────────────┘
        ↓
┌──────────────────┐
│ Execute update   │
│ int rows = ...   │
└──────────────────┘


DELETE (DELETE)
┌──────────────────┐
│ Prepare DELETE   │
│ DELETE FROM      │
│ WHERE id = ?     │
└──────────────────┘
        ↓
┌──────────────────┐
│ Set parameters   │
│ setInt(1, id)    │
└──────────────────┘
        ↓
┌──────────────────┐
│ Execute update   │
│ int rows = ...   │
└──────────────────┘
```

---

## Connection Pooling

### Problem Without Pooling

```
Each Request:
┌─────────────────────────────┐
│ 1. Create new connection    │  ← Expensive (500ms)
│ 2. Execute query            │  ← Fast (10ms)
│ 3. Close connection         │  ← Overhead (100ms)
└─────────────────────────────┘
Total: 610ms per request
```

### Solution With Pooling

```
Connection Pool (10 connections):
┌──────────────────────────────────┐
│ [Conn-1] [Conn-2] [Conn-3]       │
│ [Conn-4] [Conn-5] [Conn-6]       │
│ [Conn-7] [Conn-8] [Conn-9]       │
│ [Conn-10] [Available]            │
└──────────────────────────────────┘

Each Request:
┌─────────────────────────────┐
│ 1. Get connection from pool │  ← Instant (1ms)
│ 2. Execute query            │  ← Fast (10ms)
│ 3. Return to pool           │  ← Instant (1ms)
└─────────────────────────────┘
Total: 12ms per request
```

### Pool Configuration (web.xml)

```xml
<!-- Tomcat context.xml -->
<Resource
    name="jdbc/mydb"
    auth="Container"
    type="javax.sql.DataSource"
    maxActive="20"           <!-- Max connections -->
    maxIdle="10"             <!-- Max idle connections -->
    maxWait="30000"          <!-- Wait timeout (ms) -->
    username="root"
    password="password"
    driverClassName="com.mysql.cj.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/mydb"
/>
```

---

## Resource Management

### The Problem: Resource Leaks

```java
// BAD: Connection never closed if exception occurs
Connection conn = DriverManager.getConnection(url, user, pass);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

while (rs.next()) {
    // If exception here → rs, stmt, conn never closed!
    process(rs);
}
```

### Solution 1: Try-Finally

```java
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;

try {
    conn = DriverManager.getConnection(url, user, pass);
    stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);
    
    while (rs.next()) {
        process(rs);
    }
} finally {
    if (rs != null) rs.close();
    if (stmt != null) stmt.close();
    if (conn != null) conn.close();
}
```

### Solution 2: Try-With-Resources (Java 7+, Recommended)

```java
// Auto-closes resources in reverse order
try (Connection conn = DriverManager.getConnection(url, user, pass);
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(sql)) {
    
    while (rs.next()) {
        process(rs);
    }
} catch (SQLException e) {
    e.printStackTrace();
}
```

### Closing Order

```
Open:  Connection → Statement → ResultSet
Close: ResultSet → Statement → Connection (reverse order)
```

---

## Common Exceptions

| Exception | Cause | Solution |
|-----------|-------|----------|
| **ClassNotFoundException** | Driver jar not in classpath | Add driver to lib folder |
| **SQLException** | SQL error or connection failed | Check SQL syntax, credentials, DB running |
| **NullPointerException** | Connection/statement null | Verify connection successful |
| **SQLIntegrityConstraintViolationException** | Unique/foreign key violation | Validate data before insert |
| **DataAccessException** | Data layer error | Log error, return user-friendly message |

---

## Code Examples

### Example 1: Simple SELECT Query

```java
public class UserListServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        
        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Get connection
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // Create statement
            String sql = "SELECT id, name, email FROM users";
            Statement stmt = conn.createStatement();
            
            // Execute query
            ResultSet rs = stmt.executeQuery(sql);
            
            // Process results
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th></tr>");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            
            // Close resources
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (ClassNotFoundException e) {
            out.println("<p>Driver not found: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Database error: " + e.getMessage() + "</p>");
        }
        
        out.close();
    }
}
```

### Example 2: INSERT with PreparedStatement (Secure)

```java
public class CreateUserServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Validation
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h2>Error: Name and email required</h2>");
            return;
        }
        
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // Use PreparedStatement to prevent SQL injection
            String sql = "INSERT INTO users (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, name.trim());
            pstmt.setString(2, email.trim());
            pstmt.setString(3, phone != null ? phone.trim() : null);
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("<h2>User created successfully!</h2>");
                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Email: " + email + "</p>");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<p>Driver error: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<p>Database error: " + e.getMessage() + "</p>");
        }
        
        out.close();
    }
}
```

### Example 3: UPDATE Query

```java
public class UpdateUserServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if (id == null || name == null || email == null) {
            out.println("<h2>Error: All fields required</h2>");
            return;
        }
        
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            
            String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, name.trim());
            pstmt.setString(2, email.trim());
            pstmt.setInt(3, Integer.parseInt(id));
            
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                out.println("<h2>User updated successfully!</h2>");
            } else {
                out.println("<h2>User not found</h2>");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            out.println("<p>Invalid ID format</p>");
        } catch (ClassNotFoundException e) {
            out.println("<p>Driver error</p>");
        }
        
        out.close();
    }
}
```

### Example 4: DELETE Query

```java
public class DeleteUserServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if (id == null) {
            out.println("<h2>Error: ID required</h2>");
            return;
        }
        
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                out.println("<h2>User deleted successfully!</h2>");
            } else {
                out.println("<h2>User not found</h2>");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (SQLException | NumberFormatException | ClassNotFoundException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
        
        out.close();
    }
}
```

### Example 5: Try-With-Resources (Best Practice)

```java
public class ModernDatabaseServlet extends HttpServlet {
    
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASS = "password";
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            out.println("<p>Driver error</p>");
            return;
        }
        
        // Try-with-resources: auto-closes all
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            
            out.println("<h2>Users</h2>");
            out.println("<ul>");
            
            while (rs.next()) {
                out.println("<li>" + rs.getString("name") + "</li>");
            }
            
            out.println("</ul>");
            
        } catch (SQLException e) {
            out.println("<p>Database error: " + e.getMessage() + "</p>");
        }
        
        out.close();
    }
}
```

---

## Important Notes

### 1. **Always Use PreparedStatement for User Input**
```java
// VULNERABLE: SQL Injection
String query = "SELECT * FROM users WHERE id = " + userId;
// If userId = "1 OR 1=1" → Returns all users!

// SECURE: PreparedStatement
String query = "SELECT * FROM users WHERE id = ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setInt(1, userId);  // Automatically escaped
```

### 2. **Close Resources in Finally or Try-With**
```java
// BAD: Leaks connections if exception occurs
Connection conn = DriverManager.getConnection(...);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(...);
stmt.close();  // Never reached if exception above
conn.close();

// GOOD: Try-with-resources (Java 7+)
try (Connection conn = DriverManager.getConnection(...);
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(...)) {
    // Auto-closes even if exception
}
```

### 3. **Load Driver Only Once**
```java
// INEFFICIENT: Load driver every time
for (int i = 0; i < 1000; i++) {
    Class.forName("com.mysql.cj.jdbc.Driver");  // DON'T do this!
    Connection conn = DriverManager.getConnection(...);
}

// EFFICIENT: Load once in servlet init
@Override
public void init() throws ServletException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new ServletException(e);
    }
}
```

### 4. **Use Connection Pool in Production**
```java
// Development: DriverManager (acceptable)
Connection conn = DriverManager.getConnection(url, user, pass);

// Production: Connection Pool (required)
DataSource ds = (DataSource) new InitialContext()
    .lookup("java:comp/env/jdbc/mydb");
Connection conn = ds.getConnection();
```

### 5. **Validate Data Before Database Operations**
```java
// FIRST: Validate input
if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
    return error("Invalid email");
}

// THEN: Database operation
String sql = "INSERT INTO users (email) VALUES (?)";
pstmt.setString(1, email);
pstmt.executeUpdate();
```

### 6. **Handle SQLException Properly**
```java
try {
    // Database operation
} catch (SQLException e) {
    // Log full exception for debugging
    e.printStackTrace();
    
    // Send user-friendly message
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    out.println("An error occurred. Please try again later.");
}
```

### 7. **Connection String Format**
```
jdbc:mysql://[host]:[port]/[database]
       ↓         ↓        ↓        ↓
       │      localhost  3306    mydb
       │
   Database type
   
Example:
jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC
```

### 8. **Test Connection Before Deployment**
```java
// Simple connection test
try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, user, pass);
    System.out.println("✓ Connection successful");
    conn.close();
} catch (Exception e) {
    System.out.println("✗ Connection failed: " + e.getMessage());
}
```

---
