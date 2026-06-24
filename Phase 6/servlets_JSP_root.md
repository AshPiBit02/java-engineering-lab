# Phase 6: Servlets and JSP - Root Overview

## Introduction

**Servlets and JSP (JavaServer Pages)** are the foundation of Java-based web application development. They enable developers to build dynamic, server-side web applications that respond to client requests, process data, and return dynamic content.

### What is a Servlet?

A **Servlet** is a Java class that extends server functionality and responds to requests, typically from web clients (browsers). Servlets are:
- Server-side components (run on application server, not browser)
- Request-response handlers (receive HTTP requests, send HTTP responses)
- Stateless (each request is independent)
- Thread-based (server creates thread for each request)
- Deployed in containers (Apache Tomcat, JBoss, Jetty, etc.)

### What is JSP (JavaServer Pages)?

**JSP** is a technology that allows you to write Java code mixed with HTML. JSPs are compiled into servlets at runtime, providing:
- Template-based dynamic content generation
- Simplified syntax for web developers
- Reusable components (includes, taglibs)
- Session and state management
- Direct access to servlet APIs

---

## Architecture Overview

### Fig. 1: Servlet/JSP Architecture in Web Applications

```
┌─────────────────────────────────────────────────────────────────┐
│                        WEB BROWSER (CLIENT)                     │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ - User Interface (HTML/CSS/JavaScript)                   │   │
│  │ - Sends HTTP requests to server                          │   │
│  │ - Receives & displays responses                          │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                          │
                          │ HTTP Request
                          │ (GET/POST with data)
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                  NETWORK (TCP/IP over port 80/443)              │
└─────────────────────────────────────────────────────────────────┘
                          │
                          │
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│              WEB APPLICATION SERVER                             │
│                (Tomcat, JBoss, Jetty, etc.)                     │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ HTTP LISTENER (Port 8080/8888/etc.)                      │   │
│  │ - Receives HTTP requests                                 │   │
│  │ - Routes to appropriate servlet/JSP                      │   │
│  └──────────────────────────────────────────────────────────┘   │
│           │                                                     │
│           ▼                                                     │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ SERVLET CONTAINER                                        │   │
│  │ - Manages servlet lifecycle                              │   │
│  │ - Thread management                                      │   │
│  │ - Request dispatching                                    │   │
│  │ - Response handling                                      │   │
│  │                                                          │   │
│  │ ┌────────────────────────────────────────────────────┐   │   │
│  │ │ DEPLOYED APPLICATIONS                              │   │   │
│  │ │                                                    │   │   │
│  │ │ ├─ Servlet Classes                                 │   │   │
│  │ │ │  ├─ LoginServlet.java                            │   │   │
│  │ │ │  ├─ UserServlet.java                             │   │   │
│  │ │ │  └─ ProcessFormServlet.java                      │   │   │
│  │ │ │                                                  │   │   │
│  │ │ ├─ JSP Pages (compiled to servlets)                │   │   │
│  │ │ │  ├─ index.jsp → index_jsp.class                  │   │   │
│  │ │ │  ├─ login.jsp → login_jsp.class                  │   │   │
│  │ │ │  └─ dashboard.jsp → dashboard_jsp.class          │   │   │
│  │ │ │                                                  │   │   │
│  │ │ ├─ Static Resources                                │   │   │
│  │ │ │  ├─ HTML files                                   │   │   │
│  │ │ │  ├─ CSS stylesheets                              │   │   │
│  │ │ │  ├─ JavaScript files                             │   │   │
│  │ │ │  └─ Images                                       │   │   │
│  │ │ │                                                  │   │   │
│  │ │ └─ Configuration Files                             │   │   │
│  │ │    ├─ web.xml (deployment descriptor)              │   │   │
│  │ │    └─ context configuration                        │   │   │
│  │ │                                                    │   │   │
│  │ └────────────────────────────────────────────────────┘   │   │
│  │                                                          │   │
│  │ ┌────────────────────────────────────────────────────┐   │   │
│  │ │ REQUEST HANDLING FLOW                              │   │   │
│  │ │                                                    │   │   │
│  │ │ 1. HTTP Request arrives                            │   │   │
│  │ │ 2. Container parses request                        │   │   │
│  │ │ 3. Request dispatched to servlet/JSP               │   │   │
│  │ │ 4. Servlet processes request                       │   │   │
│  │ │    - Extract parameters                            │   │   │
│  │ │    - Query database                                │   │   │
│  │ │    - Business logic                                │   │   │
│  │ │ 5. Generate response (HTML/JSON/XML)               │   │   │
│  │ │ 6. Send response to client                         │   │   │
│  │ │                                                    │   │   │
│  │ └────────────────────────────────────────────────────┘   │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                          │
                          │ HTTP Response
                          │ (HTML/JSON/XML content)
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                        WEB BROWSER (CLIENT)                     │
│  - Receives response                                            │
│  - Renders HTML                                                 │
│  - Executes JavaScript                                          │
│  - Displays to user                                             │
└─────────────────────────────────────────────────────────────────┘
```

