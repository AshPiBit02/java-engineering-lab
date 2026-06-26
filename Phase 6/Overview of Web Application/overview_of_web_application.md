# 6.1 Overview of Web Application

## Table of Contents
1. [Web Application Fundamentals](#web-application-fundamentals)
2. [Web Application Architecture](#web-application-architecture)
3. [Client-Server Model](#client-server-model)
4. [HTTP Protocol Basics](#http-protocol-basics)
5. [Web Application Components](#web-application-components)
6. [Stateless Nature of HTTP](#stateless-nature-of-http)
7. [Web Server vs Application Server](#web-server-vs-application-server)
8. [Web Application Deployment](#web-application-deployment)
9. [Important Notes](#important-notes)

---

## Web Application Fundamentals

### Definition

A **Web Application** is a software program that runs on a web server and is accessed by clients (users) through a web browser over the HTTP/HTTPS protocol. Unlike desktop applications installed on individual machines, web applications are centralized, accessible from any device with a browser.

### Characteristics of Web Applications

| Characteristic | Description |
|---|---|
| **Client-Server Architecture** | Clients request, servers respond (centralized logic) |
| **HTTP-Based Communication** | Uses HTTP/HTTPS for all communication |
| **Browser-Independent Rendering** | Client-side: HTML, CSS, JavaScript (standardized) |
| **Centralized Data Management** | Single source of truth on server |
| **Multi-User Support** | Multiple users can access simultaneously |
| **Stateless Protocol** | Each request is independent; state must be managed |
| **Platform-Independent** | Works on Windows, Linux, macOS, mobile browsers |
| **No Installation Required** | Users access via URL (no setup needed) |
| **Real-Time Updates** | Server changes immediately visible to all users |
| **Scalability** | Can handle thousands of concurrent users |

### Web Applications vs Desktop Applications

| Aspect | Web App | Desktop App |
|---|---|---|
| **Installation** | None (browser only) | Full installation required |
| **Updates** | Automatic (server-side) | Manual updates needed |
| **Accessibility** | Anywhere with internet | Must be on installed machine |
| **Data Consistency** | Always current (central) | May have local copies |
| **Maintenance** | Single point (server) | Each machine separately |
| **Hardware Requirements** | Low (simple client) | Often high (powerful machine) |
| **Cost** | Lower (shared infrastructure) | Higher (per-machine licensing) |
| **Offline Usage** | Limited (needs internet) | Works without connection |

---

## Web Application Architecture

### Fig. 1: Three-Tier Web Application Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER (Client)                  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ WEB BROWSER                                              │   │
│  │                                                          │   │
│  │ ┌────────────────────────────────────────────────────┐   │   │
│  │ │ HTML/CSS - Structure & Styling                     │   │   │
│  │ │ └─ Defines page layout, forms, buttons             │   │   │
│  │ │                                                    │   │   │
│  │ │ JavaScript - Interactivity                         │   │   │
│  │ │ └─ Form validation, AJAX, DOM manipulation         │   │   │
│  │ │                                                    │   │   │
│  │ │ DOM (Document Object Model)                        │   │   │
│  │ │ └─ In-memory representation of HTML                │   │   │
│  │ │                                                    │   │   │
│  │ │ User Interface (UI)                                │   │   │
│  │ │ └─ What user sees and interacts with               │   │   │
│  │ └────────────────────────────────────────────────────┘   │   │
│  │                                                          │   │
│  │ Responsibilities:                                        │   │
│  │  • Display data to user                                  │   │
│  │  • Collect user input                                    │   │
│  │  • Validate input (client-side)                          │   │
│  │  • Send requests to server                               │   │
│  │  • Process server responses                              │   │
│  │  • Render HTML                                           │   │
│  │  • Execute JavaScript                                    │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                    HTTP/HTTPS Protocol
                   (TCP/IP Transport Layer)
                              │
┌─────────────────────────────────────────────────────────────────┐
│            BUSINESS LOGIC LAYER (Application Server)            │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ APPLICATION SERVER (Tomcat, JBoss, Jetty, etc.)          │   │
│  │                                                          │   │
│  │ ┌────────────────────────────────────────────────────┐   │   │
│  │ │ REQUEST DISPATCHER                                 │   │   │
│  │ │  └─ Route requests to appropriate handler          │   │   │
│  │ │                                                    │   │   │
│  │ │ SERVLET CONTAINER                                  │   │   │
│  │ │  └─ Manage servlet lifecycle                       │   │   │
│  │ │  └─ Thread pool management                         │   │   │
│  │ │  └─ Session management                             │   │   │
│  │ │                                                    │   │   │
│  │ │ SERVLET / JSP                                      │   │   │
│  │ │  ├─ LoginServlet (authentication)                  │   │   │
│  │ │  ├─ UserServlet (business operations)              │   │   │
│  │ │  ├─ ReportServlet (data processing)                │   │   │
│  │ │  └─ dashboard.jsp (dynamic content)                │   │   │
│  │ │                                                    │   │   │
│  │ │ BUSINESS LOGIC LAYER                               │   │   │
│  │ │  ├─ Service classes (UserService, OrderService)    │   │   │
│  │ │  ├─ Validation & processing                        │   │   │
│  │ │  └─ Business rules implementation                  │   │   │
│  │ │                                                    │   │   │
│  │ │ FILTERS & INTERCEPTORS                             │   │   │
│  │ │  ├─ Authentication filter                          │   │   │
│  │ │  ├─ Logging filter                                 │   │   │
│  │ │  └─ Encoding filter                                │   │   │
│  │ └────────────────────────────────────────────────────┘   │   │
│  │                                                          │   │
│  │ Responsibilities:                                        │   │
│  │  • Receive HTTP requests                                 │   │
│  │  • Parse request parameters                              │   │
│  │  • Execute business logic                                │   │
│  │  • Query database (via DAO)                              │   │
│  │  • Generate responses (HTML/JSON)                        │   │
│  │  • Manage sessions & state                               │   │
│  │  • Handle errors & exceptions                            │   │
│  │  • Send HTTP responses                                   │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                    Database Driver (JDBC)
                   (Network Connection to DB)
                              │
┌─────────────────────────────────────────────────────────────────┐
│                 DATA ACCESS LAYER (Database)                    │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ DATABASE SERVER (MySQL, PostgreSQL, Oracle, etc.)        │   │
│  │                                                          │   │
│  │ ┌────────────────────────────────────────────────────┐   │   │
│  │ │ TABLES                                             │   │   │
│  │ │  ├─ users (id, name, email, password_hash)         │   │   │
│  │ │  ├─ orders (id, user_id, amount, date)             │   │   │
│  │ │  ├─ products (id, name, price, stock)              │   │   │
│  │ │  └─ (other domain tables)                          │   │   │
│  │ │                                                    │   │   │
│  │ │ INDEXES                                            │   │   │
│  │ │  └─ Speed up query execution                       │   │   │
│  │ │                                                    │   │   │
│  │ │ STORED PROCEDURES (Optional)                       │   │   │
│  │ │  └─ Complex server-side logic                      │   │   │
│  │ │                                                    │   │   │
│  │ │ CONSTRAINTS                                        │   │   │
│  │ │  ├─ Primary keys (uniqueness)                      │   │   │
│  │ │  ├─ Foreign keys (relationships)                   │   │   │
│  │ │  └─ Check constraints (data validity)              │   │   │
│  │ └────────────────────────────────────────────────────┘   │   │
│  │                                                          │   │
│  │ Responsibilities:                                        │   │
│  │  • Store persistent data                                 │   │
│  │  • Execute SQL queries                                   │   │
│  │  • Enforce data integrity                                │   │
│  │  • Manage transactions                                   │   │
│  │  • Provide security (user permissions)                   │   │
│  │  • Enable concurrent access                              │   │
│  │  • Backup & recovery                                     │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Separation of Concerns (MVC Pattern)

```
MODEL-VIEW-CONTROLLER (MVC)
═══════════════════════════════════════════════════════════════

MODEL (Data & Business Logic)
├─ Domain objects (User, Order, Product)
├─ Business rules (validation, calculations)
├─ DAO classes (database access)
└─ Independent of view/controller

CONTROLLER (Request Handler)
├─ Servlets
├─ Processes user requests
├─ Calls model for business logic
├─ Forwards to appropriate view
└─ No HTML generation

VIEW (User Interface)
├─ JSP pages
├─ HTML templates
├─ Presentation logic only
├─ No business logic
└─ Displays model data

Flow:
    User Action (click, submit)
           │
           ▼
    Controller (Servlet)
           │
           ├─ Extract parameters
           ├─ Call Model (Service/DAO)
           │
           ▼
    Model (Business Logic)
           │
           ├─ Process data
           ├─ Query database
           │
           ▼
    View (JSP/HTML)
           │
           └─ Display results to user
```

---

## Client-Server Model

### Fig. 2: Client-Server Communication

```
CLIENT (Thin Client)
═══════════════════════════════════════════════════════════════

┌─────────────────────────────────────┐
│   WEB BROWSER                       │
│                                     │
│  1. User enters URL or clicks link  │
│  2. Browser makes HTTP request      │
│  3. Receives response (HTML)        │
│  4. Renders page                    │
│  5. Waits for user interaction      │
│                                     │
│  Resources:                         │
│  ├─ Minimal CPU/Memory              │
│  ├─ HTML/CSS/JavaScript             │
│  └─ No business logic               │
│                                     │
│  Runs on:                           │
│  ├─ Desktop, laptop                 │
│  ├─ Tablet, smartphone              │
│  └─ Any device with browser         │
└─────────────────────────────────────┘

Why "Thin"?
├─ Minimal processing on client
├─ No data stored locally (except cache)
├─ Server handles all logic
├─ Easy to update (no client-side deployment)
└─ Centralized control

─────────────────────────────────────────────────────────────────

COMMUNICATION (HTTP/HTTPS)
═══════════════════════════════════════════════════════════════

HTTP Request (Client → Server)
┌──────────────────────────────┐
│ GET /users?id=5 HTTP/1.1     │  ← Method & URL & Version
│ Host: example.com            │  ← Host header
│ User-Agent: Mozilla/5.0      │  ← Browser info
│ Accept: text/html            │  ← Expected content type
│ Cookie: sessionId=abc123     │  ← Session identifier
│                              │
│ (no body for GET)            │
└──────────────────────────────┘

HTTP Response (Server → Client)
┌──────────────────────────────┐
│ HTTP/1.1 200 OK              │  ← Status code
│ Content-Type: text/html      │  ← Response format
│ Content-Length: 4096         │  ← Size
│ Set-Cookie: sessionId=xyz789 │  ← New session
│                              │
│ <html>                       │  ← Body (actual content)
│   <body>                     │
│     User data here...        │
│   </body>                    │
│ </html>                      │
└──────────────────────────────┘

─────────────────────────────────────────────────────────────────

SERVER (Fat Server)
═══════════════════════════════════════════════════════════════

┌──────────────────────────────────────┐
│  APPLICATION SERVER                  │
│                                      │
│  1. Receives HTTP request            │
│  2. Parses request details           │
│  3. Routes to appropriate servlet    │
│  4. Executes business logic          │
│  5. Queries database                 │
│  6. Generates HTML response          │
│  7. Sends response back              │
│                                      │
│  Resources:                          │
│  ├─ Powerful CPU                     │
│  ├─ Large memory                     │
│  ├─ Persistent storage               │
│  ├─ Business logic                   │
│  ├─ Database access                  │
│  └─ Session management               │
│                                      │
│  Runs on:                            │
│  ├─ Dedicated server machines        │
│  ├─ Cloud infrastructure             │
│  └─ Can be clustered                 │
└──────────────────────────────────────┘

Why "Fat"?
├─ All processing here
├─ Stores all data
├─ Executes all business logic
├─ Maintains session state
├─ Database access
└─ Expensive but centralized

─────────────────────────────────────────────────────────────────

REQUEST-RESPONSE CYCLE
═══════════════════════════════════════════════════════════════

Time    │ Client              │  Network      │  Server
────────┼─────────────────────┼───────────────┼──────────────
   t1   │ User clicks link    │               │
   t2   │ Generate request    │               │
   t3   │ Send request        │ Transmit ──►  │
   t4   │                     │               │ Receive
   t5   │                     │               │ Process
   t6   │                     │               │ Query DB
   t7   │                     │               │ Generate HTML
   t8   │                     │ ◄── Send HTML │
   t9   │ Receive response    │               │
  t10   │ Parse HTML          │               │
  t11   │ Render page         │               │
  t12   │ Display to user     │               │

Latency = t8 - t3 (Network + Processing)
```

---

## HTTP Protocol Basics

### Fig. 3: HTTP Protocol Details

```
HTTP (HyperText Transfer Protocol)
═══════════════════════════════════════════════════════════════

Characteristics:
├─ Application-level protocol (Layer 7)
├─ Built on TCP/IP (Layers 4-3)
├─ Request-Response pattern
├─ Stateless (no connection state)
├─ Unencrypted text (HTTP)
├─ Can be encrypted (HTTPS)
├─ Versions: 1.0 (obsolete), 1.1 (common), 2.0 (modern), 3.0 (latest)
└─ Default ports: 80 (HTTP), 443 (HTTPS)

─────────────────────────────────────────────────────────────────

HTTP STATUS CODES
═══════════════════════════════════════════════════════════════

1xx Informational (100-199)
├─ 100 Continue
└─ Rarely used in web apps

2xx Success (200-299)
├─ 200 OK (request successful, response included)
├─ 201 Created (new resource created)
├─ 202 Accepted (request accepted, processing)
├─ 204 No Content (success, no body)
└─ Most common: 200 OK

3xx Redirection (300-399)
├─ 301 Moved Permanently (old URL, use new one)
├─ 302 Found (temporary redirect)
├─ 303 See Other (redirect to different URL)
├─ 304 Not Modified (cached version is current)
└─ Used by: Expired pages, forwarding, load balancing

4xx Client Error (400-499)
├─ 400 Bad Request (malformed request)
├─ 401 Unauthorized (login required)
├─ 403 Forbidden (authenticated but no permission)
├─ 404 Not Found (resource doesn't exist)
├─ 405 Method Not Allowed (POST on GET-only resource)
└─ User's fault

5xx Server Error (500-599)
├─ 500 Internal Server Error (unhandled exception)
├─ 501 Not Implemented (feature not available)
├─ 502 Bad Gateway (upstream server error)
├─ 503 Service Unavailable (server overloaded/down)
└─ Server's fault

Common in Web Apps:
    200 - Page loaded successfully
    302 - Redirect after login
    400 - Invalid form submission
    401 - Session expired, login again
    404 - Page not found
    500 - Servlet threw exception

─────────────────────────────────────────────────────────────────

HTTP REQUEST FORMAT
═══════════════════════════════════════════════════════════════

Request Line:
    METHOD PATH HTTP_VERSION
    GET /users?id=5 HTTP/1.1

Request Headers:
    Header-Name: Header-Value
    Host: example.com
    User-Agent: Mozilla/5.0
    Accept: text/html
    Cookie: sessionId=abc123
    Content-Length: 256
    Content-Type: application/x-www-form-urlencoded

Request Body (only for POST, PUT, PATCH):
    username=john&password=secret

Complete Request:
    POST /login HTTP/1.1
    Host: example.com
    User-Agent: Mozilla/5.0
    Content-Length: 32
    Content-Type: application/x-www-form-urlencoded
    
    username=john&password=secret

─────────────────────────────────────────────────────────────────

HTTP RESPONSE FORMAT
═══════════════════════════════════════════════════════════════

Status Line:
    HTTP_VERSION STATUS_CODE REASON_PHRASE
    HTTP/1.1 200 OK

Response Headers:
    Header-Name: Header-Value
    Content-Type: text/html; charset=UTF-8
    Content-Length: 4096
    Set-Cookie: sessionId=xyz789; Path=/
    Expires: Thu, 01 Jan 2025 00:00:00 GMT
    Cache-Control: no-cache

Response Body:
    <html>
    <head><title>Login Successful</title></head>
    <body>
      <h1>Welcome, John!</h1>
    </body>
    </html>

Complete Response:
    HTTP/1.1 200 OK
    Content-Type: text/html; charset=UTF-8
    Content-Length: 150
    Set-Cookie: sessionId=xyz789; Path=/
    
    <html>
      <body>
        <h1>Welcome!</h1>
      </body>
    </html>

─────────────────────────────────────────────────────────────────

HTTP vs HTTPS
═══════════════════════════════════════════════════════════════

HTTP (Insecure)
├─ Unencrypted communication
├─ Data visible to anyone on network
├─ No authentication of server
├─ Port: 80
├─ Use: Public, non-sensitive content only
└─ Deprecated (browsers show warning)

HTTPS (Secure)
├─ Encrypted with TLS/SSL
├─ Data protected from eavesdropping
├─ Server authentication via certificates
├─ Port: 443
├─ Use: ALL modern web apps (required for login, payment, etc.)
└─ Standard today (HTTP2/3 run over HTTPS)

Certificate:
    ├─ Issued by Certificate Authority (CA)
    ├─ Contains server's public key
    ├─ Verified by browser
    ├─ Expires periodically (renewal needed)
    └─ Examples: DigiCert, Let's Encrypt, Comodo

Encryption Process:
    1. Browser requests HTTPS connection
    2. Server sends certificate
    3. Browser verifies certificate
    4. Browser generates session key
    5. Browser encrypts key with server's public key
    6. All communication now encrypted/decrypted
    7. Only server can decrypt (has private key)
```

---

## Web Application Components

### Fig. 4: Component Architecture

```
WEB APPLICATION COMPONENTS
═══════════════════════════════════════════════════════════════

STATIC RESOURCES
├─ HTML Files
│  └─ Pre-written pages (about.html, contact.html)
├─ CSS Files
│  └─ Styling (style.css, responsive.css)
├─ JavaScript Files
│  └─ Client-side logic (app.js, validation.js)
├─ Images
│  └─ PNG, JPG, GIF files
├─ Media
│  └─ Video, audio files
└─ Served directly by web server (fast)

DYNAMIC RESOURCES
├─ Servlets
│  ├─ Java classes
│  ├─ Handle requests programmatically
│  └─ Generate responses
├─ JSP Pages
│  ├─ HTML + Java mix
│  ├─ Compiled to servlets
│  └─ Generate dynamic HTML
└─ Execute on server (slower but flexible)

CONFIGURATION FILES
├─ web.xml
│  ├─ Deployment descriptor
│  ├─ URL mappings
│  ├─ Servlet configuration
│  └─ Filter configuration
├─ Properties Files
│  ├─ Application settings
│  ├─ Database connection strings
│  └─ Feature flags
└─ Context Configuration
   └─ Server-specific settings

APPLICATION LOGIC
├─ Service Classes
│  ├─ Business logic (UserService, OrderService)
│  ├─ Validation
│  └─ Calculations
├─ DAO Classes (Data Access Objects)
│  ├─ Database queries
│  ├─ CRUD operations
│  └─ SQL statements
├─ Domain Objects
│  ├─ User, Order, Product (POJOs)
│  └─ Represent data entities
└─ Utility Classes
   └─ Helper functions

SESSION & STATE MANAGEMENT
├─ Session Object
│  ├─ Per-user data storage
│  ├─ Persists across requests
│  └─ Identified by session ID
├─ Cookies
│  ├─ Client-side storage
│  ├─ Sent with each request
│  └─ Persistent or temporary
└─ Request Scope
   └─ Data valid for single request

SECURITY COMPONENTS
├─ Authentication
│  └─ Login/logout mechanism
├─ Authorization
│  └─ Role-based access control (RBAC)
├─ Filters
│  ├─ Security filters
│  └─ Request validation
└─ Secure Session Management
   └─ Session ID randomization, timeout

PERSISTENCE LAYER
├─ JDBC Connections
│  └─ Database connectivity
├─ Connection Pool
│  ├─ HikariCP, c3p0, DBCP
│  └─ Reusable connections
└─ Transaction Management
   └─ Commit/rollback
```

---

## Stateless Nature of HTTP

### Fig. 5: State Management in Stateless HTTP

```
PROBLEM: HTTP IS STATELESS
═══════════════════════════════════════════════════════════════

Each Request is Independent
├─ Server doesn't remember previous requests
├─ No connection between requests
├─ Server processes each request alone
└─ No way to identify returning user

Example Problem:
    Request 1: User logs in as "john"
    Server: OK, user authenticated
    
    Request 2: User requests dashboard
    Server: Who are you? (forgot john already!)
    User: I just logged in!
    Server: No record of that...

Solution: USE SESSIONS
├─ Create unique session ID per user
├─ Send session ID with each request
├─ Server maintains session data
├─ User stays "logged in" across requests

─────────────────────────────────────────────────────────────────

SESSION-BASED STATE MANAGEMENT
═══════════════════════════════════════════════════════════════

Flow:
    1. User logs in (POST /login)
       ├─ Server validates credentials
       ├─ Creates session: Session session = request.getSession()
       ├─ Stores user: session.setAttribute("user", userObj)
       └─ Returns Set-Cookie: sessionId=abc123xyz

    2. Browser receives Set-Cookie header
       └─ Stores sessionId in browser's cookie jar

    3. Next request (GET /dashboard)
       ├─ Browser adds: Cookie: sessionId=abc123xyz
       ├─ Server receives request + sessionId
       ├─ Looks up session by ID
       ├─ Retrieves user object
       └─ User still "logged in"

    4. Session expires (timeout)
       ├─ Server removes session after inactivity
       ├─ Next request has invalid sessionId
       ├─ Server creates new session
       └─ User needs to log in again

Code Example:
    // Request 1: Login
    protected void doPost(HttpServletRequest req, ...) {
        String username = req.getParameter("username");
        User user = authenticate(username, password);
        
        Session session = req.getSession();  // Create session
        session.setAttribute("user", user);  // Store user
        
        res.sendRedirect("dashboard.jsp");
    }
    
    // Request 2: Dashboard (different request, same session)
    protected void doGet(HttpServletRequest req, ...) {
        Session session = req.getSession(false);  // Get existing
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            // User is logged in
            displayDashboard(user);
        } else {
            // No session or expired
            res.sendRedirect("login.jsp");
        }
    }

─────────────────────────────────────────────────────────────────

SESSION ID FLOW
═══════════════════════════════════════════════════════════════

Request 1 (Login):
    Client:
        POST /login HTTP/1.1
        username=john&password=secret
    
    Server:
        HTTP/1.1 200 OK
        Set-Cookie: JSESSIONID=abc123xyz789; Path=/; HttpOnly
        
        (Session map: {abc123xyz789 -> {user: User("john")}})
    
    Client:
        Browser stores cookie: JSESSIONID=abc123xyz789

Request 2 (Subsequent):
    Client:
        GET /dashboard HTTP/1.1
        Cookie: JSESSIONID=abc123xyz789  ← Automatically included
    
    Server:
        Receives request
        Extracts JSESSIONID=abc123xyz789
        Looks up session: sessionMap.get("abc123xyz789")
        Retrieves User("john")
        User is authenticated!

Session Timeout:
    Server:
        If no request for 30 minutes (default)
        Removes session from sessionMap
        Cookie still in browser (stale)
    
    Client:
        Sends: Cookie: JSESSIONID=abc123xyz789 (stale)
        Server: Invalid session ID
        Requires re-login

─────────────────────────────────────────────────────────────────

ALTERNATIVE: COOKIES FOR STATE
═══════════════════════════════════════════════════════════════

Using Cookies Directly:
    Request 1:
        Server: res.addCookie(new Cookie("theme", "dark"));
    
    Request 2-N:
        Browser sends: Cookie: theme=dark
        Server can read: String theme = req.getCookies()...
    
    Advantages:
        ├─ No server-side storage needed
        └─ Lighter server load
    
    Disadvantages:
        ├─ User can modify cookies (security risk)
        ├─ Limited size (4KB per cookie)
        └─ Sensitive data visible in headers

Use Cases:
    ├─ Remember preferences (theme, language)
    ├─ Track user (analytics cookies)
    └─ Short-term remembering
    
    NOT for:
        ├─ Authentication/authorization
        ├─ Sensitive data
        └─ Session management (use sessions instead)
```

---

## Web Server vs Application Server

### Fig. 6: Component Roles

```
WEB SERVER (Apache, Nginx, IIS)
═══════════════════════════════════════════════════════════════

Responsibilities:
├─ Listen on port 80/443
├─ Receive HTTP requests
├─ Serve static files (HTML, CSS, JS, images)
├─ Reverse proxy (forward to app server)
├─ Load balancing (distribute requests)
├─ SSL/TLS termination (HTTPS)
├─ Compression (gzip)
├─ Caching
└─ Logging & monitoring

Fast for:
├─ Static content
├─ Simple requests
├─ High concurrency
└─ Minimal processing

Example: Nginx Configuration
    server {
        listen 80;
        server_name example.com;
        
        # Serve static files
        location /static/ {
            alias /var/www/static/;
        }
        
        # Proxy to app server
        location / {
            proxy_pass http://localhost:8080;
            proxy_set_header Host $host;
        }
    }

─────────────────────────────────────────────────────────────────

APPLICATION SERVER (Tomcat, JBoss, Jetty)
═══════════════════════════════════════════════════════════════

Responsibilities:
├─ Listen on port 8080/8888 (internal)
├─ Receive HTTP requests (from web server)
├─ Execute servlets
├─ Compile & execute JSPs
├─ Manage sessions
├─ Transaction management
├─ Database connectivity
├─ Business logic execution
├─ Generate dynamic responses
└─ Send responses to web server

Processing:
    1. Parse request
    2. Route to servlet
    3. Create/retrieve servlet instance
    4. Execute business logic
    5. Query database
    6. Generate HTML
    7. Return response

Slow for:
├─ High volume of requests (if overloaded)
├─ Database-heavy operations
├─ Complex computations
└─ Needs horizontal scaling

Example: Tomcat with Servlet
    public class UserServlet extends HttpServlet {
        protected void doGet(...) {
            // Business logic
            User user = userService.getUser(id);
            // Generate response
            response.getWriter().print(user.getName());
        }
    }

─────────────────────────────────────────────────────────────────

TYPICAL DEPLOYMENT ARCHITECTURE
═════════════════════════════════════════════════════════════════

Internet
    │
    ▼
┌──────────────────┐
│   Firewall       │
└────────┬─────────┘
         │
         ▼
┌───────────────────────────────────────┐
│     Web Server (Nginx/Apache)         │
│                                       │
│  Listen: 0.0.0.0:80/443               │
│  - Serve static files                 │
│  - Reverse proxy                      │
│  - SSL termination                    │
│  - Load balancing                     │
└────────────┬──────────────────────────┘
             │
      ┌──────┴──────┐
      │             │
      ▼             ▼
┌──────────────┐  ┌──────────────┐
│ App Server 1 │  │ App Server 2 │  (Scalable cluster)
│ (Tomcat:8080)│  │ (Tomcat:8080)│
│              │  │              │
│ ┌──────────┐ │  │ ┌──────────┐ │
│ │ Servlets │ │  │ │ Servlets │ │
│ │   JSPs   │ │  │ │   JSPs   │ │
│ └────┬─────┘ │  │ └────┬─────┘ │
└──────┼───────┘  └──────┼───────┘
       │                 │
       └────────┬────────┘
                │
                ▼
        ┌──────────────────┐
        │  Database Server │
        │  (MySQL/Postgres)│
        │                  │
        │  ┌────────────┐  │
        │  │  Tables    │  │
        │  │  Indexes   │  │
        │  │  Data      │  │
        │  └────────────┘  │
        └──────────────────┘

Benefits of This Architecture:
├─ Load distribution
├─ Scalability (add app servers)
├─ High availability (redundancy)
├─ Security (firewall, separation)
├─ Performance (static caching, proxy)
└─ Flexibility (upgrade components independently)
```

---

## Web Application Deployment

### Fig. 7: Deployment & Packaging

```
PACKAGING FOR DEPLOYMENT
═══════════════════════════════════════════════════════════════

Development Structure:
    myapp/
    ├─ src/
    │  ├─ LoginServlet.java
    │  ├─ UserService.java
    │  ├─ UserDAO.java
    │  └─ ...
    ├─ WebContent/
    │  ├─ index.html
    │  ├─ login.jsp
    │  ├─ dashboard.jsp
    │  ├─ css/
    │  │  └─ style.css
    │  ├─ js/
    │  │  └─ app.js
    │  └─ WEB-INF/
    │     ├─ web.xml
    │     └─ lib/ (JARs)
    └─ pom.xml (Maven)

Compilation:
    javac src/*.java -d bin/
    (Produces .class files in bin/)

Packaging to WAR:
    Java classes + JSPs + Config + Libraries
            ↓
    WAR File (Web ARchive - just a ZIP)
    
    myapp.war
    └─ Compressed archive containing:
       ├─ WEB-INF/
       │  ├─ classes/
       │  │  ├─ LoginServlet.class
       │  │  ├─ UserService.class
       │  │  └─ ...
       │  ├─ lib/
       │  │  ├─ mysql-connector.jar
       │  │  ├─ JSTL.jar
       │  │  └─ ...
       │  └─ web.xml
       ├─ index.html
       ├─ login.jsp
       ├─ css/
       │  └─ style.css
       └─ js/
          └─ app.js

Deployment Process:
    1. Build: mvn clean package
       └─ Creates myapp.war
    
    2. Deploy: Copy myapp.war to Tomcat/webapps/
       └─ Tomcat auto-deploys
    
    3. Start: bin/startup.sh
       └─ Tomcat extracts WAR and runs
    
    4. Access: http://localhost:8080/myapp/
       └─ Application is live!

─────────────────────────────────────────────────────────────────

DEPLOYMENT ENVIRONMENTS
═══════════════════════════════════════════════════════════════

Development (Local Machine)
├─ IDE: Eclipse, IntelliJ, VS Code
├─ Server: Tomcat (local instance)
├─ Database: MySQL (local)
├─ Purpose: Development, testing
├─ Access: http://localhost:8080
└─ Issues: Doesn't matter, rapid iteration

Testing (Test Server)
├─ Dedicated machine
├─ Tomcat (shared instance)
├─ MySQL (shared instance)
├─ Purpose: QA, integration testing
├─ Access: http://testing.company.com
└─ Issues: Must be fixed before prod

Staging (Pre-Production)
├─ Production-like environment
├─ Tomcat (load-balanced, clustered)
├─ MySQL (replicated, backed-up)
├─ Purpose: Final validation before release
├─ Access: http://staging.company.com
└─ Issues: Must pass all tests

Production (Live System)
├─ Multiple servers (high availability)
├─ Tomcat (load-balanced cluster)
├─ MySQL (master-slave replication)
├─ Purpose: Serving real users
├─ Access: https://example.com
├─ Monitoring: 24/7 surveillance
└─ Issues: Critical, must be fixed immediately

Environment Variables per Env:
    Development:
        ├─ DATABASE_URL=localhost:3306
        ├─ DEBUG=true
        └─ LOG_LEVEL=DEBUG
    
    Production:
        ├─ DATABASE_URL=db.aws.com:3306
        ├─ DEBUG=false
        └─ LOG_LEVEL=ERROR

─────────────────────────────────────────────────────────────────

CLOUD DEPLOYMENT OPTIONS
═══════════════════════════════════════════════════════════════

Platform as a Service (PaaS)
├─ Heroku, AWS Elastic Beanstalk
├─ Just deploy WAR, platform handles rest
├─ Automatic scaling
├─ Managed database
└─ Easiest but less control

Infrastructure as a Service (IaaS)
├─ AWS EC2, Azure, GCP
├─ Rent virtual machines
├─ Install Tomcat, database yourself
├─ More control, more responsibility
└─ Pay per resource used

Containerization (Docker)
├─ Package app in Docker container
├─ Container includes: Java, Tomcat, App
├─ Deploy same container everywhere
├─ Kubernetes for orchestration
└─ Modern standard
```

---

## Important Notes

### ✓ Key Concepts

1. **Web applications are Client-Server**
   - Client (browser) is thin and simple
   - Server handles all heavy lifting
   - Communication via HTTP/HTTPS

2. **HTTP is Stateless**
   - Each request independent
   - No memory of previous interactions
   - Use sessions/cookies for state
   - Session data stored on server

3. **Three Tiers Separate Concerns**
   - Presentation (HTML/CSS/JS)
   - Business Logic (Servlets, Services)
   - Data (Database)
   - Changes in one layer don't affect others

4. **Web Servers vs App Servers**
   - Web servers (Nginx) serve static content fast
   - App servers (Tomcat) execute dynamic logic
   - Often used together (web server as proxy)

5. **Request-Response Cycle**
   - Client sends request (parameters, headers)
   - Server processes (queries, logic)
   - Server sends response (HTML, JSON)
   - Cycle completes, connection might close

6. **Security Requires HTTPS**
   - Always use HTTPS in production
   - HTTP is insecure (data visible)
   - Certificates required (self-signed for dev)
   - Browsers warn about insecure content

### ⚠ Common Mistakes

- Storing state in servlet fields (thread safety)
- Not using HTTPS in production
- Forgetting to close database connections
- Not validating user input on server
- Hardcoding configuration in code
- Not handling session expiration
- Storing sensitive data in cookies
- Not implementing proper error handling
- Ignoring HTTP status codes
- Not logging important events

---

**End of 6.1_Overview_of_Web_Application.md**