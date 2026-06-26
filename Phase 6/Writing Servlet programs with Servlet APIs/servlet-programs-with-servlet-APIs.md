# Writing Servlet Programs with Servlet APIs

---

## Table of Contents
1. [Servlet API Overview](#servlet-api-overview)
2. [HttpServletRequest Methods](#httpservletrequest-methods)
3. [HttpServletResponse Methods](#httpservletresponse-methods)
4. [ServletConfig Methods](#servletconfig-methods)
5. [ServletContext Methods](#servletcontext-methods)
6. [Request/Response Flow](#requestresponse-flow)
7. [Code Examples](#code-examples)
8. [Common Exceptions](#common-exceptions)
9. [Important Notes](#important-notes)

---

## Servlet API Overview

### Core Classes and Interfaces

```
javax.servlet Package
├─ Servlet (interface)
│  └ Methods: init(), service(), destroy()
│
├─ GenericServlet (abstract class)
│  └ Methods: init(), destroy(), service() (abstract)
│
├─ ServletConfig (interface)
│  └ Methods: getInitParameter(), getInitParameterNames(), getServletContext()
│
├─ ServletContext (interface)
│  └ Methods: getAttribute(), setAttribute(), getInitParameter(), log()
│
└─ ServletException, UnavailableException


javax.servlet.http Package
├─ HttpServlet (abstract class, extends GenericServlet)
│  └ Methods: doGet(), doPost(), doPut(), doDelete(), service()
│
├─ HttpServletRequest (interface)
│  └ Methods: getParameter(), getHeader(), getMethod(), getSession(), etc.
│
├─ HttpServletResponse (interface)
│  └ Methods: setStatus(), setHeader(), sendError(), getWriter(), etc.
│
├─ HttpSession (interface)
│  └ Methods: getAttribute(), setAttribute(), getId(), invalidate()
│
└─ Cookie (class)
   └ Methods: getName(), getValue(), setMaxAge(), setPath()
```

---

## HttpServletRequest Methods

### Purpose
Access information about HTTP request: headers, parameters, method, attributes

### Key Methods

| Method | Return Type | Purpose |
|--------|-------------|---------|
| **getParameter(String name)** | String | Get single query/form parameter |
| **getParameterValues(String name)** | String[] | Get multiple values (checkboxes) |
| **getParameterNames()** | Enumeration | Get all parameter names |
| **getParameterMap()** | Map | Get all parameters as map |
| **getHeader(String name)** | String | Get HTTP header value |
| **getHeaders(String name)** | Enumeration | Get multiple header values |
| **getHeaderNames()** | Enumeration | Get all header names |
| **getMethod()** | String | Get HTTP method (GET, POST, etc.) |
| **getRequestURI()** | String | Get request URI (e.g., /app/login) |
| **getRequestURL()** | StringBuffer | Get full URL (e.g., http://localhost/app/login) |
| **getQueryString()** | String | Get query string (e.g., name=John&age=25) |
| **getInputStream()** | ServletInputStream | Get request body as stream (binary) |
| **getReader()** | BufferedReader | Get request body as character stream |
| **getAttribute(String name)** | Object | Get request attribute (set by server) |
| **setAttribute(String name, Object value)** | void | Set request attribute |
| **getSession()** | HttpSession | Get/create user session |
| **getSession(boolean create)** | HttpSession | Get session (create if true) |
| **getCookies()** | Cookie[] | Get all cookies sent by client |
| **getRemoteAddr()** | String | Get client IP address |
| **getRemoteUser()** | String | Get authenticated user name |
| **getServerName()** | String | Get server domain name |
| **getServerPort()** | int | Get server port (80, 8080, etc.) |
| **getContextPath()** | String | Get application path (e.g., /myapp) |
| **getServletPath()** | String | Get servlet path (e.g., /login) |
| **getPathInfo()** | String | Get path after servlet path |

### Request Data Categories

```
HTTP Request Structure Mapping to Methods:

GET /app/login?username=john&password=123 HTTP/1.1
│   │    │    │    │
│   │    │    │    └─→ getQueryString()
│   │    │    └───────→ getParameterMap()
│   │    └────────────→ getServletPath()
│    └───────────────→ getContextPath()
└────────────────────→ getRequestURI()

Host: localhost:8080
User-Agent: Mozilla/5.0
┌──→ getHeader("Host")
└──→ getHeader("User-Agent")
```

---

## HttpServletResponse Methods

### Purpose
Send HTTP response: status code, headers, content

### Key Methods

| Method | Return Type | Purpose |
|--------|-------------|---------|
| **setStatus(int sc)** | void | Set HTTP status code (200, 404, etc.) |
| **setContentType(String type)** | void | Set response content type (MIME type) |
| **setContentLength(int len)** | void | Set response body length |
| **setHeader(String name, String value)** | void | Set response header (replaces if exists) |
| **addHeader(String name, String value)** | void | Add response header (doesn't replace) |
| **getWriter()** | PrintWriter | Get character output stream |
| **getOutputStream()** | ServletOutputStream | Get binary output stream |
| **sendRedirect(String location)** | void | Send 302 redirect to new URL |
| **sendError(int sc)** | void | Send error status with default message |
| **sendError(int sc, String msg)** | void | Send error status with custom message |
| **addCookie(Cookie cookie)** | void | Add cookie to response |
| **containsHeader(String name)** | boolean | Check if header already set |
| **isCommitted()** | boolean | Check if response headers sent |
| **reset()** | void | Clear response buffer (before commit) |
| **flushBuffer()** | void | Send buffered output to client |
| **getBufferSize()** | int | Get response buffer size |
| **setBufferSize(int size)** | void | Set response buffer size |

### Common Status Codes with Constants

```java
// 2xx Success
HttpServletResponse.SC_OK                    // 200
HttpServletResponse.SC_CREATED               // 201
HttpServletResponse.SC_ACCEPTED              // 202
HttpServletResponse.SC_NO_CONTENT            // 204

// 3xx Redirection
HttpServletResponse.SC_MOVED_PERMANENTLY     // 301
HttpServletResponse.SC_FOUND                 // 302
HttpServletResponse.SC_TEMPORARY_REDIRECT    // 307
HttpServletResponse.SC_NOT_MODIFIED          // 304

// 4xx Client Error
HttpServletResponse.SC_BAD_REQUEST           // 400
HttpServletResponse.SC_UNAUTHORIZED          // 401
HttpServletResponse.SC_FORBIDDEN             // 403
HttpServletResponse.SC_NOT_FOUND             // 404
HttpServletResponse.SC_METHOD_NOT_ALLOWED    // 405
HttpServletResponse.SC_CONFLICT              // 409

// 5xx Server Error
HttpServletResponse.SC_INTERNAL_SERVER_ERROR // 500
HttpServletResponse.SC_BAD_GATEWAY           // 502
HttpServletResponse.SC_SERVICE_UNAVAILABLE   // 503
```

---

## ServletConfig Methods

### Purpose
Access servlet-specific initialization parameters and context

### Key Methods

| Method | Return Type | Purpose |
|--------|-------------|---------|
| **getInitParameter(String name)** | String | Get servlet init parameter from web.xml |
| **getInitParameterNames()** | Enumeration | Get all init parameter names |
| **getServletName()** | String | Get servlet name from web.xml |
| **getServletContext()** | ServletContext | Get application-level context |

### Access Pattern
```java
// In init() method:
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    // Now can access:
    String value = config.getInitParameter("param.name");
    String name = config.getServletName();
    ServletContext context = config.getServletContext();
}

// Also can access later via:
ServletConfig config = getServletConfig();  // Returns config stored by super.init()
```

---

## ServletContext Methods

### Purpose
Access application-level data, resources, and configuration

### Key Methods

| Method | Return Type | Purpose |
|--------|-------------|---------|
| **getAttribute(String name)** | Object | Get application attribute (shared by all servlets) |
| **setAttribute(String name, Object value)** | void | Set application attribute |
| **removeAttribute(String name)** | void | Remove application attribute |
| **getAttributeNames()** | Enumeration | Get all attribute names |
| **getInitParameter(String name)** | String | Get application init parameter |
| **getInitParameterNames()** | Enumeration | Get all init parameter names |
| **getRealPath(String path)** | String | Convert relative path to absolute file path |
| **getResourceAsStream(String path)** | InputStream | Get resource file as stream |
| **getResource(String path)** | URL | Get resource file as URL |
| **log(String message)** | void | Write message to server log |
| **log(String message, Throwable t)** | void | Write message and exception to log |
| **getServletNames()** | String[] | Get names of all servlets in app |
| **getServletNamesForPath(String path)** | Collection | Get servlets matching URL pattern |
| **getServerInfo()** | String | Get server software name/version |
| **getMajorVersion()** | int | Get servlet specification major version |
| **getMinorVersion()** | int | Get servlet specification minor version |

### Application vs Request Attributes
```
Request Attributes:              ServletContext Attributes:
└─ request.setAttribute()        └─ servletContext.setAttribute()
   Scoped to single request         Scoped to entire application
   Destroyed after response         Live until application stops
   Not shared between requests      Shared by all servlets/threads
   Access via request object       Access via context object
```

---

## Request/Response Flow

### Complete Request-Response Cycle

```
CLIENT                  SERVER (Servlet Container)
  │                              │
  │  1. Send HTTP Request        │
  ├─────────────────────────────>│
  │  GET /app/hello?name=john    │
  │                              │
  │                    2. Parse Request
  │                       Create HttpServletRequest object
  │                       Parse headers, parameters, body
  │                              │
  │                    3. Create HttpServletResponse
  │                       Allocate output buffer
  │                       Initialize status, headers
  │                              │
  │                    4. Route to Servlet
  │                       Call servlet.service(req, res)
  │                       ├─ Identify method (GET)
  │                       └─ Call doGet(req, res)
  │                              │
  │                    5. Servlet Executes
  │                       - Read request data
  │                       - Process business logic
  │                       - Generate output
  │                              │
  │                    6. Servlet Writes Response
  │                       res.setStatus(200)
  │                       res.setHeader("Content-Type", "text/html")
  │                       res.getWriter().println("<html>...")
  │                              │
  │  7. Receive HTTP Response    │
  │<─────────────────────────────┤
  │  HTTP/1.1 200 OK             │
  │  Content-Type: text/html     │
  │  Content-Length: 256         │
  │  <html>...</html>            │
  │                              │
  8. Browser Processes & Renders │
  │                              │
```

### Data Flow Within Servlet

```
doGet(HttpServletRequest req, HttpServletResponse res)
  │
  ├─→ Read From Request
  │   ├─ req.getParameter("name")      → "john"
  │   ├─ req.getHeader("User-Agent")   → "Mozilla/5.0"
  │   ├─ req.getMethod()               → "GET"
  │   └─ req.getSession()              → HttpSession
  │
  ├─→ Process Data
  │   ├─ Database query
  │   ├─ Business logic
  │   └─ Generate response content
  │
  └─→ Write To Response
      ├─ res.setStatus(200)
      ├─ res.setHeader("Content-Type", "text/html")
      ├─ res.addCookie(cookie)
      └─ res.getWriter().println("<html>...")
```

---

## Code Examples

### Example 1: Basic Request/Response Handling

```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class BasicServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Read from request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String method = request.getMethod();
        
        // Validate
        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }
        
        // 2. Set response headers (BEFORE writing body)
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("X-Custom", "MyValue");
        
        // 3. Get writer and send response
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Welcome</title></head>");
        out.println("<body>");
        out.println("<h1>Welcome, " + name + "!</h1>");
        
        if (email != null) {
            out.println("<p>Email: " + email + "</p>");
        }
        
        out.println("<p>Method: " + method + "</p>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
}
```

**Usage:** `http://localhost:8080/app/basic?name=John&email=john@example.com`

### Example 2: Reading All Request Headers

```java
public class HeaderReaderServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Request Information</h2>");
        
        // Request line info
        out.println("<h3>Request Line</h3>");
        out.println("<p>Method: " + request.getMethod() + "</p>");
        out.println("<p>URI: " + request.getRequestURI() + "</p>");
        out.println("<p>URL: " + request.getRequestURL() + "</p>");
        out.println("<p>Query: " + request.getQueryString() + "</p>");
        
        // Server info
        out.println("<h3>Server</h3>");
        out.println("<p>Server: " + request.getServerName() + "</p>");
        out.println("<p>Port: " + request.getServerPort() + "</p>");
        out.println("<p>Context: " + request.getContextPath() + "</p>");
        out.println("<p>Servlet: " + request.getServletPath() + "</p>");
        
        // Client info
        out.println("<h3>Client</h3>");
        out.println("<p>IP: " + request.getRemoteAddr() + "</p>");
        out.println("<p>Host: " + request.getRemoteHost() + "</p>");
        
        // Headers
        out.println("<h3>Headers</h3>");
        out.println("<table border='1'>");
        
        java.util.Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            out.println("<tr><td>" + name + "</td><td>" + value + "</td></tr>");
        }
        
        out.println("</table>");
        out.close();
    }
}
```

### Example 3: Handling Form Parameters

```java
public class FormServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] interests = request.getParameterValues("interests");  // Multiple checkboxes
        String country = request.getParameter("country");
        
        // Validate
        if (username == null || username.isEmpty() || 
            password == null || password.isEmpty()) {
            
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().println("{\"error\":\"Missing required fields\"}");
            return;
        }
        
        // Set response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Registration Successful</h2>");
        out.println("<p>Username: " + username + "</p>");
        out.println("<p>Country: " + country + "</p>");
        
        if (interests != null && interests.length > 0) {
            out.println("<p>Interests:</p>");
            out.println("<ul>");
            for (String interest : interests) {
                out.println("<li>" + interest + "</li>");
            }
            out.println("</ul>");
        }
        
        out.close();
    }
}
```

### Example 4: Working with ServletContext

```java
public class ContextServlet extends HttpServlet {
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        ServletContext context = config.getServletContext();
        
        // Get application-level init parameters (from web.xml)
        String appVersion = context.getInitParameter("app.version");
        String dbUrl = context.getInitParameter("db.url");
        
        // Set application-level attributes (shared by all servlets)
        context.setAttribute("startTime", System.currentTimeMillis());
        context.setAttribute("appName", "MyApp");
        
        // Log to server logs
        context.log("ContextServlet initialized. App: " + appVersion);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        ServletContext context = getServletContext();
        
        // Retrieve application attributes
        Long startTime = (Long) context.getAttribute("startTime");
        String appName = (String) context.getAttribute("appName");
        long uptime = System.currentTimeMillis() - startTime;
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>" + appName + " Status</h2>");
        out.println("<p>Uptime: " + uptime + " ms</p>");
        out.println("<p>Server: " + context.getServerInfo() + "</p>");
        
        out.close();
    }
}
```

**web.xml for above example:**
```xml
<context-param>
    <param-name>app.version</param-name>
    <param-value>1.0.0</param-value>
</context-param>

<context-param>
    <param-name>db.url</param-name>
    <param-value>jdbc:mysql://localhost/mydb</param-value>
</context-param>
```

### Example 5: Error Handling and Redirects

```java
public class ErrorHandlingServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            // Send error response
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST, 
                "Missing required parameter: action"
            );
            return;
        }
        
        if (action.equals("login")) {
            // Successful operation - redirect to home
            response.sendRedirect("/app/home");
            return;
        }
        
        if (action.equals("logout")) {
            // Another redirect
            response.sendRedirect("/app/login?msg=LoggedOut");
            return;
        }
        
        // Unknown action
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\":\"Unknown action: " + action + "\"}");
    }
}
```

---

## Common Exceptions

| Exception | Cause | Solution |
|-----------|-------|----------|
| **IOException** | I/O error reading/writing streams | Declare throws or try-catch |
| **ServletException** | General servlet error | Wrap underlying exception |
| **IllegalStateException** | Called after response committed | Check response state first |
| **NullPointerException** | Null parameter not validated | Add null checks |
| **NumberFormatException** | String to number conversion failed | Use try-catch for parseInt() |

---

## Important Notes

### 1. **Set Content-Type BEFORE Writing**
```java
// CORRECT
response.setContentType("text/html");
response.getWriter().println("<html>");

// WRONG - type may be ignored
response.getWriter().println("<html>");
response.setContentType("text/html");
```

### 2. **Parameters vs Attributes**
```java
// Request parameter - from query string or form body
String name = request.getParameter("name");  // "john"

// Request attribute - set by server/filters
request.setAttribute("user", userObj);
User user = (User) request.getAttribute("user");

// Context attribute - application-wide, thread-shared
getServletContext().setAttribute("appName", "MyApp");
String app = (String) getServletContext().getAttribute("appName");
```

### 3. **Use getParameter() Safely**
```java
// getParameter() returns null if not present
String optional = request.getParameter("optional");
if (optional != null) {
    // Use optional
}

// For multiple values (checkboxes)
String[] values = request.getParameterValues("checkbox");
if (values != null) {
    for (String value : values) {
        // Process value
    }
}
```

### 4. **Headers vs Parameters**
```
GET /app/search?q=java HTTP/1.1
     │      │      │
     │      │      └─→ Query parameter: request.getParameter("q")
     │      └────────→ Servlet path: request.getServletPath()
     └──────────────→ Context path: request.getContextPath()

Host: localhost:8080
User-Agent: Mozilla/5.0
└──→ Headers: request.getHeader("Host")
```

### 5. **Response Committed**
```java
// Once response is committed (headers sent to client):
// - Cannot change status code
// - Cannot add headers
// - Can only write to body

// Check before operations:
if (!response.isCommitted()) {
    response.setStatus(HttpServletResponse.SC_OK);
}
```

### 6. **Encode Output**
```java
// Always encode user input to prevent XSS
String userInput = request.getParameter("name");
String encoded = org.apache.commons.text.StringEscapeUtils
    .escapeHtml4(userInput);
response.getWriter().println("<p>" + encoded + "</p>");
```

### 7. **Resource Scope**
```
Request Scope:        Session Scope:           Application Scope:
request.setAttribute  session.setAttribute     context.setAttribute
│                     │                        │
├─ Lifetime: 1 req   ├─ Lifetime: session     ├─ Lifetime: app running
├─ Thread: 1 thread  ├─ Thread: multiple      ├─ Thread: multiple
├─ Shared: No        ├─ Shared: Single user   ├─ Shared: All users
└─ Type: Object      └─ Type: Object          └─ Type: Object (THREAD-SAFE!)
```

### 8. **Always Close Writer**
```java
PrintWriter out = response.getWriter();
try {
    out.println("Hello");
} finally {
    out.close();  // Or use try-with-resources
}

// Better: try-with-resources (auto-closes)
try (PrintWriter out = response.getWriter()) {
    out.println("Hello");
}
```

---