---

## Key Concepts

### Fig. 2: Servlet vs JSP Comparison

```
SERVLET (Java Class)
═══════════════════════════════════════════════════════════════

Code:
    public class LoginServlet extends HttpServlet {
        protected void doPost(HttpServletRequest req, 
                             HttpServletResponse res) 
            throws ServletException, IOException {
            
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            
            // Validate credentials
            if (authenticate(username, password)) {
                res.sendRedirect("dashboard.jsp");
            } else {
                res.sendRedirect("login.jsp?error=invalid");
            }
        }
    }

Characteristics:
├─ Pure Java code
├─ More control & flexibility
├─ Better for complex logic
├─ Business logic layer
├─ No HTML mixing
├─ Reusable across applications
└─ Suitable for: Authentication, form processing, API endpoints

─────────────────────────────────────────────────────────────────

JSP (Java + HTML Template)
═══════════════════════════════════════════════════════════════

Code:
    <%@ page import="java.util.List" %>
    
    <html>
    <head><title>User Dashboard</title></head>
    <body>
        <%
            String username = (String) session.getAttribute("username");
            List<User> users = UserDAO.getAllUsers();
        %>
        
        <h1>Welcome, <%= username %></h1>
        <table>
            <% for (User user : users) { %>
                <tr>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                </tr>
            <% } %>
        </table>
    </body>
    </html>

Characteristics:
├─ HTML + Java mix
├─ Simpler syntax for web developers
├─ Better for presentation layer
├─ Compiled to servlet at runtime
├─ Focus on UI/display logic
├─ Easy dynamic content generation
└─ Suitable for: Web pages, templates, dynamic content

─────────────────────────────────────────────────────────────────

COMPARISON TABLE
═════════════════════════════════════════════════════════════════

Aspect              │ Servlet              │ JSP
────────────────────┼──────────────────────┼──────────────────
Syntax              │ Pure Java            │ HTML + Java
Use Case            │ Logic, processing    │ Presentation
Learning Curve      │ Steeper              │ Easier
Code Organization   │ Clean separation     │ Mixed
Reusability         │ High                 │ Lower
Performance         │ Native (fast)        │ Compiled to servlet
Debugging           │ Easier (IDE support) │ Harder
Team Preference     │ Backend devs         │ Web/UI devs
Best For            │ Controller layer     │ View layer
Complexity          │ Handles complex ops  │ Simple display logic
Scalability         │ Better for large     │ Better for quick pages
                    │ applications         │
```

---

## Request-Response Cycle

### Fig. 3: Complete HTTP Request-Response Flow

