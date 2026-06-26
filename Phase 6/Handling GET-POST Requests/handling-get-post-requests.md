# Handling GET/POST Requests

---

## Table of Contents
1. [GET vs POST Comparison](#get-vs-post-comparison)
2. [Method Dispatch Flow](#method-dispatch-flow)
3. [Handling GET Requests](#handling-get-requests)
4. [Handling POST Requests](#handling-post-requests)
5. [Handling Both Methods](#handling-both-methods)
6. [Request Forwarding vs Redirection](#request-forwarding-vs-redirection)
7. [Common Patterns](#common-patterns)
8. [Security Implications](#security-implications)
9. [Code Examples](#code-examples)
10. [Important Notes](#important-notes)

---

## GET vs POST Comparison

### Detailed Comparison

| Aspect | GET | POST |
|--------|-----|------|
| **HTTP Specification** | RFC 7231 – Retrieve resource | RFC 7231 – Submit/Create resource |
| **Data Transmission** | Query string in URL | Request body (hidden) |
| **Data Visibility** | Visible in browser URL bar | Hidden from user |
| **Data Size Limit** | ~2000 characters (browser dependent) | 50MB+ (server config) |
| **Caching** | Cached by browser by default | Not cached (requires explicit header) |
| **Browser History** | Stored in browser history | Not stored in history |
| **Bookmarkable** | Yes (URL contains all data) | No (data in body) |
| **Back Button** | Safe (just revisits URL) | May ask user to resubmit |
| **Security** | Visible to anyone watching | More secure (data hidden) |
| **Idempotent** | Yes (no side effects) | No (may create duplicates) |
| **Use for** | Retrieve, filter, search, pagination | Create, update, login, upload |
| **CSRF Vulnerable** | Less (token in URL easy to include) | More (token in hidden field) |
| **Form Encoding** | Query string | application/x-www-form-urlencoded or multipart/form-data |

### Visual Comparison

```
GET Request:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
GET /api/search?q=java&category=tutorials HTTP/1.1
Host: example.com

(No body)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Data visible in URL ✓
Bookmarkable ✓
Cached by default ✓
For reading data ✓


POST Request:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
POST /api/users HTTP/1.1
Host: example.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 42

username=john&email=john@example.com&age=25
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Data in body (hidden) ✓
Not bookmarkable ✓
For writing data ✓
Creates resources ✓
```

---

## Method Dispatch Flow

### HttpServlet Service Method Routing

```
HTTP Request arrives
        │
        ▼
HttpServlet.service(request, response)
        │
        ├─ Reads request.getMethod()
        │
        ├─────────────────────────────────────┐
        │                                     │
        ▼                                     ▼
    "GET"                                 "POST"
        │                                     │
        ▼                                     ▼
    doGet(req, res)                     doPost(req, res)
        │                                     │
        ▼                                     ▼
    (User implementation)            (User implementation)
        │                                     │
        └─────────────────┬───────────────────┘
                          │
                          ▼
                    Send Response


Other methods (PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE)
follow same pattern → doPut(), doDelete(), etc.
```

### Dispatcher Logic (Simplified)

```java
// Inside HttpServlet.service():
public void service(HttpServletRequest req, HttpServletResponse res) {
    String method = req.getMethod();
    
    if ("GET".equals(method)) {
        this.doGet(req, res);
    } 
    else if ("POST".equals(method)) {
        this.doPost(req, res);
    } 
    else if ("PUT".equals(method)) {
        this.doPut(req, res);
    } 
    else if ("DELETE".equals(method)) {
        this.doDelete(req, res);
    }
    // ... etc
}
```

---

## Handling GET Requests

### Characteristics
- **Purpose:** Retrieve data/resources
- **Idempotent:** Multiple calls → same result
- **Safe:** No data modification on server
- **Cacheable:** Browser/server may cache response
- **Use Case:** Search, filtering, pagination, display

### doGet() Skeleton

```java
@Override
protected void doGet(HttpServletRequest request, 
                    HttpServletResponse response) 
        throws ServletException, IOException {
    
    // 1. Read parameters from query string
    String searchTerm = request.getParameter("q");
    
    // 2. Validate
    if (searchTerm == null || searchTerm.trim().isEmpty()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Search term required");
        return;
    }
    
    // 3. Query data (database, cache, etc.)
    // List<Result> results = database.search(searchTerm);
    
    // 4. Generate response
    response.setContentType("application/json");
    response.getWriter().println("{\"results\": [...]}");
}
```

### GET Request Examples

```
Simple retrieval:
GET /products HTTP/1.1

With parameters:
GET /products?category=electronics&sort=price HTTP/1.1

Search:
GET /search?q=java+tutorial&page=1 HTTP/1.1

Pagination:
GET /api/users?page=2&limit=50 HTTP/1.1

Filtering:
GET /articles?author=john&date=2024 HTTP/1.1
```

---

## Handling POST Requests

### Characteristics
- **Purpose:** Create/Submit/Modify data
- **Not Idempotent:** Multiple calls may have different results
- **Not Safe:** Modifies server state
- **Not Cacheable:** Response usually not cached
- **Use Case:** Form submission, login, file upload, creation

### doPost() Skeleton

```java
@Override
protected void doPost(HttpServletRequest request, 
                     HttpServletResponse response) 
        throws ServletException, IOException {
    
    // 1. Read parameters from request body
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    // 2. Validate
    if (username == null || password == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Missing credentials");
        return;
    }
    
    // 3. Process (database write, etc.)
    // boolean success = database.createUser(username, password);
    
    // 4. Send response
    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setContentType("application/json");
    response.getWriter().println("{\"message\": \"User created\"}");
}
```

### POST Request Examples

```
Login form:
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=john&password=secret123

Create resource:
POST /api/users HTTP/1.1
Content-Type: application/json

{"name":"John","email":"john@example.com"}

File upload:
POST /upload HTTP/1.1
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary

------WebKitFormBoundary
Content-Disposition: form-data; name="file"; filename="doc.pdf"
...binary data...
```

---

## Handling Both Methods

### Strategy 1: Separate Methods (Recommended)

```java
public class DualMethodServlet extends HttpServlet {
    
    // Handle GET: Display form
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(
            "<form method='POST'>" +
            "<input type='text' name='name' />" +
            "<input type='submit' />" +
            "</form>"
        );
    }
    
    // Handle POST: Process form
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        response.setContentType("text/html");
        response.getWriter().println("<h1>Hello, " + name + "</h1>");
    }
}
```

### Strategy 2: Unified Handler (Less Common)

```java
public class UnifiedServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        handleRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        handleRequest(request, response);
    }
    
    private void handleRequest(HttpServletRequest request, 
                              HttpServletResponse response) 
            throws ServletException, IOException {
        
        String method = request.getMethod();
        
        if ("GET".equals(method)) {
            handleGet(request, response);
        } else if ("POST".equals(method)) {
            handlePost(request, response);
        }
    }
    
    private void handleGet(HttpServletRequest req, 
                          HttpServletResponse res) throws IOException {
        // GET logic
    }
    
    private void handlePost(HttpServletRequest req, 
                           HttpServletResponse res) throws IOException {
        // POST logic
    }
}
```

---

## Request Forwarding vs Redirection

### Key Differences

```
FORWARD (Server-side):
┌──────────────────────────────────────────────┐
│  Client                        Server        │
│    │                             │           │
│    │  GET /servlet1              │           │
│    ├────────────────────────────>│           │
│    │                             │ FORWARD   │
│    │                             ├──────┐    │
│    │                             │      │    │
│    │                        /servlet2   │    │
│    │                             │<─────┘    │
│    │                    (Same URL in browser)│
│    │  Response                   │           │
│    │<────────────────────────────┤           │
│    │  /servlet1                  │           │
│    │                             │           │
└──────────────────────────────────────────────┘

REDIRECT (Client-side):
┌──────────────────────────────────────────────┐
│  Client                        Server        │
│    │                             │           │
│    │  GET /servlet1              │           │
│    ├────────────────────────────>│           │
│    │                             │           │
│    │  302 Found                  │           │
│    │  Location: /servlet2        │           │
│    │<────────────────────────────┤           │
│    │                             │           │
│    │  GET /servlet2              │           │
│    ├────────────────────────────>│           │
│    │                             │           │
│    │  Response                   │           │
│    │<────────────────────────────┤           │
│    │  /servlet2                  │           │
│    │                             │           │
└──────────────────────────────────────────────┘
```

### Comparison Table

| Aspect | Forward | Redirect |
|--------|---------|----------|
| **Location** | Server-side | Client-side |
| **URL in Browser** | Original URL (servlet1) | New URL (servlet2) |
| **HTTP Status** | No redirect (200 OK) | 301/302/307 redirect |
| **Request Object** | Same request passed along | New request created |
| **Request Data** | Accessible in target | Lost (new request) |
| **Performance** | Faster (one trip) | Slower (two trips) |
| **Use Case** | Same app routing | Different domain/URL |
| **Back Button** | Works (browser knows one URL) | Works (each URL in history) |

### Code Comparison

```java
// FORWARD (keep request data, stay on same URL)
RequestDispatcher dispatcher = request.getRequestDispatcher("/target");
dispatcher.forward(request, response);
// Browser URL: /servlet1
// Request data preserved

// REDIRECT (lose request data, change URL)
response.sendRedirect("/target");
// Browser URL: /target
// New request created, old data lost

// Alternative redirect with absolute URL
response.sendRedirect("https://example.com/page");
```

---

## Common Patterns

### Pattern 1: Form Display (GET) + Processing (POST)

```
User                          Server
  │                            │
  │  GET /register             │
  ├───────────────────────────>│
  │                            │
  │ Display form (doGet)       │
  │<───────────────────────────┤
  │                            │
  │ Fill form, Submit (POST)   │
  │  POST /register            │
  ├───────────────────────────>│
  │                            │
  │ Process form (doPost)      │
  │ Store in DB                │
  │<───────────────────────────┤
  │ Success page or redirect   │
```

### Pattern 2: REST API (Multiple Methods)

```
GET /api/users           → List all users (doGet)
POST /api/users          → Create user (doPost)
GET /api/users/5         → Get user 5 (doGet)
PUT /api/users/5         → Update user 5 (doPut)
DELETE /api/users/5      → Delete user 5 (doDelete)
```

### Pattern 3: Action-Based Routing

```
POST /products?action=add     → doPost → handleAdd()
POST /products?action=update  → doPost → handleUpdate()
POST /products?action=delete  → doPost → handleDelete()
```

---

## Security Implications

### GET Security Risks

```java
// RISKY: Sensitive data in URL (visible in history, logs, proxies)
// GET /login?username=admin&password=secret123
// Password in URL → Logged in browser history!

// BETTER: Use POST for authentication
// POST /login
// Body: username=admin&password=secret123
```

### POST Security Risks

```java
// RISKY: No CSRF protection
<form method="POST" action="/transfer">
    <input type="hidden" name="amount" value="1000" />
    <input type="hidden" name="to" value="attacker" />
    <!-- Attacker tricks user into submitting this form -->
</form>

// SECURE: Add CSRF token
<form method="POST" action="/transfer">
    <input type="hidden" name="csrf" value="unique_token_123" />
    <!-- Verify token in servlet -->
</form>
```

---

## Code Examples

### Example 1: Search Functionality (GET)

```java
public class SearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Read search parameters
        String query = request.getParameter("q");
        String sortBy = request.getParameter("sort");
        
        // Default sort
        if (sortBy == null) {
            sortBy = "relevance";
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Search Results</h2>");
        
        // Handle empty query
        if (query == null || query.trim().isEmpty()) {
            out.println("<p>Please enter a search term</p>");
            out.close();
            return;
        }
        
        query = query.trim();
        
        out.println("<p>Query: <strong>" + query + "</strong></p>");
        out.println("<p>Sorted by: " + sortBy + "</p>");
        out.println("<ul>");
        out.println("<li>Result 1 for '" + query + "'</li>");
        out.println("<li>Result 2 for '" + query + "'</li>");
        out.println("<li>Result 3 for '" + query + "'</li>");
        out.println("</ul>");
        
        out.close();
    }
}
```

**Browser:** `http://localhost:8080/app/search?q=java&sort=date`

### Example 2: Login Form (GET + POST)

```java
public class LoginServlet extends HttpServlet {
    
    // Display login form
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h2>Login</h2>");
        out.println("<form method='POST'>");
        out.println("  <input type='text' name='username' placeholder='Username' required /><br/>");
        out.println("  <input type='password' name='password' placeholder='Password' required /><br/>");
        out.println("  <button type='submit'>Login</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
    
    // Process login
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Validate
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h2>Error: Missing credentials</h2>");
            out.close();
            return;
        }
        
        // Mock authentication
        if ("admin".equals(username) && "password123".equals(password)) {
            out.println("<h2>Login Successful!</h2>");
            out.println("<p>Welcome, " + username + "</p>");
            out.println("<a href='/app/dashboard'>Go to Dashboard</a>");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("<h2>Login Failed!</h2>");
            out.println("<p><a href='/app/login'>Try Again</a></p>");
        }
        
        out.close();
    }
}
```

### Example 3: Forwarding vs Redirection

```java
public class RoutingServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("forward".equals(action)) {
            // Forward to another servlet (same request)
            // Browser URL stays as /routing
            RequestDispatcher dispatcher = 
                request.getRequestDispatcher("/process");
            dispatcher.forward(request, response);
        } 
        else if ("redirect".equals(action)) {
            // Redirect to another servlet (new request)
            // Browser URL changes to /success
            response.sendRedirect("/app/success");
        }
        else {
            response.getWriter().println("Unknown action");
        }
    }
}

// Target servlet for forward
@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Can access original request parameters
        String action = request.getParameter("action");
        
        response.setContentType("text/html");
        response.getWriter().println("<h2>Processing...</h2>");
        response.getWriter().println("<p>Action was: " + action + "</p>");
    }
}
```

### Example 4: REST-Style Handlers

```java
public class ResourceServlet extends HttpServlet {
    
    // GET /api/items → List all
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.getWriter().println("[{\"id\":1,\"name\":\"Item1\"}, {\"id\":2,\"name\":\"Item2\"}]");
    }
    
    // POST /api/items → Create
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().println("{\"id\":3,\"name\":\"" + name + "\",\"created\":true}");
    }
    
    // PUT /api/items/{id} → Update
    @Override
    protected void doPut(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();  // e.g., "/3"
        String name = request.getParameter("name");
        
        response.setContentType("application/json");
        response.getWriter().println("{\"id\":" + pathInfo + ",\"name\":\"" + name + "\",\"updated\":true}");
    }
    
    // DELETE /api/items/{id} → Remove
    @Override
    protected void doDelete(HttpServletRequest request, 
                           HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        response.setContentType("application/json");
        response.getWriter().println("{\"id\":" + pathInfo + ",\"deleted\":true}");
    }
}
```

---

## Important Notes

### 1. **Use GET for Idempotent Operations**
```java
// Good: GET is safe and idempotent
GET /api/users/5      // Always returns same user
GET /search?q=java    // Can call multiple times safely

// Wrong: POST for read operations wastes resources
POST /api/users/5     // Unnecessary, should be GET
```

### 2. **Use POST for State-Changing Operations**
```java
// Good: POST for write operations
POST /users           // Create new user
POST /transfer        // Transfer money
DELETE /users/5       // Delete user

// Wrong: GET for state-changing is dangerous
GET /delete?id=5      // Vulnerable to link hijacking
```

### 3. **POST-Redirect-GET Pattern**

```java
// ANTI-PATTERN: Redirect after form submission without redirect
@Override
protected void doPost(HttpServletRequest request, 
                     HttpServletResponse response) {
    saveData(request);
    // Display directly
    response.getWriter().println("Saved!");
    // Refresh page → Form resubmitted!
}

// PATTERN: POST-Redirect-GET
@Override
protected void doPost(HttpServletRequest request, 
                     HttpServletResponse response) {
    saveData(request);
    // Redirect to GET endpoint
    response.sendRedirect("/app/success");
    // Refresh page → Safe GET call
}
```

### 4. **Request Method Matters**
```java
// Some frameworks auto-handle, but servlet doesn't
// Must explicitly implement doGet(), doPost(), etc.

// If only doGet() implemented and POST sent:
// Servlet returns "405 Method Not Allowed"

// Best practice: Implement only what's needed
```

### 5. **Sensitive Data in URLs**
```java
// NEVER use GET for passwords, tokens, etc.
// GET /login?user=admin&pass=secret123  // BAD!

// Use POST instead
// POST /login
// Body: user=admin&pass=secret123  // Better (though use HTTPS)
```

### 6. **Always Send Appropriate Status Codes**
```java
// Good practice:
response.setStatus(HttpServletResponse.SC_OK);           // 200 GET success
response.setStatus(HttpServletResponse.SC_CREATED);      // 201 POST success
response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Invalid input
response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Not authenticated
response.setStatus(HttpServletResponse.SC_FORBIDDEN);    // 403 Not authorized
response.setStatus(HttpServletResponse.SC_NOT_FOUND);    // 404 Resource missing
```

---
