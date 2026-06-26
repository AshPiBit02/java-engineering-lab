# Life Cycle of Web Servlets

---

## Table of Contents
1. [Servlet Lifecycle Phases](#servlet-lifecycle-phases)
2. [Lifecycle Methods](#lifecycle-methods)
3. [Initialization Phase](#initialization-phase)
4. [Service Phase](#service-phase)
5. [Destruction Phase](#destruction-phase)
6. [Class Hierarchy](#class-hierarchy)
7. [Working Sequence](#working-sequence)
8. [Code Examples](#code-examples)
9. [Important Notes](#important-notes)

---

## Servlet Lifecycle Phases

```
┌──────────────────────────────────────────────────────┐
│  1. LOADING                                          │
│  Servlet class loaded by ClassLoader                 │
└──────────────────────────────────────────────────────┘
                      ↓
┌──────────────────────────────────────────────────────┐
│  2. INSTANTIATION                                    │
│  Servlet instance created via new                    │
└──────────────────────────────────────────────────────┘
                      ↓
┌──────────────────────────────────────────────────────┐
│  3. INITIALIZATION (Called ONCE)                     │
│  init() method invoked                               │
│  Load resources, initialize database connections     │
└──────────────────────────────────────────────────────┘
                      ↓
┌──────────────────────────────────────────────────────┐
│  4. SERVICE (Called MULTIPLE TIMES)                  │
│  service() → doGet()/doPost()/doPut()/doDelete()     │
│  One instance handles multiple concurrent requests   │
└──────────────────────────────────────────────────────┘
                      ↓
┌──────────────────────────────────────────────────────┐
│  5. DESTRUCTION (Called ONCE)                        │
│  destroy() method invoked                            │
│  Release resources, close connections, cleanup       │
└──────────────────────────────────────────────────────┘
```

### Key Points
- **Single Instance:** One servlet instance serves all requests
- **Multithreaded:** Each request runs in separate thread
- **init() & destroy():** Called once in lifetime
- **service():** Called for every request

---

## Lifecycle Methods

| Method | Called | Frequency | Purpose |
|--------|--------|-----------|---------|
| **init(ServletConfig config)** | Startup | Once | Initialize servlet, load resources |
| **service(ServletRequest req, ServletResponse res)** | Request | Multiple | Determine HTTP method, dispatch to doXxx() |
| **doGet(HttpServletRequest, HttpServletResponse)** | GET request | Multiple | Handle GET requests |
| **doPost(HttpServletRequest, HttpServletResponse)** | POST request | Multiple | Handle POST requests |
| **doPut(HttpServletRequest, HttpServletResponse)** | PUT request | Multiple | Handle PUT requests |
| **doDelete(HttpServletRequest, HttpServletResponse)** | DELETE request | Multiple | Handle DELETE requests |
| **destroy()** | Shutdown | Once | Clean up resources, close connections |

---

## Initialization Phase

### Purpose
- Load configuration
- Establish database connections
- Initialize resource pools
- Prepare reusable objects

### Method Signature
```java
public void init(ServletConfig config) throws ServletException {
    super.init(config);  // Always call parent init()
    // Initialization code
}
```

### Characteristics
- Called **exactly once** per servlet instance
- Called **before first request**
- Runs in **single thread** (safe for initialization)
- Should be fast to improve server startup time

### Parameters
- **ServletConfig:** Contains servlet initialization parameters and references
- Accessed via `getServletConfig()` after initialization

---

## Service Phase

### Purpose
- Process client requests
- Dispatch to appropriate HTTP method handler
- Generate and send response

### Method Hierarchy
```
service(ServletRequest, ServletResponse)
    ↓
Casts to HttpServletRequest/HttpServletResponse
    ↓
Reads request method (GET, POST, PUT, etc.)
    ↓
Calls appropriate doXxx() method:
├─ GET  → doGet()
├─ POST → doPost()
├─ PUT  → doPut()
└─ DELETE → doDelete()
```

### Key Characteristics
- Called **multiple times** (once per request)
- Each request **in separate thread** from thread pool
- **Concurrent:** Multiple threads can execute simultaneously
- Instance variables must be **thread-safe**

### Threading Model
```
Thread Pool
├─ Thread-1 → Request-A (doGet)
├─ Thread-2 → Request-B (doPost)  [Concurrent]
├─ Thread-3 → Request-C (doDelete)
└─ Thread-4 → Idle
```

---

## Destruction Phase

### Purpose
- Close database connections
- Release file handles
- Stop background threads
- Free memory resources

### Method Signature
```java
public void destroy() {
    // Cleanup code
}
```

### Triggered By
- Server shutdown
- Application undeployment
- Servlet reloading

### Characteristics
- Called **exactly once** in servlet lifetime
- No new requests accepted after destroy() called
- Should wait for pending requests to complete
- Should be fast (server may timeout)

---

## Class Hierarchy

```
java.lang.Object
    ↓
GenericServlet (implements Servlet, ServletConfig)
    │
    ├─ Methods: init(), destroy(), getServletConfig(), getServletName()
    ├─ Methods: getInitParameter(), getInitParameterNames()
    └─ Abstract method: service()
    │
    ↓
HttpServlet (extends GenericServlet)
    │
    ├─ Methods: service() (implements HTTP routing)
    ├─ Methods: doGet(), doPost(), doPut(), doDelete(), doHead()
    └─ Methods: doOptions(), doTrace()
    │
    ↓
Your Custom Servlet
    │
    └─ Override: init(), doGet(), doPost(), destroy()
```

### Interface: Servlet
```java
interface Servlet {
    void init(ServletConfig config) throws ServletException;
    ServletConfig getServletConfig();
    void service(ServletRequest req, ServletResponse res) 
        throws ServletException, IOException;
    String getServletInfo();
    void destroy();
}
```

---

## Working Sequence

### Timeline Diagram

```
TIME
  │
  ├─ T0: Server starts
  │   Reads web.xml
  │
  ├─ T1: First request arrives
  │   ClassLoader loads MyServlet.class
  │   Creates instance: MyServlet servlet = new MyServlet()
  │   Calls: servlet.init(config)  ← Initialization
  │
  ├─ T2: servlet.service(request, response)  ← Service
  │   Identifies GET method
  │   Calls: servlet.doGet(request, response)
  │   Generates response
  │
  ├─ T3: Second concurrent request
  │   Uses SAME servlet instance (different thread)
  │   Calls: servlet.service(request2, response2)
  │
  ├─ T4: More requests...
  │   All use same instance
  │   All in separate threads
  │
  ├─ T5: Server shutdown
  │   Calls: servlet.destroy()  ← Destruction
  │   Resources released
  │
  └─ T6: Servlet instance eligible for garbage collection
```

---

## Code Examples

### Example 1: Complete Servlet Lifecycle

```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class LifecycleServlet extends HttpServlet {
    
    private int requestCount = 0;  // CAUTION: Not thread-safe!
    private java.sql.Connection dbConnection;
    
    // 1. INITIALIZATION
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);  // Always call parent
        
        System.out.println("✓ init() called - Servlet initializing...");
        
        // Load configuration parameters
        String dbUrl = config.getInitParameter("db.url");
        String dbUser = config.getInitParameter("db.user");
        
        try {
            // Establish database connection (done once)
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = java.sql.DriverManager.getConnection(dbUrl, dbUser, "");
            System.out.println("✓ Database connected");
        } catch (Exception e) {
            throw new ServletException("DB init failed", e);
        }
    }
    
    // 2. SERVICE - Handle GET
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        synchronized(this) {
            requestCount++;  // Unsafe outside synchronized block
        }
        
        out.println("<html><body>");
        out.println("<h2>Lifecycle Demonstration</h2>");
        out.println("<p>Total requests handled: " + requestCount + "</p>");
        out.println("<p>Servlet initialized once, serving many requests</p>");
        out.println("</body></html>");
    }
    
    // 3. SERVICE - Handle POST
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>POST received</h2>");
        out.println("<p>Processed by same servlet instance</p>");
        out.println("</body></html>");
    }
    
    // 4. DESTRUCTION
    @Override
    public void destroy() {
        System.out.println("✗ destroy() called - Servlet shutting down...");
        
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
                System.out.println("✗ Database connection closed");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### Example 2: Thread-Safe Request Counter

```java
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeServlet extends HttpServlet {
    
    // CORRECT: Use AtomicInteger for thread-safe counting
    private AtomicInteger requestCount = new AtomicInteger(0);
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        int currentCount = requestCount.incrementAndGet();
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>Request #" + currentCount + "</p>");
        out.close();
    }
}
```

### Example 3: Using ServletConfig in init()

```java
public class ConfigServlet extends HttpServlet {
    
    private String appName;
    private String version;
    private String supportEmail;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        // Read init parameters from web.xml
        appName = config.getInitParameter("app.name");
        version = config.getInitParameter("app.version");
        supportEmail = config.getInitParameter("support.email");
        
        // Validate required parameters
        if (appName == null || appName.isEmpty()) {
            throw new ServletException("Missing required: app.name");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h1>" + appName + " v" + version + "</h1>");
        out.println("<p>Support: " + supportEmail + "</p>");
        out.close();
    }
}
```

### web.xml Configuration for Above Example

```xml
<servlet>
    <servlet-name>ConfigServlet</servlet-name>
    <servlet-class>com.example.ConfigServlet</servlet-class>
    
    <init-param>
        <param-name>app.name</param-name>
        <param-value>MyApplication</param-value>
    </init-param>
    
    <init-param>
        <param-name>app.version</param-name>
        <param-value>2.1.0</param-value>
    </init-param>
    
    <init-param>
        <param-name>support.email</param-name>
        <param-value>support@example.com</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>ConfigServlet</servlet-name>
    <url-pattern>/config</url-pattern>
</servlet-mapping>
```

### Example 4: Resource Pool Management

```java
public class PoolServlet extends HttpServlet {
    
    private ConnectionPool pool;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        // Initialize connection pool (done once for all requests)
        int poolSize = Integer.parseInt(
            config.getInitParameter("pool.size")
        );
        
        pool = new ConnectionPool("jdbc:mysql://localhost/mydb", poolSize);
        System.out.println("Connection pool initialized with " + 
                          poolSize + " connections");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get connection from pool (reused across requests)
        Connection conn = null;
        try {
            conn = pool.getConnection();  // Efficient: no need to create new
            
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            
            // Process results
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<table>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("name") + "</td></tr>");
            }
            out.println("</table>");
            
        } finally {
            if (conn != null) {
                pool.returnConnection(conn);  // Return to pool
            }
        }
    }
    
    @Override
    public void destroy() {
        // Shutdown pool (done once when servlet destroyed)
        if (pool != null) {
            pool.shutdown();
            System.out.println("Connection pool shut down");
        }
    }
}
```

---

## Important Notes

### 1. **init() Called Only Once**
```java
// Not efficient:
@Override
protected void doGet(HttpServletRequest request, 
                    HttpServletResponse response) {
    // DON'T do this - happens for every request
    loadDatabaseDriver();
    establishConnection();
}

// Efficient:
@Override
public void init(ServletConfig config) throws ServletException {
    // DO this - happens once
    super.init(config);
    loadDatabaseDriver();
    establishConnection();
}
```

### 2. **Shared Instance, Multiple Threads**
```
Memory Layout:
┌─────────────────────────────────────┐
│  Single Servlet Instance            │
│  ┌────────────────────────────────┐ │
│  │ Instance Variables (SHARED)    │ │
│  │ private int count = 0;         │ │ ← NOT THREAD-SAFE
│  └────────────────────────────────┘ │
│                                     │
│  doGet() method code                │
└─────────────────────────────────────┘
         ↑          ↑          ↑
      Thread-1   Thread-2   Thread-3
      (Request-A)(Request-B)(Request-C)
```

**Thread-safety strategies:**
- Use synchronized blocks
- Use AtomicInteger/AtomicLong
- Use ThreadLocal
- Avoid modifying instance variables

### 3. **destroy() May Not Be Called**
```java
// Not guaranteed to execute:
- Server crash
- Forced shutdown
- Browser closes connection mid-request

// Best practice: Use try-finally or try-with-resources
@Override
public void destroy() {
    // This runs, but might not if VM crashes
}
```

### 4. **Always Call super.init()**
```java
// WRONG - ServletConfig not properly initialized
@Override
public void init(ServletConfig config) throws ServletException {
    appName = config.getInitParameter("app.name");
}

// CORRECT
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);  // Must call parent
    appName = config.getInitParameter("app.name");
}
```

### 5. **Servlet Instance Reuse**
```
Request 1 at 08:00:05  → Thread-1 executes doGet() in Instance-A
Request 2 at 08:00:06  → Thread-2 executes doPost() in Instance-A  (same!)
Request 3 at 08:00:07  → Thread-3 executes doGet() in Instance-A   (same!)

NOT:
Request 1 → Instance-A created, doGet() executed
Request 2 → Instance-B created, doPost() executed  ← Wrong!
Request 3 → Instance-C created, doGet() executed   ← Wrong!
```

### 6. **Initialization Parameters vs Runtime Parameters**
```java
// Initialization Parameters (from web.xml, set once at startup)
String dbUrl = config.getInitParameter("db.url");

// Runtime Parameters (from request, vary per request)
String username = request.getParameter("username");
```

### 7. **Exception During init()**
```java
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    try {
        // If this throws exception:
        loadCriticalResource();
    } catch (Exception e) {
        // Wrap and throw ServletException
        throw new ServletException("Failed to initialize", e);
    }
    // If ServletException thrown:
    // - Servlet marked as unavailable
    // - No requests will be routed to it
    // - Server returns 503 error
}
```

### 8. **Life Cycle Timeline**
```
Load     Instantiate   init()   [Service] × Many   destroy()
  │          │          │            │              │
  ├─────────┼──────────┼────────────┘──────────────┤
  0ms       1ms        5ms            Lifetime       Shutdown
            
  Once      Once       Once        Per Request     Once
```

---