```
STEP 1: USER ACTION
═══════════════════════════════════════════════════════════════

Browser:
    User clicks link or submits form
    ┌─────────────────────────────────┐
    │ <a href="/login.jsp">Login</a>  │
    └─────────────────────────────────┘
                │
                │ User clicks
                ▼
    Browser constructs HTTP request:
    ┌─────────────────────────────────┐
    │ GET /webapp/login.jsp HTTP/1.1  │
    │ Host: localhost:8080            │
    │ Cookie: sessionId=abc123        │
    │ User-Agent: Mozilla/5.0         │
    └─────────────────────────────────┘

─────────────────────────────────────────────────────────────────

STEP 2: REQUEST TRANSMISSION
═══════════════════════════════════════════════════════════════

Browser → Network → Web Server
    - HTTP travels over TCP/IP
    - Arrives at server port (8080, 8888, etc.)
    - Server receives complete request

─────────────────────────────────────────────────────────────────

STEP 3: SERVER PROCESSING
═══════════════════════════════════════════════════════════════

Tomcat Container:
    1. Parse HTTP request
       ├─ Extract method (GET/POST)
       ├─ Extract path (/webapp/login.jsp)
       ├─ Extract parameters & headers
       └─ Build ServletRequest object
    
    2. Create ServletRequest & ServletResponse
       ├─ HttpServletRequest req = ...
       └─ HttpServletResponse res = ...
    
    3. Match request to servlet/JSP
       ├─ Check URL mapping in web.xml
       ├─ Or use annotation @WebServlet("/path")
       └─ Find correct handler
    
    4. Create/Retrieve servlet instance
       ├─ Check servlet pool
       ├─ If not exists: instantiate
       └─ If exists: reuse
    
    5. Call servlet method
       ├─ GET request → doGet(req, res)
       ├─ POST request → doPost(req, res)
       └─ Other → doHead(), doPut(), etc.
    
    6. Servlet processes
       ├─ Extract parameters: req.getParameter("name")
       ├─ Access session: req.getSession()
       ├─ Query database: DAO.getUser()
       ├─ Perform business logic
       └─ Generate response content
    
    7. Servlet writes response
       ├─ Set content type: res.setContentType("text/html")
       ├─ Get output stream: PrintWriter out = res.getWriter()
       ├─ Write HTML/JSON: out.println("<html>...")
       └─ Flush output

─────────────────────────────────────────────────────────────────

STEP 4: RESPONSE GENERATION
═══════════════════════════════════════════════════════════════

Server generates response:
    ┌──────────────────────────────────┐
    │ HTTP/1.1 200 OK                  │
    │ Content-Type: text/html          │
    │ Set-Cookie: sessionId=xyz789     │
    │ Content-Length: 2048             │
    │                                  │
    │ <html>                           │
    │   <body>                         │
    │     <h1>Login Page</h1>          │
    │     <form method="post">...      │
    │   </body>                        │
    │ </html>                          │
    └──────────────────────────────────┘

─────────────────────────────────────────────────────────────────

STEP 5: RESPONSE TRANSMISSION
═══════════════════════════════════════════════════════════════

Server → Network → Browser
    - HTTP response transmitted
    - Browser receives complete response
    - Connection may close or remain open

─────────────────────────────────────────────────────────────────

STEP 6: BROWSER RENDERING
═══════════════════════════════════════════════════════════════

Browser:
    1. Parse HTML
    2. Apply CSS styles
    3. Execute JavaScript
    4. Store cookies (Set-Cookie header)
    5. Render page to user
    6. Ready for next action

User sees: Login page with form
```

---

## HTTP Fundamentals

### Fig. 4: HTTP Methods Used in Web Applications

```
HTTP METHODS (Verbs)
═══════════════════════════════════════════════════════════════

GET (Safe, Idempotent)
├─ Purpose: Retrieve data
├─ Parameters: Query string in URL
├─ Example: GET /users?id=5 HTTP/1.1
├─ Use: Search, filtering, pagination
├─ Caching: YES (browser caches)
├─ Bookmarkable: YES
└─ Secure: NO (data visible in URL)
    
    Code:
    <a href="/users?id=5">View User</a>
    
    Servlet:
    protected void doGet(HttpServletRequest req, ...) {
        String userId = req.getParameter("id");
    }

─────────────────────────────────────────────────────────────────

POST (Unsafe, Non-idempotent)
├─ Purpose: Submit data to server
├─ Parameters: Request body
├─ Example: POST /login HTTP/1.1
│           Body: username=john&password=secret
├─ Use: Form submission, file upload, sensitive data
├─ Caching: NO (not cached)
├─ Bookmarkable: NO
└─ Secure: BETTER (data not in URL, use HTTPS)
    
    Code:
    <form method="post" action="/login">
        <input name="username" />
        <input name="password" type="password" />
    </form>
    
    Servlet:
    protected void doPost(HttpServletRequest req, ...) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
    }

─────────────────────────────────────────────────────────────────

HEAD (Safe, Idempotent)
├─ Purpose: Get headers only (no body)
├─ Use: Check resource existence, size
├─ Less common in web apps
└─ Rarely overridden

─────────────────────────────────────────────────────────────────

PUT (Unsafe, Idempotent)
├─ Purpose: Replace entire resource
├─ Use: REST APIs
├─ Not used in traditional web forms
└─ Example: PUT /users/5 HTTP/1.1

─────────────────────────────────────────────────────────────────

DELETE (Unsafe, Idempotent)
├─ Purpose: Delete resource
├─ Use: REST APIs
├─ Not used in HTML forms (would need AJAX)
└─ Example: DELETE /users/5 HTTP/1.1

─────────────────────────────────────────────────────────────────

PATCH (Unsafe, Non-idempotent)
├─ Purpose: Partial update
├─ Use: REST APIs
└─ Example: PATCH /users/5 HTTP/1.1
```

---

## Phase 6 Chapter Overview

### Chapter Breakdown

| Chapter | Title | Focus |
|---------|-------|-------|
| **6.1** | Overview of Web Application | Web app architecture, client-server model, HTTP basics |
| **6.2** | HTTP Methods and Responses | GET/POST, status codes, headers, response types |
| **6.3** | Life Cycle of Web Servlets | init(), service(), destroy(), request handling |
| **6.4** | Writing Servlet programs with Servlet APIs | HttpServlet, HttpServletRequest, HttpServletResponse |
| **6.5** | Reading and Processing Forms | Form parameters, parameter extraction, validation |
| **6.6** | Handling GET/POST Requests | doGet() vs doPost(), form submission handling |
| **6.7** | Database connectivity through servlets | JDBC in servlets, DAO pattern, query execution |
| **6.8** | Cookies and Sessions | State management, session attributes, cookies |

---

## Technology Stack

### Fig. 5: Typical Java Web Application Stack

```
PRESENTATION LAYER (Client-Side)
═══════════════════════════════════════════════════════════════
- HTML (structure)
- CSS (styling)
- JavaScript (interactivity)
- Run in: Web Browser (Chrome, Firefox, Safari, Edge)

─────────────────────────────────────────────────────────────────

WEB/VIEW LAYER (Server-Side)
═══════════════════════════════════════════════════════════════
- JSP (dynamic templates)
- Servlet (request handlers)
- Taglibs (JSTL, custom tags)
- Run in: Application Server (Tomcat, JBoss)

─────────────────────────────────────────────────────────────────

BUSINESS LOGIC LAYER
═══════════════════════════════════════════════════════════════
- Java classes (business rules)
- Service classes (processing)
- Validation (data checks)
- Servlet controllers (request routing)
- Run in: Application Server JVM

─────────────────────────────────────────────────────────────────

DATA ACCESS LAYER
═══════════════════════════════════════════════════════════════
- DAO (Data Access Objects)
- JDBC (database connectivity)
- SQL queries/statements
- Connection pooling
- Run in: Application Server + Database Server

─────────────────────────────────────────────────────────────────

DATABASE LAYER
═══════════════════════════════════════════════════════════════
- MySQL, PostgreSQL, Oracle, SQL Server
- Tables, indexes, constraints
- Stored procedures
- Run in: Database Server
```

---

## Common Web Application Scenarios

### Scenario 1: User Login Flow

```
USER LOGIN PROCESS
═══════════════════════════════════════════════════════════════

1. Browser loads login.jsp
   ├─ Server returns login form HTML
   └─ User sees username/password fields

2. User enters credentials & clicks Login

3. Browser sends POST request to LoginServlet
   ├─ Body: username=john&password=secret

4. LoginServlet.doPost() processes:
   ├─ Extract username & password
   ├─ Query database: SELECT * FROM users WHERE username=?
   ├─ Validate password hash
   │
   ├─ If valid:
   │  ├─ Create session: req.getSession().setAttribute("user", user)
   │  ├─ Redirect: res.sendRedirect("dashboard.jsp")
   │  └─ User logged in
   │
   └─ If invalid:
      ├─ Redirect: res.sendRedirect("login.jsp?error=invalid")
      └─ User sees error message

5. Browser follows redirect to dashboard.jsp

6. dashboard.jsp checks session
   ├─ if (session.getAttribute("user") != null) {
   │    // Show dashboard
   └─ else redirect to login.jsp
```

### Scenario 2: Form Submission with Database

```
FORM SUBMISSION FLOW
═══════════════════════════════════════════════════════════════

1. User views form (new_user.jsp)
   ├─ Form fields: name, email, department

2. User fills form & submits

3. Browser POST to CreateUserServlet

4. Servlet processes:
   ├─ Extract parameters:
   │  ├─ name = req.getParameter("name")
   │  ├─ email = req.getParameter("email")
   │  └─ dept = req.getParameter("department")
   │
   ├─ Validate data
   │  ├─ Check: name not empty
   │  ├─ Check: email format valid
   │  └─ Check: department exists
   │
   ├─ If valid:
   │  ├─ Create connection: Connection conn = ds.getConnection()
   │  ├─ Prepare insert: INSERT INTO users (name, email, dept)
   │  ├─ Execute: stmt.executeUpdate()
   │  ├─ Close resources: stmt.close(), conn.close()
   │  ├─ Set message: req.setAttribute("success", "User created!")
   │  └─ Forward: req.getRequestDispatcher("success.jsp").forward(req, res)
   │
   └─ If invalid:
      ├─ Set error: req.setAttribute("error", "Invalid email")
      └─ Forward: req.getRequestDispatcher("new_user.jsp").forward(req, res)

5. success.jsp displays confirmation

6. User can continue
```

---

## Key Technologies & Standards

| Technology | Purpose | Used In |
|---|---|---|
| **Servlet API** | Core interface for request handling | All servlets |
| **JSP** | Server-side templating | Dynamic web pages |
| **JSTL** | Tag library for common tasks | JSP pages |
| **Session API** | User state management | Tracking logged-in users |
| **Cookies** | Client-side state storage | Remember preferences |
| **JDBC** | Database connectivity | Data access |
| **web.xml** | Deployment descriptor | Configuration & routing |
| **Annotations** | Configuration metadata | @WebServlet, @WebListener |
| **Filters** | Request/response processing | Logging, authentication |
| **Listeners** | Event handling | Session events |

---

## Important Concepts Summary

### ✓ Key Points

1. **Servlets are Controllers**
   - Handle requests
   - Coordinate business logic
   - Route to views

2. **JSP are Views**
   - Display data
   - User interface
   - Template-based

3. **HTTP is Stateless**
   - Each request is independent
   - Use sessions/cookies for state
   - Server doesn't know previous requests

4. **Threading Model**
   - One servlet instance, multiple threads
   - Must be thread-safe
   - Container manages thread pool

5. **Lifecycle Matters**
   - init(): Called once at startup
   - service(): Called for each request
   - destroy(): Called at shutdown

6. **Request/Response Pattern**
   - Request: GET/POST with parameters
   - Response: HTML/JSON/XML content
   - Container manages lifecycle

### ⚠ Common Mistakes

- Storing request data in servlet fields (thread safety issue)
- Not closing database connections
- Mixing business logic and presentation
- Not validating user input
- Storing sensitive data in cookies
- Creating servlet instances manually
- Not understanding request scope vs session scope

---

**End of Phase 6 Root Overview - Servlets and JSP**

---

### Ready for Specific Chapters:
- `generate 6.1` → Overview of Web Application
- `generate 6.2` → HTTP Methods and Responses
- `generate 6.3` → Life Cycle of Web Servlets
- `generate 6.4` → Writing Servlet programs with Servlet APIs
- `generate 6.5` → Reading and Processing Forms
- `generate 6.6` → Handling GET/POST Requests
- `generate 6.7` → Database connectivity through servlets
- `generate 6.8` → Cookies and Sessions